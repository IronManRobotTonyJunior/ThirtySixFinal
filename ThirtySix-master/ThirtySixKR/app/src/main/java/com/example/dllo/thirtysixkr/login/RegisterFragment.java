package com.example.dllo.thirtysixkr.login;

import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.dllo.thirtysixkr.R;
import com.example.dllo.thirtysixkr.base.BaseFragment;

import java.util.HashMap;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;

public class RegisterFragment extends BaseFragment implements View.OnClickListener {

    private TextInputLayout etTelLayout;
    private TextInputEditText etTel;
    private ImageView imgInputDelete;
    private Button btnRegist;

    @Override
    protected int setLayout() {
        return R.layout.fragment_register;
    }

    @Override
    protected void initView() {
        RelativeLayout registerTelPhone = bindView(R.id.register_et);
        etTelLayout = bindView(R.id.login_til, registerTelPhone);
        etTel = bindView(R.id.login_et, registerTelPhone);
        imgInputDelete = bindView(R.id.login_img_delete_input, registerTelPhone);
        btnRegist = bindView(R.id.register_btn_send);


    }

    @Override
    protected void initData() {
        etTelLayout.setHint("手机号");
        imgInputDelete.setOnClickListener(this);
        btnRegist.setOnClickListener(this);
        etTel.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_PHONE);
        etTel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    imgInputDelete.setVisibility(View.VISIBLE);
                } else {
                    imgInputDelete.setVisibility(View.GONE);
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_img_delete_input:
                etTel.setText("");
                break;
            case R.id.register_btn_send:
                //打开注册页面
                RegisterPage registerPage = new RegisterPage();
                registerPage.setRegisterCallback(new EventHandler() {
                    public void afterEvent(int event, int result, Object data) {
                        // 解析注册结果
                        if (result == SMSSDK.RESULT_COMPLETE) {
                            @SuppressWarnings("unchecked")
                            HashMap<String, Object> phoneMap = (HashMap<String, Object>) data;
                            String country = (String) phoneMap.get("country");
                            String phone = (String) phoneMap.get("phone");
                        }
                    }
                });
                registerPage.show(mContext);
                break;
        }
    }
}
