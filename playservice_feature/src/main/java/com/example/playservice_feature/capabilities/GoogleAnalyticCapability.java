package com.example.playservice_feature.capabilities;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.example.core_feature.Params;
import com.example.core_feature.configuration.BaseConfiguration;
import com.example.core_feature.services.BaseAnalyticService;
import com.example.playservice_feature.BuildConfig;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.util.Map;

public class GoogleAnalyticCapability extends BaseAnalyticService {
    private static final String TAG = GoogleAnalyticCapability.class.getSimpleName();
    private final static double DEFAULT_SAMPLE_RATE = 100.0d;

    private Tracker tracker;

    private String trackerId = null;
    private String ecommerceTrackerId = null;

    private double sampleRate = DEFAULT_SAMPLE_RATE;
    private double ecommerceSampleRate = DEFAULT_SAMPLE_RATE;

    private String userId;
    private boolean sendUserId;

    @Override
    public void setUserInfo(Bundle userInfo) {
        if (userInfo != null) {
            this.userId = userInfo.getString(Params.USER_INFO_ID, this.userId);
            this.sendUserId = userInfo.getBoolean(Params.ANALYTIC_SEND_USER_ID, false); // ask user for more privacy!
            this.trackerId = userInfo.getString(Params.ANALYTIC_TRACKING_ID, this.trackerId);
            this.sampleRate = userInfo.getDouble(Params.ANALYTIC_SAMPLE_RATE, this.sampleRate);
            this.ecommerceTrackerId = userInfo.getString(Params.ANALYTIC_ECOMMERCE_TRACKING_ID, this.ecommerceTrackerId);
            this.ecommerceSampleRate = userInfo.getDouble(Params.ANALYTIC_ECOMMERCE_SAMPLE_RATE, this.ecommerceSampleRate);
        }
    }

    @Override
    public void setCurrentScreen(Activity activity, String screenName, String className) {
        // nothing!
    }

    @Override
    public void onFragmentStart(String fragmentName) {
        if (fragmentName == null || fragmentName.isEmpty()) {
            return;
        }

        if (isGooglePlayServicesSupport()) {
            Tracker tracker = getSynchronizedTracker();
            tracker.setScreenName(fragmentName);
            tracker.send(new HitBuilders.ScreenViewBuilder().build());
        }
    }

    @Override
    public void sendEvent(String category, String action, String label) {
        if (isGooglePlayServicesSupport()) {
            Tracker tracker = getSynchronizedTracker();

            if (category == null) {
                category = "";
            }
            if (action == null) {
                action = "";
            }
            if (label == null) {
                label = "";
            }

            Map<String, String> hitBuilders = new HitBuilders.EventBuilder()
                    .setCategory(category)
                    .setAction(action)
                    .setLabel(label).build();

            tracker.send(hitBuilders);
        }
    }

    @Override
    public void sendEvent(String eventName, Bundle bundle) {
        // nothing!
    }

    @Override
    public void init() {
        // nothing!
    }

    private synchronized Tracker getSynchronizedTracker() {
        if (tracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(BaseConfiguration.getContext());
            analytics.setDryRun(BuildConfig.DEBUG);

            tracker = analytics.newTracker(trackerId);
            tracker.enableAdvertisingIdCollection(true);
            tracker.enableAutoActivityTracking(false);
            tracker.enableExceptionReporting(true);

            int appVersion = 0;
            try {
                appVersion = BaseConfiguration.getContext().getPackageManager()
                        .getPackageInfo(BaseConfiguration.getContext().getPackageName(),
                                0).versionCode;
            } catch (PackageManager.NameNotFoundException e) {
                // nothing!
            }
            tracker.setAppVersion(appVersion + "");
            tracker.setSampleRate(sampleRate);
        }

        if (sendUserId && userId != null && !userId.isEmpty()) {
            tracker.set("&uid", userId);
        }

        return tracker;
    }

    private boolean isGooglePlayServicesSupport() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        return apiAvailability.isGooglePlayServicesAvailable(BaseConfiguration.getContext()) == ConnectionResult.SUCCESS;
    }
}
