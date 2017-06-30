package com.refactor.sweed.sweedversiontwo.presentation.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.makeramen.roundedimageview.RoundedImageView;
import com.refactor.sweed.sweedversiontwo.R;
import com.refactor.sweed.sweedversiontwo.data.models.UserProfile;
import com.refactor.sweed.sweedversiontwo.data.models.UserProfileResponse;
import com.refactor.sweed.sweedversiontwo.util.Constants;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by andrei.istrate on 28.06.2017.
 */

public class ProfileFragment extends Fragment {

    UserProfileResponse<UserProfile> selectedProfile;
    @BindView(R.id.profile_image_view)
    RoundedImageView mProfileImage;
    @BindView(R.id.personal_events_recycler_view)
    RecyclerView mRecycler;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null)
        {
            selectedProfile = (UserProfileResponse<UserProfile>) getArguments().getSerializable(Constants.PROFILE_RESPONSE_ITEM);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment_layout,container,false);
        ButterKnife.bind(this,view);
        initDetails(selectedProfile);
        return view;
    }

    public void initDetails(UserProfileResponse<UserProfile> profile)
    {
        Picasso.with(getActivity()).load(profile.getData().getPhotoURL()).fit().centerCrop().into(mProfileImage);

    }
}
