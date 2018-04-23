package com.dingmouren.commonlib.rxlifecycle;

import java.util.HashSet;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Administrator on 2018/4/3.
 * Rxjava订阅关系的管理类
 * 使用BaseObserver作为订阅者的RxJava请求，都可以调用 DisposableManager.clear()来取消订阅关系
 */

public  class DisposableManager {

    private static CompositeDisposable sCompositeDisposable = new CompositeDisposable();

    private DisposableManager(){throw new UnsupportedOperationException("can't instantiate DisposableManager");}

    public static void add(Disposable disposable){
        sCompositeDisposable.add(disposable);
    }

    public static void clear(){
      sCompositeDisposable.clear();
    }
}
