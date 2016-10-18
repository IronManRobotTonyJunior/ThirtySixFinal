package com.example.dllo.thirtysixkr.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.dllo.thirtysixkr.R;
import com.example.dllo.thirtysixkr.bean.HistoryBean;

import java.util.ArrayList;

public class SearchAdapter extends BaseAdapter {
    private ArrayList<HistoryBean> beans;
    private Context context;

    public SearchAdapter(Context context) {
        this.context = context;

    }

    public void setBeans(ArrayList<HistoryBean> beans) {
        this.beans = beans;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return beans == null ? 0 : beans.size();
    }

    @Override
    public Object getItem(int position) {
        return beans.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_search, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.textView.setText(beans.get(beans.size() - position - 1).getHistoryQuery());
        return convertView;
    }

    private class ViewHolder {


        private final TextView textView;

        public ViewHolder(View v) {

            textView = (TextView) v.findViewById(R.id.search_history_tv);

        }
    }
}
