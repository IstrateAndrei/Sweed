package com.refactor.sweed.sweedversiontwo.util;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.refactor.sweed.sweedversiontwo.SweedApplication;

/**
 * Created by andrei.istrate on 07.06.2017.
 */

public class SharedPref {

    private static SharedPref mInstance = null;

    public static SharedPref getInstance()
    {
        if(mInstance != null)
        {
            return mInstance;
        }else
        {
            mInstance =  new SharedPref();
            return mInstance;
        }
    }

    public SharedPref()
    {
        mInstance = this;
    }

    public SharedPreferences.Editor getPreferenceEditor()
    {
       SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(SweedApplication.getInstance().getApplicationContext());
        return sharedPreferences.edit();
    }
    public void saveStateToPreference(String key, boolean value)
    {
        SharedPreferences.Editor edit = getPreferenceEditor();
        edit.putBoolean(key, value);
        edit.commit();
    }

    public boolean getAuthenticationState()
    {
        return PreferenceManager.getDefaultSharedPreferences(SweedApplication.getInstance()).getBoolean(Constants.FACEBOOK_LOGIN_AUTHENTICATION, false);
    }

    public boolean checkState(String key)
    {
        return PreferenceManager.getDefaultSharedPreferences(SweedApplication.getInstance()).getBoolean(key,false);
    }


}
