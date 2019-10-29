package com.example.core_feature.configuration;

import android.content.Context;

import com.example.core_feature.capability.ServiceCapability;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseConfiguration implements ServiceConfiguration {

    private static WeakReference<Context> weakContext;
    protected List<ServiceCapability> capabilities;

    protected BaseConfiguration() {
        capabilities = new ArrayList<>();
    }

    public static void init(Context context) {
        weakContext = new WeakReference<>(context);
    }

    public static Context getContext() {
        return weakContext.get();
    }

    @Override
    public String getServiceName() {
        return null;
    }

    @Override
    public List<ServiceCapability> getCapabilities() {
        return capabilities;
    }

    @Override
    public <T extends ServiceCapability> List<T> getCapabilities(Class<T> clazz) {
        List<T> localCapabilities = new ArrayList<>();
        for (ServiceCapability capability : capabilities) {
            // check all possible interfaces and classes
            if (capability.getClass().getName().equals(clazz.getName()) ||
                    (capability.getClass().getInterfaces().length > 0
                            && capability.getClass().getInterfaces()[0].getName().equals(clazz.getName()))) {
                localCapabilities.add(clazz.cast(capability));
            }
        }
        return localCapabilities;
    }

    @Override
    public <T extends ServiceCapability> T getCapability(Class<T> clazz) {
        for (ServiceCapability capability : capabilities) {
            // check all possible interfaces and classes
            if (capability.getClass().getName().equals(clazz.getName()) ||
                    (capability.getClass().getInterfaces().length > 0
                            && capability.getClass().getInterfaces()[0].getName().equals(clazz.getName())) ||
                    (capability.getClass().getSuperclass() != null
                            && capability.getClass().getSuperclass().getInterfaces().length > 0
                            && capability.getClass().getSuperclass().getInterfaces()[0].getName().equals(clazz.getName()))) {
                return clazz.cast(capability);
            }
        }
        return null;
    }
}

