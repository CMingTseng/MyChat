package com.dingmouren.mychat.ui.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dingmouren.mychat.R;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.friend.FriendService;
import com.netease.nimlib.sdk.friend.constant.VerifyType;
import com.netease.nimlib.sdk.friend.model.AddFriendData;
import com.netease.nimlib.sdk.friend.model.Friend;
import com.netease.nimlib.sdk.uinfo.UserService;

/**
 * Created by Administrator on 2018/4/20.
 * 搜索好友界面
 */

public class SearchActivity extends AppCompatActivity {

    private static final String TAG = "SearchActivity";

    private ImageView mImgArrowBack;
    private EditText mEditSearch;
    private TextView mTvResult;
    private Button mBtnAddFriend;
    private ImageButton mBtnSearch;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initView();

        initListener();
    }

    private void initView() {
        mImgArrowBack = findViewById(R.id.img_arrow_back);
        mEditSearch = findViewById(R.id.edit_search);
        mTvResult = findViewById(R.id.tv_user_account);
        mBtnAddFriend = findViewById(R.id.btn_add_friend);
        mBtnSearch = findViewById(R.id.btn_search);
    }

    private void initListener(){
        mImgArrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchAccount();
            }
        });


    }

    /**
     * 搜索好友
     */
    private void searchAccount() {
        String account = mEditSearch.getText().toString().trim();
        if (TextUtils.isEmpty(account)){
            Toast.makeText(this,"搜索好友不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        Friend friend = NIMClient.getService(FriendService.class).getFriendByAccount("account");
        showSearchResult(friend);
    }

    /**
     * 展示搜索结果
     * @param friend
     */
    private void showSearchResult(final Friend friend) {
        if (null == friend){
            mTvResult.setText("没有这个用户");
            mBtnAddFriend.setVisibility(View.INVISIBLE);
        }else {
            mTvResult.setText(friend.getAccount());
            mBtnAddFriend.setVisibility(View.VISIBLE);
            mBtnAddFriend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   addFriend(friend);
                }
            });
        }
    }

    private void addFriend(final Friend friend) {
        NIMClient.getService(FriendService.class)
                .addFriend(new AddFriendData(friend.getAccount(), VerifyType.DIRECT_ADD))
                .setCallback(new RequestCallback<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(SearchActivity.this, "添加好友成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailed(int i) {
                        Toast.makeText(SearchActivity.this, "添加好友失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onException(Throwable throwable) {
                        Toast.makeText(SearchActivity.this, "添加好友失败", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
