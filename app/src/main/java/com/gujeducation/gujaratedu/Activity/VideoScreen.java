package com.gujeducation.gujaratedu.Activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.gujeducation.R;
import com.gujeducation.gujaratedu.Helper.FullScreenVideoView;
import com.gujeducation.gujaratedu.Helper.Functions;

public class VideoScreen extends AppCompatActivity {
    private static final String PLAYBACK_TIME = "play_time";
    AppCompatTextView mTvTitle;
    Functions mFunctions;
    String videoLink, videoTitle, videoThumbnail;
    Intent intent;
    FullScreenVideoView videoView;
    MediaController mediacontroller;
    private AppCompatImageView mIvVideoThumbnail, mIvPlay, btnBack;
    private int mCurrentPosition = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        mFunctions = new Functions(this);
        Intent intent = getIntent();
        if (intent.hasExtra("videoTitle") && intent.hasExtra("videoLink")) {
            videoTitle = intent.getExtras().getString("videoTitle");
            videoLink = intent.getExtras().getString("videoLink");
        } else {
            videoTitle = "";
            videoLink = "";
        }
        mTvTitle = findViewById(R.id.header_title);
        btnBack = findViewById(R.id.ivback);
        videoView = (FullScreenVideoView)findViewById(R.id.vdoView);
        /*DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        android.widget.LinearLayout.LayoutParams params = (android.widget.LinearLayout.LayoutParams) videoView.getLayoutParams();
        params.width = metrics.widthPixels;
        params.height = metrics.heightPixels;
        params.leftMargin = 0;
        videoView.setLayoutParams(params);*/

        Log.e("VideoScreen", "videoLink-->" + videoLink);
        Log.e("VideoScreen", "videoTitle-->" + videoTitle);


        //mTvTitle.setText(videoTitle);

        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(PLAYBACK_TIME);
        }
        mediacontroller = new MediaController(this);
        mediacontroller.setMediaPlayer(videoView);
        videoView.setMediaController(mediacontroller);




        /*mIvPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(VideoScreen.this, "mIvPlay", Toast.LENGTH_SHORT).show();
                watchYoutubeVideo(videoLink);
            }
        });*/
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Load the media each time onStart() is called.
        initializePlayer();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the current playback position (in milliseconds) to the
        // instance state bundle.
        outState.putInt(PLAYBACK_TIME, videoView.getCurrentPosition());
    }

    private void initializePlayer() {
        // Show the "Buffering..." message while the video loads.
        //    mBufferingTextView.setVisibility(VideoView.VISIBLE);

        // Buffer and decode the video sample.
        Uri videoUri = getMedia(videoLink);
        videoView.setVideoURI(videoUri);

        // Listener for onPrepared() event (runs after the media is prepared).
        videoView.setOnPreparedListener(
                new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {

                        // Hide buffering message.
                        //mBufferingTextView.setVisibility(VideoView.INVISIBLE);

                        // Restore saved position, if available.
                        if (mCurrentPosition > 0) {
                            videoView.seekTo(mCurrentPosition);
                        } else {
                            // Skipping to 1 shows the first frame of the video.
                            videoView.seekTo(1);
                        }

                        // Start playing!
                        videoView.start();
                    }
                });

        // Listener for onCompletion() event (runs after media has finished
        // playing).
        videoView.setOnCompletionListener(
                new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {

                        Toast.makeText(VideoScreen.this,
                                "hi",
                                Toast.LENGTH_SHORT).show();

                        // Return the video position to the start.
                        videoView.seekTo(0);
                    }
                });
    }

    private void releasePlayer() {
        videoView.stopPlayback();
    }

    private Uri getMedia(String mediaName) {
        if (URLUtil.isValidUrl(mediaName)) {
            // Media name is an external URL.
            return Uri.parse(mediaName);
        } else {

            // you can also put a video file in raw package and get file from there as shown below

            return Uri.parse("android.resource://" + getPackageName() +
                    "/raw/" + mediaName);


        }
    }
}
