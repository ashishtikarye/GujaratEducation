package com.gujeducation.gujaratedu.Adapter;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gujeducation.R;
import com.gujeducation.gujaratedu.Activity.MinamiDuniaScreen;
import com.gujeducation.gujaratedu.Activity.VideoScreen;
import com.gujeducation.gujaratedu.Helper.Functions;
import com.gujeducation.gujaratedu.Model.Minami;
import com.gujeducation.gujaratedu.Model.Video;

import java.util.ArrayList;

public class MinamiListAdapter extends RecyclerView.Adapter<MinamiListAdapter.MyViewHolder> {

    private final AppCompatActivity activity;
    public boolean error = false;
    ArrayList<Minami> listMinami = new ArrayList();
    Functions mFunction;
    Intent intent;
    private int mNumColumns = 0;

    public MinamiListAdapter(AppCompatActivity activity, ArrayList<Minami> listMinami) {
        this.activity = activity;
        this.listMinami = listMinami;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup holder, int i) {
        View itemView = LayoutInflater.from(holder.getContext()).inflate(R.layout.row_video, holder, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final MyViewHolder menuItemHolder = holder;
        mFunction = new Functions(activity);
        final Minami minamiList = listMinami.get(position);


        try {
            menuItemHolder.mTvTitle.setText(minamiList.getTitle());
            menuItemHolder.mTvMadeBy.setText("Made By : " + minamiList.getMadeBy());
            menuItemHolder.mTvCredits.setText("Credits : " + minamiList.getCreditsAndLicence());
            Glide.with(activity)
                    .load(minamiList.getThumbnail())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.IMMEDIATE).dontAnimate()
                    .into(menuItemHolder.mIvThumbnail);

        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Log.e("videoTitle", "" + minamiList.getVideo());
                ((MinamiDuniaScreen)activity).showInterstitialAd();
                if (!minamiList.getVideo().contains("https://youtu.be/")) {
                    intent = new Intent(activity, VideoScreen.class);
                    intent.putExtra("videoLink", minamiList.getVideo());
                    intent.putExtra("videoTitle", minamiList.getTitle());
                    activity.startActivity(intent);
                } else {
                    watchYoutubeVideo(minamiList.getVideo());
                }


            }
        });


    }

    public void watchYoutubeVideo(String link) {
        //Log.e("link", "" + link);

        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse(link));
        try {

            activity.startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            activity.startActivity(webIntent);
        }
    }


    @Override
    public int getItemCount() {
        return listMinami.size();
    }

    public int getNumColumns() {
        return this.mNumColumns;
    }

    public void setNumColumns(int numColumns) {
        this.mNumColumns = numColumns;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final AppCompatTextView mTvTitle;
        private final AppCompatTextView mTvMadeBy;
        private final AppCompatTextView mTvCredits;
        private final AppCompatImageView mIvThumbnail, mIvPlay;

        public MyViewHolder(View itemView) {
            super(itemView);
            mFunction = new Functions(activity);
            mTvTitle = itemView.findViewById(R.id.tv_videotitle);
            mIvThumbnail = itemView.findViewById(R.id.iv_thumbnail);
            mTvMadeBy = itemView.findViewById(R.id.tv_made_by);
            mTvCredits = itemView.findViewById(R.id.tv_credits);
            mIvPlay = itemView.findViewById(R.id.iv_play);

        }
    }
}


