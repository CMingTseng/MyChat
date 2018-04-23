package com.dingmouren.mychat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

import com.dingmouren.mychat.ui.login.LoginActivity;
import com.dingmouren.mychat.ui.main.MainActivity;
import com.netease.nim.uikit.api.NimUIKit;

/**
 * Created by Administrator on 2018/4/18.
 */

public class WelcomeActivity extends AppCompatActivity {

    private static final String TAG = "WelcomeActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (isLogined()){
                    toMainActivity();
                }else {
                    toLoginActivity();
                }

            }
        },4000);

    }

    /**
     * 判断是不是已经登录过
     * @return
     */
    private boolean isLogined() {
        String account = NimPreferences.getUserAccount();
        String token = NimPreferences.getUserToken();
        NimUIKit.setAccount(account);
        Log.e(TAG,account +"  "+token);
        return !TextUtils.isEmpty(account) && !TextUtils.isEmpty(token);
    }

    /**
     * 跳往登录界面
     */
    private void toLoginActivity() {
        startActivity(new Intent(this,LoginActivity.class));
        finish();
    }

    /**
     * 跳往主界面
     */
    private void toMainActivity() {
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }

}
