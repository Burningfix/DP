package com.dp.shell;

import android.app.Application;
import android.content.Context;

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/8/11 11:32 AM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public class VVV {

    public static void applicationOnCreate(final Application baseContext) {
        DpPluginHelper.getInstance().applicationOnCreate(baseContext);
    }

    public static void applicationAttachBaseContext(Context baseContext, boolean crash) {
        DpPluginHelper.getInstance().applicationAttachBaseContext(baseContext, crash);
    }
}
