package com.dingmouren.mychat.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dingmouren.mychat.NimPreferences;
import com.dingmouren.mychat.R;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.uinfo.UserService;
import com.netease.nimlib.sdk.uinfo.UserServiceObserve;
import com.netease.nimlib.sdk.uinfo.constant.UserInfoFieldEnum;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;
import com.netease.nimlib.sdk.uinfo.model.UserInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/4/20.
 * 用户资料界面
 */

public class AccountProfileActivity extends AppCompatActivity{


    private static final String TAG = "AccountProfileActivity";
    private static final String ACCOUNT = "account";

    private ImageView mImgHeaderIcon;
    private TextView mTvName;
    private TextView mTvSign;
    private ImageView mImgEdit;
    private ImageView mImgArrowBack;

    private String mAccount;//账户

    public static void start(Context context,String account){
        Intent intent = new Intent(context,AccountProfileActivity.class);
        intent.putExtra(ACCOUNT,account);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_profile);

        parseIntent();

        initView();

        initListener();

        initData();
    }

    private void parseIntent() {
        if (getIntent() != null) mAccount = getIntent().getStringExtra(ACCOUNT);
    }

    private void initView(){
        mImgHeaderIcon = findViewById(R.id.img_head_icon);
        mTvName = findViewById(R.id.tv_name);
        mTvSign = findViewById(R.id.tv_sign);
        mImgEdit = findViewById(R.id.img_edit);
        mImgArrowBack = findViewById(R.id.img_arrow_back);

        String appAccount = NimPreferences.getUserAccount();
        if (appAccount.equals(mAccount)){
            mImgEdit.setVisibility(View.VISIBLE);
        }else {
            mImgEdit.setVisibility(View.GONE);
        }
    }

    private void initListener(){
        mImgArrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mImgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountProfileActivity.this,EditAccountProfileActivity.class));
            }
        });
    }

    private void initData(){
        NimUserInfo userInfo = NIMClient.getService(UserService.class).getUserInfo(mAccount);
        Glide.with(this).load(userInfo.getAvatar()).into(mImgHeaderIcon);
        mTvName.setText(userInfo.getAccount());
        mTvSign.setText(userInfo.getSignature());
    }

}
