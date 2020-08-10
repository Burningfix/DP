
package com.dp.shell;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.lang.reflect.Method;

public class DpPluginManagerService extends Service {

    private Object mObj;

    @Override
    public void onCreate() {
        super.onCreate();
        if (mObj == null) {
            mObj = DpLoadUtils.load(getApplicationContext(), "com.morgoo.droidplugin.PluginManagerService");
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
