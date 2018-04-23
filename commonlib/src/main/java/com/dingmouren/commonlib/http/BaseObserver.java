package com.dingmouren.commonlib.http;


import android.widget.Toast;

import com.dingmouren.commonlib.http.exception.HttpExceptionHandle;
import com.dingmouren.commonlib.http.exception.ResponseException;
import com.dingmouren.commonlib.rxlifecycle.DisposableManager;
import com.dingmouren.commonlib.util.ApplicationUtils;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.BehaviorSubject;

/**
 * Created by Administrator on 2018/4/3.
 */

public abstract class BaseObserver<T> implements Observer<T> {
    @Override
    public void onSubscribe(Disposable disposable) {
        DisposableManager.add(disposable);//订阅关系的管理
        //展示加载中
    }


    @Override
    public void onError(Throwable throwable) {
        ResponseException responseException = HttpExceptionHandle.handleException(throwable);
        Toast.makeText(ApplicationUtils.getApp(),responseException.message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onComplete() {
        //隐藏加载中
    }

}
