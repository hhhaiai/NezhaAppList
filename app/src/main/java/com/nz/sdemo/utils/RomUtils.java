package com.nz.sdemo.utils;

import android.os.Build;
import android.text.TextUtils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import me.hhhaiai.nzlist.utils.NzAppLog;

public class RomUtils {

    public static final String[] ROM_360 = {"360", "qiku"};
    public static final String[] ROM_COOLPAD = {"coolpad", "yulong"};
    public static final String[] ROM_GIONEE = {"gionee", "amigo"};
    public static final String[] ROM_GOOGLE = {"google"};
    public static final String[] ROM_HTC = {"htc"};
    public static final String[] ROM_HUAWEI = {"huawei"};
    public static final String[] ROM_LEECO = {"leeco", "letv"};
    public static final String[] ROM_LENOVO = {"lenovo"};
    public static final String[] ROM_LG = {"lg", "lge"};
    public static final String[] ROM_MEIZU = {"meizu"};
    public static final String[] ROM_MOTOROLA = {"motorola"};
    public static final String[] ROM_NUBIA = {"nubia"};
    public static final String[] ROM_ONEPLUS = {"oneplus"};
    public static final String[] ROM_OPPO = {"oppo"};
    public static final String[] ROM_SAMSUNG = {"samsung"};
    public static final String[] ROM_SMARTISAN = {"smartisan"};
    public static final String[] ROM_SONY = {"sony"};
    public static final String[] ROM_VIVO = {"vivo"};
    public static final String[] ROM_XIAOMI = {"xiaomi"};
    public static final String[] ROM_ZTE = {"zte"};
    public static final String VERSION_PROPERTY_360 = "ro.build.uiversion";
    public static final String VERSION_PROPERTY_LENOVO = "ro.lenovo.lvp.version";

    public static final String VERSION_PROPERTY_HUAWEI = "ro.build.version.emui";
    public static final String VERSION_PROPERTY_LEECO = "ro.letv.release.version";
    public static final String VERSION_PROPERTY_NUBIA = "ro.build.rom.id";
    public static final String VERSION_PROPERTY_ONEPLUS = "ro.rom.version";
    public static final String VERSION_PROPERTY_OPPO = "ro.build.version.opporom";
    public static final String VERSION_PROPERTY_OPPO2 = "ro.rom.different.version";
    public static final String VERSION_PROPERTY_VIVO = "ro.vivo.os.build.display.id";
    private static final String VERSION_PROPERTY_VIVO2 = "ro.vivo.os.version";
    public static final String VERSION_PROPERTY_XIAOMI = "ro.build.version.incremental";
    public static final String VERSION_PROPERTY_ZTE = "ro.build.MiFavor_version";
    private static final String VERSION_PROPERTY_SMARTISAN = "ro.smartisan.version";

    public static String getSubVersion() {

        if (isRightRom(ROM_360)) {
            return "360_" + getRomVersion(VERSION_PROPERTY_360);
        } else if (isRightRom(ROM_HUAWEI)) {
            return "EMUI_" + getRomVersion(VERSION_PROPERTY_HUAWEI);
        } else if (isRightRom(ROM_LENOVO)) {
            return "LENOVO_" + getRomVersion(VERSION_PROPERTY_LENOVO);
        } else if (isRightRom(ROM_OPPO)) {
            String s = getRomVersion(VERSION_PROPERTY_OPPO);
            if (TextUtils.isEmpty(s)) {
                s = getRomVersion(VERSION_PROPERTY_OPPO2);
            }
            return "ColorOS_" + s;

        } else if (isRightRom(ROM_VIVO)) {
            String s = getRomVersion(VERSION_PROPERTY_VIVO);
            if (TextUtils.isEmpty(s)) {
                s = getRomVersion(VERSION_PROPERTY_VIVO2);
            }
            return "OriginOS_" + s;
        } else if (isRightRom(ROM_ONEPLUS)) {
            return "ONEPLUS_" + getRomVersion(VERSION_PROPERTY_ONEPLUS);
        } else if (isRightRom(ROM_XIAOMI)) {
            return "MIUI_" + getRomVersion(VERSION_PROPERTY_XIAOMI);
        } else if (isRightRom(ROM_ZTE)) {
            return "ZTE_" + getRomVersion(VERSION_PROPERTY_ZTE);
        } else if (isRightRom(ROM_NUBIA)) {
            return "NUBIA_" + getRomVersion(VERSION_PROPERTY_NUBIA);
        } else if (isRightRom(ROM_LEECO)) {
            return "LEECO_" + getRomVersion(VERSION_PROPERTY_LEECO);
        } else if (isRightRom(ROM_SMARTISAN)) {
            return "SMARTISAN_" + getRomVersion(VERSION_PROPERTY_SMARTISAN);
        }

        return "";
    }

    public static boolean isRightRom(String[] strArr) {
        if (strArr != null && strArr.length > 0) {
            for (int i = 0; i < strArr.length; i++) {
                String v = strArr[i];
                return Build.BOARD.toLowerCase(Locale.CHINA).contains(v)
                        || Build.BRAND.toLowerCase(Locale.CHINA).contains(v)
                        || Build.MANUFACTURER.toLowerCase(Locale.CHINA).contains(v);
            }
            List ss = Arrays.asList(strArr);
        }

        return false;
    }

    public static String getRomVersion(String str) {
        String systemProperty = !TextUtils.isEmpty(str) ? getProp(str) : "";
        if (TextUtils.isEmpty(systemProperty)
                || systemProperty.toLowerCase(Locale.CHINA).equals("unknown")) {
            try {
                systemProperty = getResultString("getprop " + str);
            } catch (Throwable unused) {
            }
        }
        if (TextUtils.isEmpty(systemProperty)) {
            return "";
        }
        return systemProperty;
    }

    public static String getProp(String str) {
        try {
            if (Build.VERSION.SDK_INT < 26) {
                try {
                    Method declaredMethod =
                            Class.forName("android.os.SystemProperties")
                                    .getDeclaredMethod("get", String.class, String.class);
                    declaredMethod.setAccessible(true);
                    return (String) declaredMethod.invoke(null, str, "");
                } catch (Exception unused) {
                    return "";
                }
            } else {
                Method declaredMethod2 =
                        Class.class.getDeclaredMethod(
                                "getDeclaredMethod", String.class, Class[].class);
                declaredMethod2.setAccessible(true);
                Method method =
                        (Method)
                                declaredMethod2.invoke(
                                        Class.forName("android.os.SystemProperties"),
                                        "get",
                                        new Class[]{String.class, String.class});
                method.setAccessible(true);
                return (String) method.invoke(null, str, "");
            }
        } catch (Throwable e) {
            NzAppLog.e(e);
        }
        return "";
    }

    private static String getResultString(String cmd) {
        String result = "";
        Process proc = null;
        BufferedInputStream in = null;
        BufferedReader br = null;
        InputStreamReader is = null;
        InputStream ii = null;
        StringBuilder sb = new StringBuilder();
        DataOutputStream os = null;
        OutputStream pos = null;
        try {
            proc = Runtime.getRuntime().exec("sh");
            pos = proc.getOutputStream();
            os = new DataOutputStream(pos);

            // donnot use os.writeBytes(commmand), avoid chinese charset error
            os.write(cmd.getBytes());
            os.writeBytes("\n");
            os.flush();
            // exitValue
            os.writeBytes("exit\n");
            os.flush();
            ii = proc.getInputStream();
            in = new BufferedInputStream(ii);
            is = new InputStreamReader(in);
            br = new BufferedReader(is);
            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            if (sb.length() > 0) {
                return sb.substring(0, sb.length() - 1);
            }
            result = String.valueOf(sb);
            if (!TextUtils.isEmpty(result)) {
                result = result.trim();
            }
        } catch (Throwable e) {
        } finally {
            safeClose(pos, ii, br, is, in, os);
        }

        return result;
    }

    private static void safeClose(Closeable... closeables) {
        for (int i = 0; i < closeables.length; i++) {
            Closeable ab = closeables[i];
            if (ab != null) {
                try {
                    ab.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
