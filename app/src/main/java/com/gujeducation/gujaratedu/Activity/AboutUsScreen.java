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

public class AboutUsScreen extends AppCompatActivity implements OnResult {

    Functions mFunctions;
    AppCompatTextView mTvTitle, mTvAboutUs;
    String eTitle, eBody;
    private AppCompatImageView btnBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_essay_description);
        mFunctions = new Functions(this);

        mTvTitle = findViewById(R.id.header_title);
        btnBack = findViewById(R.id.ivback);
        mTvAboutUs = findViewById(R.id.tvEssay);


        if (Functions.knowInternetOn(this)) {
            APIs.getAboutUs(AboutUsScreen.this, this);
        } else {
            Functions.showInternetAlert(this);
        }

        try {


        } catch (Exception e) {
            e.printStackTrace();
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
                    mTvAboutUs.setText(HtmlCompat.fromHtml(jObj.optString("description"), 0));
                    mTvTitle.setText(jObj.optString("title"));
                } else {
                    Functions.ToastUtility(AboutUsScreen.this, strMessage);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
