package com.sccomponents.utils.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sccomponents.utils.ScChecker;

public class MainActivity extends AppCompatActivity {

    // Holder
    private MyChecker mChecker = null;
    private TextView mText = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Init
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the text component
        this.mText = (TextView) this.findViewById(R.id.text1);

        // Create the checker
        this.mChecker = new MyChecker(this);
        // Check every 3 seconds
        this.mChecker.setCheckRate(3000);
        // Listener
        this.mChecker.setCheckerListener(new ScChecker.CheckerListener() {
            @Override
            public void onSuccess() {
                // If the check return true
                MainActivity.this.write("Still true");
            }

            @Override
            public void onFail() {
                // If the check return false
                MainActivity.this.write("Still false");
            }

            @Override
            public void onChangeState(boolean result) {
                // If the check return true
                MainActivity.this.write("Now changed to " + result);
            }
        });

        // Apply on click event on the button
        Button button = (Button) this.findViewById(R.id.button1);
        assert button != null;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start to check
                MainActivity.this.mChecker.start();
            }
        });
    }

    // Write on screen
    private void write(final String text) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                MainActivity.this.mText.setText(text);
            }
        });
    }
}
