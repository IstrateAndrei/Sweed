package com.refactor.sweed.sweedversiontwo.presentation.presenters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.orhanobut.hawk.Hawk;
import com.refactor.sweed.sweedversiontwo.R;
import com.refactor.sweed.sweedversiontwo.data.models.FacebookUser;
import com.refactor.sweed.sweedversiontwo.data.models.TokenPostModel;
import com.refactor.sweed.sweedversiontwo.data.retrofit.APIRequests;
import com.refactor.sweed.sweedversiontwo.data.retrofit.SweedApi;
import com.refactor.sweed.sweedversiontwo.util.Constants;
import com.refactor.sweed.sweedversiontwo.util.HelperClass;
import com.refactor.sweed.sweedversiontwo.util.SharedPref;
import com.refactor.sweed.sweedversiontwo.presentation.activities.MainEventsActivity;
import com.refactor.sweed.sweedversiontwo.presentation.views.SplashScreenView;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by andrei.istrate on 31.05.2017.
 */

public class SplashScreenPresenterImpl implements SplashScreenPresenter{

    private SplashScreenView mView;
    CallbackManager mCallBackManager;


    public SplashScreenPresenterImpl(SplashScreenView mView)
    {
        this.mView = mView;
    }

    public SplashScreenPresenterImpl(){}
    @Override
    public void showLoginButtons() {

    }

    public void initFacebookCallbackManager()
    {
        mCallBackManager = CallbackManager.Factory.create();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (FacebookSdk.isFacebookRequestCode(requestCode)) {
            mCallBackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void setFacebookLogin() {
        FacebookCallback<LoginResult> callback = new FacebookLoginCallBack(mView);
        LoginManager.getInstance().logInWithReadPermissions((Activity) mView, Constants.FACEBOOK_LOGIN_PERMISSIONS);
        LoginManager.getInstance().setLoginBehavior(LoginBehavior.NATIVE_WITH_FALLBACK);
        LoginManager.getInstance().registerCallback(mCallBackManager, callback);
    }

    @Override
    public void setSkipLogin() {
        Hawk.put(Constants.HAWK_CHECK_LOGIN_STRING,false);
        Intent intent = new Intent((Context) mView, MainEventsActivity.class);
        ((Context) mView).startActivity(intent);
    }

    @Override
    public void initFBSignIn() {
        LoginManager.getInstance().logOut();
    }

    public void login()
    {
        SharedPref sharedPref = SharedPref.getInstance();
        boolean hasToken = Hawk.contains(Constants.ACCESS_TOKEN);
        String tokenType = Hawk.get(Constants.TOKEN_TYPE);
        String userID = Hawk.get(Constants.USER_ID);
//        String accessToken = (String) Hawk.get(Constants.ACCESS_TOKEN);
        if(Hawk.contains(Constants.HAWK_CHECK_LOGIN_STRING))
        {
            if(Hawk.contains(Constants.ACCESS_TOKEN) && Hawk.contains(Constants.USER_ID) && Hawk.contains(Constants.TOKEN_TYPE))
            {
                if(sharedPref.checkState(Constants.SEEN_ONBOARDING_CAMERA) && sharedPref.checkState(Constants.SEEN_ONBOARDING_LOCATION))
                {
                    mView.goToMain();
                }else
                {
                    mView.goToOnBoarding();
                }
            }else
            {
                setFacebookLogin();
            }
            }

    }

    public void postFbToken(String loginToken, String facebookToken, String openIdOfflineAccess)
    {
//        APIRequests postFacebookToken = new SweedApi().createService();
        APIRequests postFacebookToken = new SweedApi().createAuthService(APIRequests.class, "mvc", "myusersecret");
        Observable<Response<FacebookUser>> postTokenObservable = postFacebookToken.postFacebookAccessToken(loginToken,facebookToken,openIdOfflineAccess);
        postTokenObservable.observeOn(Schedulers.newThread())
                            .subscribeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<Response<FacebookUser>>() {
                                @Override
                                public void onSubscribe(@NonNull Disposable d) {

                                }

                                @Override
                                public void onNext(@NonNull Response<FacebookUser> facebookUserResponse) {
                                    Log.e("WIN!","WIN DE WIN");
                                    if(!Hawk.isBuilt())
                                    {
                                        Hawk.init((Context) mView).build();

                                    }
                                    if(facebookUserResponse.isSuccessful())
                                    {
                                        Hawk.put(Constants.HAWK_CHECK_LOGIN_STRING, true);
                                        Hawk.put(Constants.REFRESH_TOKEN, facebookUserResponse.body().getRefreshToken());
                                        Hawk.put(Constants.ACCESS_TOKEN, facebookUserResponse.body().getAccessToken());
                                        Hawk.put(Constants.TOKEN_TYPE, facebookUserResponse.body().getTokenType());
                                        Hawk.put(Constants.USER_ID, facebookUserResponse.body().getUser().getUserId());
                                        onLogin();
                                    }
                                    //TODO handle expire time

                                }

                                @Override
                                public void onError(@NonNull Throwable e) {
                                    Log.e("FAIL!","FAIL DE FAIL");

                                }

                                @Override
                                public void onComplete() {

                                }
                            });
    }

    @Override
    public void postFireBaseToken(String token, String userId, TokenPostModel tokenModel) {

    }

    public void onLogin()
    {
       Handler handler = new Handler(Looper.getMainLooper());

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                login();
            }
        }, 2000);
    }


    public class FacebookLoginCallBack implements FacebookCallback<LoginResult> {

        private Context mContext;
        public FacebookLoginCallBack(SplashScreenView view)
        {
            this.mContext = (Context) view;
        }


        @Override
        public void onSuccess(LoginResult loginResult) {
            Log.e("SUCCESS","WIN!");
            if(isLoggedIn(loginResult))
            {
                HelperClass.hawkInit((Activity) mView);
                Log.e("SEE RESULT ",loginResult.getAccessToken().toString());
                Hawk.put(Constants.ACCESS_TOKEN, loginResult.getAccessToken().getToken());
                postFbToken(loginResult.getAccessToken().getToken(),mContext.getResources().getString(R.string.facebook_token),mContext.getResources().getString(R.string.openid_offline_access));

            }
        }

        @Override
        public void onCancel() {
            Log.e("CANCEL","CANCEL");
            Hawk.deleteAll();
            initFBSignIn();
        }

        @Override
        public void onError(FacebookException error) {
            Log.e("ERROR",error.getMessage());
        }

        public boolean isLoggedIn(LoginResult loginResult)
        {return loginResult != null && loginResult.getAccessToken() != null;}
    }


}
