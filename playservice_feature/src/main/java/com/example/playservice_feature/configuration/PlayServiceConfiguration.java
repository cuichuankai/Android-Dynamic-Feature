package com.example.playservice_feature.configuration;

import com.example.core_feature.configuration.BaseConfiguration;
import com.example.playservice_feature.capabilities.GoogleAnalyticCapability;

public class PlayServiceConfiguration extends BaseConfiguration {

    private static PlayServiceConfiguration INSTANCE = null;

    private PlayServiceConfiguration() {
        capabilities.add(new GoogleAnalyticCapability());
    }

    public static PlayServiceConfiguration getInstance() {
        if (INSTANCE == null) {
            synchronized (PlayServiceConfiguration.class) {
                if (INSTANCE == null) {
                    INSTANCE = new PlayServiceConfiguration();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public String getServiceName() {
        return PlayServiceConfiguration.class.getSimpleName();
    }
}
