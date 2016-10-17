package com.example.dllo.thirtysixkr.find;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.dllo.thirtysixkr.R;
import com.example.dllo.thirtysixkr.bean.findbean.InvestorBean;
import com.example.dllo.thirtysixkr.tools.customview.CropSquareTransformation;
import com.example.dllo.thirtysixkr.tools.customview.MyCircleImage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class InvestorAdapter extends BaseAdapter {
    private Context mContext;
    private List<InvestorBean.Data.DataBean> beans;
    private final ForegroundColorSpan blackSpan;
    private final ForegroundColorSpan graySpan;
    private final ForegroundColorSpan orangeSpan;
    private SpannableStringBuilder builderDomain;
    private SpannableStringBuilder builderStage;

    public InvestorAdapter(Context mContext) {
        this.mContext = mContext;
        beans = new ArrayList<>();
        blackSpan = new ForegroundColorSpan(Color.parseColor("#636A75"));
        graySpan = new ForegroundColorSpan(Color.parseColor("#99A2AB"));
        orangeSpan = new ForegroundColorSpan(Color.parseColor("#FF6F0F"));
    }

    public void refreshBeans(List<InvestorBean.Data.DataBean> beans) {
        this.beans.clear();
        this.beans.addAll(beans);
        notifyDataSetChanged();
    }

    public void addBeans(List<InvestorBean.Data.DataBean> beans) {
        this.beans.addAll(beans);
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
        InvestorBean.Data.DataBean bean = beans.get(position);
        String focusIndusty = "";
        String investPhases = "";
        for (int i = 0; i < bean.getFocusIndustry().size(); i++) {
            focusIndusty += bean.getFocusIndustry().get(i) + " ";
        }
        for (int i = 0; i < bean.getInvestPhases().size(); i++) {
            investPhases += bean.getInvestPhases().get(i) + " ";
        }

        ViewHolder viewHolder = null;
        if (viewHolder == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_find_investor, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Picasso.with(mContext).load(bean.getUser().getAvatar()).error(R.mipmap.common_avatar).transform(new CropSquareTransformation()).into(viewHolder.investorIcon);

        viewHolder.tvName.setText(bean.getUser().getName());

        if (bean.getFocusIndustry().size() != 0) {
            viewHolder.tvDomain.setText("关注领域:" + focusIndusty);
        } else {
            viewHolder.tvDomain.setText("关注领域:未披露");
        }
        if (bean.getInvestPhases().size() != 0) {
            viewHolder.tvStage.setText("投资阶段:" + investPhases);
        } else {
            viewHolder.tvStage.setText("投资阶段:未披露");
        }
        builderDomain = new SpannableStringBuilder(viewHolder.tvDomain.getText().toString());
        builderStage = new SpannableStringBuilder(viewHolder.tvStage.getText().toString());
        builderDomain.setSpan(blackSpan, 0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builderDomain.setSpan(graySpan, 5, viewHolder.tvDomain.getText().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        viewHolder.tvDomain.setText(builderDomain);

        builderStage.setSpan(blackSpan, 0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builderStage.setSpan(orangeSpan, 5, viewHolder.tvStage.getText().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        viewHolder.tvStage.setText(builderStage);

        return convertView;
    }

    class ViewHolder {

        private final TextView tvStage;
        private final TextView tvDomain;
        private final TextView tvName;
        private final MyCircleImage investorIcon;


        public ViewHolder(View view) {
            investorIcon = (MyCircleImage) view.findViewById(R.id.investor_icon);
            tvName = (TextView) view.findViewById(R.id.investor_name);
            tvDomain = (TextView) view.findViewById(R.id.investor_domain);
            tvStage = (TextView) view.findViewById(R.id.investor_stage);


        }
    }
}
