package com.example.firebase_feature;

import com.example.core_feature.ServiceProvider;
import com.example.core_feature.registry.ServiceRegistryImp;
import com.example.firebase_feature.configuration.FirebaseConfiguration;

public class FirebaseProvider extends ServiceProvider {

    @Override
    public boolean onCreate() {
        boolean result = super.onCreate();

        ServiceRegistryImp.getInstance().register(FirebaseConfiguration.getInstance());

        return result;
    }
}

