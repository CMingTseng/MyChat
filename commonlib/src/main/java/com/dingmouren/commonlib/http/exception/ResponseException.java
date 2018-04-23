package com.dingmouren.commonlib.http.exception;

/**
 * Created by Administrator on 2018/4/3.
 */

public class ResponseException extends Exception {

    public int code;

    public String message;

    public ResponseException(Throwable throwable,int code) {
        this.code = code;
    }
}
