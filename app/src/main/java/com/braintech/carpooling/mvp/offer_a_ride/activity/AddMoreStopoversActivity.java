package com.braintech.carpooling.mvp.offer_a_ride.activity;

import android.content.Intent;
import android.location.Address;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.braintech.carpooling.R;
import com.braintech.carpooling.common.requestresponse.ConstIntent;
import com.braintech.carpooling.mvp.offer_a_ride.adapter.AddMoreStopsAdapter;
import com.braintech.carpooling.mvp.offer_a_ride.model.StopListModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddMoreStopoversActivity extends AppCompatActivity {

    @BindView(R.id.recycleViewStopPlace)
    RecyclerView recycleViewStopPlace;

    int REQUEST_CODE = 1234;

    JSONArray jsonArrayStopsInBetween;

    AddMoreStopsAdapter addMoreStopsAdapter;
    String addressLine;
    String locationName;
    String latitude;
    String longitude;
    String fullAddress;

    ArrayList<String> arrayListLocationName;
    ArrayList<String> arrayListLatitude;
    ArrayList<String> arrayListLongitude;
    ArrayList<String> arrayListFullAddress;
    ArrayList<String> arrayListAddressLine;


    ArrayList<StopListModel> arrayListStopList;
    // for intent value get

    List<Address> sourceAddressesList;
    String sourceFullAddress;
    String sourceLatitude;
    String sourceLongitude;
    String sourceLineAddress;

    String destinationLatitude;
    String destinationLongitude;
    String destinationAddressLine;
    String destinationFullAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_more_passenger);

        ButterKnife.bind(this);
        arrayListLocationName = new ArrayList<>();
        arrayListLatitude = new ArrayList<>();
        arrayListLongitude = new ArrayList<>();
        arrayListFullAddress = new ArrayList<>();
        arrayListAddressLine = new ArrayList<>();
        arrayListStopList = new ArrayList<>();
        getIntentData();
        setLayoutManager();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode == REQUEST_CODE)
        {
            if (data != null) {
                if (data.hasExtra(ConstIntent.KEY_STOP_NAME)) {

                    if(data != null) {
                        this.addressLine = data.getStringExtra(ConstIntent.KEY_STOP_ADDRESS_LINE);

                        locationName = data.getStringExtra(ConstIntent.KEY_STOP_NAME);
                    }


                    //this.fullAddress = data.getStringExtra(ConstIntent.KEY_STOP_FULL_ADDRESS);
                    if (!arrayListLocationName.contains(locationName)) {
                        arrayListLocationName.add(locationName);
                        StopListModel stopListModel = new  StopListModel();
                        stopListModel.setLocationName(data.getStringExtra(ConstIntent.KEY_STOP_NAME));
                        stopListModel.setStopLatitude(data.getStringExtra(ConstIntent.KEY_STOP_LATITUDE));
                        stopListModel.setLongitude(data.getStringExtra(ConstIntent.KEY_STOP_LONGITUDE));
                        stopListModel.setSelected(data.getBooleanExtra(ConstIntent.KEY_STOP_IS_SELECTED,false));
                        arrayListStopList.add(stopListModel);

                    }
                }
            }
            if(arrayListLocationName != null) {
                if(arrayListLocationName.size() > 0) {
                    setAdapter();
                }
            }
        }
    }

    @OnClick(R.id.textViewForSearchLocation)
    void onClickAddMorePassenger(){
        Intent intent = new Intent(AddMoreStopoversActivity.this, SearchMoreStopsActivity.class);
        startActivityForResult(intent,REQUEST_CODE);
    }

    @OnClick(R.id.imgViewBackArrow)
    void onClickBackArrow(){
        finish();
    }

    @OnClick(R.id.btnConfirm)
    void onClickBtnConfirm(){
        Intent intent = new Intent(AddMoreStopoversActivity.this, DateSelectActivity.class);
        if(arrayListLocationName != null){
            if(arrayListLocationName.size() > 0) {

                createStopJsonArray();

                /*Bundle bundle = new Bundle();
                bundle.putSerializable(ConstIntent.KEY_STOP_MODEL,(Serializable)jsonArrayStopsInBetween);
                intent.putExtras(bundle);

                Log.e("key model",jsonArrayStopsInBetween.toString());
*/
                intent.putExtra(ConstIntent.KEY_STOP_MODEL,jsonArrayStopsInBetween.toString());
               // Log.e("key model",jsonArrayStopsInBetween.toString());
                /*Bundle bundle = new Bundle();
                bundle.putSerializable(ConstIntent.KEY_STOP_MODEL,(Serializable) stopListModel);
                intent.putExtras(bundle);
*/
                /*intent.putStringArrayListExtra(ConstIntent.KEY_ARRAYLIST_STOP_NAME, arrayListLocationName);
                intent.putStringArrayListExtra(ConstIntent.KEY_ARRAYLIST_STOP_FULL_ADDRESS, arrayListFullAddress);
                intent.putStringArrayListExtra(ConstIntent.KEY_ARRAYLIST_STOP_LATITUDE, arrayListLatitude);
                intent.putStringArrayListExtra(ConstIntent.KEY_ARRAYLIST_STOP_LONGITUDE, arrayListLongitude);
                intent.putStringArrayListExtra(ConstIntent.KEY_ARRAYLIST_STOP_ADDRESS_LINE, arrayListAddressLine);*/
            }
        }
        if (sourceAddressesList != null) {
            if (sourceAddressesList.size() > 0) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(ConstIntent.KEY_SOURCE_ADDRESS_BUNDLE, (Serializable) sourceAddressesList);
                intent.putExtras(bundle);
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
        startActivity(intent);
    }

    private void createStopJsonArray() {

        if (arrayListLocationName != null) {
            jsonArrayStopsInBetween = new JSONArray();
            for (int i = 0; i < arrayListStopList.size(); i++) {

                JSONObject jsonObject = new JSONObject();
                if(arrayListStopList.get(i).isSelected()) {
                    try {
                        jsonObject.put("latitude", arrayListStopList.get(i).getStopLatitude());
                        jsonObject.put("longitude", arrayListStopList.get(i).getLongitude());
                        jsonObject.put("address", arrayListStopList.get(i).getLocationName());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    jsonArrayStopsInBetween.put(jsonObject);
                }

            }
        }
        else {
            jsonArrayStopsInBetween = new JSONArray();
        }
    }



    private void getIntentData() {

        Intent intent = getIntent();
        if (intent.hasExtra(ConstIntent.KEY_SOURCE_LATITUDE)) {
            sourceFullAddress = intent.getStringExtra(ConstIntent.KEY_SOURCE_FULL_ADDRESS);
            sourceLatitude = intent.getStringExtra(ConstIntent.KEY_SOURCE_LATITUDE);
            sourceLongitude = intent.getStringExtra(ConstIntent.KEY_SOURCE_LONGITUDE);
            sourceLineAddress = intent.getStringExtra(ConstIntent.KEY_LINE_SOURCE_ADDRESS);
        }
        if(intent.hasExtra(ConstIntent.KEY_SOURCE_ADDRESS_BUNDLE)){
            sourceAddressesList = (List<Address>) intent.getSerializableExtra(ConstIntent.KEY_SOURCE_ADDRESS_BUNDLE);
        }
        if(intent.hasExtra(ConstIntent.KEY_DESTINATION_ADDRESS_LINE)) {
            destinationAddressLine = intent.getStringExtra(ConstIntent.KEY_DESTINATION_ADDRESS_LINE);
            destinationLatitude = intent.getStringExtra(ConstIntent.KEY_DESTINATION_LATITUDE);
            destinationLongitude = intent.getStringExtra(ConstIntent.KEY_DESTINATION_LONGITUDE);
            destinationFullAddress = intent.getStringExtra(ConstIntent.KEY_DESTINATION_FULL_ADDRESS);
        }
    }

    private void setLayoutManager() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recycleViewStopPlace.setLayoutManager(linearLayoutManager);
    }

    private void setAdapter() {
        addMoreStopsAdapter = new AddMoreStopsAdapter(this, arrayListStopList);
        recycleViewStopPlace.setAdapter(addMoreStopsAdapter);
    }
}
