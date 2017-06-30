package com.refactor.sweed.sweedversiontwo.presentation.adapters;

import android.content.Context;
import android.support.constraint.Group;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.refactor.sweed.sweedversiontwo.R;
import com.refactor.sweed.sweedversiontwo.data.models.EventResponse;
import com.refactor.sweed.sweedversiontwo.data.models.EventsResponse;
import com.refactor.sweed.sweedversiontwo.util.Constants;
import com.squareup.picasso.Picasso;

import java.text.DateFormatSymbols;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by andrei.istrate on 31.05.2017.
 */

public class DisplayEventsAdapter extends RecyclerView.Adapter<DisplayEventsAdapter.MyHolder> {

    private Context mContext;
    private EventsResponse<EventResponse> mEventList;
    private int counter;
    private SparseBooleanArray selectedItem = new SparseBooleanArray();
    private OnItemClickListener mListener;

    public DisplayEventsAdapter(Context context, EventsResponse<EventResponse> mList,OnItemClickListener listener)
    {
        this.mContext = context;
        this.mEventList = mList;
        this.mListener = listener;

    }



    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    @Override
    public DisplayEventsAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_event_item_layout, parent, false);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.updated_event_item_layout, parent, false);

        return new MyHolder(view);
    }

    public interface OnItemClickListener{
        void onItemClick(EventResponse item);
    }

    @Override
    public void onBindViewHolder(DisplayEventsAdapter.MyHolder holder, int position) {
        EventResponse eventItem = mEventList.getData().get(position);
        Picasso.with(mContext).load(eventItem.getPresentation().getImage()).fit().centerCrop().into(holder.eventPicture);
        if(eventItem.getState().equals("Open"))
        {
            setCounter(counter + 1);
        }

        if(position == 0 && eventItem.getState().equals("Open"))
        {
            holder.title.setVisibility(View.VISIBLE);
            holder.title.setText(Constants.HAPPENING_NOW_STRING);
            holder.title.setGravity(Gravity.START);
        }else if(position == 0 && eventItem.getState().equals("Close"))
        {
            holder.title.setVisibility(View.VISIBLE);
            holder.title.setText(Constants.UPCOMING_EVENTS_STRING);
            holder.title.setGravity(Gravity.END);
        }else
        {
            if(position == getCounter() && eventItem.getState().equals("Close"))
            {
                selectedItem.put(position, true);
                holder.title.setText(Constants.UPCOMING_EVENTS_STRING);
                holder.title.setGravity(Gravity.RIGHT);
                holder.title.setVisibility(View.VISIBLE);
            }else{
                holder.title.setVisibility(View.GONE);
            }
        }

        holder.eventTitle.setText(eventItem.getName());

        if(eventItem.getPostsCount() > 0)
        {
            holder.eventPosts.setVisibility(View.VISIBLE);
            holder.eventPosts.setText(String.valueOf(eventItem.getPostsCount()));
        }

        String distanceString = String.valueOf(eventItem.getDistanceTo()) + " km";
        holder.eventDistance.setText(distanceString);

        String rawStartDateString = eventItem.getStartsDate();
        String rawEndDateString = eventItem.getEndsDate();

        String startMonthNumber = rawStartDateString.substring(0, rawStartDateString.indexOf("/"));
        String endMonthNumber = rawEndDateString.substring(0, rawEndDateString.indexOf("/"));

        DateFormatSymbols mDateFormat = new DateFormatSymbols();

        String startMonthString = mDateFormat.getMonths()[Integer.parseInt(startMonthNumber ) - 1].substring(0,3).toUpperCase();
        String endMonthString = mDateFormat.getMonths()[Integer.parseInt(endMonthNumber ) - 1].substring(0,3).toUpperCase();

        String startDayString = rawStartDateString.substring(rawStartDateString.indexOf("/") + 1 , rawStartDateString.lastIndexOf("/"));
        String endDayString = rawEndDateString.substring(rawEndDateString.indexOf("/") + 1, rawEndDateString.lastIndexOf("/"));


            if(startDayString.equals(endDayString))
            {
//                    holder.endDateLayout.setVisibility(View.GONE);
//                  holder.startDateLayout.setVisibility(View.VISIBLE);
                holder.startDateDay.setText(startDayString);handleMargins(holder.startDateDay);
                holder.startDateMonth.setText(startMonthString);handleMargins(holder.startDateMonth);
            }else
            {
//                    holder.startDateLayout.setVisibility(View.VISIBLE);
//                    holder.endDateLayout.setVisibility(View.VISIBLE);
                holder.startDateDay.setText(startDayString);
                holder.startDateMonth.setText(startMonthString);
                holder.endDateDay.setText(endDayString);
                holder.endDateMonth.setText(endMonthString);
            }
            holder.eventPrice.setText(String.valueOf(eventItem.getEntryFee().getAmmount()));
            holder.currency.setText(eventItem.getEntryFee().getCurrency());


        holder.bind(eventItem,mListener);
    }
    public void handleMargins(View v)
    {
        ViewGroup.MarginLayoutParams param = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
        param.setMargins(35,5,0,0);
        v.setLayoutParams(param);
        v.requestLayout();
    }

    @Override
    public int getItemCount() {
        return mEventList.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.poster_image_event)
        ImageView eventPicture;
        @BindView(R.id.eventPosts)
        TextView eventPosts;
        @BindView(R.id.startDateDay)
        TextView startDateDay;
        @BindView(R.id.startDateMonth)
        TextView startDateMonth;
//                @BindView(R.id.startDateLayout)
//                LinearLayout startDateLayout;
        @BindView(R.id.endDateDay)
        TextView endDateDay;
        @BindView(R.id.endDateMonth)
        TextView endDateMonth;
//        @BindView(R.id.constraint_group)
//        Group mDateGroup;
//
//        @BindView(R.id.endDateLayout)
//        LinearLayout endDateLayout;
//        @BindView(R.id.dateslayout)
//        LinearLayout dateslayout;


        @BindView(R.id.eventTitle)
        TextView eventTitle;
        @BindView(R.id.eventDistance)
        TextView eventDistance;
        @BindView(R.id.eventPrice)
        TextView eventPrice;
        @BindView(R.id.event_currency)
        TextView currency;
//        @BindView(R.id.locationDetailsLayout)
//        LinearLayout locationDetailsLayout;
//        @BindView(R.id.eventNamePriceLayout)
//        LinearLayout eventNamePriceLayout;

        public MyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void bind(final EventResponse item, final OnItemClickListener listener)
        {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }
}
