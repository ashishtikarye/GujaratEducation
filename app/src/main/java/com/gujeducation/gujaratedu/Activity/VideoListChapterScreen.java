package com.gujeducation.gujaratedu.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gujeducation.R;
import com.gujeducation.gujaratedu.Adapter.ChapterOptionAdapter;
import com.gujeducation.gujaratedu.Adapter.VideoListAdapter;
import com.gujeducation.gujaratedu.Adapter.VideoListChapterAdapter;
import com.gujeducation.gujaratedu.Helper.Connection;
import com.gujeducation.gujaratedu.Helper.Functions;
import com.gujeducation.gujaratedu.Interface.OnResult;
import com.gujeducation.gujaratedu.Model.Video;
import com.gujeducation.gujaratedu.Model.VideoChapter;
import com.gujeducation.gujaratedu.ServerAPIs.APIs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class VideoListChapterScreen extends AppCompatActivity implements OnResult {
    private final ArrayList<VideoChapter> listArrVideos = new ArrayList<VideoChapter>();
    RecyclerView recyclerViewVideoList;
    VideoListChapterAdapter mVideoListChapterAdapter;
    LinearLayoutManager mLayoutManager;
    AppCompatTextView mTvTitle;
    Functions mFunctions;
    int section, semester,standardId,subjectId, chapterId;
    private AppCompatImageView mIvVideoThumbnail, btnBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videolist);
        mFunctions = new Functions(this);
        Intent intent = getIntent();
        if (intent.hasExtra("section") && intent.hasExtra("semester") && intent.hasExtra("standardId")
                && intent.hasExtra("subjectId") && intent.hasExtra("chapterId")) {
            section = intent.getExtras().getInt("section");
            semester = intent.getExtras().getInt("semester");
            standardId = intent.getExtras().getInt("standardId");
            subjectId = intent.getExtras().getInt("subjectId");
            chapterId = intent.getExtras().getInt("chapterId");
        } else {
            section = 0;
            semester = 0;
            standardId = 0;
            subjectId = 0;
            chapterId = 0;
        }
        mTvTitle = findViewById(R.id.header_title);
        btnBack = findViewById(R.id.ivback);


        recyclerViewVideoList = findViewById(R.id.recyclerview_videolist);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewVideoList.setHasFixedSize(true);
        recyclerViewVideoList.setLayoutManager(mLayoutManager);




        mTvTitle.setText("Video");

        if (Functions.knowInternetOn(this)) {
            APIs.getStudyVideo(VideoListChapterScreen.this,this,
                    mFunctions.getPrefMediumId(),
                    section, semester,
                    standardId, subjectId,
                    chapterId);
        } else {
            Functions.showInternetAlert(this);
        }


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    public void onResult(JSONObject jobjWhole) {
        try {
            if (jobjWhole != null) {
                JSONObject jObj = jobjWhole.optJSONObject(Connection.TAG_DATA);
                int strStatus = jObj.optInt("success");
                String strApiName = jObj.optString("api");
                String strMessage = jObj.optString("message");
                if (strApiName.equalsIgnoreCase("getStudyVideo")) {
                    JSONArray jArrayTextSub = jObj.getJSONArray("study_video");
                    if (jArrayTextSub.length() > 0) {
                        for (int i = 0; i < jArrayTextSub.length(); i++) {
                            try {
                                JSONObject obj = jArrayTextSub.getJSONObject(i);
                                listArrVideos.add(new VideoChapter(
                                        obj.optInt("studyvideo_Id"),
                                        obj.optInt("mediumId"),
                                        obj.optInt("subjectId"),
                                        obj.optInt("chapterId"),
                                        obj.optString("madeBy"),
                                        obj.optString("credits"),
                                        obj.optString("title"),
                                        obj.optString("thumbnail"),
                                        obj.optString("video")
                                ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        if (listArrVideos.size() != 0) {
                            mVideoListChapterAdapter = new VideoListChapterAdapter(VideoListChapterScreen.this, listArrVideos);
                            recyclerViewVideoList.setAdapter(mVideoListChapterAdapter);
                        }
                    } else {
                        Functions.ToastUtility(VideoListChapterScreen.this, strMessage);
                        //recyclerViewLanguage.setVisibility(View.GONE);
                    }
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
