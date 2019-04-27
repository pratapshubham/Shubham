package com.braintech.carpooling.mvp.splash_login.presenter;

/**
 * Created by Braintech on 6/14/2018.
 */

public interface LoginContractor {

    interface LoginView{

        void onLoginSuccess(LoginModel loginModel);
        void onLoginUnsuccess(String message);
        void onLoginInternetError();
    }

    interface LoginPresenter{

        void onLoginDataSubmit(String name,String userId,String phone,String email);
    }



}
