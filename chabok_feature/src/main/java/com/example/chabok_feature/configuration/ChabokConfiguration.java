package com.example.chabok_feature.configuration;

import android.content.Intent;

import com.adpdigital.push.AdpPushClient;
import com.example.chabok_feature.BuildConfig;
import com.example.chabok_feature.capabilities.ChabokAnalyticCapability;
import com.example.core_feature.configuration.BaseConfiguration;

public class ChabokConfiguration extends BaseConfiguration {
    private static ChabokConfiguration INSTANCE = null;
    private static AdpPushClient adpPush;

    private ChabokConfiguration() {
        capabilities.add(new ChabokAnalyticCapability());

        init();
    }

    public static ChabokConfiguration getInstance() {
        if (INSTANCE == null) {
            synchronized (ChabokConfiguration.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ChabokConfiguration();
                }
            }
        }
        return INSTANCE;
    }

    public static AdpPushClient ChabokClient() {
        return adpPush;
    }

    private void init() {
        try {
            String packageName = BaseConfiguration.getContext().getPackageName();
            Intent launchIntent = BaseConfiguration.getContext().getPackageManager().getLaunchIntentForPackage(packageName);

            adpPush = AdpPushClient.init(
                    BaseConfiguration.getContext(),
                    Class.forName(launchIntent.getComponent().getClassName()),
                    "XXX/1111111111",
                    "XXX",
                    "XXX",
                    "XXX"
            );
            adpPush.setDevelopment(BuildConfig.DEBUG);
        } catch (ClassNotFoundException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getServiceName() {
        return ChabokConfiguration.class.getSimpleName();
    }
}

