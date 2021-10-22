package com.gujeducation.gujaratedu.Activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gujeducation.R;
import com.gujeducation.gujaratedu.Adapter.LanguageAdapter;
import com.gujeducation.gujaratedu.Helper.Connection;
import com.gujeducation.gujaratedu.Helper.Functions;
import com.gujeducation.gujaratedu.Interface.OnResult;
import com.gujeducation.gujaratedu.Model.Language;
import com.gujeducation.gujaratedu.ServerAPIs.APIs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.gujeducation.gujaratedu.Helper.CommonMethod.ManageScreenView;


public class LanguageScreen extends AppCompatActivity implements OnResult {
    RelativeLayout mRlGujarati, mRlEnglish, mRlHindi, mRlMarathi, mRlOdiya, mRLTelugu, mRLUrdu;
    Functions mFunction;
    public static String mediumId_guest = "";
    private Context mcontext;
    Intent intent;
    private ArrayList<Language> listArrLanguage = new ArrayList<Language>();
    RecyclerView recyclerViewLanguage;
    LanguageAdapter mLanguageAdapter;
    LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
        ManageScreenView(this);
        mcontext = this;
        mFunction = new Functions(this);
        recyclerViewLanguage = findViewById(R.id.recyclerview_language);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        recyclerViewLanguage.setHasFixedSize(true);
        recyclerViewLanguage.setLayoutManager(mLayoutManager);

        if (Functions.knowInternetOn(this)) {
            APIs.getLanguage(LanguageScreen.this,this);
        } else {
            Functions.showInternetAlert(this);
        }

    }

    @Override
    public void onResult(JSONObject jobjWhole) {
        try {
            if (jobjWhole != null) {
                JSONObject jObj = jobjWhole.optJSONObject(Connection.TAG_DATA);
                int strStatus = jObj.optInt("success");
                String strMessage = jObj.optString("message");

                if (strStatus != 0) {
                    JSONArray jArrayTextSub = jObj.getJSONArray("language");
                    if (jArrayTextSub.length() > 0) {
                        for (int i = 0; i < jArrayTextSub.length(); i++) {
                            try {
                                JSONObject obj = jArrayTextSub.getJSONObject(i);
                                listArrLanguage.add(new Language(
                                        obj.optInt("mediumId"),
                                        obj.optString("medium"),
                                        obj.optInt("status"),
                                        obj.optInt("isdelete")
                                ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        if (listArrLanguage.size() != 0) {
                            mLanguageAdapter= new LanguageAdapter(LanguageScreen.this, listArrLanguage);
                            recyclerViewLanguage.setAdapter(mLanguageAdapter);
                        }
                    }
                } else {
                    mFunction.ToastUtility(LanguageScreen.this, strMessage);
                    //recyclerViewLanguage.setVisibility(View.GONE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}