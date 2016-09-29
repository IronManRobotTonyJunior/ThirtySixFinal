package com.example.dllo.thirtysixkr.investment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.ListView;

import com.example.dllo.thirtysixkr.BaseFragment;
import com.example.dllo.thirtysixkr.R;
import com.example.dllo.thirtysixkr.tools.url.Kr36Url;
import com.example.dllo.thirtysixkr.tools.webrequest.SendGetRequest;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class ChildFragment extends BaseFragment {

    private int page;

    public static Fragment newInstance(int page, String type) {
        Bundle args = new Bundle();
        args.putInt("page", page);
        args.putString("type", type);
        Fragment fragment = new ChildFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private String url;
    private PullToRefreshListView pullToRefreshListView;
    private Context context;
    private ChildFragmentAdapter adapter;


    @Override
    protected int setLayout() {
        return R.layout.child_fragment_investment;
    }

    @Override
    protected void initView() {

        pullToRefreshListView = bindView(R.id.refresh_child_investment);

        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);

        url = Kr36Url.investment(page, getArguments().getString("type"));

        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {


            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page = 1;
                url = Kr36Url.investment(page, getArguments().getString("type"));
                refreshData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                page++;
                url = Kr36Url.investment(page, getArguments().getString("type"));
                loadData();



            }
        });


    }

    @Override
    protected void initData() {
        adapter = new ChildFragmentAdapter(context);
        pullToRefreshListView.setAdapter(adapter);
        refreshData();

    }

    private void refreshData() {
        SendGetRequest.sendGetRequest(url, InvestmentBean.class, new SendGetRequest.OnResponseListener<InvestmentBean>() {
            @Override
            public void onResponse(InvestmentBean response) {
                adapter.setBeans(response.getD().getData());
                pullToRefreshListView.onRefreshComplete();

            }

            @Override
            public void onError() {

            }
        });
    }

    private void loadData() {
        SendGetRequest.sendGetRequest(url, InvestmentBean.class, new SendGetRequest.OnResponseListener<InvestmentBean>() {
            @Override
            public void onResponse(InvestmentBean response) {
                adapter.loadBeans(response.getD().getData());
                pullToRefreshListView.onRefreshComplete();

            }

            @Override
            public void onError() {

            }
        });
    }

    @Override
    public void onAttach(Context context) {
        page = getArguments().getInt("page");
        super.onAttach(context);
        this.context = context;
    }

}
