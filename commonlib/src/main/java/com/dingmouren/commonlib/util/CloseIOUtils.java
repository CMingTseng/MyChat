package com.dingmouren.commonlib.util;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by dingmouren on 2018/3/22.
 * emial: naildingmouren@gmail.com
 * 关闭IO流的工具类
 closeIO       : 关闭 IO
 closeIOQuietly: 安静关闭 IO
 */

public class CloseIOUtils {
    /**
     * 关闭 IO
     * @param closeables closeables
     */
    public static void closeIO(final Closeable... closeables) {
        if (closeables == null) return;
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 安静关闭 IO.
     * @param closeables closeables
     */
    public static void closeIOQuietly(final Closeable... closeables) {
        if (closeables == null) return;
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException ignored) {
                }
            }
        }
    }
}
