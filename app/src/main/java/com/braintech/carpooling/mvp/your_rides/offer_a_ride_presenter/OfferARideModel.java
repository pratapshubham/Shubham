package com.braintech.carpooling.mvp.your_rides.offer_a_ride_presenter;

/**
 * Created by Braintech on 6/21/2018.
 */

import java.util.ArrayList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OfferARideModel {

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

    public class Datum {

        @SerializedName("id")
        @Expose
        private Integer id;
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
        @SerializedName("date_of_return_journey")
        @Expose
        private String dateOfReturnJourney;
        @SerializedName("time_of_return_journey")
        @Expose
        private String timeOfReturnJourney;
        @SerializedName("message_for_passengers")
        @Expose
        private String messageForPassengers;
        @SerializedName("stops_in_between")
        @Expose
        private ArrayList<StopsInBetween> stopsInBetween = null;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
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

        public String getMessageForPassengers() {
            return messageForPassengers;
        }

        public void setMessageForPassengers(String messageForPassengers) {
            this.messageForPassengers = messageForPassengers;
        }

        public ArrayList<StopsInBetween> getStopsInBetween() {
            return stopsInBetween;
        }

        public void setStopsInBetween(ArrayList<StopsInBetween> stopsInBetween) {
            this.stopsInBetween = stopsInBetween;
        }

        public class StopsInBetween {

            @SerializedName("latitude")
            @Expose
            private String latitude;
            @SerializedName("longitude")
            @Expose
            private String longitude;
            @SerializedName("address")
            @Expose
            private String address;

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

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

        }

    }


}