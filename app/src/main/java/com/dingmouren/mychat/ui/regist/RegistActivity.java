package com.dingmouren.mychat.ui.regist;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.dingmouren.mychat.NimPreferences;
import com.dingmouren.mychat.R;
import com.dingmouren.mychat.ui.login.LoginActivity;
import com.dingmouren.mychat.ui.main.MainActivity;
import com.netease.nim.uikit.api.NimUIKit;


/**
 * Created by Administrator on 2018/4/23.
 * 注册界面
 */

public class RegistActivity extends AppCompatActivity {
    private static final String TAG = "RegistActivity";
    private ImageView mImgArrowBack;
    private EditText mEditAccount;
    private EditText mEditNickName;
    private EditText mEditPass;
    private Button mBtnRegist;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);

        initView();

        initListener();
    }


    private void initView() {
        mImgArrowBack = findViewById(R.id.img_arrow_back);
        mEditAccount = findViewById(R.id.edit_account);
        mEditNickName = findViewById(R.id.edit_nick_name);
        mEditPass = findViewById(R.id.edit_pass);
        mBtnRegist  = findViewById(R.id.btn_regist);
    }

    private void initListener() {
        mImgArrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mBtnRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registNewAccount();
            }
        });
    }

    /**
     * 注册新账号
     */
    private void registNewAccount() {
        String account = mEditAccount.getText().toString().trim();
        String nickName = mEditNickName.getText().toString().trim();
        String pass = mEditPass.getText().toString().trim();
        if (TextUtils.isEmpty(account) || TextUtils.isEmpty(nickName) || TextUtils.isEmpty(pass)) {
            Toast.makeText(RegistActivity.this,"填写完整",Toast.LENGTH_SHORT).show();
            return;
        }

        HttpRegistClient.getInstance().register(account, nickName, pass, new HttpRegistClient.HttpClientCallback<String>(){
            // {"code":200,"info":{"token":"4565206","accid":"test_8","name":""}}
            @Override
            public void onSuccess(String response) {
                Log.e(TAG,"注册成功:"+response);
                String account = "";
                String token = "";
                /*解析json*/
                JSONObject jsonObject = (JSONObject) JSONObject.parse(response);
                JSONObject infoObject = (JSONObject) JSONObject.parse(jsonObject.getString("info"));
                account = infoObject.getString("accid");
                token = infoObject.getString("token");
                Log.e(TAG,"注册成功 account:"+account +" token:"+token);

                 /*本地存储用户账户信息，用于登录的*/
                NimPreferences.saveUserAccount(account);
                NimPreferences.saveUserToken(token);

                /*设置当前应用的用户*/
                NimUIKit.setAccount(account);

                startActivity(new Intent(RegistActivity.this,MainActivity.class));
                finish();

            }

            @Override
            public void onFailed(int code, String errorMsg) {
                Log.e(TAG,"注册失败！code:"+code+"  errorMsg:"+errorMsg);
            }
        });

    }

}
