package com.suli.testupdate;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * Created by suli on 16-10-13.
 */

public class ApkUtils {
    public final static String rootDir = Environment.getExternalStorageDirectory().getPath() + File.separator + "patchCache" + File.separator;
    public final static String downPatchPath = rootDir + "apk.patch";
    public final static String newVersionPath = rootDir + "PatchUpdate_v_2_0.apk";

    /**
     * 提取当前应用的apk包
     */
    public static String getCurrentApkPath(Context context) {
        context = context.getApplicationContext();
        ApplicationInfo appInfo = context.getApplicationInfo();
        String apkPath = appInfo.sourceDir;
        Log.d("suli", "apk path:" + apkPath);
        return apkPath;
    }

    public static void installApk(Context context, String apkPath) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.parse("content://" + apkPath), "application/vnd.android.package-archive");
        context.startActivity(intent);
    }
}
