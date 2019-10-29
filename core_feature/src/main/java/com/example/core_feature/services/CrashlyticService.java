package com.example.core_feature.services;

import android.os.Bundle;

import com.example.core_feature.capability.ServiceCapability;

public interface CrashlyticService extends ServiceCapability {

    void log(String message);

    void logException(Throwable e);

    void setUserInfo(Bundle userInfo);

    void setParameters(Bundle params);

    void crash();
}
