package com.example.firebase_feature.capabilities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.example.core_feature.Params;
import com.example.core_feature.configuration.BaseConfiguration;
import com.example.core_feature.services.BaseAnalyticService;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.Set;

public class FirebaseAnalyticCapability extends BaseAnalyticService {
    private final static String TAG = FirebaseAnalyticCapability.class.getSimpleName();
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    public void setUserInfo(Bundle userInfo) {
        if (userInfo != null) {
            // set user properties
            try {
                Set<String> keys = userInfo.keySet();
                for (String key : keys) {
                    if (key.equalsIgnoreCase(Params.USER_INFO_ID)) {
                        mFirebaseAnalytics.setUserId(userInfo.getString(Params.USER_INFO_ID));
                    } else if (key.equalsIgnoreCase(Params.USER_INFO_EMAIL)) {
                        mFirebaseAnalytics.setUserProperty("Email", (String) userInfo.get(key));
                    } else if (key.equalsIgnoreCase(Params.USER_INFO_PHONE)) {
                        mFirebaseAnalytics.setUserProperty("Phone", (String) userInfo.get(key));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setCurrentScreen(Activity activity, String screenName, String className) {
        if (screenName == null || screenName.isEmpty()) {
            return;
        }

        mFirebaseAnalytics.setCurrentScreen(activity, screenName, className);
    }

    @Override
    public void onFragmentStart(String fragmentName) {
        // nothing!
    }

    @Override
    public void sendEvent(String category, String action, String label) {
        // nothing!
    }

    @Override
    public void sendEvent(String eventName, Bundle bundle) {
        if (eventName == null || eventName.isEmpty()) {
            return;
        }

        Log.d(TAG, "sendEvent(" + eventName + ", " + bundle.toString() + ")");
        mFirebaseAnalytics.logEvent(eventName, bundle);
    }

    @Override
    public void init() {
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(BaseConfiguration.getContext());
    }
}
