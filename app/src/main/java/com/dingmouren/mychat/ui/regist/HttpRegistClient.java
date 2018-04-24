package com.dingmouren.mychat.ui.regist;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.dingmouren.mychat.NimApplication;
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
 * 注册
 */

public class HttpRegistClient {

    private static final String TAG = "HttpRegistClient";

    public interface HttpClientCallback<T> {
        void onSuccess(T t);

        void onFailed(int code, String errorMsg);

    }

    private static HttpRegistClient sInstance;

    private HttpRegistClient(){NimHttpClient.getInstance().init(NimApplication.sApplication);}

    public static synchronized HttpRegistClient getInstance() {
        if (sInstance == null) sInstance = new HttpRegistClient();
        return sInstance;
    }

    /**
     * 注册
     * 已经注册过返回的json: reponse:{"desc":"already register","code":414}  code:200
     * 注册成功返回的json:  reponse:{"code":200,"info":{"token":"4565206","accid":"test_8","name":""}} code:200
     * @param account 账户名
     * @param nickName 昵称
     * @param password 密码
     * @param callback 回调
     */
    public void register(String account, String nickName, String password, final HttpClientCallback<String> callback){
        String url = "https://api.netease.im/nimserver/user/create.action";
        password = MD5.getStringMD5(password);
        try {
            nickName = URLEncoder.encode(nickName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

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
                .append("name").append("=").append(nickName).append("&")
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

                /*code等于200，解析返回的json*/
                try {
                    JSONObject resObj = JSONObject.parseObject(response);
                    int resCode = resObj.getIntValue("code");
                    if (resCode == 200) {
                        String info = resObj.getString("info");
                        callback.onSuccess(info);
                    } else {
                        String errorMsg = resObj.getString("desc");
                        callback.onFailed(resCode, errorMsg);
                    }
                } catch (JSONException e) {
                    callback.onFailed(-1, e.getMessage());
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
