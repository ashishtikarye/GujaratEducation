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
import com.gujeducation.gujaratedu.Adapter.VideoListAdapter;
import com.gujeducation.gujaratedu.Helper.Connection;
import com.gujeducation.gujaratedu.Helper.Functions;
import com.gujeducation.gujaratedu.Interface.OnResult;
import com.gujeducation.gujaratedu.Model.Video;
import com.gujeducation.gujaratedu.ServerAPIs.APIs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class VideoListScreen extends AppCompatActivity implements OnResult {
    private final ArrayList<Video> listArrVideos = new ArrayList<Video>();
    RecyclerView recyclerViewVideoList;
    VideoListAdapter mVideoListAdapter;
    LinearLayoutManager mLayoutManager;
    AppCompatTextView mTvTitle;
    Functions mFunctions;
    int examId, papersId, subjectId, chapterId;
    String examName;
    Intent intent;
    private AppCompatImageView mIvVideoThumbnail, btnBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videolist);
        mFunctions = new Functions(this);
        Intent intent = getIntent();
        if (intent.hasExtra("examId") && intent.hasExtra("papersId") && intent.hasExtra("subjectId")
                && intent.hasExtra("chapterId")) {
            examId = intent.getExtras().getInt("examId");
            papersId = intent.getExtras().getInt("papersId");
            subjectId = intent.getExtras().getInt("subjectId");
            chapterId = intent.getExtras().getInt("chapterId");
        } else {
            examId = 0;
            papersId = 0;
            subjectId = 0;
            chapterId = 0;
            //examName = "";
        }
        mTvTitle = findViewById(R.id.header_title);
        btnBack = findViewById(R.id.ivback);


        recyclerViewVideoList = findViewById(R.id.recyclerview_videolist);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewVideoList.setHasFixedSize(true);
        recyclerViewVideoList.setLayoutManager(mLayoutManager);




        mTvTitle.setText("Video");

        if (Functions.knowInternetOn(this)) {
            APIs.getExamStudyVideo(VideoListScreen.this, this, mFunctions.getPrefMediumId(), examId, papersId, subjectId, chapterId);

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
                if (strApiName.equalsIgnoreCase("getExamStudyVideo")) {
                    JSONArray jArrayTextSub = jObj.getJSONArray("exam_video");
                    if (jArrayTextSub.length() > 0) {
                        for (int i = 0; i < jArrayTextSub.length(); i++) {
                            try {
                                JSONObject obj = jArrayTextSub.getJSONObject(i);
                                listArrVideos.add(new Video(
                                        obj.optInt("studyvideo_Id"),
                                        obj.optInt("mediumId"),
                                        obj.optInt("examId"),
                                        obj.optInt("papersId"),
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
                            mVideoListAdapter = new VideoListAdapter(VideoListScreen.this, listArrVideos);
                            recyclerViewVideoList.setAdapter(mVideoListAdapter);
                        }
                    } else {
                        Functions.ToastUtility(VideoListScreen.this, strMessage);
                        //recyclerViewLanguage.setVisibility(View.GONE);
                    }
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
