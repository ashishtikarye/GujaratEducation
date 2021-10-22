package com.gujeducation.gujaratedu.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gujeducation.R;
import com.gujeducation.gujaratedu.Adapter.EssayListAdapter;
import com.gujeducation.gujaratedu.Helper.Connection;
import com.gujeducation.gujaratedu.Helper.Functions;
import com.gujeducation.gujaratedu.Interface.OnResult;
import com.gujeducation.gujaratedu.Model.Essay;
import com.gujeducation.gujaratedu.Model.OldPaperSubject;
import com.gujeducation.gujaratedu.ServerAPIs.APIs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class EssayListScreen extends AppCompatActivity implements OnResult {

    private final ArrayList<Essay> listArrEssay = new ArrayList<Essay>();
    RecyclerView recyclerViewOldPaperList;
    LinearLayoutManager mLayoutManager;
    AppCompatTextView mTvTitle;
    EssayListAdapter mEssayListAdapter;
    Functions mFunctions;
    String optionType;
    Integer SubjectID;
    private AppCompatImageView btnBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oldpaperlist);
        mFunctions = new Functions(this);

        mTvTitle = (AppCompatTextView) findViewById(R.id.header_title);
        btnBack = (AppCompatImageView) findViewById(R.id.ivback);

        Intent intent = getIntent();
        if (intent.hasExtra("optionType") && intent.hasExtra("subjectId")) {
            optionType = intent.getExtras().getString("optionType");
            SubjectID = intent.getExtras().getInt("subjectId");
        } else {
            optionType = "";
            SubjectID = 0;
        }

        mTvTitle.setText(optionType);


        recyclerViewOldPaperList = (RecyclerView) findViewById(R.id.recyclerview_oldpapers);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        recyclerViewOldPaperList.setHasFixedSize(true);
        recyclerViewOldPaperList.setLayoutManager(mLayoutManager);


        if (Functions.knowInternetOn(this)) {
            APIs.getOldPprEssay(EssayListScreen.this, this, mFunctions.getPrefMediumId(),SubjectID,
                    optionType);
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
                JSONArray jArrayTextSub = jObj.getJSONArray("resdata");
                if (jArrayTextSub.length() > 0) {
                    for (int i = 0; i < jArrayTextSub.length(); i++) {
                        try {
                            JSONObject obj = jArrayTextSub.getJSONObject(i);
                            listArrEssay.add(new Essay(
                                    obj.optInt("essayId"),
                                    obj.optInt("semesterId"),
                                    obj.optInt("sectionId"),
                                    obj.optInt("mediumId"),
                                    obj.optInt("standardId"),
                                    obj.optInt("subjectId"),
                                    obj.optString("title"),
                                    obj.optString("essay_body")
                            ));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    //APIs.getExamInformation(PaperListScreen.this, this, mFunctions.getPrefMediumId(), examId);
                    //Log.e("JSONArray", "exam_info-->" + jObj.getJSONArray("exam_info"));

                    if (listArrEssay.size() != 0) {
                        mEssayListAdapter = new EssayListAdapter(EssayListScreen.this, listArrEssay);
                        recyclerViewOldPaperList.setAdapter(mEssayListAdapter);
                    }
                } else {
                    Functions.ToastUtility(EssayListScreen.this, strMessage);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
