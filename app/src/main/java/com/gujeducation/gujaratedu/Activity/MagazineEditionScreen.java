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
import com.google.android.gms.ads.InterstitialAd;
import com.gujeducation.R;
import com.gujeducation.gujaratedu.Adapter.MagazineEditionAdapter;
import com.gujeducation.gujaratedu.Helper.Connection;
import com.gujeducation.gujaratedu.Helper.Functions;
import com.gujeducation.gujaratedu.Interface.OnResult;
import com.gujeducation.gujaratedu.Model.MagazineEdition;
import com.gujeducation.gujaratedu.ServerAPIs.APIs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MagazineEditionScreen extends AppCompatActivity implements OnResult {

    private final ArrayList<MagazineEdition> listArrMagazineEditor = new ArrayList<MagazineEdition>();
    RecyclerView recyclerViewMagazineEdition;
    LinearLayoutManager mLayoutManager;
    MagazineEditionAdapter mMagazineEditionAdapter;
    Functions mFunctions;
    Intent intent;
    int MagazineId = 0;
    AppCompatTextView mTvHeaderTitle;
    private AppCompatImageView btnBack;
    AdView mAdView;
    public InterstitialAd interstitialAd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magazine);

        intent = getIntent();
        try {

            if (intent.hasExtra("_MagazineId")) {
                MagazineId = intent.getExtras().getInt("_MagazineId");
            } else {
                MagazineId = 0;
            }
        } catch (Exception e) {
            Log.e("Null Object exception", "Name");
        }

        //Log.e("MagazineId", "-->" + MagazineId);

        mFunctions = new Functions(this);

        interstitialAd = new InterstitialAd(MagazineEditionScreen.this);
        //interstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        interstitialAd.setAdUnitId("ca-app-pub-6923797063551368/3272831029");
        loadInterstitialAd();

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mTvHeaderTitle = findViewById(R.id.header_title);
        mTvHeaderTitle.setText(R.string.home_magazines);
        btnBack = findViewById(R.id.ivback);
        recyclerViewMagazineEdition = findViewById(R.id.recyclerview_magazine);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mTvHeaderTitle.setText("Magazines Editions");

        recyclerViewMagazineEdition.setHasFixedSize(true);
        recyclerViewMagazineEdition.setLayoutManager(mLayoutManager);

        if (Functions.knowInternetOn(this)) {
            APIs.getMagazineEdition(MagazineEditionScreen.this, this, mFunctions.getPrefMediumId(), MagazineId);
        } else {
            Functions.showInternetAlert(this);
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MagazineEditionScreen.this, MagazineScreen.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        intent = new Intent(MagazineEditionScreen.this, HomeScreen.class);
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
                    JSONArray jArrayTextSub = jObj.getJSONArray("magazine");
                    if (jArrayTextSub.length() > 0) {
                        for (int i = 0; i < jArrayTextSub.length(); i++) {
                            try {
                                JSONObject obj = jArrayTextSub.getJSONObject(i);
                                listArrMagazineEditor.add(new MagazineEdition(
                                        obj.optInt("magazineEditionId"),
                                        obj.optInt("magazineId"),
                                        obj.optString("months"),
                                        obj.optString("madeBy"),
                                        obj.optString("creditsAndLicence"),
                                        obj.optString("pdf"),
                                        obj.optString("pdf_name"),
                                        obj.optString("coverImage")
                                ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        if (listArrMagazineEditor.size() != 0) {
                            mMagazineEditionAdapter = new MagazineEditionAdapter(MagazineEditionScreen.this, listArrMagazineEditor);
                            recyclerViewMagazineEdition.setAdapter(mMagazineEditionAdapter);
                        }
                    }
                } else {
                    Functions.ToastUtility(MagazineEditionScreen.this, strMessage);
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
