package com.braintech.carpooling.mvp.offer_a_ride.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.braintech.carpooling.R;
import com.braintech.carpooling.mvp.offer_a_ride.model.StopListModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Braintech on 3/6/2018.
 */

public class AddMoreStopsAdapter extends RecyclerView.Adapter<AddMoreStopsAdapter.ViewHolder> {
    Context context;
    ArrayList<StopListModel> arrayListStopList;

    boolean isEdit;

    public AddMoreStopsAdapter(Context context, ArrayList<StopListModel> arrayListStopList) {
        this.context = context;
        this.arrayListStopList = arrayListStopList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlist_add_more_stopovers, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final StopListModel stopListModel = arrayListStopList.get(position);
        String placeName = stopListModel.getLocationName();

        holder.txtViewPlaceName.setText(placeName);

        /*if(holder.checkboxPlaceSelected.isChecked()){

            stopListModel.setSelected(false);
        }else{
            stopListModel.setSelected(true);
        }*/

        holder.checkboxPlaceSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(stopListModel.isSelected()){
                    holder.checkboxPlaceSelected.setChecked(false);
                    stopListModel.setSelected(false);
                }else {
                    holder.checkboxPlaceSelected.setChecked(true);
                    stopListModel.setSelected(true);
                }
            }
        });



    }

    @Override
    public int getItemCount() {
        return arrayListStopList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

       @BindView(R.id.txtViewPlaceName)
        TextView txtViewPlaceName;

       @BindView(R.id.checkboxPlaceSelected)
        CheckBox checkboxPlaceSelected;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
