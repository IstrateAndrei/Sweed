package com.refactor.sweed.sweedversiontwo.presentation.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.orhanobut.hawk.Hawk;
import com.refactor.sweed.sweedversiontwo.R;
import com.refactor.sweed.sweedversiontwo.data.models.EventResponse;
import com.refactor.sweed.sweedversiontwo.data.models.EventsResponse;
import com.refactor.sweed.sweedversiontwo.data.models.UserProfile;
import com.refactor.sweed.sweedversiontwo.data.models.UserProfileResponse;
import com.refactor.sweed.sweedversiontwo.presentation.fragments.EventDetailFragment;
import com.refactor.sweed.sweedversiontwo.presentation.fragments.ProfileFragment;
import com.refactor.sweed.sweedversiontwo.presentation.fragments.WhenWhatFragment;
import com.refactor.sweed.sweedversiontwo.util.Constants;
import com.refactor.sweed.sweedversiontwo.util.FragmentFlags;
import com.refactor.sweed.sweedversiontwo.util.HelperClass;
import com.refactor.sweed.sweedversiontwo.util.SharedPref;
import com.refactor.sweed.sweedversiontwo.presentation.adapters.DisplayEventsAdapter;
import com.refactor.sweed.sweedversiontwo.presentation.presenters.MainEventsPresenterImpl;
import com.refactor.sweed.sweedversiontwo.presentation.views.MainEventsView;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by andrei.istrate on 31.05.2017.
 */

public class MainEventsActivity extends AppCompatActivity implements MainEventsView,BottomNavigationView.OnNavigationItemSelectedListener, SwipeRefreshLayout.OnRefreshListener, FragmentManager.OnBackStackChangedListener {

    @BindView(R.id.events_recyler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.bottom_navigation_layout)
    BottomNavigationView mBottomView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.filterIconOpen)
    ImageView openFilterIcon;
    @BindView(R.id.filterIconClose)
    ImageView closeFilterIcon;

    @BindView(R.id.layout_container)
    FrameLayout mContainer;
    @BindView(R.id.filter_bar_id)
    LinearLayout mFilterLayout;

    MainEventsPresenterImpl mEventsPresenter;
    DisplayEventsAdapter mAdapter;
    FrameLayout realFrameLayout;

    SharedPref mSharedPreferences;
    private Animation slideLeftRightAnim, slideRightLeftAnim;

    public static final String[] EXTERNAL_STORAGE_CAMERA = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO};
    public static final int CAMERA_REQUEST_CODE = 1069;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_events_activity);
        ButterKnife.bind(this);
        HelperClass.hawkInit(this);
        initAnimations();
        mEventsPresenter = new MainEventsPresenterImpl(this);
        mSharedPreferences = new SharedPref();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(MainEventsActivity.this));
        mRefreshLayout.setOnRefreshListener(this);
        mBottomView.setOnNavigationItemSelectedListener(this);
        getSupportFragmentManager().addOnBackStackChangedListener(this);
        onRefresh();
    }

    public void initAnimations()
    {
        slideLeftRightAnim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_left_to_right_animation);
        slideRightLeftAnim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_right_to_left_animations);
        realFrameLayout = mContainer.findViewById(R.id.frame_recycler_layout);
    }

    public DisplayEventsAdapter.OnItemClickListener mListener = new DisplayEventsAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(EventResponse item) {

            mToolbar.setVisibility(View.GONE);
            EventDetailFragment eventFragment = new EventDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constants.SELECTED_EVENT,item);
            eventFragment.setArguments(bundle);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.layout_container,eventFragment);
            transaction.addToBackStack(FragmentFlags.EVENT_DETAIL_FRAGMENT);
            transaction.commit();



        }
    };

    @Override
    public void displayEvents(final EventsResponse<EventResponse> eventsList) {
        mAdapter = new DisplayEventsAdapter(this, eventsList, mListener);
        runOnUiThread(new Thread(new Runnable() {
          @Override
          public void run() {
              mRecyclerView.setAdapter(mAdapter);
              mAdapter.notifyDataSetChanged();
          }
      }));

    }

    @Override
    public void onRefresh() {
        mRefreshLayout.setRefreshing(true);
        mFilterLayout.setVisibility(View.GONE);
        openFilterIcon.setVisibility(View.VISIBLE);
        mEventsPresenter.getEventsRequest();
        if(openFilterIcon.getVisibility() != View.VISIBLE ) openFilterIcon.setVisibility(View.VISIBLE);
        if(!openFilterIcon.isClickable()) openFilterIcon.setClickable(true);
    }

    @Override
    public void onStopRefresh() {
        runOnUiThread(new Thread(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(false);
            }
        }));
    }

    @Override
    public void closeActivity() {
        this.finish();
    }

    @Override
    public void openProfileFragment(UserProfileResponse<UserProfile> profileResponse) {
        ProfileFragment mFragment = new ProfileFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.PROFILE_RESPONSE_ITEM, (Serializable) profileResponse);
        mFragment.setArguments(bundle);
        FragmentTransaction mTransaction = getSupportFragmentManager().beginTransaction();
        mTransaction.replace(R.id.layout_container,mFragment);
        mTransaction.addToBackStack(FragmentFlags.PROFILE_FRAGMENT);
        mTransaction.commit();

    }

    @Override
    public void openFilterLayoutBar() {
        mFilterLayout.setAnimation(slideLeftRightAnim);
        mFilterLayout.startAnimation(slideLeftRightAnim);
        mFilterLayout.setVisibility(View.VISIBLE);
        closeFilterIcon.setClickable(true);
        openFilterIcon.setClickable(false);
        openFilterIcon.setVisibility(View.GONE);
    }

    @Override
    public void closeFilterlayoutBar() {
        mFilterLayout.setAnimation(slideRightLeftAnim);
        mFilterLayout.startAnimation(slideRightLeftAnim);
        mFilterLayout.setVisibility(View.GONE);
        openFilterIcon.setClickable(true);
        openFilterIcon.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.filterIconOpen,R.id.filterIconClose,R.id.search_icon,R.id.when,R.id.where,R.id.what})
    public void onButtonsClick(View v)
    {
        switch(v.getId())
        {
            case R.id.filterIconOpen:
                openFilterLayoutBar();
                break;
            case R.id.filterIconClose:
                closeFilterlayoutBar();
                break;
            case R.id.where:
                //TODO do where...
                break;
            case R.id.when:
                mToolbar.setVisibility(View.VISIBLE);
                WhenWhatFragment whenFragment = new WhenWhatFragment(Constants.WHEN_KEY_STRING);
                getSupportFragmentManager().beginTransaction().replace(R.id.layout_container,whenFragment).addToBackStack(FragmentFlags.WHEN_FRAGMENT_FLAG).commit();
                //TODO do when....
                break;
            case R.id.what:
                mToolbar.setVisibility(View.VISIBLE);
                WhenWhatFragment whatFragment = new WhenWhatFragment(Constants.WHAT_KEY_STRING);
                getSupportFragmentManager().beginTransaction().replace(R.id.layout_container,whatFragment).addToBackStack(FragmentFlags.WHAT_FRAGMENT_FLAG).commit();
                //TODO do what....
                break;

        }
    }

    @Override
    public boolean onNavigationItemSelected( MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.events:
                item.setChecked(true);
                Log.e("USER_ID",String.valueOf(Hawk.get(Constants.USER_ID)));
                Log.e("HEADER TOKEN","Bearer " + String.valueOf(Hawk.get(Constants.ACCESS_TOKEN)));
                if(getSupportFragmentManager().getBackStackEntryCount() == 0)
                {
                    onRefresh();
                }else {
                    getSupportFragmentManager().popBackStack();
                    onRefresh();
                }
                break;
            case R.id.camera:
                if(Hawk.contains(Constants.ACCESS_TOKEN) && Hawk.contains(Constants.USER_ID))
                {
                    item.setChecked(true);
                    handleCameraPermissions();
                }else
                {
                    mEventsPresenter.showNotLoggedDialog();
                }
                break;
            case R.id.profile:

                if(Hawk.contains(Constants.ACCESS_TOKEN) && Hawk.contains(Constants.USER_ID))
                {
                    item.setChecked(true);
                    mEventsPresenter.handleProfileAccess();
                }else{
                    mEventsPresenter.showNotLoggedDialog();
                }

        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        onRefresh();
    }

    public void handleCameraPermissions() {
        if(ContextCompat.checkSelfPermission(this, EXTERNAL_STORAGE_CAMERA[0]) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, EXTERNAL_STORAGE_CAMERA[1]) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, EXTERNAL_STORAGE_CAMERA[2]) != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(EXTERNAL_STORAGE_CAMERA,CAMERA_REQUEST_CODE);
        }else
        {
            mEventsPresenter.handleCameraAccess();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == CAMERA_REQUEST_CODE && HelperClass.hasAllPermissionsGranted(grantResults))
        {
            mEventsPresenter.handleCameraAccess();
        }
    }
    @Override
    public void onBackStackChanged() {
        if(getSupportFragmentManager().getBackStackEntryCount() == 0)
        {
            if(mToolbar.getVisibility() == View.GONE) mToolbar.setVisibility(View.VISIBLE);
        }else
        {
            mToolbar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
       if(getSupportFragmentManager().getBackStackEntryCount() > 0)
       {
           if(getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() - 1 ).getName().equals(FragmentFlags.DETAIL_FEED_FRAGMENT))
           {
               HelperClass.clearBackStack(this);
               onRefresh();
           }else{
               getSupportFragmentManager().popBackStack();
               onRefresh();
           }
       }else
       {
           finish();
       }
    }
}
