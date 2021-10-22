package com.gujeducation.gujaratedu.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.gujeducation.R;
import com.gujeducation.gujaratedu.Adapter.PrePrimaryTextbookAdapter;
import com.gujeducation.gujaratedu.Helper.Connection;
import com.gujeducation.gujaratedu.Helper.Functions;
import com.gujeducation.gujaratedu.Interface.OnResult;
import com.gujeducation.gujaratedu.Model.PrePrimaryTextbook;
import com.gujeducation.gujaratedu.ServerAPIs.APIs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PrePrimaryTextScreen extends AppCompatActivity implements OnResult {

    private AppCompatImageView btnBack;
    RecyclerView recyclerViewPrePrimaryTextbookList;
    LinearLayoutManager mLayoutManager;
    PrePrimaryTextbookAdapter mPrePrimaryTextbookAdapter;
    Functions mFunctions;
    private ArrayList<PrePrimaryTextbook> listArrPrePrimaryTextbook = new ArrayList<PrePrimaryTextbook>();
    Intent intent;
    int chapterId;
    String chapterName;
    AppCompatTextView mTvTitle;
    AdView mAdView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preprimary_activity);
        mFunctions = new Functions(this);

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        Intent intent = getIntent();
        if (intent.hasExtra("chapterId") && intent.hasExtra("chapterName")) {
            chapterId = intent.getExtras().getInt("chapterId");
            chapterName = intent.getExtras().getString("chapterName");
        } else {
            chapterId = 0;
            chapterName = "Unknown";

        }

        btnBack = (AppCompatImageView) findViewById(R.id.ivback);
        mTvTitle = (AppCompatTextView) findViewById(R.id.header_title);
        recyclerViewPrePrimaryTextbookList = (RecyclerView) findViewById(R.id.recyclerview_preprimary_textbook);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        recyclerViewPrePrimaryTextbookList.setHasFixedSize(true);
        recyclerViewPrePrimaryTextbookList.setLayoutManager(mLayoutManager);

        mTvTitle.setText(chapterName);
        /*Log.e("PrePrimaryTextScreen","MediumId-"+mFunctions.getPrefMediumId()+
                "\nchapterName-"+chapterName+
                "\nChapterId-"+chapterId+
                " \nSemesterId-"+mFunctions.getSemester()+
                " \nStandardId-"+mFunctions.getStandardId()+
                " \nSubjectId-"+mFunctions.getSubjectId()+
                " \nSectionId-"+mFunctions.getSection());*/



        if (mFunctions.knowInternetOn(this)) {
            APIs.getPreprimaryTextbook(PrePrimaryTextScreen.this, this,mFunctions.getPrefMediumId(),
                    mFunctions.getSemester(),
                    mFunctions.getSection(),
                    mFunctions.getStandardId(),
                    mFunctions.getSubjectId(),
                    mFunctions.getChapterId());
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
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onResult(JSONObject jobjWhole) {
        try {
            if (jobjWhole != null) {
                JSONObject jObj = jobjWhole.optJSONObject(Connection.TAG_DATA);
                int strStatus = jObj.optInt("success");
                String strMessage = jObj.optString("message");

                if (strStatus != 0) {
                    JSONArray jArrayTextSub = jObj.getJSONArray("preprimarytextbook");
                    if (jArrayTextSub.length() > 0) {
                        for (int i = 0; i < jArrayTextSub.length(); i++) {
                            try {
                                JSONObject obj = jArrayTextSub.getJSONObject(i);
                                listArrPrePrimaryTextbook.add(new PrePrimaryTextbook(
                                        obj.optInt("id"),
                                        obj.optInt("chapterId"),
                                        obj.optString("medium_title"),
                                        obj.optString("common_title"),
                                        obj.optString("image"),
                                        obj.optString("medium_audio"),
                                        obj.optString("common_audio")
                                ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        if (listArrPrePrimaryTextbook.size() != 0) {
                            mPrePrimaryTextbookAdapter = new PrePrimaryTextbookAdapter(PrePrimaryTextScreen.this, listArrPrePrimaryTextbook);
                            recyclerViewPrePrimaryTextbookList.setAdapter(mPrePrimaryTextbookAdapter);
                        }
                    }
                } else {
                    mFunctions.ToastUtility(PrePrimaryTextScreen.this, strMessage);
                    //recyclerViewLanguage.setVisibility(View.GONE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
