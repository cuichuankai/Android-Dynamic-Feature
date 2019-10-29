package com.example.dynamicfeature;

import android.app.Application;
import android.os.Handler;

public class App extends Application {
    public static volatile Handler applicationHandler;

    @Override
    public void onCreate() {
        super.onCreate();

        // save applicationHandler
        applicationHandler = new Handler(getApplicationContext().getMainLooper());

        LogHelper.init();
        AnalyticsHelper.init();
    }

    public static void runOnUIThread(Runnable runnable) {
        runOnUIThread(runnable, 0);
    }

    public static void runOnUIThread(Runnable runnable, long delay) {
        if (delay == 0) {
            applicationHandler.post(runnable);
        } else {
            applicationHandler.postDelayed(runnable, delay);
        }
    }

    public static void cancelRunOnUIThread(Runnable runnable) {
        applicationHandler.removeCallbacks(runnable);
    }
}
