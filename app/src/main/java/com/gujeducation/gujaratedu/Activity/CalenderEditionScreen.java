package com.gujeducation.gujaratedu.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.gujeducation.R;
import com.gujeducation.gujaratedu.Adapter.CalenderAdapter;
import com.gujeducation.gujaratedu.Adapter.CalenderEditionAdapter;
import com.gujeducation.gujaratedu.Helper.Connection;
import com.gujeducation.gujaratedu.Helper.Functions;
import com.gujeducation.gujaratedu.Interface.OnResult;
import com.gujeducation.gujaratedu.Model.Calender;
import com.gujeducation.gujaratedu.Model.CalenderEdition;
import com.gujeducation.gujaratedu.ServerAPIs.APIs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CalenderEditionScreen extends AppCompatActivity implements OnResult {

    private AppCompatImageView btnBack;
    RecyclerView recyclerViewCalender;
    LinearLayoutManager mLayoutManager;
    CalenderEditionAdapter mCalenderEditionAdapter;
    Functions mFunctions;
    Intent intent;
    int CalenderId=0;
    public InterstitialAd interstitialAd;

    private ArrayList<CalenderEdition> listArrCalenderEd = new ArrayList<CalenderEdition>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);
        intent = getIntent();
        try {

            if (intent.hasExtra("calenderId")) {
                CalenderId = intent.getExtras().getInt("calenderId");
            } else {
                CalenderId = 0;
            }
        } catch (Exception e) {
            Log.e("Null Object exception", "Name");
        }

        //Log.e("CalenderId", "-->" + CalenderId);
        mFunctions = new Functions(this);
        btnBack = (AppCompatImageView)findViewById(R.id.ivback);
        recyclerViewCalender = (RecyclerView) findViewById(R.id.recyclerview_calender);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        recyclerViewCalender.setHasFixedSize(true);
        recyclerViewCalender.setLayoutManager(mLayoutManager);

        interstitialAd = new InterstitialAd(CalenderEditionScreen.this);
        //interstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        interstitialAd.setAdUnitId("ca-app-pub-6923797063551368/3272831029");

        List<String> testDeviceIds = new ArrayList<String>();
        testDeviceIds.add("8A898BC8824C996E9320D350D4AF1F10");
        testDeviceIds.add("FFB848305EE41D5DB1D6C522BFB75BEE");
        testDeviceIds.add("105122E1816DB58B97D2DF2E357E7A37");
        testDeviceIds.add("ED6E76F6E947CC1B01B01524B255999E");

        RequestConfiguration configuration =
                new RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build();
        MobileAds.setRequestConfiguration(configuration);
        if(mFunctions.knowInternetOn(this)){
            APIs.getCalenderEdition(CalenderEditionScreen.this,this,CalenderId);
        }
        else {
            Functions.showInternetAlert(this);
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(CalenderEditionScreen.this,HomeScreen.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        intent = new Intent(CalenderEditionScreen.this,HomeScreen.class);
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
                    JSONArray jArrayTextSub = jObj.getJSONArray("CalendarEdition");
                    if (jArrayTextSub.length() > 0) {
                        for (int i = 0; i < jArrayTextSub.length(); i++) {
                            try {
                                JSONObject obj = jArrayTextSub.getJSONObject(i);
                                listArrCalenderEd.add(new CalenderEdition(
                                        obj.optInt("calendarEditionId"),
                                        obj.optInt("calendarId"),
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
                        if (listArrCalenderEd.size() != 0) {
                            mCalenderEditionAdapter= new CalenderEditionAdapter(CalenderEditionScreen.this, listArrCalenderEd);
                            recyclerViewCalender.setAdapter(mCalenderEditionAdapter);
                        }
                    }
                } else {
                    mFunctions.ToastUtility(CalenderEditionScreen.this, strMessage);
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
            Toast.makeText(CalenderEditionScreen.this, "Interstitial is loaded and showing ad  ", Toast.LENGTH_SHORT).show();
        } else {
            //Load the Interstitial ad if it is not loaded
            loadInterstitialAd();

            // Showing a simple Toast message to user when an ad is not loaded
            //Toast.makeText(CalenderEditionScreen.this, "Interstitial Ad is not Loaded ", Toast.LENGTH_SHORT).show();
        }
    }

    public void loadInterstitialAd() {
        // Creating  a Ad Request
        AdRequest adRequest = new AdRequest.Builder().build();
        // load Ad with the Request
        interstitialAd.loadAd(adRequest);
        adRequest.isTestDevice(CalenderEditionScreen.this);

        // Showing a simple Toast message to user when an ad is Loading
        //Toast.makeText(CalenderEditionScreen.this, "Interstitial Ad is loading ", Toast.LENGTH_SHORT).show();
    }
}
