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
import android.view.View;
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
public class MainActivity extends UI {

    private static final String TAG = "MainActivity";

    private FrameLayout mFrameLayout;
    private FragmentManager mFragmentManager;
    private Fragment mCurrentFragment,mRecentSessionFragment;
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

        mFragments.clear();
        mFragments.add(mRecentSessionFragment);



        mFragmentManager.beginTransaction().replace(R.id.frame_layout,mRecentSessionFragment).commit();

    }

    private void initView() {
        mFrameLayout = findViewById(R.id.frame_layout);
    }

    private void initListener(){

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

}
