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
import com.gujeducation.gujaratedu.Adapter.ExamInfoPaperAdapter;
import com.gujeducation.gujaratedu.Adapter.SubjectDetailsAdapter;
import com.gujeducation.gujaratedu.Helper.Connection;
import com.gujeducation.gujaratedu.Helper.Functions;
import com.gujeducation.gujaratedu.Interface.OnResult;
import com.gujeducation.gujaratedu.Model.ExamChapterOptionList;
import com.gujeducation.gujaratedu.Model.ExamInfoPaper;
import com.gujeducation.gujaratedu.Model.Papers;
import com.gujeducation.gujaratedu.ServerAPIs.APIs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PaperExamListDetailsScreen extends AppCompatActivity implements OnResult {

    private final ArrayList<ExamInfoPaper> listExamInfo = new ArrayList<ExamInfoPaper>();
    RecyclerView recyclerviewExamInfolist;
    LinearLayoutManager mLayoutManager;
    AppCompatTextView mTvTitle;
    ExamInfoPaperAdapter mExamInfoPaperAdapter;
    Functions mFunctions;
    LinearLayout mLvPapers,mLvDetails;
    int  exam_id;
    String exam_name;
    private AppCompatImageView btnBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paperexaminfolist);
        mFunctions = new Functions(this);
        Intent intent = getIntent();
        if (intent.hasExtra("exam_id") && intent.hasExtra("exam_name")) {
            exam_id = intent.getExtras().getInt("exam_id");
            exam_name = intent.getExtras().getString("exam_name");
        } else {
            exam_id = 0;
            exam_name = "";
        }

        Log.e("ppr_exam_id","->"+exam_id);
        mTvTitle = (AppCompatTextView) findViewById(R.id.header_title);
        btnBack = (AppCompatImageView) findViewById(R.id.ivback);
        mLvPapers = (LinearLayout) findViewById(R.id.lvPapers);
        mLvDetails = (LinearLayout) findViewById(R.id.lvDetails);


        recyclerviewExamInfolist = (RecyclerView) findViewById(R.id.recyclerview_paperexaminfolist);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        recyclerviewExamInfolist.setHasFixedSize(true);
        recyclerviewExamInfolist.setLayoutManager(mLayoutManager);

       mTvTitle.setText(exam_name);
        if (Functions.knowInternetOn(this)) {
            APIs.getExamInformation(PaperExamListDetailsScreen.this, this, mFunctions.getPrefMediumId(), exam_id);
        } else {
            Functions.showInternetAlert(this);
        }

        mTvTitle.setText(exam_name);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mLvPapers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

       /* mLvDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/
    }


    @Override
    public void onResult(JSONObject jobjWhole) {
        try {
            if (jobjWhole != null) {
                JSONObject jObj = jobjWhole.optJSONObject(Connection.TAG_DATA);
                int strStatus = jObj.optInt("success");
                String strApiName = jObj.optString("api");
                String strMessage = jObj.optString("message");
                    JSONArray jArrayTextSub = jObj.getJSONArray("exam_info");
                    if (jArrayTextSub.length() > 0) {
                        for (int i = 0; i < jArrayTextSub.length(); i++) {
                            try {
                                JSONObject obj = jArrayTextSub.getJSONObject(i);
                                listExamInfo.add(new ExamInfoPaper(
                                        obj.optInt("examInfoId"),
                                        obj.optInt("mediumId"),
                                        obj.optInt("examId"),
                                        obj.optString("topic"),
                                        obj.optString("pdf"),
                                        obj.optString("pdf_name")
                                ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        if (listExamInfo.size() != 0) {
                            mExamInfoPaperAdapter = new ExamInfoPaperAdapter(PaperExamListDetailsScreen.this, listExamInfo);
                            recyclerviewExamInfolist.setAdapter(mExamInfoPaperAdapter);
                        }
                    } else {
                        Functions.ToastUtility(PaperExamListDetailsScreen.this, strMessage);
                        //recyclerViewLanguage.setVisibility(View.GONE);
                    }

               /*if(strApiName.equalsIgnoreCase("getExamInfo")) {
                    JSONArray jArrayTextSub = jObj.getJSONArray("exam_info");
                    JSONObject obj = jArrayTextSub.getJSONObject(0);
                    examInfoName = obj.optString("topic");
                    examInfoPdf = obj.optString("pdf");
                }*/


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
