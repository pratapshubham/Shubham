package com.braintech.carpooling.mvp.your_rides.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.braintech.carpooling.R;
import com.braintech.carpooling.mvp.find_a_ride.activity.FindARideActivity;
import com.braintech.carpooling.mvp.offer_a_ride.activity.LeavingFromActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class DashboardFragment extends Fragment {

    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @OnClick(R.id.btnOfferARide)
    void onClickOfferARide(){

        Intent intentLeaveFrom = new Intent(getActivity(), LeavingFromActivity.class);
        startActivity(intentLeaveFrom);

    }

    @OnClick(R.id.btnFindARide)
    void onClickFindARide(){

        Intent intentLeaveFrom = new Intent(getActivity(), FindARideActivity.class);
        startActivity(intentLeaveFrom);

    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


}
