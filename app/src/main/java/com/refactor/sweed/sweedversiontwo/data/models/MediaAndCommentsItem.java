package com.refactor.sweed.sweedversiontwo.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



public class MediaAndCommentsItem {

	@SerializedName("userInfo")
	@Expose
	private UserInfo userInfo;

	@SerializedName("createdAt")
	@Expose
	private String createdAt;

	@SerializedName("timePassed")
	@Expose
	private String timePassed;

	@SerializedName("mediaBody")
	@Expose
	private String mediaBody;

	@SerializedName("type")
	@Expose
	private String type;

	@SerializedName("id")
	@Expose
	private String id;

	@SerializedName("videoPosterUrl")
	@Expose
	private String videoPosterUrl;

	public String getId() {
		return id;
	}

	public UserInfo getUserInfo(){
		return userInfo;
	}

	public String getTimePassed(){
		return timePassed;
	}

	public String getMediaBody(){
		return mediaBody;
	}

	public String getType(){
		return type;
	}

	public String getPosterUrl() {
		return videoPosterUrl;
	}

}