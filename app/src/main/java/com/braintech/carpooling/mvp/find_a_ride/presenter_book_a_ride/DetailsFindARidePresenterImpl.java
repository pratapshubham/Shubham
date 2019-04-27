package com.braintech.carpooling.mvp.find_a_ride.presenter_book_a_ride;

import android.app.Activity;
import android.util.Log;

import com.braintech.carpooling.BuildConfig;
import com.braintech.carpooling.R;
import com.braintech.carpooling.common.helpers.LoginManagerSession;
import com.braintech.carpooling.common.helpers.Progress;
import com.braintech.carpooling.common.requestresponse.ApiAdapter;
import com.braintech.carpooling.common.requestresponse.Const;
import com.braintech.carpooling.mvp.find_a_ride.presenter.FindARideModel;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Braintech on 6/26/2018.
 */

public class DetailsFindARidePresenterImpl implements DetailsFindRideContractor.DetailsFindARidePresenter {

    Activity activity;
    DetailsFindRideContractor.DetailsFindARideView detailsFindARideView;

    public DetailsFindARidePresenterImpl(Activity activity, DetailsFindRideContractor.DetailsFindARideView detailsFindARideView){
        this.activity = activity;
        this.detailsFindARideView = detailsFindARideView;
    }

    @Override
    public void onBookARide(FindARideModel.Datum findARideDatumModel,String numberOfSeatsForBook) {
        try {
            ApiAdapter.getInstance(activity);
            bookARide(findARideDatumModel,numberOfSeatsForBook);
        } catch (ApiAdapter.NoInternetException e) {
            e.printStackTrace();
            detailsFindARideView.onFindARideInternetError();
        }
    }

    private void bookARide(FindARideModel.Datum findARideDatumModel,String numberOfSeatsForBook) {

        Progress.start(activity);

        String userID = String.valueOf(LoginManagerSession.getInstance().getUserData().getUserId());

        JSONObject jsonObject = null;



        try {
            //user_id,ride_id,destination_location,destination_latitude,destination_longitude
            jsonObject = new JSONObject();
            jsonObject.put(Const.PARAM_USER_ID, userID);
            jsonObject.put(Const.PARAM_RIDE_ID, findARideDatumModel.getRideId());
            jsonObject.put(Const.PARAM_DESTINATION_LOCATION,findARideDatumModel.getDestinationLocation() );
            jsonObject.put(Const.PARAM_DESTINATION_LATITUDE, findARideDatumModel.getDestinationLatitude());
            jsonObject.put(Const.PARAM_DESTINATION_LONGITUDE, findARideDatumModel.getDestinationLongitude());
            jsonObject.put(Const.PARAM_NUMBER_OF_PASSENGER,numberOfSeatsForBook );
            //jsonObject.put(Const.PARAM_STOPS_IN_BETWEEN, );
        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        final RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (jsonObject.toString()));

        Call<BookARideModel> getBookARideResult = ApiAdapter.getApiService().bookARideData("application/json", "no-cache", body);

        getBookARideResult.enqueue(new Callback<BookARideModel>() {
            @Override
            public void onResponse(Call<BookARideModel> call, Response<BookARideModel> response) {

                Progress.stop();

                Log.e("response", "->" + response.body());

                try {
                    //getting whole data from response
                    BookARideModel bookARideModel = response.body();

                    if (bookARideModel.getStatus() == 1) {
                        detailsFindARideView.onFindARideSuccess(bookARideModel);

                    } else {
                        detailsFindARideView.onFindARideUnSuccess(bookARideModel.getMessage());
                    }
                } catch (NullPointerException exp) {
                    if (BuildConfig.DEBUG)
                        exp.printStackTrace();
                    detailsFindARideView.onFindARideUnSuccess(activity.getString(R.string.error_server));
                }
            }

            @Override
            public void onFailure(Call<BookARideModel> call, Throwable t) {
                Progress.stop();
                detailsFindARideView.onFindARideUnSuccess(activity.getString(R.string.error_server));
            }
        });




    }
}
