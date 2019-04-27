package com.braintech.carpooling.mvp.find_a_ride.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.braintech.carpooling.R;
import com.braintech.carpooling.common.helpers.AlertDialogHelper;
import com.braintech.carpooling.common.interfaces.OnClickInterface;
import com.braintech.carpooling.common.requestresponse.ConstIntent;
import com.braintech.carpooling.common.utility.Utils;
import com.braintech.carpooling.mvp.find_a_ride.presenter.FindARideModel;
import com.braintech.carpooling.mvp.find_a_ride.presenter_book_a_ride.BookARideModel;
import com.braintech.carpooling.mvp.find_a_ride.presenter_book_a_ride.DetailsFindARidePresenterImpl;
import com.braintech.carpooling.mvp.find_a_ride.presenter_book_a_ride.DetailsFindRideContractor;
import com.braintech.carpooling.mvp.your_rides.activity.YourRidesActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailFindRideActivity extends AppCompatActivity implements DetailsFindRideContractor.DetailsFindARideView {


    FindARideModel.Datum findARideDatumModel;

    @BindView(R.id.txtViewToolSourceAddress)
    TextView txtViewToolSourceAddress;

    @BindView(R.id.txtViewToolDestinationAddress)
    TextView txtViewToolDestinationAddress;

    @BindView(R.id.txtViewToolDate)
    TextView txtViewToolDate;

    @BindView(R.id.txtViewToolTime)
    TextView txtViewToolTime;

    @BindView(R.id.txtViewSourceAddress)
    TextView txtViewSourceAddress;

    @BindView(R.id.txtViewSourceFullAddress)
    TextView txtViewSourceFullAddress;

    @BindView(R.id.txtViewDestinationAddress)
    TextView txtViewDestinationAddress;

    @BindView(R.id.txtViewDestinationFullAddress)
    TextView txtViewDestinationFullAddress;

    @BindView(R.id.txtViewNumberOfSeatsAvailable)
    TextView txtViewNumberOfSeatsAvailable;

    @BindView(R.id.txtViewAmountPerSeat)
    TextView txtViewAmountPerSeat;

    @BindView(R.id.txtViewNumberSeatsToBook)
    TextView txtViewNumberSeatsToBook;

    @BindView(R.id.txtViewTotalAmount)
    TextView txtViewTotalAmount;

    @BindView(R.id.imgViewDecrease)
    ImageView imgViewDecrease;

    @BindView(R.id.imgViewIncrease)
    ImageView imgViewIncrease;

    String amountPerSeat;
    String numberOfSeats;
    String destinationAddress;
    String sourceAddress;

    String journeyDate;
    String journeyTime;

    int numberOfPassengerToBook;
    int numberOfSeatsAvailable;
    int totalAmount;
    int BASE_PRICE;

    String finalBookingSeat;

    DetailsFindARidePresenterImpl detailsFindARidePresenterImpl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_find_ride);

        ButterKnife.bind(this);
        getIntentData();
        setData();

        detailsFindARidePresenterImpl = new DetailsFindARidePresenterImpl(this, this);

    }

    @Override
    public void onFindARideSuccess(BookARideModel bookARideModel) {

        showAlertForSuccess(bookARideModel.getMessage());
    }

    @Override
    public void onFindARideUnSuccess(String message) {

        AlertDialogHelper.showMessage(this, message);

    }

    @Override
    public void onFindARideInternetError() {
        AlertDialogHelper.alertInternetError(this, onRetryBookARide);
    }

    @OnClick(R.id.imgViewBackArrow)
    void onClickBackArrow() {
        finish();
    }

    @OnClick(R.id.btnBook)
    void onClickBtnBook() {
        finalBookingSeat = txtViewNumberSeatsToBook.getText().toString().trim();

        detailsFindARidePresenterImpl.onBookARide(findARideDatumModel, finalBookingSeat);

    }

    @OnClick(R.id.imgViewDecrease)
    void onClickImgDecrease() {

        Utils.hideKeyboardIfOpen(this);

        numberOfPassengerToBook = Integer.parseInt(txtViewNumberSeatsToBook.getText().toString().trim());
        totalAmount = (numberOfPassengerToBook * BASE_PRICE);
        if (numberOfPassengerToBook != 1) {
            numberOfPassengerToBook = numberOfPassengerToBook - 1;
            totalAmount = (BASE_PRICE * numberOfPassengerToBook);
            txtViewTotalAmount.setText(String.valueOf(totalAmount));
            txtViewNumberSeatsToBook.setText(String.valueOf(numberOfPassengerToBook));
        } else {
            imgViewDecrease.setVisibility(View.GONE);
        }

        if (numberOfPassengerToBook > numberOfSeatsAvailable) {
            imgViewIncrease.setVisibility(View.GONE);
        } else {
            imgViewIncrease.setVisibility(View.VISIBLE);
        }

        Log.e("numberOfPassenger ->D", String.valueOf(numberOfPassengerToBook));

    }

    @OnClick(R.id.imgViewIncrease)
    void onClickImgIncrease() {

        Utils.hideKeyboardIfOpen(this);
        numberOfPassengerToBook = Integer.parseInt(txtViewNumberSeatsToBook.getText().toString().trim());
        totalAmount = (numberOfPassengerToBook * BASE_PRICE);
        if (numberOfPassengerToBook < numberOfSeatsAvailable) {
            numberOfPassengerToBook = numberOfPassengerToBook + 1;
            totalAmount = (BASE_PRICE * numberOfPassengerToBook);
            txtViewTotalAmount.setText(String.valueOf(totalAmount));
            txtViewNumberSeatsToBook.setText(String.valueOf(numberOfPassengerToBook));
        } else {
            imgViewIncrease.setVisibility(View.GONE);
        }

        if (numberOfPassengerToBook == 1) {
            imgViewDecrease.setVisibility(View.GONE);
        } else {
            imgViewDecrease.setVisibility(View.VISIBLE);
        }

        Log.e("numberOfPassenger ->I", String.valueOf(numberOfPassengerToBook));
    }


    private void setData() {

        if (findARideDatumModel != null) {

            //if(arrayListFindARideDatum.size() > 0){

            sourceAddress = findARideDatumModel.getStartLocation();
            destinationAddress = findARideDatumModel.getDestinationLocation();
            numberOfSeats = findARideDatumModel.getNoOfPassengers();
            numberOfSeatsAvailable = Integer.parseInt(numberOfSeats);
            journeyDate = findARideDatumModel.getJourneyDate();
            journeyTime = findARideDatumModel.getJourneyTime();


            String strPrice = findARideDatumModel.getDistance();
            //Log.e("price before-->",strPrice);
            String newPrice = strPrice.replaceAll("\u00a0", "");
            //Log.e("price after-->",newPrice);

            BASE_PRICE = ((int) Float.parseFloat(newPrice) * 6);
            amountPerSeat = String.valueOf(BASE_PRICE);

            txtViewSourceAddress.setText(sourceAddress);
            txtViewToolSourceAddress.setText(sourceAddress);
            txtViewToolDestinationAddress.setText(destinationAddress);
            txtViewDestinationAddress.setText(destinationAddress);
            txtViewNumberOfSeatsAvailable.setText(numberOfSeats);
            txtViewAmountPerSeat.setText(amountPerSeat);
            txtViewTotalAmount.setText(amountPerSeat);

            txtViewToolDate.setText(journeyDate + ", ");
            txtViewToolTime.setText(journeyTime);
            //}
        }
    }

    private void getIntentData() {

        Intent intent = getIntent();
        if (intent.hasExtra(ConstIntent.KEY_FIND_A_RIDE_MODEL1)) {
            findARideDatumModel = (FindARideModel.Datum) intent.getSerializableExtra(ConstIntent.KEY_FIND_A_RIDE_MODEL1);
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
                Intent intent = new Intent(DetailFindRideActivity.this, YourRidesActivity.class);
                startActivity(intent);

            }
        });

        // Showing Alert Message
        alertDialog.show();
    }


    OnClickInterface onRetryBookARide = new OnClickInterface() {
        @Override
        public void onClick() {
            onClickBtnBook();
        }
    };
}
