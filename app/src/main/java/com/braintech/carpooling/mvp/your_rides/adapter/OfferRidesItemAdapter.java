package com.braintech.carpooling.mvp.your_rides.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.braintech.carpooling.R;
import com.braintech.carpooling.mvp.find_a_ride.presenter.FindARideModel;
import com.braintech.carpooling.mvp.your_rides.activity.YourRidesActivity;
import com.braintech.carpooling.mvp.your_rides.offer_a_ride_presenter.OfferARideModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Braintech on 3/6/2018.
 */

public class OfferRidesItemAdapter extends RecyclerView.Adapter<OfferRidesItemAdapter.ViewHolder> {
    Activity context;
    ArrayList<OfferARideModel.Datum> arrayListFindARide;

    boolean isEdit;

    public OfferRidesItemAdapter(Activity context, ArrayList<OfferARideModel.Datum> arrayListFindARide) {
        this.context = context;
        this.arrayListFindARide = arrayListFindARide;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_your_offer_rides_show, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        OfferARideModel.Datum offerARideModel = arrayListFindARide.get(position);

        String journeyDate = offerARideModel.getJourneyDate();
        String journeyTime = offerARideModel.getJourneyTime();
        String sourceLocation = offerARideModel.getStartLocation();
        String destinationLocation = offerARideModel.getDestinationLocation();

        if(offerARideModel.getStopsInBetween() != null) {

            StringBuffer stringBufferStopInBetween = new StringBuffer();
            for (int i = 0; i < offerARideModel.getStopsInBetween().size(); i++) {
                stringBufferStopInBetween.append(offerARideModel.getStopsInBetween().get(i).getAddress()).append(", ");
            }
            String stopsInBetween = stringBufferStopInBetween.substring(0,stringBufferStopInBetween.length()-2);
            holder.txtViewStopsInBetween.setText(stopsInBetween);
        }else {
            holder.txtViewStopsInBetween.setText("--NA--");
        }

        holder.txtViewDate.setText(journeyDate);
        holder.txtViewTime.setText(journeyTime);
        holder.txtViewSourceAddress.setText(sourceLocation);
        holder.txtViewDestinationAddress.setText(destinationLocation);
        holder.txtViewStatus.setText("--NA--");



        /*holder.txtViewStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((YourRidesActivity)context).loadFragment();
            }
        });*/



    }

    @Override
    public int getItemCount() {
        return arrayListFindARide.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

       @BindView(R.id.txtViewDate)
       TextView txtViewDate;

        @BindView(R.id.txtViewTime)
        TextView txtViewTime;

        @BindView(R.id.txtViewSourceAddress)
        TextView txtViewSourceAddress;

        @BindView(R.id.txtViewDestinationAddress)
        TextView txtViewDestinationAddress;

        @BindView(R.id.txtViewStatus)
        TextView txtViewStatus;

        @BindView(R.id.txtViewStopsInBetween)
        TextView txtViewStopsInBetween;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
