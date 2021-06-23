package com.nz.sdemo;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.nz.sdemo.utils.Github;

import me.hhhaiai.nzlist.interfaces.IProcesBase;
import me.hhhaiai.nzlist.utils.NzAppLog;

public class PortToServer implements IProcesBase {
    String name = "发送服务";

    @Override
    public String getName() {
        return name;
    }
   private CharSequence appName="";
    @Override
    public void work(final Context ctx, String pkgName) {


        try {

            String path = ctx.getApplicationContext().getPackageManager().getApplicationInfo(pkgName, 0).sourceDir;
            StringBuffer sb = new StringBuffer();
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pInfo = pm.getPackageInfo(pkgName, 0);

            try {
                appName=pInfo.applicationInfo.loadLabel(pm);
                sb.append("-").append(appName);
            } catch (Throwable e) {
            }
            String version=pInfo.versionName+"|"+pInfo.versionCode;
            sb.append("-").append(pInfo.versionName).append("_").append(pInfo.versionCode);
            final String info = sb.toString();

            NzAppLog.i("\nCase名称:\t" + name + "\n包 名:\t" + pkgName + "\n安装路径:\t" + path);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    new Github().report(pkgName,appName+"",version,
                            path, info);
                }
            }).start();
        } catch (Throwable e) {
            NzAppLog.e(e);
        }

    }


}
