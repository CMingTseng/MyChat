package com.dingmouren.commonlib.http.interceptor;

import android.app.Activity;

import com.dingmouren.commonlib.util.ApplicationUtils;

import java.io.File;

import okhttp3.Cache;

/**
 * Created by Administrator on 2018/4/2.
 * 设置缓存文件路径和缓存文件大小
 */

public class HttpCache {

    private static Cache mCache;
    private static int cacheSize = 10 * 1024 * 1024;//缓存文件大小10M
    private static File cacheDir = new File(ApplicationUtils.getApp().getCacheDir(),"httpcache");//缓存文件路径

    private HttpCache(){}

    public static Cache getCacheObject(){
        if (null == mCache){
            mCache = new Cache(cacheDir,cacheSize);
        }
        return mCache;
    }
}
