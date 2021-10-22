package com.gujeducation.gujaratedu.Activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatTextView;

import com.gujeducation.R;
import com.gujeducation.gujaratedu.Adapter.ChapterAdapter;
import com.gujeducation.gujaratedu.Helper.Functions;

public class ResultScreen extends AppCompatActivity {
    AppCompatButton btnReTest, btnTestAnalysis;
    AppCompatImageView mIvBackbtn, mIvheader_logo;
    AppCompatTextView mTvTimer, mTvTimeTitle, mTvAnswer;
    Functions mFunction;
    LinearLayout mLlQue1, mLlQue2, mLlQue3, mLlQue4, mLlQue5;
    RadioGroup rdQueGrp1, rdQueGrp2, rdQueGrp3, rdQueGrp4, rdQueGrp5;
    int TotalCorrectAnswer = 0;
    //intent data
    int chapter_ID, semester_ID, subject_ID;
    String chapter_Name, subject_Name, standard_NO;
    private AppCompatRadioButton rdoAnswer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // To make activity full screen.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultscreen);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        ChapterAdapter.bottomSheetChapterFragment.dismiss();
        mFunction = new Functions(this);

        com.gujeducation.gujaratedu.Helper.Language.set(ResultScreen.this, mFunction.getPrefLanguageCode());
        /*Log.e("GETLANGUAGE_LOGIN", "-" + mFunction.getPrefLanguageCode() +
                " LngCode - " + com.gujeducation.gujaratedu.Helper.Language.getCode());*/


        mIvBackbtn = findViewById(R.id.ivback);
        mTvAnswer = findViewById(R.id.tvanswer);
        btnReTest = findViewById(R.id.btnretest);
        btnTestAnalysis = findViewById(R.id.btnanalysis);

        try {
            Intent inte = getIntent();
            if (inte.hasExtra("txtsemsterID") && inte.hasExtra("txtstandardNO")
                    && inte.hasExtra("txtsubjectID") && inte.hasExtra("txtchapterID") &&
                    inte.hasExtra("sendanswer")) {
                semester_ID = inte.getExtras().getInt("txtsemsterID");
                standard_NO = inte.getExtras().getString("txtstandardNO");
                subject_ID = inte.getExtras().getInt("txtsubjectID");
                subject_Name = inte.getExtras().getString("txtsubject_name");
                chapter_ID = inte.getExtras().getInt("txtchapterID");
                chapter_Name = inte.getExtras().getString("txtchapter_name");
                TotalCorrectAnswer = getIntent().getExtras().getInt("sendanswer");
            } else {
                semester_ID = 0;
                standard_NO = "";
                subject_Name = "";
                chapter_Name = "";
                subject_ID = 0;
                chapter_ID = 0;
                TotalCorrectAnswer = 0;
            }

            Log.e("1_ResultScrnIntent",
                    "semester_ID->" + semester_ID + " standard_NO->" + standard_NO +
                            " subject_ID->" + subject_ID + "  subject_Name->" + subject_Name +
                            " chapter_ID->" + chapter_ID + "  chapter_Name->" + chapter_Name +
                            "  TotalCorrectAnswer->" + TotalCorrectAnswer);

            mTvAnswer.setText("" + TotalCorrectAnswer + " / " + 10);

        } catch (Exception e) {
            e.printStackTrace();
        }


        btnTestAnalysis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ResultScreen.this, "btnTestAnalysis", Toast.LENGTH_SHORT).show();
            }
        });

        btnReTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultScreen.this, TestScreen.class);

                intent.putExtra("txtsemsterID", semester_ID);
                intent.putExtra("txtstandardNO", "" + standard_NO);
                intent.putExtra("txtsubjectID", subject_ID);
                intent.putExtra("txtchapterID", chapter_ID);
                intent.putExtra("txtchapter_name", chapter_Name);
                intent.putExtra("txtsubject_name", subject_Name);

                Log.e("1_SendTestIntnt",
                        "semester_ID->" + semester_ID + " standard_NO->" + standard_NO +
                                " subject_ID->" + subject_ID + "  subject_Name->" + subject_Name +
                                " chapter_ID->" + chapter_ID + "  chapter_Name->" + chapter_Name);

                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.no_animation);
            }
        });

        mIvBackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(ResultScreen.this, HomeScreen.class);
                //startActivity(intent);
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.no_animation);
            }
        });

        btnTestAnalysis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(ResultScreen.this,TestAnalysis.class));
            }
        });
    }
}