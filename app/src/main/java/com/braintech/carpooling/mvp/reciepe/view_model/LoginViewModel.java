package com.braintech.carpooling.mvp.reciepe.view_model;

import android.arch.lifecycle.ViewModel;
import android.text.Editable;
import android.text.TextWatcher;

import com.braintech.carpooling.mvp.reciepe.interface_.LoginResultCallbacks;
import com.braintech.carpooling.mvp.reciepe.model.User;

public class LoginViewModel extends ViewModel {

    private User user;
    private LoginResultCallbacks loginResultCallbacks;

    public LoginViewModel(LoginResultCallbacks loginResultCallbacks) {
        this.loginResultCallbacks = loginResultCallbacks;
        user = new User();
    }

    public TextWatcher getEmailTextWatcher(){
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
    }
}
