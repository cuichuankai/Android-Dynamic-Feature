package com.example.facebook_feature.configuration;

import com.example.core_feature.configuration.BaseConfiguration;
import com.example.facebook_feature.capabilities.FacebookAnalyticCapability;

public class FacebookConfiguration extends BaseConfiguration {
    private static FacebookConfiguration INSTANCE = null;

    private FacebookConfiguration() {
        capabilities.add(new FacebookAnalyticCapability());
    }

    public static FacebookConfiguration getInstance() {
        if (INSTANCE == null) {
            synchronized (FacebookConfiguration.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FacebookConfiguration();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public String getServiceName() {
        return FacebookConfiguration.class.getSimpleName();
    }
}
