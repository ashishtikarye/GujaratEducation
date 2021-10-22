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

import com.gujeducation.R;
import com.gujeducation.gujaratedu.Adapter.ImportantLinksAdapter;
import com.gujeducation.gujaratedu.Adapter.SubLinkAdapter;
import com.gujeducation.gujaratedu.Helper.Connection;
import com.gujeducation.gujaratedu.Helper.Functions;
import com.gujeducation.gujaratedu.Interface.OnResult;
import com.gujeducation.gujaratedu.Model.SubLink;
import com.gujeducation.gujaratedu.ServerAPIs.APIs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SubLinkScreen extends AppCompatActivity implements OnResult {

    private final ArrayList<SubLink> listArrSubLink = new ArrayList<SubLink>();
    RecyclerView recyclerViewSubLinkList;
    LinearLayoutManager mLayoutManager;
    SubLinkAdapter mSubLinkAdapter;
    Functions mFunctions;
    Intent intent;
    int impLinkId;
    String impLinkName;
    private AppCompatImageView btnBack;
    AppCompatTextView mTvTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sublink);
        mFunctions = new Functions(this);

        intent = getIntent();
        if (intent.hasExtra("impLinkId") && intent.hasExtra("impLinkName")) {
            impLinkId = intent.getExtras().getInt("impLinkId");
            impLinkName = intent.getExtras().getString("impLinkName");
        } else {
            impLinkId = 0;
            impLinkName = "SubLink";
        }


        mTvTitle = (AppCompatTextView) findViewById(R.id.header_title);
        btnBack = (AppCompatImageView) findViewById(R.id.ivback);
        recyclerViewSubLinkList = (RecyclerView) findViewById(R.id.recyclerview_sublinks);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        recyclerViewSubLinkList.setHasFixedSize(true);
        recyclerViewSubLinkList.setLayoutManager(mLayoutManager);

        mTvTitle.setText(impLinkName);
        if (Functions.knowInternetOn(this)) {
            APIs.getSubLink(SubLinkScreen.this, this, impLinkId);
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
                String strMessage = jObj.optString("message");

                if (strStatus != 0) {
                    JSONArray jArrayTextSub = jObj.getJSONArray("subLink");
                    if (jArrayTextSub.length() > 0) {
                        for (int i = 0; i < jArrayTextSub.length(); i++) {
                            try {
                                JSONObject obj = jArrayTextSub.getJSONObject(i);
                                listArrSubLink.add(new SubLink(
                                        obj.optInt("subLink_id"),
                                        obj.optInt("impLink_id"),
                                        obj.optString("subLink_name"),
                                        obj.optString("pdf")
                                ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }


                        if (listArrSubLink.size() != 0) {
                            mSubLinkAdapter = new SubLinkAdapter(SubLinkScreen.this,
                                    listArrSubLink);
                            recyclerViewSubLinkList.setAdapter(mSubLinkAdapter);
                            recyclerViewSubLinkList.setVisibility(View.VISIBLE);
                        } else {
                            recyclerViewSubLinkList.setVisibility(View.GONE);
                        }

                    }
                } else {
                    Functions.ToastUtility(SubLinkScreen.this, strMessage);
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
        intent = new Intent(SubLinkScreen.this, HomeScreen.class);
        startActivity(intent);
    }
}
