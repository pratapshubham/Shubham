package com.braintech.carpooling.mvp.offer_a_ride.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.braintech.carpooling.R;
import com.braintech.carpooling.common.requestresponse.ConstIntent;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchMoreStopsActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks {

    Place place;

    int REQUEST_CODE = 1234;

    double latitude;
    double longitude;
    String sourceAddress;

    @BindView(R.id.autoCompleteTextViewForLocation)
    AutoCompleteTextView autoCompleteTextViewForLocation;

    private static final String LOG_TAG = "SearchActivity";
    private static final int GOOGLE_API_CLIENT_ID = 0;

    private GoogleApiClient mGoogleApiClient;
    private PlaceArrayAdapter mPlaceArrayAdapter;
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_more_stops);

        ButterKnife.bind(this);

        mGoogleApiClient = new GoogleApiClient.Builder(SearchMoreStopsActivity.this)
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
        Log.e(LOG_TAG, "Google Places API connected.");

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
        finish();
    }

    private AdapterView.OnItemClickListener mAutocompleteClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final PlaceArrayAdapter.PlaceAutocomplete item = mPlaceArrayAdapter.getItem(position);
            final String placeId = String.valueOf(item.placeId);
            Log.e(LOG_TAG, "Selected: " + item.description);
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
            Log.e(LOG_TAG, "Fetching details for ID: " + item.placeId);
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
            // Selecting the first object buffer.
            place = (Place) places.get(0);
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

            Log.e(LOG_TAG, "place.getLatLng() + " +
                    Html.fromHtml(place.getLatLng().latitude + ""));
            String fullAddress = autoCompleteTextViewForLocation.getText().toString().trim();

            /*AddMoreStopoversActivity addMorePassengerActivity = new AddMoreStopoversActivity();
            addMorePassengerActivity.addMorePassenger(place.getName(),place.getAddress(),place.getLatLng().latitude,place.getLatLng().longitude,fullAddress);
            finish();*/

            Intent intent=new Intent();
            intent.putExtra(ConstIntent.KEY_STOP_NAME,String.valueOf(place.getName()));
            intent.putExtra(ConstIntent.KEY_STOP_LATITUDE,String.valueOf(place.getLatLng().latitude));
            intent.putExtra(ConstIntent.KEY_STOP_LONGITUDE,String.valueOf(place.getLatLng().longitude));
            intent.putExtra(ConstIntent.KEY_STOP_IS_SELECTED,true);
            //intent.putExtra(ConstIntent.KEY_STOP_FULL_ADDRESS,fullAddress);
            setResult(REQUEST_CODE,intent);
            finish();//finishing activity

        }
    };




}
