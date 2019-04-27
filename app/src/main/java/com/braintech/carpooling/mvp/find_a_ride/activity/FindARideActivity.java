package com.braintech.carpooling.mvp.find_a_ride.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.braintech.carpooling.R;
import com.braintech.carpooling.common.helpers.AlertDialogHelper;
import com.braintech.carpooling.common.interfaces.OnClickInterface;
import com.braintech.carpooling.common.requestresponse.ConstIntent;
import com.braintech.carpooling.common.utility.Utils;
import com.braintech.carpooling.mvp.find_a_ride.adapter.PlaceArrayDestinationAdapter;
import com.braintech.carpooling.mvp.find_a_ride.presenter.FindARideContractor;
import com.braintech.carpooling.mvp.find_a_ride.presenter.FindARideModel;
import com.braintech.carpooling.mvp.find_a_ride.presenter.FindARidePresenterImpl;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FindARideActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks, FindARideContractor.FindARideView {

    Place sourcePlace;
    Place destinationPlace;

    int numberOfPassenger;

    Double sourceLatitude;
    Double sourceLongitude;
    Double destinationLatitude;
    Double destinationLongitude;

    @BindView(R.id.imgViewIncrease)
    ImageView imgViewIncrease;

    @BindView(R.id.imgViewDecrease)
    ImageView imgViewDecrease;

    @BindView(R.id.txtViewDatePicker)
    TextView txtViewDatePicker;

    @BindView(R.id.txtViewTimePicker)
    TextView txtViewTimePicker;

    @BindView(R.id.txtViewToolTitle)
    TextView txtViewToolTitle;

    @BindView(R.id.txtViewNumberOfSeatsAvailable)
    TextView txtViewNumberOfSeats;

    @BindView(R.id.autoCompleteTextViewSource)
    AutoCompleteTextView autoCompleteTextViewSource;

    @BindView(R.id.autoCompleteTextViewDestination)
    AutoCompleteTextView autoCompleteTextViewDestination;

    FindARidePresenterImpl findARidePresenterImpl;
    String rideDate;

    private static final String LOG_TAG = "FindARideActivity";
    private static final int GOOGLE_API_CLIENT_ID = 0;
    private static final int GOOGLE_API_CLIENT_ID_DESTINATION = 1;

    private GoogleApiClient mGoogleApiClient;
    private PlaceArrayAdapter mPlaceArrayAdapter;
    private PlaceArrayDestinationAdapter placeArrayDestinationAdapter;
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_aride);

        ButterKnife.bind(this);
        txtViewToolTitle.setText("Find a ride");
        numberOfPassenger = Integer.parseInt(txtViewNumberOfSeats.getText().toString().trim());

        findARidePresenterImpl = new FindARidePresenterImpl(this, this);
        setAutoCompleteTextViewSource();
        setAutoCompleteTextViewDestination();
    }

    @OnClick(R.id.imgViewBackArrow)
    void onClickBackArrow() {
        finish();
    }

    @OnClick(R.id.imgViewDecrease)
    void onClickImgDecrease() {

        Utils.hideKeyboardIfOpen(this);
        numberOfPassenger = Integer.parseInt(txtViewNumberOfSeats.getText().toString().trim());
        if (numberOfPassenger != 1) {
            numberOfPassenger = numberOfPassenger - 1;
            txtViewNumberOfSeats.setText(String.valueOf(numberOfPassenger));
            if(numberOfPassenger == 1){
                imgViewDecrease.setEnabled(false);
                imgViewDecrease.setColorFilter(ContextCompat.getColor(this, R.color.color_light_grey), android.graphics.PorterDuff.Mode.MULTIPLY);
            }
        } else {
            //imgViewDecrease.setVisibility(View.GONE);
            imgViewDecrease.setEnabled(false);
            imgViewDecrease.setColorFilter(ContextCompat.getColor(this, R.color.color_light_grey), android.graphics.PorterDuff.Mode.MULTIPLY);
        }

        if (numberOfPassenger > 4) {
            //imgViewIncrease.setVisibility(View.GONE);
            imgViewIncrease.setEnabled(false);
            imgViewIncrease.setColorFilter(ContextCompat.getColor(this, R.color.color_light_grey), android.graphics.PorterDuff.Mode.MULTIPLY);
        } else {
            //imgViewIncrease.setVisibility(View.VISIBLE);
            imgViewIncrease.setEnabled(true);
            imgViewIncrease.setColorFilter(ContextCompat.getColor(this, R.color.color_black), android.graphics.PorterDuff.Mode.MULTIPLY);
        }

        Log.e("numberOfPassenger ->D", String.valueOf(numberOfPassenger));

    }

    @OnClick(R.id.imgViewIncrease)
    void onClickImgIncrease() {

        Utils.hideKeyboardIfOpen(this);
        numberOfPassenger = Integer.parseInt(txtViewNumberOfSeats.getText().toString().trim());

        if (numberOfPassenger < 4) {
            numberOfPassenger = numberOfPassenger + 1;
            txtViewNumberOfSeats.setText(String.valueOf(numberOfPassenger));
            if(numberOfPassenger == 4){
                imgViewIncrease.setEnabled(false);
                imgViewIncrease.setColorFilter(ContextCompat.getColor(this, R.color.color_light_grey), android.graphics.PorterDuff.Mode.MULTIPLY);
            }
        } else {
            //imgViewIncrease.setVisibility(View.GONE);
            imgViewIncrease.setEnabled(false);
            imgViewIncrease.setColorFilter(ContextCompat.getColor(this, R.color.color_light_grey), android.graphics.PorterDuff.Mode.MULTIPLY);
        }

        if (numberOfPassenger == 1) {
            //imgViewDecrease.setVisibility(View.GONE);
            imgViewDecrease.setEnabled(false);
            imgViewDecrease.setColorFilter(ContextCompat.getColor(this, R.color.color_light_grey), android.graphics.PorterDuff.Mode.MULTIPLY);
        } else {
            //imgViewDecrease.setVisibility(View.VISIBLE);
            imgViewDecrease.setEnabled(true);
            imgViewDecrease.setColorFilter(ContextCompat.getColor(this, R.color.color_black), android.graphics.PorterDuff.Mode.MULTIPLY);
        }

        Log.e("numberOfPassenger ->I", String.valueOf(numberOfPassenger));
    }

    @OnClick(R.id.txtViewDatePicker)
    void onClickDataPicker() {


        Utils.hideKeyboardIfOpen(this);
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);


        final DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, monthOfYear, dayOfMonth);

                        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                        rideDate = format.format(calendar.getTime());

                        txtViewDatePicker.setText(rideDate);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    @OnClick(R.id.txtViewTimePicker)
    void onClickTimePicker() {
        // Get Current Time

        Utils.hideKeyboardIfOpen(this);

        final Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        if(String.valueOf(minute).length() > 1)
                        txtViewTimePicker.setText(hourOfDay + ":" + minute);
                        else {
                            txtViewTimePicker.setText(hourOfDay + ":0" + minute);
                        }
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();

    }

    @OnClick(R.id.btnConfirm)
    void onClickBtnConfirm() {

        if (sourcePlace != null && destinationPlace != null) {
            sourceLatitude = sourcePlace.getLatLng().latitude;
            sourceLongitude = sourcePlace.getLatLng().longitude;
            destinationLatitude = destinationPlace.getLatLng().latitude;
            destinationLongitude = destinationPlace.getLatLng().longitude;

            //rideDate = txtViewDatePicker.getText().toString().trim();
            String rideTime = txtViewTimePicker.getText().toString().trim();
            String numberOfPassenger = txtViewNumberOfSeats.getText().toString().trim();

            if (isValidate(sourceLatitude, sourceLongitude, destinationLatitude, destinationLongitude, rideDate, rideTime, numberOfPassenger)) {
                findARidePresenterImpl.onFindARideDataSubmit(String.valueOf(sourceLatitude), String.valueOf(sourceLongitude), String.valueOf(destinationLatitude), String.valueOf(destinationLongitude), rideDate, rideTime, numberOfPassenger);
            }

        } else {
            AlertDialogHelper.showMessage(this, "Please enter address.");
        }
    }

    @Override
    public void onFindARideSuccess(FindARideModel findARideModel) {
        //AlertDialogHelper.showMessage(this, findARideModel.getMessage());

        Intent intent = new Intent(FindARideActivity.this, ListOfFindRidesActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(ConstIntent.KEY_LIST_OF_RIDES_MODEL, (Serializable) findARideModel.getData());
        intent.putExtras(bundle);
        startActivity(intent);

    }

    @Override
    public void onFindARideUnSuccess(String message) {

        AlertDialogHelper.showMessage(this, message);
    }

    @Override
    public void onFindARideInternetError() {
        AlertDialogHelper.alertInternetError(this, onRetryFindARide);

    }

    private boolean isValidate(Double sourceLatitude, Double sourceLongitude, Double destinationLatitude, Double destinationLongitude, String rideDate, String rideTime, String numberOfPassenger) {

        if (Utils.isEmptyOrNull(rideDate)) {
            AlertDialogHelper.showMessage(this, getString(R.string.error_date));
            return false;
        } else if (Utils.isEmptyOrNull(rideTime)) {
            AlertDialogHelper.showMessage(this, getString(R.string.error_time));
            return false;
        } else if (Utils.isEmptyOrNull(numberOfPassenger)) {
            AlertDialogHelper.showMessage(this, getString(R.string.error_number_passenger));
            return false;
        }
        return true;
    }


    private void setAutoCompleteTextViewDestination() {
        mGoogleApiClient = new GoogleApiClient.Builder(FindARideActivity.this)
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(this, GOOGLE_API_CLIENT_ID_DESTINATION, this)
                .addConnectionCallbacks(this)
                .build();

        autoCompleteTextViewDestination.setThreshold(3);

        autoCompleteTextViewDestination.setOnItemClickListener(mAutocompleteClickListenerDestination);

        placeArrayDestinationAdapter = new PlaceArrayDestinationAdapter(this, android.R.layout.simple_list_item_1,
                BOUNDS_MOUNTAIN_VIEW, null);
        autoCompleteTextViewDestination.setAdapter(placeArrayDestinationAdapter);
    }

    private void setAutoCompleteTextViewSource() {

        mGoogleApiClient = new GoogleApiClient.Builder(FindARideActivity.this)
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(this, GOOGLE_API_CLIENT_ID, this)
                .addConnectionCallbacks(this)
                .build();

        autoCompleteTextViewSource.setThreshold(3);

        autoCompleteTextViewSource.setOnItemClickListener(mAutocompleteClickListenerSource);

        mPlaceArrayAdapter = new PlaceArrayAdapter(this, android.R.layout.simple_list_item_1,
                BOUNDS_MOUNTAIN_VIEW, null);
        autoCompleteTextViewSource.setAdapter(mPlaceArrayAdapter);
    }

    private AdapterView.OnItemClickListener mAutocompleteClickListenerSource
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final PlaceArrayAdapter.PlaceAutocomplete item = mPlaceArrayAdapter.getItem(position);
            final String placeId = String.valueOf(item.placeId);
            Log.e(LOG_TAG, "Selected: " + item.description);
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallbackSource);
            Log.e(LOG_TAG, "Fetching details for ID: " + item.placeId);
        }
    };

    private AdapterView.OnItemClickListener mAutocompleteClickListenerDestination
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final PlaceArrayDestinationAdapter.PlaceAutocomplete item = placeArrayDestinationAdapter.getItem(position);
            final String placeId = String.valueOf(item.placeId);
            Log.e(LOG_TAG, "Selected: " + item.description);
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallbackDestination);
            Log.e(LOG_TAG, "Fetching details for ID: " + item.placeId);
        }
    };
    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallbackSource
            = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                Log.e(LOG_TAG, "Place query did not complete. Error: " +
                        places.getStatus().toString());
                return;
            }
            Utils.hideKeyboardIfOpen(FindARideActivity.this);

            // Selecting the first object buffer.
            sourcePlace = (Place) places.get(0);
            CharSequence attributions = places.getAttributions();
            Log.e(LOG_TAG, "getName " +
                    Html.fromHtml(sourcePlace.getName() + ""));


            Log.e(LOG_TAG, "getId() ) " +
                    Html.fromHtml(sourcePlace.getId() + ""));

            Log.e(LOG_TAG, "getAddress " +
                    Html.fromHtml(sourcePlace.getAddress() + ""));

            Log.e(LOG_TAG, "getWebsiteUri()  " +
                    sourcePlace.getWebsiteUri() + "");

            Log.e(LOG_TAG, "getPhoneNumber() + " +
                    Html.fromHtml(sourcePlace.getPhoneNumber() + ""));

            Log.e(LOG_TAG, "place.getLatLng() + " +
                    Html.fromHtml(sourcePlace.getLatLng().latitude + ""));
            String sourceAddress = autoCompleteTextViewSource.getText().toString().trim();
            //String destinationAddress = autoCompleteTextViewDestination.getText().toString().trim();

            Log.e("source address", sourceAddress);

            // Log.e("destination address",destinationAddress);

        }
    };

    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallbackDestination
            = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                Log.e(LOG_TAG, "Place query did not complete. Error: " +
                        places.getStatus().toString());
                return;
            }

            Utils.hideKeyboardIfOpen(FindARideActivity.this);

            // Selecting the first object buffer.
            destinationPlace = (Place) places.get(0);
            CharSequence attributions = places.getAttributions();
            Log.e(LOG_TAG, "getName " +
                    Html.fromHtml(destinationPlace.getName() + ""));


            Log.e(LOG_TAG, "getId() ) " +
                    Html.fromHtml(destinationPlace.getId() + ""));

            Log.e(LOG_TAG, "getAddress " +
                    Html.fromHtml(destinationPlace.getAddress() + ""));

            Log.e(LOG_TAG, "getWebsiteUri()  " +
                    destinationPlace.getWebsiteUri() + "");

            Log.e(LOG_TAG, "getPhoneNumber() + " +
                    Html.fromHtml(destinationPlace.getPhoneNumber() + ""));

            Log.e(LOG_TAG, "place.getLatLng() + " +
                    Html.fromHtml(destinationPlace.getLatLng().latitude + ""));
            String sourceAddress = autoCompleteTextViewSource.getText().toString().trim();
            //String destinationAddress = autoCompleteTextViewDestination.getText().toString().trim();

            Log.e("source address", sourceAddress);


            // Log.e("destination address",destinationAddress);

        }
    };


    @Override
    public void onConnected(Bundle bundle) {
        mPlaceArrayAdapter.setGoogleApiClient(mGoogleApiClient);
        placeArrayDestinationAdapter.setGoogleApiClient(mGoogleApiClient);
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
        placeArrayDestinationAdapter.setGoogleApiClient(null);
        Log.e(LOG_TAG, "Google Places API connection suspended.");
    }

    OnClickInterface onRetryFindARide = new OnClickInterface() {
        @Override
        public void onClick() {
            onClickBtnConfirm();
        }
    };


}
