package com.example.dllo.thirtysixkr.find;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.dllo.thirtysixkr.base.BaseActivity;
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
    private ImageView imgBack;
    private AnimatorSet setOpen;
    private AnimatorSet setClose;
    private ImageView imgGrayBackground;
    private RadioGroup rg;

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
        imgBack = bindView(R.id.activity_recent_img_back);
        linearLayoutStyle = bindView(R.id.find_style_ll);
        linearLayoutTime = bindView(R.id.find_time_ll);
        imgGrayBackground = bindView(R.id.recent_gray_background);
        rg = bindView(R.id.activity_recent_rg);
        findStyleById();
        findTimeById();
        rbStyle.setOnClickListener(this);
        rbTime.setOnClickListener(this);
        imgBack.setOnClickListener(this);


    }

    @Override
    protected void initData() {
        url = Kr36Url.findAll(1);
        refreshData();

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
                imgGrayBackground.setVisibility(View.GONE);
                rbStyle.setText("全部");
                break;
            case R.id.find_style_demo_day:
                url = Kr36Url.findAll(1) + Kr36Url.findDemoDay;
                refreshData();
                linearLayoutStyle.setVisibility(View.GONE);
                imgGrayBackground.setVisibility(View.GONE);
                rbStyle.setText("Demo Day");
                break;
            case R.id.find_style_zone:
                url = Kr36Url.findAll(1) + Kr36Url.findZone;
                refreshData();
                linearLayoutStyle.setVisibility(View.GONE);
                imgGrayBackground.setVisibility(View.GONE);
                rbStyle.setText("氪空间");
                break;
            case R.id.find_style_investment:
                url = Kr36Url.findAll(1) + Kr36Url.findInvestment;
                refreshData();
                linearLayoutStyle.setVisibility(View.GONE);
                imgGrayBackground.setVisibility(View.GONE);
                rbStyle.setText("股权投资");
                break;
            case R.id.find_style_service:
                url = Kr36Url.findAll(1) + Kr36Url.findService;
                refreshData();
                linearLayoutStyle.setVisibility(View.GONE);
                imgGrayBackground.setVisibility(View.GONE);
                rbStyle.setText("企业服务");
                break;
            case R.id.find_style_financing:
                url = Kr36Url.findAll(1) + Kr36Url.findFinance;
                refreshData();
                linearLayoutStyle.setVisibility(View.GONE);
                imgGrayBackground.setVisibility(View.GONE);
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
                imgGrayBackground.setVisibility(View.GONE);
                break;
            case R.id.find_style_ll:
                linearLayoutStyle.setVisibility(View.GONE);
                imgGrayBackground.setVisibility(View.GONE);
                break;
            case R.id.activity_recent_img_back:
                finish();
                break;
            default:
                break;
        }

    }

    //  点击活动类型时的动作
    private void recentRBStyle() {
        if (linearLayoutStyle.getVisibility() == View.VISIBLE) {
            animationClose(linearLayoutStyle);
            rg.clearCheck();
            setClose.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    linearLayoutStyle.setVisibility(View.GONE);
                    imgGrayBackground.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });


        } else {
            linearLayoutStyle.setVisibility(View.VISIBLE);
            imgGrayBackground.setVisibility(View.VISIBLE);
            animationOpen(linearLayoutStyle);
            if (linearLayoutTime.getVisibility() == View.VISIBLE) {
                linearLayoutTime.setVisibility(View.GONE);
            }
            Log.d("RecentActivity", "rbStyle.isChecked():" + rbStyle.isChecked());
        }
    }

    //  点击活动时间时的动作
    private void recentRBTime() {
        if (linearLayoutTime.getVisibility() == View.VISIBLE) {
            animationClose(linearLayoutTime);
            rg.clearCheck();
            setClose.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    linearLayoutTime.setVisibility(View.GONE);
                    imgGrayBackground.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });

        } else {
            if (linearLayoutStyle.getVisibility() == View.VISIBLE) {
                linearLayoutStyle.setVisibility(View.GONE);
            }
            imgGrayBackground.setVisibility(View.VISIBLE);
            linearLayoutTime.setVisibility(View.VISIBLE);
            animationOpen(linearLayoutTime);
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

    private void animationOpen(View v) {
        ObjectAnimator ota = ObjectAnimator.ofFloat(v, "alpha", 0.3f, 0.5f, 0.7f, 1.0f);
        ObjectAnimator ota1 = ObjectAnimator.ofFloat(v, "translationY", -150, 0);
        setOpen = new AnimatorSet();
        setOpen.playTogether(ota, ota1);
        setOpen.setDuration(300);
        setOpen.start();
    }

    private void animationClose(View v) {
        ObjectAnimator ota = ObjectAnimator.ofFloat(v, "alpha", 1.0f, 0.7f, 0.5f, 0.0f);
        ObjectAnimator ota1 = ObjectAnimator.ofFloat(v, "translationY", 0, -150);
        setClose = new AnimatorSet();
        setClose.playTogether(ota, ota1);
        setClose.setDuration(300);
        setClose.start();
    }
}
