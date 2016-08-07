package com.sccomponents.utils;

import android.Manifest;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

/**
 * Check the location service status
 * Call the function NetworkService or GPS for check the current status or call check get true is
 * at least one connection is alive.
 * <p/>
 * v2.0.0
 */
public class ScLocationService
        extends ScChecker {

    /**********************************************************************************
     * Static and privates variables
     */

    public final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;   // Define a request code to send to Google Play services
    public static final int REQUEST_PERMISSION_ID = 9001;                   // Request permission key

    private Context mContext;                        // The context holder
    private static LocationManager mManager;         // Location service manager
    private static GoogleApiClient mGoogleApiClient; // Google API client

    private LocationRequest mLocationRequest;    // Location request settings
    private LocationListener mLocationListener;  // Location listener
    private boolean mStartingLocationTracking;  // Trigger for hold the request to starting
    // location tracking


    /**********************************************************************************
     * Constructor
     */

    public ScLocationService(Context context) {
        // Define
        this.mContext = context;
        this.mLocationRequest = new LocationRequest();
        this.mStartingLocationTracking = false;

        // Request the permission
        this.requestPermissions();
        // Initialize the google API client
        this.initializeGoogleAPIClient();
    }


    /**********************************************************************************
     * Private methods
     */

    // Request user permission
    private void requestPermissions() {
        // If version if minor than M we need to have an explicit permission from the user
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                this.mContext != null && this.mContext instanceof Activity) {
            // Get the activity
            Activity activity = (Activity) this.mContext;
            // Check the permission
            if (activity.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    activity.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // If requested show a popup to the have the user permission
                String permissions[] = new String[]{
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION
                };
                activity.requestPermissions(permissions, ScLocationService.REQUEST_PERMISSION_ID);
            }
        }
    }

    // Start the location tracking
    public void internalStartLocationTracking() {
        // Check for empty value and if the service is connected
        if (this.mLocationListener != null && this.mStartingLocationTracking &&
                ScLocationService.mGoogleApiClient.isConnected())
            try {
                // Set the trigger
                this.mStartingLocationTracking = false;

                // Start the service
                LocationServices.FusedLocationApi
                        .requestLocationUpdates(
                                ScLocationService.mGoogleApiClient,
                                this.mLocationRequest,
                                this.mLocationListener
                        );

            } catch (SecurityException e) {
                // NOP
            }
    }

    // Stop the location tracking
    public void internalStopLocationTracking() {
        try {
            // Set the trigger
            this.mStartingLocationTracking = false;
            // Start the service
            LocationServices.FusedLocationApi
                    .removeLocationUpdates(
                            ScLocationService.mGoogleApiClient,
                            this.mLocationListener
                    );

        } catch (SecurityException e) {
            // NOP
        }
    }

    // Try to initialize the google API client
    private void initializeGoogleAPIClient() {
        // Initialize the google API client is needed
        if (ScLocationService.mGoogleApiClient == null && this.mContext != null) {
            ScLocationService.mGoogleApiClient = new GoogleApiClient.Builder(this.mContext)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                        @Override
                        public void onConnected(@Nullable Bundle bundle) {
                            // if have a request to start tracking try to activate the service
                            ScLocationService.this.internalStartLocationTracking();
                        }

                        @Override
                        public void onConnectionSuspended(int i) {
                            // Do nothing
                        }
                    })
                    .build();
        }
    }


    /**********************************************************************************
     * Public methods
     */

    // Check is able to find the location by network
    @SuppressWarnings("unused")
    public boolean isNetworkEnabled() {
        // Find the manager
        LocationManager manager = this.getLocationManager();
        // Check if the location via network
        return manager != null && manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    // Check is able to find the location by GPS
    @SuppressWarnings("unused")
    public boolean isGPSEnabled() {
        // Find the manager
        LocationManager manager = this.getLocationManager();
        // Check if the location via GPS
        return manager != null && manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    // Get the location manager
    @SuppressWarnings("unused")
    public LocationManager getLocationManager() {
        // Check if already exists
        if (ScLocationService.mManager == null && this.mContext != null) {
            // Hold the manager reference in a static private variable
            ScLocationService.mManager = (LocationManager) this.mContext
                    .getSystemService(Service.LOCATION_SERVICE);
        }
        // Return the reference to the manager
        return ScLocationService.mManager;
    }


    /**********************************************************************************
     * Location tracking
     */

    // Check if the location tracker is enabled
    @SuppressWarnings("unused")
    public boolean isLocationTrackerEnabled() {
        // Check for empty value
        if (ScLocationService.mGoogleApiClient != null) {
            return ScLocationService.mGoogleApiClient.isConnected();
        }
        // Return false
        return false;
    }

    /*
     * Check is the google API is available.
     * The google API needed for the focused location tracker.
     */
    @SuppressWarnings("unused")
    public boolean isGoogleAPIAvailable() {
        // Check for empty values
        if (this.mContext == null) return false;

        // Get the google API checker
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        // Request if the play service is available and hold the result
        int result = googleAPI.isGooglePlayServicesAvailable(this.mContext);
        // If success
        if (result == ConnectionResult.SUCCESS) {
            // Continue
            return true;
        }
        // If not available
        else {
            // Check if possible to have a resolution
            if (googleAPI.isUserResolvableError(result) && this.mContext instanceof Activity) {
                // Open the error dialog
                googleAPI.getErrorDialog(
                        (Activity) this.mContext,
                        result,
                        ScLocationService.CONNECTION_FAILURE_RESOLUTION_REQUEST
                ).show();
            }
            // Return
            return false;
        }
    }

    // Start the location tracking
    @SuppressWarnings("unused")
    public void startLocationTracking(LocationListener listener) {
        // Hold the listener and set the trigger
        this.mLocationListener = listener;
        this.mStartingLocationTracking = true;

        // Check for the connection
        if (!ScLocationService.mGoogleApiClient.isConnected()) {
            // Try to connect and the tracker will attached once the connection will done
            ScLocationService.mGoogleApiClient.connect();
        }
        // Else attach the location tracker directly
        else {
            this.internalStartLocationTracking();
        }
    }

    // Stop the location tracking
    @SuppressWarnings("unused")
    public void stopLocationTracking() {
        // Stop the tracking
        this.internalStopLocationTracking();
        // Stop the service
        ScLocationService.mGoogleApiClient.disconnect();
    }

    // Get the last known location
    @SuppressWarnings("unused")
    public Location getLocation() {
        try {
            // Check for connection
            if (ScLocationService.mGoogleApiClient.isConnected()) {
                // Try to get the last location
                return LocationServices.FusedLocationApi
                        .getLastLocation(ScLocationService.mGoogleApiClient);
            }

        } catch (SecurityException e) {
            // NOP
        }
        // In error case return null
        return null;
    }

    // Get the location request object
    @SuppressWarnings("unused")
    public LocationRequest getLocationRequest() {
        return this.mLocationRequest;
    }


    /**********************************************************************************
     * Override
     */

    // Override the check function.
    // As this class extend the ScChecker this methods can be auto-checked periodically.
    @Override
    @SuppressWarnings("unused")
    public boolean check() {
        // Return the current status
        return this.isGPSEnabled() || this.isNetworkEnabled();
    }

}
