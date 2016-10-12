package com.example.dllo.thirtysixkr.find;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dllo.thirtysixkr.base.BaseFragment;
import com.example.dllo.thirtysixkr.R;
import com.example.dllo.thirtysixkr.news.RingBean;
import com.example.dllo.thirtysixkr.search.SearchActivity;
import com.example.dllo.thirtysixkr.tools.url.Kr36Url;
import com.example.dllo.thirtysixkr.tools.webrequest.SendGetRequest;
import com.example.dllo.thirtysixkr.web.ObservableScrollView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;

public class FindFragment extends BaseFragment implements View.OnClickListener {

    private Banner banner;
    private ArrayList<String> imageViews;

    @Override
    protected int setLayout() {
        return R.layout.fragment_find;
    }

    @Override
    protected void initView() {
        ObservableScrollView mySV = bindView(R.id.find_scroll_view);
        final ImageView myIVResearch = bindView(R.id.find_research);
        banner = bindView(R.id.find_banner);
        TextView recentActivity = bindView(R.id.find_recent_activity);
        RelativeLayout findInvestor = bindView(R.id.find_rl_find_investor);
        Button btnFounder = bindView(R.id.find_btn_found);
        Button btnProfessor = bindView(R.id.find_btn_professor);
        mySV.setmOnScrollChangedCallback2(new ObservableScrollView.OnScrollChangedCallback2() {
            @Override
            public void onScroll(int l, int t) {

                if (t > 280){
                    myIVResearch.setVisibility(View.INVISIBLE);
                    Log.d("FindFragment", "滑动t");
                }
                else {
                    myIVResearch.setVisibility(View.VISIBLE);
                }
            }
        });
        myIVResearch.setOnClickListener(this);
        recentActivity.setOnClickListener(this);
        findInvestor.setOnClickListener(this);
        btnFounder.setOnClickListener(this);
        btnProfessor.setOnClickListener(this);

    }

    @Override
    protected void initData() {
        SendGetRequest.sendGetRequest(Kr36Url.findRotate, RingBean.class, new SendGetRequest.OnResponseListener<RingBean>() {
            @Override
            public void onResponse(RingBean response) {
                imageViews = new ArrayList<>();
                for (int i = 0; i < response.getData().getPics().size(); i++) {
                    imageViews.add(response.getData().getPics().get(i).getImgUrl());
                }
                banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
                banner.setIndicatorGravity(BannerConfig.CENTER);
                banner.setImages(imageViews);
            }

            @Override
            public void onError() {

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.find_research:
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
                break;
            case R.id.find_recent_activity:
                Intent intentRecent = new Intent(getActivity(),RecentActivity.class);
                startActivity(intentRecent);
                break;
            case R.id.find_btn_found:
                break;
            case R.id.find_btn_professor:
                break;
            case R.id.find_rl_find_investor:
                Intent intentInvestor = new Intent(getActivity(), InvestorActivity.class);
                startActivity(intentInvestor);
                break;
        }

    }
}
