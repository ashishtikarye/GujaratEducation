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
import com.gujeducation.gujaratedu.Adapter.EvalutionAdapter;
import com.gujeducation.gujaratedu.Adapter.NewsCircularAdapter;
import com.gujeducation.gujaratedu.Helper.Connection;
import com.gujeducation.gujaratedu.Helper.Functions;
import com.gujeducation.gujaratedu.Interface.OnResult;
import com.gujeducation.gujaratedu.Model.Evalution;
import com.gujeducation.gujaratedu.Model.NewsCircular;
import com.gujeducation.gujaratedu.ServerAPIs.APIs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class EvalutionScreen extends AppCompatActivity implements OnResult {

    private AppCompatImageView btnBack;
    RecyclerView recyclerViewEvalution;
    LinearLayoutManager mLayoutManager;
    EvalutionAdapter mEvalutionListAdapter;
    Functions mFunctions;
    private ArrayList<Evalution> listArrEvalution = new ArrayList<Evalution>();
    AppCompatTextView mTitle;
    Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sublink);
        mFunctions = new Functions(this);

        btnBack = (AppCompatImageView)findViewById(R.id.ivback);
        recyclerViewEvalution = (RecyclerView) findViewById(R.id.recyclerview_sublinks);
        mTitle = (AppCompatTextView) findViewById(R.id.header_title);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        recyclerViewEvalution.setHasFixedSize(true);
        recyclerViewEvalution.setLayoutManager(mLayoutManager);
        recyclerViewEvalution.setLayoutManager(mLayoutManager);

        if(mFunctions.knowInternetOn(this)){
            APIs.getEvaluation(EvalutionScreen.this,this,mFunctions.getPrefMediumId(),mFunctions.getSubjectId(),"Evalution");
        }
        else {
            Functions.showInternetAlert(this);
        }
        mTitle.setText("Evaluation");

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(EvalutionScreen.this,HomeScreen.class);
                startActivity(intent);
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
                    JSONArray jArrayTextSub = jObj.getJSONArray("resdata");
                    if (jArrayTextSub.length() > 0) {
                        for (int i = 0; i < jArrayTextSub.length(); i++) {
                            try {
                                JSONObject obj = jArrayTextSub.getJSONObject(i);
                                listArrEvalution.add(new Evalution(
                                        obj.optInt("evalutionId"),
                                        obj.optInt("semesterId"),
                                        obj.optInt("sectionId"),
                                        obj.optInt("mediumId"),
                                        obj.optInt("standardId"),
                                        obj.optInt("subjectId"),
                                        obj.optString("patrak"),
                                        obj.optString("pdf"),
                                        obj.optString("pdf_name"),
                                        obj.optString("date")
                                ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        if (listArrEvalution.size() != 0) {
                            mEvalutionListAdapter= new EvalutionAdapter(EvalutionScreen.this, listArrEvalution);
                            recyclerViewEvalution.setAdapter(mEvalutionListAdapter);
                        }
                    }
                } else {
                    mFunctions.ToastUtility(EvalutionScreen.this, strMessage);
                    //recyclerViewLanguage.setVisibility(View.GONE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
