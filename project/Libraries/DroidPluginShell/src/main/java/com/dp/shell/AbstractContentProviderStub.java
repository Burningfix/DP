
package com.dp.shell;

import android.annotation.TargetApi;
import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;

import java.lang.reflect.Method;
import java.util.ArrayList;

public abstract class AbstractContentProviderStub extends ContentProvider {

    private static final String TAG = AbstractContentProviderStub.class.getSimpleName();

    private Object mObj;


    @Override
    public boolean onCreate() {
        if (mObj == null) {
            mObj = DpLoadUtils.load(PluginHelper.getInstance().getContext(), "com.morgoo.droidplugin.stub.AbstractContentProviderStub");
        }
        return true;
    }


    @Override
    public Cursor query(Uri uri, String[] projection,
                        String selection, String[] selectionArgs, String sortOrder) {
        if (mObj != null) {
            Method method = null;
            try {
                method = mObj.getClass().getDeclaredMethod("query", Uri.class, String[].class, String.class, String[].class, String.class);
                method.setAccessible(true);
                return (Cursor) method.invoke(mObj, uri, projection, selection, selectionArgs, sortOrder);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    @Override
    public String getType(Uri uri) {
        if (mObj != null) {
            Method method = null;
            try {
                method = mObj.getClass().getDeclaredMethod("getType", Uri.class);
                method.setAccessible(true);
                return (String) method.invoke(mObj, uri);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        if (mObj != null) {
            Method method = null;
            try {
                method = mObj.getClass().getDeclaredMethod("insert", Uri.class, ContentValues.class);
                method.setAccessible(true);
                return (Uri) method.invoke(mObj, uri, contentValues);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        if (mObj != null) {
            Method method = null;
            try {
                method = mObj.getClass().getDeclaredMethod("delete", Uri.class, String.class, String[].class);
                method.setAccessible(true);
                return (int) method.invoke(mObj, uri, selection, selectionArgs);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        if (mObj != null) {
            Method method = null;
            try {
                method = mObj.getClass().getDeclaredMethod("update", Uri.class, String.class, String[].class);
                method.setAccessible(true);
                return (int) method.invoke(mObj, uri, selection, selectionArgs);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    @TargetApi(VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public Bundle call(String method, String arg, Bundle extras) {
        if (mObj != null) {
            Method method1 = null;
            try {
                method1 = mObj.getClass().getDeclaredMethod("call", String.class, String.class, Bundle.class);
                method1.setAccessible(true);
                return (Bundle) method1.invoke(mObj, method, arg, extras);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return super.call(method, arg, extras);
    }


    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        if (mObj != null) {
            Method method = null;
            try {
                method = mObj.getClass().getDeclaredMethod("bulkInsert", Uri.class, ContentValues[].class);
                method.setAccessible(true);
                return (int) method.invoke(mObj, uri, values);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return super.bulkInsert(uri, values);
    }

    @Override
    public ContentProviderResult[] applyBatch(ArrayList<ContentProviderOperation> operations) throws OperationApplicationException {
//TODO applyBatch转发
//        if (operations != null && operations.size() > 0) {
//            ArrayList<ContentProviderOperation> OldOperations = new ArrayList<ContentProviderOperation>();
//            Map<String, ArrayList<ContentProviderOperation>> newOperations = new HashMap<String, ArrayList<ContentProviderOperation>>();
//            for (ContentProviderOperation operation : operations) {
//                Uri uri = operation.getUri();
//                String targetAuthority = uri.getQueryParameter(Env.EXTRA_TARGET_AUTHORITY);
//                if (!TextUtils.isEmpty(targetAuthority) && !TextUtils.equals(targetAuthority, uri.getAuthority())) {
//                    try {
//                        Uri newUri = buildNewUri(uri, targetAuthority);
//                        FieldUtils.writeField(operation, "mUri", newUri, true);
//                        ArrayList<ContentProviderOperation> newOps = newOperations.get(targetAuthority);
//                        if (newOps == null) {
//                            newOps = new ArrayList<ContentProviderOperation>(1);
//                            newOps.add(operation);
//                            newOperations.put(targetAuthority, newOps);
//                        } else {
//                            newOps.add(operation);
//                        }
//                    } catch (IllegalAccessException e) {
//                        handleExpcetion(e);
//                    }
//                } else {
//                    OldOperations.add(operation);
//                }
//            }
//
//            if (newOperations.size() > 0) {
//                ArrayList<ContentProviderResult> results = new ArrayList<ContentProviderResult>(operations.size());
//                for (String authority : newOperations.keySet()) {
//                    ContentProviderClient client = getContentProviderClient(authority);
//                    ArrayList<ContentProviderOperation> contentProviderOperations = newOperations.get(authority);
//                    if (contentProviderOperations.size() > 0) {
//                        ContentProviderResult[] rs = client.applyBatch(contentProviderOperations);
//                        for (ContentProviderResult r : rs) {
//                            results.add(r);
//                        }
//                    }
//                }
//                //这一步必须要在主线程中执行，这里还有bug
//
//            }
//        }
        return super.applyBatch(operations);
    }


}
