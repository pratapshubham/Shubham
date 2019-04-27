package com.braintech.carpooling.mvp.offer_a_ride.ride_confirmation_presenter;

/**
 * Created by Braintech on 6/27/2018.
 */

public interface RideConfirmationContractor {

    interface RideConfirmationPresenter{
        /*http://onlineprojectprogress.com/carpooling/Api/cancelRide
        {"user_id":"2180021705347294","ride_scheduled_id":3}
        http://onlineprojectprogress.com/carpooling/Api/acceptRide
        {"user_id":"2180021705347294","ride_scheduled_id":3}*/
        void onRideAccept(String rideScheduledId);
        void onRideCancel(String rideScheduledId);

    }

    interface RideConfirmationView{

        void onSuccessRideAccept(RideConfirmationModel rideConfirmationModel);
        void onSuccessRideCancel(RideConfirmationModel rideConfirmationModel);
        void onUnSuccessRideAccept(String message);
        void onUnSuccessRideCancel(String message);
        void onInternetErrorRideAccept();
        void onInternetErrorRideCancel();

    }
}
