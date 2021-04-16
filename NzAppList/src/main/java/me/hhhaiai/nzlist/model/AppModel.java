package me.hhhaiai.nzlist.model;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

/**
 * @Copyright © 2016 sanbo Inc. All rights reserved.
 * @Description: 数据模型, 用于传递信息
 * @Version: 1.0
 * @Create: 2016年5月15日 上午2:42:21
 * @Author: sanbo
 */
public class AppModel implements Serializable {
    private String mAppName; // app名字
    private String mAppVersionName;// app版本
    private String mAppPackageName; // app包名
    private String mAppLaunchActivity;// 启动app名字
    private String mDataSize;// app数据大小
    private String mCacheSize;// app缓存大小
    private String mAppSize;// app大小
    private String mTotalSize;// app使用空间总大小
    private Drawable mIcon; // app图标
    private String mSortLetters; // 显示数据拼音的首字母
    private Etype mType = Etype.APP_UNKNOW; // app 类型,系统安装还是用户安装

    private String mInternalName;// 内部名字

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(mAppName).append("]").append("--").append(mAppPackageName).append("--")
                .append(mAppVersionName).append("===type==").append(mType);
        return sb.toString();
    }

    public String getInternalName() {
        return mInternalName;
    }

    public void setInternalName(String internalName) {
        this.mInternalName = internalName;
    }

    public String getAppLaunchActivity() {
        return mAppLaunchActivity;
    }

    public void setAppLaunchActivity(String appLaunchActivity) {
        this.mAppLaunchActivity = appLaunchActivity;
    }

    public Drawable getIcon() {
        return mIcon;
    }

    public void setIcon(Drawable image) {
        this.mIcon = image;
    }

    public Etype getType() {
        return mType;
    }

    public void setType(Etype type) {
        this.mType = type;
    }

    public String getAppName() {
        return mAppName;
    }

    public void setAppName(String appName) {
        this.mAppName = appName;
    }

    public String getAppPackageName() {
        return mAppPackageName;
    }

    public void setAppPackageName(String appPackageName) {
        this.mAppPackageName = appPackageName;
    }

    public String getDataSize() {
        return mDataSize;
    }

    public void setDataSize(String dataSize) {
        this.mDataSize = dataSize;
    }

    public String getCacheSize() {
        return mCacheSize;
    }

    public void setCacheSize(String cacheSize) {
        this.mCacheSize = cacheSize;
    }

    public String getAppSize() {
        return mAppSize;
    }

    public void setAppSize(String appSize) {
        this.mAppSize = appSize;
    }

    public String getTotalSize() {
        return mTotalSize;
    }

    public void setTotalSize(String totalSize) {
        this.mTotalSize = totalSize;
    }

    public String getName() {
        return mAppName;
    }

    public void setName(String name) {
        this.mAppName = name;
    }

    public String getSortLetters() {
        return mSortLetters;
    }

    public void setSortLetters(String sortLetters) {
        this.mSortLetters = sortLetters;
    }

    public String getAppVersionName() {
        return mAppVersionName;
    }

    public void setAppVersionName(String appVersionName) {
        this.mAppVersionName = appVersionName;
    }


    /**
     * @Copyright © 2016 sanbo Inc. All rights reserved.
     * @Description: 应用类型，是系统自带还是用户安装，还是系统软件更新
     * @Version: 1.0
     * @Create: 2016年5月15日 上午2:45:21
     * @Author: sanbo
     */
    public enum Etype {
        APP_UNKNOW, APP_SYSTEM/* , APP_SYSTEM_UPDATE */, APP_USER
    }

}
