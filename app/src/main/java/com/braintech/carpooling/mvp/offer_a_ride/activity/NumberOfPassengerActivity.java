package com.braintech.carpooling.mvp.offer_a_ride.activity;

import android.content.Intent;
import android.location.Address;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.braintech.carpooling.R;
import com.braintech.carpooling.common.requestresponse.ConstIntent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NumberOfPassengerActivity extends AppCompatActivity {

    @BindView(R.id.txtViewToolTitle)
    TextView txtViewToolTitle;

    @BindView(R.id.txtViewNumberOfPassenger)
    TextView txtViewNumberOfPassenger;

    @BindView(R.id.imgViewIncrease)
    ImageView imgViewIncrease;

    @BindView(R.id.imgViewDecrease)
    ImageView imgViewDecrease;

    int numberOfPassenger;


    // for intent value get
    String leavingTime;

    String leavingDate;
    String jsonArrayStopsInBetween;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_of_passenger);

        ButterKnife.bind(this);
        txtViewToolTitle.setVisibility(View.GONE);
        numberOfPassenger = Integer.parseInt(txtViewNumberOfPassenger.getText().toString().trim());

        getIntentData();
    }

    @OnClick(R.id.imgViewBackArrow)
    void onClickBackArrow(){
        finish();
    }

    @OnClick(R.id.btnConfirm)
    void onClickBtnConfirm(){

        Intent intent = new Intent(NumberOfPassengerActivity.this, CalculateAmountActivity.class);
       /* if(arrayListStopLocationName != null){
            if(arrayListStopLocationName.size() > 0) {

                intent.putStringArrayListExtra(ConstIntent.KEY_ARRAYLIST_STOP_NAME, arrayListStopLocationName);
                intent.putStringArrayListExtra(ConstIntent.KEY_ARRAYLIST_STOP_FULL_ADDRESS, arrayListStopFullAddress);
                intent.putStringArrayListExtra(ConstIntent.KEY_ARRAYLIST_STOP_LATITUDE, arrayListStopLatitude);
                intent.putStringArrayListExtra(ConstIntent.KEY_ARRAYLIST_STOP_LONGITUDE, arrayListStopLongitude);
                intent.putStringArrayListExtra(ConstIntent.KEY_ARRAYLIST_STOP_ADDRESS_LINE, arrayListStopAddressLine);

            }
        }*/
        if (sourceAddressesList != null) {
            if (sourceAddressesList.size() > 0) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(ConstIntent.KEY_SOURCE_ADDRESS_BUNDLE, (Serializable) sourceAddressesList);
                intent.putExtras(bundle);
            } else {
                intent.putExtra(ConstIntent.KEY_SOURCE_LATITUDE, sourceLatitude);
                intent.putExtra(ConstIntent.KEY_SOURCE_LONGITUDE, sourceLongitude);
                intent.putExtra(ConstIntent.KEY_SOURCE_FULL_ADDRESS, sourceFullAddress);
                intent.putExtra(ConstIntent.KEY_LINE_SOURCE_ADDRESS, sourceLineAddress);
            }
        } else {
            intent.putExtra(ConstIntent.KEY_SOURCE_LATITUDE, sourceLatitude);
            intent.putExtra(ConstIntent.KEY_SOURCE_LONGITUDE, sourceLongitude);
            intent.putExtra(ConstIntent.KEY_SOURCE_FULL_ADDRESS, sourceFullAddress);
            intent.putExtra(ConstIntent.KEY_LINE_SOURCE_ADDRESS, sourceLineAddress);
        }

        intent.putExtra(ConstIntent.KEY_DESTINATION_LATITUDE,destinationLatitude);
        intent.putExtra(ConstIntent.KEY_DESTINATION_LONGITUDE,destinationLongitude);
        intent.putExtra(ConstIntent.KEY_DESTINATION_FULL_ADDRESS,destinationFullAddress);
        intent.putExtra(ConstIntent.KEY_DESTINATION_ADDRESS_LINE, destinationAddressLine);

        intent.putExtra(ConstIntent.KEY_LEAVING_DATE, leavingDate);
        intent.putExtra(ConstIntent.KEY_STOP_MODEL,jsonArrayStopsInBetween);
        intent.putExtra(ConstIntent.KEY_LEAVING_TIME, leavingTime);

        intent.putExtra(ConstIntent.KEY_LEAVING_NUMBER_OF_PASSENGER, String.valueOf(numberOfPassenger));

        startActivity(intent);

    }

    @OnClick(R.id.imgViewDecrease)
    void onClickImgDecrease(){
        numberOfPassenger = Integer.parseInt(txtViewNumberOfPassenger.getText().toString().trim());
        if(numberOfPassenger != 1){
            numberOfPassenger = numberOfPassenger - 1;
            txtViewNumberOfPassenger.setText(String.valueOf(numberOfPassenger));
            if(numberOfPassenger == 1){
                imgViewDecrease.setEnabled(false);
                imgViewDecrease.setColorFilter(ContextCompat.getColor(this, R.color.color_light_grey), android.graphics.PorterDuff.Mode.MULTIPLY);
            }
        }else {
            //imgViewDecrease.setVisibility(View.GONE);
            imgViewDecrease.setEnabled(false);
            imgViewDecrease.setColorFilter(ContextCompat.getColor(this, R.color.color_light_grey), android.graphics.PorterDuff.Mode.MULTIPLY);
        }

        if(numberOfPassenger > 3){
            //imgViewIncrease.setVisibility(View.GONE);
            imgViewIncrease.setEnabled(false);
            imgViewIncrease.setColorFilter(ContextCompat.getColor(this, R.color.color_light_grey), android.graphics.PorterDuff.Mode.MULTIPLY);
        }else {
            //imgViewIncrease.setVisibility(View.VISIBLE);
            imgViewIncrease.setEnabled(true);
            imgViewIncrease.setColorFilter(ContextCompat.getColor(this, R.color.color_black), android.graphics.PorterDuff.Mode.MULTIPLY);
        }

        Log.e("numberOfPassenger ->D",String.valueOf(numberOfPassenger));

    }

    @OnClick(R.id.imgViewIncrease)
    void onClickImgIncrease(){
        numberOfPassenger = Integer.parseInt(txtViewNumberOfPassenger.getText().toString().trim());

        if(numberOfPassenger < 4){
            numberOfPassenger = numberOfPassenger + 1;
            txtViewNumberOfPassenger.setText(String.valueOf(numberOfPassenger));
            if(numberOfPassenger == 4){
                imgViewIncrease.setEnabled(false);
                imgViewIncrease.setColorFilter(ContextCompat.getColor(this, R.color.color_light_grey), android.graphics.PorterDuff.Mode.MULTIPLY);
            }
        }else {
            //imgViewIncrease.setVisibility(View.GONE);
            imgViewIncrease.setEnabled(false);
            imgViewIncrease.setColorFilter(ContextCompat.getColor(this, R.color.color_light_grey), android.graphics.PorterDuff.Mode.MULTIPLY);

        }

        if(numberOfPassenger == 1){
            //imgViewDecrease.setVisibility(View.GONE);
            imgViewDecrease.setEnabled(false);
            imgViewDecrease.setColorFilter(ContextCompat.getColor(this, R.color.color_light_grey), android.graphics.PorterDuff.Mode.MULTIPLY);
        }else {
            //imgViewDecrease.setVisibility(View.VISIBLE);
            imgViewDecrease.setEnabled(true);
            imgViewDecrease.setColorFilter(ContextCompat.getColor(this, R.color.color_black), android.graphics.PorterDuff.Mode.MULTIPLY);
        }

        Log.e("numberOfPassenger ->I",String.valueOf(numberOfPassenger));
    }

    private void getIntentData() {

        Intent intent = getIntent();
        if (intent.hasExtra(ConstIntent.KEY_SOURCE_LATITUDE)) {
            sourceFullAddress = intent.getStringExtra(ConstIntent.KEY_SOURCE_FULL_ADDRESS);
            sourceLatitude = intent.getStringExtra(ConstIntent.KEY_SOURCE_LATITUDE);
            sourceLongitude = intent.getStringExtra(ConstIntent.KEY_SOURCE_LONGITUDE);
            sourceLineAddress = intent.getStringExtra(ConstIntent.KEY_LINE_SOURCE_ADDRESS);
        }else if(intent.hasExtra(ConstIntent.KEY_SOURCE_ADDRESS_BUNDLE)){
            sourceAddressesList = (List<Address>) intent.getSerializableExtra(ConstIntent.KEY_SOURCE_ADDRESS_BUNDLE);
        }
        if(intent.hasExtra(ConstIntent.KEY_STOP_MODEL)){
            /*arrayListStopLocationName = intent.getStringArrayListExtra(ConstIntent.KEY_ARRAYLIST_STOP_NAME);
            arrayListStopFullAddress = intent.getStringArrayListExtra(ConstIntent.KEY_ARRAYLIST_STOP_FULL_ADDRESS);
            arrayListStopLatitude = intent.getStringArrayListExtra(ConstIntent.KEY_ARRAYLIST_STOP_LATITUDE);
            arrayListStopLongitude = intent.getStringArrayListExtra(ConstIntent.KEY_ARRAYLIST_STOP_LONGITUDE);
            arrayListStopAddressLine = intent.getStringArrayListExtra(ConstIntent.KEY_ARRAYLIST_STOP_ADDRESS_LINE);
*/
            jsonArrayStopsInBetween =  intent.getStringExtra(ConstIntent.KEY_STOP_MODEL);
            //Log.e("ListStopLocationName",arrayListStopLocationName + ", "+ arrayListStopFullAddress);
        }
        if(intent.hasExtra(ConstIntent.KEY_DESTINATION_ADDRESS_LINE)){
            //Log.e("source latlng",sourceLatitude + ", "+ sourceLongitude);

            destinationAddressLine = intent.getStringExtra(ConstIntent.KEY_DESTINATION_ADDRESS_LINE);
            destinationLatitude = intent.getStringExtra(ConstIntent.KEY_DESTINATION_LATITUDE);
            destinationLongitude = intent.getStringExtra(ConstIntent.KEY_DESTINATION_LONGITUDE);
            destinationFullAddress = intent.getStringExtra(ConstIntent.KEY_DESTINATION_FULL_ADDRESS);
            //Log.e("destination latlng",destinationLatitude + ", "+ destinationLongitude);

            leavingDate = intent.getStringExtra(ConstIntent.KEY_LEAVING_DATE);

            leavingTime = intent.getStringExtra(ConstIntent.KEY_LEAVING_TIME);

        }
    }

}
