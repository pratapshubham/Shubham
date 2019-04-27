package com.braintech.carpooling.mvp.find_a_ride.presenter;

/**
 * Created by Braintech on 6/21/2018.
 */

import java.io.Serializable;
import java.util.ArrayList;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class FindARideModel implements Serializable {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private ArrayList<Datum> data = null;
    @SerializedName("status")
    @Expose
    private Integer status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Datum> getData() {
        return data;
    }

    public void setData(ArrayList<Datum> data) {
        this.data = data;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public class Datum implements Serializable {

        @SerializedName("user_name")
        @Expose
        private String userName;
        @SerializedName("destination_latitude")
        @Expose
        private String destinationLatitude;
        @SerializedName("destination_longitude")
        @Expose
        private String destinationLongitude;
        @SerializedName("pickup_distance")
        @Expose
        private String pickupDistance;
        @SerializedName("ride_id")
        @Expose
        private Integer rideId;
        @SerializedName("distance")
        @Expose
        private String distance;
        @SerializedName("start_location")
        @Expose
        private String startLocation;
        @SerializedName("destination_location")
        @Expose
        private String destinationLocation;
        @SerializedName("journey_date")
        @Expose
        private String journeyDate;
        @SerializedName("journey_time")
        @Expose
        private String journeyTime;
        @SerializedName("no_of_passengers")
        @Expose
        private String noOfPassengers;
        @SerializedName("rides_stopages")
        @Expose
        private ArrayList<RidesStopage> ridesStopages = null;

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getDestinationLatitude() {
            return destinationLatitude;
        }

        public void setDestinationLatitude(String destinationLatitude) {
            this.destinationLatitude = destinationLatitude;
        }

        public String getDestinationLongitude() {
            return destinationLongitude;
        }

        public void setDestinationLongitude(String destinationLongitude) {
            this.destinationLongitude = destinationLongitude;
        }

        public Integer getRideId() {
            return rideId;
        }

        public void setRideId(Integer rideId) {
            this.rideId = rideId;
        }

        public String getPickupDistance() {
            return pickupDistance;
        }

        public void setPickupDistance(String pickupDistance) {
            this.pickupDistance = pickupDistance;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getStartLocation() {
            return startLocation;
        }

        public void setStartLocation(String startLocation) {
            this.startLocation = startLocation;
        }

        public String getDestinationLocation() {
            return destinationLocation;
        }

        public void setDestinationLocation(String destinationLocation) {
            this.destinationLocation = destinationLocation;
        }

        public String getJourneyDate() {
            return journeyDate;
        }

        public void setJourneyDate(String journeyDate) {
            this.journeyDate = journeyDate;
        }

        public String getJourneyTime() {
            return journeyTime;
        }

        public void setJourneyTime(String journeyTime) {
            this.journeyTime = journeyTime;
        }

        public String getNoOfPassengers() {
            return noOfPassengers;
        }

        public void setNoOfPassengers(String noOfPassengers) {
            this.noOfPassengers = noOfPassengers;
        }

        public ArrayList<RidesStopage> getRidesStopages() {
            return ridesStopages;
        }

        public void setRidesStopages(ArrayList<RidesStopage> ridesStopages) {
            this.ridesStopages = ridesStopages;
        }

        public class RidesStopage implements Serializable {

            @SerializedName("address")
            @Expose
            private String address;
            @SerializedName("latitude")
            @Expose
            private String latitude;
            @SerializedName("longitude")
            @Expose
            private String longitude;

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getLatitude() {
                return latitude;
            }

            public void setLatitude(String latitude) {
                this.latitude = latitude;
            }

            public String getLongitude() {
                return longitude;
            }

            public void setLongitude(String longitude) {
                this.longitude = longitude;
            }

        }

    }


}

