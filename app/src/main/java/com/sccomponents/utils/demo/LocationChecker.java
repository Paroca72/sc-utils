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
                // Do nothing
            }

            @Override
            public void onFail() {
                // Do nothing
            }

            @Override
            public void onChangeState(boolean result) {
                // Write
                LocationChecker.this.write();
            }
        });
        this.mService.startLocationTracking(new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                TextView text = (TextView) LocationChecker.this.findViewById(R.id.txtLocation);
                if (location == null) {
                    text.setText("Unknown location");
                } else {
                    text.setText(location.getLatitude() + " : " + location.getLongitude());
                }
            }
        });

        // Write
        this.write();
    }

    // Write the current status
    private void write() {
        // Network
        TextView network = (TextView) this.findViewById(R.id.txtNetwork);
        network.setText(this.mService.isNetworkEnabled() ? "ON" : "OFF");

        // GPS
        TextView gps = (TextView) this.findViewById(R.id.txtGPS);
        gps.setText(this.mService.isGPSEnabled() ? "ON" : "OFF");

        // GPS
        TextView available = (TextView) this.findViewById(R.id.txtAvailable);
        available.setText(this.mService.check() ? "YES" : "NO");
    }

}
