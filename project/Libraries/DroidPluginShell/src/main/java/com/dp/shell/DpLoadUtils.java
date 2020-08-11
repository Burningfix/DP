package com.dp.shell;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import dalvik.system.DexClassLoader;

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/7/26 6:28 PM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public class DpLoadUtils {
    private static DexClassLoader dexClassLoader = null;

    public static final String dexpath = "core.apk";

    public static <T extends Object> T load(Context context, String name) {
        T obj = null;
        String out = context.getCacheDir() + File.separator + context.getPackageName();
        File file = new File(out);
        if (!file.exists()) {
            file.mkdirs();
        }
        if (dexClassLoader == null) {
            dexClassLoader = new DpClassLoader(apkPath(context), out, null, context.getClassLoader());
        }
        if (dexClassLoader != null) {
            try {
                Class<?> aClass = dexClassLoader.loadClass(name);
                obj = (T) aClass.newInstance();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
        RecursionDeleteFile(file);
        return obj;
    }


    public static String apkPath(Context context) {
        return context.getFilesDir() + File.separator + dexpath;
    }

    public static boolean copyAssetsFile(Context context, String fileName, String toFilePath) {
        boolean isCopy = false;
        AssetManager assetManager = context.getAssets();
        InputStream in = null;
        OutputStream out = null;
        try {
            in = assetManager.open(fileName);
            File outFile = new File(toFilePath);
            out = new FileOutputStream(outFile);
            copyFile(in, out);
            isCopy = true;
        } catch (IOException e) {
            e.printStackTrace();
            isCopy = false;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    isCopy = false;
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    isCopy = false;
                }
            }
        }
        return isCopy;
    }

    public static void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }

    /**
     * 递归删除文件和文件夹
     *
     * @param file 要删除的根目录
     */
    public static void RecursionDeleteFile(File file) {
        if (file.isFile()) {
            file.delete();
            return;
        }
        if (file.isDirectory()) {
            File[] childFile = file.listFiles();
            if (childFile == null || childFile.length == 0) {
                file.delete();
                return;
            }
            for (File f : childFile) {
                RecursionDeleteFile(f);
            }
            file.delete();
        }
    }
}
