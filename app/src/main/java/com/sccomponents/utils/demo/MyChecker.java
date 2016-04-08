package com.sccomponents.utils.demo;

import android.app.Activity;
import android.content.Context;
import android.widget.Switch;

import com.sccomponents.utils.ScChecker;

/**
 * This is a very simple example of ScChecker use
 */
public class MyChecker extends ScChecker {

    // Context holder
    private Context mContext = null;

    // Constructor
    public MyChecker(Context context) {
        // Save the source context
        this.mContext = context;
    }

    @Override
    public boolean check() {
        // Get the component
        Switch sw = (Switch) ((Activity) this.mContext).findViewById(R.id.switch1);
        // Return true if checked
        return sw.isChecked();
    }
}
