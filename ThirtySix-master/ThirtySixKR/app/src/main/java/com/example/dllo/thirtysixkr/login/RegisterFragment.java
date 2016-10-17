package com.example.dllo.thirtysixkr.login;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.dllo.thirtysixkr.R;
import com.example.dllo.thirtysixkr.base.BaseFragment;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.smssdk.EventHandler;
import cn.smssdk.OnSendMessageHandler;

public class RegisterFragment extends BaseFragment implements View.OnClickListener {

    private static final int REQUEST_CODE = 100;
    private TextInputLayout etTelLayout;
    private TextInputEditText etTel;
    private ImageView imgInputDelete;
    private Button btnRegist;
    private EventHandler mHandler;

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
                if (isPhoneNumberValid(etTel.getText().toString())) {
                    //打开注册页面
                    initDialog();
                } else {
                    etTelLayout.setError("请输入正确电话号码");
                    etTelLayout.setErrorEnabled(true);
                }

//                mHandler = new EventHandler(){
//                    @Override
//                    public void afterEvent(final int i, final int i1, final Object o) {
//                        getActivity().runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                if(i1 == -1) {
//                                    if(i == 2) {
//                                        boolean status = ((Boolean)o).booleanValue();
//                                        afterVerificationCodeRequested(status);
//                                    }
//                                }
//                            }
//                        });
//                    }
//                };

                break;
        }
    }
//    private void afterVerificationCodeRequested(boolean smart) {
//        Intent intent = new Intent(mContext,SettingPasswordActivity.class);
//        startActivityForResult(intent, REQUEST_CODE);
//    }


    private void initDialog() {
        AlertDialog.Builder custom = new AlertDialog.Builder(mContext);
        custom.setTitle("确认手机号码");
        custom.setMessage("我们将发送验证码短信到这个号码:\n+86 " + etTel.getText());
        custom.setNegativeButton("好", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                cn.smssdk.SMSSDK.getVerificationCode("+86", etTel.getText().toString().trim(), new OnSendMessageHandler() {
                    @Override
                    public boolean onSendMessage(String s, String s1) {
                        Log.d("RegisterFragment", s);
                        Log.d("RegisterFragment", s1);
                        return false;
                    }
                });
                etTel.setText("");
                Intent intent = new Intent(mContext, SettingPasswordActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                Toast.makeText(mContext, "已发送", Toast.LENGTH_SHORT).show();
            }
        });
        custom.setPositiveButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        custom.show();
    }

//    @Override
//    public void onResume() {
//        SMSSDK.registerEventHandler(this.mHandler);
//    }

    //    @Override
//    public void onDestroy() {
//        SMSSDK.unregisterEventHandler(this.mHandler);
//    }
    private boolean isPhoneNumberValid(String phoneNumber) {
        boolean isValid = false;
        /**
         * valid phone number format;
         */
        String expression1 = "^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{5})$";
        /**
         * valid phone number format;
         */
        String expression2 = "^\\(?(\\d{3})\\)?[- ]?(\\d{4})[- ]?(\\d{4})$";
        CharSequence inputStr = phoneNumber;
        Pattern pattern1 = Pattern.compile(expression1);
        Matcher matcher1 = pattern1.matcher(inputStr);

        Pattern pattern2 = Pattern.compile(expression2);
        Matcher matcher2 = pattern2.matcher(inputStr);
        if (matcher1.matches() || matcher2.matches()) {
            isValid = true;
        }
        return isValid;
    }
}
