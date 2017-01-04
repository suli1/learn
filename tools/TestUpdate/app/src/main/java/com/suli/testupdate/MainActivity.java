package com.suli.testupdate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.btn_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
                installApk();
            }
        });

        findViewById(R.id.btn_install_apk).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                installApk();
            }
        });

        ClassLoader classLoader = getClassLoader();
        if (classLoader != null) {
            Log.i(TAG, classLoader.toString());
            while ((classLoader = classLoader.getParent()) != null) {
                Log.i(TAG, classLoader.toString());
            }
        }

    }

    private void update() {
        String oldApkPath = ApkUtils.getCurrentApkPath(this);
        File patchFile = new File(ApkUtils.downPatchPath);
        if (!patchFile.exists()) {
            Toast.makeText(this, "not found patch file:" + ApkUtils.downPatchPath, Toast.LENGTH_SHORT).show();
            return;
        }

        int ret = Updater.bspatch(oldApkPath, ApkUtils.newVersionPath, ApkUtils.downPatchPath);
        Log.d(TAG, "bspatch return:" + ret);
        // TODO: 16-10-13 check md5
    }

    private void installApk() {
        File newFile = new File(ApkUtils.newVersionPath);
        if (newFile.exists()) {
            ApkUtils.installApk(this, ApkUtils.newVersionPath);
        } else {
            Toast.makeText(this, "not found apk at:" + ApkUtils.newVersionPath, Toast.LENGTH_LONG).show();
        }
    }

}
