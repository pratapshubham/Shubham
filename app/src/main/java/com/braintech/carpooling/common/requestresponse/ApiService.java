package com.braintech.carpooling.common.requestresponse;


import com.braintech.carpooling.mvp.find_a_ride.presenter.FindARideModel;
import com.braintech.carpooling.mvp.find_a_ride.presenter_book_a_ride.BookARideModel;
import com.braintech.carpooling.mvp.offer_a_ride.ride_confirmation_presenter.RideConfirmationModel;
import com.braintech.carpooling.mvp.splash_login.presenter.LoginModel;
import com.braintech.carpooling.mvp.your_rides.offer_a_ride_presenter.OfferARideModel;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by Braintech on 9/12/2016.
 */
public interface ApiService {

    //<----------- Login Api------------------------------>
    @POST("Api/socialLogin")
    Call<LoginModel> login(@Header("Content-Type") String contentType, @Header("Cache-Control") String cache, @Body RequestBody params);
 //<----------- Login Api------------------------------>
    //@POST("officeDevicePreference")
    //Call<LoginModel> login(@Header("Content-Type") String contentType, @Header("Cache-Control") String cache, @Body RequestBody params);

    //<----------- Offer A Ride Api------------------------------>
    @POST("Api/offerARide")
    Call<com.braintech.carpooling.mvp.offer_a_ride.presenter.OfferARideModel> offerARide(@Header("Content-Type") String contentType, @Header("Cache-Control") String cache, @Body RequestBody params);

    //<----------- find A Ride Api------------------------------>
    @POST("Api/LocationWiseRideListing")
    Call<FindARideModel> findARide(@Header("Content-Type") String contentType, @Header("Cache-Control") String cache, @Body RequestBody params);

    //<----------- list offer A Ride Api------------------------------>
    @POST("Api/listRide")
    Call<OfferARideModel> listOfferARide(@Header("Content-Type") String contentType, @Header("Cache-Control") String cache, @Body RequestBody params);

    //<----------- book A Ride Api------------------------------>
    @POST("Api/requestForRide")
    Call<BookARideModel> bookARideData(@Header("Content-Type") String contentType, @Header("Cache-Control") String cache, @Body RequestBody params);

    //<----------- accept A Ride Api------------------------------>
    @POST("Api/acceptRide")
    Call<RideConfirmationModel> acceptARide(@Header("Content-Type") String contentType, @Header("Cache-Control") String cache, @Body RequestBody params);

    //<----------- cancel A Ride Api------------------------------>
    @POST("Api/cancelRide")
    Call<RideConfirmationModel> cancelARide(@Header("Content-Type") String contentType, @Header("Cache-Control") String cache, @Body RequestBody params);

}




