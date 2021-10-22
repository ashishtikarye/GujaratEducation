package com.gujeducation.gujaratedu.Adapter;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.gujeducation.R;
import com.gujeducation.gujaratedu.Helper.BaseViewHolder;
import com.gujeducation.gujaratedu.Model.YoutubeVideoTest;
import com.pierfrancescosoffritti.youtubeplayer.player.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.youtubeplayer.player.YouTubePlayerView;

import java.util.ArrayList;

public class YoutubeRecyclerAdapterTest extends RecyclerView.Adapter<BaseViewHolder> {


    public static final int VIEW_TYPE_NORMAL = 1;

    private ArrayList<YoutubeVideoTest> mYoutubeVideos;
    DisplayMetrics displayMetrics = new DisplayMetrics();

    public YoutubeRecyclerAdapterTest(ArrayList<YoutubeVideoTest> youtubeVideos) {
        mYoutubeVideos = youtubeVideos;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_youtube_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }


    @Override
    public int getItemViewType(int position) {
        return VIEW_TYPE_NORMAL;
    }

    @Override
    public int getItemCount() {
        if (mYoutubeVideos != null && mYoutubeVideos.size() > 0) {
            return mYoutubeVideos.size();
        } else {
            return 1;
        }
    }

    public void setItems(ArrayList<YoutubeVideoTest> youtubeVideos) {
        mYoutubeVideos = youtubeVideos;
        notifyDataSetChanged();
    }


    public class ViewHolder extends BaseViewHolder {
        TextView textWaveTitle;
        ImageView playButton,imageViewItems;
        YouTubePlayerView youTubePlayerView;

        public ViewHolder(View itemView) {
            super(itemView);
            playButton = itemView.findViewById(R.id.btnPlay);
            imageViewItems = itemView.findViewById(R.id.imageViewItem);
            textWaveTitle = itemView.findViewById(R.id.textViewTitle);
            youTubePlayerView = itemView.findViewById(R.id.youtube_view);

        }

        protected void clear() {

        }

        public void onBind(int position) {
            super.onBind(position);
            final YoutubeVideoTest mYoutubeVideo = mYoutubeVideos.get(position);
            ((Activity) itemView.getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int width = displayMetrics.widthPixels;
            if (mYoutubeVideo.getTitle() != null)
                textWaveTitle.setText(mYoutubeVideo.getTitle());

            if (mYoutubeVideo.getImageUrl() != null) {
                Glide.with(itemView.getContext())
                        .load(mYoutubeVideo.getImageUrl()).
                        apply(new RequestOptions().override(width - 36, 200))
                        .into(imageViewItems);
            }
            imageViewItems.setVisibility(View.VISIBLE);
            playButton.setVisibility(View.VISIBLE);
            youTubePlayerView.setVisibility(View.GONE);

            playButton.setOnClickListener(view -> {
                imageViewItems.setVisibility(View.GONE);
                youTubePlayerView.setVisibility(View.VISIBLE);
                playButton.setVisibility(View.GONE);
                youTubePlayerView.initialize(initializedYouTubePlayer -> initializedYouTubePlayer.addListener(new AbstractYouTubePlayerListener() {
                    @Override
                    public void onReady() {
                        Log.e("onReady", "videoCode->" + mYoutubeVideo.getVideoId());
                        initializedYouTubePlayer.loadVideo(mYoutubeVideo.getVideoId(), 0);
                    }
                }), true);
            });
        }
    }
}
