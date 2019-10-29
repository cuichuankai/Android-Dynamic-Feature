package com.example.firebase_feature.configuration;

import com.example.core_feature.configuration.BaseConfiguration;
import com.example.firebase_feature.capabilities.FirebaseAnalyticCapability;
import com.example.firebase_feature.capabilities.FirebaseCrashlyticCapability;
import com.google.firebase.FirebaseApp;

public class FirebaseConfiguration extends BaseConfiguration {

    private static FirebaseConfiguration INSTANCE = null;

    private FirebaseConfiguration() {
        FirebaseApp.initializeApp(BaseConfiguration.getContext());

        capabilities.add(new FirebaseAnalyticCapability());
        capabilities.add(new FirebaseCrashlyticCapability());
    }

    public static FirebaseConfiguration getInstance() {
        if (INSTANCE == null) {
            synchronized (FirebaseConfiguration.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FirebaseConfiguration();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public String getServiceName() {
        return FirebaseConfiguration.class.getSimpleName();
    }
}