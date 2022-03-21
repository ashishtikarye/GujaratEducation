package com.gujeducation.gujaratedu.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.text.HtmlCompat;

import com.gujeducation.R;
import com.gujeducation.gujaratedu.Helper.Functions;

public class EssayDescription extends AppCompatActivity {

    Functions mFunctions;
    WebView wvEssay;
    Intent intent;
    AppCompatTextView mTvTitle,mTvEssayBody;
    String eTitle, eBody;
    private AppCompatImageView btnBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_essay_description);
        mFunctions = new Functions(this);

        mTvTitle = (AppCompatTextView) findViewById(R.id.header_title);
        btnBack = (AppCompatImageView) findViewById(R.id.ivback);
        mTvEssayBody = (AppCompatTextView) findViewById(R.id.tvEssay);


        Intent intent = getIntent();
        if (intent.hasExtra("essayBody") && intent.hasExtra("essayTitle")) {
            eTitle = intent.getExtras().getString("essayTitle");
            eBody = intent.getExtras().getString("essayBody");
        } else {
            eTitle = "Unknown";
            eBody = "Unknown";
        }

        //Log.e("ebody",""+eBody);

        try {
            mTvTitle.setText(eTitle);
            mTvEssayBody.setText(HtmlCompat.fromHtml(eBody, 0));

            /*if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                mTvEssayBody.setText(Html.fromHtml(eTitle));
            } else {
                mTvEssayBody.setText(Html.fromHtml(eTitle));
            }*/


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


}
