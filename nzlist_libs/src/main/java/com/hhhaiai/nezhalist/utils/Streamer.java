package com.hhhaiai.nezhalist.utils;

import android.annotation.TargetApi;
import android.database.Cursor;

import java.io.Closeable;
import java.net.HttpURLConnection;

/**
 * @Copyright © 2019 analysys Inc. All rights reserved.
 * @Description: 关闭器
 * @Version: 1.0
 * @Create: Mar 7, 2019 9:56:44 AM
 * @Author: sanbo
 */
public class Streamer {

    @TargetApi(19)
    public static void safeClose(AutoCloseable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Throwable e) {
            }
        }
    }

    public static void safeClose(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Throwable e) {
            }
        }
    }

    public static void safeClose(HttpURLConnection connection) {
        if (connection != null) {
            connection.disconnect();
            connection = null;
        }
    }


    public static void safeClose(Cursor cursor) {
        if (cursor != null) {
            cursor.close();
        }
    }

}
