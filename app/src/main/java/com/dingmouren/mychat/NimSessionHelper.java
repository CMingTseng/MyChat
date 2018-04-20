package com.dingmouren.mychat;

import android.content.Context;

import com.dingmouren.mychat.ui.profile.AccountProfileActivity;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nim.uikit.api.model.session.SessionEventListener;
import com.netease.nimlib.sdk.msg.model.IMMessage;

/**
 * Created by Administrator on 2018/4/20.
 */

public class NimSessionHelper {

    /**
     * 初始化
     */
    public static void init(){

        setSessionListener();
    }

    private static void setSessionListener() {
        SessionEventListener sessionEventListener = new SessionEventListener() {
            @Override
            public void onAvatarClicked(Context context, IMMessage message) {
                AccountProfileActivity.start(context,message.getFromAccount());
            }

            @Override
            public void onAvatarLongClicked(Context context, IMMessage message) {

            }

            @Override
            public void onAckMsgClicked(Context context, IMMessage message) {

            }
        };
        NimUIKit.setSessionListener(sessionEventListener);
    }
}
