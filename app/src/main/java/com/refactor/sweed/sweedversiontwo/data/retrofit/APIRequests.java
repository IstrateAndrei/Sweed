package com.refactor.sweed.sweedversiontwo.data.retrofit;

import com.refactor.sweed.sweedversiontwo.data.models.EventResponse;
import com.refactor.sweed.sweedversiontwo.data.models.EventsResponse;
import com.refactor.sweed.sweedversiontwo.data.models.FacebookUser;
import com.refactor.sweed.sweedversiontwo.data.models.MediaAndCommentsItem;
import com.refactor.sweed.sweedversiontwo.data.models.UserProfile;
import com.refactor.sweed.sweedversiontwo.data.models.UserProfileResponse;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by andrei.istrate on 31.05.2017.
 */

public interface APIRequests {

    /*
   * Api for getting events list ordered by ongoing & upcoming and filtered by:
   * @Query optional param {longitude} - the longitude of the event
   * @Query optional param {latitude} - the latitude of the event
   * @Query optional param {cat} - the category of the event
   * @Query optional param {startDate} - the start date of the event
   */
    @GET("api/Events/live_and_upcoming")
    Observable<Response<EventsResponse<EventResponse>>> getEventsLiveAndUpcoming();


    /*
   * Api for sending facebook login token to backend
   * @Header param {Authorization} = "Bearer bXZjOm15dXNlcnNlY3JldA=="
   * @Field param {token} - facebook login token
   * @Field param {grant_type} = "facebook_token"
   * @Field param {scope} = "openid offline_access"
   */
    @FormUrlEncoded
    @POST("http://46.101.173.236:51057/connect/token")
    Observable<Response<FacebookUser>> postFacebookAccessToken(@Field("token") String token,
                                                               @Field("grant_type") String grantType, @Field("scope") String openidOfflineAccess);


    /*
   * Api for getting all media and messages for an event
   * @Path param (id) - the id of the opened event
   */
    @GET("api/Media/Event/media_and_messages/{id}")
    Observable<Response<EventsResponse<MediaAndCommentsItem>>> getAllMediaForEvent(
            @Header("Authorization") String accessToken,
            @Path("id") String eventId
            );


    /*
   * Api for getting user facebook profile
   * @Header param {Authorization} - access_token from /connect/token response
   * @Header param (Content-Type) = "application/x-www-form-urlencoded"
   * @Path param (id) - userId
   */
    @GET("api/Users/{id}")
    Observable<Response<UserProfileResponse<UserProfile>>> getUserProfile(
            @Header("Authorization") String authorizationToken,@Path("id") String userId
                                                                          );
}
