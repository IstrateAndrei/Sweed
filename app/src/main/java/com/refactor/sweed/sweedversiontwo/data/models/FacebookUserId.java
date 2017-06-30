package com.refactor.sweed.sweedversiontwo.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



public class FacebookUserId  {

	@SerializedName("userId")
	@Expose
	private String userId;

	public String getUserId(){
		return userId;
	}

}