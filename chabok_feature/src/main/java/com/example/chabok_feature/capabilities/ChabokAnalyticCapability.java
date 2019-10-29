package com.example.chabok_feature.capabilities;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.example.chabok_feature.configuration.ChabokConfiguration;
import com.example.core_feature.Params;
import com.example.core_feature.configuration.BaseConfiguration;
import com.example.core_feature.services.BaseAnalyticService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Set;

public class ChabokAnalyticCapability extends BaseAnalyticService {
    private final static String TAG = ChabokAnalyticCapability.class.getSimpleName();
    private int appVersion = 0;

    @Override
    public void setUserInfo(Bundle userInfo) {
        if (userInfo != null) {
            // set user properties
            try {
                HashMap<String, Object> attrs = new HashMap<>();
                Set<String> keys = userInfo.keySet();
                for (String key : keys) {
                    if (key.equalsIgnoreCase(Params.USER_INFO_EMAIL)) {
                        attrs.put(key, userInfo.get(key));
                    } else if (key.equalsIgnoreCase(Params.USER_INFO_PHONE)) {
                        attrs.put(key, userInfo.get(key));
                    }
                }
                ChabokConfiguration.ChabokClient().setUserAttributes(attrs);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setCurrentScreen(Activity activity, String screenName, String className) {
        // nothing!
    }

    @Override
    public void onFragmentStart(String fragmentName) {
        if (fragmentName == null || fragmentName.isEmpty()) {
            return;
        }

        JSONObject data = new JSONObject();
        try {
            data.put("screen_name", fragmentName);
            data.put("app_version", appVersion);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ChabokConfiguration.ChabokClient().track("screen_view", data);
    }

    @Override
    public void sendEvent(String category, String action, String label) {
        // nothing!
    }

    @Override
    public void sendEvent(String eventName, Bundle bundle) {
        JSONObject jsonObject = new JSONObject();
        Set<String> keys = bundle.keySet();
        for (String key : keys) {
            try {
                jsonObject.put(key, bundle.get(key));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        ChabokConfiguration.ChabokClient().track(eventName, jsonObject);
    }

    @Override
    public void init() {
        try {
            appVersion = BaseConfiguration.getContext()
                    .getPackageManager()
                    .getPackageInfo(BaseConfiguration.getContext().getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void login(String id) {
        ChabokConfiguration.ChabokClient().register(id);
    }

    public void logout() {
        ChabokConfiguration.ChabokClient().unregister();
        ChabokConfiguration.ChabokClient().registerAsGuest();
    }
}

