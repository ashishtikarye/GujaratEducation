package com.gujeducation.gujaratedu.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gujeducation.R;
import com.gujeducation.gujaratedu.Helper.Functions;
import com.gujeducation.gujaratedu.Model.YoutubeVideo;
import com.pierfrancescosoffritti.youtubeplayer.player.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.youtubeplayer.player.YouTubePlayerView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostUtubeAdapter extends RecyclerView.Adapter<PostUtubeAdapter.MyViewHolder> {

    private final AppCompatActivity activity;
    public boolean error = false;
    ArrayList<YoutubeVideo> listPostUtube = new ArrayList();
    Functions mFunction;
    String videoCode;
    String strVideoCode;
    private int mNumColumns = 0;
    Dialog dialogPopup;
    AppCompatImageView mIvPopupImage, mIvClose;
    String userImage="";

    public PostUtubeAdapter(AppCompatActivity activity, ArrayList<YoutubeVideo> listPostUtube) {
        this.activity = activity;
        this.listPostUtube = listPostUtube;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup holder, int i) {
        View itemView = LayoutInflater.from(holder.getContext()).inflate(R.layout.row_post, holder, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final MyViewHolder menuItemHolder = holder;
        mFunction = new Functions(activity);
        final YoutubeVideo utubeList = listPostUtube.get(position);

        try {

            menuItemHolder.mTvFullName.setText(utubeList.getFullname());
            menuItemHolder.mTvSchoolName.setText(utubeList.getSchoolname());
            if (utubeList.getRole().equalsIgnoreCase("T")) {
                menuItemHolder.mTvUserType.setText("[Teacher]");
            } else {
                menuItemHolder.mTvUserType.setText("[Student]");
            }
            menuItemHolder.mTvTitle.setText(utubeList.getTitle());

            try {
                //Log.e("showPopup", "getUser_image-->" + utubeList.getUser_image());
                userImage=utubeList.getUser_image();
                Glide.with(activity).load(utubeList.getUser_image())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .priority(Priority.HIGH).dontAnimate()
                        .into(menuItemHolder.mIvUserPic);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (!utubeList.getImage().equalsIgnoreCase("NA")) {

                //Log.e("setThumbnail", "postimage-->" + utubeList.getImage());

                menuItemHolder.mIvThumbnail.setVisibility(View.VISIBLE);
                menuItemHolder.playButton.setVisibility(View.GONE);
                menuItemHolder.youTubePlayerView.setVisibility(View.GONE);
                Glide.with(activity)
                        .load(utubeList.getImage())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .priority(Priority.IMMEDIATE).dontAnimate()
                        .into(menuItemHolder.mIvThumbnail);
            } else {
                menuItemHolder.mIvThumbnail.setVisibility(View.GONE);
            }




            //youtube link
            if (!utubeList.getYoutubeLink().equalsIgnoreCase("NA")) {
                menuItemHolder.mIvThumbnail.setVisibility(View.GONE);
                menuItemHolder.playButton.setVisibility(View.VISIBLE);
                menuItemHolder.youTubePlayerView.setVisibility(View.VISIBLE);
                /*try {
                    Log.e("onBindViewHolder", "youtbLink->" + utubeList.getYoutubeLink() + " - " + utubeList.getTitle());
                    if (utubeList.getYoutubeLink().contains("https://www.youtube.com/")) {
                        extractYoutubeCode(utubeList.getYoutubeLink());
                    } else {
                        extractYoutubeCodeShort(utubeList.getYoutubeLink());
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }*/
            } else {
                menuItemHolder.playButton.setVisibility(View.GONE);
                menuItemHolder.youTubePlayerView.setVisibility(View.GONE);
            }
            //Log.e("videoCodefinal->", "" + videoCode);

        } catch (Exception e) {
            e.printStackTrace();
        }


        holder.mIvThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(utubeList.getImage());
            }
        });



        holder.playButton.setOnClickListener(view -> {
            //  imageViewItems.setVisibility(View.GONE);
            //Log.e("playButton", "youtbLink->" + utubeList.getYoutubeLink() + " - " + utubeList.getTitle());
            holder.youTubePlayerView.setVisibility(View.VISIBLE);
            holder.playButton.setVisibility(View.GONE);
            holder.youTubePlayerView.initialize(initializedYouTubePlayer -> initializedYouTubePlayer.addListener(new AbstractYouTubePlayerListener() {
                @Override
                public void onReady() {

                    try {
                        if (utubeList.getYoutubeLink().contains("https://www.youtube.com/")) {
                            strVideoCode = extractYoutubeCode(utubeList.getYoutubeLink());
                            //Log.e("LongLink->", "" + strVideoCode);
                        } else {
                            strVideoCode = extractYoutubeCodeShort(utubeList.getYoutubeLink());
                            //Log.e("ShortLink->", "" + strVideoCode);
                        }
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }


                    //Log.e("onReady", "strVideoCode->" + strVideoCode);
                    initializedYouTubePlayer.loadVideo(strVideoCode, 0);
                    strVideoCode="";
                }
            }), true);
        });
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


    @Override
    public int getItemCount() {
        return listPostUtube.size();
    }

    public int getNumColumns() {
        return this.mNumColumns;
    }

    public void setNumColumns(int numColumns) {
        this.mNumColumns = numColumns;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView mTvFullName, mTvUserType, mTvSchoolName, mTvTitle;
        AppCompatImageView mIvThumbnail;
        AppCompatImageView playButton;
        YouTubePlayerView youTubePlayerView;
        CircleImageView mIvUserPic;

        public MyViewHolder(View itemView) {
            super(itemView);
            mTvFullName = itemView.findViewById(R.id.tvfullname);
            mTvUserType = itemView.findViewById(R.id.tvusertype);
            mTvSchoolName = itemView.findViewById(R.id.tvschoolname);
            mTvTitle = itemView.findViewById(R.id.tvtopicname);
            mIvThumbnail = itemView.findViewById(R.id.ivthumbnail);
            playButton = itemView.findViewById(R.id.btnPlay);
            youTubePlayerView = itemView.findViewById(R.id.youtubeView);
            mIvUserPic = itemView.findViewById(R.id.ivuser);
        }
    }
}
