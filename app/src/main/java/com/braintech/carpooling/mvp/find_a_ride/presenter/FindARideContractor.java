package com.braintech.carpooling.mvp.find_a_ride.presenter;

/**
 * Created by Braintech on 6/21/2018.
 */

public interface FindARideContractor {

    interface FindARidePresenter {

        void onFindARideDataSubmit(String sourceLatitude, String sourceLongitude, String destinationLatitude, String destinationLongitude, String rideDate, String rideTime, String numberOfPassenger);
    }

    interface FindARideView{

        void onFindARideSuccess(FindARideModel findARideModel);
        void onFindARideUnSuccess(String message);
        void onFindARideInternetError();

    }


}


