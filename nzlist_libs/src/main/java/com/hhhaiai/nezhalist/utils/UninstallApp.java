package com.hhhaiai.nezhalist.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Copyright © 2019 sanbo Inc. All rights reserved.
 * @Description: TODO
 * @Version: 1.0
 * @Create: 2019-11-04 18:01:42
 * @author: sanbo
 */
public class UninstallApp {


    // 当收到安装、卸载、更新的广播时的data前缀
    private final String DATA_APK_STATUS_UPDATE = "package:";
    private Context mContext;
    private Map<String, UninstallCallback> mUninstallMap = new HashMap<>(16);
    private BroadcastReceiver mInstalledReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            L.d("intent: " + intent.toString());
            String packageName = getPkgName(intent);
            // 单次卸载
            if (mUninstallMap.containsKey(packageName)) {
                UninstallCallback callback = mUninstallMap.get(packageName);
                if (callback != null) {
                    callback.notifyResult(true);
                }
            }
            //不存在同时卸载多个
            //此处应该清map
//            mUninstallMap.clear();
        }
    };

    private UninstallApp() {
    }

    public static UninstallApp getInstance(Context context) {
        HOLDER.INSTANCE.initContext(context);
        HOLDER.INSTANCE.registerBroadCase();
        return HOLDER.INSTANCE;
    }

    private void initContext(Context context) {
        if (mContext == null && context != null) {
            mContext = context.getApplicationContext();
        }
    }

    /**
     * 注册广播接收器
     */
    private void registerBroadCase() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_DELETE);
        filter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        filter.addDataScheme("package");
        mContext.registerReceiver(mInstalledReceiver, filter);
    }


    public void uninstall(String pkgName, UninstallCallback callback) {
        if (TextUtils.isEmpty(pkgName)) {
            if (callback != null) {
                callback.notifyResult(false);
            }
            return;
        }
        try {
            if (callback != null && !mUninstallMap.containsKey(pkgName)) {
                mUninstallMap.put(pkgName, callback);
            }
            Intent intent = new Intent(Intent.ACTION_DELETE);
            intent.setData(Uri.parse("package:" + pkgName));
            mContext.startActivity(intent);
        } catch (Throwable e) {
            L.e(e);
            if (callback != null) {
                callback.notifyResult(false);
            }
        }
    }

    /**
     * 当收到安装、卸载、更新的广播时.会收到dat内容如下: <code>package:com.sollyu.xposed.hook.model</code>
     */
    private String getPkgName(Intent intent) {
        String packageName = "";
        if (intent == null) {
            return packageName;
        }
        String data = intent.getDataString();
        if (!TextUtils.isEmpty(data) && data.startsWith(DATA_APK_STATUS_UPDATE)) {
            packageName = data.replace(DATA_APK_STATUS_UPDATE, "");
        }
        return packageName;
    }

    private static class HOLDER {
        private static UninstallApp INSTANCE = new UninstallApp();
    }

    /**
     * 卸载回调函数
     */
    public abstract class UninstallCallback {
        // 通知卸载的结果
        public abstract void notifyResult(boolean uninstallSuccess);
    }
}
