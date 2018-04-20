package com.dingmouren.mychat;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2018/4/18.
 * 本地化存储(网易云信)
 */

public class NimPreferences {
    /*用户的id和token*/
    private static final String KEY_USER_ACCOUNT = "account";
    private static final String KEY_USER_TOKEN = "token";

    /*存储和获取用户的id*/
    public static void saveUserAccount(String account) {
        saveString(KEY_USER_ACCOUNT, account);
    }
    public static String getUserAccount() {
        return getString(KEY_USER_ACCOUNT);
    }
    public static void saveUserToken(String token) {
        saveString(KEY_USER_TOKEN, token);
    }
    public static String getUserToken() {
        return getString(KEY_USER_TOKEN);
    }

    private static void saveString(String key, String value) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(key, value);
        editor.commit();
    }

    private static String getString(String key) {
        return getSharedPreferences().getString(key, null);
    }

    static SharedPreferences getSharedPreferences() {
        return NimApplication.sApplication.getSharedPreferences("nim", Context.MODE_PRIVATE);
    }
}
