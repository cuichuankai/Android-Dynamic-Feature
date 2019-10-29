package com.example.facebook_feature.capabilities;

import android.app.Activity;
import android.os.Bundle;

import com.example.core_feature.configuration.BaseConfiguration;
import com.example.core_feature.services.BaseAnalyticService;
import com.example.facebook_feature.BuildConfig;
import com.facebook.FacebookSdk;
import com.facebook.LoggingBehavior;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.appevents.AppEventsLogger;

import java.math.BigDecimal;
import java.util.Currency;

public class FacebookAnalyticCapability extends BaseAnalyticService {
    private final static String TAG = FacebookAnalyticCapability.class.getSimpleName();
    private AppEventsLogger logger;

    @Override
    public void setUserInfo(Bundle userInfo) {
        // nothing!
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

        Bundle parameters = new Bundle();
        parameters.putString("param_screen", fragmentName);
        logger.logEvent("screen", parameters);
    }

    @Override
    public void sendEvent(String category, String action, String label) {
        if (category == null || category.isEmpty()) {
            return;
        }
        if (action == null || action.isEmpty()) {
            return;
        }
        if (label == null || label.isEmpty()) {
            return;
        }

        Bundle parameters = new Bundle();
        parameters.putString("param_category", category);
        parameters.putString("param_action", action);
        parameters.putString("param_label", label);
        sendEvent("event", parameters);
    }

    @Override
    public void sendEvent(String eventName, Bundle bundle) {
        logger.logEvent(eventName, bundle);
    }

    @Override
    public void init() {
        FacebookSdk.setAutoInitEnabled(true);
        FacebookSdk.sdkInitialize(BaseConfiguration.getContext());
        if (BuildConfig.DEBUG) {
            FacebookSdk.setIsDebugEnabled(true);
            FacebookSdk.addLoggingBehavior(LoggingBehavior.APP_EVENTS);
        }
        FacebookSdk.setAutoLogAppEventsEnabled(true);
        FacebookSdk.setAdvertiserIDCollectionEnabled(true);
        logger = AppEventsLogger.newLogger(BaseConfiguration.getContext());
    }

    public void logViewedContentEvent(String contentType, String contentData, String contentId, double price) {
        Bundle params = new Bundle();
        params.putString(AppEventsConstants.EVENT_PARAM_CONTENT_TYPE, contentType);
        params.putString(AppEventsConstants.EVENT_PARAM_CONTENT, contentData);
        params.putString(AppEventsConstants.EVENT_PARAM_CONTENT_ID, contentId);
        params.putString(AppEventsConstants.EVENT_PARAM_CURRENCY, "IRR");
        logger.logEvent(AppEventsConstants.EVENT_NAME_VIEWED_CONTENT, price, params);
    }

    public void logSearchedEvent(String contentType, String contentData, String contentId, String searchString, boolean success) {
        Bundle params = new Bundle();
        params.putString(AppEventsConstants.EVENT_PARAM_CONTENT_TYPE, contentType);
        params.putString(AppEventsConstants.EVENT_PARAM_CONTENT, contentData);
        params.putString(AppEventsConstants.EVENT_PARAM_CONTENT_ID, contentId);
        params.putString(AppEventsConstants.EVENT_PARAM_SEARCH_STRING, searchString);
        params.putInt(AppEventsConstants.EVENT_PARAM_SUCCESS, success ? 1 : 0);
        logger.logEvent(AppEventsConstants.EVENT_NAME_SEARCHED, params);
    }

    public void logRatedEvent(String contentType, String contentData, String contentId, int maxRatingValue, double ratingGiven) {
        Bundle params = new Bundle();
        params.putString(AppEventsConstants.EVENT_PARAM_CONTENT_TYPE, contentType);
        params.putString(AppEventsConstants.EVENT_PARAM_CONTENT, contentData);
        params.putString(AppEventsConstants.EVENT_PARAM_CONTENT_ID, contentId);
        params.putInt(AppEventsConstants.EVENT_PARAM_MAX_RATING_VALUE, maxRatingValue);
        logger.logEvent(AppEventsConstants.EVENT_NAME_RATED, ratingGiven, params);
    }

    public void logCompletedRegistrationEvent(String registrationMethod) {
        Bundle params = new Bundle();
        params.putString(AppEventsConstants.EVENT_PARAM_REGISTRATION_METHOD, registrationMethod);
        logger.logEvent(AppEventsConstants.EVENT_NAME_COMPLETED_REGISTRATION, params);
    }

    public void logInitiatedCheckoutEvent(String contentData, String contentId, String contentType, int numItems, boolean paymentInfoAvailable, double totalPrice) {
        Bundle params = new Bundle();
        params.putString(AppEventsConstants.EVENT_PARAM_CONTENT, contentData);
        params.putString(AppEventsConstants.EVENT_PARAM_CONTENT_ID, contentId);
        params.putString(AppEventsConstants.EVENT_PARAM_CONTENT_TYPE, contentType);
        params.putInt(AppEventsConstants.EVENT_PARAM_NUM_ITEMS, numItems);
        params.putInt(AppEventsConstants.EVENT_PARAM_PAYMENT_INFO_AVAILABLE, paymentInfoAvailable ? 1 : 0);
        params.putString(AppEventsConstants.EVENT_PARAM_CURRENCY, "IRR");
        logger.logEvent(AppEventsConstants.EVENT_NAME_INITIATED_CHECKOUT, totalPrice, params);
    }

    public void logPurchased(BigDecimal purchaseAmount, Currency currency, Bundle parameters) {
        /**
         * logger is an instance of AppEventsLogger and has been
         * created using AppEventsLogger.newLogger() call.
         * purchaseAmount is BigDecimal, e.g. BigDecimal.valueOf(4.32).
         * currency is Currency, e.g. Currency.getInstance("USD"),
         *     where the string is from http://en.wikipedia.org/wiki/ISO_4217.
         * parameters is Bundle.
         */
        logger.logPurchase(purchaseAmount, currency, parameters);
    }
}

