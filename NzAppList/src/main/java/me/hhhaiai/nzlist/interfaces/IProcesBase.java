package me.hhhaiai.nzlist.interfaces;

import android.content.Context;

import java.io.Serializable;

public interface IProcesBase extends Serializable {
    public abstract String getName();

    /**
     * 工作
     *
     * @param ctx                安装列表的页面，对外传递的是activity对象
     * @param processPackageName 待处理的包名，即按谁触发的
     */
    public abstract void work(Context ctx, String processPackageName);
}
