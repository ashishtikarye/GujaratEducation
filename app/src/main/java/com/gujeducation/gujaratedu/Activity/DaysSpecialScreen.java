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
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
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
import java.util.List;

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

        List<String> testDeviceIds = new ArrayList<String>();
        testDeviceIds.add("8A898BC8824C996E9320D350D4AF1F10");
        testDeviceIds.add("FFB848305EE41D5DB1D6C522BFB75BEE");
        testDeviceIds.add("105122E1816DB58B97D2DF2E357E7A37");
        testDeviceIds.add("ED6E76F6E947CC1B01B01524B255999E");

        RequestConfiguration configuration =
                new RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build();
        MobileAds.setRequestConfiguration(configuration);

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


    public void showInterstitialAd() {
        if (interstitialAd.isLoaded()) {
            //showing the ad Interstitial Ad if it is loaded
            interstitialAd.show();

            // Showing a simple Toast message to user when an Interstitial ad is shown to the user
            Toast.makeText(DaysSpecialScreen.this, "Interstitial is loaded and showing ad  ", Toast.LENGTH_SHORT).show();
        } else {
            //Load the Interstitial ad if it is not loaded
            loadInterstitialAd();

            // Showing a simple Toast message to user when an ad is not loaded
            //Toast.makeText(DaysSpecialScreen.this, "Interstitial Ad is not Loaded ", Toast.LENGTH_SHORT).show();
        }
    }

    public void loadInterstitialAd() {
        // Creating  a Ad Request
        AdRequest adRequest = new AdRequest.Builder().build();
        // load Ad with the Request
        interstitialAd.loadAd(adRequest);
        adRequest.isTestDevice(DaysSpecialScreen.this);

        // Showing a simple Toast message to user when an ad is Loading
        //Toast.makeText(DaysSpecialScreen.this, "Interstitial Ad is loading ", Toast.LENGTH_SHORT).show();
    }
}

