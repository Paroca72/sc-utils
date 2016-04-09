package com.sccomponents.utils;

import android.app.Service;
import android.content.Context;
import android.location.LocationManager;

/**
 * Check the location service status
 * Call the function NetworkService or GPS for check the current status or call check get true is
 * at least one connection is alive.
 *
 * v1.0.0
 *
 */
@SuppressWarnings("unused")
public class ScLocationService extends ScChecker {

    /**
     * Static and privates variables
     */

    // Location service manager
    private static LocationManager mManager = null;
    // The context holder
    private Context mContext = null;


    /**
     * Constructor
     */

    public ScLocationService(Context context) {
        // Save the context reference
        this.mContext = context;
    }


    /**
     * Public methods
     */

    // Check is able a networkService connection
    @SuppressWarnings("unused")
    public boolean isNetworkEnabled() {
        // Find the manager
        LocationManager manager = this.getLocationManager();
        // Check if the location via networkService is enabled
        return manager != null && manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    // Check is able a LocationService connection
    @SuppressWarnings("unused")
    public boolean isGPSEnabled() {
        // Find the manager
        LocationManager manager = this.getLocationManager();
        // Check if the location via LocationService is enabled
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

    // Override the check function.
    // As this class extend the ScChecker this methods can be auto-checked periodically.
    @Override
    @SuppressWarnings("unused")
    public boolean check() {
        return this.isGPSEnabled() || this.isNetworkEnabled();
    }

}
