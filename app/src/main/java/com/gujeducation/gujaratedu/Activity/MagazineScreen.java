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
import com.gujeducation.R;
import com.gujeducation.gujaratedu.Adapter.MagazineAdapter;
import com.gujeducation.gujaratedu.Helper.Connection;
import com.gujeducation.gujaratedu.Helper.Functions;
import com.gujeducation.gujaratedu.Interface.OnResult;
import com.gujeducation.gujaratedu.Model.Magazine;
import com.gujeducation.gujaratedu.ServerAPIs.APIs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MagazineScreen extends AppCompatActivity implements OnResult {

    private AppCompatImageView btnBack;
    RecyclerView recyclerViewMagazine;
    LinearLayoutManager mLayoutManager;
    MagazineAdapter mMagazineListAdapter;
    Functions mFunctions;
    Intent intent;
    AppCompatTextView mTvHeaderTitle;
    private ArrayList<Magazine> listArrMagazine = new ArrayList<Magazine>();
    AdView mAdView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magazine);

        mFunctions = new Functions(this);
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mTvHeaderTitle = findViewById(R.id.header_title);
        btnBack = (AppCompatImageView)findViewById(R.id.ivback);
        recyclerViewMagazine = (RecyclerView) findViewById(R.id.recyclerview_magazine);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mTvHeaderTitle.setText("Magazines");
        recyclerViewMagazine.setHasFixedSize(true);
        recyclerViewMagazine.setLayoutManager(mLayoutManager);

        if(mFunctions.knowInternetOn(this)){
            APIs.getMagazine(MagazineScreen.this,this,mFunctions.getPrefMediumId());
        }
        else {
            Functions.showInternetAlert(this);
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MagazineScreen.this,HomeScreen.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        intent = new Intent(MagazineScreen.this,HomeScreen.class);
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
                                listArrMagazine.add(new Magazine(
                                        obj.optInt("magazineId"),
                                        obj.optInt("mediumId"),
                                        obj.optString("title"),
                                        obj.optString("coverImage"),
                                        obj.optString("month"),
                                        obj.optString("image"),
                                        obj.optString("date"),
                                        obj.optString("madeBy"),
                                        obj.optString("creditAndLicence")
                                ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        if (listArrMagazine.size() != 0) {
                            mMagazineListAdapter= new MagazineAdapter(MagazineScreen.this, listArrMagazine);
                            recyclerViewMagazine.setAdapter(mMagazineListAdapter);
                        }
                    }
                } else {
                    mFunctions.ToastUtility(MagazineScreen.this, strMessage);
                    //recyclerViewLanguage.setVisibility(View.GONE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
