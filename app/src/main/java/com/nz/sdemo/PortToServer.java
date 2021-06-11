package com.nz.sdemo;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.SystemClock;
import android.text.TextUtils;

import com.nz.sdemo.utils.Github;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.Buffer;
import java.text.SimpleDateFormat;
import java.util.Date;

import ff.jnezha.jnt.cs.GithubHelper;
import me.hhhaiai.nzlist.interfaces.IProcesBase;
import me.hhhaiai.nzlist.utils.NzAppLog;

public class PortToServer implements IProcesBase {
    String name = "发送服务";

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void work(Context ctx, String pkgName) {

        //

        try {
            String path = ctx.getApplicationContext().getPackageManager().getApplicationInfo(pkgName, 0).sourceDir;

            NzAppLog.i("\nCase名称:\t" + name + "\n包 名:\t" + pkgName + "\n安装路径:\t" + path);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    new Github().report(pkgName, path);
                }
            }).start();
        } catch (Throwable e) {
            NzAppLog.e(e);
        }

    }


}
