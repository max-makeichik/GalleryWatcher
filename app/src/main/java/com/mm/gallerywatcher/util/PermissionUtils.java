package com.mm.gallerywatcher.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by Maksim Makeychik on 04.02.2018.
 */

public class PermissionUtils {

    public static boolean hasLocationPermissions(Context context) {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED;

    }

    public static void requestLocationPermissions(Activity activity, int code) {
        String[] permissions = new String[] { Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION };
        ActivityCompat.requestPermissions(activity, permissions, code);
    }

    public static boolean hasStorageReadPermissions(Context context) {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED;

    }

    public static void requestStorageReadPermissions(Activity activity, int code) {
        String[] permissions = new String[] { Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE };
        ActivityCompat.requestPermissions(activity, permissions, code);
    }

    public static boolean hasStorageWritePermissions(Context context) {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED;

    }

    public static void requestStorageWritePermissions(Activity activity, int code) {
        String[] permissions = new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE };
        ActivityCompat.requestPermissions(activity, permissions, code);
    }

}
