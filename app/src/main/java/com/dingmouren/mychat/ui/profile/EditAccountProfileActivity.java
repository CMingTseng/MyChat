package com.dingmouren.mychat.ui.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.dingmouren.mychat.R;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.uinfo.UserService;
import com.netease.nimlib.sdk.uinfo.UserServiceObserve;
import com.netease.nimlib.sdk.uinfo.constant.UserInfoFieldEnum;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/4/20.
 * 编辑个人资料界面
 */

public class EditAccountProfileActivity extends AppCompatActivity {

    private static final String TAG = "EditAccountProfileActivity";

    private ImageView mImgArrowBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account_profile);

        initView();

        initListener();
    }

    private void initView() {
        mImgArrowBack = findViewById(R.id.img_arrow_back);
    }

    private void initListener(){
        mImgArrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void updateAccountProfile(View view){
        Map<UserInfoFieldEnum,Object> fieldsMap = new HashMap<>(1);
        fieldsMap.put(UserInfoFieldEnum.AVATAR,"http://pic22.photophoto.cn/20120228/0017030573365454_b.jpg");
        fieldsMap.put(UserInfoFieldEnum.BIRTHDAY,"2000-12-12");
        fieldsMap.put(UserInfoFieldEnum.EMAIL,"526279492@qq.com");
        fieldsMap.put(UserInfoFieldEnum.GENDER,1);
        fieldsMap.put(UserInfoFieldEnum.MOBILE,"15298458596");
        fieldsMap.put(UserInfoFieldEnum.Name,"昵称呢");
        fieldsMap.put(UserInfoFieldEnum.SIGNATURE,"每天进步一点点");
        NIMClient.getService(UserService.class)
                .updateUserInfo(fieldsMap)
                .setCallback(new RequestCallback<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.e(TAG,"onSuccess");
                    }

                    @Override
                    public void onFailed(int i) {
                        Log.e(TAG,"onFailed");
                    }

                    @Override
                    public void onException(Throwable throwable) {
                        Log.e(TAG,"onException");
                    }
                });

        NIMClient.getService(UserServiceObserve.class).observeUserInfoUpdate(new Observer<List<NimUserInfo>>() {
            @Override
            public void onEvent(List<NimUserInfo> nimUserInfos) {
                Log.e(TAG,nimUserInfos.size()+"");
            }
        },true);


    }
}
