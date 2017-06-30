package com.refactor.sweed.sweedversiontwo.util;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.orhanobut.hawk.Hawk;

/**
 * Created by andrei.istrate on 12.06.2017.
 */

public class HelperClass {

    public static boolean hasAllPermissionsGranted(int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }

    public static void clearBackStack(Activity activity) {
        FragmentManager manager = ((AppCompatActivity) activity).getSupportFragmentManager();
        if (manager.getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry first = manager.getBackStackEntryAt(0);
            try {
                manager.popBackStack(first.getId(), android.app.FragmentManager.POP_BACK_STACK_INCLUSIVE);
            } catch (IllegalStateException ignored) {
                // There's no way to avoid getting this if saveInstanceState has already been called.
            }
        }
    }

    public static void hawkInit(Activity activity)
    {
        Hawk.init(activity).build();
    }
}
