package com.gujeducation.gujaratedu.Activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.text.HtmlCompat;

import com.gujeducation.R;
import com.gujeducation.gujaratedu.Helper.Connection;
import com.gujeducation.gujaratedu.Helper.Functions;
import com.gujeducation.gujaratedu.Interface.OnResult;
import com.gujeducation.gujaratedu.ServerAPIs.APIs;

import org.json.JSONObject;

public class TermsConditionScreen extends AppCompatActivity implements OnResult {

    Functions mFunctions;
    AppCompatTextView mTvTitle, mTvTermsCondition;
    String eTitle, eBody;
    private AppCompatImageView btnBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_essay_description);
        mFunctions = new Functions(this);

        mTvTitle = findViewById(R.id.header_title);
        btnBack = findViewById(R.id.ivback);
        mTvTermsCondition = findViewById(R.id.tvEssay);
        mTvTitle.setText(R.string.menu_term_conditions);

        if (Functions.knowInternetOn(this)) {
            APIs.getTermsCondition(TermsConditionScreen.this, this);
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
                if (strStatus != 0) {
                    mTvTermsCondition.setText(HtmlCompat.fromHtml(jObj.optString("description"), 0));
                    mTvTitle.setText(jObj.optString("title"));
                } else {
                    Functions.ToastUtility(TermsConditionScreen.this, strMessage);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
