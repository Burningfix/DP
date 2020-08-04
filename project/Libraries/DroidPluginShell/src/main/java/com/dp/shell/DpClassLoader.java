
package com.dp.shell;

import dalvik.system.DexClassLoader;

public class DpClassLoader extends DexClassLoader {

    public DpClassLoader(String apkfile, String optimizedDirectory, String libraryPath, ClassLoader systemClassLoader) {
        super(apkfile, optimizedDirectory, libraryPath, systemClassLoader);
    }

    @Override
    protected Class<?> loadClass(String className, boolean resolve) throws ClassNotFoundException {
        try {
            Class<?> clazz = findClass(className);
            if (clazz != null) {
                return clazz;
            }
        } catch (ClassNotFoundException e) {
            return super.loadClass(className, resolve);
        }
        return super.loadClass(className, resolve);
    }
}
