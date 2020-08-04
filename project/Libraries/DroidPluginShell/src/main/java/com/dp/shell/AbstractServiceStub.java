

package com.dp.shell;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.lang.reflect.Method;

public abstract class AbstractServiceStub extends Service {

    private static final String TAG = "AbstractServiceStub";

    private Object mObj;

    @Override
    public void onCreate() {
        super.onCreate();
        if (mObj == null) {
            mObj = DpLoadUtils.load(getApplicationContext(), "com.morgoo.droidplugin.stub.AbstractContentProviderStub");
        }
        if (mObj != null) {
            Method method = null;
            try {
                method = mObj.getClass().getDeclaredMethod("onCreate", Service.class);
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
                method.invoke(mObj);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        super.onDestroy();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        if (mObj != null) {
            Method method = null;
            try {
                method = mObj.getClass().getDeclaredMethod("onStart", Intent.class, int.class);
                method.invoke(mObj, intent, startId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        super.onStart(intent, startId);
    }


    @Override
    public void onTaskRemoved(Intent rootIntent) {
        if (mObj != null) {
            Method method = null;
            try {
                method = mObj.getClass().getDeclaredMethod("onTaskRemoved", Intent.class);
                method.invoke(mObj, rootIntent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public IBinder onBind(Intent intent) {
        if (mObj != null) {
            Method method = null;
            try {
                method = mObj.getClass().getDeclaredMethod("onBind", Intent.class);
                return (IBinder) method.invoke(mObj, intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public void onRebind(Intent intent) {
        if (mObj != null) {
            Method method = null;
            try {
                method = mObj.getClass().getDeclaredMethod("onRebind", Intent.class);
                method.invoke(mObj, intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        if (mObj != null) {
            Method method = null;
            try {
                method = mObj.getClass().getDeclaredMethod("onUnbind", Intent.class);
                return (boolean) method.invoke(mObj, intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
