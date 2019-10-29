package com.example.facebook_feature;

import com.example.core_feature.ServiceProvider;
import com.example.core_feature.registry.ServiceRegistryImp;
import com.example.facebook_feature.configuration.FacebookConfiguration;

public class FacebookProvider extends ServiceProvider {

    @Override
    public boolean onCreate() {
        boolean result = super.onCreate();

        ServiceRegistryImp.getInstance().register(FacebookConfiguration.getInstance());

        return result;
    }
}
