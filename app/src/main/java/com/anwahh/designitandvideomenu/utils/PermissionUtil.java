package com.anwahh.designitandvideomenu.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;

public class PermissionUtil
{
    private static final String TAG = "efun_PermissionUtil";

    public static void requestPermission(Activity activity, String permission, int requestCode)
    {
        checkAndroidRequestPermissions(activity, new String[] { permission }, requestCode);
    }

    public static void requestPermissions(Activity activity, String[] permissions, int requestCode)
    {
        checkAndroidRequestPermissions(activity, permissions, requestCode);
    }

    private static boolean checkAndroidRequestPermissions(Activity activity, String[] permissions, int requestCode)
    {
        String[] p = hasSelfPermissionFilter(activity, permissions);
        Log.i("efun_PermissionUtil", "has not permissions length:" + p.length);
        if (p.length > 0)
        {
            ActivityCompat.requestPermissions(activity, p, requestCode);

            return false;
        }
        return true;
    }
    @TargetApi(Build.VERSION_CODES.M)
    public static int checkAndroidPermission(@NonNull Activity activity, @NonNull String permission){
        if(getCurrentAndroidVersion() >= Build.VERSION_CODES.M) {
            return activity.checkSelfPermission(permission);
        }else {
            return 0;
        }
    }
    public static int getCurrentAndroidVersion(){
        return Build.VERSION.SDK_INT;
    }
    static final String[] PERMISSIONS_PHONE = { "android.permission.READ_PHONE_STATE" };
    static final String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE  };
    static final String[] PERMISSIONS_ACCOUNTS = { "android.permission.GET_ACCOUNTS" };
    static final String[] PERMISSIONS_CAMERA = { "android.permission.CAMERA" };
    static final String[] PERMISSIONS_PHONE_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE };
    static final String[] PERMISSIONS_PHONE_STORAGE_CONTACTS = {
            "android.permission.READ_PHONE_STATE",
            "android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.GET_ACCOUNTS" };

    public static boolean requestPermissions_PHONE(Activity activity, int requestCode)
    {
        return checkAndroidRequestPermissions(activity, PERMISSIONS_PHONE, requestCode);
    }

    public static boolean requestPermissions_STORAGE(Activity activity, int requestCode)
    {
        return checkAndroidRequestPermissions(activity, PERMISSIONS_STORAGE, requestCode);
    }

    public static boolean requestPermissions_CONTACTS(Activity activity, int requestCode)
    {
        return checkAndroidRequestPermissions(activity, PERMISSIONS_ACCOUNTS, requestCode);
    }

    public static boolean requestPermissions_PHONE_STORAGE(Activity activity, int requestCode)
    {
        return checkAndroidRequestPermissions(activity, PERMISSIONS_PHONE_STORAGE, requestCode);
    }

    public static boolean requestPermissions_CAMERA(Activity activity, int requestCode)
    {
        return checkAndroidRequestPermissions(activity, PERMISSIONS_CAMERA, requestCode);
    }

    public static boolean requestPermissions_PHONE_STORAGE_CONTACTS(Activity activity, int requestCode)
    {
        return checkAndroidRequestPermissions(activity, PERMISSIONS_PHONE_STORAGE_CONTACTS, requestCode);
    }

    public static boolean verifyPermissions(int[] grantResults)
    {
        int[] arrayOfInt = grantResults;int j = grantResults.length;
        for (int i = 0; i < j; i++)
        {
            int result = arrayOfInt[i];
            if (result != 0) {
                return false;
            }
        }
        return true;
    }

    @SuppressLint({"NewApi"})
    public static boolean hasSelfPermission(Activity activity, String[] permissions)
    {
        if (!moreThan23()) {
            return true;
        }
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(activity, permission) != 0) {
                return false;
            }
        }
        return true;
    }

    @SuppressLint({"NewApi"})
    public static String[] hasSelfPermissionFilter(Activity activity, String[] permissions)
    {
        if (!moreThan23()) {
            return new String[0];
        }
        ArrayList<String> a = new ArrayList();
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(activity, permission) != 0) {
                a.add(permission);
            }
        }
        if (a.isEmpty()) {
            return new String[0];
        }
        String[] contents = new String[a.size()];
        return (String[])a.toArray(contents);
    }

    @SuppressLint({"NewApi"})
    public static boolean hasSelfPermission(Activity activity, String permission)
    {
        if (!moreThan23()) {
            return true;
        }
        return ActivityCompat.checkSelfPermission(activity, permission) == 0;
    }

    public static boolean hasSelfPermission(Context context, String permission)
    {
        if (!moreThan23()) {
            return true;
        }
        return ActivityCompat.checkSelfPermission(context, permission) == 0;
    }

    public static boolean moreThan23()
    {
        if (Build.VERSION.SDK_INT < 23) {
            return false;
        }
        return true;
    }
}

