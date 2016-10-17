package com.example.dllo.thirtysixkr.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.dllo.thirtysixkr.R;
import com.example.dllo.thirtysixkr.base.BaseActivity;

import java.util.ArrayList;

public class LeadActivity extends BaseActivity {
    private SharedPreferences mSetting;
    private ArrayList<View> mLeadViews;
    private View mViewPageThree;
    private View mViewPageTwo;
    private View mViewPageOne;
    private ViewPager mVp;
    private Intent mIntent;

    @Override
    protected int setLayout() {
        mIntent = new Intent(LeadActivity.this, MainActivity.class);
        mSetting = getSharedPreferences("settingTimePush", MODE_PRIVATE);
        boolean isFirst = mSetting.getBoolean("firstStart", true);
        if (!isFirst) {
            startActivity(mIntent);
        }
        return R.layout.activity_lead;
    }

    @Override
    protected void initView() {
        mViewPageOne = LayoutInflater.from(this).inflate(R.layout.lead_image_one, null);
        mViewPageTwo = LayoutInflater.from(this).inflate(R.layout.lead_image_two, null);
        mViewPageThree = LayoutInflater.from(this).inflate(R.layout.lead_image_three, null);
        mVp = bindView(R.id.lead_vp);
        mLeadViews = new ArrayList<>();
        Button btn = (Button) mViewPageThree.findViewById(R.id.btn_into_main);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(mIntent);
            }
        });


    }

    @Override
    protected void initData() {
        mLeadViews.add(mViewPageOne);
        mLeadViews.add(mViewPageTwo);
        mLeadViews.add(mViewPageThree);
        PagerAdapter pagerAdapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return mLeadViews == null ? 0 : mLeadViews.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(mLeadViews.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(mLeadViews.get(position));
                return mLeadViews.get(position);
            }
        };
        mVp.setAdapter(pagerAdapter);
    }
}
