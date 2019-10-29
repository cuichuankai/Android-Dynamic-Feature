package com.example.core_feature.configuration;

import com.example.core_feature.capability.ServiceCapability;

import java.util.List;

public interface ServiceConfiguration {

    String getServiceName();

    List<ServiceCapability> getCapabilities();

    <T extends ServiceCapability> List<T> getCapabilities(Class<T> clazz);

    <T extends ServiceCapability> T getCapability(Class<T> clazz);
}
