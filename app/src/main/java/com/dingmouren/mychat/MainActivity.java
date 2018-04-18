package com.dingmouren.mychat;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nimlib.sdk.AbortableFuture;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private FrameLayout frameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frameLayout = findViewById(R.id.frame_layout);

    }

    public void login(View view){
        final String account = "test_3";
        final String token = "4565206";
        AbortableFuture<LoginInfo> loginRequest = NIMClient.getService(AuthService.class).login(new LoginInfo(account,token));
        loginRequest.setCallback(new RequestCallback<LoginInfo>() {
            @Override
            public void onSuccess(LoginInfo loginInfo) {
                Log.e(TAG,"onSuccess:"+loginInfo.getAccount()+" "+loginInfo.getAppKey()+"  "+loginInfo.getToken());
                NimUIKit.setAccount(loginInfo.getAccount());
                NimUIKit.startP2PSession(getApplicationContext(),"test_2");
            }

            @Override
            public void onFailed(int code) {
                Log.e(TAG,"onFailed:"+code);
            }

            @Override
            public void onException(Throwable throwable) {
                Log.e(TAG,"onException:"+throwable.getMessage());
            }
        });


    }

    public void sessionToTest3(View view){


    }


    /**
     * 读取AndroidManifest.xml中meta-data中的键值对信息
     * @param context
     * @return
     */
    private static String readAppKey(Context context) {
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            if (appInfo != null) {
                String appkey = appInfo.metaData.getString("com.netease.nim.appKey");
                Log.e(TAG,"readAppKey:"+appkey);
                return appkey;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
