package com.gujeducation.gujaratedu.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class CalenderEditionScreen extends AppCompatActivity implements OnResult {

    private AppCompatImageView btnBack;
    RecyclerView recyclerViewCalender;
    LinearLayoutManager mLayoutManager;
    CalenderEditionAdapter mCalenderEditionAdapter;
    Functions mFunctions;
    Intent intent;
    int CalenderId=0;

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
}
