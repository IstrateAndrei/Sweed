package com.refactor.sweed.sweedversiontwo.data.models;

/**
 * Created by ovidiu.vicsocsan on 10/02/2017.
 */

public class TokenPostModel {

    private String deviceType;
    private String deviceId;

    public TokenPostModel(String deviceType, String firebaseToken) {
        this.deviceType = deviceType;
        this.deviceId = firebaseToken;
    }
}
