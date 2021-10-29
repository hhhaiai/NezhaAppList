package me.hhhaiai.nzlist.utils;

import android.database.Cursor;

import java.io.Closeable;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.nio.channels.FileLock;

/**
 * @Copyright © 2019 analysys Inc. All rights reserved.
 * @Description: 关闭器
 * @Version: 1.0
 * @Create: Mar 7, 2019 9:56:44 AM
 * @Author: sanbo
 */
public class Streamer {

    /**
     * java 对象关闭器.
     *
     * @param os 可关闭的对象，如I/O类，HttpURLConnection 等
     */
    public static void safeClose(Object... os) {
        if (os != null && os.length > 0) {
            for (Object o : os) {
                if (o != null) {
                    try {
                        if (o instanceof HttpURLConnection) {
                            ((HttpURLConnection) o).disconnect();
                        } else if (o instanceof Closeable) {
                            ((Closeable) o).close();
                        } else if (o instanceof FileLock) {
                            ((FileLock) o).release();
                        } else if (o instanceof Closeable) {
                            ((Closeable) o).close();
                        } else if (o instanceof Cursor) {
                            ((Cursor) o).close();
                        }
                        Method close = o.getClass().getDeclaredMethod("close");
                        if (close != null) {
                            close.invoke(o);
                        }
                    } catch (Throwable e) {
                    }
                }
            }
        }
    }
}
