package com.example.dynamicfeature;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private int seenCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seenCount = 0;

        List<Fragment> fragments = new ArrayList<>();
        List<String> titles = new ArrayList<>();

        SimpleFragment fragmentOne = new SimpleFragment();
        fragmentOne.setArguments(SimpleFragment.serializeBundle("Screen Number One"));
        fragments.add(fragmentOne);
        titles.add("Screen Number One");

        SimpleFragment fragmentTwo = new SimpleFragment();
        fragmentTwo.setArguments(SimpleFragment.serializeBundle("Screen Number Two"));
        fragments.add(fragmentTwo);
        titles.add("Screen Number Two");

        SimpleFragment fragmentThree = new SimpleFragment();
        fragmentThree.setArguments(SimpleFragment.serializeBundle("Screen Number Three"));
        fragments.add(fragmentThree);
        titles.add("Screen Number Three");

        SwipeAdapter adapter = new SwipeAdapter(getSupportFragmentManager(), fragments, titles);
        ViewPager viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(3);
    }

    @Override
    protected void onStart() {
        super.onStart();

        AnalyticsHelper.sendEvent("test_event",
                new AnalyticsHelper.AnalyticParamBuilder()
                        .addBoolParam("alive", true)
                        .build());
    }

    @Override
    protected void onResume() {
        super.onResume();

        AnalyticsHelper.sendEvent("test_event",
                new AnalyticsHelper.AnalyticParamBuilder()
                        .addIntParam("seen_count", ++seenCount)
                        .build());
    }

    @Override
    protected void onStop() {
        super.onStop();

        AnalyticsHelper.sendEvent("test_event",
                new AnalyticsHelper.AnalyticParamBuilder()
                        .addBoolParam("alive", false)
                        .build());
    }
}
