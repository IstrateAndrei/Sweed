package com.refactor.sweed.sweedversiontwo;

import android.app.Application;
import android.content.Context;


/**
 * Created by andrei.istrate on 07.06.2017.
 */

public class SweedApplication extends Application {

    private static SweedApplication mInstance;
    public static SweedApplication getInstance()
    {
        if(mInstance == null)
        {
            mInstance = new SweedApplication();
        }
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }
}
