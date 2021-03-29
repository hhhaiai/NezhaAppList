package com.gooker.bloomingvioletssimple.utils;

import android.database.Cursor;

import java.net.HttpURLConnection;

/**
 * @Copyright © 2019 analysys Inc. All rights reserved.
 * @Description: 关闭器
 * @Version: 1.0
 * @Create: Mar 7, 2019 9:56:44 AM
 * @Author: sanbo
 */
public class Streamer {

    public static void safeClose(AutoCloseable closeable) {
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

    public static void safeClose(Process proc) {
        if (proc != null) {
            proc.destroy();
            proc = null;
        }

    }

    public static void safeClose(ProcessBuilder pb) {
        if (pb != null) {
            pb.directory();
            pb = null;
        }

    }

    public static void safeClose(Cursor cursor) {
        if (cursor != null) {
            cursor.close();
        }
    }

}
