package com.suli.testupdate;

/**
 * Created by suli on 16-10-13.
 * 将增量apk包与当前安装的apk包合并,再安装升级
 */

public class Updater {

    static {
        System.loadLibrary("bspatch");
    }

    public static native int bspatch(String oldApk, String newApk, String patch);

}
