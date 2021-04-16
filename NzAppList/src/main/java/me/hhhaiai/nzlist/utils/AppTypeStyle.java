package me.hhhaiai.nzlist.utils;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;

/**
 * @Copyright © 2016 sanbo Inc. All rights reserved.
 * @Description: 应用类型判断
 * @Version: 1.0
 * @Create: 2016年5月15日 上午3:19:10
 * @Author: sanbo
 */
public class AppTypeStyle {
    /**
     * 是否是系统软件或者是系统软件的更新软件
     *
     * @return
     */
    public static boolean isSystemApp(PackageInfo pInfo) {
        return ((pInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0);
    }

    /**
     * 是否是系统软件的更新软件
     *
     * @return
     */
    public static boolean isSystemUpdateApp(PackageInfo pInfo) {
        return ((pInfo.applicationInfo.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0);
    }

    /**
     * 用户安装的app
     *
     * @param pInfo
     * @return
     */
    public static boolean isUserApp(PackageInfo pInfo) {
        return (!isSystemApp(pInfo) && !isSystemUpdateApp(pInfo));
    }
}
