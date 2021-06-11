package com.nz.sdemo.utils;

import android.os.Build;

public class Rom {

    private static Boolean isOppo = null;

    /**
     * 判断是否是oppo
     *
     * @return
     */
    public static boolean isOppoSystem() {
        if (isOppo == null) {
            isOppo = Build.MANUFACTURER.toUpperCase().contains("OPPO");
        }
        return isOppo;
    }

    private static Boolean isVivo = null;

    public static boolean isVivoSystem() {
        if (isVivo == null) {
            isVivo = Build.MANUFACTURER.toUpperCase().contains("VIVO");
        }
        return isVivo;
    }

}
