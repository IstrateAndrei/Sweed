package com.refactor.sweed.sweedversiontwo.presentation.presenters;

import android.support.v7.widget.Toolbar;

import com.refactor.sweed.sweedversiontwo.data.models.EventResponse;

/**
 * Created by andrei.istrate on 12.06.2017.
 */

public interface EventDetailPresenter {
    void initView(EventResponse eventItem);
    void getEventPostComments(String accessToken,String eventID);
}
