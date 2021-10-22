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
import com.gujeducation.gujaratedu.Adapter.OldPaperListAdapter;
import com.gujeducation.gujaratedu.Adapter.PaperListAdapter;
import com.gujeducation.gujaratedu.Helper.Connection;
import com.gujeducation.gujaratedu.Helper.Functions;
import com.gujeducation.gujaratedu.Interface.OnResult;
import com.gujeducation.gujaratedu.Model.OldPaper;
import com.gujeducation.gujaratedu.Model.Papers;
import com.gujeducation.gujaratedu.ServerAPIs.APIs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class OldPaperListScreen extends AppCompatActivity implements OnResult {

    private final ArrayList<OldPaper> listArrOldPaper = new ArrayList<OldPaper>();
    RecyclerView recyclerViewOldPaperList;
    LinearLayoutManager mLayoutManager;
    LinearLayout mLlExamList, mLlDetails;
    AppCompatTextView mTvTitle;
    OldPaperListAdapter mOldPaperListAdapter;
    Functions mFunctions;
    int examId;

    Intent intent;
    private AppCompatImageView btnBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oldpaperlist);
        mFunctions = new Functions(this);

        mTvTitle = (AppCompatTextView) findViewById(R.id.header_title);
        btnBack = (AppCompatImageView) findViewById(R.id.ivback);
        mLlExamList = (LinearLayout) findViewById(R.id.lvExamlist);
        mLlDetails = (LinearLayout) findViewById(R.id.lvDetails);


        recyclerViewOldPaperList = (RecyclerView) findViewById(R.id.recyclerview_oldpapers);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        recyclerViewOldPaperList.setHasFixedSize(true);
        recyclerViewOldPaperList.setLayoutManager(mLayoutManager);


        if (Functions.knowInternetOn(this)) {
            APIs.getExamOldPapers(OldPaperListScreen.this, this, mFunctions.getPrefMediumId(), mFunctions.getPrefExamId(),
                    mFunctions.getPrefPapersId(),mFunctions.getPrefSubjectId());
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
                String strApiName = jObj.optString("api");
                String strMessage = jObj.optString("message");
                if (strApiName.equalsIgnoreCase("getExamOldPapers")) {
                    JSONArray jArrayTextSub = jObj.getJSONArray("exam_oldpapers");
                    if (jArrayTextSub.length() > 0) {
                        for (int i = 0; i < jArrayTextSub.length(); i++) {
                            try {
                                JSONObject obj = jArrayTextSub.getJSONObject(i);
                                listArrOldPaper.add(new OldPaper(
                                        obj.optInt("oldpaper_Id"),
                                        obj.optInt("mediumId"),
                                        obj.optInt("examId"),
                                        obj.optInt("papersId"),
                                        obj.optInt("subjectId"),
                                        obj.optString("year"),
                                        obj.optString("image"),
                                        obj.optString("pdfName")
                                ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        //APIs.getExamInformation(PaperListScreen.this, this, mFunctions.getPrefMediumId(), examId);
                        //Log.e("JSONArray", "exam_info-->" + jObj.getJSONArray("exam_info"));

                        if (listArrOldPaper.size() != 0) {
                            mOldPaperListAdapter = new OldPaperListAdapter(OldPaperListScreen.this, listArrOldPaper);
                            recyclerViewOldPaperList.setAdapter(mOldPaperListAdapter);
                        }
                    } else {
                        Functions.ToastUtility(OldPaperListScreen.this, strMessage);
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
