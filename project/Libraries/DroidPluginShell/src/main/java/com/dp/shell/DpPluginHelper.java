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

    private void createObj(Context baseContext) {
        if (mObj == null) {
            DpLoadUtils.copyAssetsFile(baseContext, DpLoadUtils.dexpath, DpLoadUtils.apkPath(baseContext));
            mObj = DpLoadUtils.load(baseContext, "com.morgoo.droidplugin.PluginHelper");
        }
        if (mObj != null) {
            Object instance = null;
            try {
                Method getInstance = mObj.getClass().getDeclaredMethod("getInstance");
                getInstance.setAccessible(true);
                instance = getInstance.invoke(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (instance == null) {
                return;
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
            try {
                Method method = instance.getClass().getDeclaredMethod("applicationOnCreate", Context.class);
                method.setAccessible(true);
                method.invoke(instance, baseContext);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Object mObj;

    private Context mContext;

    public Context getContext() {
        return mContext;
    }

    public void applicationAttachBaseContext(Context baseContext) {
        mContext = baseContext;
        createObj(baseContext);
        Reflection.unseal(baseContext);
        DpCrashHandler.getInstance().register(baseContext);
    }
}
