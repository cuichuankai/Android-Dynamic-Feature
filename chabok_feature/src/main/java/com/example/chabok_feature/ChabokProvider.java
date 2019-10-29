package com.example.chabok_feature;

import com.example.chabok_feature.configuration.ChabokConfiguration;
import com.example.core_feature.ServiceProvider;
import com.example.core_feature.registry.ServiceRegistryImp;

public class ChabokProvider extends ServiceProvider {

    @Override
    public boolean onCreate() {
        boolean result = super.onCreate();

        ServiceRegistryImp.getInstance().register(ChabokConfiguration.getInstance());

        return result;
    }
}
