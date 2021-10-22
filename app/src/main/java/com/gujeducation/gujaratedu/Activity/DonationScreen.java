package com.gujeducation.gujaratedu.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.LoadAdError;
import com.gujeducation.R;
import com.gujeducation.gujaratedu.Adapter.DonationAdapter;
import com.gujeducation.gujaratedu.Helper.Connection;
import com.gujeducation.gujaratedu.Helper.Functions;
import com.gujeducation.gujaratedu.Interface.OnResult;
import com.gujeducation.gujaratedu.Model.Donation;
import com.gujeducation.gujaratedu.ServerAPIs.APIs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DonationScreen extends AppCompatActivity implements OnResult {

    private final ArrayList<Donation> listArrDonation = new ArrayList<Donation>();
    //creating Object of InterstitialAd
    public InterstitialAd interstitialAd;
    RecyclerView recyclerViewDonation;
    LinearLayoutManager mLayoutManager;
    DonationAdapter mDonationListAdapter;
    Functions mFunctions;
    Intent intent;
    AppCompatTextView mTvHeaderTitle;
    AdView mAdView;
    private AppCompatImageView btnBack;
    //creating Object of Buttons

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magazine);

        mFunctions = new Functions(this);


        /*if (TextUtils.isEmpty(getString(R.string.banner_home_footer))) {
            Toast.makeText(getApplicationContext(), "Please mention your Banner Ad ID in strings.xml", Toast.LENGTH_LONG).show();
            return;
        }*/

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        interstitialAd = new InterstitialAd(DonationScreen.this);
        //interstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        interstitialAd.setAdUnitId("ca-app-pub-6923797063551368/3272831029");
        loadInterstitialAd();

        mTvHeaderTitle = findViewById(R.id.header_title);
        btnBack = findViewById(R.id.ivback);
        recyclerViewDonation = findViewById(R.id.recyclerview_magazine);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mTvHeaderTitle.setText(R.string.donation);
        recyclerViewDonation.setHasFixedSize(true);
        recyclerViewDonation.setLayoutManager(mLayoutManager);
        //Log.e("DonationScreen", "MediumId->" + mFunctions.getPrefMediumId());

        if (Functions.knowInternetOn(this)) {
            APIs.getDonation(DonationScreen.this, this, mFunctions.getPrefMediumId());
        } else {
            Functions.showInternetAlert(this);
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(DonationScreen.this, HomeScreen.class);
                startActivity(intent);
            }
        });

        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {

                // Showing a simple Toast message to user when an ad is loaded
                //Toast.makeText(DonationScreen.this, "Interstitial Ad is Loaded", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                interstitialAd = null;

                // Showing a simple Toast message to user when and ad is failed to load
                //
                // Toast.makeText(DonationScreen.this, "Interstitial Ad Failed to Load ", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAdOpened() {

                // Showing a simple Toast message to user when an ad opens and overlay and covers the device screen
                //Toast.makeText(DonationScreen.this, "Interstitial Ad Opened", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAdClicked() {

                // Showing a simple Toast message to user when a user clicked the ad
               // Toast.makeText(DonationScreen.this, "Interstitial Ad Clicked", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAdLeftApplication() {

                // Showing a simple Toast message to user when the user left the application
                //Toast.makeText(DonationScreen.this, "Interstitial Ad Left the Application", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAdClosed() {

                //loading new interstitialAd when the ad is closed
                loadInterstitialAd();

                // Showing a simple Toast message to user when the user interacted with ad and got the other app and then return to the app again
               // Toast.makeText(DonationScreen.this, "Interstitial Ad is Closed", Toast.LENGTH_LONG).show();

            }
        });



    }


    public void loadInterstitialAd() {
        // Creating  a Ad Request
        AdRequest adRequest = new AdRequest.Builder().build();

        // load Ad with the Request
        interstitialAd.loadAd(adRequest);

        // Showing a simple Toast message to user when an ad is Loading
        //Toast.makeText(DonationScreen.this, "Interstitial Ad is loading ", Toast.LENGTH_LONG).show();
    }


    public void showInterstitialAd() {
        if (interstitialAd.isLoaded()) {
            //showing the ad Interstitial Ad if it is loaded
            interstitialAd.show();

            // Showing a simple Toast message to user when an Interstitial ad is shown to the user
            //Toast.makeText(DonationScreen.this, "Interstitial is loaded and showing ad  ", Toast.LENGTH_LONG).show();
        } else {
            //Load the Interstitial ad if it is not loaded
            loadInterstitialAd();

            // Showing a simple Toast message to user when an ad is not loaded
            //Toast.makeText(DonationScreen.this, "Interstitial Ad is not Loaded ", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        intent = new Intent(DonationScreen.this, HomeScreen.class);
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
                    JSONArray jArrayTextSub = jObj.getJSONArray("donation");
                    if (jArrayTextSub.length() > 0) {
                        for (int i = 0; i < jArrayTextSub.length(); i++) {
                            try {
                                JSONObject obj = jArrayTextSub.getJSONObject(i);
                                listArrDonation.add(new Donation(
                                        obj.optInt("donationId"),
                                        obj.optInt("mediumId"),
                                        obj.optString("donationName"),
                                        obj.optString("madeBy"),
                                        obj.optString("paymentLink"),
                                        obj.optString("date"),
                                        obj.optString("image")
                                ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        if (listArrDonation.size() != 0) {
                            mDonationListAdapter = new DonationAdapter(DonationScreen.this, listArrDonation);
                            recyclerViewDonation.setAdapter(mDonationListAdapter);
                        }
                    }
                } else {
                    Functions.ToastUtility(DonationScreen.this, strMessage);
                    //recyclerViewLanguage.setVisibility(View.GONE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
