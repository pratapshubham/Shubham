package com.braintech.carpooling.mvp.offer_a_ride.presenter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Braintech on 6/19/2018.
 */

public class OfferARideModel {

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
        @SerializedName("start_location")
        @Expose
        private String startLocation;
        @SerializedName("start_latitude")
        @Expose
        private String startLatitude;
        @SerializedName("start_longitude")
        @Expose
        private String startLongitude;
        @SerializedName("destination_location")
        @Expose
        private String destinationLocation;
        @SerializedName("destination_latitude")
        @Expose
        private String destinationLatitude;
        @SerializedName("destination_longitude")
        @Expose
        private String destinationLongitude;
        @SerializedName("journey_date")
        @Expose
        private String journeyDate;
        @SerializedName("journey_time")
        @Expose
        private String journeyTime;
        @SerializedName("no_of_passengers")
        @Expose
        private String noOfPassengers;
        @SerializedName("is_return_journey_offered")
        @Expose
        private String isReturnJourneyOffered;
        @SerializedName("message_for_passengers")
        @Expose
        private String messageForPassengers;
        @SerializedName("date_of_return_journey")
        @Expose
        private String dateOfReturnJourney;
        @SerializedName("time_of_return_journey")
        @Expose
        private String timeOfReturnJourney;
        @SerializedName("created")
        @Expose
        private String created;
        @SerializedName("updated")
        @Expose
        private String updated;
        @SerializedName("id")
        @Expose
        private Integer id;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getStartLocation() {
            return startLocation;
        }

        public void setStartLocation(String startLocation) {
            this.startLocation = startLocation;
        }

        public String getStartLatitude() {
            return startLatitude;
        }

        public void setStartLatitude(String startLatitude) {
            this.startLatitude = startLatitude;
        }

        public String getStartLongitude() {
            return startLongitude;
        }

        public void setStartLongitude(String startLongitude) {
            this.startLongitude = startLongitude;
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

        public String getIsReturnJourneyOffered() {
            return isReturnJourneyOffered;
        }

        public void setIsReturnJourneyOffered(String isReturnJourneyOffered) {
            this.isReturnJourneyOffered = isReturnJourneyOffered;
        }

        public String getMessageForPassengers() {
            return messageForPassengers;
        }

        public void setMessageForPassengers(String messageForPassengers) {
            this.messageForPassengers = messageForPassengers;
        }

        public String getDateOfReturnJourney() {
            return dateOfReturnJourney;
        }

        public void setDateOfReturnJourney(String dateOfReturnJourney) {
            this.dateOfReturnJourney = dateOfReturnJourney;
        }

        public String getTimeOfReturnJourney() {
            return timeOfReturnJourney;
        }

        public void setTimeOfReturnJourney(String timeOfReturnJourney) {
            this.timeOfReturnJourney = timeOfReturnJourney;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public String getUpdated() {
            return updated;
        }

        public void setUpdated(String updated) {
            this.updated = updated;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

    }

}