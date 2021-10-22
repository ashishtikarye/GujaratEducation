package com.gujeducation.gujaratedu.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.gujeducation.R;
import com.gujeducation.gujaratedu.Adapter.CreditorsListAdapter;
import com.gujeducation.gujaratedu.Helper.Connection;
import com.gujeducation.gujaratedu.Helper.Functions;
import com.gujeducation.gujaratedu.Interface.OnResult;
import com.gujeducation.gujaratedu.Model.Creditors;
import com.gujeducation.gujaratedu.ServerAPIs.APIs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;



/**
 * Created by Tikarye Ashish on 19/01/2019.
 */


public class CreditorsScreen extends AppCompatActivity implements OnResult {

    RecyclerView recyclerViewCreditorsList;
    Functions mFunction;
    SharedPreferences mPreference;
    LinearLayoutManager mLayoutManager;
    AppCompatImageView mIvBack;
    CreditorsListAdapter mCreditorsListAdapter;
    AppCompatTextView mTvTitle;
    private ArrayList<Creditors> listArrCreditors = new ArrayList<Creditors>();
    AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_creditors);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }

        mPreference = getSharedPreferences(getResources().getString(R.string.app_name), MODE_PRIVATE);
        mFunction = new Functions(this);
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mIvBack = (AppCompatImageView) findViewById(R.id.ivback);
        recyclerViewCreditorsList = (RecyclerView) findViewById(R.id.recyclerview_creditors);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewCreditorsList.setHasFixedSize(true);
        recyclerViewCreditorsList.setLayoutManager(mLayoutManager);

        if (mFunction.knowInternetOn(this)) {
            APIs.getCreditors(this, this);
        } else {
            mFunction.showInternetAlert(this);
        }

        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreditorsScreen.this, HomeScreen.class);
                startActivity(intent);
                finish();
            }
        });

    }


    @Override
    public void onResult(JSONObject jobjWhole) {
        try {
            if (jobjWhole != null) {
                JSONObject jObj = jobjWhole.optJSONObject(Connection.TAG_DATA);
                String strApiName = jObj.optString("api");
                int strStatus = jObj.optInt("success");
                String strMessage = jObj.optString("message");
                if (strApiName.equalsIgnoreCase("getCreditsMembers")) {
                    if (strStatus != 0) {
                        JSONArray jArraySsaStaff = jObj.getJSONArray("credits");
                        if (jArraySsaStaff.length() > 0) {
                            for (int i = 0; i < jArraySsaStaff.length(); i++) {
                                try {
                                    JSONObject obj = jArraySsaStaff.getJSONObject(i);
                                    //Add in Model
                                    //listArrCartProduct.clear();
                                    listArrCreditors.add(new Creditors
                                            (obj.optInt("creditsMembersId"),
                                                    obj.optString("title"),
                                                    obj.optString("name"),
                                                    obj.optString("designation"),
                                                    obj.optString("description")));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (listArrCreditors.size() != 0) {
                                mCreditorsListAdapter = new CreditorsListAdapter(CreditorsScreen.this,
                                        listArrCreditors);
                                recyclerViewCreditorsList.setAdapter(mCreditorsListAdapter);
                                recyclerViewCreditorsList.setVisibility(View.VISIBLE);
                            } else {
                                recyclerViewCreditorsList.setVisibility(View.GONE);
                            }
                        } else {
                            recyclerViewCreditorsList.setVisibility(View.GONE);
                        }
                    } else {
                        mFunction.ToastUtility(CreditorsScreen.this, strMessage);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
