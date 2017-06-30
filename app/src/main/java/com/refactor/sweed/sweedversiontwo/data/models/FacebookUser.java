package com.refactor.sweed.sweedversiontwo.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



public class FacebookUser {

    @SerializedName("access_token")
    @Expose
    private String accessToken;

    @SerializedName("refresh_token")
    @Expose
    private String refreshToken;

    @SerializedName("token_type")
    @Expose
    private String tokenType;

    @SerializedName("expires_in")
    @Expose
    private String expiresIn;

    @SerializedName("user")
    @Expose
    private FacebookUserId user;

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public FacebookUserId getUser() {
        return user;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}