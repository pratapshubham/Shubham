package com.braintech.carpooling.mvp.find_a_ride.presenter_book_a_ride;

/**
 * Created by Braintech on 6/26/2018.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookARideModel {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("status")
    @Expose
    private Integer status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public class Data {

        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("ride_id")
        @Expose
        private Integer rideId;
        @SerializedName("destination_location")
        @Expose
        private String destinationLocation;
        @SerializedName("destination_latitude")
        @Expose
        private String destinationLatitude;
        @SerializedName("destination_longitude")
        @Expose
        private String destinationLongitude;
        @SerializedName("schedule_status")
        @Expose
        private String scheduleStatus;
        @SerializedName("id")
        @Expose
        private Integer id;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public Integer getRideId() {
            return rideId;
        }

        public void setRideId(Integer rideId) {
            this.rideId = rideId;
        }

        public String getDestinationLocation() {
            return destinationLocation;
        }

        public void setDestinationLocation(String destinationLocation) {
            this.destinationLocation = destinationLocation;
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

        public String getScheduleStatus() {
            return scheduleStatus;
        }

        public void setScheduleStatus(String scheduleStatus) {
            this.scheduleStatus = scheduleStatus;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

    }

}