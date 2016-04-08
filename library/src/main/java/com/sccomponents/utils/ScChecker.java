package com.sccomponents.utils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * This class manager a fixed timed repeat check function.
 * Useful when you need to check periodically.
 */
@SuppressWarnings("unused")
public abstract class ScChecker {

    /**
     * Private variables
     */

    private ScheduledExecutorService mExecutor = null;  // The executor
    private long mStartDelay = 100;                     // Start delay in milliseconds
    private long mCheckRate = 1000;                     // Check rate
    private Modes mMode = Modes.FIXED_DELAY;            // The check mode type
    private Boolean mLastState = null;                  // Hold the last result state
    private CheckerListener mListener = null;             // The listener


    /**
     * Private methods
     */

    // The handler
    private class Handler implements Runnable {
        public void run() {
            // Check
            boolean result = ScChecker.this.check();

            // Call the methods
            if (result) {
                // Overridable
                ScChecker.this.onSuccess();
                // Listener
                if (ScChecker.this.mListener != null)
                    ScChecker.this.mListener.onSuccess();

            } else {
                // Overridable
                ScChecker.this.onFail();
                // Listener
                if (ScChecker.this.mListener != null)
                    ScChecker.this.mListener.onFail();
            }

            // Checking changing state
            if (ScChecker.this.mLastState == null ||
                    ScChecker.this.mLastState != result) {
                // Save the new state
                ScChecker.this.mLastState = result;

                // Overridable
                ScChecker.this.onChangeState(result);
                // Call the listener method
                if (ScChecker.this.mListener != null)
                    ScChecker.this.mListener.onChangeState(result);
            }
        }
    }


    /**
     * Public methods
     */

    // Constructor
    @SuppressWarnings("unused")
    public ScChecker() {
    }

    // Type
    @SuppressWarnings("unused")
    public enum Modes {
        FIXED_RATE,
        FIXED_DELAY
    }

    // The check method
    @SuppressWarnings("unused")
    public abstract boolean check();

    // Start to check
    @SuppressWarnings("unused")
    public void start() {
        // If have an active execute force to stop it
        this.stop(true);

        // Create a single thread scheduled executor
        this.mExecutor = Executors.newSingleThreadScheduledExecutor();

        // Start new one by the mode
        switch (this.mMode) {
            // Fixed rate
            case FIXED_RATE:
                this.mExecutor.scheduleAtFixedRate(
                        new Handler(), this.mStartDelay, this.mCheckRate, TimeUnit.MILLISECONDS);
                break;

            // Fixed delay
            case FIXED_DELAY:
                this.mExecutor.scheduleWithFixedDelay(
                        new Handler(), this.mStartDelay, this.mCheckRate, TimeUnit.MILLISECONDS);
                break;
        }
    }

    // Stop to check
    @SuppressWarnings("unused")
    public void stop() {
        // Call the soft stop
        this.stop(false);
    }

    @SuppressWarnings("unused")
    public void stop(boolean force) {
        // Check if already a instance active
        if (this.mExecutor != null && !this.mExecutor.isTerminated()) {
            // Force to stop it
            if (force) this.mExecutor.shutdownNow();
                // Try to stop it
            else this.mExecutor.shutdown();
        }
    }

    // Set the listener
    @SuppressWarnings("unused")
    public void setCheckerListener(CheckerListener listener) {
        this.mListener = listener;
    }


    /**
     * Getters and Setters
     */

    // Get or set the start delay in milliseconds
    // DEFAULT: 100
    @SuppressWarnings("unused")
    public long getStartDelay() {
        return mStartDelay;
    }

    @SuppressWarnings("unused")
    public void setStartDelay(long mStartDelay) {
        this.mStartDelay = mStartDelay;
    }

    // Get or set the check rate in milliseconds
    // DEFAULT: 1000
    @SuppressWarnings("unused")
    public long getCheckRate() {
        return mCheckRate;
    }

    @SuppressWarnings("unused")
    public void setCheckRate(long mCheckRate) {
        this.mCheckRate = mCheckRate;
    }

    // Get or set the start delay in milliseconds
    // DEFAULT: FIXED_DELAY
    @SuppressWarnings("unused")
    public Modes getMode() {
        return mMode;
    }

    @SuppressWarnings("unused")
    public void setMode(Modes mType) {
        this.mMode = mType;
    }


    /**
     * Listener
     */

    @SuppressWarnings("unused")
    public interface CheckerListener {

        void onSuccess();

        void onFail();

        void onChangeState(boolean result);
    }


    /**
     * Overridable
     */

    // Called on success
    @SuppressWarnings("unused")
    public void onSuccess() {
        // NOP
    }

    // Called on fail
    @SuppressWarnings("unused")
    public void onFail() {
        // NOP
    }

    // Called on change status
    @SuppressWarnings("unused")
    public void onChangeState(boolean result) {
        // NOP
    }

}
