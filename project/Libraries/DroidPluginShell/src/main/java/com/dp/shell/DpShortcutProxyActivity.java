

package com.dp.shell;

import android.app.Activity;
import android.os.Bundle;
import android.util.Base64;

import java.lang.reflect.Method;


public class DpShortcutProxyActivity extends Activity {

    private Object mObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            if (mObj == null) {
//                mObj = DpLoadUtils.load(getApplicationContext(), "com.morgoo.droidplugin.stub.ShortcutProxyActivity");
                mObj = DpLoadUtils.load(getApplicationContext(), new String(Base64.decode("Y29tLm1vcmdvby5kcm9pZHBsdWdpbi5zdHViLlNob3J0Y3V0UHJveHlBY3Rpdml0eQ==", 0)));
            }
            if (mObj != null) {
                Method method = null;
                try {
                    method = mObj.getClass().getDeclaredMethod("onCreate", Activity.class, Bundle.class);
                    method.setAccessible(true);
                    method.invoke(mObj, this, savedInstanceState);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            finish();
        }
    }

    @Override
    protected void onDestroy() {
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
}
