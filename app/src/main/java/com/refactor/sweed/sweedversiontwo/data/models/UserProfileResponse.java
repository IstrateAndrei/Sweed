package com.refactor.sweed.sweedversiontwo.data.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class UserProfileResponse<T> implements Serializable{

    @SerializedName("data")
    @Expose
    private T data = null;

    public T getData() {
        return data;
    }

}
