package com.dingmouren.mychat.ui.updatePass;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.dingmouren.mychat.NimApplication;
import com.dingmouren.mychat.ui.regist.HttpRegistClient;
import com.dingmouren.mychat.util.CheckSumBuilder;
import com.netease.nim.uikit.common.http.NimHttpClient;
import com.netease.nim.uikit.common.util.log.LogUtil;
import com.netease.nim.uikit.common.util.string.MD5;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/4/24.
 * 更新用户密码，也就是token
 */

public class HttpUpdatePassClient {

    private static final String TAG = "HttpUpdatePassClient";

    private static HttpUpdatePassClient sInstance;

    public interface HttpUpdatePassClientCallback<T> {
        void onSuccess(T t);

        void onFailed(int code, String errorMsg);

    }


    private HttpUpdatePassClient(){
        NimHttpClient.getInstance().init(NimApplication.sApplication);}

    public static synchronized HttpUpdatePassClient getInstance() {
        if (sInstance == null) sInstance = new HttpUpdatePassClient();
        return sInstance;
    }

    /**
     * 修改面成功返回的json:  reponse:{"code":200}  code:200
     */
    public void updatePassword(String account, String password, final HttpUpdatePassClientCallback<String> callback){
        String url = "https://api.netease.im/nimserver/user/update.action";

        String appKey = readAppKey();
        String appSecret = "ad9c31435f94";
        String nonce = "12345";
        String curTime = String.valueOf((new Date()).getTime() / 1000L);
        String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce ,curTime);

        /*header*/
        Map<String, String> headers = new HashMap<>(1);
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
        headers.put("AppKey",appKey);
        headers.put("Nonce",nonce);
        headers.put("CurTime",curTime);
        headers.put("CheckSum",checkSum);

        /*参数*/
        StringBuilder body = new StringBuilder();
        body.append("accid").append("=").append(account).append("&")
                .append("token").append("=").append(password);
        String bodyString = body.toString();

        /*使用uikit组件中原有的去发出post请求方式*/
        NimHttpClient.getInstance().execute(url, headers, bodyString, new NimHttpClient.NimHttpCallback() {
            @Override
            public void onResponse(String response, int code, Throwable exception) {

                Log.e(TAG,"reponse:"+response+"  code:"+code);

                if (code != 200 || exception != null) {
                    LogUtil.e(TAG, "register failed : code = " + code + ", errorMsg = " + (exception != null ? exception.getMessage() : "null"));
                    if (callback != null) {
                        callback.onFailed(code, exception != null ? exception.getMessage() : "null");
                    }
                    return;
                }

                /*code等于200，就说明密码更改成功*/
              if (code == 200){
                  if (null != callback) callback.onSuccess("成功");
              }
            }
        });
    }

    /**
     * 获取AndroidManifest.xml中meta-data中的appkey的值
     * @return
     */
    private String readAppKey() {
        try {
            ApplicationInfo appInfo = NimApplication.sApplication.getPackageManager()
                    .getApplicationInfo(NimApplication.sApplication.getPackageName(), PackageManager.GET_META_DATA);
            if (appInfo != null) {
                return appInfo.metaData.getString("com.netease.nim.appKey");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
