package com.moziqi.main;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.morgoo.droidplugin.PluginHelper;
import com.morgoo.droidplugin.hook.handle.IActivityManagerHookHandle;
import com.morgoo.droidplugin.pm.PluginManager;

import java.io.File;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static com.morgoo.helper.compat.PackageManagerCompat.INSTALL_SUCCEEDED;

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/8/10 9:08 PM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public class MainService /*extends Service*/ implements ServiceConnection {


    private final static String TAG = MainService.class.getSimpleName();

    private static Executor executor = Executors.newSingleThreadExecutor();

    private Service mObj;


    public static void start(Context context) {
        Log.i(TAG, "start");
        Intent intent = new Intent(context, PluginHelper.getInstance().getMainService());
        context.startService(intent);
    }

    //@Override
    public void onCreate() {
        //super.onCreate();
        Log.i(TAG, "onCreate");
        if (PluginManager.getInstance().isConnected()) {
            Log.i(TAG, "isConnected.startLoad");
            startLoad();
        } else {
            PluginManager.getInstance().addServiceConnection(this);
        }
    }

    public void onCreate(Service service) {
        mObj = service;
        onCreate();
    }

    private void startLoad() {
        Log.i(TAG, "to startLoad");
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String appApk = "ApiTest-release.apk";
                    String packName = "com.example.ApiTest";
                    Log.i(TAG, "startLoad Thread");
                    PackageInfo packageInfo = PluginManager.getInstance().getPackageInfo(packName, 0);
                    if (packageInfo == null) {
                        Log.i(TAG, "packageInfo null");
                        PluginManager.getInstance().installPackage(new File(Environment.getExternalStorageDirectory(), appApk).getAbsolutePath(), 0);
                    }
                    Thread.sleep(1000);
                    packageInfo = PluginManager.getInstance().getPackageInfo(packName, 0);
                    if (packageInfo == null) {
                        Log.i(TAG, "packageInfo 2222 null");
                        PluginManager.getInstance().installPackage(new File(Environment.getExternalStorageDirectory(), appApk).getAbsolutePath(), 0);
                    }
                    PackageManager pm = mObj.getPackageManager();
                    Intent intent = pm.getLaunchIntentForPackage(packName);
                    String sourceDir = pm.getApplicationInfo(mObj.getPackageName(), 0).sourceDir;
                    String publicSourceDir = pm.getApplicationInfo(mObj.getPackageName(), 0).publicSourceDir;
                    if (intent != null) {
                        if (packageInfo != null) {
                            Log.i(TAG, "start " + packageInfo.packageName + "@" + intent);
                        }
                        intent.putExtra("sourceDir", sourceDir);
                        intent.putExtra("publicSourceDir", publicSourceDir);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mObj.startActivity(intent);
                    } else {
                        PluginManager.getInstance().installPackage(new File(Environment.getExternalStorageDirectory(), appApk).getAbsolutePath(), 0);
                        intent = pm.getLaunchIntentForPackage(packName);
                        if (intent != null) {
                            intent.putExtra("sourceDir", sourceDir);
                            intent.putExtra("publicSourceDir", publicSourceDir);
                            if (packageInfo != null) {
                                Log.i(TAG, "2222 - start " + packageInfo.packageName + "@" + intent);
                            }
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            mObj.startActivity(intent);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Nullable
    //@Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    //@Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //这里要处理IntentService
        return Service.START_NOT_STICKY;
    }

    //@Override
    public void onDestroy() {
        PluginManager.getInstance().removeServiceConnection(this);
        //super.onDestroy();
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        Log.i(TAG, "onServiceDisconnected.startLoad");
        startLoad();
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }

}
