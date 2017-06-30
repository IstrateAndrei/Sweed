package com.refactor.sweed.sweedversiontwo.presentation.views;

import com.refactor.sweed.sweedversiontwo.data.models.EventResponse;
import com.refactor.sweed.sweedversiontwo.data.models.EventsResponse;
import com.refactor.sweed.sweedversiontwo.data.models.UserProfile;
import com.refactor.sweed.sweedversiontwo.data.models.UserProfileResponse;

/**
 * Created by andrei.istrate on 31.05.2017.
 */

public interface MainEventsView {

    void displayEvents(EventsResponse<EventResponse> eventsList);
    void onStopRefresh();
    void closeActivity();
    void openProfileFragment(UserProfileResponse<UserProfile> profileResponse);
    void openFilterLayoutBar();
    void closeFilterlayoutBar();
}
