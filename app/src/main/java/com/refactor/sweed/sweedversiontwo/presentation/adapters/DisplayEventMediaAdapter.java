package com.refactor.sweed.sweedversiontwo.presentation.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.orhanobut.hawk.Hawk;
import com.refactor.sweed.sweedversiontwo.R;
import com.refactor.sweed.sweedversiontwo.data.models.EventsResponse;
import com.refactor.sweed.sweedversiontwo.data.models.MediaAndCommentsItem;
import com.refactor.sweed.sweedversiontwo.util.Constants;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.zip.Inflater;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by andrei.istrate on 28.06.2017.
 */

public class DisplayEventMediaAdapter extends RecyclerView.Adapter<DisplayEventMediaAdapter.MyHolder> {

    private Context mContext;
    private EventsResponse<MediaAndCommentsItem> contentList;

    public DisplayEventMediaAdapter(Context context, EventsResponse<MediaAndCommentsItem> mList)
    {
        this.mContext = context;
        this.contentList = mList;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item_view_layout,parent,false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        MediaAndCommentsItem item = contentList.getData().get(position);
        holder.userTitle.setText(item.getUserInfo().getName());
        holder.postTime.setText(item.getTimePassed());
        Picasso.with(mContext).load(item.getUserInfo().getPhotoURL()).fit().centerCrop().into(holder.userImage);
        switch(item.getType())
        {
            case "Video":
                holder.videoPost.setVisibility(View.VISIBLE);
                if(isCommentByUser(item)) holder.deleteVideoImage.setVisibility(View.VISIBLE);
                //TODO fix video things....
                holder.videoPost.setVideoPath(item.getMediaBody());
                holder.videoPost.start();

                break;
            case "Message":
                holder.messageLayout.setVisibility(View.VISIBLE);
                holder.commentText.setText(item.getMediaBody());
                if(isCommentByUser(item)) holder.deleteCommentImage.setVisibility(View.VISIBLE);
                break;

            case "Photo":
                holder.photoImage.setVisibility(View.VISIBLE);
                Picasso.with(mContext).load(item.getMediaBody()).fit().centerCrop().into(holder.photoImage);
        }
    }

    public boolean isCommentByUser(MediaAndCommentsItem item)
    {
        if(item.getUserInfo().getUserId().equals(Hawk.get(Constants.USER_ID)))
        {
            return true;
        }
        return false;
    }
    @Override
    public int getItemCount() {
        return contentList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.user_icon_image)
        RoundedImageView userImage;
        @BindView(R.id.username_title_text)
        TextView userTitle;
        @BindView(R.id.post_time_text)
        TextView postTime;
        @BindView(R.id.photo_post_image)
        ImageView photoImage;
        @BindView(R.id.text_message_layout)
        LinearLayout messageLayout;
        @BindView(R.id.post_comment_text)
        TextView commentText;
        @BindView(R.id.delete_text_comment_image)
        RoundedImageView deleteCommentImage;
        @BindView(R.id.comment_video_holder)
        VideoView videoPost;
        @BindView(R.id.delete_video_comment_image)
        RoundedImageView deleteVideoImage;

        public MyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

}
