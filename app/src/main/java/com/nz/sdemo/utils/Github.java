package com.nz.sdemo.utils;

import android.os.Build;
import android.text.TextUtils;

import com.nz.sdemo.BuildConfig;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ff.jnezha.jnt.cs.GithubHelper;
import me.hhhaiai.nzlist.utils.NzAppLog;

public class Github {

    public void report(
            String pkgName, String appLable, String appVersion, String path, String pkgInfo) {

        String appName = getFileName(pkgName, appLable, appVersion);
        String msg = getCommitMsg(pkgInfo);

        String pathUpload = "/" + Build.MANUFACTURER.toLowerCase(Locale.CHINA) + "/" + appName + ".apk";


        NzAppLog.i(
                " GithubHelper.createFile(\"hhhaiai\",\"ManagerApk\", \""
                        + pathUpload + "\", \""
                        + BuildConfig.GITHUB_TOKEN + "\",\"" + path + "\", \"" + msg + "\");");
        GithubHelper.createFile(
                "hhhaiai",
                "ManagerApk",
                pathUpload,
                BuildConfig.GITHUB_TOKEN,
                new File(path),
                msg);
    }

    private String getFileName(String pkgName, String appLable, String appVersion) {

        String result =
                appLable
                        + "[" + pkgName + "]"
                        + appVersion
                        + "-" + Build.MANUFACTURER.toLowerCase(Locale.CHINA)
                        + "[" + Build.VERSION.SDK_INT + "]";
        String subversion = RomUtils.getSubVersion();
        if (!TextUtils.isEmpty(subversion)) {
            result = result + "-" + subversion;
        }

        return result;
    }

    private String getCommitMsg(String pkgInfo) {
        String deviceInfo =
                "["
                        + Build.VERSION.SDK_INT
                        + "]" + Build.MANUFACTURER
                        + "||" + Build.MODEL
                        + "||" + Build.BRAND
                        + "||" + Build.BOARD
                        + "||" + pkgInfo
                        + "||" + Build.FINGERPRINT;
        return getNow() + deviceInfo;
    }

    public static final String getNow() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ")
                .format(new Date(System.currentTimeMillis()));
    }
}
