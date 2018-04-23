package com.dingmouren.commonlib.constant;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
日期时间的字段
 */
public final class DateConstants {

    public static final int MSEC = 1;
    public static final int SEC  = 1000;
    public static final int MIN  = 60000;
    public static final int HOUR = 3600000;
    public static final int DAY  = 86400000;

    @IntDef({MSEC, SEC, MIN, HOUR, DAY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Unit {
    }
}
