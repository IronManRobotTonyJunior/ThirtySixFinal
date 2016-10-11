package com.example.dllo.thirtysixkr.investment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.dllo.thirtysixkr.R;
import com.example.dllo.thirtysixkr.tools.customview.CropSquareTransformation;
import com.example.dllo.thirtysixkr.tools.customview.MyCircleImage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ChildFragmentAdapter extends BaseAdapter {


    private Context context;
    private List<InvestmentBean.Data.DataBean> beans;
    private final Drawable drawableNormal;
    private final Drawable drawable;

    public ChildFragmentAdapter(Context context) {
        this.context = context;
        beans = new ArrayList<>();
        drawableNormal = context.getResources().getDrawable(R.drawable.layer_list);
        drawable = context.getResources().getDrawable(R.drawable.layer_list_orange);
    }

    public void setBeans(List<InvestmentBean.Data.DataBean> beans) {
        this.beans.clear();
        this.beans.addAll(beans);
        notifyDataSetChanged();
    }

    public void loadBeans(List<InvestmentBean.Data.DataBean> beans) {
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
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_child_investment, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        InvestmentBean.Data.DataBean bean = (InvestmentBean.Data.DataBean) getItem(position);

        Picasso.with(context).load(bean.getCompany_logo()).transform(new CropSquareTransformation()).into(viewHolder.userIcon);
        Picasso.with(context).load(bean.getFile_list_img()).into(viewHolder.itemInvestmentImg);


        viewHolder.tvItemBrief.setText(bean.getCompany_brief());
        viewHolder.tvItemName.setText(bean.getCompany_name());
        viewHolder.tvInputLeader.setText(bean.getLead_name());
        viewHolder.tvInputFounder.setText(bean.getCf_advantage().get(0).getAdcontent());
        viewHolder.tvInputHatch.setText(bean.getCf_advantage().get(1).getAdcontent());

        switch (bean.getFundStatus().getCrowd_funding_status()) {
            case "MAO_DING":
                viewHolder.tvInputDesc.setText("锚定中");
                viewHolder.pb.setProgress((int) (bean.getRate() * 100));
                viewHolder.tvInputRate.setText(bean.getFundStatus().getDesc());
//                viewHolder.pb.setProgressDrawable(drawableNormal);
                break;
            default:
                viewHolder.tvInputDesc.setText(bean.getFundStatus().getDesc());
                if (bean.getRate() >= 1) {
                    viewHolder.tvInputRate.setText("已募资" + (bean.getRate() * 100 + "").substring(0, 3) + "%");
                    viewHolder.pb.setProgress(100);
//                    viewHolder.pb.setProgressDrawable(drawable);
                } else {
                    viewHolder.pb.setProgress((int) (bean.getRate() * 100));
                    Log.d("ChildFragmentAdapter", "bean.getRate():" + bean.getRate());
                    viewHolder.tvInputRate.setText("已募资" + (bean.getRate() * 100 + "").substring(0, 2) + "%");
//                    viewHolder.pb.setProgressDrawable(drawableNormal);
                }
                break;
        }


        switch (viewHolder.tvInputDesc.getText() + "") {
            case "募资中":
                viewHolder.tvInputDesc.setTextColor(Color.parseColor("#4285f4"));
                viewHolder.itemInvestmentBought.setText("认购");
                viewHolder.itemInvestmentBought.setTextColor(Color.WHITE);
                viewHolder.itemInvestmentBought.setBackgroundResource(R.drawable.shape_bought);
                break;
            case "锚定中":
                viewHolder.tvInputDesc.setTextColor(Color.parseColor("#FF6E0E"));
                viewHolder.itemInvestmentBought.setText("认购");
                viewHolder.itemInvestmentBought.setTextColor(Color.WHITE);
                viewHolder.itemInvestmentBought.setBackgroundResource(R.drawable.shape_bought);
                break;
            default:
                viewHolder.tvInputDesc.setTextColor(Color.GRAY);
                viewHolder.itemInvestmentBought.setBackgroundResource(R.drawable.shape_bank);
                viewHolder.itemInvestmentBought.setText("去看看");
                viewHolder.itemInvestmentBought.setTextColor(Color.GRAY);
                break;
        }


        return convertView;
    }

    class ViewHolder {
        TextView tvItemBrief, tvItemName, tvInputLeader, tvInputFounder, tvInputHatch, tvInputDesc, tvInputRate;
        MyCircleImage userIcon;
        ProgressBar pb;
        ImageView itemInvestmentImg;
        Button itemInvestmentBought;

        public ViewHolder(View v) {

            tvItemBrief = (TextView) v.findViewById(R.id.item_investment_brief);
            tvItemName = (TextView) v.findViewById(R.id.item_investment_name);
            tvInputLeader = (TextView) v.findViewById(R.id.tv_input_lead);
            tvInputFounder = (TextView) v.findViewById(R.id.tv_input_founder);
            tvInputDesc = (TextView) v.findViewById(R.id.tv_input_desc);
            tvInputHatch = (TextView) v.findViewById(R.id.tv_input_hatch);
            tvInputRate = (TextView) v.findViewById(R.id.tv_input_rate);
            userIcon = (MyCircleImage) v.findViewById(R.id.item_investment_user_icon);
            itemInvestmentBought = (Button) v.findViewById(R.id.item_investment_buy);
            itemInvestmentImg = (ImageView) v.findViewById(R.id.item_investment_img);
            pb = (ProgressBar) v.findViewById(R.id.progress_bar);
        }
    }
}
