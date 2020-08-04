package com.example.TestPlugin;

import android.app.Application;
import android.content.Context;

import com.dp.shell.PluginHelper;

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/8/4 9:39 PM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        PluginHelper.getInstance().applicationOnCreate(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        PluginHelper.getInstance().applicationAttachBaseContext(this);
    }
}
