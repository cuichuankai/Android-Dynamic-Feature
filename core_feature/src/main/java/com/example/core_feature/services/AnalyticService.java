package com.example.core_feature.services;

import android.app.Activity;
import android.os.Bundle;

import com.example.core_feature.capability.ServiceCapability;

public interface AnalyticService extends ServiceCapability {

    void setUserInfo(Bundle userInfo);

    void setCurrentScreen(Activity activity, String screenName, String className);

    void onFragmentStart(String fragmentName);

    void sendEvent(String category, String action, String label);

    void sendEvent(String eventName, Bundle bundle);

    void startScreenViewRecord(String nextScreen, long startTime);

    void stopScreenViewRecord(String screen, long finishTime);
}
