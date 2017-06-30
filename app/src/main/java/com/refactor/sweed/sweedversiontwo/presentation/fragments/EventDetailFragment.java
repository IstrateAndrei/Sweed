package com.refactor.sweed.sweedversiontwo.presentation.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.orhanobut.hawk.Hawk;
import com.refactor.sweed.sweedversiontwo.R;
import com.refactor.sweed.sweedversiontwo.data.models.EventResponse;
import com.refactor.sweed.sweedversiontwo.presentation.activities.MainEventsActivity;
import com.refactor.sweed.sweedversiontwo.util.Constants;
import com.refactor.sweed.sweedversiontwo.util.FragmentFlags;
import com.refactor.sweed.sweedversiontwo.util.HelperClass;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by andrei.istrate on 12.06.2017.
 */

public class EventDetailFragment extends Fragment implements OnMapReadyCallback {

    EventResponse selectedEvent;
    @BindView(R.id.poster_image_event)
    ImageView posterImage;
    @BindView(R.id.about_button)
    Button aboutButton;
    @BindView(R.id.feed_button)
    Button feedButton;
    @BindView(R.id.description_text_view)
    TextView descriptionText;
    @BindView(R.id.attending_number_text)
    TextView attendingText;
    @BindView(R.id.location_param_text)
    TextView locationText;
    @BindView(R.id.back_arrow_button)
    ImageButton backArrowButton;
    @BindView(R.id.map_container)
    FrameLayout mapLayout;
    @BindView(R.id.join_floating_buton)
    FloatingActionButton mJoinButton;
    @BindView(R.id.line_views_layout)
    LinearLayout linesLayout;

    View aboutLine, feedLine;
    public GoogleMap mMap;
    SupportMapFragment mapFragment;

    public static final String[] LOCATION_PERMISSIONS = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION};
    public static final int LOCATION_REQUEST_CODE = 669;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.event_detail_layout_fragment,container,false);
        ButterKnife.bind(this,view);
        showEventDetails(selectedEvent);
        handleFloatingButton(selectedEvent);
        initViewLines();
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!getArguments().isEmpty()) {
            selectedEvent = (EventResponse) getArguments().getSerializable(Constants.SELECTED_EVENT);
        }
        mapFragment = SupportMapFragment.newInstance();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.map_container, mapFragment).commit();
        try {
            mapFragment.getMapAsync((OnMapReadyCallback) this);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void showEventDetails(EventResponse eventItem) {
        Picasso.with(getActivity()).load(eventItem.getPresentation().getImage()).fit().centerCrop().into(posterImage);
        descriptionText.setText(eventItem.getDescription());
        String attendingString = String.valueOf(eventItem.getNumberAttendees()) + " " + this.getResources().getString(R.string.people_are_attending_string);
        attendingText.setText(attendingString);
    }
    public void handleFloatingButton(EventResponse eventItem)
    {
        if(eventItem.isAttending())
        {
            mJoinButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_check_white_48dpa,getActivity().getTheme()));
            mJoinButton.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getActivity(),R.color.holo_green_dark)));
            mJoinButton.setClickable(false);
        }else
        {
            mJoinButton.setClickable(true);
        }
    }
    public void initViewLines()
    {
        aboutLine = (View) linesLayout.findViewById(R.id.about_button_line);
        feedLine = (View) linesLayout.findViewById(R.id.feed_button_line);
        aboutLine.setVisibility(View.INVISIBLE);
        feedLine.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.back_arrow_button,R.id.about_button,R.id.feed_button,R.id.join_floating_buton})
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.back_arrow_button:
                HelperClass.clearBackStack(getActivity());
                ((MainEventsActivity) getActivity()).onRefresh();
                break;
            case R.id.about_button:

                if((descriptionText.getVisibility() == View.GONE) && (mapLayout.getVisibility() == View.GONE))
                {
                    descriptionText.setVisibility(View.VISIBLE);
                    mapLayout.setVisibility(View.VISIBLE);
                }

                if(getActivity().getSupportFragmentManager().getBackStackEntryCount() > 0)
                {
                    String fragmentTag = getActivity().getSupportFragmentManager().getBackStackEntryAt(getActivity().getSupportFragmentManager().getBackStackEntryCount() - 1).getName();
                    if(fragmentTag.equals(FragmentFlags.DETAIL_FEED_FRAGMENT))
                    {
                        getActivity().getSupportFragmentManager().popBackStack();
                        aboutLine.setVisibility(View.INVISIBLE);
                        feedLine.setVisibility(View.VISIBLE);
                    }else if (fragmentTag.equals(FragmentFlags.EVENT_DETAIL_FRAGMENT)){} //do nothing....
                }
                break;
            case R.id.feed_button:

                //change this in the future.... make the transition smooth...
                if((descriptionText.getVisibility() == View.VISIBLE) && (mapLayout.getVisibility() == View.VISIBLE))
                {
                    descriptionText.setVisibility(View.GONE);
                    mapLayout.setVisibility(View.GONE);
                }
                if(getActivity().getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    String fragmentTag = getActivity().getSupportFragmentManager().getBackStackEntryAt(getActivity().getSupportFragmentManager().getBackStackEntryCount() - 1).getName();
                    if (fragmentTag.equals(FragmentFlags.DETAIL_FEED_FRAGMENT)) {
                        //already in the feed_fragment
                    } else if (fragmentTag.equals(FragmentFlags.EVENT_DETAIL_FRAGMENT)) {
                        aboutLine.setVisibility(View.VISIBLE);
                        feedLine.setVisibility(View.INVISIBLE);
                        openFeedFragment();
                    }
                }
                break;
            case R.id.join_floating_buton:
                selectedEvent.setAttending(true);
                handleFloatingButton(selectedEvent);
                break;
        }
    }

//    public void handleBackPressed()
//    {
//        String fragmentFlag = getActivity().getSupportFragmentManager().getBackStackEntryAt(getActivity().getSupportFragmentManager().getBackStackEntryCount() - 1).getName();
//        Log.e("CURRENT FRAGMENT IS ", fragmentFlag);
//        if(getView() != null && fragmentFlag.equals(FragmentFlags.DETAIL_FEED_FRAGMENT))
//        {
//            getView().requestFocus();
//            getView().setFocusableInTouchMode(true);
//            getView().setOnKeyListener(new View.OnKeyListener() {
//                @Override
//                public boolean onKey(View view, int i, KeyEvent keyEvent) {
//                    if (i == android.view.KeyEvent.KEYCODE_BACK) {
//                        HelperClass.clearBackStack(getActivity());
//                        return true;
//                    }
//                    return false;
//                }
//            });
//        }
//    }

    @Override
    public void onResume() {
        super.onResume();

    }

    public void openFeedFragment()
    {
        DetailFeedFragment mFragment = DetailFeedFragment.getInstance();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.SELECTED_EVENT, selectedEvent);
        HelperClass.hawkInit(getActivity());
        String accessToken = Hawk.get(Constants.ACCESS_TOKEN);
        mFragment.setArguments(bundle);
        FragmentTransaction mTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        mTransaction.replace(R.id.detail_frame_container,mFragment);
        mTransaction.addToBackStack(FragmentFlags.DETAIL_FEED_FRAGMENT);
        mTransaction.commit();
        //Resize the frame container:
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (selectedEvent != null) {
            LatLng latLng = new LatLng((double) selectedEvent.getLocation().getLatitude(), (double) selectedEvent.getLocation().getLongitude());
            MarkerOptions mMarker = new MarkerOptions();
            mMarker.position(latLng);
            mMarker.title(selectedEvent.getLocation().getName());

//            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
            mMap.addMarker(mMarker).showInfoWindow();
            if (ActivityCompat.checkSelfPermission(getActivity(), LOCATION_PERMISSIONS[0]) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), LOCATION_PERMISSIONS[1]) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(),LOCATION_PERMISSIONS,LOCATION_REQUEST_CODE);
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
            mMap.getUiSettings().setScrollGesturesEnabled(true);
            mMap.getUiSettings().setTiltGesturesEnabled(true);
            mMap.getUiSettings().setRotateGesturesEnabled(true);
            mMap.getUiSettings().setZoomGesturesEnabled(true);

        }
//        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
//            @Override
//            public boolean onMarkerClick(Marker marker) {
//                Log.e("MARKER","MARKER CLICKED");
//                RelativeLayout.LayoutParams relativeParams = new RelativeLayout.LayoutParams(width,height);
//                relativeParams.setMargins(0,0,0,30);
//                mJoinButton.setLayoutParams(relativeParams);
//                return true;
//            }
//        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == LOCATION_REQUEST_CODE && HelperClass.hasAllPermissionsGranted(grantResults))
        {
            if(mMap != null)
            {
                mMap.setMyLocationEnabled(true);
            }
        }
    }



}
