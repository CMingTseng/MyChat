package com.dingmouren.mychat.ui.main.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.dingmouren.mychat.R;
import com.dingmouren.mychat.ui.login.LoginActivity;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.auth.AuthService;

/**
 * Created by Administrator on 2018/4/23.
 */

public class SettingFragment extends Fragment {

    private View mRootView;
    private Button mBtnLogOut;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_setting,container,false);

        initView(mRootView);

        initListener();

        return mRootView;
    }


    private void initView(View view) {
        mBtnLogOut = view.findViewById(R.id.btn_log_out);
    }

    private void initListener() {
        mBtnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NIMClient.getService(AuthService.class).logout();
                startActivity(new Intent(getContext(),LoginActivity.class));
                getActivity().finish();
            }
        });
    }

}
