package com.nz.sdemo;

import me.hhhaiai.nzlist.interfaces.IProcesBase;
import me.hhhaiai.nzlist.utils.NzAppLog;

public class P3 implements IProcesBase {
    String name = P3.class.getName();

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void work() {
        NzAppLog.i(name + " GO TO WORK!");
    }


}
