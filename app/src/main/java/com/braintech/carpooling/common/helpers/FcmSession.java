package com.braintech.carpooling.common.helpers;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Braintech on 1/27/2017.
 */

public class FcmSession {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;
    int PRIVATE_MODE = 0;
    public static final String FCM_TOKEN = "fcmToken";
    public static final String FCM_COUNT = "fcmCount";
    public static final String FCM_MESSAGE = "fcmMessage";

    // Shared preferences file name
    private static final String PREF_NAME = "fcm_pref";

    public FcmSession(Context _context) {
        this.context = _context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void saveFcmToken(String token) {
        editor.putString(FCM_TOKEN, token);
        editor.commit();
    }

    public void saveFcmMessage(String message) {
        editor.putString(FCM_MESSAGE, message);
        editor.commit();
    }

    public String getFcmToken() {
        return pref.getString(FCM_TOKEN, null);
    }

    public String getFcmMessage() {
        return pref.getString(FCM_MESSAGE, null);
    }

    public void saveFcmCount(int count) {
        editor.putInt(FCM_COUNT, count);
        editor.commit();
    }

    public int getFcmCount() {
        return pref.getInt(FCM_COUNT,0);
    }

}
