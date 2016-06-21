package com.sccomponents.utils.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.sccomponents.utils.ScChecker;

public class GenericChecker extends AppCompatActivity {

    // Holder
    private TextView mText = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Init
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generic_checker);

        // Get the text component
        this.mText = (TextView) this.findViewById(R.id.text1);

        // Create the checker
        MyChecker checker = new MyChecker(this);
        // Check every 3 seconds
        checker.setCheckRate(3000);
        // Listener
        checker.setCheckerListener(new ScChecker.CheckerListener() {
            @Override
            public void onSuccess() {
                // If the check return true
                GenericChecker.this.write("Still true");
            }

            @Override
            public void onFail() {
                // If the check return false
                GenericChecker.this.write("Still false");
            }

            @Override
            public void onChangeState(boolean result) {
                // If the check return true
                GenericChecker.this.write("Now changed to " + result);
            }
        });

        // Start to check
        checker.start();
    }

    // Write on screen
    private void write(final String text) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                GenericChecker.this.mText.setText(text);
            }
        });
    }
}
