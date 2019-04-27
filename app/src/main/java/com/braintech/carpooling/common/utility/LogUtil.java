package com.braintech.carpooling.common.utility;

import android.util.Log;

/**
 * Created by Braintech on 11-09-2017.
 */

public class LogUtil {
    public static final String TAG = "NKD";

    public static void v(String msg) {
        Log.v(TAG, msg);
    }

    public static void d(String msg) {
        Log.d(TAG, msg);
    }

    public static void e(String msg) {
        Log.e(TAG, msg);
    }


}

