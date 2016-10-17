package com.example.dllo.thirtysixkr.find;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.example.dllo.thirtysixkr.base.BaseActivity;
import com.example.dllo.thirtysixkr.R;
import com.example.dllo.thirtysixkr.bean.findbean.InvestorBean;
import com.example.dllo.thirtysixkr.tools.url.Kr36Url;
import com.example.dllo.thirtysixkr.tools.webrequest.SendGetRequest;

public class InvestorActivity extends BaseActivity{

    private ListView lv;
    private Toolbar toolbar;
    private String url = Kr36Url.findInvestor;
    private InvestorAdapter investorAdapter;

    @Override
    protected int setLayout() {
        return R.layout.activity_investor;
    }

    @Override
    protected void initView() {
        toolbar = bindView(R.id.investor_toolbar);
        lv = bindView(R.id.investor_lv);
        investorAdapter = new InvestorAdapter(this);
        lv.setAdapter(investorAdapter);
    }

    @Override
    protected void initData() {
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.mipmap.common_nav_icon_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        refreshData();
    }

    private void refreshData() {
        SendGetRequest.sendGetRequest(url, InvestorBean.class, new SendGetRequest.OnResponseListener<InvestorBean>() {
            @Override
            public void onResponse(InvestorBean response) {
                investorAdapter.refreshBeans(response.getD().getData());
            }

            @Override
            public void onError() {

            }
        });
    }
}
