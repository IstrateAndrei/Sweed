package com.refactor.sweed.sweedversiontwo.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by andrei.istrate on 31.05.2017.
 */

public class EventSchedule implements Serializable {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("icon")
    @Expose
    private String icon;

    @SerializedName("startsAt")
    @Expose
    private String startsAt;

    @SerializedName("endsAt")
    @Expose
    private String endsAt;

}
