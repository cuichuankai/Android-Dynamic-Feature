package com.example.core_feature.registry;

import com.example.core_feature.configuration.ServiceConfiguration;

import java.util.List;

public interface ServiceRegistry {

    void register(ServiceConfiguration service);

    List<ServiceConfiguration> getServices();

    <T extends ServiceConfiguration> T getService(Class<T> clazz);
}
