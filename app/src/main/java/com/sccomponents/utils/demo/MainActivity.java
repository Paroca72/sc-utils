package com.sccomponents.utils.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity
        extends AppCompatActivity
        implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Init
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Buttons events
        Button genericChecker = (Button) this.findViewById(R.id.btnScChecker);
        genericChecker.setOnClickListener(this);

        Button locationChecker = (Button) this.findViewById(R.id.btnScLocationService);
        locationChecker.setOnClickListener(this);

        Button networkChecker = (Button) this.findViewById(R.id.btnScNetworkService);
        networkChecker.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // Generic intent
        Intent generic = null;

        // Selector
        switch (v.getId()) {
            // Generic
            case R.id.btnScChecker:
                generic = new Intent(this, GenericChecker.class);
                break;

            // Location
            case R.id.btnScLocationService:
                generic = new Intent(this, LocationChecker.class);
                break;

            // Network
            case R.id.btnScNetworkService:
                generic = new Intent(this, NetworkChecker.class);
                break;
        }

        // Start
        this.startActivity(generic);
    }
}
