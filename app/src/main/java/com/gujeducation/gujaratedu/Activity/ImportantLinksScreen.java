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
import com.gujeducation.gujaratedu.Adapter.ImportantLinksAdapter;
import com.gujeducation.gujaratedu.Helper.Connection;
import com.gujeducation.gujaratedu.Helper.Functions;
import com.gujeducation.gujaratedu.Interface.OnResult;
import com.gujeducation.gujaratedu.Model.ImportantLinks;
import com.gujeducation.gujaratedu.ServerAPIs.APIs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ImportantLinksScreen extends AppCompatActivity implements OnResult {

    RecyclerView recyclerViewImpLinksList;
    LinearLayoutManager mLayoutManager;
    ImportantLinksAdapter mImpLinksListAdapter;
    Functions mFunctions;
    Intent intent;
    private AppCompatImageView btnBack;
    private final ArrayList<ImportantLinks> listArrImpLinks = new ArrayList<ImportantLinks>();
    AdView mAdView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_important_links);
        mFunctions = new Functions(this);
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        btnBack = (AppCompatImageView) findViewById(R.id.ivback);
        recyclerViewImpLinksList = (RecyclerView) findViewById(R.id.recyclerview_implinks);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        recyclerViewImpLinksList.setHasFixedSize(true);
        recyclerViewImpLinksList.setLayoutManager(mLayoutManager);

        if (Functions.knowInternetOn(this)) {
            APIs.getLink(ImportantLinksScreen.this, this, mFunctions.getPrefMediumId());
        } else {
            Functions.showInternetAlert(this);
        }


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(ImportantLinksScreen.this, HomeScreen.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResult(JSONObject jobjWhole) {
        try {
            if (jobjWhole != null) {
                JSONObject jObj = jobjWhole.optJSONObject(Connection.TAG_DATA);
                int strStatus = jObj.optInt("success");
                String strMessage = jObj.optString("message");

                if (strStatus != 0) {
                    JSONArray jArrayTextSub = jObj.getJSONArray("impLink");
                    if (jArrayTextSub.length() > 0) {
                        for (int i = 0; i < jArrayTextSub.length(); i++) {
                            try {
                                JSONObject obj = jArrayTextSub.getJSONObject(i);
                                listArrImpLinks.add(new ImportantLinks(
                                        obj.optInt("impLink_id"),
                                        obj.optInt("mediumId"),
                                        obj.optString("impLink_name"),
                                        obj.optString("By"),
                                        obj.optString("credits"),
                                        obj.optString("image")
                                ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }


                        if (listArrImpLinks.size() != 0) {
                            mImpLinksListAdapter = new ImportantLinksAdapter(ImportantLinksScreen.this,
                                    listArrImpLinks);
                            recyclerViewImpLinksList.setAdapter(mImpLinksListAdapter);
                            recyclerViewImpLinksList.setVisibility(View.VISIBLE);
                        } else {
                            recyclerViewImpLinksList.setVisibility(View.GONE);
                        }

                    }
                } else {
                    Functions.ToastUtility(ImportantLinksScreen.this, strMessage);
                    //recyclerViewLanguage.setVisibility(View.GONE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        intent = new Intent(ImportantLinksScreen.this, HomeScreen.class);
        startActivity(intent);
    }
}
