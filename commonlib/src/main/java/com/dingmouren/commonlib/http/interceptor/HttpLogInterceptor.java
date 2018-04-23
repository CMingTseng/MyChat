package com.dingmouren.commonlib.http.interceptor;

import com.dingmouren.commonlib.util.ApplicationUtils;
import com.dingmouren.commonlib.util.LogUtils;

import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by Administrator on 2018/3/30.
 * 日志拦截器
 */

public class HttpLogInterceptor {
    private static HttpLoggingInterceptor sHttpLoggingInterceptor = new HttpLoggingInterceptor(message -> showHttpLog(message)).setLevel(HttpLoggingInterceptor.Level.BODY);

    private HttpLogInterceptor() {
        throw new UnsupportedOperationException("can't instantiate HttpLogInterceptor");
    }

    public static HttpLoggingInterceptor getInstance(){
        return sHttpLoggingInterceptor;
    }

    /**
     * 显示网络日志
     * @param message
     */
    private static void showHttpLog(String message) {

        if (ApplicationUtils.isDebug()){//在debug环境下，打印服务端返回的数据以及http请求相关数据
            if (null  != message && message.length() > 0) LogUtils.json(message);
        }
    }
}
