package com.example.dynamicfeature;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SimpleFragment extends BaseFragment {
    private String screenName = "Unknown";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        deserializeBundle();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_base, container, false);
        TextView tv = view.findViewById(R.id.textView);
        tv.setText(screenName);
        return view;
    }

    @Override
    public CharSequence getAnalyticPageName() {
        return screenName;
    }

    /* bundle methods */
    public static Bundle serializeBundle(String content) {
        Bundle bundle = new Bundle();
        bundle.putString("screenName", content);
        return bundle;
    }

    private void deserializeBundle() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            screenName = bundle.getString("screenName");
        }
    }
}
