package com.refactor.sweed.sweedversiontwo.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by andrei.istrate on 31.05.2017.
 */

public class EventResponse implements Serializable{

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("distanceTo")
    @Expose
    public double distanceTo;
    @SerializedName("category")
    @Expose
    public String category;
    @SerializedName("state")
    @Expose
    public String state;
    @SerializedName("information")
    @Expose
    public List<EventInformation> information = null;
    @SerializedName("entryFee")
    @Expose
    public EventEntryFee entryFee;
    @SerializedName("presentation")
    @Expose
    public EventPresentation presentation;
    @SerializedName("location")
    @Expose
    public EventLocation location;
    @SerializedName("menu")
    @Expose
    public String menu;
    @SerializedName("startsDate")
    @Expose
    public String startsDate;
    @SerializedName("endsDate")
    @Expose
    public String endsDate;
    @SerializedName("startsTime")
    @Expose
    public String startsTime;
    @SerializedName("endsTime")
    @Expose
    public String endsTime;
    @SerializedName("schedule")
    @Expose
    public List<EventSchedule> schedule = null;
    @SerializedName("isBookable")
    @Expose
    public boolean isBookable;
    @SerializedName("isPublic")
    @Expose
    public boolean isPublic;
    @SerializedName("isRecommended")
    @Expose
    public boolean isRecommended;
    @SerializedName("reviewables")
    @Expose
    public List<String> reviewables = null;
    @SerializedName("createdAt")
    @Expose
    public String createdAt;
    @SerializedName("updatedAt")
    @Expose
    public String updatedAt;
    @SerializedName("numberAttendees")
    @Expose
    public int numberAttendees;
    @SerializedName("attending")
    @Expose
    public boolean attending;
    @SerializedName("postsCount")
    @Expose
    public int postsCount;


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getDistanceTo() {
        DecimalFormat df = new DecimalFormat("#.##");
        return Double.valueOf(df.format(distanceTo));
    }

    public String getCategory() {
        return category;
    }

    public String getState() {
        return state;
    }

    public EventEntryFee getEntryFee() {
        return entryFee;
    }

    public EventPresentation getPresentation() {
        return presentation;
    }

    public EventLocation getLocation() {
        return location;
    }

    public String getStartsDate() {
        return startsDate;
    }

    public String getEndsDate() {
        return endsDate;
    }

    public int getNumberAttendees() {
        return numberAttendees;
    }

    public boolean isAttending() {
        return attending;
    }

    public void setAttending(boolean attending) {
        this.attending = attending;
    }

    public int getPostsCount() {
        return postsCount;
    }


}
