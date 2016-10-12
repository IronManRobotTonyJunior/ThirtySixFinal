package com.example.dllo.thirtysixkr.web;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.dllo.thirtysixkr.base.BaseActivity;
import com.example.dllo.thirtysixkr.R;
import com.example.dllo.thirtysixkr.news.FormatTime;
import com.example.dllo.thirtysixkr.tools.url.Kr36Url;
import com.example.dllo.thirtysixkr.tools.webrequest.SendGetRequest;
import com.example.dllo.thirtysixkr.web.richtext.HtmlTextView;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.OnekeyShareTheme;

public class WebDetailActivity extends BaseActivity implements View.OnClickListener {

    private HtmlTextView tv;
    private TextView tvName;
    private TextView tvDetail;
    private TextView tvTime;
    private TextView tvTitle;
    private ImageView circleAuthorIcon;
    private String time;
    private String title;
    private LinearLayout llAuthorDetail;
    private PopupWindow popupWindowUp;
    private AuthorDetailBean.DataBean bean;
    private TextView tvArticles;
    private TextView tvView;
    private ImageView ivFirst;
    private ImageView ivSecond;
    private ImageView ivThird;
    private TextView tvTitleFirst;
    private TextView tvTitleSecond;
    private TextView tvTitleThird;
    private PopupWindow popupWindowDown;
    private View llAuthorLastFirst;
    private View llAuthorLastSecond;
    private View llAuthorLastThird;
    private ImageView ivOther;
    private FrameLayout div;
    private ObservableScrollView scrollView;
    private ImageView ivDownUp;
    private ImageView imgBackFrame;
    private ImageView imgCommentFrame;
    private ImageView rbFavoriteFrame;
    private ImageView imgShareFrame;
    private ImageView imgMoreFrame;
    private String url;
    private TextView tvWechat;
    private TextView tvMoment;
    private TextView tvWeiBo;
    private TextView tvQQ;
    private TextView tvALi;
    private TextView tvLiving;
    private TextView tvLink;
    private Button btnShareCancle;
    private RelativeLayout webShareRl;
    private RadioButton tvLargest;
    private RadioButton tvBig;
    private RadioButton tvMiddle;
    private RadioButton tvTiny;
    private SeekBar sbLight;
    private Button btnAward;
    private Dialog dialogMore;
    private Dialog dialogShare;
    private RelativeLayout rlMore;
    private final static float TEXT_SIZE_TINY = 10;
    private final static float TEXT_SIZE_MIDDLE = 15;
    private final static float TEXT_SIZE_BIG = 20;
    private final static float TEXT_SIZE_LARGEST = 25;
    private SharedPreferences.Editor spET;
    private SharedPreferences sp;
    private float textSize;
    private RadioGroup radioGroup;
    private Platform playform;

    @Override
    protected int setLayout() {
//        struct();
        return R.layout.activity_web_detail;
    }

    @Override
    protected void initView() {


        Intent intent = getIntent();

        String urlFeedId = intent.getStringExtra("web");

        url = Kr36Url.detailWeb(urlFeedId);

        String authorUrl = Kr36Url.authorDetails(urlFeedId);

        time = FormatTime.formatTime(intent.getLongExtra("time", 0));

        title = intent.getStringExtra("title");

        div = bindView(R.id.web_div);

        // 下方弹出框
        imgBackFrame = (ImageView) div.findViewById(R.id.dialog_web_img_back);

        imgCommentFrame = (ImageView) div.findViewById(R.id.dialog_web_img_comment);

        rbFavoriteFrame = (ImageView) div.findViewById(R.id.dialog_web_radio_favorite);

        imgShareFrame = (ImageView) div.findViewById(R.id.dialog_web_img_share);

        imgMoreFrame = (ImageView) div.findViewById(R.id.dialog_web_img_more);

        imgBackFrame.setOnClickListener(WebDetailActivity.this);
        imgCommentFrame.setOnClickListener(WebDetailActivity.this);
        rbFavoriteFrame.setOnClickListener(WebDetailActivity.this);
        imgShareFrame.setOnClickListener(WebDetailActivity.this);
        imgMoreFrame.setOnClickListener(WebDetailActivity.this);

        //  结束

        scrollView = bindView(R.id.scroll_web);

        // 作者简介的横布局 (设点击事件 下弹Dialog)
        llAuthorDetail = bindView(R.id.web_author_details_ll);

        llAuthorDetail.setOnClickListener(this);

        // 名字头像点进作者详情
        tvName = bindView(R.id.web_tv_author_name);

        tvName.setOnClickListener(this);

        tvDetail = bindView(R.id.web_tv_author_detail);

        tvTime = bindView(R.id.web_tv_time);

        tvTitle = bindView(R.id.web_tv_title);

        circleAuthorIcon = bindView(R.id.ci_author_icon);

        ivDownUp = bindView(R.id.web_iv_icon_down_up);

        tv = bindView(R.id.tv_detail);
        sp = getSharedPreferences("TextSize", MODE_PRIVATE);
        spET = sp.edit();
        textSize = sp.getFloat("textSize", TEXT_SIZE_MIDDLE);


//        tv.setMovementMethod(ScrollingMovementMethod.getInstance());
//
//        tv.setMovementMethod(LinkMovementMethod.getInstance());

        SendGetRequest.sendGetRequest(url, WebDetailBean.class, new SendGetRequest.OnResponseListener<WebDetailBean>() {
            @Override
            public void onResponse(WebDetailBean response) {
                tv.setTextSize(textSize);
                tv.setHtmlFromString(response.getData().getContent());
            }

            @Override
            public void onError() {

            }
        });

        SendGetRequest.sendGetRequest(authorUrl, AuthorDetailBean.class, new SendGetRequest.OnResponseListener<AuthorDetailBean>() {
            @Override
            public void onResponse(AuthorDetailBean response) {
                llAuthorDetail.setVisibility(View.VISIBLE);
                bean = response.getData();
                tvDetail.setText(response.getData().getBrief());
                tvName.setText(response.getData().getName());
                tvTitle.setText(title);

                Glide.with(WebDetailActivity.this).load(response.getData().getAvatar()).asBitmap().centerCrop().into(new BitmapImageViewTarget(circleAuthorIcon) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        super.setResource(resource);
                        RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        circleAuthorIcon.setImageDrawable(circularBitmapDrawable);
                    }
                });

                tvTime.setText(time);
                circleAuthorIcon.setOnClickListener(WebDetailActivity.this);
            }

            @Override
            public void onError() {

            }
        });


    }

    protected void initData() {
        initPopupWindowUp();
        initShareDialog();
        initMoreDialog();
        scrollView.setOnScrollChangedCallback(new ObservableScrollView.OnScrollChangedCallback() {
            @Override
            public void onScroll(int dx, int dy) {
                Log.d("WebDetailActivity", "滑动");
                // 下滑
                if (dy > 5) {
                    if (div.getVisibility() == View.VISIBLE) {
                        div.setAnimation(AnimationUtils.loadAnimation(WebDetailActivity.this, R.anim.actionsheet_dialog_out));
                        div.setVisibility(View.GONE);
                    }
                }
                // 上滑
                if (dy < -5) {
                    if (div.getVisibility() == View.GONE) {
                        div.setAnimation(AnimationUtils.loadAnimation(WebDetailActivity.this, R.anim.actionsheet_dialog_in));
                        div.setVisibility(View.VISIBLE);

                    }
                }
            }
        });


    }


//    private void initPopupWindowDown() {
//        popupWindowDown = new PopupWindow(this);
//
//        View view = LayoutInflater.from(this).inflate(R.layout.dialog_web_detail, null);
//
//        popupWindowDown.setContentView(view);
//
//        popupWindowDown.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
//
//        popupWindowDown.setAnimationStyle(R.style.ActionSheetDialogAnimation);
//        popupWindowDown.showAtLocation(, Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL,0, 0);
//
//
//        ImageView imgBack = (ImageView) view.findViewById(R.id.dialog_web_img_back);
//        ImageView imgComment = (ImageView) view.findViewById(R.id.dialog_web_img_comment);
//        RadioButton radioButton = (RadioButton) view.findViewById(R.id.dialog_web_radio_favorite);
//        ImageView imgShare = (ImageView) view.findViewById(R.id.dialog_web_img_share);
//        ImageView imgMore = (ImageView) view.findViewById(R.id.dialog_web_img_more);
//        imgBack.setOnClickListener(this);
//        imgComment.setOnClickListener(this);
//        radioButton.setOnClickListener(this);
//        imgShare.setOnClickListener(this);
//        imgMore.setOnClickListener(this);
//
//    private class MyWebViewClient extends WebViewClient {
//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            view.loadUrl(url);
//            return true;
//        }
//
//
//
//    }
//
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if ((keyCode == KeyEvent.KEYCODE_BACK) && wb.canGoBack()) {
//            wb.goBack(); //goBack()表示返回WebView的上一页面
//            return true;
//        }
//        return false;
//    }
//    }


//        public static void struct() {
//        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
//                .detectDiskReads().detectDiskWrites().detectNetwork() or
//                .detectAll()
//                 for
//                 all
//                 detectable
//                 problems
//                .penaltyLog().build());
//        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
//                .detectLeakedSqlLiteObjects() // 探测SQLite数据库操作
//                .penaltyLog() // 打印logcat
//                .penaltyDeath().build());
//    }
    private void initPopupWindowUp() {
        popupWindowUp = new PopupWindow(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        View view = LayoutInflater.from(this).inflate(R.layout.author_popup_window_up, null);
        popupWindowUp.setContentView(view);
        tvArticles = (TextView) view.findViewById(R.id.tv_author_popup_articles);
        tvView = (TextView) view.findViewById(R.id.tv_author_popup_view);
        ivFirst = (ImageView) view.findViewById(R.id.iv_author_news_first);
        ivSecond = (ImageView) view.findViewById(R.id.iv_author_news_second);
        ivThird = (ImageView) view.findViewById(R.id.iv_author_news_third);
        tvTitleFirst = (TextView) view.findViewById(R.id.tv_author_title_first);
        tvTitleSecond = (TextView) view.findViewById(R.id.tv_author_title_second);
        tvTitleThird = (TextView) view.findViewById(R.id.tv_author_title_third);
        llAuthorLastFirst = view.findViewById(R.id.author_last_first);

        llAuthorLastSecond = view.findViewById(R.id.author_last_second);

        llAuthorLastThird = view.findViewById(R.id.author_last_third);

        ivOther = (ImageView) view.findViewById(R.id.tv_author_popup_other);

    }

    private void initShareDialog() {
        dialogShare = new Dialog(this, R.style.ActionSheetDialogStyle);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_web_share, null);

        // 分享界面的8个按钮
        webShareRl = (RelativeLayout) view.findViewById(R.id.web_share_rl);
        tvWechat = (TextView) view.findViewById(R.id.share_wechat);
        tvMoment = (TextView) view.findViewById(R.id.share_moment);
        tvWeiBo = (TextView) view.findViewById(R.id.share_weibo);
        tvQQ = (TextView) view.findViewById(R.id.share_qq);
        tvALi = (TextView) view.findViewById(R.id.share_alipay);
        tvLiving = (TextView) view.findViewById(R.id.share_living);
        tvLink = (TextView) view.findViewById(R.id.share_link);
        btnShareCancle = (Button) view.findViewById(R.id.share_cancel);
        dialogShare.setContentView(view);
        Window win = dialogShare.getWindow();
        win.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        win.setAttributes(lp);
    }

    private void initMoreDialog() {
        dialogMore = new Dialog(this, R.style.ActionSheetDialogStyle);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_web_more, null);


        // 更多界面
        radioGroup = (RadioGroup) view.findViewById(R.id.more_radio_group);
        btnAward = (Button) view.findViewById(R.id.more_award);
        sbLight = (SeekBar) view.findViewById(R.id.more_light);
        tvTiny = (RadioButton) view.findViewById(R.id.more_text_size_tiny);
        tvMiddle = (RadioButton) view.findViewById(R.id.more_text_size_middle);
        tvBig = (RadioButton) view.findViewById(R.id.more_text_size_big);
        tvLargest = (RadioButton) view.findViewById(R.id.more_text_size_largest);
        rlMore = (RelativeLayout) view.findViewById(R.id.web_more);
        dialogMore.setContentView(view);
        Window win = dialogMore.getWindow();
        win.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        win.setAttributes(lp);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // 上方作者详情里的点击事件
            case R.id.web_author_details_ll:
                if (popupWindowUp.isShowing()) {
                    popupWindowUp.dismiss();
                    ivDownUp.setImageResource(R.mipmap.icon_down);
                } else {
                    popupWindowUp.setAnimationStyle(R.style.popwin_anim_style);
                    popupWindowUp.showAsDropDown(llAuthorDetail);
                    ivDownUp.setImageResource(R.mipmap.icon_up);
                    if (tv.getVisibility() == View.VISIBLE) {
                        tvArticles.setText(bean.getTotalCount() + "篇");
                        tvView.setText(bean.getTotalView() / 10000 + "万");
                        llAuthorLastFirst.setOnClickListener(this);
                        llAuthorLastSecond.setOnClickListener(this);
                        llAuthorLastThird.setOnClickListener(this);
                        ivOther.setOnClickListener(this);
                        switch (bean.getLatestArticle().size()) {
                            case 3:
                                tvTitleThird.setText(bean.getLatestArticle().get(2).getTitle());
                                Glide.with(this).load(bean.getLatestArticle().get(2).getFeatureImg()).into(ivThird);
                            case 2:
                                Glide.with(this).load(bean.getLatestArticle().get(1).getFeatureImg()).into(ivSecond);
                                tvTitleSecond.setText(bean.getLatestArticle().get(1).getTitle());
                                if (bean.getLatestArticle().size() == 2) {
                                    llAuthorLastThird.setVisibility(View.GONE);
                                }
                            case 1:
                                Glide.with(this).load(bean.getLatestArticle().get(0).getFeatureImg()).into(ivFirst);
                                tvTitleFirst.setText(bean.getLatestArticle().get(0).getTitle());
                                if (bean.getLatestArticle().size() == 1) {
                                    llAuthorLastSecond.setVisibility(View.GONE);
                                    llAuthorLastThird.setVisibility(View.GONE);
                                }
                        }
                    }
                }
                break;
            case R.id.web_tv_author_name:
                break;
            case R.id.ci_author_icon:
                break;
            case R.id.tv_author_popup_other:
                popupWindowUp.dismiss();
                ivDownUp.setImageResource(R.mipmap.icon_down);
                break;
            case R.id.author_last_first:
                if (bean.getLatestArticle().get(0).getTitle().equals(title)) {
                    Toast.makeText(this, "您正在看此篇文章,可以看看其他感兴趣的文章", Toast.LENGTH_SHORT).show();
                } else {
                    intentOtherArticle(0);
                }
                break;
            case R.id.author_last_second:
                if (bean.getLatestArticle().get(1).getTitle().equals(title)) {
                    Toast.makeText(this, "您正在看此篇文章,可以看看其他感兴趣的文章", Toast.LENGTH_SHORT).show();
                } else {
                    intentOtherArticle(1);
                }
                break;
            case R.id.author_last_third:
                if (bean.getLatestArticle().get(2).getTitle().equals(title)) {
                    Toast.makeText(this, "您正在看此篇文章,可以看看其他感兴趣的文章", Toast.LENGTH_SHORT).show();
                } else {
                    intentOtherArticle(2);
                }
                break;

            // 下方的Frame的点击事件
            case R.id.dialog_web_img_back:
                finish();
                break;
            case R.id.dialog_web_img_comment:

                break;
            case R.id.dialog_web_radio_favorite:

                break;
            case R.id.dialog_web_img_share:
                dialogShare.show();
                webShareRl.setOnClickListener(this);
                btnShareCancle.setOnClickListener(this);
                tvWechat.setOnClickListener(this);
                tvMoment.setOnClickListener(this);
                tvWeiBo.setOnClickListener(this);
                tvQQ.setOnClickListener(this);
                tvALi.setOnClickListener(this);
                tvLiving.setOnClickListener(this);
                tvLink.setOnClickListener(this);
                break;
            case R.id.dialog_web_img_more:
                dialogMore.show();
                radioGroup.check(sp.getInt("radioButtonId", R.id.more_text_size_middle));
                rlMore.setOnClickListener(this);
                btnAward.setOnClickListener(this);
                tvTiny.setOnClickListener(this);
                tvMiddle.setOnClickListener(this);
                tvBig.setOnClickListener(this);
                tvLargest.setOnClickListener(this);
                sbLight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        WebDetailActivity.this.setScreenBrightness((float) seekBar
                                .getProgress() / 100);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
                break;
            // 分享的点击事件
            case R.id.web_share_rl:
                dialogShare.dismiss();
                break;
            case R.id.share_wechat:
                playform = ShareSDK.getPlatform("Wechat");
                showShare(this,playform.getName(),true);
                dialogShare.dismiss();
                break;
            case R.id.share_moment:
                playform = ShareSDK.getPlatform("WechatMoments");
                showShare(this,playform.getName(),true);
                dialogShare.dismiss();
                break;
            case R.id.share_weibo:
                playform = ShareSDK.getPlatform("SinaWeibo");
                showShare(this,playform.getName(),true);
                dialogShare.dismiss();
                break;
            case R.id.share_qq:
                playform = ShareSDK.getPlatform("QQ");
                showShare(this,playform.getName(),true);
                dialogShare.dismiss();
                break;
            case R.id.share_alipay:
                playform = ShareSDK.getPlatform("Alipay");
                showShare(this,playform.getName(),true);
                dialogShare.dismiss();
                break;
            case R.id.share_living:
                playform = ShareSDK.getPlatform("Wechat");
                showShare(this,playform.getName(),true);
                dialogShare.dismiss();
                break;
            case R.id.share_link:
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setPrimaryClip(ClipData.newPlainText("url", url));
                Toast.makeText(this, "复制成功", Toast.LENGTH_SHORT).show();
                dialogShare.dismiss();
                break;
            case R.id.share_cancel:
                dialogShare.dismiss();
                break;

            // 更多的按钮点击事件
            case R.id.more_award:
                Toast.makeText(this, "滚吧快,你咋那么有钱", Toast.LENGTH_SHORT).show();
                break;
            case R.id.more_text_size_tiny:
                spET.putFloat("textSize", TEXT_SIZE_TINY);
                tv.setTextSize(TEXT_SIZE_TINY);
                spET.putInt("radioButtonId", R.id.more_text_size_tiny);
                tvTiny.setChecked(true);
                break;
            case R.id.more_text_size_middle:
                spET.putFloat("textSize", TEXT_SIZE_MIDDLE);
                tv.setTextSize(TEXT_SIZE_MIDDLE);
                spET.putInt("radioButtonId", R.id.more_text_size_middle);
                tvMiddle.setChecked(true);
                break;
            case R.id.more_text_size_big:
                spET.putFloat("textSize", TEXT_SIZE_BIG);
                tv.setTextSize(TEXT_SIZE_BIG);
                spET.putInt("radioButtonId", R.id.more_text_size_big);
                tvBig.setChecked(true);
                break;
            case R.id.more_text_size_largest:
                spET.putFloat("textSize", TEXT_SIZE_LARGEST);
                tv.setTextSize(TEXT_SIZE_LARGEST);
                spET.putInt("radioButtonId", R.id.more_text_size_largest);
                tvLargest.setChecked(true);
                break;
            case R.id.web_more:
                dialogMore.dismiss();
                break;
        }
        spET.commit();

    }

    private void setScreenBrightness(float num) {    // 0 ~ 1表示亮度
        WindowManager.LayoutParams layoutParams = super.getWindow().getAttributes();    // 取得屏幕的属性
        layoutParams.screenBrightness = num;    // 设置屏幕亮度
        super.getWindow().setAttributes(layoutParams);    // 重新设置窗口的属性
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.activity_out);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (popupWindowUp.isShowing()) {
            popupWindowUp.dismiss();
        }
        if (dialogShare.isShowing()) {
            dialogShare.dismiss();
        }
        if (dialogMore.isShowing()) {
            dialogMore.dismiss();
        }
    }

    private void intentOtherArticle(int i) {
        Intent intent = new Intent(this, WebDetailActivity.class);
        intent.putExtra("web", bean.getLatestArticle().get(i).getPostId() + "");
        intent.putExtra("title", bean.getLatestArticle().get(i).getTitle());
        intent.putExtra("time", bean.getLatestArticle().get(i).getPublishTime());
        startActivity(intent);
        overridePendingTransition(R.anim.activity_in, 0);
        popupWindowUp.dismiss();
    }
    public static void showShare(Context context, String platformToShare, boolean showContentEdit) {
        OnekeyShare oks = new OnekeyShare();
        oks.setSilent(!showContentEdit);
        if (platformToShare != null) {
            oks.setPlatform(platformToShare);
        }
        //ShareSDK快捷分享提供两个界面第一个是九宫格 CLASSIC  第二个是SKYBLUE
        oks.setTheme(OnekeyShareTheme.CLASSIC);
        // 令编辑页面显示为Dialog模式
        oks.setDialogMode();
        // 在自动授权时可以禁用SSO方式
        oks.disableSSOWhenAuthorize();
        //oks.setAddress("12345678901"); //分享短信的号码和邮件的地址
        oks.setTitle("ShareSDK--Title");
        oks.setTitleUrl("http://mob.com");
        oks.setText("ShareSDK--文本");
        //oks.setImagePath("/sdcard/test-pic.jpg");  //分享sdcard目录下的图片
        oks.setImageUrl(randomPic()[0]);
        oks.setUrl("http://www.mob.com"); //微信不绕过审核分享链接
        //oks.setFilePath("/sdcard/test-pic.jpg");  //filePath是待分享应用程序的本地路劲，仅在微信（易信）好友和Dropbox中使用，否则可以不提供
        oks.setComment("分享"); //我对这条分享的评论，仅在人人网和QQ空间使用，否则可以不提供
        oks.setSite("ShareSDK");  //QZone分享完之后返回应用时提示框上显示的名称
        oks.setSiteUrl("http://mob.com");//QZone分享参数
        oks.setVenueName("ShareSDK");
        oks.setVenueDescription("This is a beautiful place!");
        // 将快捷分享的操作结果将通过OneKeyShareCallback回调
        //oks.setCallback(new OneKeyShareCallback());
        // 去自定义不同平台的字段内容
        //oks.setShareContentCustomizeCallback(new ShareContentCustomizeDemo());
        // 在九宫格设置自定义的图标
        String label = "ShareSDK";
        View.OnClickListener listener = new View.OnClickListener() {
            public void onClick(View v) {

            }
        };

        // 为EditPage设置一个背景的View
        //oks.setEditPageBackground(getPage());
        // 隐藏九宫格中的新浪微博
        // oks.addHiddenPlatform(SinaWeibo.NAME);

        // String[] AVATARS = {
        // 		"http://99touxiang.com/public/upload/nvsheng/125/27-011820_433.jpg",
        // 		"http://img1.2345.com/duoteimg/qqTxImg/2012/04/09/13339485237265.jpg",
        // 		"http://diy.qqjay.com/u/files/2012/0523/f466c38e1c6c99ee2d6cd7746207a97a.jpg",
        // 		"http://diy.qqjay.com/u2/2013/0422/fadc08459b1ef5fc1ea6b5b8d22e44b4.jpg",
        // 		"http://img1.2345.com/duoteimg/qqTxImg/2012/04/09/13339510584349.jpg",
        // 		"http://diy.qqjay.com/u2/2013/0401/4355c29b30d295b26da6f242a65bcaad.jpg" };
        // oks.setImageArray(AVATARS);              //腾讯微博和twitter用此方法分享多张图片，其他平台不可以

        // 启动分享
        oks.show(context);
    }
    public static String[] randomPic() {
        String url = "http://git.oschina.net/alexyu.yxj/MyTmpFiles/raw/master/kmk_pic_fld/";
        String urlSmall = "http://git.oschina.net/alexyu.yxj/MyTmpFiles/raw/master/kmk_pic_fld/small/";
        String[] pics = new String[] {
                "120.JPG",
                "127.JPG",
                "130.JPG",
                "18.JPG",
                "184.JPG",
                "22.JPG",
                "236.JPG",
                "237.JPG",
                "254.JPG",
                "255.JPG",
                "263.JPG",
                "265.JPG",
                "273.JPG",
                "37.JPG",
                "39.JPG",
                "IMG_2219.JPG",
                "IMG_2270.JPG",
                "IMG_2271.JPG",
                "IMG_2275.JPG",
                "107.JPG"
        };
        int index = (int) (System.currentTimeMillis() % pics.length);
        return new String[] {
                url + pics[index],
                urlSmall + pics[index]
        };
    }


}