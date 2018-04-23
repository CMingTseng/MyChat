package com.dingmouren.commonlib.http.interceptor;

import com.dingmouren.commonlib.util.NetworkUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/3/30.
 * 设置缓存的拦截器
 * okhttp只会对get请求进行缓存，因为get请求的数据一般是比较持久的，post请求一般是交互操作，数据变化
 */

public class HttpCacheInterceptor implements Interceptor{

    public static HttpCacheInterceptor getInstance(){
        return new HttpCacheInterceptor();
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();

        Response response = chain.proceed(request);

        if (NetworkUtils.isConnected()){//有网的时候进行数据将数据缓存

            String cacheControl = request.cacheControl().toString();

            return response.newBuilder()
                    .removeHeader("Pragma")//清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                    .header("Cache-Control",cacheControl)
                    .build();
        }
        return response;
    }
}
