package com.refactor.sweed.sweedversiontwo.presentation.views;

import com.refactor.sweed.sweedversiontwo.data.models.FacebookUser;

/**
 * Created by andrei.istrate on 31.05.2017.
 */

public interface SplashScreenView {

    void goToMain();
    void goToOnBoarding();
    void handleSuccessPostResponse(FacebookUser user);
}
