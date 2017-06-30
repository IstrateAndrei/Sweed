package com.refactor.sweed.sweedversiontwo.presentation.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v4.view.ViewPager;
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

public class BoardingLocationFragment extends Fragment {

    public static final String TAG = BoardingLocationFragment.class.getSimpleName();

    public static final String[] LOCATION_PERMISSIONS = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};


    public static final int PERMISSION_REQUEST_CODE = 1387;
    public static final String PERMISSION_GRANTED_STRING = "Permission Granted";

    @BindView(R.id.button_agree_permissions)
    Button agreePermisionButton;
    @BindView(R.id.button_deny_permission)
    Button deniPermisionButton;

    ViewPager mPager;
    SharedPref mSharedPref;
    private PermissionChecker mChecker;

    public static BoardingLocationFragment getInstance() {
        BoardingLocationFragment fragment = new BoardingLocationFragment();
        return fragment;
    }

    public BoardingLocationFragment testSomething(ViewPager pager) {
        this.mPager = pager;
        BoardingLocationFragment fragment = new BoardingLocationFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.boarding_location_fragment, container, false);
        ButterKnife.bind(this, v);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSharedPref = new SharedPref();

    }

    @OnClick({R.id.button_agree_permissions, R.id.button_deny_permission})
    public void onClicked(View v) {
        switch (v.getId()) {
            case R.id.button_agree_permissions:
                handleLocationPermision();

                break;
            case R.id.button_deny_permission:
                ((BoardingActivity) getActivity()).changePage(1);
                mSharedPref.saveStateToPreference(Constants.SEEN_ONBOARDING_LOCATION,false);
                break;
        }
    }

    public void handleLocationPermision() {
        if (ContextCompat.checkSelfPermission(getActivity(), LOCATION_PERMISSIONS[0]) == PackageManager.PERMISSION_DENIED
                || ContextCompat.checkSelfPermission(getActivity(), LOCATION_PERMISSIONS[1]) == PackageManager.PERMISSION_DENIED) {
            Log.e("permission", "refused");
            requestPermissions(LOCATION_PERMISSIONS, PERMISSION_REQUEST_CODE);
        } else {
            agreePermisionButton.setText(PERMISSION_GRANTED_STRING);
            mSharedPref.saveStateToPreference(Constants.SEEN_ONBOARDING_LOCATION,true);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

            if (requestCode == PERMISSION_REQUEST_CODE && HelperClass.hasAllPermissionsGranted(grantResults)) {
                agreePermisionButton.setText(PERMISSION_GRANTED_STRING);
                ((BoardingActivity) getActivity()).changePage(1);
                mSharedPref.saveStateToPreference(Constants.SEEN_ONBOARDING_LOCATION,true);
            }
    }



}
