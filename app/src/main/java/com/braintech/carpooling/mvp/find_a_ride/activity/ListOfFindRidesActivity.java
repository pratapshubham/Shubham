package com.braintech.carpooling.mvp.find_a_ride.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.braintech.carpooling.R;
import com.braintech.carpooling.common.requestresponse.ConstIntent;
import com.braintech.carpooling.mvp.find_a_ride.adapter.ListOfFindRideItemAdapter;
import com.braintech.carpooling.mvp.find_a_ride.presenter.FindARideModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ListOfFindRidesActivity extends AppCompatActivity {

    @BindView(R.id.recyclerViewItemList)
    RecyclerView recyclerViewItemList;

    @BindView(R.id.txtViewSourceAddress)
    TextView txtViewSourceAddress;

    @BindView(R.id.txtViewDestinationAddress)
    TextView txtViewDestinationAddress;

    @BindView(R.id.txtViewDate)
    TextView txtViewDate;

    @BindView(R.id.txtViewTime)
    TextView txtViewTime;

    ListOfFindRideItemAdapter listOfFindRideItemAdapter;

    ArrayList<FindARideModel.Datum> arrayListFindARideDatum;
    FindARideModel.Datum findARideModelData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_find_rides);

        ButterKnife.bind(this);

        arrayListFindARideDatum = new ArrayList<>();
        setLayoutManager();
        getIntentData();

        setData();
        setAdapter();
    }

    @OnClick(R.id.imgViewBackArrow)
    void onClickBackArrow(){
        finish();
    }


    private void getIntentData() {

        Intent intent = getIntent();
        if (intent.hasExtra(ConstIntent.KEY_LIST_OF_RIDES_MODEL)) {
            arrayListFindARideDatum=(ArrayList<FindARideModel.Datum>) intent.getSerializableExtra(ConstIntent.KEY_LIST_OF_RIDES_MODEL);
            //arrayListFindARideDatum.add(findARideModelData);
        }
    }


    private void setData() {

        if (arrayListFindARideDatum != null) {

            // if(arrayListFindARideDatum.size() > 0) {

            String sourceAddress = arrayListFindARideDatum.get(0).getStartLocation();
            String destinationAddress = arrayListFindARideDatum.get(0).getDestinationLocation();
            String date = arrayListFindARideDatum.get(0).getJourneyDate();
            String time = arrayListFindARideDatum.get(0).getJourneyTime();

            txtViewSourceAddress.setText(sourceAddress);
            txtViewDestinationAddress.setText(destinationAddress);
            txtViewDate.setText(date);
            //txtViewTime.setText(time);
            //  }

        }
    }

    private void setLayoutManager() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewItemList.setLayoutManager(linearLayoutManager);
    }

    private void setAdapter() {
        listOfFindRideItemAdapter = new ListOfFindRideItemAdapter(this, arrayListFindARideDatum);
        recyclerViewItemList.setAdapter(listOfFindRideItemAdapter);
    }
}
