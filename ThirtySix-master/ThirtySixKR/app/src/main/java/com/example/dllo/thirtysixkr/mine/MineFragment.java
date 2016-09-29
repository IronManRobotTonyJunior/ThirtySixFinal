package com.example.dllo.thirtysixkr.mine;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dllo.thirtysixkr.BaseFragment;
import com.example.dllo.thirtysixkr.R;

public class MineFragment extends BaseFragment implements View.OnClickListener {

    private TextView tvAccount;
    private TextView tvAttestation;
    private TextView tvArticle;
    private TextView tvCompany;
    private TextView tvFOUN;
    private TextView tvLearn;
    private TextView tvCall;
    private TextView tvCallNumber;
    private View viewCall;
    private RelativeLayout dialog_all;
    private Dialog dialog;


    @Override
    protected int setLayout() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView() {
        View viewOrder = bindView(R.id.mine_item_order);
        View viewAccount = bindView(R.id.mine_item_account);
        View viewAttestation = bindView(R.id.mine_item_attestation);
        View viewArticle = bindView(R.id.mine_item_article);
        View viewCompany = bindView(R.id.mine_item_company);
        View viewFOUN = bindView(R.id.mine_item_FOUN);
        View viewLearn = bindView(R.id.mine_item_learn);
        viewCall = bindView(R.id.mine_item_call);

        ImageView imageViewAccount = bindView(R.id.mine_include_iv, viewAccount);
        ImageView imageViewAttestation = bindView(R.id.mine_include_iv, viewAttestation);
        ImageView imageViewArticle = bindView(R.id.mine_include_iv, viewArticle);
        ImageView imageViewCompany = bindView(R.id.mine_include_iv, viewCompany);
        ImageView imageViewFOUN = bindView(R.id.mine_include_iv, viewFOUN);
        ImageView imageViewLearn = bindView(R.id.mine_include_iv, viewLearn);
        ImageView imageViewCall = bindView(R.id.mine_include_iv, viewCall);
        tvAccount = bindView(R.id.mine_include_tv, viewAccount);
        tvAttestation = bindView(R.id.mine_include_tv, viewAttestation);
        tvArticle = bindView(R.id.mine_include_tv, viewArticle);
        tvCompany = bindView(R.id.mine_include_tv, viewCompany);
        tvFOUN = bindView(R.id.mine_include_tv, viewFOUN);
        tvLearn = bindView(R.id.mine_include_tv, viewLearn);
        tvCall = bindView(R.id.mine_include_tv, viewCall);
        tvCallNumber = bindView(R.id.mine_include_number, viewCall);
        imageViewAccount.setImageResource(R.mipmap.mine_icon_account);
        tvAccount.setText("账号信息");

        imageViewAttestation.setImageResource(R.mipmap.mine_icon_authentication);
        tvAttestation.setText("跟投人认证");

        imageViewArticle.setImageResource(R.mipmap.mine_icon_favorite);
        tvArticle.setText("我收藏的文章");

        imageViewCompany.setImageResource(R.mipmap.mine_icon_company);
        tvCompany.setText("我投资的公司");

        imageViewFOUN.setImageResource(R.mipmap.mine_icon_coupon);
        tvFOUN.setText("我的投资券");

        imageViewLearn.setImageResource(R.mipmap.mine_icon_understand);
        tvLearn.setText("了解股权投资");

        imageViewCall.setImageResource(R.mipmap.mine_icon_hotline);
        tvCall.setText("客服热线");
        tvCallNumber.setText("400-995-3636");
        tvCallNumber.setTextColor(Color.parseColor("#9DA5AF"));


        viewOrder.setOnClickListener(this);
        viewAccount.setOnClickListener(this);
        viewAttestation.setOnClickListener(this);
        viewArticle.setOnClickListener(this);
        viewCompany.setOnClickListener(this);
        viewFOUN.setOnClickListener(this);
        viewLearn.setOnClickListener(this);
        viewCall.setOnClickListener(this);


    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
//        if (dialog != null && dialog.isShowing()) {
//            dialog.dismiss();
//        }
        switch (v.getId()) {
            case R.id.dialog_all:
                dialog.dismiss();
                break;
            case R.id.mine_item_order:
                break;
            case R.id.mine_item_account:
                break;
            case R.id.mine_item_attestation:
                break;
            case R.id.mine_item_article:
                break;
            case R.id.mine_item_company:
                break;
            case R.id.mine_item_FOUN:
                break;
            case R.id.mine_item_learn:
                break;
            case R.id.mine_item_call:
                initDialog();
                break;
            case R.id.popup_call:
                dialog.dismiss();
                Intent intent = new Intent();
                intent.setAction("android.intent.action.CALL");
                intent.setData(Uri.parse("tel:" + "4009953636"));
                startActivity(intent);
                break;
            case R.id.popup_cancel:
                dialog.dismiss();
                break;
            default:
                break;
        }
    }

    private void initPop() {
//        popupWindow = new PopupWindow(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
//        popupWindow.setContentView(view);
//        Button btn_call = bindView(R.id.popup_call,view);
//        Button btn_cancel = bindView(R.id.popup_cancel,view);
//        btn_call.setOnClickListener(this);
//        btn_cancel.setOnClickListener(this);
//        popupWindow.showAsDropDown(viewCall);

    }


    private void initDialog() {
        dialog = new Dialog(mContext, R.style.ActionSheetDialogStyle);
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_window_call, null);
        Button btn_call = bindView(R.id.popup_call, view);
        Button btn_cancel = bindView(R.id.popup_cancel, view);
        dialog_all = bindView(R.id.dialog_all, view);
        dialog.setContentView(view);
        Window win = dialog.getWindow();
        win.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        win.setAttributes(lp);
        dialog.show();
        dialog_all.setOnClickListener(this);
        btn_call.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
//        AlertDialog custom = new AlertDialog.Builder(mContext).create();

//        custom.setView(view);
//        custom.show();
//
//        win.setContentView(view);
//        win.getDecorView().setPadding(0, 0, 0, 0);

//        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        win.setAttributes(lp);
//
//
//
//


    }
}
