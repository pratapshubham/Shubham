package com.braintech.carpooling.mvp.your_rides.offer_a_ride_presenter;

/**
 * Created by Braintech on 6/21/2018.
 */

public interface ListOfferARideContractor {

    interface ListOfferARidePresenter{

        void listOfferARideGetData();

    }

    interface ListOfferARideView{

        void onListOfferARideSuccess(OfferARideModel offerARideModel);
        void OnListOfferARideUnSuccess(String message);
        void onListOfferARideInternetError();

    }


}
