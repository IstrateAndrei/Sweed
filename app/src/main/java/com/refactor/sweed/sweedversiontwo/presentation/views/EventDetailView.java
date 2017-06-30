package com.refactor.sweed.sweedversiontwo.presentation.views;

import com.refactor.sweed.sweedversiontwo.data.models.EventResponse;

/**
 * Created by andrei.istrate on 12.06.2017.
 */

public interface EventDetailView {
    void showEventDetails(EventResponse eventItem);
    void initMap();
    void joinEvent();
    void initToolbar();

}
