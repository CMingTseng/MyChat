package com.dingmouren.commonlib.http.interceptor;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/3/30.
 *  网络请求中添加公共请求参数的插入器
 *  使用addInterceptor（）这个方法
 */

public class HttpCommonParamInterceptor implements Interceptor {

    private   Map<String,String> mCommonParamsMap;

    private HttpCommonParamInterceptor(){}

    private HttpCommonParamInterceptor(Map<String,String> commonParamsMap){
        this.mCommonParamsMap = commonParamsMap;
    }

    public static HttpCommonParamInterceptor getInstance(Map<String,String> commonParamsMap){
        return new HttpCommonParamInterceptor(commonParamsMap);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        if (mCommonParamsMap.size() <= 0 || null == mCommonParamsMap){//如果没有公共请求参数，不做处理
            return chain.proceed(chain.request());
        }

        Request request = chain.request();//获取到Request对象

        String method = request.method();//获取到请求方式

        switch (method){//根据请求方式的不同选择相应的处理
            case "GET":
                request = request.newBuilder().url(httpUrlGetAddedCommonParams(request)).build();
                break;

            case "POST":
                request = request.newBuilder().post(httpUrlPostAddedCommonParams(request)).build();
                break;
        }

        return chain.proceed(request);
    }

    /**
     * get请求下，添加公共请求参数到url末尾
     * @param request
     * @return
     */
    private HttpUrl httpUrlGetAddedCommonParams(Request request) {

        HttpUrl.Builder builder = request.url().newBuilder();

        Iterator iterator = mCommonParamsMap.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String,String> entry = (Map.Entry<String,String>) iterator.next();
            builder.addQueryParameter(entry.getKey(),entry.getValue());
        }

        return builder.build();
    }

    /**
     * post请求下，添加公共请求参数到表单
     * @param request
     * @return
     */
    private RequestBody httpUrlPostAddedCommonParams(Request request) {

        FormBody.Builder newFormBuilder = new FormBody.Builder();

        /*post表单中追加的公共请求参数*/
        Iterator<Map.Entry<String,String>> iterator = mCommonParamsMap.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String,String> entry = iterator.next();
            newFormBuilder.add(entry.getKey(),entry.getValue());
        }

        /*拦截原来请求中用户传入的参数数据，将参数遍历放入新的表单中，然后一并提交*/
        FormBody oldForm = (FormBody) request.body();
        for (int i = 0; i < oldForm.size(); i++) {
            newFormBuilder.add(oldForm.encodedName(i),oldForm.encodedValue(i));
        }

        return newFormBuilder.build();
    }

}

