package com.example.suli690.myandfix;

import android.app.Application;
import android.os.Environment;
import android.util.Log;

import com.alipay.euler.andfix.patch.PatchManager;

import java.io.File;
import java.io.IOException;


/**
 * Created by suli690 on 2017/1/9.
 */

public class MyApplication extends Application {
    private final static String TAG = MyApplication.class.getName();
    private final static String APATCH_PATH = "1.apatch";

    private PatchManager patchManager;


    @Override
    public void onCreate() {
        super.onCreate();
        patchManager = new PatchManager(this);
        patchManager.init("1");
        patchManager.loadPatch();
        addPatch("1");
    }

    public boolean addPatch(String version) {
        String patchFileString = Environment.getExternalStorageDirectory().getPath() + File.separator + APATCH_PATH;
        File patchFile = new File(patchFileString);
        if (!patchFile.exists()) {
            Log.e(TAG, "file not found: " + patchFileString);
           return false;
        }

        try {
            patchManager.init(version);
            patchManager.addPatch(patchFileString);
            Log.d(TAG, "apatch:" + patchFileString + " added.");
        } catch (IOException e) {
            Log.e(TAG, "", e);
            return false;
        }

        return true;
    }

    public void removePatch() {
        patchManager.removeAllPatch();
    }
}

