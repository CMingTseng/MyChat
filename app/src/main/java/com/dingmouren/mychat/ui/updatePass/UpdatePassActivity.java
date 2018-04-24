package com.dingmouren.mychat.ui.updatePass;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.dingmouren.mychat.NimPreferences;
import com.dingmouren.mychat.R;

/**
 * Created by Administrator on 2018/4/24.
 */

public class UpdatePassActivity extends AppCompatActivity {
    private ImageView mImgArrowBack;
    private EditText mEditNewPass;
    private Button mBtnConfirm;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_pass);

        initView();

        initListener();
    }

    private void initView() {
        mImgArrowBack = findViewById(R.id.img_arrow_back);
        mEditNewPass = findViewById(R.id.edit_new_pass);
        mBtnConfirm = findViewById(R.id.btn_confirm);
    }

    private void initListener() {
        mImgArrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mBtnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePass();
            }
        });
    }

    /**
     * 更新用户的密码
     */
    private void updatePass() {
        String newPassword = mEditNewPass.getText().toString().trim();
        if (TextUtils.isEmpty(newPassword)){
            Toast.makeText(UpdatePassActivity.this,"新密码不能空",Toast.LENGTH_SHORT).show();
            return;
        }

        String account = NimPreferences.getUserAccount();
        if (TextUtils.isEmpty(account)){
            Toast.makeText(UpdatePassActivity.this,"用户account为空",Toast.LENGTH_SHORT).show();
            return;
        }

        HttpUpdatePassClient.getInstance().updatePassword(account,newPassword, new HttpUpdatePassClient.HttpUpdatePassClientCallback<String>() {
            @Override
            public void onSuccess(String s) {
                Toast.makeText(UpdatePassActivity.this,"密码更改成功",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed(int code, String errorMsg) {
                Toast.makeText(UpdatePassActivity.this,"密码更改失败",Toast.LENGTH_SHORT).show();
            }
        });

    }
}
