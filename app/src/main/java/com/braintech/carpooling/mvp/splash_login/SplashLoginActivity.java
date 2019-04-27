



package com.braintech.carpooling.mvp.splash_login;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import com.braintech.carpooling.common.helpers.LoginManagerSession;
import com.braintech.carpooling.common.helpers.Progress;
import com.braintech.carpooling.common.requestresponse.ConstIntent;
import com.braintech.carpooling.mvp.offer_a_ride.activity.SeekBarActivity;
import com.braintech.carpooling.mvp.your_rides.activity.YourRidesActivity;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.braintech.carpooling.R;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SplashLoginActivity extends AppCompatActivity {

    @BindView(R.id.imgViewSplashImage)
    ImageView imgViewSplashImage;

    @BindView(R.id.txtViewFacebookConected)
    AppCompatTextView txtViewFacebookConected;

    private static int SPLASH_TIME_OUT = 3000;

    android.app.AlertDialog dialog = null;

    CallbackManager mCallbackManager;
    String userEmail;
    String fullName;
    String userId;
    String first_name;
    String last_name;

    Handler handler;
    Runnable myRunnable;
    LoginManagerSession loginManagerSession;

    boolean doubleBackToExitPressedOnce = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_login);
        ButterKnife.bind(this);



        loginManagerSession = new LoginManagerSession();
        //getHashKey();

        Boolean isLoggedIn = loginManagerSession.isLoggedIn();
        if (!isLoggedIn) {
            setAnimation();
        }


        performSplashTimeTask();

        //Facebook
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(getApplication());
    }


    public void rateApp()
    {
        try
        {
            Intent rateIntent = rateIntentForUrl("market://details");
            startActivity(rateIntent);
        }
        catch (ActivityNotFoundException e)
        {
            Intent rateIntent = rateIntentForUrl("https://play.google.com/store/apps/details");
            startActivity(rateIntent);
        }
    }

    private Intent rateIntentForUrl(String url)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("%s?id=%s", url, getPackageName())));
        int flags = Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_MULTIPLE_TASK;
        if (Build.VERSION.SDK_INT >= 21)
        {
            flags |= Intent.FLAG_ACTIVITY_NEW_DOCUMENT;
        }
        else
        {
            //noinspection deprecation
            flags |= Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET;
        }
        intent.addFlags(flags);
        return intent;
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finishAffinity();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, getString(R.string.title_back_again_to_exit), Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }



    @OnClick(R.id.txtViewFacebookConected)
    void onClickButtonFacebook() {
        mCallbackManager = CallbackManager.Factory.create();
        Progress.start(this);

        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList(new String[]{"email"}));

        LoginManager.getInstance().registerCallback(mCallbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResults) {

                        Progress.stop();
                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResults.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(
                                            JSONObject object,
                                            GraphResponse response) {

                                        // Application code
                                        userEmail = "";
                                        fullName = "";
                                        userId = "";
                                        first_name = "";
                                        last_name = "";

                                        try {
                                            if (object.has("email"))
                                                userEmail = object.getString("email");
                                            if (object.has("name")){
                                                fullName = object.getString("name");
                                            }
                                            if (object.has("id"))
                                                userId = object.getString("id");
                                            if (object.has("first_name"))
                                                first_name = object.getString("first_name");
                                            if (object.has("last_name"))
                                                last_name = object.getString("last_name");
                                            //userId=object.getString();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                        setIntentDataAndSend();
                                        //log out user before login to server
                                        if (AccessToken.getCurrentAccessToken() != null) {
                                            LoginManager.getInstance().logOut();
                                        }



                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email,gender,first_name,last_name");
                        request.setParameters(parameters);
                        request.executeAsync();

                    }

                    @Override
                    public void onCancel() {
                        if (AccessToken.getCurrentAccessToken() != null) {
                            LoginManager.getInstance().logOut();
                        }
                        Progress.stop();
                        // Log.e("dd", "facebook login canceled");
                    }


                    @Override
                    public void onError(FacebookException e) {
                        if (AccessToken.getCurrentAccessToken() != null) {
                            LoginManager.getInstance().logOut();
                        }
                        e.printStackTrace();
                        Progress.stop();

                    }
                });

    }

    private void setIntentDataAndSend() {

        Intent intentGetPhoneNUmber = new Intent(SplashLoginActivity.this,GetPhoneNumberActivity.class);

        intentGetPhoneNUmber.putExtra(ConstIntent.KEY_USER_ID, userId);
        intentGetPhoneNUmber.putExtra(ConstIntent.KEY_USERNAME, fullName);
        intentGetPhoneNUmber.putExtra(ConstIntent.KEY_EMAIL, userEmail);
        intentGetPhoneNUmber.putExtra(ConstIntent.KEY_FIRST_NAME, first_name);
        intentGetPhoneNUmber.putExtra(ConstIntent.KEY_LAST_NAME, last_name);
        startActivity(intentGetPhoneNUmber);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        mCallbackManager.onActivityResult(requestCode, resultCode, data);

    }


    private void performSplashTimeTask() {

        handler = new Handler();
        myRunnable = new Runnable() {
            public void run() {

                //imgViewSplashImage.animate().y(500.0f);

                boolean isLoggedIn = loginManagerSession.isLoggedIn();
                if(isLoggedIn){
                    Intent intent = new Intent(SplashLoginActivity.this,YourRidesActivity.class);
                    startActivity(intent);
                }else {
                    txtViewFacebookConected.setVisibility(View.VISIBLE);
                }


            }
        };
        handler.postDelayed(myRunnable, SPLASH_TIME_OUT);

    }

    private void setAnimation() {
        Animation animation = new TranslateAnimation(0, 0, 0, -150);
        animation.setDuration(2000);
        animation.setFillAfter(true);
        imgViewSplashImage.startAnimation(animation);

    }





    private void getHashKey() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.braintech.carpooling",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }



}
