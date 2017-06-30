package com.refactor.sweed.sweedversiontwo.presentation.presenters;

import com.refactor.sweed.sweedversiontwo.data.models.TokenPostModel;
import com.refactor.sweed.sweedversiontwo.presentation.views.SplashScreenView;

/**
 * Created by andrei.istrate on 31.05.2017.
 */

public interface SplashScreenPresenter {

    void showLoginButtons();
    void setFacebookLogin();
    void setSkipLogin();
    void initFBSignIn();
    void postFbToken(String loginToken, String facebookToken, String openIdOfflineAccess);
    void postFireBaseToken(String token,String userId, TokenPostModel tokenModel);

}
