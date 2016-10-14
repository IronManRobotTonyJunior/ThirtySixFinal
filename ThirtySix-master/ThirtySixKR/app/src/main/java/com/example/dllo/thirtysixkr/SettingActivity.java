package com.example.dllo.thirtysixkr;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import com.example.dllo.thirtysixkr.base.BaseActivity;

public class SettingActivity extends BaseActivity implements View.OnClickListener {

    private TextView tvMessage;
    private TextView tvWiFi;
    private TextView tvClear;
    private TextView tvAbout;
    private TextView tvCheck;
    private TextView tvFeedback;
    private TextView tvCache;
    private Switch switchWifi;
    private Toolbar mToolbar;
    private String str;

    @Override
    protected int setLayout() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView() {
        mToolbar = bindView(R.id.setting_toolbar);
        tvMessage = bindView(R.id.setting_tv_message);
        tvWiFi = bindView(R.id.setting_tv_wifi);
        tvCache = bindView(R.id.setting_cache_size);
        tvClear = bindView(R.id.setting_tv_clear);
        tvAbout = bindView(R.id.setting_tv_about);
        tvCheck = bindView(R.id.setting_tv_check);
        tvFeedback = bindView(R.id.setting_tv_feedback);
        switchWifi = bindView(R.id.setting_switch);


    }

    @Override
    protected void initData() {
        try {
            str = DataCleanManager.getTotalCacheSize(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (str.substring(0, 3) == "0.0") {
        } else {
            tvCache.setText(str);
        }

        mToolbar.setOnClickListener(this);
        tvMessage.setOnClickListener(this);
        tvWiFi.setOnClickListener(this);
        tvClear.setOnClickListener(this);
        tvAbout.setOnClickListener(this);
        tvCheck.setOnClickListener(this);
        tvFeedback.setOnClickListener(this);
        switchWifi.setOnClickListener(this);
//        Drawable drawable = getResources().getDrawable(R.mipmap.seaarch_nav_icon_search);
//        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
//        tvFeedback.setCompoundDrawables( null, null,drawable, null);
        mToolbar.setNavigationIcon(R.mipmap.common_nav_icon_back);
        mToolbar.setTitle("");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setting_tv_message:
                Intent intent = new Intent(SettingActivity.this, MessageSettingActivity.class);
                startActivity(intent);
                break;
            case R.id.setting_tv_wifi:
                if (switchWifi.isChecked()) {
                    switchWifi.setChecked(false);
                } else {
                    switchWifi.setChecked(true);
                }
                break;
            case R.id.setting_tv_clear:
                DataCleanManager.clearAllCache(this);
                tvCache.setText("0K");
                break;
            case R.id.setting_tv_about:
                break;
            case R.id.setting_tv_check:
                break;
            case R.id.setting_tv_feedback:
                break;
        }

    }
}
