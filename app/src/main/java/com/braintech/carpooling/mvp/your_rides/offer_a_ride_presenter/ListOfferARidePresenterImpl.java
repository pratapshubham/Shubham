package com.braintech.carpooling.mvp.your_rides.offer_a_ride_presenter;

import android.app.Activity;

import com.braintech.carpooling.R;
import com.braintech.carpooling.common.helpers.LoginManagerSession;
import com.braintech.carpooling.common.helpers.Progress;
import com.braintech.carpooling.common.requestresponse.ApiAdapter;
import com.braintech.carpooling.common.requestresponse.Const;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Braintech on 6/21/2018.
 */

public class ListOfferARidePresenterImpl implements ListOfferARideContractor.ListOfferARidePresenter {

    Activity activity;
    ListOfferARideContractor.ListOfferARideView listOfferARideView;

    public ListOfferARidePresenterImpl(Activity activity, ListOfferARideContractor.ListOfferARideView listOfferARideView){
        this.activity = activity;
        this.listOfferARideView = listOfferARideView;
    }

    @Override
    public void listOfferARideGetData() {

        try {
            ApiAdapter.getInstance(activity);
            listOfferARide();
        }catch (ApiAdapter.NoInternetException e){
            e.printStackTrace();
            listOfferARideView.onListOfferARideInternetError();
        }

    }

    private void listOfferARide() {

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

        Call<OfferARideModel> getListOfferARide = ApiAdapter.getApiService().listOfferARide("application/json", "no-cache", body);

        getListOfferARide.enqueue(new Callback<OfferARideModel>() {
            @Override
            public void onResponse(Call<OfferARideModel> call, Response<OfferARideModel> response) {

                Progress.stop();

               // Log.e("response", "->" + response.body());

                try {
                    //getting whole data from response
                    OfferARideModel offerARideModel = response.body();

                    if (offerARideModel.getStatus() == 1) {
                        listOfferARideView.onListOfferARideSuccess(offerARideModel);
                    } else {
                        listOfferARideView.OnListOfferARideUnSuccess(offerARideModel.getMessage());
                    }
                } catch (NullPointerException exp) {
                        exp.printStackTrace();
                    listOfferARideView.OnListOfferARideUnSuccess(activity.getString(R.string.error_server));
                }
            }

            @Override
            public void onFailure(Call<OfferARideModel> call, Throwable t) {
                Progress.stop();
                t.printStackTrace();
                listOfferARideView.OnListOfferARideUnSuccess(activity.getString(R.string.error_server));
            }
        });



    }
}
