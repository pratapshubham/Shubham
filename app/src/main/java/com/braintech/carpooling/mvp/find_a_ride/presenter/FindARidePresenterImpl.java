package com.braintech.carpooling.mvp.find_a_ride.presenter;

import android.app.Activity;
import android.util.Log;

import com.braintech.carpooling.BuildConfig;
import com.braintech.carpooling.R;
import com.braintech.carpooling.common.helpers.LoginManagerSession;
import com.braintech.carpooling.common.helpers.Progress;
import com.braintech.carpooling.common.requestresponse.ApiAdapter;
import com.braintech.carpooling.common.requestresponse.Const;
import com.braintech.carpooling.common.utility.Utils;
import com.braintech.carpooling.mvp.splash_login.presenter.LoginModel;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Braintech on 6/21/2018.
 */

public class FindARidePresenterImpl implements FindARideContractor.FindARidePresenter {

    Activity activity;
    FindARideContractor.FindARideView findARideView;


    public FindARidePresenterImpl(Activity activity, FindARideContractor.FindARideView findARideView){
        this.activity = activity;
        this.findARideView = findARideView;
    }


    @Override
    public void onFindARideDataSubmit(String sourceLatitude, String sourceLongitude, String destinationLatitude, String destinationLongitude, String rideDate, String rideTime, String numberOfPassenger) {
        try {
            ApiAdapter.getInstance(activity);
            findARide(sourceLatitude,sourceLongitude,destinationLatitude,destinationLongitude,rideDate,rideTime,numberOfPassenger);
        }catch (ApiAdapter.NoInternetException e){
            e.printStackTrace();
            findARideView.onFindARideInternetError();
        }


    }

    private void findARide(String sourceLatitude, String sourceLongitude, String destinationLatitude, String destinationLongitude, String rideDate, String rideTime, String numberOfPassenger) {

        Progress.start(activity);

        String userID = String.valueOf(LoginManagerSession.getInstance().getUserData().getUserId());

        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject();
            jsonObject.put(Const.PARAM_USER_ID, userID);
            jsonObject.put(Const.PARAM_SOURCE_LATITUDE, sourceLatitude);
            jsonObject.put(Const.PARAM_SOURCE_LONGITUDE, sourceLongitude);
            jsonObject.put(Const.PARAM_DESTINATION_LATITUDE, destinationLatitude);
            jsonObject.put(Const.PARAM_DESTINATION_LONGITUDE, destinationLongitude);
            jsonObject.put(Const.PARAM_DATE, rideDate);
            //jsonObject.put(Const.PARAM_TIME, rideTime);
            jsonObject.put(Const.PARAM_NUMBER_OF_SEATS, numberOfPassenger);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        final RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (jsonObject.toString()));

        Call<FindARideModel> getLoginOutput = ApiAdapter.getApiService().findARide("application/json", "no-cache", body);

        getLoginOutput.enqueue(new Callback<FindARideModel>() {
            @Override
            public void onResponse(Call<FindARideModel> call, Response<FindARideModel> response) {

                Progress.stop();

                //Log.e("response", "->" + response.body());

                try {
                    //getting whole data from response
                    FindARideModel findARideModel = response.body();

                    if (findARideModel.getStatus() == 1) {
                        findARideView.onFindARideSuccess(findARideModel);

                    } else {
                        findARideView.onFindARideUnSuccess(findARideModel.getMessage());
                    }
                } catch (NullPointerException exp) {
                    if (BuildConfig.DEBUG)
                        exp.printStackTrace();
                    findARideView.onFindARideUnSuccess(activity.getString(R.string.error_server));
                }
            }

            @Override
            public void onFailure(Call<FindARideModel> call, Throwable t) {
                Progress.stop();
                findARideView.onFindARideUnSuccess(activity.getString(R.string.error_server));
            }
        });




    }
}
