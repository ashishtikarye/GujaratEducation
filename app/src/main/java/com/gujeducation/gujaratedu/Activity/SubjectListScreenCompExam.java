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
import com.gujeducation.gujaratedu.Adapter.SubjectChapterListAdapter;
import com.gujeducation.gujaratedu.Helper.Connection;
import com.gujeducation.gujaratedu.Helper.Functions;
import com.gujeducation.gujaratedu.Interface.OnResult;
import com.gujeducation.gujaratedu.Model.SubjectChapterCompExam;
import com.gujeducation.gujaratedu.ServerAPIs.APIs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SubjectListScreenCompExam extends AppCompatActivity implements OnResult {

    private final ArrayList<SubjectChapterCompExam> listSubjectChapterCompExam = new ArrayList<SubjectChapterCompExam>();
    RecyclerView recyclerviewSubjectchapterlist;
    LinearLayoutManager mLayoutManager;
    LinearLayout mLlExamList, mLlDetails;
    AppCompatTextView mTvTitle;
    SubjectChapterListAdapter mSubjectChapterListAdapter;
    Functions mFunctions;
    int examId, papersId, subjectId;
    String subjectName,examName, examInfoName, examInfoPdf;
    Intent intent;
    private AppCompatImageView btnBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjectchapter);
        mFunctions = new Functions(this);
        Intent intent = getIntent();
        if (intent.hasExtra("exam_id") && intent.hasExtra("papers_id") && intent.hasExtra("subject_id") && intent.hasExtra("subject_name")) {
            examId = intent.getExtras().getInt("exam_id");
            papersId = intent.getExtras().getInt("papers_id");
            subjectId = intent.getExtras().getInt("subject_id");
            subjectName = intent.getExtras().getString("subject_name");
        } else {
            examId = 0;
            papersId = 0;
            subjectId = 0;
            subjectName ="";
        }
        mTvTitle = (AppCompatTextView) findViewById(R.id.header_title);
        btnBack = (AppCompatImageView) findViewById(R.id.ivback);
        mLlExamList = (LinearLayout) findViewById(R.id.lvExamlist);
        mLlDetails = (LinearLayout) findViewById(R.id.lvDetails);


        recyclerviewSubjectchapterlist = (RecyclerView) findViewById(R.id.recyclerview_subjectchapterlist);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        recyclerviewSubjectchapterlist.setHasFixedSize(true);
        recyclerviewSubjectchapterlist.setLayoutManager(mLayoutManager);

       // mTvTitle.setText(examName);
        mTvTitle.setText(subjectName);

        if (Functions.knowInternetOn(this)) {
            APIs.getExamChapter(SubjectListScreenCompExam.this, this, mFunctions.getPrefMediumId(),
                    examId,papersId,subjectId);
            //APIs.getExamInformation(SubjectListScreenCompExam.this, this, mFunctions.getPrefMediumId(), examId);

        } else {
            Functions.showInternetAlert(this);
        }

        mLlExamList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mLlDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //enter details code
                Intent intent = new Intent(SubjectListScreenCompExam.this, SubjectDetailsListScreenCompExam.class);
                intent.putExtra("subject_id", subjectId);
                intent.putExtra("subject_name", subjectName);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, 0);
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
                if (strApiName.equalsIgnoreCase("getExamChapter")) {
                    JSONArray jArrayTextSub = jObj.getJSONArray("exam_chapter");
                    if (jArrayTextSub.length() > 0) {
                        for (int i = 0; i < jArrayTextSub.length(); i++) {
                            try {
                                JSONObject obj = jArrayTextSub.getJSONObject(i);
                                listSubjectChapterCompExam.add(new SubjectChapterCompExam(
                                        obj.optInt("chapterId"),
                                        obj.optInt("mediumId"),
                                        obj.optInt("examId"),
                                        obj.optInt("papersId"),
                                        obj.optInt("subjectId"),
                                        obj.optString("chapterno"),
                                        obj.optString("chapter")
                                ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        //APIs.getExamInformation(PaperListScreen.this, this, mFunctions.getPrefMediumId(), examId);
                        //Log.e("JSONArray", "exam_info-->" + jObj.getJSONArray("exam_info"));

                        if (listSubjectChapterCompExam.size() != 0) {
                            mSubjectChapterListAdapter = new SubjectChapterListAdapter(SubjectListScreenCompExam.this, listSubjectChapterCompExam);
                            recyclerviewSubjectchapterlist.setAdapter(mSubjectChapterListAdapter);
                        }
                    } else {
                        Functions.ToastUtility(SubjectListScreenCompExam.this, strMessage);
                        //recyclerViewLanguage.setVisibility(View.GONE);
                    }
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
