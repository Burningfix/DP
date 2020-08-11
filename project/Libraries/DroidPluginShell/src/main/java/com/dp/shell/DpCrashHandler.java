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

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.os.Environment;
import android.util.Log;


import java.io.File;
import java.io.PrintWriter;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author Andy Zhang(zhangyong232@gmail.com)
 */
public class DpCrashHandler implements UncaughtExceptionHandler {

    private static final String TAG = DpCrashHandler.class.getSimpleName();

    private static final DpCrashHandler sMyCrashHandler = new DpCrashHandler();


    private UncaughtExceptionHandler mOldHandler;

    private Context mContext;


    public static DpCrashHandler getInstance() {
        return sMyCrashHandler;
    }

    public void register(Context context) {
        if (context != null) {
            mOldHandler = Thread.getDefaultUncaughtExceptionHandler();
            if (mOldHandler != this) {
                Thread.setDefaultUncaughtExceptionHandler(this);
            }
            mContext = context;
        }
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        Log.e(TAG, "uncaughtException", ex);
    }
}
