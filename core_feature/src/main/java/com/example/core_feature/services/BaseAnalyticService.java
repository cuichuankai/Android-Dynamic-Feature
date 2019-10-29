package com.example.core_feature.services;

import android.os.Bundle;
import android.util.Log;

import com.example.core_feature.capability.BaseCapability;

public abstract class BaseAnalyticService extends BaseCapability implements AnalyticService {
    private String mScreenTitle;
    private long mStartTime;
    private long mFinishTime;

    @Override
    public void startScreenViewRecord(String nextScreen, long startTime) {
        if (mScreenTitle != null && !mScreenTitle.isEmpty()
                && !mScreenTitle.equalsIgnoreCase(nextScreen) && mFinishTime - mStartTime > 0) {
            Bundle bundle = new Bundle();
            bundle.putString("next_screen_title", nextScreen);
            bundle.putString("screen_title", mScreenTitle);
            bundle.putString("duration", String.valueOf((int) ((mFinishTime - mStartTime) / 1000)));
            sendEvent("screen_change", bundle);

            Log.d("screen_change",
                    "screen_title=" + mScreenTitle + ", next_screen_title=" + nextScreen + ", duration=" + (int) ((mFinishTime - mStartTime) / 1000));
        }
        mStartTime = startTime;
    }

    @Override
    public void stopScreenViewRecord(String screen, long finishTime) {
        mScreenTitle = screen;
        mFinishTime = finishTime;
    }
}
