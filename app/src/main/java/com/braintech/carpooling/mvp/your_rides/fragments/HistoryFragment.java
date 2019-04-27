package com.braintech.carpooling.mvp.your_rides.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.braintech.carpooling.R;
import com.braintech.carpooling.common.helpers.AlertDialogHelper;
import com.braintech.carpooling.common.interfaces.OnClickInterface;
import com.braintech.carpooling.mvp.your_rides.adapter.OfferRidesItemAdapter;
import com.braintech.carpooling.mvp.your_rides.offer_a_ride_presenter.ListOfferARideContractor;
import com.braintech.carpooling.mvp.your_rides.offer_a_ride_presenter.OfferARideModel;
import com.braintech.carpooling.mvp.your_rides.offer_a_ride_presenter.ListOfferARidePresenterImpl;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HistoryFragment extends Fragment implements ListOfferARideContractor.ListOfferARideView {

    @BindView(R.id.imgViewBackArrow)
    ImageView imgViewBackArrow;

    @BindView(R.id.relLayYourBookings)
    RelativeLayout relLayYourBookings;

    @BindView(R.id.relLayOfferRides)
    RecyclerView relLayOfferRides;

    @BindView(R.id.txtViewOfferRidesMessage)
    TextView txtViewOfferRidesMessage;

    OfferRidesItemAdapter offerRidesItemAdapter;

    ListOfferARidePresenterImpl listOfferARidePresenterImpl;

    OfferARideModel offerARideModel;

    public HistoryFragment(){

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        ButterKnife.bind(this,view);
        getAllOfferRides();
        setLayoutManager();
        relLayYourBookings.setVisibility(View.GONE); // when there is no booking available.
        imgViewBackArrow.setVisibility(View.GONE);
        return view;
    }

    private void getAllOfferRides() {

        listOfferARidePresenterImpl = new ListOfferARidePresenterImpl(getActivity(),this);
        listOfferARidePresenterImpl.listOfferARideGetData();

    }

    private void setLayoutManager() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        relLayOfferRides.setLayoutManager(linearLayoutManager);
    }




    @Override
    public void onListOfferARideSuccess(OfferARideModel offerARideModel) {
        this.offerARideModel = offerARideModel;
        if(offerARideModel.getData().size() > 0){
            txtViewOfferRidesMessage.setVisibility(View.GONE);
            relLayOfferRides.setVisibility(View.VISIBLE);
            setAdapter();
        }

    }

    private void setAdapter() {
        offerRidesItemAdapter = new OfferRidesItemAdapter(getActivity(), offerARideModel.getData());
        relLayOfferRides.setAdapter(offerRidesItemAdapter);
    }


    @Override
    public void OnListOfferARideUnSuccess(String message) {
        AlertDialogHelper.showMessage(getActivity(),message);
    }

    @Override
    public void onListOfferARideInternetError() {
        AlertDialogHelper.alertInternetError(getActivity(), onRetryGetAllOfferRides);
    }

    OnClickInterface onRetryGetAllOfferRides = new OnClickInterface() {
        @Override
        public void onClick() {
            getAllOfferRides();
        }
    };
}
