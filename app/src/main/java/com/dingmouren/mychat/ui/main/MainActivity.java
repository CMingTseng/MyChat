package com.dingmouren.mychat.ui.main;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.dingmouren.mychat.R;
import com.dingmouren.mychat.ui.main.fragment.ContactsFragment;
import com.dingmouren.mychat.ui.main.fragment.RecentSessionFragment;
import com.dingmouren.mychat.ui.main.fragment.SettingFragment;
import com.dingmouren.mychat.ui.search.SearchActivity;
import com.netease.nim.uikit.common.activity.UI;

import java.util.ArrayList;
import java.util.List;

/**
 * 主界面
 */
public class MainActivity extends UI implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    private FrameLayout mFrameLayout;
    private ImageView mImgSearch;
    private RelativeLayout mRelaRecentSession;
    private ImageView mImgRecentSessionLeftArrow;
    private RelativeLayout mRelaContacts;
    private ImageView mImgContactsLeftArrow;
    private RelativeLayout mRelaSetting;
    private ImageView mImgSettingLeftArrow;

    private FragmentManager mFragmentManager;
    private Fragment mCurrentFragment;//当前显示的fragment
    private Fragment mRecentSessionFragment;//最近会话列表
    private Fragment mContactsFragment;//通讯录列表
    private Fragment mSettingFragment;//设置界面
    private List<Fragment> mFragments = new ArrayList<>();//用于切换Fragment
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

        mRecentSessionFragment = new RecentSessionFragment();//最近会话列表
        mContactsFragment = new ContactsFragment();//联系人列表
        mSettingFragment = new SettingFragment();

        mFragments.clear();
        mFragments.add(mRecentSessionFragment);
        mFragments.add(mContactsFragment);
        mFragments.add(mSettingFragment);


        mFragmentManager.beginTransaction()
                .replace(R.id.frame_layout,mRecentSessionFragment)
                .add(R.id.frame_layout,mContactsFragment)
                .hide(mContactsFragment)
                .add(R.id.frame_layout,mSettingFragment)
                .hide(mSettingFragment)
                .commit();

        mCurrentFragment = mRecentSessionFragment;
    }

    private void initView() {
        mFrameLayout = findViewById(R.id.frame_layout);
        mImgSearch = findView(R.id.img_search);
        mRelaRecentSession = findViewById(R.id.rela_recent_session);
        mImgRecentSessionLeftArrow = findViewById(R.id.recent_session_left_arrow);
        mRelaContacts = findViewById(R.id.rela_contacts);
        mImgContactsLeftArrow = findViewById(R.id.contacts_left_arrow);
        mRelaSetting = findViewById(R.id.rela_setting);
        mImgSettingLeftArrow = findViewById(R.id.setting_left_arrow);
    }

    private void initListener(){
        mRelaRecentSession.setOnClickListener(this);
        mRelaContacts.setOnClickListener(this);
        mRelaSetting.setOnClickListener(this);
        mImgSearch.setOnClickListener(this);
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
            case R.id.rela_recent_session://消息界面
                mImgRecentSessionLeftArrow.setVisibility(View.VISIBLE);
                mImgContactsLeftArrow.setVisibility(View.INVISIBLE);
                mImgSettingLeftArrow.setVisibility(View.INVISIBLE);
                switchFragment(0,v);
                break;
            case R.id.rela_contacts://联系人界面
                mImgRecentSessionLeftArrow.setVisibility(View.INVISIBLE);
                mImgContactsLeftArrow.setVisibility(View.VISIBLE);
                mImgSettingLeftArrow.setVisibility(View.INVISIBLE);
                switchFragment(1,v);
                break;
            case R.id.rela_setting:
                mImgRecentSessionLeftArrow.setVisibility(View.INVISIBLE);
                mImgContactsLeftArrow.setVisibility(View.INVISIBLE);
                mImgSettingLeftArrow.setVisibility(View.VISIBLE);
                switchFragment(2,v);
                break;
            case R.id.img_search://打开搜索界面
                startActivity(new Intent(MainActivity.this, SearchActivity.class));
                break;
        }
    }

    /**
     * 切换fragment
     * @param index
     * @param view
     */
    private void switchFragment(int index,View view) {

        mFragmentManager.beginTransaction()
                .hide(mCurrentFragment)
                .show(mFragments.get(index))
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
        mCurrentFragment = mFragments.get(index);
    }
}
