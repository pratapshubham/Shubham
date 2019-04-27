package com.braintech.carpooling.mvp.splash_login;

import android.app.Service;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import com.braintech.carpooling.R;
import com.braintech.carpooling.common.helpers.AlertDialogHelper;
import com.braintech.carpooling.common.helpers.LoginManagerSession;
import com.braintech.carpooling.common.interfaces.OnClickInterface;
import com.braintech.carpooling.common.requestresponse.ConstIntent;
import com.braintech.carpooling.common.utility.Utils;
import com.braintech.carpooling.mvp.splash_login.presenter.LoginContractor;
import com.braintech.carpooling.mvp.splash_login.presenter.LoginModel;
import com.braintech.carpooling.mvp.splash_login.presenter.LoginPresenterImpl;
import com.braintech.carpooling.mvp.your_rides.activity.YourRidesActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;

public class GetPhoneNumberActivity extends AppCompatActivity implements LoginContractor.LoginView {

    @BindView(R.id.edtTextPhoneNumber)
    AppCompatEditText edtTextPhoneNumber;

    @BindView(R.id.imgViewBackArrow)
    ImageView imgViewBackArrow;

    String number;
    String userEmail;
    String fullName;
    String userId;
    String first_name;
    String last_name;

    LoginPresenterImpl loginPresenterImpl;
    LoginManagerSession loginManagerSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_phone_number);

        ButterKnife.bind(this);

        getIntentData();
        loginManagerSession = new LoginManagerSession();
        loginPresenterImpl = new LoginPresenterImpl(this, this);
    }

    @OnEditorAction(R.id.edtTextPhoneNumber)
    public boolean setEdTextPassword(int actionId) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Service.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(edtTextPhoneNumber.getWindowToken(), 0);
            onClickOk();
            return true;
        }
        return false;
    }


    @Override
    public void onLoginSuccess(LoginModel loginModel) {

        loginManagerSession.createLoginSession(loginModel.getData());
        Intent intent = new Intent(GetPhoneNumberActivity.this, YourRidesActivity.class);
        startActivity(intent);
    }

    @Override
    public void onLoginUnsuccess(String message) {

        AlertDialogHelper.showMessage(this, message);
    }

    @Override
    public void onLoginInternetError() {
        AlertDialogHelper.alertInternetError(this, onRetryLogin);
    }


    @OnClick(R.id.btnSubmit)
    void onClickOk() {
        Utils.hideKeyboardIfOpen(this);
        number = edtTextPhoneNumber.getText().toString().trim();

        //Log.e("number",number);
        if (Utils.isEmptyOrNull(number)) {
            AlertDialogHelper.showMessage(this, getString(R.string.error_phone_number));
        }else if(number.length() != 10){
            AlertDialogHelper.showMessage(this, getString(R.string.error_complete_phone_number));
        } else {
            afterFBSuccess();
        }
    }

    @OnClick(R.id.imgViewBackArrow)
    void onClickBackArrow() {
        finish();
    }


    private void getIntentData() {

        Intent intent = getIntent();
        if (intent.hasExtra(ConstIntent.KEY_USER_ID)) {
            userId = intent.getStringExtra(ConstIntent.KEY_USER_ID);
            fullName = intent.getStringExtra(ConstIntent.KEY_USERNAME);
            userEmail = intent.getStringExtra(ConstIntent.KEY_EMAIL);
            first_name = intent.getStringExtra(ConstIntent.KEY_FIRST_NAME);
            last_name = intent.getStringExtra(ConstIntent.KEY_LAST_NAME);

        }

    }

    private void afterFBSuccess() {
        loginPresenterImpl.onLoginDataSubmit(fullName, userId, number, userEmail);
    }

    OnClickInterface onRetryLogin = new OnClickInterface() {
        @Override
        public void onClick() {
            afterFBSuccess();
        }
    };

}
