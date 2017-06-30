package com.refactor.sweed.sweedversiontwo.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


import java.io.Serializable;
import java.util.List;

public class UserProfile implements Serializable {

	@SerializedName("photoURL")
	@Expose
	private String photoURL;

	@SerializedName("createdAt")
	@Expose
	private String createdAt;

	@SerializedName("eventsIds")
	@Expose
	private List<String> eventsIds;

	@SerializedName("facebookId")
	@Expose
	private String facebookId;

	@SerializedName("authToken")
	@Expose
	private Object authToken;

	@SerializedName("name")
	@Expose
	private String name;

	@SerializedName("about")
	@Expose
	private String about;

	@SerializedName("deviceTokens")
	@Expose
	private List<Object> deviceTokens;

	@SerializedName("id")
	@Expose
	private String id;

	@SerializedName("isActive")
	@Expose
	private boolean isActive;

	@SerializedName("updatedAt")
	@Expose
	private String updatedAt;


	public String getPhotoURL(){
		return photoURL;
	}

	public List<String> getEventsIds(){
		return eventsIds;
	}

	public String getName(){
		return name;
	}

	public String getAbout(){
		return about;
	}

}