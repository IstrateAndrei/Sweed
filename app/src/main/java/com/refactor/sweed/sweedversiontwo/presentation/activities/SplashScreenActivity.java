package com.refactor.sweed.sweedversiontwo.presentation.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.innovattic.font.FontTextView;
import com.orhanobut.hawk.Hawk;
import com.refactor.sweed.sweedversiontwo.R;
import com.refactor.sweed.sweedversiontwo.data.models.FacebookUser;
import com.refactor.sweed.sweedversiontwo.util.Constants;
import com.refactor.sweed.sweedversiontwo.util.HelperClass;
import com.refactor.sweed.sweedversiontwo.util.SharedPref;
import com.refactor.sweed.sweedversiontwo.presentation.presenters.SplashScreenPresenterImpl;
import com.refactor.sweed.sweedversiontwo.presentation.views.SplashScreenView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SplashScreenActivity extends AppCompatActivity implements SplashScreenView {

    @BindView(R.id.facebook_login_button)
    Button facebookLoginButton;
    @BindView(R.id.skip_login_button)
    Button skipLoginButton;
    @BindView(R.id.privacy_policy_ftv)
    FontTextView privacyPolicyFTV;
    @BindView(R.id.terms_ftv)
    FontTextView termsFTV;

    //Presenter declaration
    SplashScreenPresenterImpl mSplashPresenter;
    SharedPref mSharedPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_activity_layout);
        ButterKnife.bind(this);
        HelperClass.hawkInit(this);
        mSplashPresenter = new SplashScreenPresenterImpl(this);

        mSharedPreference = new SharedPref();
        mSharedPreference.saveStateToPreference(Constants.LOGIN_PRESSED_FROM_DIALOG, false);
        mSplashPresenter.initFacebookCallbackManager();
        mSplashPresenter.initFBSignIn();
//        mSplashPresenter.onLogin();


    }

    @OnClick({R.id.facebook_login_button,R.id.skip_login_button,R.id.terms_ftv,R.id.privacy_policy_ftv})
    public void onItemClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.facebook_login_button:
                mSplashPresenter.setFacebookLogin();
                break;
            case R.id.skip_login_button:
                mSplashPresenter.setSkipLogin();
                break;
            case R.id.terms_ftv:
                startActivity(new Intent(SplashScreenActivity.this, TermsAndConditionsActivity.class));
                break;
            case R.id.privacy_policy_ftv:
                //TODO impl privacy policy activity;
                break;
        }
    }

    @Override
    public void goToMain() {
        Intent intent = new Intent(SplashScreenActivity.this, MainEventsActivity.class);
        startActivity(intent);
    }

    @Override
    public void goToOnBoarding() {
        Intent intent = new Intent(SplashScreenActivity.this, BoardingActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mSplashPresenter.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSplashPresenter.onLogin();
    }

    @Override
    public void handleSuccessPostResponse(FacebookUser user)
    {
        if(user.getAccessToken() != null)
        {
            mSharedPreference.saveStateToPreference(Constants.FACEBOOK_LOGIN_AUTHENTICATION, true);

            Hawk.put(Constants.ACCESS_TOKEN, user.getAccessToken());
            Hawk.put(Constants.USER_ID, user.getUser().getUserId());
            Hawk.put(Constants.REFRESH_TOKEN, user.getRefreshToken());

            if(mSharedPreference.checkState(Constants.SEEN_ONBOARDING_CAMERA) && mSharedPreference.checkState(Constants.SEEN_ONBOARDING_LOCATION))
            {
                goToMain();
            }else{goToOnBoarding();}
        }
    }
}
