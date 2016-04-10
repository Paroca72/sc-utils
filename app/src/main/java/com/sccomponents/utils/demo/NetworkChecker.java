package com.sccomponents.utils.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.sccomponents.utils.ScChecker;
import com.sccomponents.utils.ScLocationService;
import com.sccomponents.utils.ScNetworkService;

public class NetworkChecker extends AppCompatActivity {

    // Holder
    private ScNetworkService mService = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Init
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_checker);

        // Create the checker
        this.mService = new ScNetworkService(this);
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
                NetworkChecker.this.write();
            }
        });

        // Write
        this.write();
    }

    // Write the current status
    private void write() {
        // Network
        TextView mobile = (TextView) this.findViewById(R.id.txtMobile);
        mobile.setText(this.mService.isMobileEnabled() ? "ON" : "OFF");

        // GPS
        TextView wiFi = (TextView) this.findViewById(R.id.txtWiFi);
        wiFi.setText(this.mService.isWifiEnabled() ? "ON" : "OFF");

        // GPS
        TextView available = (TextView) this.findViewById(R.id.txtAvailable);
        available.setText(this.mService.check() ? "YES" : "NO");
    }

}
