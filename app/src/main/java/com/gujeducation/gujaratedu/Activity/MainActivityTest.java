package com.gujeducation.gujaratedu.Activity;

import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gujeducation.R;
import com.gujeducation.gujaratedu.Adapter.YoutubeRecyclerAdapterTest;
import com.gujeducation.gujaratedu.Model.YoutubeVideoTest;

import java.util.ArrayList;


public class MainActivityTest extends AppCompatActivity {

    RecyclerView recyclerViewFeed;
    YoutubeRecyclerAdapterTest mRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintest);

        recyclerViewFeed = findViewById(R.id.recyclerViewFeed);
        ArrayList<YoutubeVideoTest> youtubeVideos = prepareList();
        mRecyclerAdapter = new YoutubeRecyclerAdapterTest(youtubeVideos);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewFeed.setLayoutManager(mLayoutManager);
        recyclerViewFeed.setItemAnimator(new DefaultItemAnimator());
        recyclerViewFeed.setAdapter(mRecyclerAdapter);
    }

    private ArrayList<YoutubeVideoTest> prepareList() {
        ArrayList mYoutubeVideo = new ArrayList();
        // add first item
        YoutubeVideoTest video1 = new YoutubeVideoTest();
        video1.setId(1l);
        video1.setImageUrl("https://i.ytimg.com/vi/zI-Pux4uaqM/maxresdefault.jpg");
        video1.setTitle("Thugs Of Hindostan - Official Trailer | Amitabh Bachchan | Aamir Khan | Katrina Kaif | Fatima");
        video1.setVideoId("qFkNATtc3mc");
        mYoutubeVideo.add(video1);

        // add second item
        YoutubeVideoTest video2 = new YoutubeVideoTest();
        video2.setId(2l);
        video2.setImageUrl("https://i.ytimg.com/vi/8ZK_S-46KwE/maxresdefault.jpg");
        video2.setTitle("Colors for Children to Learning with Baby Fun Play with Color Balls Dolphin Slider Toy Set Kids Edu");
        video2.setVideoId("e8B0AzmXPV8");
        mYoutubeVideo.add(video2);

        // add third item
        YoutubeVideoTest video3 = new YoutubeVideoTest();
        video3.setId(3l);
        video3.setImageUrl("https://i.ytimg.com/vi/8czMWUH7vW4/hqdefault.jpg");
        video3.setTitle("Air Hostess Accepts Marriage Proposal Mid-Air, Airline Fires her.");
        video3.setVideoId("TyHHkT4Yreo");
        mYoutubeVideo.add(video3);

        // add four item
        YoutubeVideoTest video4 = new YoutubeVideoTest();
        video4.setId(4l);
        video4.setImageUrl("https://i.ytimg.com/vi/YrQVYEb6hcc/maxresdefault.jpg");
        video4.setTitle("EXPERIMENT Glowing 1000 degree METAL BALL vs Gunpowder (100 grams)");
        video4.setVideoId("xZCkBNXUacQ");
        mYoutubeVideo.add(video4);

        // add four item
        YoutubeVideoTest video5 = new YoutubeVideoTest();
        video5.setId(5l);
        video5.setImageUrl("https://i.ytimg.com/vi/S84Fuo2rGoY/maxresdefault.jpg");
        video5.setTitle("What happened after Jauhar of Padmavati");
        video5.setVideoId("S84Fuo2rGoY");
        mYoutubeVideo.add(video5);

        mYoutubeVideo.add(video1);
        mYoutubeVideo.add(video2);
        mYoutubeVideo.add(video3);
        mYoutubeVideo.add(video4);
        return mYoutubeVideo;
    }
}
