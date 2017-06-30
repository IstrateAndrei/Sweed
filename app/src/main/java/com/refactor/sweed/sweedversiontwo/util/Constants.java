package com.refactor.sweed.sweedversiontwo.util;

import java.util.List;

import static java.util.Arrays.asList;

/**
 * Created by andrei.istrate on 31.05.2017.
 */

public class Constants {

    public static final String GOOGLE_MAPS_API_KEY = "AIzaSyDBLddRKxvs_rH9Z5I76mdRuto0xGk6fns";

    public static final String BASE_URL = "http://46.101.173.236:4582/";
    public static final String BEARER_STRING= "Bearer ";
    public static final List<String> FACEBOOK_LOGIN_PERMISSIONS = asList("public_profile", "email", "user_photos", "user_videos", "user_about_me");


    // CONSTANTS USED BY SHARED PREFERENCES
    public static final String FACEBOOK_LOGIN_AUTHENTICATION = "login_authentication";
    public static final String ACCESS_TOKEN = "login_authentication";
    public static final String SEEN_ONBOARDING_LOCATION = "user_seen_onboarding_location";
    public static final String SEEN_ONBOARDING_CAMERA = "user_seen_onboarding_camera";
    public static final String USER_ID = "user_id";
    public static final String REFRESH_TOKEN = "refresh_token";
    public static final String TOKEN_TYPE = "token_type";
    public static final String SELECTED_EVENT = "selected_event";
    public static final String BOTTOM_BAR_TAG = "bottom_bar";
    public static final String LOGIN_PRESSED_FROM_DIALOG = "login_dialog";
    public static final String PROFILE_RESPONSE_ITEM = "profile_response_item";
    public static final String WHEN_KEY_STRING = "when_fragment";
    public static final String WHAT_KEY_STRING = "what_fragment";


    public static final int REQUEST_LOCATION = 1645;
    public static final int REQUEST_STORAGE_CAMERA = 1649;

   //EVENT ITEM STRINGS
    public static final String HAPPENING_NOW_STRING = "Happening now!";
    public static final String UPCOMING_EVENTS_STRING = "Upcoming Events";

    //HAWK KEY STRINGS
    public static final String HAWK_CHECK_LOGIN_STRING = "hawk_login";
}
