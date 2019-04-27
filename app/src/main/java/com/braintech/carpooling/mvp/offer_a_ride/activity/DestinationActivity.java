package com.braintech.carpooling.mvp.offer_a_ride.activity;

import android.content.Intent;
import android.location.Address;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.braintech.carpooling.R;
import com.braintech.carpooling.common.helpers.AlertDialogHelper;
import com.braintech.carpooling.common.requestresponse.ConstIntent;
import com.braintech.carpooling.common.utility.Utils;
import com.braintech.carpooling.mvp.offer_a_ride.adapter.PlaceArrayAdapter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DestinationActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks{

    @BindView(R.id.autoCompleteTextViewForLocation)
    AutoCompleteTextView autoCompleteTextViewForLocation;

    private static final String LOG_TAG = "MainActivity";
    private static final int GOOGLE_API_CLIENT_ID = 0;

    private GoogleApiClient mGoogleApiClient;
    private PlaceArrayAdapter mPlaceArrayAdapter;
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));

    Place place;

    List<Address> sourceAddressesList;
    String sourceFullAddress;
    String sourceLatitude;
    String sourceLongitude;
    String sourceLineAddress;

    String destinationLatitude;
    String destinationLongitude;
    String destinationAddressLine;
    String returnAddressLine;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination);

        ButterKnife.bind(this);


        getIntentData();

        mGoogleApiClient = new GoogleApiClient.Builder(DestinationActivity.this)
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(this, GOOGLE_API_CLIENT_ID, this)
                .addConnectionCallbacks(this)
                .build();
        autoCompleteTextViewForLocation = findViewById(R.id
                .autoCompleteTextViewForLocation);
        autoCompleteTextViewForLocation.setThreshold(3);
        autoCompleteTextViewForLocation.setOnItemClickListener(mAutocompleteClickListener);
        mPlaceArrayAdapter = new PlaceArrayAdapter(this, android.R.layout.simple_list_item_1,
                BOUNDS_MOUNTAIN_VIEW, null);
        autoCompleteTextViewForLocation.setAdapter(mPlaceArrayAdapter);
    }

    @Override
    public void onConnected(Bundle bundle) {
        mPlaceArrayAdapter.setGoogleApiClient(mGoogleApiClient);
        Log.i(LOG_TAG, "Google Places API connected.");

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e(LOG_TAG, "Google Places API connection failed with error code: "
                + connectionResult.getErrorCode());

        Toast.makeText(this,
                "Google Places API connection failed with error code:" +
                        connectionResult.getErrorCode(),
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnectionSuspended(int i) {
        mPlaceArrayAdapter.setGoogleApiClient(null);
        Log.e(LOG_TAG, "Google Places API connection suspended.");
    }


    @OnClick(R.id.imgViewBackArrow)
    void onClickBackArror(){

        Intent intent = new Intent(DestinationActivity.this, LeavingFromActivity.class);
        intent.putExtra(ConstIntent.KEY_RETURN_ADDRESS,returnAddressLine);
        finish();
        startActivity(intent);
    }

    @OnClick(R.id.btnConfirm)
    void onClickBtnConfirm(){
        String destinationFullAddress = autoCompleteTextViewForLocation.getText().toString().trim();
        if(place != null){
            destinationLatitude = String.valueOf(place.getLatLng().latitude);
            destinationLongitude = String.valueOf(place.getLatLng().longitude);
            destinationAddressLine = place.getName().toString();
        }else {
            Log.e("place","is null");
        }

        String lat = String.valueOf(destinationLatitude);
        String longi = String.valueOf(destinationLongitude);

        if(isValidate(destinationFullAddress,lat,longi, destinationAddressLine)){

            Intent intent = new Intent(DestinationActivity.this, AddMoreStopoversActivity.class);
            intent.putExtra(ConstIntent.KEY_DESTINATION_LATITUDE,destinationLatitude);
            intent.putExtra(ConstIntent.KEY_DESTINATION_LONGITUDE,destinationLongitude);
            intent.putExtra(ConstIntent.KEY_DESTINATION_FULL_ADDRESS,destinationFullAddress);
            intent.putExtra(ConstIntent.KEY_DESTINATION_ADDRESS_LINE, destinationAddressLine);

            if(sourceAddressesList != null) {
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
            }else {
                intent.putExtra(ConstIntent.KEY_SOURCE_LATITUDE, sourceLatitude);
                intent.putExtra(ConstIntent.KEY_SOURCE_LONGITUDE, sourceLongitude);
                intent.putExtra(ConstIntent.KEY_SOURCE_FULL_ADDRESS, sourceFullAddress);
                intent.putExtra(ConstIntent.KEY_LINE_SOURCE_ADDRESS, sourceLineAddress);

            }
            startActivity(intent);
        }
    }


    private void getIntentData() {

        Intent intent = getIntent();
        if (intent.hasExtra(ConstIntent.KEY_SOURCE_LATITUDE)) {
            sourceFullAddress = intent.getStringExtra(ConstIntent.KEY_SOURCE_FULL_ADDRESS);
            sourceLatitude = intent.getStringExtra(ConstIntent.KEY_SOURCE_LATITUDE);
            sourceLongitude = intent.getStringExtra(ConstIntent.KEY_SOURCE_LONGITUDE);
            sourceLineAddress = intent.getStringExtra(ConstIntent.KEY_LINE_SOURCE_ADDRESS);
            returnAddressLine = sourceLineAddress;
        }else if(intent.hasExtra(ConstIntent.KEY_SOURCE_ADDRESS_BUNDLE)){
            sourceAddressesList = (List<Address>) intent.getSerializableExtra(ConstIntent.KEY_SOURCE_ADDRESS_BUNDLE);
            returnAddressLine = sourceAddressesList.get(0).getAddressLine(0);
        }
    }

    private boolean isValidate(String destinationFullAddress, String destinationLatitude, String destinationLongitude, String destinationAddressLine) {

        if(Utils.isEmptyOrNull(destinationFullAddress) || Utils.isEmptyOrNull(destinationLatitude) || Utils.isEmptyOrNull(destinationLongitude) || Utils.isEmptyOrNull(destinationAddressLine)){
            /*Log.e("full address", destinationFullAddress);
            Log.e("latitude address", destinationLatitude);
            Log.e("longitude address", destinationLongitude);
            Log.e("addressline address", destinationAddressLine);*/
            AlertDialogHelper.showMessage(this,"please enter your address.");
            return false;
        }
        return true;


    }

    private AdapterView.OnItemClickListener mAutocompleteClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final PlaceArrayAdapter.PlaceAutocomplete item = mPlaceArrayAdapter.getItem(position);
            final String placeId = String.valueOf(item.placeId);
            Log.i(LOG_TAG, "Selected: " + item.description);
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
            Log.i(LOG_TAG, "Fetching details for ID: " + item.placeId);
        }
    };

    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback
            = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                Log.e(LOG_TAG, "Place query did not complete. Error: " +
                        places.getStatus().toString());
                return;
            }

            Utils.hideKeyboardIfOpen(DestinationActivity.this);
            // Selecting the first object buffer.
            place = places.get(0);
            CharSequence attributions = places.getAttributions();
            Log.e(LOG_TAG, "getName " +
                    Html.fromHtml(place.getName() + ""));

            Log.e(LOG_TAG, "getId() ) " +
                    Html.fromHtml(place.getId() + ""));

            Log.e(LOG_TAG, "getAddress " +
                    Html.fromHtml(place.getAddress() + ""));

            Log.e(LOG_TAG, "getWebsiteUri()  " +
                    place.getWebsiteUri() + "");

            Log.e(LOG_TAG, "getPhoneNumber() + " +
                    Html.fromHtml(place.getPhoneNumber() + ""));



        }
    };


}
