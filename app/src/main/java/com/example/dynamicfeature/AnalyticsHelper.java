package com.example.dynamicfeature;

import android.app.Activity;
import android.os.Bundle;

import com.example.core_feature.Params;
import com.example.core_feature.configuration.ServiceConfiguration;
import com.example.core_feature.registry.ServiceRegistryImp;
import com.example.core_feature.services.AnalyticService;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class AnalyticsHelper {
    private static final String TAG = AnalyticsHelper.class.getSimpleName();

    private static List<WeakReference<AnalyticService>> analyticServices;

    private AnalyticsHelper() {
        // nothing!
    }

    public static void init() {
        analyticServices = new ArrayList<>();
        try {
            List<ServiceConfiguration> configs = ServiceRegistryImp.getInstance().getServices();
            if (configs != null) {
                for (ServiceConfiguration config : configs) {
                    analyticServices.add(new WeakReference<>(config.getCapability(AnalyticService.class)));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogHelper.logException(e);
        }
    }

    public static void setParams(String trackerId,
                                 double sampleRate,
                                 String ecommerceTrackerId,
                                 double ecommerceSampleRate,
                                 Account account) {
        Bundle userInfo = new Bundle();
        userInfo.putString(Params.USER_INFO_ID, String.valueOf(account.id));
        userInfo.putString(Params.USER_INFO_EMAIL, account.email);
        userInfo.putString(Params.USER_INFO_PHONE, account.phone);
        userInfo.putBoolean(Params.ANALYTIC_SEND_USER_ID, account.sendUserId);

        userInfo.putString(Params.ANALYTIC_TRACKING_ID, trackerId);
        userInfo.putDouble(Params.ANALYTIC_SAMPLE_RATE, sampleRate);
        userInfo.putString(Params.ANALYTIC_ECOMMERCE_TRACKING_ID, ecommerceTrackerId);
        userInfo.putDouble(Params.ANALYTIC_ECOMMERCE_SAMPLE_RATE, ecommerceSampleRate);
        userInfo.putBoolean(Params.ANALYTIC_SEND_USER_ID, account.sendUserId);

        LogHelper.setUserInfo(userInfo);

        if (analyticServices != null) {
            for (WeakReference<AnalyticService> service : analyticServices) {
                if (service != null && service.get() != null) {
                    service.get().setUserInfo(userInfo);
                }
            }
        }
    }

    public static void setUserInfo(Account account) {
        Bundle userInfo = new Bundle();
        userInfo.putString(Params.USER_INFO_ID, String.valueOf(account.id));
        userInfo.putString(Params.USER_INFO_EMAIL, account.email);
        userInfo.putString(Params.USER_INFO_PHONE, account.phone);
        userInfo.putBoolean(Params.ANALYTIC_SEND_USER_ID, account.sendUserId);

        LogHelper.setUserInfo(userInfo);

        if (analyticServices != null) {
            for (WeakReference<AnalyticService> service : analyticServices) {
                if (service != null && service.get() != null) {
                    service.get().setUserInfo(userInfo);
                }
            }
        }
    }

    public static void sendEvent(String category, String action, String label) {
        if (analyticServices != null) {
            for (WeakReference<AnalyticService> service : analyticServices) {
                if (service != null && service.get() != null) {
                    service.get().sendEvent(category, action, label);
                }
            }
        }
    }

    public static void sendEvent(String eventName, Bundle bundle) {
        if (analyticServices != null) {
            for (WeakReference<AnalyticService> service : analyticServices) {
                if (service != null && service.get() != null) {
                    service.get().sendEvent(eventName, bundle);
                }
            }
        }
    }

    public static void onFragmentStart(String fragmentName) {
        if (analyticServices != null) {
            for (WeakReference<AnalyticService> service : analyticServices) {
                if (service != null && service.get() != null) {
                    service.get().onFragmentStart(fragmentName);
                }
            }
        }
    }

    public static void setCurrentScreen(Activity activity, String screenName, String className) {
        if (analyticServices != null) {
            for (WeakReference<AnalyticService> service : analyticServices) {
                if (service != null && service.get() != null) {
                    service.get().setCurrentScreen(activity, screenName, className);
                }
            }
        }
    }

    public static void startScreenViewRecord(String nextScreen, long startTime) {
        if (analyticServices != null) {
            for (WeakReference<AnalyticService> service : analyticServices) {
                if (service != null && service.get() != null) {
                    service.get().startScreenViewRecord(nextScreen, startTime);
                }
            }
        }
    }

    public static void stopScreenViewRecord(String screen, long finishTime) {
        if (analyticServices != null) {
            for (WeakReference<AnalyticService> service : analyticServices) {
                if (service != null && service.get() != null) {
                    service.get().stopScreenViewRecord(screen, finishTime);
                }
            }
        }
    }

    public static class AnalyticParamBuilder {
        private Bundle bundle;

        public AnalyticParamBuilder() {
            this.bundle = new Bundle();
        }

        public AnalyticParamBuilder addStringParam(String key, String value) {
            this.bundle.putString(key, value);
            return this;
        }

        public AnalyticParamBuilder addIntParam(String key, int value) {
            this.bundle.putInt(key, value);
            return this;
        }

        public AnalyticParamBuilder addBoolParam(String key, boolean value) {
            this.bundle.putBoolean(key, value);
            return this;
        }

        public Bundle build() {
            return this.bundle;
        }
    }
}
