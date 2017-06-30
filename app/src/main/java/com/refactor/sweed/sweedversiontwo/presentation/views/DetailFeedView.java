package com.refactor.sweed.sweedversiontwo.presentation.views;

import com.refactor.sweed.sweedversiontwo.data.models.EventsResponse;
import com.refactor.sweed.sweedversiontwo.data.models.MediaAndCommentsItem;

import java.util.ArrayList;

import retrofit2.Response;

/**
 * Created by andrei.istrate on 27.06.2017.
 */

public interface DetailFeedView {

    void displayEventMedia(final EventsResponse<MediaAndCommentsItem> responseData);
}
