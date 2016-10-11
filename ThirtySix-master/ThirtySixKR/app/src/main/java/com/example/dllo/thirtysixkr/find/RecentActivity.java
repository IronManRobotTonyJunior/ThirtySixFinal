package com.example.dllo.thirtysixkr.find;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;

import com.example.dllo.thirtysixkr.BaseActivity;
import com.example.dllo.thirtysixkr.R;
import com.example.dllo.thirtysixkr.tools.url.Kr36Url;
import com.example.dllo.thirtysixkr.tools.webrequest.SendGetRequest;

/**
 * Created by 鸿尧 on 2016/9/30.
 */
public class RecentActivity extends BaseActivity implements View.OnClickListener {

    private RecentAdapter recentAdapter;
    private String url;
    private RadioButton rbTime;
    private RadioButton rbStyle;
    private RadioButton rbAll;
    private RadioButton rbDemo;
    private RadioButton rbZone;
    private RadioButton rbInvestment;
    private RadioButton rbService;
    private RadioButton rbFinancing;
    private LinearLayout linearLayoutStyle;
    private LinearLayout linearLayoutTime;
    private RadioButton rbTimeAll;
    private RadioButton rbWeekend;
    private RadioButton rbWeek;
    private RadioButton rbNext;

    @Override
    protected int setLayout() {
        return R.layout.activity_recent;
    }

    @Override
    protected void initView() {
        ListView lv = bindView(R.id.recent_lv);
        recentAdapter = new RecentAdapter(this);
        lv.setAdapter(recentAdapter);
        rbStyle = bindView(R.id.activity_recent_rb_style);
        rbTime = bindView(R.id.activity_recent_rb_time);
        rbStyle.setOnClickListener(this);
        rbTime.setOnClickListener(this);


    }

    @Override
    protected void initData() {
        url = Kr36Url.findAll(1);
        refreshData();
        linearLayoutStyle = (LinearLayout) findViewById(R.id.find_style_ll);
        linearLayoutTime = (LinearLayout) findViewById(R.id.find_time_ll);
        findStyleById();
        findTimeById();
    }


    private void refreshData() {
        SendGetRequest.sendGetRequest(url, RecentBean.class, new SendGetRequest.OnResponseListener<RecentBean>() {
            @Override
            public void onResponse(RecentBean response) {
                recentAdapter.refreshBeans(response.getD().getData());
            }

            @Override
            public void onError() {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_recent_rb_style:
                recentRBStyle();
                break;
            case R.id.activity_recent_rb_time:
                recentRBTime();
                break;
            case R.id.find_style_all:
                url = Kr36Url.findAll(1);
                refreshData();
                linearLayoutStyle.setVisibility(View.GONE);
                rbStyle.setText("全部");
                break;
            case R.id.find_style_demo_day:
                url = Kr36Url.findAll(1) + Kr36Url.findDemoDay;
                refreshData();
                linearLayoutStyle.setVisibility(View.GONE);
                rbStyle.setText("Demo Day");
                break;
            case R.id.find_style_zone:
                url = Kr36Url.findAll(1) + Kr36Url.findZone;
                refreshData();
                linearLayoutStyle.setVisibility(View.GONE);
                rbStyle.setText("氪空间");
                break;
            case R.id.find_style_investment:
                url = Kr36Url.findAll(1) + Kr36Url.findInvestment;
                refreshData();
                linearLayoutStyle.setVisibility(View.GONE);
                rbStyle.setText("股权投资");
                break;
            case R.id.find_style_service:
                url = Kr36Url.findAll(1) + Kr36Url.findService;
                refreshData();
                linearLayoutStyle.setVisibility(View.GONE);
                rbStyle.setText("企业服务");
                break;
            case R.id.find_style_financing:
                url = Kr36Url.findAll(1) + Kr36Url.findFinance;
                refreshData();
                linearLayoutStyle.setVisibility(View.GONE);
                rbStyle.setText("极速融资");
                break;
            case R.id.find_time_all:
                break;
            case R.id.find_time_weekend:
                break;
            case R.id.find_time_week:
                break;
            case R.id.find_time_next:
                break;
            case R.id.find_time_ll:
                linearLayoutTime.setVisibility(View.GONE);
                break;
            case R.id.find_style_ll:
                linearLayoutStyle.setVisibility(View.GONE);
                break;
            default:
                break;
        }

    }

    private void recentRBStyle() {
        if (linearLayoutStyle.getVisibility() == View.VISIBLE) {
            linearLayoutStyle.setVisibility(View.GONE);
            rbStyle.setChecked(false);
        } else {
            rbStyle.setChecked(true);
            linearLayoutStyle.setVisibility(View.VISIBLE);
            linearLayoutTime.setVisibility(View.GONE);
        }
    }

    private void recentRBTime() {
        if (linearLayoutTime.getVisibility() == View.VISIBLE) {
            linearLayoutTime.setVisibility(View.GONE);
            rbTime.setChecked(false);
        } else {
            rbTime.setChecked(true);
            linearLayoutStyle.setVisibility(View.GONE);
            linearLayoutTime.setVisibility(View.VISIBLE);
        }
    }

    private void findStyleById() {
        linearLayoutStyle.setOnClickListener(this);
        rbAll = (RadioButton) linearLayoutStyle.findViewById(R.id.find_style_all);
        rbDemo = (RadioButton) linearLayoutStyle.findViewById(R.id.find_style_demo_day);
        rbZone = (RadioButton) linearLayoutStyle.findViewById(R.id.find_style_zone);
        rbInvestment = (RadioButton) linearLayoutStyle.findViewById(R.id.find_style_investment);
        rbService = (RadioButton) linearLayoutStyle.findViewById(R.id.find_style_service);
        rbFinancing = (RadioButton) linearLayoutStyle.findViewById(R.id.find_style_financing);
        rbAll.setOnClickListener(this);
        rbDemo.setOnClickListener(this);
        rbZone.setOnClickListener(this);
        rbInvestment.setOnClickListener(this);
        rbService.setOnClickListener(this);
        rbFinancing.setOnClickListener(this);
    }

    private void findTimeById() {
        linearLayoutTime.setOnClickListener(this);
        rbTimeAll = (RadioButton) linearLayoutTime.findViewById(R.id.find_time_all);
        rbWeekend = (RadioButton) linearLayoutTime.findViewById(R.id.find_time_weekend);
        rbWeek = (RadioButton) linearLayoutTime.findViewById(R.id.find_time_week);
        rbNext = (RadioButton) linearLayoutTime.findViewById(R.id.find_time_next);
        rbTimeAll.setOnClickListener(this);
        rbWeekend.setOnClickListener(this);
        rbWeek.setOnClickListener(this);
        rbNext.setOnClickListener(this);
    }
}
