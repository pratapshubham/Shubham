package com.braintech.carpooling.common.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import com.braintech.carpooling.common.application.CarPoolingApplication;
import com.braintech.carpooling.common.utility.PrefUtil;
import com.braintech.carpooling.mvp.splash_login.presenter.LoginModel;
import com.google.gson.Gson;

/**
 * Created by amit on 5/2/2016.
 */
public class LoginManagerSession {

    public static String KEY_USER_DATA = "user_data";
    public static String KEY_USER_DETAIL = "user_detail";
    public static String IS_LOGIN = "is_login";
    public static String USER_PASSWORD = "user_pass";

    private static volatile LoginManagerSession instance;
    //  private static Context ctx;


    /* public static synchronized void initialize(Context applicationContext) {
         ctx = applicationContext;
     }
 */
    public static LoginManagerSession getInstance() {
        if (instance == null) {
            synchronized (LoginManagerSession.class) {
                if (instance == null) {
                    instance = new LoginManagerSession();
                }
            }
        }
        return instance;
    }

    public void logoutUser() {
        // Clearing all data from Shared Preferences
        //PrefUtil.clear(ctx);
        PrefUtil.remove(CarPoolingApplication.getInstance(), KEY_USER_DATA);
        PrefUtil.remove(CarPoolingApplication.getInstance(), IS_LOGIN);
        PrefUtil.remove(CarPoolingApplication.getInstance(), KEY_USER_DETAIL);

    }

    public boolean isLoggedIn() {
        return PrefUtil.getBoolean(CarPoolingApplication.getInstance(), IS_LOGIN, false);
    }

    public void createLoginSession(LoginModel.Data data) {
        PrefUtil.putBoolean(CarPoolingApplication.getInstance(), IS_LOGIN, true);
        PrefUtil.putString(CarPoolingApplication.getInstance(), KEY_USER_DATA, new Gson().toJson(data));
    }


    public LoginModel.Data getUserData() {
        LoginModel.Data data = new Gson().fromJson(PrefUtil.getString(CarPoolingApplication.getInstance(), KEY_USER_DATA, ""), LoginModel.Data.class);
        return data;
    }

}