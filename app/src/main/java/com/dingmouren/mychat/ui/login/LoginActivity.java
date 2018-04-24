package com.dingmouren.mychat.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dingmouren.mychat.NimPreferences;
import com.dingmouren.mychat.R;
import com.dingmouren.mychat.ui.main.MainActivity;
import com.dingmouren.mychat.ui.regist.RegistActivity;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.LoginInfo;

/**
 * Created by Administrator on 2018/4/19.
 * 登录界面
 */

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    private EditText mEditAccount,mEditPassword;
    private Button mBtnLogin;
    private Button mBtnRegist;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();

        initListener();
    }


    private void initView() {
        mEditAccount = findViewById(R.id.edit_account);
        mEditPassword = findViewById(R.id.edit_password);
        mBtnLogin = findViewById(R.id.btn_login);
        mBtnRegist = findViewById(R.id.btn_regist);
    }

    private void initListener() {
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String account = mEditAccount.getText().toString().trim();
                if (TextUtils.isEmpty(account)) Toast.makeText(LoginActivity.this,"账户名不能为空",Toast.LENGTH_SHORT).show();

                String password = mEditPassword.getText().toString().trim();
                if (TextUtils.isEmpty(password)) Toast.makeText(LoginActivity.this,"密码不能为空",Toast.LENGTH_SHORT).show();

                toLogin(account,password);//登录账户
            }
        });

        mBtnRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegistActivity.class));
            }
        });
    }

    /**
     * 登录账户
     * @param account
     * @param password
     */
    private void toLogin(final String account, String password) {
        NimUIKit.login(new LoginInfo(account, password), new RequestCallback<LoginInfo>() {
            @Override
            public void onSuccess(LoginInfo loginInfo) {
                Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();

                /*本地存储用户账户信息，用于登录的*/
                String accout = loginInfo.getAccount();
                String token = loginInfo.getToken();
                Log.e(TAG,"account:"+account+" token:"+token);
                NimPreferences.saveUserAccount(accout);
                NimPreferences.saveUserToken(token);

                /*设置当前应用的用户*/
                NimUIKit.setAccount(accout);

                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                finish();
            }

            @Override
            public void onFailed(int i) {
                Toast.makeText(LoginActivity.this,"登录失败",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onException(Throwable throwable) {
                Toast.makeText(LoginActivity.this,"登录失败",Toast.LENGTH_SHORT).show();
            }
        });

    }


}
