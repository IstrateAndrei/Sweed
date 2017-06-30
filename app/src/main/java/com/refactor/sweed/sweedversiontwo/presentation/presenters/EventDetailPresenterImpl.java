package com.refactor.sweed.sweedversiontwo.presentation.presenters;

import android.util.Log;

import com.refactor.sweed.sweedversiontwo.data.models.EventResponse;
import com.refactor.sweed.sweedversiontwo.data.models.EventsResponse;
import com.refactor.sweed.sweedversiontwo.data.models.MediaAndCommentsItem;
import com.refactor.sweed.sweedversiontwo.data.retrofit.APIRequests;
import com.refactor.sweed.sweedversiontwo.data.retrofit.SweedApi;
import com.refactor.sweed.sweedversiontwo.presentation.views.DetailFeedView;
import com.refactor.sweed.sweedversiontwo.presentation.views.EventDetailView;
import com.refactor.sweed.sweedversiontwo.util.Constants;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by andrei.istrate on 12.06.2017.
 */

public class EventDetailPresenterImpl implements EventDetailPresenter {

    private EventDetailView mView;
    private DetailFeedView feedView;
    public static final String TAG = EventDetailPresenterImpl.class.getSimpleName();

    public EventDetailPresenterImpl(EventDetailView view)
    {
        this.mView = view;
    }

    public EventDetailPresenterImpl(DetailFeedView view)
    {
        this.feedView = view;
    }

    @Override
    public void initView(EventResponse eventItem) {
        mView.showEventDetails(eventItem);
        mView.initToolbar();
    }

    @Override
    public void getEventPostComments(String accessToken, String eventID) {

        String headerParam = Constants.BEARER_STRING + accessToken;
        APIRequests getEventComments = new SweedApi().createService();
        Observable<Response<EventsResponse<MediaAndCommentsItem>>> getEventMediaObs = getEventComments.getAllMediaForEvent(headerParam,eventID);
                getEventMediaObs.observeOn(Schedulers.newThread())
                                .subscribeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<Response<EventsResponse<MediaAndCommentsItem>>>() {
                                    @Override
                                    public void onSubscribe( Disposable d) {

                                    }

                                    @Override
                                    public void onNext( Response<EventsResponse<MediaAndCommentsItem>> eventsResponseResponse) {
                                        if(eventsResponseResponse.isSuccessful())
                                        {
                                            feedView.displayEventMedia(eventsResponseResponse.body());
                                        }else
                                        {
                                            Log.e(TAG,"NOT REALY WIN");
                                        }
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        Log.e(TAG,"ERROR");
                                    }

                                    @Override
                                    public void onComplete() {

                                    }
                                });
    }


}
