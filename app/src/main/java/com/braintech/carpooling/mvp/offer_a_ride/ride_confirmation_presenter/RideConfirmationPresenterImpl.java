package com.braintech.carpooling.mvp.offer_a_ride.ride_confirmation_presenter;

import android.app.Activity;

import com.braintech.carpooling.R;
import com.braintech.carpooling.common.helpers.LoginManagerSession;
import com.braintech.carpooling.common.helpers.Progress;
import com.braintech.carpooling.common.requestresponse.ApiAdapter;
import com.braintech.carpooling.common.requestresponse.Const;
import com.braintech.carpooling.mvp.your_rides.offer_a_ride_presenter.OfferARideModel;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Braintech on 6/27/2018.
 */

public class RideConfirmationPresenterImpl implements RideConfirmationContractor.RideConfirmationPresenter {

    Activity activity;
    RideConfirmationContractor.RideConfirmationView rideConfirmationView;

    public RideConfirmationPresenterImpl(Activity activity, RideConfirmationContractor.RideConfirmationView rideConfirmationView) {
        this.activity = activity;
        this.rideConfirmationView = rideConfirmationView;
    }

    @Override
    public void onRideAccept(String rideScheduledId) {

        try {
            ApiAdapter.getInstance(activity);
            rideConfirm(rideScheduledId);
        }catch (ApiAdapter.NoInternetException e){
            e.printStackTrace();
            rideConfirmationView.onInternetErrorRideAccept();
        }


    }

    @Override
    public void onRideCancel(String rideScheduledId) {

        try {
            ApiAdapter.getInstance(activity);
            rideCancel(rideScheduledId);
        }catch (ApiAdapter.NoInternetException e){
            e.printStackTrace();
            rideConfirmationView.onInternetErrorRideCancel();
        }
    }

    private void rideCancel(String rideScheduledId) {

        Progress.start(activity);

        String userID = String.valueOf(LoginManagerSession.getInstance().getUserData().getUserId());

        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject();
            jsonObject.put(Const.PARAM_USER_ID, userID);
            jsonObject.put(Const.PARAM_RIDE_SCHEDULED_ID, rideScheduledId);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        final RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (jsonObject.toString()));

        Call<RideConfirmationModel> getListOfferARide = ApiAdapter.getApiService().cancelARide("application/json", "no-cache", body);

        getListOfferARide.enqueue(new Callback<RideConfirmationModel>() {
            @Override
            public void onResponse(Call<RideConfirmationModel> call, Response<RideConfirmationModel> response) {

                Progress.stop();

                // Log.e("response", "->" + response.body());

                /*try {
                    //getting whole data from response
                    RideConfirmationModel offerARideModel = response.body();

                    if (offerARideModel.getStatus() == 1) {
                        rideConfirmationView.onSuccessRideCancel(offerARideModel);

                    } else {
                        rideConfirmationView.onUnSuccessRideCancel(offerARideModel.getMessage());
                    }
                } catch (NullPointerException exp) {
                    exp.printStackTrace();
                    rideConfirmationView.onUnSuccessRideCancel(activity.getString(R.string.error_server));
                }*/
            }

            @Override
            public void onFailure(Call<RideConfirmationModel> call, Throwable t) {
                Progress.stop();
                t.printStackTrace();
                rideConfirmationView.onUnSuccessRideCancel(activity.getString(R.string.error_server));
            }
        });




    }


    private void rideConfirm(String rideScheduledId) {

        Progress.start(activity);

        String userID = String.valueOf(LoginManagerSession.getInstance().getUserData().getUserId());

        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject();
            jsonObject.put(Const.PARAM_USER_ID, userID);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        final RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (jsonObject.toString()));

        Call<RideConfirmationModel> getListOfferARide = ApiAdapter.getApiService().acceptARide("application/json", "no-cache", body);

        getListOfferARide.enqueue(new Callback<RideConfirmationModel>() {
            @Override
            public void onResponse(Call<RideConfirmationModel> call, Response<RideConfirmationModel> response) {

                Progress.stop();

                // Log.e("response", "->" + response.body());

                /*try {
                    //getting whole data from response
                    RideConfirmationModel offerARideModel = response.body();

                    if (offerARideModel.getStatus() == 1) {
                        rideConfirmationView.onSuccessRideAccept(offerARideModel);

                    } else {
                        rideConfirmationView.onUnSuccessRideAccept(offerARideModel.getMessage());
                    }
                } catch (NullPointerException exp) {
                    exp.printStackTrace();
                    rideConfirmationView.onUnSuccessRideAccept(activity.getString(R.string.error_server));
                }*/
            }

            @Override
            public void onFailure(Call<RideConfirmationModel> call, Throwable t) {
                Progress.stop();
                t.printStackTrace();
                rideConfirmationView.onUnSuccessRideAccept(activity.getString(R.string.error_server));
            }
        });




    }

}
