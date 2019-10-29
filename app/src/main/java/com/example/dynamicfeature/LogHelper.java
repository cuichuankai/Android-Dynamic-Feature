package com.example.dynamicfeature;

import android.os.Bundle;

import com.example.core_feature.configuration.ServiceConfiguration;
import com.example.core_feature.registry.ServiceRegistryImp;
import com.example.core_feature.services.CrashlyticService;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class LogHelper {
    private static final String TAG = LogHelper.class.getSimpleName();

    private static List<WeakReference<CrashlyticService>> crashlyticServices;

    private LogHelper() {
        // nothing!
    }

    public static void init() {
        crashlyticServices = new ArrayList<>();
        try {
            List<ServiceConfiguration> configs = ServiceRegistryImp.getInstance().getServices();
            if (configs != null) {
                for (ServiceConfiguration config : configs) {
                    crashlyticServices.add(new WeakReference<>(config.getCapability(CrashlyticService.class)));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogHelper.logException(e);
        }
    }

    public static void setUserInfo(Bundle userInfo) {
        if (crashlyticServices != null) {
            for (WeakReference<CrashlyticService> service : crashlyticServices) {
                if (service != null && service.get() != null) {
                    service.get().setUserInfo(userInfo);
                }
            }
        }
    }

    public static void log(String message) {
        if (crashlyticServices != null) {
            for (WeakReference<CrashlyticService> service : crashlyticServices) {
                if (service != null && service.get() != null) {
                    service.get().log(message);
                }
            }
        }
    }

    public static void logException(Throwable e) {
        if (crashlyticServices != null) {
            for (WeakReference<CrashlyticService> service : crashlyticServices) {
                if (service != null && service.get() != null) {
                    service.get().logException(e);
                }
            }
        }
    }

    public static void crash() {
        if (crashlyticServices != null) {
            for (WeakReference<CrashlyticService> service : crashlyticServices) {
                if (service != null && service.get() != null) {
                    service.get().crash();
                }
            }
        }
    }
}
