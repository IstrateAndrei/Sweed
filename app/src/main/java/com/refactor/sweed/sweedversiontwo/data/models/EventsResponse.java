package com.refactor.sweed.sweedversiontwo.data.models;

import com.google.android.gms.common.api.Result;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by andrei.istrate on 31.05.2017.
 */

public class EventsResponse<T>  {

    @SerializedName("data")
    @Expose
    private List<T> data = null;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int size()
    {
        return data.size();
    }
}
