package com.example.dllo.thirtysixkr.login;

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
import com.example.dllo.thirtysixkr.base.BaseActivity;

public class SettingPasswordActivity extends BaseActivity {
    private TextInputLayout etVerificationLayout;
    private TextInputEditText etVertification;
    private ImageView imgInputDeleteVertification;
    private TextInputLayout etPasswordLayout;
    private TextInputEditText etPassword;
    private ImageView imgInputDeletePassword;
    private TextInputLayout etCheckedLayout;
    private TextInputEditText etChecked;
    private ImageView imgInputDeleteChecked;
    private CheckBox cbEyePassword;
    private CheckBox cbEyeChecked;
    private Button mBtn;

    @Override
    protected int setLayout() {
        return R.layout.activity_setting_password;
    }

    @Override
    protected void initView() {
        RelativeLayout linearLayoutVerification = bindView(R.id.verification_code);
        RelativeLayout linearLayoutPassword = bindView(R.id.password_setting);
        RelativeLayout linearLayoutChecked = bindView(R.id.password_checked);
        etVerificationLayout = (TextInputLayout) linearLayoutVerification.findViewById(R.id.login_til);
        etPasswordLayout = (TextInputLayout) linearLayoutPassword.findViewById(R.id.login_til);
        etCheckedLayout = (TextInputLayout) linearLayoutChecked.findViewById(R.id.login_til);
        etVertification = (TextInputEditText) linearLayoutVerification.findViewById(R.id.login_et);
        etPassword = (TextInputEditText) linearLayoutPassword.findViewById(R.id.login_et);
        etChecked = (TextInputEditText) linearLayoutChecked.findViewById(R.id.login_et);
        imgInputDeleteVertification = (ImageView) linearLayoutVerification.findViewById(R.id.login_img_delete_input);
        imgInputDeletePassword = (ImageView) linearLayoutPassword.findViewById(R.id.login_img_delete_input);
        imgInputDeleteChecked = (ImageView) linearLayoutChecked.findViewById(R.id.login_img_delete_input);

        cbEyePassword = (CheckBox) linearLayoutPassword.findViewById(R.id.login_cb_eye);
        cbEyeChecked = (CheckBox) linearLayoutChecked.findViewById(R.id.login_cb_eye);

        // 设置验证码不隐藏
        etVertification.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

    }


    @Override
    protected void initData() {
        // 三个EditText的删除文本键
        imgInputDeleteVertification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etVertification.setText("");
            }
        });
        imgInputDeletePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etPassword.setText("");
            }
        });
        imgInputDeleteChecked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etChecked.setText("");
            }
        });

        //设置隐藏字符
        etVerificationLayout.setHint("手机号验证码");
        etPasswordLayout.setHint("密码(长度6-16位,支持数字,字母,字符)");
        etCheckedLayout.setHint("输入密码");



        mBtn = bindView(R.id.complete_regist);

        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etChecked.getText().equals(etPassword.getText())) {
                    Toast.makeText(SettingPasswordActivity.this, "已成功注册", Toast.LENGTH_SHORT).show();
                } else {
                    etCheckedLayout.setError("两次密码输入不相同");
                    etCheckedLayout.setErrorEnabled(true);
                }

            }
        });
        etPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    cbEyePassword.setVisibility(View.VISIBLE);
                    cbEyeChecked.setVisibility(View.GONE);
                } else {
                    cbEyePassword.setVisibility(View.GONE);
                    cbEyeChecked.setVisibility(View.VISIBLE);
                }
            }
        });
        cbEyePassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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
        cbEyeChecked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    etChecked.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    etChecked.setSelection(etChecked.getText().length());
                } else {
                    etChecked.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    etChecked.setSelection(etChecked.getText().length());
                }
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
                setVisibility(s, imgInputDeletePassword);
            }
        });
        etChecked.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                setVisibility(s, imgInputDeleteChecked);
            }
        });

    }

    private void setVisibility(Editable s, ImageView currentImg) {
        if (s.length() > 0) {
            currentImg.setVisibility(View.VISIBLE);
            if (etPassword.getText().length() > 6 && etChecked.getText().length() > 6) {
                mBtn.setEnabled(true);
            } else {
                mBtn.setEnabled(false);
            }
        } else {
            currentImg.setVisibility(View.GONE);
            mBtn.setEnabled(false);
        }
    }
}
