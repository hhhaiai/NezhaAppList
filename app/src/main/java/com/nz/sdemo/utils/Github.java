package com.nz.sdemo.utils;

import android.os.Build;
import android.text.TextUtils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;

import ff.jnezha.jnt.cs.GithubHelper;
import me.hhhaiai.nzlist.utils.NzAppLog;

public class Github {
    private static final String TOKEN_GITHUB = "";


    public void report(String pkgName, String path) {
        try {
            //    public static String createFile(String owner, String repo, String path, String token, String contentWillBase64, String commitMsg) {
            GithubHelper.createFile("hhhaiai",
                    "ManagerApk", "/" + getFileName(pkgName) + ".apk",
                    TOKEN_GITHUB,
                    getFileContext(path),
                    getCommitMsg());
        } catch (Throwable e) {
            NzAppLog.e(e);
        }
    }

    private String getFileName(String pkgName) {
        if (Rom.isOppoSystem()) {
            // get [Color OS]  version
            String s = getResultString("getprop ro.build.version.opporom");
            if (TextUtils.isEmpty(s)) {
                s = getResultString("getprop ro.rom.different.version");
            }
            return "oppo-" + pkgName + "-" + Build.VERSION.SDK_INT + "-" + s;
        } else if (Rom.isVivoSystem()) {
            // get [Origin OS]  version
            String s = getResultString("getprop ro.vivo.os.version");
            return "vivo-" + pkgName + "-" + Build.VERSION.SDK_INT + "-" + s;
        }
        return Build.MANUFACTURER + "-" + pkgName + "-" + Build.VERSION.SDK_INT;
    }

    private String getCommitMsg() {
        String deviceInfo = "[" + Build.VERSION.SDK_INT + "] " + Build.MANUFACTURER
                + "-" + Build.MODEL + "-" + Build.BRAND + "-" + Build.BOARD + "-" + Build.FINGERPRINT;
        return getNow() + deviceInfo;
    }


    private String getFileContext(String path) {
        byte[] bs = toByteArray(path);
        return new String(bs);
    }

    private static byte[] toByteArray(String filename) {
        File f = new File(filename);
        FileChannel channel = null;
        FileInputStream fs = null;
        try {
            fs = new FileInputStream(f);
            channel = fs.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate((int) channel.size());
            while ((channel.read(byteBuffer)) > 0) {
            }
            return byteBuffer.array();
        } catch (Throwable igone) {
        } finally {
            safeClose(channel, fs);
        }
        return null;
    }

    public static final String getNow() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ").format(new Date(System.currentTimeMillis()));
    }

    private static String getResultString(String cmd) {
        String result = "";
        Process proc = null;
        BufferedInputStream in = null;
        BufferedReader br = null;
        InputStreamReader is = null;
        InputStream ii = null;
        StringBuilder sb = new StringBuilder();
        DataOutputStream os = null;
        OutputStream pos = null;
        try {
            proc = Runtime.getRuntime().exec("sh");
            pos = proc.getOutputStream();
            os = new DataOutputStream(pos);

            // donnot use os.writeBytes(commmand), avoid chinese charset error
            os.write(cmd.getBytes());
            os.writeBytes("\n");
            os.flush();
            //exitValue
            os.writeBytes("exit\n");
            os.flush();
            ii = proc.getInputStream();
            in = new BufferedInputStream(ii);
            is = new InputStreamReader(in);
            br = new BufferedReader(is);
            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            if (sb.length() > 0) {
                return sb.substring(0, sb.length() - 1);
            }
            result = String.valueOf(sb);
            if (!TextUtils.isEmpty(result)) {
                result = result.trim();
            }
        } catch (Throwable e) {
        } finally {
            safeClose(pos, ii, br, is, in, os);
        }

        return result;
    }

    private static void safeClose(Closeable... closeables) {
        for (int i = 0; i < closeables.length; i++) {
            Closeable ab = closeables[i];
            if (ab != null) {
                try {
                    ab.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
