package com.refactor.sweed.sweedversiontwo.presentation.presenters;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AlertDialogLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import com.makeramen.roundedimageview.RoundedImageView;
import com.orhanobut.hawk.Hawk;
import com.refactor.sweed.sweedversiontwo.R;
import com.refactor.sweed.sweedversiontwo.data.models.EventResponse;
import com.refactor.sweed.sweedversiontwo.data.models.EventsResponse;
import com.refactor.sweed.sweedversiontwo.data.models.UserProfile;
import com.refactor.sweed.sweedversiontwo.data.models.UserProfileResponse;
import com.refactor.sweed.sweedversiontwo.data.retrofit.APIRequests;
import com.refactor.sweed.sweedversiontwo.data.retrofit.SweedApi;
import com.refactor.sweed.sweedversiontwo.presentation.activities.MainEventsActivity;
import com.refactor.sweed.sweedversiontwo.presentation.fragments.BoardingCameraFragment;
import com.refactor.sweed.sweedversiontwo.presentation.views.MainEventsView;
import com.refactor.sweed.sweedversiontwo.util.Constants;
import com.refactor.sweed.sweedversiontwo.util.HelperClass;
import com.refactor.sweed.sweedversiontwo.util.SharedPref;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

import static com.refactor.sweed.sweedversiontwo.presentation.fragments.BoardingCameraFragment.EXTERNAL_STORAGE_CAMERA;

/**
 * Created by andrei.istrate on 31.05.2017.
 */

public class MainEventsPresenterImpl implements MainEventsPresenter{

    public static final String TAG = MainEventsPresenterImpl.class.getSimpleName();


    private MainEventsView mView;
    private Context mContext;
    private SharedPref mSharedPref;

    public MainEventsPresenterImpl(MainEventsView view)
    {
        this.mContext = (Context) view;
        this.mView = view;
        mSharedPref = SharedPref.getInstance();
    }

    @Override
    public void getEventsRequest() {
        HelperClass.hawkInit((Activity) mView);
        String Authorization = Constants.BEARER_STRING + Hawk.get(Constants.ACCESS_TOKEN);
        APIRequests getEvents = new SweedApi().createService(Authorization);
        Observable<Response<EventsResponse<EventResponse>>> getEventsObservable = getEvents.getEventsLiveAndUpcoming();
        getEventsObservable.observeOn(Schedulers.newThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<EventsResponse<EventResponse>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<EventsResponse<EventResponse>> eventsResponseResponse) {
                        Log.e(TAG, eventsResponseResponse.body().getData().get(0).toString());
                        EventsResponse<EventResponse> response = eventsResponseResponse.body();
                        mView.displayEvents(response);
                        mView.onStopRefresh();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void showNotLoggedDialog() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder((Context) mView);
                mBuilder.setTitle(R.string.not_logged_dialog_title_string)
                .setNegativeButton(R.string.alert_dialog_close_string, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).setPositiveButton(R.string.alert_dialog_login_string, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mView.closeActivity();
                    }
                }).setMessage(R.string.alert_dialog_message_string);
        AlertDialog dialog = mBuilder.create();

        dialog.show();
    }

    @Override
    public void handleCameraAccess() {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder((Context) mView);
        final AlertDialog cameraDialog = mBuilder.create();
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.video_camera_dialog_layout,null);

        cameraDialog.setView(v);
        cameraDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        RoundedImageView videoButton = (RoundedImageView) v.findViewById(R.id.video_button);
        RoundedImageView photoButton = (RoundedImageView) v.findViewById(R.id.photo_button);
        videoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cameraDialog.dismiss();
                mContext.startActivity(new Intent(MediaStore.ACTION_VIDEO_CAPTURE));

            }
        });

        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraDialog.dismiss();
                mContext.startActivity(new Intent(MediaStore.ACTION_IMAGE_CAPTURE));
            }
        });

    cameraDialog.show();

    }

    @Override
    public void handleProfileAccess() {

        String userId = (String) Hawk.get(Constants.USER_ID);
        String authorizationToken = Constants.BEARER_STRING + (String) Hawk.get(Constants.ACCESS_TOKEN);
        APIRequests getUserProfile = new SweedApi().createService();
        Observable<Response<UserProfileResponse<UserProfile>>> getProfileObs = getUserProfile.getUserProfile(authorizationToken,userId);
                getProfileObs.observeOn(Schedulers.newThread())
                             .subscribeOn(AndroidSchedulers.mainThread())
                             .subscribe(new Observer<Response<UserProfileResponse<UserProfile>>>() {
                                 @Override
                                 public void onSubscribe( Disposable d) {

                                 }

                                 @Override
                                 public void onNext( Response<UserProfileResponse<UserProfile>> userProfileResponseResponse) {
                                        if(userProfileResponseResponse.isSuccessful())
                                        {
                                            //TODO handle success profile access
                                            Log.e("PROFILE_REQ","WIN");
                                            mView.openProfileFragment(userProfileResponseResponse.body());

                                        }else{
                                            Log.e("PROFILE_REQ","UNAUTHORIZED");
                                        }
                                 }

                                 @Override
                                 public void onError(Throwable e) {
                                     Log.e("PROFILE_REQ","FAIL");
                                 }

                                 @Override
                                 public void onComplete() {

                                 }
                             });
    }


}
