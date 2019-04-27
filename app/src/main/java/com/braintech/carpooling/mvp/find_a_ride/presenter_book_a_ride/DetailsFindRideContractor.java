package com.braintech.carpooling.mvp.find_a_ride.presenter_book_a_ride;

import com.braintech.carpooling.mvp.find_a_ride.presenter.FindARideModel;

/**
 * Created by Braintech on 6/26/2018.
 */

public interface DetailsFindRideContractor {

    interface DetailsFindARidePresenter{
        //user_id,ride_id,destination_location,destination_latitude,destination_longitude
        void onBookARide(FindARideModel.Datum findARideDatumModel ,String numberOfSeatsForBook);
    }

    interface DetailsFindARideView{


        void onFindARideSuccess(BookARideModel bookARideModel);
        void onFindARideUnSuccess(String message);
        void onFindARideInternetError();

    }


}
