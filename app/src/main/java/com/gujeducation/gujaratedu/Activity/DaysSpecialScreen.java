package com.gujeducation.gujaratedu.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.LoadAdError;
import com.gujeducation.R;
import com.gujeducation.gujaratedu.Adapter.DaysSpecialListAdapter;
import com.gujeducation.gujaratedu.Helper.Connection;
import com.gujeducation.gujaratedu.Helper.Functions;
import com.gujeducation.gujaratedu.Interface.OnResult;
import com.gujeducation.gujaratedu.Model.DaySpecial;
import com.gujeducation.gujaratedu.ServerAPIs.APIs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DaysSpecialScreen extends AppCompatActivity implements OnResult {

    private AppCompatImageView btnBack;
    RecyclerView recyclerViewDaysSpecialList;
    LinearLayoutManager mLayoutManager;
    DaysSpecialListAdapter mDaysSpecialListAdapter;
    Functions mFunctions;
    public InterstitialAd interstitialAd;
    private ArrayList<DaySpecial> listArrDaysSpecial = new ArrayList<DaySpecial>();
    AdView mAdView;
    Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daysspecial);
        mFunctions = new Functions(this);
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        interstitialAd = new InterstitialAd(DaysSpecialScreen.this);
        //interstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        interstitialAd.setAdUnitId("ca-app-pub-6923797063551368/3272831029");
        //loadInterstitialAd();

        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {

                // Showing a simple Toast message to user when an ad is loaded
             //   Toast.makeText(DaysSpecialScreen.this, "Interstitial Ad is Loaded", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {

                // Showing a simple Toast message to user when and ad is failed to load
            //    Toast.makeText(DaysSpecialScreen.this, "Interstitial Ad Failed to Load ", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAdOpened() {
                // Showing a simple Toast message to user when an ad opens and overlay and covers the device screen
            //    Toast.makeText(DaysSpecialScreen.this, "Interstitial Ad Opened", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAdClicked() {

                // Showing a simple Toast message to user when a user clicked the ad
             //   Toast.makeText(DaysSpecialScreen.this, "Interstitial Ad Clicked", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAdLeftApplication() {

                // Showing a simple Toast message to user when the user left the application
             //   Toast.makeText(DaysSpecialScreen.this, "Interstitial Ad Left the Application", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAdClosed() {

                //loading new interstitialAd when the ad is closed
                //loadInterstitialAd();

                // Showing a simple Toast message to user when the user interacted with ad and got the other app and then return to the app again
                //Toast.makeText(DaysSpecialScreen.this, "Interstitial Ad is Closed", Toast.LENGTH_LONG).show();

            }
        });


        btnBack = (AppCompatImageView)findViewById(R.id.ivback);
        recyclerViewDaysSpecialList = (RecyclerView) findViewById(R.id.recyclerview_daysspecial);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        recyclerViewDaysSpecialList.setHasFixedSize(true);
        recyclerViewDaysSpecialList.setLayoutManager(mLayoutManager);

        if(mFunctions.knowInternetOn(this)){
            APIs.getDaySpecial(DaysSpecialScreen.this,this,mFunctions.getPrefMediumId());
        }
        else {
            Functions.showInternetAlert(this);
        }

        /*listArrDaysSpecial.add(new DaysSpecial(1,getString(R.string.daysspecial_title),"10/10/2020"));
        listArrDaysSpecial.add(new DaysSpecial(2,"Demo","20/10/2020"));
        if(listArrDaysSpecial.size() != 0){
            mDaysSpecialListAdapter = new DaysSpecialListAdapter(DaysSpecialScreen.this,
                    listArrDaysSpecial);
            recyclerViewDaysSpecialList.setAdapter(mDaysSpecialListAdapter);
            recyclerViewDaysSpecialList.setVisibility(View.VISIBLE);
        }
        else
        {
            recyclerViewDaysSpecialList.setVisibility(View.GONE);
        }*/


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(DaysSpecialScreen.this,HomeScreen.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        intent = new Intent(DaysSpecialScreen.this,HomeScreen.class);
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
                    JSONArray jArrayTextSub = jObj.getJSONArray("daySpecial");
                    if (jArrayTextSub.length() > 0) {
                        for (int i = 0; i < jArrayTextSub.length(); i++) {
                            try {
                                JSONObject obj = jArrayTextSub.getJSONObject(i);
                                listArrDaysSpecial.add(new DaySpecial(
                                        obj.optInt("dayspecial_id"),
                                        obj.optInt("mediumId"),
                                        obj.optString("title"),
                                        obj.optString("sub_title"),
                                        obj.optString("description"),
                                        obj.optString("image"),
                                        obj.optString("date")
                                ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        if (listArrDaysSpecial.size() != 0) {
                            mDaysSpecialListAdapter= new DaysSpecialListAdapter(DaysSpecialScreen.this, listArrDaysSpecial);
                            recyclerViewDaysSpecialList.setAdapter(mDaysSpecialListAdapter);
                        }
                    }
                } else {
                    mFunctions.ToastUtility(DaysSpecialScreen.this, strMessage);
                    //recyclerViewLanguage.setVisibility(View.GONE);
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

