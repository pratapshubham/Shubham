package com.braintech.carpooling.mvp.offer_a_ride.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Location;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.braintech.carpooling.R;
import com.braintech.carpooling.common.helpers.AlertDialogHelper;
import com.braintech.carpooling.common.interfaces.OnClickInterface;
import com.braintech.carpooling.common.requestresponse.ConstIntent;
import com.braintech.carpooling.mvp.your_rides.activity.YourRidesActivity;
import com.braintech.carpooling.mvp.offer_a_ride.presenter.OfferARideContractor;
import com.braintech.carpooling.mvp.offer_a_ride.presenter.OfferARideModel;
import com.braintech.carpooling.mvp.offer_a_ride.presenter.OfferARidePresenterImpl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CalculateAmountActivity extends AppCompatActivity implements OfferARideContractor.OfferARideView {

    @BindView(R.id.txtViewToolTitle)
    TextView txtViewToolTitle;

    @BindView(R.id.txtViewPrice)
    TextView txtViewPrice;

    @BindView(R.id.txtViewDestination)
    TextView txtViewDestination;

    @BindView(R.id.txtViewSource)
    TextView txtViewSource;

    int BASE_PRICE = 6;
    String totalAmount;

    String sourceLocationName;
    String destinationLocationName;

    //String jsonArrayStopsInBetween;
    String StringStopsInBetween;




    // for intent value get
    String numberOfPassenger;

    String leavingTime;

    String leavingDate;

    List<Address> sourceAddressesList;
    String sourceFullAddress;
    String sourceLatitude;
    String sourceLongitude;
    String sourceLineAddress;

    String destinationLatitude;
    String destinationLongitude;
    String destinationAddressLine;
    String destinationFullAddress;

    ArrayList<String> arrayListStopLocationName;
    ArrayList<String> arrayListStopLatitude;
    ArrayList<String> arrayListStopLongitude;
    ArrayList<String> arrayListStopFullAddress;
    ArrayList<String> arrayListStopAddressLine;

    // for api

    OfferARidePresenterImpl offerARidePresenterImpl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_amount);

        ButterKnife.bind(this);
        txtViewToolTitle.setVisibility(View.GONE);
        offerARidePresenterImpl = new OfferARidePresenterImpl(this, this);
        getIntentData();
        setText();
        setTravellingCharge();
    }


    @Override
    public void onOfferARideSuccess(OfferARideModel loginModel) {
        showAlertForSuccess(loginModel.getMessage());
    }

    @Override
    public void onOfferARideUnsuccess(String message) {
        AlertDialogHelper.showMessage(this, message);
    }

    @Override
    public void onOfferARideInternetError() {
        AlertDialogHelper.alertInternetError(this, onRetryOfferARide);
    }

    private void setText() {

        txtViewSource.setText(sourceLineAddress);
        if (sourceAddressesList != null) {
            txtViewDestination.setText(destinationAddressLine);
            txtViewSource.setText(sourceAddressesList.get(0).getLocality());
        } else {
            txtViewSource.setText(sourceLineAddress);
            txtViewDestination.setText(destinationAddressLine);
        }
    }

    @OnClick(R.id.imgViewBackArrow)
    void onClickBackArror() {
        finish();
    }

    @OnClick(R.id.btnConfirm)
    void onClickBtnConfirm() {

        //createStopJsonArray();
        if (sourceAddressesList == null) {
            offerARidePresenterImpl.onOfferARideSubmit(sourceLineAddress.trim(), sourceLatitude, sourceLongitude, destinationAddressLine.trim(), destinationLatitude, destinationLongitude, leavingDate, leavingTime, numberOfPassenger, StringStopsInBetween);
        } else {
            offerARidePresenterImpl.onOfferARideSubmit(sourceAddressesList.get(0).getLocality(), String.valueOf(sourceAddressesList.get(0).getLatitude()), String.valueOf(sourceAddressesList.get(0).getLongitude()), destinationAddressLine.trim(), destinationLatitude, destinationLongitude, leavingDate, leavingTime, numberOfPassenger, StringStopsInBetween);
        }
    }



    private void showAlertForSuccess(String message) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Message
        alertDialog.setMessage(message);
        alertDialog.setCancelable(false);
        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(CalculateAmountActivity.this, YourRidesActivity.class);
                startActivity(intent);

            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    private void setTravellingCharge() {
        float[] results = new float[1];
        float endLongitude;
        float endLatitude;
        float startLongitude;
        float startLatitude;
        if (sourceAddressesList == null) {
            startLatitude = Float.parseFloat(sourceLatitude);
            startLongitude = Float.parseFloat(sourceLongitude);
            endLatitude = Float.parseFloat(destinationLatitude);
            endLongitude = Float.parseFloat(destinationLongitude);

        } else {
            startLatitude = (float) sourceAddressesList.get(0).getLatitude();
            startLongitude = (float) sourceAddressesList.get(0).getLongitude();
            endLatitude = Float.parseFloat(destinationLatitude);
            endLongitude = Float.parseFloat(destinationLongitude);
        }

        Location.distanceBetween(startLatitude, startLongitude,
                endLatitude, endLongitude, results);
        float distance = results[0];
        Log.e("price-->",String.valueOf(distance));
        totalAmount = String.valueOf((int) (distance * BASE_PRICE) / 1000);
        //Log.e("int value", String.valueOf((int)totalAmount));
        txtViewPrice.setText(totalAmount);
    }

    private void getIntentData() {

        Intent intent = getIntent();
        if (intent.hasExtra(ConstIntent.KEY_SOURCE_LATITUDE)) {
            sourceFullAddress = intent.getStringExtra(ConstIntent.KEY_SOURCE_FULL_ADDRESS);
            sourceLatitude = intent.getStringExtra(ConstIntent.KEY_SOURCE_LATITUDE);
            sourceLongitude = intent.getStringExtra(ConstIntent.KEY_SOURCE_LONGITUDE);
            sourceLineAddress = intent.getStringExtra(ConstIntent.KEY_LINE_SOURCE_ADDRESS);

        } else if (intent.hasExtra(ConstIntent.KEY_SOURCE_ADDRESS_BUNDLE)) {
            sourceAddressesList = (List<Address>) intent.getSerializableExtra(ConstIntent.KEY_SOURCE_ADDRESS_BUNDLE);
        }
        if (intent.hasExtra(ConstIntent.KEY_STOP_MODEL)) {

            StringStopsInBetween =   intent.getStringExtra(ConstIntent.KEY_STOP_MODEL);
            //Log.e("jsonArrayStopsInBetween",StringStopsInBetween);
        }
        //Log.e("source latlng",sourceLatitude + ", "+ sourceLongitude);
        if (intent.hasExtra(ConstIntent.KEY_DESTINATION_ADDRESS_LINE)) {
            destinationAddressLine = intent.getStringExtra(ConstIntent.KEY_DESTINATION_ADDRESS_LINE);
            destinationLatitude = intent.getStringExtra(ConstIntent.KEY_DESTINATION_LATITUDE);
            destinationLongitude = intent.getStringExtra(ConstIntent.KEY_DESTINATION_LONGITUDE);
            destinationFullAddress = intent.getStringExtra(ConstIntent.KEY_DESTINATION_FULL_ADDRESS);
            //Log.e("destination latlng",destinationLatitude + ", "+ destinationLongitude);

            leavingDate = intent.getStringExtra(ConstIntent.KEY_LEAVING_DATE);

            leavingTime = intent.getStringExtra(ConstIntent.KEY_LEAVING_TIME);

            numberOfPassenger = intent.getStringExtra(ConstIntent.KEY_LEAVING_NUMBER_OF_PASSENGER);
        }
    }

    OnClickInterface onRetryOfferARide = new OnClickInterface() {
        @Override
        public void onClick() {
            onClickBtnConfirm();
        }
    };


}
