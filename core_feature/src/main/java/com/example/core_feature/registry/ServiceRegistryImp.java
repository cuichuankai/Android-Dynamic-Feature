package com.example.core_feature.registry;

import com.example.core_feature.configuration.ServiceConfiguration;

import java.util.ArrayList;
import java.util.List;

public class ServiceRegistryImp implements ServiceRegistry {

    private static ServiceRegistryImp INSTANCE = null;
    private List<ServiceConfiguration> services;

    private ServiceRegistryImp() {
        services = new ArrayList<>();
    }

    public static ServiceRegistryImp getInstance() {
        if (INSTANCE == null) {
            synchronized (ServiceRegistryImp.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ServiceRegistryImp();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void register(ServiceConfiguration service) {
        services.add(service);
    }

    @Override
    public List<ServiceConfiguration> getServices() {
        return services;
    }

    @Override
    public <T extends ServiceConfiguration> T getService(Class<T> clazz) {
        for (ServiceConfiguration service : services) {
            if (service.getClass().getName().equals(clazz.getName())) {
                return clazz.cast(service);
            }
        }
        return null;
    }
}
