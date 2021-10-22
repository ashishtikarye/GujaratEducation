package com.gujeducation.gujaratedu.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gujeducation.R;
import com.gujeducation.gujaratedu.Adapter.PaperListAdapter;
import com.gujeducation.gujaratedu.Helper.Connection;
import com.gujeducation.gujaratedu.Helper.Functions;
import com.gujeducation.gujaratedu.Interface.OnResult;
import com.gujeducation.gujaratedu.Model.Papers;
import com.gujeducation.gujaratedu.ServerAPIs.APIs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PaperListScreen extends AppCompatActivity implements OnResult {

    private final ArrayList<Papers> listArrPapers = new ArrayList<Papers>();
    RecyclerView recyclerViewPaperList;
    LinearLayoutManager mLayoutManager;
    LinearLayout mLlExamList, mLlDetails;
    AppCompatTextView mTvTitle;
    PaperListAdapter mPaperListAdapter;
    Functions mFunctions;
    int examId;
    String examName;
    Intent intent;
    private AppCompatImageView btnBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paperlist);
        mFunctions = new Functions(this);
        Intent intent = getIntent();
        if (intent.hasExtra("exam_id") && intent.hasExtra("exam_name")) {
            examId = intent.getExtras().getInt("exam_id");
            examName = intent.getExtras().getString("exam_name");
        } else {
            examId = 0;
            examName = "";
        }
        mTvTitle = findViewById(R.id.header_title);
        btnBack = findViewById(R.id.ivback);
        mLlExamList = findViewById(R.id.lvExamlist);
        mLlDetails = findViewById(R.id.lvDetails);


        recyclerViewPaperList = findViewById(R.id.recyclerview_papetlist);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        recyclerViewPaperList.setHasFixedSize(true);
        recyclerViewPaperList.setLayoutManager(mLayoutManager);

        mTvTitle.setText(examName);

        if (Functions.knowInternetOn(this)) {
            APIs.getExamPaper(PaperListScreen.this, this, mFunctions.getPrefMediumId(), examId);
            //APIs.getExamInformation(PaperListScreen.this, this, mFunctions.getPrefMediumId(), examId);

        } else {
            Functions.showInternetAlert(this);
        }

       /* mLlExamList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/

        mLlDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PaperListScreen.this, PaperExamListDetailsScreen.class);
                intent.putExtra("exam_id", examId);
                intent.putExtra("exam_name", examName);
                startActivity(intent);
              //  overridePendingTransition(R.anim.slide_in_x, 0);

                /*Intent intent = new Intent(PaperListScreen.this, PdfScreen.class);
                intent.putExtra("Name", examInfoName);
                intent.putExtra("PDF", examInfoPdf);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, 0);*/
            }
        });

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
                String strApiName = jObj.optString("api");
                String strMessage = jObj.optString("message");
                if (strApiName.equalsIgnoreCase("getExamPaper")) {
                    JSONArray jArrayTextSub = jObj.getJSONArray("papers");
                    if (jArrayTextSub.length() > 0) {
                        for (int i = 0; i < jArrayTextSub.length(); i++) {
                            try {
                                JSONObject obj = jArrayTextSub.getJSONObject(i);
                                listArrPapers.add(new Papers(
                                        obj.optInt("papersId"),
                                        obj.optInt("mediumId"),
                                        obj.optInt("examId"),
                                        obj.optString("paper_name")
                                ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        //APIs.getExamInformation(PaperListScreen.this, this, mFunctions.getPrefMediumId(), examId);
                        //Log.e("JSONArray", "exam_info-->" + jObj.getJSONArray("exam_info"));

                        if (listArrPapers.size() != 0) {
                            mPaperListAdapter = new PaperListAdapter(PaperListScreen.this, listArrPapers);
                            recyclerViewPaperList.setAdapter(mPaperListAdapter);
                        }
                    } else {
                        Functions.ToastUtility(PaperListScreen.this, strMessage);
                        //recyclerViewLanguage.setVisibility(View.GONE);
                    }
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
