package com.dingmouren.commonlib.http.interceptor;

import com.dingmouren.commonlib.util.LogUtils;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by dingmouren on 2018/4/1.
 * 网络请求中添加公共头信息的插入器
 */

public class HttpCommonHeaderInterceptor implements Interceptor{

    private Map<String,String> mCommonHeadersMap ;

    private HttpCommonHeaderInterceptor(){}

    private HttpCommonHeaderInterceptor(Map<String,String> commonHeadersMap){
        this.mCommonHeadersMap = commonHeadersMap;
    }

    public static HttpCommonHeaderInterceptor getInstance(Map<String,String> headersMap){
        return new HttpCommonHeaderInterceptor(headersMap);
    }
    @Override
    public Response intercept(Chain chain) throws IOException {

        if (mCommonHeadersMap.size() <= 0 || null == mCommonHeadersMap){//如果没有公共请求头信息，不做处理
            return chain.proceed(chain.request());
        }

        Request request = chain.request();//获取到Request对象

        Request.Builder newBuilder= request.newBuilder();

        Iterator<Map.Entry<String,String>> iterator = mCommonHeadersMap.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String,String> entry = iterator.next();
            newBuilder.addHeader(entry.getKey(),entry.getValue());
        }

        return chain.proceed(newBuilder.build());
    }
}
