package com.refactor.sweed.sweedversiontwo.util;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.orhanobut.hawk.Hawk;
import com.refactor.sweed.sweedversiontwo.R;
import com.refactor.sweed.sweedversiontwo.presentation.presenters.SplashScreenPresenterImpl;
import com.refactor.sweed.sweedversiontwo.presentation.views.SplashScreenView;

/**
 * Created by andrei.istrate on 07.06.2017.
 */

//public class FacebookLoginCallBack implements FacebookCallback<LoginResult> {
//
//    private SharedPref sharedPref = SharedPref.getInstance();
//    private SplashScreenView mView;
//    private SplashScreenPresenterImpl mPresenter;
//    Context mContext;
//
//    public FacebookLoginCallBack(SplashScreenView view, SplashScreenPresenterImpl mPresenter)
//    {
//        this.mView = view;
//        this.mPresenter = mPresenter;
//        mContext = (Context) view;
//
//    }
//
//    @Override
//    public void onSuccess(LoginResult loginResult) {
//        Log.e("SUCCESS","WIN!");
//        if(isLoggedIn(loginResult))
//        {
//            HelperClass.hawkInit((Activity) mContext);
//            Log.e("SEE RESULT ",loginResult.getAccessToken().toString());
//            Hawk.put(Constants.ACCESS_TOKEN, loginResult.getAccessToken().getToken());
//            mPresenter.postFbToken(loginResult.getAccessToken().getToken(),mContext.getResources().getString(R.string.facebook_token),mContext.getResources().getString(R.string.openid_offline_access));
//
//        }
//    }
//
//    @Override
//    public void onCancel() {
//        Log.e("CANCEL","CANCEL");
//    }
//
//    @Override
//    public void onError(FacebookException error) {
//        Log.e("ERROR",error.getMessage());
//    }
//
//    public boolean isLoggedIn(LoginResult loginResult)
//    {return loginResult != null && loginResult.getAccessToken() != null;}
//
//
//}
