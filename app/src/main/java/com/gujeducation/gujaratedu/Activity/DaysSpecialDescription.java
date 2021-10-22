package com.gujeducation.gujaratedu.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.gujeducation.R;
import com.gujeducation.gujaratedu.Helper.Functions;

public class DaysSpecialDescription extends AppCompatActivity {

    Functions mFunctions;
    Intent intent;
    AppCompatTextView mTvTitle, mTvSubTitle, mTvDescription;
    String Title, SubTitle, Description, Image;
    private AppCompatImageView mIvPerson, btnBack;
    AdView mAdView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dayspecial_description);
        mFunctions = new Functions(this);
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mTvTitle = findViewById(R.id.tvTitle);
        mTvSubTitle = findViewById(R.id.tvSubtitle);
        mTvDescription = findViewById(R.id.tvDescription);
        mIvPerson = findViewById(R.id.iv_person);
        btnBack = findViewById(R.id.ivback);

        Intent intent = getIntent();
        if (intent.hasExtra("title") && intent.hasExtra("subtitle") && intent.hasExtra("description") && intent.hasExtra("image")) {
            Title = intent.getExtras().getString("title");
            SubTitle = intent.getExtras().getString("subtitle");
            Description = intent.getExtras().getString("description");
            Image = intent.getExtras().getString("image");
        } else {
            Title = "Unknown";
            SubTitle = "Unknown";
            Description = "Unknown";
            Image = "Unknown";
        }

        /*Log.e("ChapterIntent", " Title->" + Title +
                " SubTitle->" + SubTitle +
                " Description->" + Description +
                " Image->" + Image
        );*/

        try {
            mTvTitle.setText(Title);
            mTvSubTitle.setText(SubTitle);
            mTvDescription.setText(Description);
            Glide.with(DaysSpecialDescription.this)
                    .load(Image)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.IMMEDIATE).dontAnimate()
                    .into(mIvPerson);
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
