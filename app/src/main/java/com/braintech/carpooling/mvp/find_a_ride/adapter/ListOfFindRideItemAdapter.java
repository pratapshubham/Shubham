package com.braintech.carpooling.mvp.find_a_ride.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.braintech.carpooling.R;
import com.braintech.carpooling.common.requestresponse.ConstIntent;
import com.braintech.carpooling.mvp.find_a_ride.activity.DetailFindRideActivity;
import com.braintech.carpooling.mvp.find_a_ride.presenter.FindARideModel;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Braintech on 3/6/2018.
 */

public class ListOfFindRideItemAdapter extends RecyclerView.Adapter<ListOfFindRideItemAdapter.ViewHolder> {
    Activity context;
    ArrayList<FindARideModel.Datum> arrayListFindARideDatum;

    public ListOfFindRideItemAdapter(Activity context, ArrayList<FindARideModel.Datum> arrayListFindARideDatum) {
        this.context = context;
        this.arrayListFindARideDatum = arrayListFindARideDatum;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlist_find_a_ride, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final FindARideModel.Datum listOfferARideModel = arrayListFindARideDatum.get(position);


        String ownerName = listOfferARideModel.getUserName();
        String strPrice = listOfferARideModel.getDistance();
        Log.e("price before-->",strPrice);
        String newPrice = strPrice.replaceAll("\u00a0","");
        Log.e("price after-->",newPrice);
        String journeyPrice = String.valueOf((int)Float.parseFloat(newPrice) * 6);
        String sourceLocation = listOfferARideModel.getStartLocation();
        String destinationLocation = listOfferARideModel.getDestinationLocation();

        holder.txtViewSourceAddress.setText(sourceLocation);
        holder.txtViewDestinationAddress.setText(destinationLocation);
        holder.txtViewOwnerName.setText(ownerName);
        holder.txtViewPrice.setText(journeyPrice);

        holder.relLayMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, DetailFindRideActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(ConstIntent.KEY_FIND_A_RIDE_MODEL1,listOfferARideModel);
                intent.putExtras(bundle);
                context.startActivity(intent);

            }
        });



    }

    @Override
    public int getItemCount() {
        return arrayListFindARideDatum.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

       @BindView(R.id.txtViewSourceAddress)
       TextView txtViewSourceAddress;

        @BindView(R.id.txtViewDestinationAddress)
        TextView txtViewDestinationAddress;

        @BindView(R.id.txtViewPrice)
        TextView txtViewPrice;

        @BindView(R.id.txtViewOwnerName)
        TextView txtViewOwnerName;

        @BindView(R.id.relLayMain)
        RelativeLayout relLayMain;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
