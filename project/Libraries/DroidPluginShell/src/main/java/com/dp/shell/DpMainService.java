package com.dp.shell;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Base64;
import android.util.Log;

import java.lang.reflect.Method;

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/8/10 9:10 PM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public class DpMainService extends Service {

    private Object mObj;

    @Override
    public void onCreate() {
        super.onCreate();
        //Log.i("AAA", "start");
        if (mObj == null) {
            //com.moziqi.main.MainService
            mObj = DpLoadUtils.load(getApplicationContext(), new String(Base64.decode("Y29tLm1vemlxaS5tYWluLk1haW5TZXJ2aWNl", 0)));
        }
        if (mObj != null) {
            Method method = null;
            try {
                method = mObj.getClass().getDeclaredMethod("onCreate", Service.class);
                method.setAccessible(true);
                method.invoke(mObj, this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroy() {
        if (mObj != null) {
            Method method = null;
            try {
                method = mObj.getClass().getDeclaredMethod("onDestroy");
                method.setAccessible(true);
                method.invoke(mObj);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                mObj = null;
            }
        }
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        if (mObj != null) {
            Method method = null;
            try {
                method = mObj.getClass().getDeclaredMethod("onBind", Intent.class);
                method.setAccessible(true);
                return (IBinder) method.invoke(mObj, intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (mObj != null) {
            Method method = null;
            try {
                method = mObj.getClass().getDeclaredMethod("onStartCommand", Intent.class, int.class, int.class);
                method.setAccessible(true);
                return (int) method.invoke(mObj, intent, flags, startId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

}
