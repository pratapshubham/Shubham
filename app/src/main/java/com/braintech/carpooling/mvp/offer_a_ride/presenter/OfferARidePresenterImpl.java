package com.braintech.carpooling.mvp.offer_a_ride.presenter;

import android.app.Activity;
import android.util.Log;

import com.braintech.carpooling.BuildConfig;
import com.braintech.carpooling.R;
import com.braintech.carpooling.common.helpers.LoginManagerSession;
import com.braintech.carpooling.common.helpers.Progress;
import com.braintech.carpooling.common.requestresponse.ApiAdapter;
import com.braintech.carpooling.common.requestresponse.Const;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Braintech on 6/19/2018.
 */

public class OfferARidePresenterImpl implements OfferARideContractor.OfferARidePresenter {

    OfferARideContractor.OfferARideView offerARideView;
    Activity activity;

    public OfferARidePresenterImpl(Activity activity, OfferARideContractor.OfferARideView offerARideView){
        this.activity = activity;
        this.offerARideView = offerARideView;
    }

    @Override
    public void onOfferARideSubmit(String startLoaction, String startLatitude, String startLongitude, String destinationLocation, String destinationLatitude, String destinationLongitude, String journeyDate, String journeyTime, String numberOfPassenger, String stringStopsInBetween) {
        try {
            ApiAdapter.getInstance(activity);
            offerARideDataSubmit(startLoaction,startLatitude,startLongitude,destinationLocation,destinationLatitude,destinationLongitude,journeyDate,journeyTime,numberOfPassenger,stringStopsInBetween);
        } catch (ApiAdapter.NoInternetException e) {
            e.printStackTrace();
            offerARideView.onOfferARideInternetError();
        }
    }

    private void offerARideDataSubmit(String startLoaction, String startLatitude, String startLongitude, String destinationLocation, String destinationLatitude, String destinationLongitude, String journeyDate, String journeyTime, String numberOfPassenger,String stringStopsInBetween) {

        Progress.start(activity);

        String userID = String.valueOf(LoginManagerSession.getInstance().getUserData().getUserId());

        JSONObject jsonObject = null;



        try {
            JSONArray jsonArrayStopsInBetween;
            if(stringStopsInBetween != null){
                jsonArrayStopsInBetween = new JSONArray(stringStopsInBetween);
            }else {
                jsonArrayStopsInBetween = new JSONArray();
            }

            jsonObject = new JSONObject();
            jsonObject.put(Const.PARAM_USER_ID, userID);
            jsonObject.put(Const.PARAM_START_LOCATION, startLoaction);
            jsonObject.put(Const.PARAM_START_LONGITUDE, startLongitude);
            jsonObject.put(Const.PARAM_START_LATITUDE, startLatitude);
            jsonObject.put(Const.PARAM_DESTINATION_LOCATION, destinationLocation);
            jsonObject.put(Const.PARAM_DESTINATION_LATITUDE, destinationLatitude);
            jsonObject.put(Const.PARAM_DESTINATION_LONGITUDE, destinationLongitude);
            jsonObject.put(Const.PARAM_JOURNEY_DATE, journeyDate);
            jsonObject.put(Const.PARAM_JOURNEY_TIME, journeyTime);
            jsonObject.put(Const.PARAM_IS_RETURN_JOURNEY_TYPE, "false");
            jsonObject.put(Const.PARAM_MESSAGE_FOR_PASSENGER, "test");
            jsonObject.put(Const.PARAM_NUMBER_OF_PASSENGER, numberOfPassenger);
            jsonObject.put(Const.PARAM_STOPS_IN_BETWEEN, jsonArrayStopsInBetween);
            //jsonObject.put(Const.PARAM_STOPS_IN_BETWEEN, );
        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        final RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (jsonObject.toString()));

        Call<OfferARideModel> getOfferARideResult = ApiAdapter.getApiService().offerARide("application/json", "no-cache", body);

        getOfferARideResult.enqueue(new Callback<OfferARideModel>() {
            @Override
            public void onResponse(Call<OfferARideModel> call, Response<OfferARideModel> response) {

                Progress.stop();

                Log.e("response", "->" + response.body());

                try {
                    //getting whole data from response
                    OfferARideModel offerARideModel = response.body();

                    if (offerARideModel.getStatus() == 1) {
                        offerARideView.onOfferARideSuccess(offerARideModel);

                    } else {
                        offerARideView.onOfferARideUnsuccess(offerARideModel.getMessage());
                    }
                } catch (NullPointerException exp) {
                    if (BuildConfig.DEBUG)
                        exp.printStackTrace();
                    offerARideView.onOfferARideUnsuccess(activity.getString(R.string.error_server));
                }
            }

            @Override
            public void onFailure(Call<OfferARideModel> call, Throwable t) {
                Progress.stop();
                offerARideView.onOfferARideUnsuccess(activity.getString(R.string.error_server));
            }
        });


    }
}
