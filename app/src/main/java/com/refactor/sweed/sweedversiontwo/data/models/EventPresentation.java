package com.refactor.sweed.sweedversiontwo.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by andrei.istrate on 31.05.2017.
 */

public class EventPresentation implements Serializable {

    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("color")
    @Expose
    private long color;

    public String getImage() {
        return image;
    }

}
