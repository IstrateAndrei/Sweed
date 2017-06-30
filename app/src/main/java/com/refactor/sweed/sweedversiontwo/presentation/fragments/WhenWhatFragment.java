package com.refactor.sweed.sweedversiontwo.presentation.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.refactor.sweed.sweedversiontwo.R;

/**
 * Created by andrei.istrate on 29.06.2017.
 */

public class WhenWhatFragment extends Fragment {

    private String whenOrWhatFragment;
    private WhenWhatFragment mInstance;

    public WhenWhatFragment(String fragmentType)
    {
        this.whenOrWhatFragment = fragmentType;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = null;
        if(whenOrWhatFragment != null)
        {
            if(whenOrWhatFragment.equals("when_fragment"))
            {
                v = inflater.inflate(R.layout.when_fragment_layout,container,false);
            }else if(whenOrWhatFragment.equals("what_fragment"))
            {
                v = inflater.inflate(R.layout.what_fragment_layout,container,false);
            }
        }
        return v;
    }
}
