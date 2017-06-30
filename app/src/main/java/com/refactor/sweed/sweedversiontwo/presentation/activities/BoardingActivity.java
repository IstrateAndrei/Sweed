package com.refactor.sweed.sweedversiontwo.presentation.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.refactor.sweed.sweedversiontwo.R;
import com.refactor.sweed.sweedversiontwo.presentation.adapters.ViewPagerAdapter;
import com.refactor.sweed.sweedversiontwo.presentation.fragments.BoardingCameraFragment;
import com.refactor.sweed.sweedversiontwo.presentation.fragments.BoardingLocationFragment;
import com.refactor.sweed.sweedversiontwo.presentation.views.BoardingView;
import com.refactor.sweed.sweedversiontwo.presentation.views.MainEventsView;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator;

/**
 * Created by andrei.istrate on 08.06.2017.
 */

public class BoardingActivity extends AppCompatActivity implements BoardingView {

    @BindView(R.id.viewpager_onboarding)
    ViewPager mViewPager;
    @BindView(R.id.viewpager_indicator_onboarding)
    CircleIndicator mIndicator;

    ViewPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.onboarding_activity_layout);
        ButterKnife.bind(this);
        setAdapter();
    }

    public void setAdapter() {
        mPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mPagerAdapter.addFragment(BoardingLocationFragment.getInstance(), BoardingLocationFragment.TAG);
        mPagerAdapter.addFragment(BoardingCameraFragment.getInstance(), BoardingCameraFragment.TAG);
        mViewPager.setAdapter(mPagerAdapter);
        mIndicator.setViewPager(mViewPager);
        mPagerAdapter.registerDataSetObserver(mIndicator.getDataSetObserver());
    }

    public void changePage(int page) {
        mViewPager.setCurrentItem(page);
    }


    @Override
    public void goToEventsActivity() {
        Intent intent = new Intent(BoardingActivity.this, MainEventsActivity.class);
        startActivity(intent);
        this.finish();
    }
}
