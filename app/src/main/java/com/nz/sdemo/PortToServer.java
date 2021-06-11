package com.nz.sdemo;

import android.content.Context;

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
        NzAppLog.i(name + "----->" + pkgName);
        //
    }


}
