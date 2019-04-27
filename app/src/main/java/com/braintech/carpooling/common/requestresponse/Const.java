package com.braintech.carpooling.common.requestresponse;

/**
 * Created by Braintech on 9/12/2016.
 */
public class Const {

   // public static String BASE_URL = " http://braintechnosys.net/Bulldoksapp/";

    public static String BASE_URL = "http://onlineprojectprogress.com/carpooling/";
    // public static String BASE_URL = "http://www.vibesync.com/apisnew/";

    public static String APP_KEY = "APP-KEY";
    public static String APP_KEY_VALUE = "2e648bf9594adb50844cad68912345";

    public static String KEY_NAME = "name";
    public static String KEY_ID = "id";

    //<------------------Params for login--------------------->

    public static String PARAM_NAME = "name";
    public static String PARAM_EMAIL = "email";
    public final static String PARAM_USER_ID = "user_id";
    public static String PARAM_PHONE_NUMBER = "phone";
    public static String PARAM_DEVICE_TYPE = "device_type";
    public static String PARAM_DEVICE_TOKEN = "device_token";
    public static String PARAM_DEVICE_ID = "device_id";

    //<------------------Params for offer a ride--------------------->

    public static String PARAM_START_LOCATION = "start_location";
    public static String PARAM_START_LONGITUDE = "start_longitude";
    public static String PARAM_START_LATITUDE = "start_latitude";
    public static String PARAM_DESTINATION_LOCATION = "destination_location";
    public static String PARAM_DESTINATION_LATITUDE = "destination_latitude";
    public static String PARAM_DESTINATION_LONGITUDE = "destination_longitude";
    public static String PARAM_JOURNEY_DATE = "journey_date";
    public static String PARAM_JOURNEY_TIME = "journey_time";
    public static String PARAM_NUMBER_OF_PASSENGER = "no_of_passengers";
    public static String PARAM_IS_RETURN_JOURNEY_TYPE = "is_return_journey_offered";
    public static String PARAM_MESSAGE_FOR_PASSENGER = "message_for_passengers";
    public static String PARAM_STOPS_IN_BETWEEN = "stops_in_between";




    //<------------------Params for Find a ride--------------------->

    public static String PARAM_SOURCE_LONGITUDE = "source_longitude";
    public static String PARAM_SOURCE_LATITUDE = "source_latitude";
    public static String PARAM_DATE = "date";
    public static String PARAM_TIME = "time";
    public static String PARAM_NUMBER_OF_SEATS = "no_of_seats";



    public static String PARAM_EMAIL_FORGOT_PASSWORD = "email";

    //<------------------Params for book a ride--------------------->
    public static String PARAM_RIDE_ID = "ride_id";

    //<------------------Params for confirmation ride--------------------->

    public static String PARAM_RIDE_SCHEDULED_ID = "ride_scheduled_id";





    public static String PARAM_DATE_OF_BIRTH_MY_PROFILE = "dob";
    public static String PARAM_ADDRESS_MY_PROFILE = "address";


    //<------------------Params to create new document--------------------->

    public final static String PARAM_CATEGORY_ID = "category_id";
    public final static String PARAM_SUBCATEGORY = "subcategory_id";
    public final static String PARAM_PRICE = "price";
    public final static String PARAM_DESCRIPTION = "description";
    public final static String PARAM_DOC_TYPE = "public";
    public final static String PARAM_DOC_ID = "id";
    public final static String PARAM_IMAGE = "image";
    public final static String PARAM_THUMB = "thumb";

    public final static int VALUE_EDIT = 1;
    public final static int VALUE_EDIT_DETAIL = 2;

    public final static int VALUE_PUBLIC_OBJECT = 1;


    //<------------------Params to create new document--------------------->

    public final static String PARAM_OLD_PASSWORD = "old_password";
    public final static String PARAM_CONFIRM_PASSWORD = "confirm_password";
}

