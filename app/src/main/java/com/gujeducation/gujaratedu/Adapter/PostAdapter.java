package com.gujeducation.gujaratedu.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
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
import com.gujeducation.gujaratedu.Helper.Functions;
import com.gujeducation.gujaratedu.Model.Post;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyViewHolder> {

    private final AppCompatActivity activity;
    public boolean error = false;
    ArrayList<Post> listPost = new ArrayList();
    Functions mFunction;
    Dialog dialogPopup, dialogPopupVideo;
    AppCompatImageView mIvPopupImage, mIvClose;
    WebView wvVideo;
    String videoCode, frameVideo;
    String api_key = "AIzaSyCDfVYbuIKGlH4N1DOTYy6YhuQ_aRPSuzk";
    private int mNumColumns = 0;

    public PostAdapter(AppCompatActivity activity, ArrayList<Post> listPost) {
        this.activity = activity;
        this.listPost = listPost;
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
        final Post postList = listPost.get(position);

        try {
           /* menuItemHolder.mTvFullName.setText(postList.getFullname());
            menuItemHolder.mTvSchoolName.setText(postList.getSchoolname());
            Log.e("youtubelinnkAll--", "" + postList.getYoutubeLink());

            if (postList.getRole().equalsIgnoreCase("T")) {
                menuItemHolder.mTvUserType.setText("[Teacher]");
            } else {
                menuItemHolder.mTvUserType.setText("[Student]");
            }
            menuItemHolder.mTvTitle.setText(postList.getTitle());


            if (!postList.getImage().equalsIgnoreCase("NA")) {
                menuItemHolder.mIvThumbnail.setVisibility(View.VISIBLE);
               // menuItemHolder.wbViewYou.setVisibility(View.GONE);
                Glide.with(activity)
                        .load(postList.getImage())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .priority(Priority.IMMEDIATE).dontAnimate()
                        .into(menuItemHolder.mIvThumbnail);
            } else {
                menuItemHolder.mIvThumbnail.setVisibility(View.GONE);
            }

            Log.e("utubelinkk", "-->" + postList.getYoutubeLink());
            if (postList.getYoutubeLink().contains("https://www.youtube.com/")) {
                videoCode = extractYoutubeCode(postList.getYoutubeLink());
                //Log.e("videoCode long->", "" + videoCode);
            } else {
                videoCode = extractYoutubeCodeShort(postList.getYoutubeLink());
                //Log.e("videoCode short->", "" + videoCode);
            }
            Log.e("videoCodefinal->", "" + videoCode);
*/
            /*if (!postList.getYoutubeLink().equalsIgnoreCase("NA")) {
                menuItemHolder.mIvThumbnail.setVisibility(View.GONE);
                menuItemHolder.wbViewYou.setVisibility(View.GONE);//VISIBLE
                menuItemHolder.ytPlayer.setVisibility(View.VISIBLE);


                menuItemHolder.ytPlayer.initialize(
                        initializedYouTubePlayer -> initializedYouTubePlayer.addListener(
                                new AbstractYouTubePlayerListener() {
                                    @Override
                                    public void onReady() {
                                        initializedYouTubePlayer.loadVideo(videoCode, 0);
                                    }
                                }), true);

            } else {
                menuItemHolder.wbViewYou.setVisibility(View.GONE);
            }*/


           /* menuItemHolder.mIvThumbnail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPopup(postList.getImage());
                }
            });

*/
        } catch (Exception e) {
            e.printStackTrace();
        }


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

    public void showVideoPopup(final String vid) {
        Log.e("showVideoPopup", "--" + vid);
        dialogPopupVideo = new Dialog(
                activity, R.style.popupTheme);
        LayoutInflater inflater = (LayoutInflater)
                activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_video_popup, null);
        dialogPopupVideo.setContentView(view); // your custom view.
        dialogPopupVideo.setCancelable(true);
        dialogPopupVideo.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialogPopupVideo.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialogPopupVideo.show();


        wvVideo = view.findViewById(R.id.wvVideoPopup);
        mIvClose = view.findViewById(R.id.ivclose);


        frameVideo = "<html><body><iframe width=\"100%\" height=\"315\" src=\"https://www.youtube.com/embed/" + vid + "\\frameborder=\"0\" allow=\"accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe></body></html>";

        Log.e("frameVid", " -> " + frameVideo);

        wvVideo.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });
        WebSettings webSettings = wvVideo.getSettings();
        webSettings.setJavaScriptEnabled(true);
        wvVideo.loadData(frameVideo, "text/html", "utf-8");


        mIvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogPopupVideo.dismiss();
            }
        });


    }

    @Override
    public int getItemCount() {
        return listPost.size();
    }

    public int getNumColumns() {
        return this.mNumColumns;
    }

    public void setNumColumns(int numColumns) {
        this.mNumColumns = numColumns;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        /*private final AppCompatTextView mTvFullName;
        private final AppCompatTextView mTvUserType;
        private final AppCompatTextView mTvSchoolName;
        private final AppCompatTextView mTvTitle;
        private final AppCompatImageView mIvThumbnail;*/
        //private AppCompatImageView mIvBoardMemberPic;
        //private final WebView wbViewYou;
        //private final YouTubePlayerView ytPlayer;

        public MyViewHolder(View itemView) {
            super(itemView);
           /* mTvFullName = itemView.findViewById(R.id.tvfullname);
            mTvUserType = itemView.findViewById(R.id.tvusertype);
            mTvSchoolName = itemView.findViewById(R.id.tvschoolname);
            mTvTitle = itemView.findViewById(R.id.tvtopicname);
            mIvThumbnail = itemView.findViewById(R.id.ivthumbnail);*/
            /*wbViewYou = itemView.findViewById(R.id.wbViewYou);
            ytPlayer = itemView.findViewById(R.id.ytPlayer);*/
        }
    }
}
