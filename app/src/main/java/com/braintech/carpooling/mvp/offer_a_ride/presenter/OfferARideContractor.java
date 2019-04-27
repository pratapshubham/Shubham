package com.braintech.carpooling.mvp.offer_a_ride.presenter;

/**
 * Created by Braintech on 6/19/2018.
 */

public interface OfferARideContractor {


    interface OfferARideView{

        void onOfferARideSuccess(OfferARideModel loginModel);
        void onOfferARideUnsuccess(String message);
        void onOfferARideInternetError();
    }

    interface OfferARidePresenter{

        void onOfferARideSubmit(String startLoaction, String startLatitude, String startLongitude, String destinationLocation
        , String destinationLatitude, String destinationLongitude, String journeyDate, String journeyTime, String numberOfPassenger, String jsonArrayStopsInBetween);
    }


}
