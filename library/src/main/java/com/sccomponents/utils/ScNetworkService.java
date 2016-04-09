package com.sccomponents.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Check the networkService service status.
 * Call the function Wifi or Mobile for check the current status or call check get true is
 * at least one connection is alive.
 *
 * v1.0.0
 *
 */
@SuppressWarnings("unused")
public class ScNetworkService extends ScChecker {

    /**
     * Static variables
     */

    // Connectivity service manager
    private static ConnectivityManager mManager = null;
    // The context holder
    private Context mContext = null;


    /**
     * Constructor
     */

    public ScNetworkService(Context context) {
        // Save the context reference
        this.mContext = context;
    }


    /**
     * Public methods
     */

    // Get the connectivity manager
    @SuppressWarnings("unused")
    public ConnectivityManager getConnectivityManager() {
        // Check if already exists
        if (ScNetworkService.mManager == null && this.mContext != null) {
            // Hold the manager reference in a static private variable
            ScNetworkService.mManager = (ConnectivityManager) this.mContext
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
        }
        // Return the reference to the manager
        return ScNetworkService.mManager;
    }

    // Check the wifi connection status
    @SuppressWarnings("unused")
    public boolean isWifiEnabled() {
        //Create object for ConnectivityManager class which returns networkService related info
        ConnectivityManager manager = this.getConnectivityManager();
        //If connectivity object is not null
        if (manager != null) {
            // Get the networkService info
            NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
            // Check if mobile type connection is active
            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                // Connected
                return true;
            }
        }
        // No connection
        return false;
    }

    // Check the mobile connection status
    @SuppressWarnings("unused")
    public boolean isMobileEnabled() {
        //Create object for ConnectivityManager class which returns networkService related info
        ConnectivityManager manager = this.getConnectivityManager();
        //If connectivity object is not null
        if (manager != null) {
            // Get the networkService info
            NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
            // Check if wi-fi type connection is active
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                // Connected
                return true;
            }
        }
        // No connection
        return false;
    }

    // Override the check function.
    // As this class extend the ScChecker this methods can be auto-checked periodically.
    @Override
    @SuppressWarnings("unused")
    public boolean check() {
        return this.isWifiEnabled() || this.isMobileEnabled();
    }

}
