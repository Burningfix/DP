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

import java.io.File;
import java.lang.reflect.Method;

import me.weishu.reflection.Reflection;

public class PluginHelper {

    private static final String TAG = PluginHelper.class.getSimpleName();

    private volatile static PluginHelper sInstance = null;


    private PluginHelper() {
    }

    public static PluginHelper getInstance() {
        if (sInstance == null) {
            sInstance = new PluginHelper();
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
            try {
                Method method = mObj.getClass().getDeclaredMethod("setPluginManagerService", Class.class);
                method.setAccessible(true);
                method.invoke(mObj, PluginManagerService.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                Method method = mObj.getClass().getDeclaredMethod("setPluginServiceProvider", Class.class);
                method.setAccessible(true);
                method.invoke(mObj, PluginServiceProvider.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                Method method = mObj.getClass().getDeclaredMethod("setShortcutProxyActivity", Class.class);
                method.setAccessible(true);
                method.invoke(mObj, ShortcutProxyActivity.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                Method method = mObj.getClass().getDeclaredMethod("setOActivity", Class.class);
                method.setAccessible(true);
                method.invoke(mObj, OActivity.class);
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
        MyCrashHandler.getInstance().register(baseContext);
    }
}
