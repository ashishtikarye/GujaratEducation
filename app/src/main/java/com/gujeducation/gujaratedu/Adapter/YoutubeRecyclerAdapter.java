package com.gujeducation.gujaratedu.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gujeducation.R;
import com.gujeducation.gujaratedu.Helper.BaseViewHolder;
import com.gujeducation.gujaratedu.Model.YoutubeVideo;
import com.pierfrancescosoffritti.youtubeplayer.player.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.youtubeplayer.player.YouTubePlayerView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class YoutubeRecyclerAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    public static final int VIEW_TYPE_NORMAL = 1;
    private final AppCompatActivity activity;
    String videoCode;
    ArrayList<YoutubeVideo> mYoutubeVideos = new ArrayList();
    Dialog dialogPopup, dialogPopupVideo;
    AppCompatImageView mIvPopupImage, mIvClose;
    //private List<YoutubeVideo> mYoutubeVideos;
    DisplayMetrics displayMetrics = new DisplayMetrics();




    public YoutubeRecyclerAdapter(AppCompatActivity activity, ArrayList<YoutubeVideo> youtubeVideos) {
        this.activity = activity;
        this.mYoutubeVideos = youtubeVideos;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_post, parent, false));
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

    public void setItems(ArrayList<YoutubeVideo> youtubeVideos) {
        mYoutubeVideos = youtubeVideos;
        notifyDataSetChanged();
    }

    public String extractYoutubeCode(String url) throws MalformedURLException {
        String query = new URL(url).getQuery();
        String[] param = query.split("&");
        String id = null;
        for (String row : param) {
            String[] param1 = row.split("=");
            if (param1[0].equals("v")) {
                id = param1[1];
            }
        }
        return id;
    }

    public String extractYoutubeCodeShort(String url) throws MalformedURLException {
        return url.substring(url.lastIndexOf("/") + 1);
    }

    public void showPopup(final String image) {

        dialogPopup = new Dialog(
                activity, R.style.popupTheme);
        LayoutInflater inflater = (LayoutInflater)
                activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_popup, null);
        dialogPopup.setContentView(view); // your custom view.
        dialogPopup.setCancelable(true);
        dialogPopup.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialogPopup.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialogPopup.show();


        mIvPopupImage = view.findViewById(R.id.ivpopupimage);
        mIvClose = view.findViewById(R.id.ivclose);

        try {
            //Log.e("showPopup", "link-->" + link);
            Glide.with(activity).load(image)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.HIGH).dontAnimate()
                    .into(mIvPopupImage);
        } catch (Exception e) {
            e.printStackTrace();
        }


        mIvPopupImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogPopup.dismiss();
            }
        });

        mIvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogPopup.dismiss();
            }
        });


    }

    public class ViewHolder extends BaseViewHolder {
        AppCompatTextView mTvFullName, mTvUserType, mTvSchoolName, mTvTitle;
        AppCompatImageView mIvThumbnail;
        AppCompatImageView playButton;
        YouTubePlayerView youTubePlayerView;

        public ViewHolder(View itemView) {
            super(itemView);
            mTvFullName = itemView.findViewById(R.id.tvfullname);
            mTvUserType = itemView.findViewById(R.id.tvusertype);
            mTvSchoolName = itemView.findViewById(R.id.tvschoolname);
            mTvTitle = itemView.findViewById(R.id.tvtopicname);
            mIvThumbnail = itemView.findViewById(R.id.ivthumbnail);
            playButton = itemView.findViewById(R.id.btnPlay);
            youTubePlayerView = itemView.findViewById(R.id.youtubeView);


        }

        protected void clear() {

        }

        public void onBind(int position) {
            super.onBind(position);
            //Log.e("position","--->"+position);

            final YoutubeVideo mYoutubeVideo = mYoutubeVideos.get(position);
            ((Activity) itemView.getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

                //Log.e("OnBind","youtbLink->"+mYoutubeVideo.getYoutubeLink()+" - "+mYoutubeVideo.getTitle());
            mTvFullName.setText(mYoutubeVideo.getFullname());
            mTvSchoolName.setText(mYoutubeVideo.getSchoolname());
            if (mYoutubeVideo.getRole().equalsIgnoreCase("T")) {
                mTvUserType.setText("[Teacher]");
            } else {
                mTvUserType.setText("[Student]");
            }
            mTvTitle.setText(mYoutubeVideo.getTitle());

            if (!mYoutubeVideo.getImage().equalsIgnoreCase("NA")) {
                mIvThumbnail.setVisibility(View.VISIBLE);
                playButton.setVisibility(View.GONE);
                youTubePlayerView.setVisibility(View.GONE);
                Glide.with(activity)
                        .load(mYoutubeVideo.getImage())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .priority(Priority.IMMEDIATE).dontAnimate()
                        .into(mIvThumbnail);
            } else {
                mIvThumbnail.setVisibility(View.GONE);
            }


            mIvThumbnail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPopup(mYoutubeVideo.getImage());
                }
            });

            if (!mYoutubeVideo.getYoutubeLink().equalsIgnoreCase("NA")) {
                // mIvThumbnail.setVisibility(View.GONE);
                playButton.setVisibility(View.VISIBLE);
                youTubePlayerView.setVisibility(View.VISIBLE);
                try {
                    //Log.e("utubelink", "-->" + mYoutubeVideo.getYoutubeLink());
                    if (mYoutubeVideo.getYoutubeLink().contains("https://www.youtube.com/")) {
                        videoCode = extractYoutubeCode(mYoutubeVideo.getYoutubeLink());
                        //Log.e("LongLink->", "" + videoCode);
                    } else {
                        videoCode = extractYoutubeCodeShort(mYoutubeVideo.getYoutubeLink());
                        //Log.e("ShortLink->", "" + videoCode);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    Log.e("catch", "---" + e.getMessage());
                }
                //Log.e("videoCodefinal->", "" + videoCode);

            } else {
                //Log.e("NALINKUTUBE", "-->" + mYoutubeVideo.getYoutubeLink());
                playButton.setVisibility(View.GONE);
                youTubePlayerView.setVisibility(View.GONE);
            }


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("onReady_itemView", "videoCode->" + videoCode);

                }
            });
            playButton.setOnClickListener(view -> {
                //  imageViewItems.setVisibility(View.GONE);
                youTubePlayerView.setVisibility(View.VISIBLE);
                playButton.setVisibility(View.GONE);
                youTubePlayerView.initialize(initializedYouTubePlayer -> initializedYouTubePlayer.addListener(new AbstractYouTubePlayerListener() {
                    @Override
                    public void onReady() {
                        Log.e("onReady", "videoCode->" + videoCode);
                        initializedYouTubePlayer.loadVideo(videoCode, 0);
                    }
                }), true);
            });
        }
    }
}
