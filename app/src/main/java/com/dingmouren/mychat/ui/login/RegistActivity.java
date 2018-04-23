package com.dingmouren.mychat.ui.login;

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

import com.dingmouren.mychat.R;
import com.netease.nim.uikit.common.http.NimHttpClient;

/**
 * Created by Administrator on 2018/4/23.
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


    }

}
