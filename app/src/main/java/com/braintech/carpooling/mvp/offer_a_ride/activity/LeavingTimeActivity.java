package com.braintech.carpooling.mvp.offer_a_ride.activity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.location.Address;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.TimePicker;

import com.braintech.carpooling.R;
import com.braintech.carpooling.common.requestresponse.ConstIntent;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LeavingTimeActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    @BindView(R.id.txtViewToolTitle)
    TextView txtViewToolTitle;

    @BindView(R.id.timePicker)
    TimePicker timePicker;

    String jsonArrayStopsInBetween;


    // for intent value get
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaving_time);

        ButterKnife.bind(this);

        txtViewToolTitle.setText("Select Time");


    getIntentData();

    }

    @OnClick(R.id.imgViewBackArrow)
    void onClickBackArrow() {
        finish();
    }

    @OnClick(R.id.btnConfirm)
    void onClickBtnConfirm() {

        int minute;
        int hour;
        hour = timePicker.getCurrentHour();
        minute = timePicker.getCurrentMinute();

        leavingTime = String.valueOf(hour) + ":" + String.valueOf(minute);
        Log.e("time",leavingTime);

        Intent intent = new Intent(LeavingTimeActivity.this, NumberOfPassengerActivity.class);

        if(sourceAddressesList !=null)
            {
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
            } else

            {
                intent.putExtra(ConstIntent.KEY_SOURCE_LATITUDE, sourceLatitude);
                intent.putExtra(ConstIntent.KEY_SOURCE_LONGITUDE, sourceLongitude);
                intent.putExtra(ConstIntent.KEY_SOURCE_FULL_ADDRESS, sourceFullAddress);
                intent.putExtra(ConstIntent.KEY_LINE_SOURCE_ADDRESS, sourceLineAddress);

            }


        intent.putExtra(ConstIntent.KEY_DESTINATION_LATITUDE,destinationLatitude);
        intent.putExtra(ConstIntent.KEY_DESTINATION_LONGITUDE,destinationLongitude);
        intent.putExtra(ConstIntent.KEY_DESTINATION_FULL_ADDRESS,destinationFullAddress);
        intent.putExtra(ConstIntent.KEY_DESTINATION_ADDRESS_LINE,destinationAddressLine);

        intent.putExtra(ConstIntent.KEY_LEAVING_DATE,leavingDate);
        intent.putExtra(ConstIntent.KEY_STOP_MODEL,jsonArrayStopsInBetween);
        intent.putExtra(ConstIntent.KEY_LEAVING_TIME,leavingTime);

            startActivity(intent);

            //Log.e("hour minute",String.valueOf(hour) + ", "+ String.valueOf(minute));
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
           /* arrayListStopLocationName = intent.getStringArrayListExtra(ConstIntent.KEY_ARRAYLIST_STOP_NAME);
            arrayListStopFullAddress = intent.getStringArrayListExtra(ConstIntent.KEY_ARRAYLIST_STOP_FULL_ADDRESS);
            arrayListStopLatitude = intent.getStringArrayListExtra(ConstIntent.KEY_ARRAYLIST_STOP_LATITUDE);
            arrayListStopLongitude = intent.getStringArrayListExtra(ConstIntent.KEY_ARRAYLIST_STOP_LONGITUDE);
            arrayListStopAddressLine = intent.getStringArrayListExtra(ConstIntent.KEY_ARRAYLIST_STOP_ADDRESS_LINE);*/

            jsonArrayStopsInBetween = intent.getStringExtra(ConstIntent.KEY_STOP_MODEL);

        }
        if (intent.hasExtra(ConstIntent.KEY_DESTINATION_ADDRESS_LINE)) {
            destinationAddressLine = intent.getStringExtra(ConstIntent.KEY_DESTINATION_ADDRESS_LINE);
            destinationLatitude = intent.getStringExtra(ConstIntent.KEY_DESTINATION_LATITUDE);
            destinationLongitude = intent.getStringExtra(ConstIntent.KEY_DESTINATION_LONGITUDE);
            destinationFullAddress = intent.getStringExtra(ConstIntent.KEY_DESTINATION_FULL_ADDRESS);

            //Log.e("destination latlng list",destinationLatitude + ", "+ destinationLongitude);

            leavingDate = intent.getStringExtra(ConstIntent.KEY_LEAVING_DATE);
        }
        //Log.e("date list",leavingDate + ", "+ leavingMonth);

    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // Do something with the time chosen by the user

       /* leavingTime = String.valueOf(hourOfDay) + ":" + String.valueOf(minute);
        Log.e("time ---->",leavingTime);*/
    }
}
