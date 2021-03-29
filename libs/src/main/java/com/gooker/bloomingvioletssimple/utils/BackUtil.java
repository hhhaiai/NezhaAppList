package com.gooker.bloomingvioletssimple.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.net.Uri;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @Copyright © 2019 sanbo Inc. All rights reserved.
 * @Description: TODO
 * @Version: 1.0
 * @Create: 2019-11-04 17:41:04
 * @author: sanbo
 */
public class BackUtil {


    /**
     * 备份文件
     *
     * @param context
     * @param packageName
     * @return </p></>
     * 1: 备份成功
     * 2: 备份失败
     * 3: 文件已经存在
     */
    public static int backupApp(Context context, String packageName) {

        String dir = "/sdcard/";

        int resultCode = 0;
        String oldFile;
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            PackageInfo packageInfo = context.getApplicationContext().getPackageManager().getPackageInfo(packageName, 0);
            ApplicationInfo appInfo = context.getApplicationContext().getPackageManager().getApplicationInfo(
                    packageName, 0);
            oldFile = appInfo.sourceDir;
            File in = new File(oldFile);
            File file = new File(dir);
            if (!file.exists()) {
                file.mkdirs();
            }

            String filePath = packageName + "-" + packageInfo.versionName + ".apk";

            File out = new File(dir, filePath);
            boolean isCreat = out.exists();
            if (!isCreat) {
                boolean isOK = out.createNewFile();
                if (isOK) {
                    resultCode = 1;
                    scanFile(context, filePath);
                } else {
                    resultCode = 2;
                }
            } else {
                resultCode = 3;
            }

            fis = new FileInputStream(in);
            fos = new FileOutputStream(out);

            int count;
            //文件太大的话，我觉得需要修改
            byte[] buffer = new byte[1024 * 1024];
            while ((count = fis.read(buffer)) > 0) {
                fos.write(buffer, 0, count);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return 2;
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                }
            }
            if (fos != null) {
                try {
                    fos.flush();
                } catch (IOException e) {
                }
                try {
                    fos.close();
                } catch (IOException e) {
                }
            }
        }
        return resultCode;
    }

    private static void scanFile(Context context, String filePath) {
        Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        scanIntent.setData(Uri.fromFile(new File(filePath)));
        context.sendBroadcast(scanIntent);
    }
}
