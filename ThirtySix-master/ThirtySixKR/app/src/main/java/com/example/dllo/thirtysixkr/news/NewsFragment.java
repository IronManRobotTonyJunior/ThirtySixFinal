package com.example.dllo.thirtysixkr.news;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dllo.thirtysixkr.BaseFragment;
import com.example.dllo.thirtysixkr.R;
import com.example.dllo.thirtysixkr.search.SearchActivity;
import com.example.dllo.thirtysixkr.tools.url.Kr36Url;
import com.example.dllo.thirtysixkr.tools.webrequest.SendGetRequest;
import com.example.dllo.thirtysixkr.web.WebDetailActivity;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerClickListener;

import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends BaseFragment implements View.OnClickListener {

    private DrawerLayout drawerLayout;
    private LinearLayout left;
    private ArrayList<String> imageViews;
    private ImageView ivDrawer;
    private ImageView ivSearch;
    private ImageView ivBack;
    private TextView drawerTvAll;
    private TextView drawerTvEarly;
    private TextView drawerTvDeep;
    private TextView drawerTvBig;
    private TextView drawerTvEight;
    private TextView drawerTvIcon;
    private TextView drawerTvFriend;
    private TextView drawerTvResearch;
    private TextView drawerTvKrTV;
    private String part = Kr36Url.NEW_ALL;
    private PullToRefreshListView pullToRefreshlv;
    private NewsAdapter newsAdapter;
    private int page = 20;
    private Banner banner;
    private List<NewsBean.Data.DataBean> beans;


    @Override
    protected int setLayout() {
        return R.layout.fragment_news;
    }

    @Override
    protected void initView() {
        pullToRefreshlv = bindView(R.id.Refresh_headline);

        pullToRefreshlv.setMode(PullToRefreshBase.Mode.BOTH);

        ListView listView = pullToRefreshlv.getRefreshableView();

        View v = LayoutInflater.from(mContext).inflate(R.layout.header_news, null);

        listView.addHeaderView(v);

        banner = bindView(R.id.header_banner, v);


        pullToRefreshlv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                refreshData(20 + "", part);
                Log.d("NewsFragment", "停");

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                page += 20;
                refreshData(page + "", part);

            }
        });

        pullToRefreshlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), WebDetailActivity.class);
                intent.putExtra("web", beans.get(position - 2).getFeedId());
                intent.putExtra("title", beans.get(position - 2).getTitle());
                intent.putExtra("time", beans.get(position - 2).getPublishTime());
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.activity_in, 0);
            }
        });
        banner.setOnBannerClickListener(new OnBannerClickListener() {
            @Override
            public void OnBannerClick(int position) {
                Log.d("NewsFragment", "banner:" + position);
            }
        });

        drawerLayout = bindView(R.id.drawerLayout);

        left = bindView(R.id.left);

        ivDrawer = bindView(R.id.news_iv_drawer);

        ivSearch = bindView(R.id.news_iv_search);

        ivBack = bindView(R.id.iv_return);

        drawerTvAll = bindView(R.id.tv_all);

        drawerTvBig = bindView(R.id.tv_big_company);

        drawerTvEarly = bindView(R.id.tv_early);

        drawerTvDeep = bindView(R.id.tv_deep);

        drawerTvEight = bindView(R.id.tv_eight);

        drawerTvIcon = bindView(R.id.tv_icon);

        drawerTvFriend = bindView(R.id.tv_friend);

        drawerTvResearch = bindView(R.id.tv_research);

        drawerTvKrTV = bindView(R.id.tv_kr_tv);

        ivDrawer.setOnClickListener(this);

        ivBack.setOnClickListener(this);

        ivSearch.setOnClickListener(this);

        drawerTvBig.setOnClickListener(this);

        drawerTvAll.setOnClickListener(this);

        drawerTvEight.setOnClickListener(this);

        drawerTvEarly.setOnClickListener(this);

        drawerTvIcon.setOnClickListener(this);

        drawerTvDeep.setOnClickListener(this);

        drawerTvFriend.setOnClickListener(this);

        drawerTvResearch.setOnClickListener(this);

        drawerTvKrTV.setOnClickListener(this);


    }

    @Override
    protected void initData() {
        newsAdapter = new NewsAdapter(mContext);
        pullToRefreshlv.setAdapter(newsAdapter);
        refreshData("20", Kr36Url.NEW_ALL);

        SendGetRequest.sendGetRequest(Kr36Url.newRotate, RingBean.class, new SendGetRequest.OnResponseListener<RingBean>() {
            @Override
            public void onResponse(RingBean response) {
                imageViews = new ArrayList<String>();
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

    private void refreshData(String page, String part) {
        SendGetRequest.sendGetRequest(Kr36Url.news(page, part), NewsBean.class, new SendGetRequest.OnResponseListener<NewsBean>() {

            @Override
            public void onResponse(NewsBean response) {
                beans = new ArrayList<>();
                newsAdapter.setBeans(response.getD().getData());
                beans = response.getD().getData();
                Log.d("NewsFragment", "response.getD().getData():" + response.getD().getData().size());
                pullToRefreshlv.onRefreshComplete();

            }

            @Override
            public void onError() {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.news_iv_drawer:
                drawerLayout.openDrawer(left);
                break;
            case R.id.iv_return:
                drawerLayout.closeDrawer(left);
                break;
            case R.id.tv_all:
                banner.setVisibility(View.VISIBLE);
                part = Kr36Url.NEW_ALL;
                pullToRefreshlv.setRefreshing(false);
                drawerLayout.closeDrawer(left);
                break;
            case R.id.tv_early:
                banner.setVisibility(View.GONE);
                part = Kr36Url.NEW_EARLY;
                drawerLayout.closeDrawer(left);
                pullToRefreshlv.setRefreshing(false);
                break;
            case R.id.tv_deep:
                banner.setVisibility(View.GONE);
                part = Kr36Url.NEW_DEEP;
                drawerLayout.closeDrawer(left);
                pullToRefreshlv.setRefreshing(false);
                break;
            case R.id.tv_big_company:
                banner.setVisibility(View.GONE);
                part = Kr36Url.NEW_BIG_COMPANY;
                drawerLayout.closeDrawer(left);
                pullToRefreshlv.setRefreshing(false);
                break;
            case R.id.tv_eight:
                banner.setVisibility(View.GONE);
                part = Kr36Url.NEW_EIGHT;
                drawerLayout.closeDrawer(left);
                pullToRefreshlv.setRefreshing(true);
                break;
            case R.id.tv_icon:
                banner.setVisibility(View.GONE);
                part = Kr36Url.NEW_ICON;
                drawerLayout.closeDrawer(left);
                pullToRefreshlv.setRefreshing(true);
                break;
            case R.id.tv_friend:
                banner.setVisibility(View.GONE);
                part = Kr36Url.NEW_FRIEND;
                drawerLayout.closeDrawer(left);
                pullToRefreshlv.setRefreshing(true);
                break;
            case R.id.tv_research:
                banner.setVisibility(View.GONE);
                part = Kr36Url.NEW_RESEARCH;
                drawerLayout.closeDrawer(left);
                pullToRefreshlv.setRefreshing(true);
                pullToRefreshlv.onRefreshComplete();
                break;
            case R.id.tv_kr_tv:
                banner.setVisibility(View.GONE);
                part = Kr36Url.NEW_TV;
                drawerLayout.closeDrawer(left);
                pullToRefreshlv.setRefreshing(true);
                break;

            case R.id.news_iv_search:
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
                break;


            default:
                break;
        }
    }
}
