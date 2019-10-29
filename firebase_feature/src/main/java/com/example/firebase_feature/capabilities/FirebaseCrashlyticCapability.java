package com.example.firebase_feature.capabilities;

import android.os.Bundle;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.ndk.CrashlyticsNdk;
import com.example.core_feature.Params;
import com.example.core_feature.configuration.BaseConfiguration;
import com.example.core_feature.services.CrashlyticService;

import java.util.Set;

import io.fabric.sdk.android.Fabric;

public class FirebaseCrashlyticCapability implements CrashlyticService {
    private static final String TAG = FirebaseCrashlyticCapability.class.getSimpleName();

    @Override
    public void logException(Throwable e) {
        Crashlytics.logException(e);
    }

    @Override
    public void log(String message) {
        Crashlytics.log(Log.DEBUG, TAG, message);
    }

    @Override
    public void setUserInfo(Bundle userInfo) {
        if (userInfo != null) {
            // set user properties
            try {
                Set<String> keys = userInfo.keySet();
                for (String key : keys) {
                    if (key.equalsIgnoreCase(Params.USER_INFO_ID)) {
                        Crashlytics.setUserIdentifier(userInfo.getString(Params.USER_INFO_ID));
                    } else if (key.equalsIgnoreCase(Params.USER_INFO_EMAIL)) {
                        Crashlytics.setUserEmail(userInfo.getString(Params.USER_INFO_EMAIL));
                    } else if (key.equalsIgnoreCase(Params.USER_INFO_PHONE)) {
                        Crashlytics.setUserName(userInfo.getString(Params.USER_INFO_PHONE));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setParameters(Bundle params) {
//        Crashlytics.setInt(key, value);
//        Crashlytics.setString(key, value);
//        Crashlytics.setBool(key, value);
    }

    @Override
    public void crash() {
        Crashlytics.getInstance().crash();
    }

    @Override
    public void init() {
        Fabric fabric = new Fabric.Builder(BaseConfiguration.getContext())
                .kits(new Crashlytics(), new CrashlyticsNdk())
                .debuggable(true)
                .build();
        Fabric.with(fabric);
    }
}
