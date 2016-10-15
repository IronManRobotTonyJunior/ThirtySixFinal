package com.example.dllo.thirtysixkr.search;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HeaderViewListAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dllo.thirtysixkr.base.BaseActivity;
import com.example.dllo.thirtysixkr.R;
import com.example.dllo.thirtysixkr.news.NewsAdapter;
import com.example.dllo.thirtysixkr.news.NewsBean;
import com.example.dllo.thirtysixkr.tools.db.DBTools;
import com.example.dllo.thirtysixkr.tools.url.Kr36Url;
import com.example.dllo.thirtysixkr.tools.webrequest.SendGetRequest;
import com.example.dllo.thirtysixkr.web.WebDetailActivity;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends BaseActivity implements View.OnClickListener {

    private EditText editText;
    private Button cancel;
    private ImageView delete;
    private ImageButton search;
    private ListView lv;
    private DBTools tools;
    private RelativeLayout noSearchRL;
    private RelativeLayout searchMoreRL;
    private List<HistoryBean> contents = new ArrayList<>();
    private SearchAdapter searchAdapter;
    private View historyViewHeader;
    private View historyViewFooter;
    private View viewMore;
    private TextView deleteHistory;
    private NewsAdapter adapter;

    @Override
    protected int setLayout() {
        return R.layout.search_activity;
    }

    @Override
    protected void initView() {


        search = bindView(R.id.search_ib);

        searchAdapter = new SearchAdapter(this);

        adapter = new NewsAdapter(this);

        editText = bindView(R.id.search_et);

        cancel = bindView(R.id.search_cancel);

        noSearchRL = bindView(R.id.search_rl);

        delete = bindView(R.id.search_delete);

        lv = bindView(R.id.search_lv);

        tools = DBTools.getInstance();

        tools.queryALLDB(new DBTools.QueryListener<HistoryBean>() {
            @Override
            public void onQuery(List<HistoryBean> data) {
                contents = data;
                if (contents.size() != 0) {
                    noSearchRL.setVisibility(View.GONE);
                    lv.setVisibility(View.VISIBLE);
                    searchAdapter.setBeans((ArrayList<HistoryBean>) contents);
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            if (position != 0) {
                                editText.setText(contents.get(contents.size() - position).getHistoryQuery());
                                editText.setSelection(editText.getText().length());
                            }
                        }
                    });
//            lv.removeHeaderView(viewMore);
//            lv.setAdapter(searchAdapter);
                    setHistoryAdapter(searchAdapter);
                } else {
                    noSearchRL.setVisibility(View.VISIBLE);
                    setSearchAdapter(adapter);
                }
            }
        });

        Log.d("SearchActivity", "contents:" + contents);


        viewMore = LayoutInflater.from(this).inflate(R.layout.search_lv_header, null);

        historyViewHeader = LayoutInflater.from(this).inflate(R.layout.search_lv_history_header, null);

        historyViewFooter = LayoutInflater.from(this).inflate(R.layout.search_lv_history_footer, null);

        deleteHistory = (TextView) historyViewFooter.findViewById(R.id.search_footer_tv);
//        lv.addHeaderView(viewMore);
//        lv.addHeaderView(historyViewHeader);
//        lv.addFooterView(historyViewFooter);
        searchMoreRL = (RelativeLayout) viewMore.findViewById(R.id.search_rl_more);


        deleteHistory.setOnClickListener(this);
        cancel.setOnClickListener(this);
        delete.setOnClickListener(this);
        search.setOnClickListener(this);


    }

    @Override
    protected void initData() {


        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                delete.setVisibility(View.VISIBLE);
                lv.setVisibility(View.VISIBLE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (editText.getText().length() != 0) {
                    String url = Kr36Url.research(editText.getText() + "");
                    SendGetRequest.sendGetRequest(url, NewsBean.class, new SendGetRequest.OnResponseListener<NewsBean>() {
                        @Override
                        public void onResponse(final NewsBean response) {
                            if (editText.getText().toString().length() == 0) {
                                return;
                            }
                            if (response.getD().getTotalCount() == 0) {
                                noSearchRL.setVisibility(View.VISIBLE);
                                lv.setVisibility(View.GONE);
                            } else {
                                noSearchRL.setVisibility(View.GONE);
                                lv.setVisibility(View.VISIBLE);
                                adapter.setBeans(response.getD().getData());
                                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        if (position != 0) {
                                            Intent intent = new Intent(SearchActivity.this, WebDetailActivity.class);
                                            intent.putExtra("web", response.getD().getData().get(position - 1).getFeedId());
                                            intent.putExtra("title", response.getD().getData().get(position - 1).getTitle());
                                            intent.putExtra("time", response.getD().getData().get(position - 1).getPublishTime());
                                            startActivity(intent);
                                            HistoryBean bean = new HistoryBean();
                                            bean.setHistoryQuery(editText.getText() + "");
                                            tools.insertDB(bean);
                                            SearchActivity.this.overridePendingTransition(R.anim.activity_in, 0);
                                        }
                                    }
                                });
//                                Log.d("SearchActivity", lv.getAdapter().getClass().getSimpleName());
                                HeaderViewListAdapter headAdapter = (HeaderViewListAdapter) lv.getAdapter();
                                if (!(headAdapter.getWrappedAdapter() instanceof NewsAdapter)) {
                                    setSearchAdapter(adapter);
                                }

                            }
                        }


                        @Override
                        public void onError() {

                        }
                    });
                } else {
                    tools.queryALLDB(new DBTools.QueryListener<HistoryBean>() {
                        @Override
                        public void onQuery(List<HistoryBean> data) {
                            contents = data;
                            delete.setVisibility(View.INVISIBLE);
                            if (contents.size() != 0) {
                                noSearchRL.setVisibility(View.GONE);
                                lv.setVisibility(View.VISIBLE);
                                searchAdapter.setBeans((ArrayList<HistoryBean>) contents);
                                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        if (position != 0) {
                                            editText.setText(contents.get(contents.size() - position).getHistoryQuery());
                                            editText.setSelection(editText.getText().length());
                                        }
                                    }
                                });
                                Log.d("SearchActivity", lv.getAdapter().getClass().getSimpleName());
                                HeaderViewListAdapter headAdapter = (HeaderViewListAdapter) lv.getAdapter();
                                if (!(headAdapter.getWrappedAdapter() instanceof SearchAdapter)) {
                                    setHistoryAdapter(searchAdapter);
                                }

                            } else {
                                noSearchRL.setVisibility(View.VISIBLE);
                                lv.setVisibility(View.GONE);
                                delete.setVisibility(View.INVISIBLE);


                            }
                        }
                    });

                }

            }
        });


    }

    //重新设置为历史记录的Adapter
    private void setHistoryAdapter(SearchAdapter searchAdapter) {
        if (lv.getHeaderViewsCount() > 0) {
            lv.removeHeaderView(viewMore);//去掉搜索的头布局
        }
        lv.addHeaderView(historyViewHeader);
        lv.addFooterView(historyViewFooter);
        lv.setAdapter(searchAdapter);

    }

    //更新为搜索Adapter
    private void setSearchAdapter(NewsAdapter newsAdapter) {
        lv.removeHeaderView(historyViewHeader);
        lv.removeFooterView(historyViewFooter);
        lv.addHeaderView(viewMore);
        lv.setAdapter(newsAdapter);
    }

//    private void addHeadHistory() {
//        lv.addHeaderView(historyViewHeader);
//        lv.addFooterView(historyViewFooter);
//        lv.removeHeaderView(viewMore);
//    }

//    private void addHeadViewMore() {
//        lv.addHeaderView(viewMore);
//        lv.removeHeaderView(historyViewHeader);
//        lv.removeFooterView(historyViewFooter);
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_cancel:

                finish();

                break;
            case R.id.search_delete:

                editText.setText("");

                break;
            case R.id.search_ib:
                if (editText.getText().length() != 0) {
                    HistoryBean bean = new HistoryBean();
                    bean.setHistoryQuery(editText.getText() + "");
                    tools.insertDB(bean);
                }
                break;
            case R.id.search_footer_tv:
                tools.deleteAllDB();
                lv.setVisibility(View.GONE);
                noSearchRL.setVisibility(View.VISIBLE);
                break;
        }

    }
}
