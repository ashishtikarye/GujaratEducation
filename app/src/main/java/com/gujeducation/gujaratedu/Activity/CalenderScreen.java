package com.gujeducation.gujaratedu.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.gujeducation.R;
import com.gujeducation.gujaratedu.Adapter.CalenderAdapter;
import com.gujeducation.gujaratedu.Helper.Connection;
import com.gujeducation.gujaratedu.Helper.Functions;
import com.gujeducation.gujaratedu.Interface.OnResult;
import com.gujeducation.gujaratedu.Model.Calender;
import com.gujeducation.gujaratedu.ServerAPIs.APIs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CalenderScreen extends AppCompatActivity implements OnResult {

    private AppCompatImageView btnBack;
    RecyclerView recyclerViewCalender;
    LinearLayoutManager mLayoutManager;
    CalenderAdapter mCalenderListAdapter;
    Functions mFunctions;
    Intent intent;
    AdView mAdView;

    private ArrayList<Calender> listArrCalender = new ArrayList<Calender>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);

        mFunctions = new Functions(this);
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        btnBack = (AppCompatImageView)findViewById(R.id.ivback);
        recyclerViewCalender = (RecyclerView) findViewById(R.id.recyclerview_calender);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        recyclerViewCalender.setHasFixedSize(true);
        recyclerViewCalender.setLayoutManager(mLayoutManager);

        if(mFunctions.knowInternetOn(this)){
            APIs.getCalender(CalenderScreen.this,this,mFunctions.getPrefMediumId());
        }
        else {
            Functions.showInternetAlert(this);
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(CalenderScreen.this,HomeScreen.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        intent = new Intent(CalenderScreen.this,HomeScreen.class);
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
                    JSONArray jArrayTextSub = jObj.getJSONArray("Calendar");
                    if (jArrayTextSub.length() > 0) {
                        for (int i = 0; i < jArrayTextSub.length(); i++) {
                            try {
                                JSONObject obj = jArrayTextSub.getJSONObject(i);
                                listArrCalender.add(new Calender(
                                        obj.optInt("calendarId"),
                                        obj.optInt("mediumId"),
                                        obj.optString("calendarName"),
                                        obj.optString("coverImage"),
                                        obj.optString("months"),
                                        obj.optString("image"),
                                        obj.optString("madeBy"),
                                        obj.optString("creditsAndLicence")
                                ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        if (listArrCalender.size() != 0) {
                            mCalenderListAdapter= new CalenderAdapter(CalenderScreen.this, listArrCalender);
                            recyclerViewCalender.setAdapter(mCalenderListAdapter);
                        }
                    }
                } else {
                    mFunctions.ToastUtility(CalenderScreen.this, strMessage);
                    //recyclerViewLanguage.setVisibility(View.GONE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
