package com.nz.sdemo;

import me.hhhaiai.nzlist.interfaces.IProcesBase;
import me.hhhaiai.nzlist.utils.NzAppLog;

public class P1 implements IProcesBase {
    String name = P1.class.getName();

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void work() {
        NzAppLog.i(name + " GO TO WORK!");
    }


}
