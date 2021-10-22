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

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.gujeducation.R;
import com.gujeducation.gujaratedu.Adapter.MinamiListAdapter;
import com.gujeducation.gujaratedu.Helper.Connection;
import com.gujeducation.gujaratedu.Helper.Functions;
import com.gujeducation.gujaratedu.Interface.OnResult;
import com.gujeducation.gujaratedu.Model.Minami;
import com.gujeducation.gujaratedu.ServerAPIs.APIs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MinamiDuniaScreen extends AppCompatActivity implements OnResult {
    private final ArrayList<Minami> listArrMinami = new ArrayList<Minami>();
    public InterstitialAd interstitialAd;
    RecyclerView recyclerViewVideoList;
    MinamiListAdapter mMinamiListAdapter;
    LinearLayoutManager mLayoutManager;
    AppCompatTextView mTvTitle;
    Functions mFunctions;
    int examId, papersId, subjectId, chapterId;
    String examName;
    Intent intent;
    AdView mAdView;
    private AppCompatImageView mIvVideoThumbnail, btnBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videolist);
        mFunctions = new Functions(this);
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        interstitialAd = new InterstitialAd(MinamiDuniaScreen.this);
        //interstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        interstitialAd.setAdUnitId("ca-app-pub-6923797063551368/3272831029");
        loadInterstitialAd();

        mTvTitle = findViewById(R.id.header_title);
        btnBack = findViewById(R.id.ivback);


        recyclerViewVideoList = findViewById(R.id.recyclerview_videolist);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewVideoList.setHasFixedSize(true);
        recyclerViewVideoList.setLayoutManager(mLayoutManager);


        mTvTitle.setText(R.string.menu_minami_duniya);

        if (Functions.knowInternetOn(this)) {
            APIs.getMinamiVideo(MinamiDuniaScreen.this, this, mFunctions.getPrefMediumId());

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
                if (strApiName.equalsIgnoreCase("minamiduniya")) {
                    JSONArray jArrayTextSub = jObj.getJSONArray("video");
                    if (jArrayTextSub.length() > 0) {
                        for (int i = 0; i < jArrayTextSub.length(); i++) {
                            try {
                                JSONObject obj = jArrayTextSub.getJSONObject(i);
                                listArrMinami.add(new Minami(
                                        obj.optInt("valueEducationId"),
                                        obj.optInt("mediumId"),
                                        obj.optString("date"),
                                        obj.optString("madeBy"),
                                        obj.optString("creditsAndLicence"),
                                        obj.optString("title"),
                                        obj.optString("thumbnail"),
                                        obj.optString("video")
                                ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        if (listArrMinami.size() != 0) {
                            mMinamiListAdapter = new MinamiListAdapter(MinamiDuniaScreen.this, listArrMinami);
                            recyclerViewVideoList.setAdapter(mMinamiListAdapter);
                        }
                    } else {
                        Functions.ToastUtility(MinamiDuniaScreen.this, strMessage);
                        //recyclerViewLanguage.setVisibility(View.GONE);
                    }
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadInterstitialAd() {
        // Creating  a Ad Request
        AdRequest adRequest = new AdRequest.Builder().build();

        // load Ad with the Request
        interstitialAd.loadAd(adRequest);

        // Showing a simple Toast message to user when an ad is Loading
        //Toast.makeText(HomeScreen.this, "Interstitial Ad is loading ", Toast.LENGTH_LONG).show();
    }


    public void showInterstitialAd() {
        if (interstitialAd.isLoaded()) {
            //showing the ad Interstitial Ad if it is loaded
            interstitialAd.show();

            // Showing a simple Toast message to user when an Interstitial ad is shown to the user
            //Toast.makeText(HomeScreen.this, "Interstitial is loaded and showing ad  ", Toast.LENGTH_LONG).show();
        } else {
            //Load the Interstitial ad if it is not loaded
            loadInterstitialAd();

            // Showing a simple Toast message to user when an ad is not loaded
            //Toast.makeText(HomeScreen.this, "Interstitial Ad is not Loaded ", Toast.LENGTH_LONG).show();
        }
    }
}
