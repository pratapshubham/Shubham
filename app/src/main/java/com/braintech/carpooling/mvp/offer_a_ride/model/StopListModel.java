package com.braintech.carpooling.mvp.offer_a_ride.model;

import java.io.Serializable;

/**
 * Created by Braintech on 6/22/2018.
 */

public class StopListModel implements Serializable{

    private String locationName;
    private String StopLatitude;
    private String longitude;
    private boolean isSelected;


    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getStopLatitude() {
        return StopLatitude;
    }

    public void setStopLatitude(String stopLatitude) {
        StopLatitude = stopLatitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
