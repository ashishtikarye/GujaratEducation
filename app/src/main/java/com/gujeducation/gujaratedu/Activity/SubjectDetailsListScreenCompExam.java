package com.gujeducation.gujaratedu.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gujeducation.R;
import com.gujeducation.gujaratedu.Adapter.SubjectDetailsAdapter;
import com.gujeducation.gujaratedu.Helper.Connection;
import com.gujeducation.gujaratedu.Helper.Functions;
import com.gujeducation.gujaratedu.Interface.OnResult;
import com.gujeducation.gujaratedu.Model.ExamChapterOptionList;
import com.gujeducation.gujaratedu.Model.SubjectChapterCompExam;
import com.gujeducation.gujaratedu.ServerAPIs.APIs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SubjectDetailsListScreenCompExam extends AppCompatActivity implements OnResult {

    private final ArrayList<ExamChapterOptionList> listSubjectDetails = new ArrayList<ExamChapterOptionList>();
    RecyclerView recyclerviewSubjectDetailslist;
    LinearLayoutManager mLayoutManager;
    AppCompatTextView mTvTitle;
    SubjectDetailsAdapter mSubjectDetailsAdapter;
    Functions mFunctions;
    LinearLayout mLvSubject,mLvDetails;
    int  subjectId;
    String subjectName;
    Intent intent;
    private AppCompatImageView btnBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjectdetailslist);
        mFunctions = new Functions(this);
        Intent intent = getIntent();
        if (intent.hasExtra("subject_id") && intent.hasExtra("subject_name")) {
            subjectId = intent.getExtras().getInt("subject_id");
            subjectName = intent.getExtras().getString("subject_name");
        } else {
            subjectId = 0;
            subjectName ="";
        }
        mTvTitle = (AppCompatTextView) findViewById(R.id.header_title);
        btnBack = (AppCompatImageView) findViewById(R.id.ivback);
        mLvSubject = (LinearLayout) findViewById(R.id.lvSubject);
        mLvDetails = (LinearLayout) findViewById(R.id.lvDetails);


        recyclerviewSubjectDetailslist = (RecyclerView) findViewById(R.id.recyclerview_subjectdetailslist);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        recyclerviewSubjectDetailslist.setHasFixedSize(true);
        recyclerviewSubjectDetailslist.setLayoutManager(mLayoutManager);

       // mTvTitle.setText(examName);
        mTvTitle.setText(subjectName);

        if (Functions.knowInternetOn(this)) {
            APIs.getExamSubjectOptionList(SubjectDetailsListScreenCompExam.this, this, mFunctions.getPrefMediumId(),subjectId);
        } else {
            Functions.showInternetAlert(this);
        }


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mLvSubject.setOnClickListener(new View.OnClickListener() {
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
                    JSONArray jArrayTextSub = jObj.getJSONArray("examSubjectOptionList");
                    if (jArrayTextSub.length() > 0) {
                        for (int i = 0; i < jArrayTextSub.length(); i++) {
                            try {
                                JSONObject obj = jArrayTextSub.getJSONObject(i);
                                listSubjectDetails.add(new ExamChapterOptionList(obj.optString("option")));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        if (listSubjectDetails.size() != 0) {
                            mSubjectDetailsAdapter = new SubjectDetailsAdapter(SubjectDetailsListScreenCompExam.this, listSubjectDetails);
                            recyclerviewSubjectDetailslist.setAdapter(mSubjectDetailsAdapter);
                        }
                    } else {
                        Functions.ToastUtility(SubjectDetailsListScreenCompExam.this, strMessage);
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
