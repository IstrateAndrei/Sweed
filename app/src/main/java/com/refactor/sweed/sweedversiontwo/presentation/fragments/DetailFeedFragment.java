package com.refactor.sweed.sweedversiontwo.presentation.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.orhanobut.hawk.Hawk;
import com.refactor.sweed.sweedversiontwo.R;
import com.refactor.sweed.sweedversiontwo.data.models.EventEntryFee;
import com.refactor.sweed.sweedversiontwo.data.models.EventResponse;
import com.refactor.sweed.sweedversiontwo.data.models.EventsResponse;
import com.refactor.sweed.sweedversiontwo.data.models.MediaAndCommentsItem;
import com.refactor.sweed.sweedversiontwo.presentation.adapters.DisplayEventMediaAdapter;
import com.refactor.sweed.sweedversiontwo.presentation.presenters.EventDetailPresenterImpl;
import com.refactor.sweed.sweedversiontwo.presentation.views.DetailFeedView;
import com.refactor.sweed.sweedversiontwo.util.Constants;
import com.refactor.sweed.sweedversiontwo.util.HelperClass;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Response;

/**
 * Created by andrei.istrate on 23.06.2017.
 */

public class DetailFeedFragment extends Fragment implements DetailFeedView {

    static DetailFeedFragment mFragment;
    EventResponse selectedEvent;
    EventDetailPresenterImpl mPresenter;

    @BindView(R.id.comment_recycler_view)
    RecyclerView mCommentRecyclerView;
    @BindView(R.id.not_logged_in_text)
    TextView notLoggedText;

    public static DetailFeedFragment getInstance()
    {
        if(mFragment == null)
        {
            mFragment = new DetailFeedFragment();
        }
        return mFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null)
        {
            selectedEvent = (EventResponse) getArguments().getSerializable(Constants.SELECTED_EVENT);
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_feed_fragment_layout,container,false);
        ButterKnife.bind(this,view);
        mCommentRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mPresenter = new EventDetailPresenterImpl((DetailFeedView) DetailFeedFragment.this);
        HelperClass.hawkInit(getActivity());
        if(selectedEvent != null)
        {
            if(Hawk.contains(Constants.ACCESS_TOKEN))
            {
                mPresenter.getEventPostComments((String) Hawk.get(Constants.ACCESS_TOKEN),selectedEvent.getId());
            }else
            {
                notLoggedText.setVisibility(View.VISIBLE);
                notLoggedText.setText(getResources().getString(R.string.login_to_see_comments_string));
            }

        }

        return view;
    }


    @Override
    public void displayEventMedia(final EventsResponse<MediaAndCommentsItem> responseData) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                DisplayEventMediaAdapter mediaAdapter = new DisplayEventMediaAdapter(getActivity(),responseData);
                mCommentRecyclerView.setAdapter(mediaAdapter);
                mediaAdapter.notifyDataSetChanged();
            }
        });

    }

}
