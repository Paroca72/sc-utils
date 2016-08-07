package com.sccomponents.utils.demo;

import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.location.LocationListener;
import com.sccomponents.utils.ScChecker;
import com.sccomponents.utils.ScLocationService;

public class LocationChecker extends AppCompatActivity {

    // Holder
    private ScLocationService mService = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Init
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_checker);

        // Create the checker
        this.mService = new ScLocationService(this);
        this.mService.setCheckerListener(new ScChecker.CheckerListener() {
            @Override
            public void onSuccess() {
                // Write
                LocationChecker.this.writeStatus();
            }

            @Override
            public void onFail() {
                // Write
                LocationChecker.this.writeStatus();
            }

            @Override
            public void onChangeState(boolean result) {
                // Do nothing
            }
        });

        // Write the initial status
        this.writeStatus();

        // Start
        this.mService.start(new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                // Write
                LocationChecker.this.writeLocation(location);
            }
        });
    }

    // Write the current status
    private void writeStatus() {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // Network
                TextView network = (TextView) LocationChecker.this.findViewById(R.id.txtNetwork);
                assert network != null;
                network.setText(LocationChecker.this.mService.isNetworkEnabled() ? "ON" : "OFF");

                // GPS
                TextView gps = (TextView) LocationChecker.this.findViewById(R.id.txtGPS);
                assert gps != null;
                gps.setText(LocationChecker.this.mService.isGPSEnabled() ? "ON" : "OFF");

                // GPS
                TextView available = (TextView) LocationChecker.this.findViewById(R.id.txtAvailable);
                assert available != null;
                available.setText(LocationChecker.this.mService.check() ? "YES" : "NO");
            }
        });
    }

    // Write the current location
    private void writeLocation(final Location location) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView text = (TextView) LocationChecker.this.findViewById(R.id.txtLocation);
                assert text != null;

                if (location == null) {
                    text.setText("Unknown location");
                } else {
                    text.setText(location.getLatitude() + " : " + location.getLongitude());
                }
            }
        });
    }

}
