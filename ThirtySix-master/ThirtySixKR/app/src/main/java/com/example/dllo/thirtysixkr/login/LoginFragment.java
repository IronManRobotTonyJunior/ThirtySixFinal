package com.example.dllo.thirtysixkr.login;

import android.graphics.Color;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.dllo.thirtysixkr.R;
import com.example.dllo.thirtysixkr.base.BaseFragment;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;

public class LoginFragment extends BaseFragment implements View.OnClickListener {

    private TextInputLayout etAccountLayout;
    private TextInputLayout etPasswordLayout;
    private TextInputEditText etAccount;
    private TextInputEditText etPassword;
    private CheckBox cbEye;
    private ImageView imgDeleteAccount;
    private ImageView imgDeletePassword;
    private Button btnLogin;
    private ImageView loginWeiBo;
    private ImageView loginQQ;

    @Override
    protected int setLayout() {
        return R.layout.fragment_login;


    }

    @Override
    protected void initView() {
        RelativeLayout account = bindView(R.id.login_account);
        RelativeLayout password = bindView(R.id.login_password);
        btnLogin = bindView(R.id.login_btn_login);
        etAccountLayout = (TextInputLayout) account.findViewById(R.id.login_til);
        etPasswordLayout = (TextInputLayout) password.findViewById(R.id.login_til);
        etAccount = (TextInputEditText) account.findViewById(R.id.login_et);
        etPassword = (TextInputEditText) password.findViewById(R.id.login_et);
        cbEye = (CheckBox) password.findViewById(R.id.login_cb_eye);
        imgDeleteAccount = (ImageView) account.findViewById(R.id.login_img_delete_input);
        imgDeletePassword = (ImageView) password.findViewById(R.id.login_img_delete_input);
        loginQQ = bindView(R.id.login_qq_login);
        loginWeiBo = bindView(R.id.login_weibo_login);


    }

    @Override
    protected void initData() {
        etAccountLayout.setHint("手机号/邮箱");
        etAccount.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        etAccount.setHintTextColor(Color.parseColor("#9DA5AE"));
        etPasswordLayout.setHint("密码");
        etPassword.setHintTextColor(Color.parseColor("#9DA5AE"));
        loginQQ.setOnClickListener(this);
        loginWeiBo.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        imgDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etAccount.setText("");
            }
        });
        imgDeletePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etPassword.setText("");
            }
        });
        etPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    cbEye.setVisibility(View.VISIBLE);
                } else {
                    cbEye.setVisibility(View.GONE);
                }
            }
        });
        cbEye.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    etPassword.setSelection(etPassword.getText().length());
                } else {
                    etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    etPassword.setSelection(etPassword.getText().length());

                }
            }
        });
        etAccount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                setVisibility(s, imgDeleteAccount, etPassword);
            }

        });
        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                setVisibility(s, imgDeletePassword, etAccount);
            }
        });
    }

    private void setVisibility(Editable s, ImageView currentImg, TextInputEditText otherET) {
        if (s.length() > 0) {
            currentImg.setVisibility(View.VISIBLE);
            if (otherET.getText().length() > 0) {
                btnLogin.setEnabled(true);
            } else {
                btnLogin.setEnabled(false);
            }
        } else {
            currentImg.setVisibility(View.GONE);
            btnLogin.setEnabled(false);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_qq_login:
                Platform qq = ShareSDK.getPlatform(QQ.NAME);
                qq.SSOSetting(false);  //设置false表示使用SSO授权方式
                qq.showUser(null);
                break;
            case R.id.login_weibo_login:
                Platform weibo = ShareSDK.getPlatform(SinaWeibo.NAME);
                weibo.SSOSetting(false);  //设置false表示使用SSO授权方式
                weibo.showUser(null);
                break;
            case R.id.login_btn_login:
                Toast.makeText(mContext, "登啥啊 emoji XXX", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}

