package com.dingmouren.mychat.ui.main.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dingmouren.mychat.R;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nim.uikit.business.recent.RecentContactsCallback;
import com.netease.nim.uikit.business.recent.RecentContactsFragment;
import com.netease.nim.uikit.common.fragment.TFragment;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.attachment.MsgAttachment;
import com.netease.nimlib.sdk.msg.model.RecentContact;

import java.util.List;

/**
 * Created by Administrator on 2018/4/19.
 * 最近的会话
 */

public class RecentSessionFragment extends RecentContactsFragment {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initListener();
    }

    private void initListener() {
        setCallback(recentContactsCallback);
    }

    /**
     * 监听
     */
    private RecentContactsCallback recentContactsCallback = new RecentContactsCallback() {
        @Override
        public void onRecentContactsLoaded() {

        }

        @Override
        public void onUnreadCountChange(int unreadCount) {

        }

        @Override
        public void onItemClick(RecentContact recent) {//最近会话列表条目的点击事件

            switch (recent.getSessionType()){
                case P2P:
                    NimUIKit.startP2PSession(getActivity(),recent.getContactId());
                    break;
                case Team:
                    NimUIKit.startTeamSession(getActivity(),recent.getContactId());
                    break;
                case ChatRoom:
                    break;
                case System:
                    break;
                case None:
                    break;
            }
        }

        @Override
        public String getDigestOfAttachment(RecentContact recent, MsgAttachment attachment) {
            return null;
        }

        @Override
        public String getDigestOfTipMsg(RecentContact recent) {
            return null;
        }
    };
}
