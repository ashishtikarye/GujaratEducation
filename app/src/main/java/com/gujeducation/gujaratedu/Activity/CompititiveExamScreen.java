package com.gujeducation.gujaratedu.Activity;


import android.content.Intent;
import android.graphics.drawable.PictureDrawable;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestBuilder;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.gujeducation.R;
import com.gujeducation.gujaratedu.Adapter.CompititiveExamsAdapter;
import com.gujeducation.gujaratedu.Helper.Connection;
import com.gujeducation.gujaratedu.Helper.Functions;
import com.gujeducation.gujaratedu.Interface.OnResult;
import com.gujeducation.gujaratedu.Model.CompititveExams;
import com.gujeducation.gujaratedu.ServerAPIs.APIs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CompititiveExamScreen extends AppCompatActivity implements OnResult {
    private final ArrayList<CompititveExams> listArrComptExams = new ArrayList<CompititveExams>();
    Functions mFunctions;
    RecyclerView recyclerViewComptExmasList;
    RecyclerView.LayoutManager mLayoutManager;
    CompititiveExamsAdapter mComptExamsListAdapter;
    Intent intent;
    private AppCompatImageView btnBack;
    AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compititive_exam_screen);
        mFunctions = new Functions(this);
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        btnBack = (AppCompatImageView) findViewById(R.id.ivback);
        recyclerViewComptExmasList = (RecyclerView) findViewById(R.id.recyclerview_compt_exams);
        //mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mLayoutManager = new GridLayoutManager(this, 2);

        recyclerViewComptExmasList.setHasFixedSize(true);
        recyclerViewComptExmasList.setLayoutManager(mLayoutManager);


        if (Functions.knowInternetOn(this)) {
            APIs.getExam(CompititiveExamScreen.this, this, mFunctions.getPrefMediumId());
        } else {
            Functions.showInternetAlert(this);
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(CompititiveExamScreen.this, HomeScreen.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        intent = new Intent(CompititiveExamScreen.this, HomeScreen.class);
        startActivity(intent);
    }


    @Override
    public void onResult(JSONObject jobjWhole) {
        try {
            if (jobjWhole != null) {
                JSONObject jObj = jobjWhole.optJSONObject(Connection.TAG_DATA);
                int strStatus = jObj.optInt("success");
                String strMessage = jObj.optString("message");

                if (strStatus != 0) {
                    JSONArray jArrayTextSub = jObj.getJSONArray("exam");
                    if (jArrayTextSub.length() > 0) {
                        for (int i = 0; i < jArrayTextSub.length(); i++) {
                            try {
                                JSONObject obj = jArrayTextSub.getJSONObject(i);
                                listArrComptExams.add(new CompititveExams(
                                        obj.optInt("examId"),
                                        obj.optInt("mediumId"),
                                        obj.optString("exam_name"),
                                        obj.optString("class"),
                                        obj.optString("image")
                                ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        if (listArrComptExams.size() != 0) {
                            mComptExamsListAdapter = new CompititiveExamsAdapter(CompititiveExamScreen.this, listArrComptExams);
                            recyclerViewComptExmasList.setAdapter(mComptExamsListAdapter);
                            recyclerViewComptExmasList.setVisibility(View.VISIBLE);
                        } else {
                            recyclerViewComptExmasList.setVisibility(View.GONE);
                        }
                    }
                } else {
                    Functions.ToastUtility(CompititiveExamScreen.this, strMessage);
                    //recyclerViewLanguage.setVisibility(View.GONE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}