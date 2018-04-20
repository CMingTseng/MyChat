package com.dingmouren.mychat.ui.main;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.dingmouren.mychat.R;
import com.dingmouren.mychat.ui.main.recentsession.RecentSessionFragment;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nim.uikit.business.recent.RecentContactsCallback;
import com.netease.nim.uikit.business.recent.RecentContactsFragment;
import com.netease.nim.uikit.common.activity.UI;
import com.netease.nimlib.sdk.AbortableFuture;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.msg.attachment.MsgAttachment;
import com.netease.nimlib.sdk.msg.model.RecentContact;

import java.util.ArrayList;
import java.util.List;

/**
 * 主界面
 */
public class MainActivity extends UI implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    private FrameLayout mFrameLayout;
    private Button mBtnRecentSession;
    private Button mBtnContacts;

    private FragmentManager mFragmentManager;
    private Fragment mCurrentFragment;//当前显示的fragment
    private Fragment mRecentSessionFragment;//最近会话列表
    private Fragment mContactsFragment;//通讯录列表
    private List<Fragment> mFragments = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        initView();
        initListener();
    }

    private void init() {
        mFragmentManager = getSupportFragmentManager();

        mRecentSessionFragment = new RecentSessionFragment();
        mContactsFragment = new ContactsFragment();

        mFragments.clear();
        mFragments.add(mRecentSessionFragment);
        mFragments.add(mContactsFragment);

        mFragmentManager.beginTransaction()
                .replace(R.id.frame_layout,mRecentSessionFragment)
                .add(R.id.frame_layout,mContactsFragment)
                .hide(mContactsFragment)
                .commit();

        mCurrentFragment = mRecentSessionFragment;
    }

    private void initView() {
        mFrameLayout = findViewById(R.id.frame_layout);
        mBtnRecentSession = findViewById(R.id.btn_recent_session);
        mBtnContacts = findViewById(R.id.btn_contacts);
    }

    private void initListener(){
        mBtnRecentSession.setOnClickListener(this);
        mBtnContacts.setOnClickListener(this);
    }




    /**
     * 读取AndroidManifest.xml中meta-data中的键值对信息
     *
     * @param context
     * @return
     */
    private static String readAppKey(Context context) {
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            if (appInfo != null) {
                String appkey = appInfo.metaData.getString("com.netease.nim.appKey");
                Log.e(TAG, "readAppKey:" + appkey);
                return appkey;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_recent_session:
                mBtnRecentSession.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
                mBtnContacts.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
                switchFragment(0,v);
                break;
            case R.id.btn_contacts:
                mBtnRecentSession.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
                mBtnContacts.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
                switchFragment(1,v);
                break;
        }
    }

    private void switchFragment(int index,View view) {

        mFragmentManager.beginTransaction()
                .hide(mCurrentFragment)
                .show(mFragments.get(index))
                .commit();
        mCurrentFragment = mFragments.get(index);
    }
}
