package com.example.dllo.thirtysixkr.investment;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.example.dllo.thirtysixkr.BaseFragment;
import com.example.dllo.thirtysixkr.R;
import com.example.dllo.thirtysixkr.search.SearchActivity;
import com.example.dllo.thirtysixkr.tools.url.Kr36Url;

import java.util.ArrayList;

public class InvestmentFragment extends BaseFragment implements View.OnClickListener {

    private ArrayList<Fragment> fragments;
    private ArrayList<String> titles;
    private TabLayout tb;
    private ViewPager vp;
    private ImageView iv_search;

    @Override
    protected int setLayout() {
        return R.layout.fragment_investment;
    }

    @Override
    protected void initView() {
        tb = bindView(R.id.investment_tb);
        vp = bindView(R.id.investment_vp);

        iv_search = bindView(R.id.investment_search);


        iv_search.setOnClickListener(this);

        fragments = new ArrayList<>();
        titles = new ArrayList<>();
        ChildFragment fragmentAll = (ChildFragment) ChildFragment.newInstance(1, Kr36Url.INVESTMENT_ALL);
        ChildFragment fragmentFundraising = (ChildFragment) ChildFragment.newInstance(1, Kr36Url.INVESTMENT_UNDERWAY);
        ChildFragment fragmentComplete = (ChildFragment) ChildFragment.newInstance(1, Kr36Url.INVESTMENT_RAISE);
        ChildFragment fragmentSuccess = (ChildFragment) ChildFragment.newInstance(1, Kr36Url.INVESTMENT_FINISH);
//        ChildFragment fragmentAll = new ChildFragment();
//        ChildFragment fragmentFundraising = new ChildFragment();
//        ChildFragment fragmentComplete = new ChildFragment();
//        ChildFragment fragmentSuccess = new ChildFragment();
//
//        fragmentAll.newInstance(1, Kr36Url.INVESTMENT_ALL);
//        fragmentFundraising.newInstance(1, Kr36Url.INVESTMENT_UNDERWAY);
//        fragmentComplete.newInstance(1, Kr36Url.INVESTMENT_RAISE);
//        fragmentSuccess.newInstance(1, Kr36Url.INVESTMENT_FINISH);


//        Bundle bundleAll = new Bundle();
//        bundleAll.putString("type", Kr36Url.investment(1,Kr36Url.INVESTMENT_ALL));
//        fragmentAll.setArguments(bundleAll);
//
//        Bundle bundleFundraising = new Bundle();
//        bundleFundraising.putString("type", Kr36Url.investment(20,Kr36Url.INVESTMENT_UNDERWAY));
//        fragmentFundraising.setArguments(bundleFundraising);
//
//        Bundle bundleComplete = new Bundle();
//        bundleComplete.putString("type", Kr36Url.investment(20,Kr36Url.INVESTMENT_RAISE));
//        fragmentComplete.setArguments(bundleComplete);
//
//        Bundle bundleSuccess = new Bundle();
//        bundleSuccess.putString("type", Kr36Url.investment(20,Kr36Url.INVESTMENT_FINISH));
//        fragmentSuccess.setArguments(bundleSuccess);

        fragments.add(fragmentAll);
        fragments.add(fragmentFundraising);
        fragments.add(fragmentComplete);
        fragments.add(fragmentSuccess);
        titles.add("全部");
        titles.add("募资中");
        titles.add("募资完成");
        titles.add("融资成功");


    }

    @Override
    protected void initData() {
        InvestmentAdapter adapter = new InvestmentAdapter(getChildFragmentManager());
        adapter.setFragments(fragments);
        adapter.setTitles(titles);
        vp.setAdapter(adapter);
        tb.setupWithViewPager(vp);
        tb.setSelectedTabIndicatorColor(Color.parseColor("#4285f4"));
        tb.setTabTextColors(Color.GRAY, Color.parseColor("#4285f4"));


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.investment_search:
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
                break;
        }
    }
}
