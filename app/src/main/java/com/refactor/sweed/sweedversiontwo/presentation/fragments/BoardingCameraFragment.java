package com.refactor.sweed.sweedversiontwo.presentation.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.refactor.sweed.sweedversiontwo.R;
import com.refactor.sweed.sweedversiontwo.util.Constants;
import com.refactor.sweed.sweedversiontwo.util.HelperClass;
import com.refactor.sweed.sweedversiontwo.util.SharedPref;
import com.refactor.sweed.sweedversiontwo.presentation.activities.BoardingActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by andrei.istrate on 08.06.2017.
 */

public class BoardingCameraFragment extends Fragment {

    public static final String TAG =BoardingCameraFragment.class.getSimpleName();

    public static final String[] EXTERNAL_STORAGE_CAMERA = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO};
    public static final int CAMERA_REQUEST_CODE = 1069;

    @BindView(R.id.camera_button_agree_permissions)
    Button agreeButton;
    @BindView(R.id.camera_button_deny_permission)
    Button denyButton;
    SharedPref mSharedPref;

    public static BoardingCameraFragment getInstance()
    {
        BoardingCameraFragment fragment = new BoardingCameraFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,  Bundle savedInstanceState) {
       View v = inflater.inflate(R.layout.boarding_camera_fragment,container,false);
        ButterKnife.bind(this,v);
        mSharedPref = new SharedPref();
        return v;
    }

    @OnClick({R.id.camera_button_agree_permissions,R.id.camera_button_deny_permission})
    public void onClicked(View v)
    {
        switch (v.getId())
        {
            case R.id.camera_button_agree_permissions:
                handleCameraPermisions();
                break;
            case R.id.camera_button_deny_permission:
                mSharedPref.saveStateToPreference(Constants.SEEN_ONBOARDING_CAMERA,false);
                getActivity().finish();
                break;

        }
    }

    public void handleCameraPermisions()
    {
        if(ContextCompat.checkSelfPermission(getActivity(), EXTERNAL_STORAGE_CAMERA[0]) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getActivity(),EXTERNAL_STORAGE_CAMERA[1]) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getActivity(),EXTERNAL_STORAGE_CAMERA[2]) != PackageManager.PERMISSION_GRANTED)
        {
            Log.e("permission","refused");
           requestPermissions(EXTERNAL_STORAGE_CAMERA, CAMERA_REQUEST_CODE );
        }else
        {
            mSharedPref.saveStateToPreference(Constants.SEEN_ONBOARDING_CAMERA,true);
            agreeButton.setText(BoardingLocationFragment.PERMISSION_GRANTED_STRING);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ((BoardingActivity) getActivity()).goToEventsActivity();
                }
            },2000);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,  String[] permissions,int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == CAMERA_REQUEST_CODE && HelperClass.hasAllPermissionsGranted(grantResults))
        {
            mSharedPref.saveStateToPreference(Constants.SEEN_ONBOARDING_CAMERA,true);
            agreeButton.setText(BoardingLocationFragment.PERMISSION_GRANTED_STRING);
            ((BoardingActivity) getActivity()).goToEventsActivity();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

    }

}
