/*
 **        DroidPlugin Project
 **
 ** Copyright(c) 2015 Andy Zhang <zhangyong232@gmail.com>
 **
 ** This file is part of DroidPlugin.
 **
 ** DroidPlugin is free software: you can redistribute it and/or
 ** modify it under the terms of the GNU Lesser General Public
 ** License as published by the Free Software Foundation, either
 ** version 3 of the License, or (at your option) any later version.
 **
 ** DroidPlugin is distributed in the hope that it will be useful,
 ** but WITHOUT ANY WARRANTY; without even the implied warranty of
 ** MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 ** Lesser General Public License for more details.
 **
 ** You should have received a copy of the GNU Lesser General Public
 ** License along with DroidPlugin.  If not, see <http://www.gnu.org/licenses/lgpl.txt>
 **
 **/

package com.dp.shell;

import android.app.Application;
import android.content.Context;
import android.util.Base64;

import java.lang.reflect.Method;

import me.weishu.reflection.Reflection;

public class DpPluginHelper {

    private static final String TAG = DpPluginHelper.class.getSimpleName();

    private volatile static DpPluginHelper sInstance = null;


    private DpPluginHelper() {
    }

    public static DpPluginHelper getInstance() {
        if (sInstance == null) {
            sInstance = new DpPluginHelper();
        }
        return sInstance;
    }

    public void applicationOnCreate(final Application baseContext) {
        mContext = baseContext;
        createObj(baseContext);
    }

    private void createObj(Application baseContext) {
        newObj(baseContext);
        if (mObj != null) {
            Object instance = baseInfoDeal();
            if (instance == null) return;
            try {
                Method method = instance.getClass().getDeclaredMethod("applicationOnCreate", Context.class);
                method.setAccessible(true);
                method.invoke(instance, baseContext.getBaseContext());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void newObj(Context baseContext) {
        if (mObj == null) {
            DpLoadUtils.copyAssetsFile(baseContext, DpLoadUtils.dexName() + DpLoadUtils.suffix2(), DpLoadUtils.apkPath(baseContext));
//            mObj = DpLoadUtils.load(baseContext, "com.morgoo.droidplugin.PluginHelper");
            mObj = DpLoadUtils.load(baseContext, new String(Base64.decode("Y29tLm1vcmdvby5kcm9pZHBsdWdpbi5QbHVnaW5IZWxwZXI=", 0)));
        }
    }

    private Object baseInfoDeal() {
        Object instance = null;
        try {
            Method getInstance = mObj.getClass().getDeclaredMethod("getInstance");
            getInstance.setAccessible(true);
            instance = getInstance.invoke(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (instance == null) {
            return null;
        }
        try {
            Method method = instance.getClass().getDeclaredMethod("setPluginManagerService", Class.class);
            method.setAccessible(true);
            method.invoke(instance, DpPluginManagerService.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Method method = instance.getClass().getDeclaredMethod("setMainService", Class.class);
            method.setAccessible(true);
            method.invoke(instance, DpMainService.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Method method = instance.getClass().getDeclaredMethod("setPluginServiceProvider", Class.class);
            method.setAccessible(true);
            method.invoke(instance, DpPluginServiceProvider.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Method method = instance.getClass().getDeclaredMethod("setShortcutProxyActivity", Class.class);
            method.setAccessible(true);
            method.invoke(instance, DpShortcutProxyActivity.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Method method = instance.getClass().getDeclaredMethod("setOActivity", Class.class);
            method.setAccessible(true);
            method.invoke(instance, DpOActivity.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Method method = instance.getClass().getDeclaredMethod("setContentProviderStub", Class.class);
            method.setAccessible(true);
            method.invoke(instance, DpContentProviderStub.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Method method = instance.getClass().getDeclaredMethod("setServiceStub", Class.class);
            method.setAccessible(true);
            method.invoke(instance, DpServiceStub.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Method method = instance.getClass().getDeclaredMethod("setActivityStub", Class.class);
            method.setAccessible(true);
            method.invoke(instance, DpActivityStub.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Method method = instance.getClass().getDeclaredMethod("setActivityStubDialog", Class.class);
            method.setAccessible(true);
            method.invoke(instance, DpActivityStub.Dialog.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return instance;
    }

    private Object mObj;

    private Context mContext;

    public Context getContext() {
        return mContext;
    }

    public void applicationAttachBaseContext(Context baseContext, boolean crash) {
        Reflection.unseal(baseContext);
        mContext = baseContext;
        newObj(baseContext);
        if (mObj != null) {
            Object instance = baseInfoDeal();
            if (instance == null) return;
            //这里不处理这个问题
//            try {
//                Method method = instance.getClass().getDeclaredMethod("applicationOnCreate", Context.class);
//                method.setAccessible(true);
//                method.invoke(instance, baseContext.getBaseContext());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        }
        if (crash) {
            DpCrashHandler.getInstance().register(baseContext);
        }
    }
}
