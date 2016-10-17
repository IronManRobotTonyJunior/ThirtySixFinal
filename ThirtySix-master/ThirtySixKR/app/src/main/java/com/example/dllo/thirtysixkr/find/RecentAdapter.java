package com.example.dllo.thirtysixkr.find;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dllo.thirtysixkr.R;
import com.example.dllo.thirtysixkr.bean.findbean.RecentBean;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 鸿尧 on 2016/9/30.
 */

public class RecentAdapter extends BaseAdapter {
    Context context;
    ArrayList<RecentBean.Data.DataBean> beans;

    public RecentAdapter(Context context) {
        this.context = context;
        beans = new ArrayList<>();
    }

    public void addBeans(List<RecentBean.Data.DataBean> beans) {
        this.beans.addAll(beans);
        notifyDataSetChanged();
    }

    public void refreshBeans(List<RecentBean.Data.DataBean> beans) {
        this.beans.clear();
        this.beans.addAll(beans);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return beans == null ? 0 : beans.size();
    }

    @Override
    public Object getItem(int i) {
        return beans.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        MyViewHolder holder = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_find_recent_activity, viewGroup, false);
            holder = new MyViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (MyViewHolder) view.getTag();
        }
        RecentBean.Data.DataBean bean = (RecentBean.Data.DataBean) getItem(i);
        Picasso.with(context).load(bean.getActivityImg()).into(holder.img);
        holder.tvAddress.setText(bean.getActivityCity());
        holder.tvDetail.setText(bean.getActivityDesc());
        holder.tvState.setText(bean.getActivityStatus());
        holder.tvTime.setText(bean.getActivityTime());
        holder.tvTitle.setText(bean.getActivityName());
        switch (bean.getActivityStatus()){
            case "报名中":
                holder.tvState.setBackgroundResource(R.drawable.shape_find_activity_apply);
                break;
            case "活动中":
                holder.tvState.setBackgroundResource(R.drawable.shape_find_while_activity);
                break;
            case "已结束":
                holder.tvState.setBackgroundResource(R.drawable.shape_find_activity_done);
                break;
        }
        return view;
    }

    class MyViewHolder {

        private TextView tvTitle;
        private TextView tvDetail;
        private TextView tvState;
        private TextView tvAddress;
        private TextView tvTime;
        private ImageView img;

        public MyViewHolder(View v) {
            tvAddress = (TextView) v.findViewById(R.id.recent_activity_address);
            tvDetail = (TextView) v.findViewById(R.id.recent_activity_detail);
            tvState = (TextView) v.findViewById(R.id.recent_activity_state);
            tvTime = (TextView) v.findViewById(R.id.recent_activity_time);
            tvTitle = (TextView) v.findViewById(R.id.recent_activity_title);
            img = (ImageView) v.findViewById(R.id.recent_activity_img);


        }
    }
}
