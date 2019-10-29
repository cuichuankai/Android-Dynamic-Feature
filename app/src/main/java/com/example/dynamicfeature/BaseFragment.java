package com.example.dynamicfeature;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Date;

public abstract class BaseFragment extends Fragment {
    private static final String TAG = BaseFragment.class.getSimpleName();

    private Activity context;
    private boolean afterResume;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        afterResume = false;
    }

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        context = activity;
    }

    @Override
    public void onResume() {
        super.onResume();

        final CharSequence title = getAnalyticPageName();
        App.runOnUIThread(() -> {
            afterResume = true;
            if (getUserVisibleHint() && isAdded()) {
                // send screen view data to analytic
                AnalyticsHelper.onFragmentStart(String.valueOf(title));
                if (title != null && title.length() > 0) {
                    AnalyticsHelper.startScreenViewRecord(title.toString(), new Date().getTime());
                    AnalyticsHelper.setCurrentScreen(context, title.toString(), TAG);
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();

        if (getUserVisibleHint()) {
            final CharSequence title = getAnalyticPageName();
            if (title != null && title.length() > 0) {
                AnalyticsHelper.stopScreenViewRecord(title.toString(), new Date().getTime());
            }
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser && afterResume) {
            // send screen view data to analytic
            AnalyticsHelper.onFragmentStart(String.valueOf(getAnalyticPageName()));
            if (getAnalyticPageName() != null) {
                AnalyticsHelper.startScreenViewRecord(getAnalyticPageName().toString(), new Date().getTime());
                AnalyticsHelper.setCurrentScreen(context, getAnalyticPageName().toString(), TAG);
            }
        } else if (!isVisibleToUser && afterResume) {
            if (getAnalyticPageName() != null) {
                AnalyticsHelper.stopScreenViewRecord(getAnalyticPageName().toString(), new Date().getTime());
            }
        }
    }

    public abstract CharSequence getAnalyticPageName();
}
