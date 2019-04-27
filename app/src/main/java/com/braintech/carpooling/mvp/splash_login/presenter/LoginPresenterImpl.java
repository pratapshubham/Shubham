package com.braintech.carpooling.mvp.splash_login.presenter;

import android.app.Activity;
import android.util.Log;

import com.braintech.carpooling.BuildConfig;
import com.braintech.carpooling.R;
import com.braintech.carpooling.common.helpers.FcmSession;
import com.braintech.carpooling.common.helpers.Progress;
import com.braintech.carpooling.common.requestresponse.ApiAdapter;
import com.braintech.carpooling.common.requestresponse.Const;
import com.braintech.carpooling.common.utility.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Braintech on 6/14/2018.
 */

public class LoginPresenterImpl implements LoginContractor.LoginPresenter {

    Activity activity;
    LoginContractor.LoginView loginView;

    public LoginPresenterImpl(Activity activity, LoginContractor.LoginView loginView) {
        this.activity = activity;
        this.loginView = loginView;
    }


    @Override
    public void onLoginDataSubmit(String name, String userId, String phone, String email) {
        try {
            ApiAdapter.getInstance(activity);
            login(name,userId,phone,email);
        } catch (ApiAdapter.NoInternetException e) {
            e.printStackTrace();
            loginView.onLoginInternetError();
        }
    }


    private void login(String name, String userId, String phone, String email) {

        Progress.start(activity);
        FcmSession fcmSession = new FcmSession(activity);
        String deviceId = Utils.getDeviceId(activity);
        String token = fcmSession.getFcmToken();

        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject();
            //jsonObject.put("office_id", 39);
            jsonObject.put(Const.PARAM_NAME, name);
            jsonObject.put(Const.PARAM_USER_ID, userId);
            jsonObject.put(Const.PARAM_PHONE_NUMBER, phone);
            jsonObject.put(Const.PARAM_DEVICE_TYPE, "A");
            jsonObject.put(Const.PARAM_DEVICE_ID, deviceId);
            jsonObject.put(Const.PARAM_DEVICE_TOKEN, token);
            jsonObject.put(Const.PARAM_EMAIL, email);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        final RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (jsonObject.toString()));

        Call<LoginModel> getLoginResult = ApiAdapter.getApiService().login("application/json", "no-cache", body);

        getLoginResult.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {

                Progress.stop();

                Log.e("response", "->" + response.body());

                try {
                    //getting whole data from response
                    LoginModel loginModel = response.body();

                    if (loginModel.getStatus() == 1) {
                        loginView.onLoginSuccess(loginModel);

                    } else {
                        loginView.onLoginUnsuccess(loginModel.getMessage());
                    }
                } catch (NullPointerException exp) {
                    if (BuildConfig.DEBUG)
                        exp.printStackTrace();
                    loginView.onLoginUnsuccess(activity.getString(R.string.error_server));
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                Progress.stop();
                loginView.onLoginUnsuccess(activity.getString(R.string.error_server));
            }
        });

    }


}
