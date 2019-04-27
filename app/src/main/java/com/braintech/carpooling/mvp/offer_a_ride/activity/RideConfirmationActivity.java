package com.braintech.carpooling.mvp.offer_a_ride.activity;

import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.braintech.carpooling.R;
import com.braintech.carpooling.common.helpers.AlertDialogHelper;
import com.braintech.carpooling.common.interfaces.OnClickInterface;
import com.braintech.carpooling.common.requestresponse.ConstIntent;
import com.braintech.carpooling.common.utility.Utils;
import com.braintech.carpooling.mvp.offer_a_ride.ride_confirmation_presenter.RideConfirmationContractor;
import com.braintech.carpooling.mvp.offer_a_ride.ride_confirmation_presenter.RideConfirmationModel;
import com.braintech.carpooling.mvp.offer_a_ride.ride_confirmation_presenter.RideConfirmationPresenterImpl;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RideConfirmationActivity extends AppCompatActivity implements RideConfirmationContractor.RideConfirmationView {

    @BindView(R.id.txtViewName)
    TextView txtViewName;

    @BindView(R.id.txtViewSource)
    TextView txtViewSource;

    @BindView(R.id.txtViewDestination)
    TextView txtViewDestination;

    @BindView(R.id.txtViewNumberOfSeats)
    TextView txtViewNumberOfSeats;

    @BindView(R.id.txtViewDate)
    TextView txtViewDate;

    @BindView(R.id.txtViewTime)
    TextView txtViewTime;

    @BindView(R.id.btnAccept)
    Button btnAccept;

    @BindView(R.id.btnCancel)
    Button btnCancel;

    RideConfirmationPresenterImpl rideConfirmationPresenterImpl;
    String rideScheduleId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_confirmation);

        ButterKnife.bind(this);
        rideConfirmationPresenterImpl = new  RideConfirmationPresenterImpl(this,this);
        getIntentData();
    }

    private void getIntentData() {

        Intent intent = getIntent();
        if(intent.hasExtra(ConstIntent.KEY_NOTIFICATION_MESSAGE_BODY)){
            String messageBody = intent.getStringExtra((ConstIntent.KEY_NOTIFICATION_MESSAGE_BODY));
            Log.e("message body",messageBody);
        }
    }

    @OnClick(R.id.btnAccept)
    void onClickBtnAccept(){

        Utils.hideKeyboardIfOpen(this);
        if(!Utils.isEmptyOrNull(rideScheduleId)) {
            rideConfirmationPresenterImpl.onRideAccept(rideScheduleId);
        }
    }


    @OnClick(R.id.btnCancel)
    void onClickBtnCancel(){

        Utils.hideKeyboardIfOpen(this);
        if(!Utils.isEmptyOrNull(rideScheduleId)) {
            rideConfirmationPresenterImpl.onRideCancel(rideScheduleId);
        }
    }


    @Override
    public void onSuccessRideAccept(RideConfirmationModel rideConfirmationModel) {
        AlertDialogHelper.showMessage(this,"success");
    }

    @Override
    public void onSuccessRideCancel(RideConfirmationModel rideConfirmationModel) {
        AlertDialogHelper.showMessage(this,"success");
    }

    @Override
    public void onUnSuccessRideAccept(String message) {

        AlertDialogHelper.showMessage(this,message);
    }

    @Override
    public void onUnSuccessRideCancel(String message) {
        AlertDialogHelper.showMessage(this,message);
    }

    @Override
    public void onInternetErrorRideAccept() {
        AlertDialogHelper.alertInternetError(this, onRetryAcceptRide);
    }

    @Override
    public void onInternetErrorRideCancel() {
        AlertDialogHelper.alertInternetError(this, onRetryCancelRide);
    }

    OnClickInterface onRetryCancelRide = new OnClickInterface() {
        @Override
        public void onClick() {
            onClickBtnCancel();
        }
    };

    OnClickInterface onRetryAcceptRide = new OnClickInterface() {
        @Override
        public void onClick() {
            onClickBtnAccept();
        }
    };
}
