package com.refactor.sweed.sweedversiontwo.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserInfo {

	@SerializedName("photoURL")
	@Expose
	private String photoURL;

	@SerializedName("name")
	@Expose
	private String name;

	@SerializedName("userId")
	@Expose
	private String userId;

	public String getPhotoURL(){
		return photoURL;
	}

	public String getName(){
		return name;
	}

	public String getUserId() {
		return userId;
	}

}