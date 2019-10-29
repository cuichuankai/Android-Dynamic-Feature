package com.example.playservice_feature;

import com.example.core_feature.ServiceProvider;
import com.example.core_feature.registry.ServiceRegistryImp;
import com.example.playservice_feature.configuration.PlayServiceConfiguration;

public class PlayServiceProvider extends ServiceProvider {

    @Override
    public boolean onCreate() {
        boolean result = super.onCreate();

        ServiceRegistryImp.getInstance().register(PlayServiceConfiguration.getInstance());

        return result;
    }
}
