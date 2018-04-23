package com.dingmouren.commonlib.http.exception;

import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;

import javax.net.ssl.SSLHandshakeException;

import retrofit2.HttpException;

/**
 * Created by Administrator on 2018/4/3.
 * 异常处理
 */

public class HttpExceptionHandle {

    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;

    public static ResponseException handleException(Throwable throwable){
        ResponseException responseException;

        if (throwable instanceof HttpException){
            HttpException httpException = (HttpException) throwable;
            responseException = new ResponseException(throwable,Error.HTTP_ERROR);
            switch (httpException.code()){
                case UNAUTHORIZED:
                case FORBIDDEN:
                case NOT_FOUND:
                case REQUEST_TIMEOUT:
                case GATEWAY_TIMEOUT:
                case INTERNAL_SERVER_ERROR:
                case BAD_GATEWAY:
                case SERVICE_UNAVAILABLE:
                default:
                    responseException.message = "网络错误";
                    break;
            }
            return responseException;
        }else if (throwable instanceof ServerException){
            ServerException serverException = (ServerException) throwable;
            responseException = new ResponseException(serverException,serverException.code);
            responseException.message = serverException.message;
            return responseException;
        }else if (throwable instanceof JsonParseException
                || throwable instanceof JSONException
                || throwable instanceof ParseException){
            responseException = new ResponseException(throwable,Error.PARSE_ERROR);
            responseException.message = "解析错误";
            return responseException;
        }else if (throwable instanceof ConnectException){
            responseException = new ResponseException(throwable,Error.NETWORD_ERROR);
            responseException.message = "连接失败";
            return responseException;
        }else if (throwable instanceof SSLHandshakeException){
            responseException =  new ResponseException(throwable,Error.SSL_ERROR);
            responseException.message = "证书验证失败";
            return responseException;
        }else if (throwable instanceof SocketTimeoutException) {
            responseException = new ResponseException(throwable, Error.TIMEOUT_ERROR);
            responseException.message = "连接超时";
            return responseException;
        }else if ( throwable instanceof UnknownHostException){
            responseException = new ResponseException(throwable, Error.UNKNOWN_ERROR);
            responseException.message = "No address associated with hostname,请先检查网络是否连接";
            return responseException;
        }else {
            responseException = new ResponseException(throwable,Error.UNKNOWN);
            responseException.message = "未知错误";
            return responseException;
        }
    }

}
