package com.example.dllo.thirtysixkr.setting;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.example.dllo.thirtysixkr.R;
import com.example.dllo.thirtysixkr.base.BaseActivity;

import cn.jpush.android.api.JPushInterface;

public class MessageSettingActivity extends BaseActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private Switch messagePushSwitch;
    private LinearLayout llMessageSetting;
    private TextView tvDay;
    private TextView tvAllDay;
    private TextView tvPush;
    private SharedPreferences mSettings;
    private int endTime;
    private int startTime;
    private SharedPreferences.Editor mEditor;
    private static final int START_TIME_ALLDAY = 0;
    private static final int END_TIME_ALLDAY = 24;
    private static final int START_TIME_DAY = 10;
    private static final int END_TIME_DAY = 20;

    @Override
    protected int setLayout() {
        return R.layout.actvity_message_setting;
    }

    @Override
    protected void initView() {
        mSettings = getSharedPreferences("settingTimePush", MODE_PRIVATE);
        mEditor = mSettings.edit();
        llMessageSetting = bindView(R.id.ll_message_setting);
        mToolbar = bindView(R.id.toolbar_message_setting);
        messagePushSwitch = bindView(R.id.message_setting_switch);
        tvAllDay = bindView(R.id.tv_message_setting_all_day);
        tvPush = bindView(R.id.tv_message_setting_push);
        tvDay = bindView(R.id.tv_message_setting_day);
        tvPush.setOnClickListener(this);
        tvAllDay.setOnClickListener(this);
        tvDay.setOnClickListener(this);

    }

    @Override
    protected void initData() {
        // 设置初始是否推送与推送时间选项, 持久化
        boolean isPush = mSettings.getBoolean("isPush", true);
        messagePushSwitch.setChecked(isPush);
        startTime = mSettings.getInt("settingTimePushStart", START_TIME_ALLDAY);
        endTime = mSettings.getInt("settingTimePushEnd", END_TIME_ALLDAY);
        setPushTime(startTime, endTime);
        mToolbar.setNavigationIcon(R.mipmap.common_nav_icon_back);
        mToolbar.setTitle("");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (messagePushSwitch.isChecked()) {
            llMessageSetting.setVisibility(View.VISIBLE);
            whichTextDrawable();
        } else {
            llMessageSetting.setVisibility(View.GONE);
        }
        messagePushSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    llMessageSetting.setVisibility(View.VISIBLE);
                    startTime = mSettings.getInt("settingTimePushStart", START_TIME_ALLDAY);
                    endTime = mSettings.getInt("settingTimePushEnd", END_TIME_ALLDAY);
                    whichTextDrawable();
                    JPushInterface.resumePush(getApplicationContext());
                } else {
                    llMessageSetting.setVisibility(View.GONE);
                    JPushInterface.stopPush(getApplicationContext());
                }
                mEditor.putBoolean("isPush", messagePushSwitch.isChecked());
                mEditor.commit();
            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_message_setting_push:
                messagePushSwitch.setChecked(!messagePushSwitch.isChecked());
                break;
            // 点击全天开启
            case R.id.tv_message_setting_all_day:
                pushSelect(tvAllDay, tvDay);
                setPushTime(START_TIME_ALLDAY, END_TIME_ALLDAY);
                Log.d("MessageSettingActivity", "startTime:" + startTime);
                break;
            // 点击只在白天开启
            case R.id.tv_message_setting_day:
                pushSelect(tvDay, tvAllDay);
                setPushTime(START_TIME_DAY, END_TIME_DAY);
                break;
        }
    }

    // 推送时间设置
    private void setPushTime(int startTime, int endTime) {
        JPushInterface.setPushTime(getApplicationContext(), null, startTime, endTime);
        mEditor.putInt("settingTimePushStart", startTime);
        mEditor.putInt("settingTimePushEnd", endTime);
        mEditor.commit();
    }

    // 推送时间对号位置
    private void pushSelect(TextView select, TextView other) {
        Drawable drawable = getResources().getDrawable(R.mipmap.compose_icon__checked);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        select.setCompoundDrawables(null, null, drawable, null);
        other.setCompoundDrawables(null, null, null, null);
    }

    private void whichTextDrawable() {
        if (startTime == START_TIME_ALLDAY) {
            pushSelect(tvAllDay, tvDay);
            setPushTime(START_TIME_ALLDAY, END_TIME_ALLDAY);
        } else {
            pushSelect(tvDay, tvAllDay);
            setPushTime(START_TIME_DAY, END_TIME_DAY);
        }
    }
}
