package com.dingmouren.commonlib.http;


import com.dingmouren.commonlib.http.interceptor.HttpCache;
import com.dingmouren.commonlib.http.interceptor.HttpCacheInterceptor;
import com.dingmouren.commonlib.http.interceptor.HttpCommonHeaderInterceptor;
import com.dingmouren.commonlib.http.interceptor.HttpCommonParamInterceptor;
import com.dingmouren.commonlib.http.interceptor.HttpLogInterceptor;
import com.dingmouren.commonlib.util.ApplicationUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2018/3/29.
 * 网络请求管理类
 */

public class HttpManager {

    private volatile static HttpManager sHttpManager = null;//网络请求管理类实例

    private static final int READ_TIME_OUT = 3;//读取超时时间

    private static final int CONNECT_TIME_OUT = 3;//连接超时时间

    private static final int WRITE_TIME_OUT = 3;//写的超时时间

    private static Map<String,String> sCommomParamsMap = new HashMap<>();//公共请求参数的map集合

    private static Map<String,String> sCommonHeadersMap = new HashMap<>();//公共头信息的map集合


    static {

        /*初始化公共请求参数集合*/
//        sCommomParamsMap.put("phone","Android");
//        sCommomParamsMap.put("name","19");

        /*初始化公共头信息集合*/
//        sCommonHeadersMap.put("demo","test");
    }





    /*OkHttp的构建者对象*/
    private static OkHttpClient.Builder sOkHttpBuilder = new OkHttpClient.Builder()
            .addInterceptor(HttpLogInterceptor.getInstance())
            .addInterceptor(HttpCommonParamInterceptor.getInstance(sCommomParamsMap))
            .addInterceptor(HttpCommonHeaderInterceptor.getInstance(sCommonHeadersMap))
            .cache(HttpCache.getCacheObject())
            .addNetworkInterceptor(HttpCacheInterceptor.getInstance());

    /*Retrofit的构建者对象*/
    private static Retrofit.Builder sRetrofitBuilder = new Retrofit.Builder()
            .baseUrl("http://gank.io/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create());


    private HttpManager() {}//私有化构造函数

    /**
     * 单例模式获取实例对象
     * @return
     */
    public static HttpManager getInstance(){
        if (sHttpManager == null){
            synchronized (HttpManager.class){
                if (sHttpManager == null) sHttpManager = new HttpManager();
            }
        }
        return sHttpManager;
    }

    /**
     * 创建Api的service
     * @param serviceClass
     * @param <S>
     * @return
     */
    public static <S> S createService(Class<S> serviceClass){
        OkHttpClient okHttpClient = sOkHttpBuilder.build();
        Retrofit retrofit = sRetrofitBuilder.client(okHttpClient).build();
        return retrofit.create(serviceClass);
    }



}
