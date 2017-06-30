package com.refactor.sweed.sweedversiontwo.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by andrei.istrate on 31.05.2017.
 */

public class EventEntryFee implements Serializable{
    @SerializedName("currency")
    @Expose
    private String currency;

    @SerializedName("ammount")
    @Expose
    private long ammount;

    public String getCurrency() {
        return currency;
    }

    public long getAmmount() {
        return ammount;
    }

}
