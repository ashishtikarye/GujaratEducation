package com.gujeducation.gujaratedu.Activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.gujeducation.R;
import com.gujeducation.gujaratedu.Adapter.ChapterAdapter;
import com.gujeducation.gujaratedu.Adapter.TestAdapter;
import com.gujeducation.gujaratedu.Helper.Connection;
import com.gujeducation.gujaratedu.Helper.Functions;
import com.gujeducation.gujaratedu.Interface.OnResult;
import com.gujeducation.gujaratedu.Model.Quiz;
import com.gujeducation.gujaratedu.ServerAPIs.APIs;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class TestScreen extends AppCompatActivity implements OnResult {
    AppCompatButton btnNextquestion, btnCompleteQuiz;
    AppCompatImageView mIvBackbtn, mIvheader_logo;
    Functions mFunction;
    LinearLayout mLlquestionGrp, mTvNoQuiz, mLlQue1, mLlQue2, mLlQue3, mLlQue4, mLlQue5, mLlQue6, mLlQue7, mLlQue8, mLlQue9, mLlQue10;
    RadioGroup rdQueGrp1, rdQueGrp2, rdQueGrp3, rdQueGrp4, rdQueGrp5, rdQueGrp6, rdQueGrp7, rdQueGrp8, rdQueGrp9, rdQueGrp10;
    String answer1 = "", answer2 = "", answer3 = "", answer4 = "", answer5 = "", answer6 = "", answer7 = "", answer8 = "", answer9 = "", answer10 = "";
    String chapter_Name, subject_Name, standard_NO, correct_answer1 = "",
            correct_answer2 = "",
            correct_answer3 = "",
            correct_answer4 = "", correct_answer5 = "", correct_answer6 = "",
            correct_answer7 = "", correct_answer8 = "", correct_answer9 = "",
            correct_answer10 = "";
    int countAns = 0;
    AppCompatTextView mTvStandard, mTvChapterName, mTvSubject, mTvquestion1, mTvquestion2, mTvquestion3, mTvquestion4, mTvquestion5, mTvquestion6, mTvquestion7, mTvquestion8,
            mTvquestion9, mTvquestion10;
    ArrayList<Quiz> listQuiz = new ArrayList<>();
    //RecyclerView recyclerViewQuiz;
    LinearLayoutManager mLayoutManager;
    TestAdapter mQuizTestAdapter;
    int chapter_ID, semester_ID, subject_ID;
    private AppCompatRadioButton rdoAnswer,
            rdAnswer1, rdAnswer2, rdAnswer3, rdAnswer4,
            rdAnswer5, rdAnswer6, rdAnswer7, rdAnswer8,
            rdAnswer9, rdAnswer10, rdAnswer11, rdAnswer12,
            rdAnswer13, rdAnswer14, rdAnswer15, rdAnswer16,
            rdAnswer17, rdAnswer18, rdAnswer19, rdAnswer20,
            rdAnswer21, rdAnswer22, rdAnswer23, rdAnswer24,
            rdAnswer25, rdAnswer26, rdAnswer27, rdAnswer28,
            rdAnswer29, rdAnswer30, rdAnswer31, rdAnswer32,
            rdAnswer33, rdAnswer34, rdAnswer35, rdAnswer36,
            rdAnswer37, rdAnswer38, rdAnswer39, rdAnswer40;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // To make activity full screen.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testscreennew);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }
        ChapterAdapter.bottomSheetChapterFragment.dismiss();
        mFunction = new Functions(this);

        com.gujeducation.gujaratedu.Helper.Language.set(TestScreen.this, mFunction.getPrefLanguageCode());
        /*Log.e("GETLANGUAGE_LOGIN", "-" + mFunction.getPrefLanguageCode() +
                " LngCode - " + com.gujeducation.gujaratedu.Helper.Language.getCode());*/
//http://ssamsbsurat.org/android_api/getQuiz.php?mediumId=2&standardId=6&semesterId=3&textbookId=40


        try {
            /*Intent inte = getIntent();
            if (inte.hasExtra("txtsemsterID") && inte.hasExtra("txtstandardNO")
                    && inte.hasExtra("txtsubjectID") && inte.hasExtra("txtchapterID")) {
                semester_ID = inte.getExtras().getInt("txtsemsterID");
                standard_NO = inte.getExtras().getString("txtstandardNO");
                subject_ID = inte.getExtras().getInt("txtsubjectID");
                subject_Name = inte.getExtras().getString("txtsubject_name");
                chapter_ID = inte.getExtras().getInt("txtchapterID");
                chapter_Name = inte.getExtras().getString("txtchapter_name");
            } else {
                semester_ID = 0;
                standard_NO = "";
                subject_Name = "";
                chapter_Name = "";
                subject_ID = 0;
                chapter_ID = 0;
            }*/

            semester_ID = mFunction.getSemester();
            standard_NO = mFunction.getStandardName();
            subject_ID = mFunction.getSubjectId();
            subject_Name = mFunction.getSubjectName();
            chapter_ID = mFunction.getChapterId();
            chapter_Name = mFunction.getChapterName();

            Log.e("1_TestScrnIntent",
                    "Medium_ID->" + mFunction.getPrefMediumId() +
                            "semester_ID->" + semester_ID + " standard_NO->" + standard_NO +
                            " subject_ID->" + subject_ID + "  subject_Name->" + subject_Name +
                            " chapter_ID->" + chapter_ID + "  chapter_Name->" + chapter_Name);
        } catch (Exception e) {
            e.printStackTrace();
        }

        mTvChapterName = findViewById(R.id.txtchapter);
        mTvChapterName.setText(mFunction.getChapterName());

        mTvSubject = findViewById(R.id.txtsubject);
        mTvSubject.setText("Subject : " + mFunction.getSubjectName());


        mTvStandard = findViewById(R.id.txtstandard);
        mIvheader_logo = findViewById(R.id.ivheader_logoo);
        mIvBackbtn = findViewById(R.id.ivbackbt);




/*

        recyclerViewQuiz = findViewById(R.id.recyclerview_Quiz);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewQuiz.setHasFixedSize(true);
        recyclerViewQuiz.setLayoutManager(mLayoutManager);
*/

        mLlquestionGrp = findViewById(R.id.llquestiongrp);
        mLlquestionGrp.setVisibility(View.GONE);

        mTvNoQuiz = findViewById(R.id.tvnoquiz);
        mTvNoQuiz.setVisibility(View.GONE);

        btnNextquestion = findViewById(R.id.btnNextQue);
        btnNextquestion.setVisibility(View.GONE);
        // btnNextquestion.setText("Next question");
        btnCompleteQuiz = findViewById(R.id.btnCompleteQuiz);
        btnCompleteQuiz.setVisibility(View.GONE);
        rdQueGrp1 = findViewById(R.id.question_group1);
        rdQueGrp2 = findViewById(R.id.question_group2);
        rdQueGrp3 = findViewById(R.id.question_group3);
        rdQueGrp4 = findViewById(R.id.question_group4);
        rdQueGrp5 = findViewById(R.id.question_group5);
        rdQueGrp6 = findViewById(R.id.question_group6);
        rdQueGrp7 = findViewById(R.id.question_group7);
        rdQueGrp8 = findViewById(R.id.question_group8);
        rdQueGrp9 = findViewById(R.id.question_group9);
        rdQueGrp10 = findViewById(R.id.question_group10);
        //    mIvBack = (AppCompatImageView) findViewById(R.id.ivback);
        mLlQue1 = findViewById(R.id.llquestion1);
        mLlQue2 = findViewById(R.id.llquestion2);
        mLlQue3 = findViewById(R.id.llquestion3);
        mLlQue4 = findViewById(R.id.llquestion4);
        mLlQue5 = findViewById(R.id.llquestion5);
        mLlQue6 = findViewById(R.id.llquestion6);
        mLlQue7 = findViewById(R.id.llquestion7);
        mLlQue8 = findViewById(R.id.llquestion8);
        mLlQue9 = findViewById(R.id.llquestion9);
        mLlQue10 = findViewById(R.id.llquestion10);

        mTvquestion1 = findViewById(R.id.tvquestion1);
        mTvquestion2 = findViewById(R.id.tvquestion2);
        mTvquestion3 = findViewById(R.id.tvquestion3);
        mTvquestion4 = findViewById(R.id.tvquestion4);
        mTvquestion5 = findViewById(R.id.tvquestion5);
        mTvquestion6 = findViewById(R.id.tvquestion6);
        mTvquestion7 = findViewById(R.id.tvquestion7);
        mTvquestion8 = findViewById(R.id.tvquestion8);
        mTvquestion9 = findViewById(R.id.tvquestion9);
        mTvquestion10 = findViewById(R.id.tvquestion10);

        rdAnswer1 = findViewById(R.id.answer1);
        rdAnswer2 = findViewById(R.id.answer2);
        rdAnswer3 = findViewById(R.id.answer3);
        rdAnswer4 = findViewById(R.id.answer4);
        rdAnswer5 = findViewById(R.id.answer5);
        rdAnswer6 = findViewById(R.id.answer6);
        rdAnswer7 = findViewById(R.id.answer7);
        rdAnswer8 = findViewById(R.id.answer8);
        rdAnswer9 = findViewById(R.id.answer9);
        rdAnswer10 = findViewById(R.id.answer10);

        rdAnswer11 = findViewById(R.id.answer11);
        rdAnswer12 = findViewById(R.id.answer12);
        rdAnswer13 = findViewById(R.id.answer13);
        rdAnswer14 = findViewById(R.id.answer14);
        rdAnswer15 = findViewById(R.id.answer15);
        rdAnswer16 = findViewById(R.id.answer16);
        rdAnswer17 = findViewById(R.id.answer17);
        rdAnswer18 = findViewById(R.id.answer18);
        rdAnswer19 = findViewById(R.id.answer19);
        rdAnswer20 = findViewById(R.id.answer20);

        rdAnswer21 = findViewById(R.id.answer21);
        rdAnswer22 = findViewById(R.id.answer22);
        rdAnswer23 = findViewById(R.id.answer23);
        rdAnswer24 = findViewById(R.id.answer24);
        rdAnswer25 = findViewById(R.id.answer25);
        rdAnswer26 = findViewById(R.id.answer26);
        rdAnswer27 = findViewById(R.id.answer27);
        rdAnswer28 = findViewById(R.id.answer28);
        rdAnswer29 = findViewById(R.id.answer29);
        rdAnswer30 = findViewById(R.id.answer30);

        rdAnswer31 = findViewById(R.id.answer31);
        rdAnswer32 = findViewById(R.id.answer32);
        rdAnswer33 = findViewById(R.id.answer33);
        rdAnswer34 = findViewById(R.id.answer34);
        rdAnswer35 = findViewById(R.id.answer35);
        rdAnswer36 = findViewById(R.id.answer36);
        rdAnswer37 = findViewById(R.id.answer37);
        rdAnswer38 = findViewById(R.id.answer38);
        rdAnswer39 = findViewById(R.id.answer39);
        rdAnswer40 = findViewById(R.id.answer40);

        addListenerOnButton();
        final Animation animShake = AnimationUtils.loadAnimation(this, R.anim.shake_error);


        mIvBackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.no_animation);
            }
        });

        btnCompleteQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Log.e("CorrectAnswer ", "-> " + countAns);
                Intent intent = new Intent(TestScreen.this, ResultScreen.class);
                intent.putExtra("sendanswer", countAns);
                intent.putExtra("txtsemsterID", semester_ID);
                intent.putExtra("txtstandardNO", "" + standard_NO);
                intent.putExtra("txtsubjectID", subject_ID);
                intent.putExtra("txtchapterID", chapter_ID);
                intent.putExtra("txtchapter_name", chapter_Name);
                intent.putExtra("txtsubject_name", subject_Name);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.no_animation);
            }
        });


        if (Functions.knowInternetOn(this)) {
            String std = getResources().getString(R.string._class);
            if (mFunction.getFlagLogCode() == 1) {
                //APIs.getQuiz(this, this, "2", "6", "3", "49");
                mTvStandard.setText(std + " : " + mFunction.getStandardName());
               /* APIs.getChapterQuiz(this, this, mFunction.getPrefMediumId(), mFunction.getSection(),
                        mFunction.getSemester(), mFunction.getStandardId(), mFunction.getSubjectId(),mFunction.getChapterId());
*/
            } else {
                mTvStandard.setText(std + " : " + mFunction.getStandardName());
               /* APIs.getChapterQuiz(this, this, mFunction.getPrefMediumId(), mFunction.getSection(),
                        mFunction.getSemester(), mFunction.getStandardId(), mFunction.getSubjectId(),mFunction.getChapterId());*/
            }
        } else {
            Functions.showInternetAlert(this);
        }
    }


    @Override
    public void onResult(JSONObject jobjWhole) {
        try {
            if (jobjWhole != null) {
                JSONObject jObj = jobjWhole.optJSONObject(Connection.TAG_DATA);
                String strApiName = jObj.optString("api");
                int strStatus = jObj.optInt("success");
                String strMessage = jObj.optString("message");
                if (strApiName.equalsIgnoreCase("getQuiz")) {
                    if (strStatus != 0) {
                        JSONArray jArrayTextSub = jObj.getJSONArray("quizData");
                        //Toast.makeText(this, "jArrayTextSub Length->"+jArrayTextSub.length(), Toast.LENGTH_SHORT).show();
                        if (jArrayTextSub.length() > 0) {

                            mTvNoQuiz.setVisibility(View.GONE);
                            mLlquestionGrp.setVisibility(View.VISIBLE);
                            btnNextquestion.setVisibility(View.VISIBLE);

                            JSONObject objj_1 = jArrayTextSub.getJSONObject(0);
                            JSONObject objj_2 = jArrayTextSub.getJSONObject(1);
                            JSONObject objj_3 = jArrayTextSub.getJSONObject(2);
                            JSONObject objj_4 = jArrayTextSub.getJSONObject(3);
                            JSONObject objj_5 = jArrayTextSub.getJSONObject(4);
                            JSONObject objj_6 = jArrayTextSub.getJSONObject(5);
                            JSONObject objj_7 = jArrayTextSub.getJSONObject(6);
                            JSONObject objj_8 = jArrayTextSub.getJSONObject(7);
                            JSONObject objj_9 = jArrayTextSub.getJSONObject(8);
                            JSONObject objj_10 = jArrayTextSub.getJSONObject(9);




                            mTvquestion1.setText("1) "+objj_1.optString("question"));
                            rdAnswer1.setText(objj_1.optString("A"));
                            rdAnswer2.setText(objj_1.optString("B"));
                            rdAnswer3.setText(objj_1.optString("C"));
                            rdAnswer4.setText(objj_1.optString("D"));
                            correct_answer1 = objj_1.optString("correct_answer");
//

                            mTvquestion2.setText("2) "+objj_2.optString("question"));
                            rdAnswer5.setText(objj_2.optString("A"));
                            rdAnswer6.setText(objj_2.optString("B"));
                            rdAnswer7.setText(objj_2.optString("C"));
                            rdAnswer8.setText(objj_2.optString("D"));
                            correct_answer2 = objj_2.optString("correct_answer");


                            mTvquestion3.setText("3) "+objj_3.optString("question"));
                            rdAnswer9.setText(objj_3.optString("A"));
                            rdAnswer10.setText(objj_3.optString("B"));
                            rdAnswer11.setText(objj_3.optString("C"));
                            rdAnswer12.setText(objj_3.optString("D"));
                            correct_answer3 = objj_3.optString("correct_answer");

                            mTvquestion4.setText("4) "+objj_4.optString("question"));
                            rdAnswer13.setText(objj_4.optString("A"));
                            rdAnswer14.setText(objj_4.optString("B"));
                            rdAnswer15.setText(objj_4.optString("C"));
                            rdAnswer16.setText(objj_4.optString("D"));
                            correct_answer4 = objj_4.optString("correct_answer");


                            mTvquestion5.setText("5) "+objj_5.optString("question"));
                            rdAnswer17.setText(objj_5.optString("A"));
                            rdAnswer18.setText(objj_5.optString("B"));
                            rdAnswer19.setText(objj_5.optString("C"));
                            rdAnswer20.setText(objj_5.optString("D"));
                            correct_answer5 = objj_5.optString("correct_answer");

                            mTvquestion6.setText("6) "+objj_6.optString("question"));
                            rdAnswer21.setText(objj_6.optString("A"));
                            rdAnswer22.setText(objj_6.optString("B"));
                            rdAnswer23.setText(objj_6.optString("C"));
                            rdAnswer24.setText(objj_6.optString("D"));
                            correct_answer6 = objj_6.optString("correct_answer");


                            mTvquestion7.setText("7) "+objj_7.optString("question"));
                            rdAnswer25.setText(objj_7.optString("A"));
                            rdAnswer26.setText(objj_7.optString("B"));
                            rdAnswer27.setText(objj_7.optString("C"));
                            rdAnswer28.setText(objj_7.optString("D"));
                            correct_answer7 = objj_7.optString("correct_answer");


                            mTvquestion8.setText("8) "+objj_8.optString("question"));
                            rdAnswer29.setText(objj_8.optString("A"));
                            rdAnswer30.setText(objj_8.optString("B"));
                            rdAnswer31.setText(objj_8.optString("C"));
                            rdAnswer32.setText(objj_8.optString("D"));
                            correct_answer8 = objj_8.optString("correct_answer");

                            mTvquestion9.setText("9) "+objj_9.optString("question"));
                            rdAnswer33.setText(objj_9.optString("A"));
                            rdAnswer34.setText(objj_9.optString("B"));
                            rdAnswer35.setText(objj_9.optString("C"));
                            rdAnswer36.setText(objj_9.optString("D"));
                            correct_answer9 = objj_9.optString("correct_answer");

                            mTvquestion10.setText("10) "+objj_10.optString("question"));
                            rdAnswer37.setText(objj_10.optString("A"));
                            rdAnswer38.setText(objj_10.optString("B"));
                            rdAnswer39.setText(objj_10.optString("C"));
                            rdAnswer40.setText(objj_10.optString("D"));
                            correct_answer10 = objj_10.optString("correct_answer");


                            /*mFunction.SetPrefQuiz(objj_1.optString("question"), objj_1.optString("correct_answer"),
                                    objj_2.optString("question"), objj_2.optString("correct_answer"),
                                    objj_3.optString("question"), objj_3.optString("correct_answer"),
                                    objj_4.optString("question"), objj_4.optString("correct_answer"),
                                    objj_5.optString("question"), objj_5.optString("correct_answer"),
                                    objj_6.optString("question"), objj_6.optString("correct_answer"),
                                    objj_7.optString("question"), objj_7.optString("correct_answer"),
                                    objj_8.optString("question"), objj_8.optString("correct_answer"),
                                    objj_9.optString("question"), objj_9.optString("correct_answer"),
                                    objj_10.optString("question"), objj_10.optString("correct_answer"));*/

                        } else {
                            mTvNoQuiz.setVisibility(View.VISIBLE);
                            mLlquestionGrp.setVisibility(View.INVISIBLE);
                            btnNextquestion.setText(View.GONE);
                        }
                    } else {
                        Functions.ToastUtility(TestScreen.this, strMessage);
                        mTvNoQuiz.setVisibility(View.VISIBLE);
                        mLlquestionGrp.setVisibility(View.INVISIBLE);
                        btnNextquestion.setText(View.GONE);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void addListenerOnButton() {

        btnNextquestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (mLlQue1.getVisibility() == View.VISIBLE) {
                    if (rdQueGrp1.getCheckedRadioButtonId() == -1) {
                        Toast.makeText(TestScreen.this,
                                "Please select any one answer", Toast.LENGTH_SHORT).show();
                    } else {
                        // one of the radio buttons is checked
                        int selectedAns = rdQueGrp1.getCheckedRadioButtonId();
                        rdoAnswer = findViewById(selectedAns);
                        answer1 = rdoAnswer.getText().toString().trim();
                        if (answer1.trim().equalsIgnoreCase(correct_answer1.trim())) {
                            countAns++;
                            //    Log.e("count_1 ", "-> " + countAns++);
                        }
                        mLlQue1.setVisibility(View.GONE);
                        mLlQue2.setVisibility(View.VISIBLE);
                    }
                } else if (mLlQue2.getVisibility() == View.VISIBLE) {
                    if (rdQueGrp2.getCheckedRadioButtonId() == -1) {
                        Toast.makeText(TestScreen.this,
                                "Please select any one answer", Toast.LENGTH_SHORT).show();
                    } else {
                        // one of the radio buttons is checked
                        int selectedAns = rdQueGrp2.getCheckedRadioButtonId();
                        rdoAnswer = findViewById(selectedAns);
                        answer2 = rdoAnswer.getText().toString().trim();
                        if (answer2.trim().equalsIgnoreCase(correct_answer2.trim())) {
                            countAns++;
                            //   Log.e("count_2 ", "-> " + countAns++);
                        }
                        mLlQue2.setVisibility(View.GONE);
                        mLlQue3.setVisibility(View.VISIBLE);
                    }
                } else if (mLlQue3.getVisibility() == View.VISIBLE) {
                    if (rdQueGrp3.getCheckedRadioButtonId() == -1) {
                        Toast.makeText(TestScreen.this,
                                "Please select any one answer", Toast.LENGTH_SHORT).show();
                    } else {
                        // one of the radio buttons is checked
                        int selectedAns = rdQueGrp3.getCheckedRadioButtonId();
                        rdoAnswer = findViewById(selectedAns);
                        answer3 = rdoAnswer.getText().toString().trim();
                        if (answer3.trim().equalsIgnoreCase(correct_answer3.trim())) {
                            countAns++;
                            //  Log.e("count_3 ", "-> " + countAns++);
                        }
                        mLlQue3.setVisibility(View.GONE);
                        mLlQue4.setVisibility(View.VISIBLE);
                    }
                } else if (mLlQue4.getVisibility() == View.VISIBLE) {
                    if (rdQueGrp4.getCheckedRadioButtonId() == -1) {
                        Toast.makeText(TestScreen.this,
                                "Please select any one answer", Toast.LENGTH_SHORT).show();
                    } else {
                        // one of the radio buttons is checked
                        int selectedAns = rdQueGrp4.getCheckedRadioButtonId();
                        rdoAnswer = findViewById(selectedAns);
                        answer4 = rdoAnswer.getText().toString().trim();
                        if (answer4.trim().equalsIgnoreCase(correct_answer4.trim())) {
                            countAns++;
                            // Log.e("count_4 ", "-> " + countAns++);
                        }
                        mLlQue4.setVisibility(View.GONE);
                        mLlQue5.setVisibility(View.VISIBLE);

                    }
                } else if (mLlQue5.getVisibility() == View.VISIBLE) {

                    if (rdQueGrp5.getCheckedRadioButtonId() == -1) {
                        Toast.makeText(TestScreen.this,
                                "Please select any one answer", Toast.LENGTH_SHORT).show();
                    } else {
                        // one of the radio buttons is checked
                        int selectedAns = rdQueGrp5.getCheckedRadioButtonId();
                        rdoAnswer = findViewById(selectedAns);
                        answer5 = rdoAnswer.getText().toString().trim();
                        if (answer5.trim().equalsIgnoreCase(correct_answer5.trim())) {
                            countAns++;
                            //   Log.e("count_5 ", "-> " + countAns++);
                        }
                        mLlQue5.setVisibility(View.GONE);
                        mLlQue6.setVisibility(View.VISIBLE);

                    }
                } else if (mLlQue6.getVisibility() == View.VISIBLE) {

                    if (rdQueGrp6.getCheckedRadioButtonId() == -1) {
                        Toast.makeText(TestScreen.this,
                                "Please select any one answer", Toast.LENGTH_SHORT).show();
                    } else {
                        // one of the radio buttons is checked
                        int selectedAns = rdQueGrp6.getCheckedRadioButtonId();
                        rdoAnswer = findViewById(selectedAns);
                        answer6 = rdoAnswer.getText().toString().trim();
                        if (answer6.trim().equalsIgnoreCase(correct_answer6.trim())) {
                            countAns++;
                            //   Log.e("count_6 ", "-> " + countAns++);
                        }
                        mLlQue6.setVisibility(View.GONE);
                        mLlQue7.setVisibility(View.VISIBLE);

                    }
                } else if (mLlQue7.getVisibility() == View.VISIBLE) {

                    if (rdQueGrp7.getCheckedRadioButtonId() == -1) {
                        Toast.makeText(TestScreen.this,
                                "Please select any one answer", Toast.LENGTH_SHORT).show();
                    } else {
                        // one of the radio buttons is checked
                        int selectedAns = rdQueGrp7.getCheckedRadioButtonId();
                        rdoAnswer = findViewById(selectedAns);
                        answer7 = rdoAnswer.getText().toString().trim();
                        if (answer7.trim().equalsIgnoreCase(correct_answer7.trim())) {
                            countAns++;
                            // Log.e("count_7 ", "-> " + countAns++);
                        }
                        mLlQue7.setVisibility(View.GONE);
                        mLlQue8.setVisibility(View.VISIBLE);

                    }
                } else if (mLlQue8.getVisibility() == View.VISIBLE) {

                    if (rdQueGrp8.getCheckedRadioButtonId() == -1) {
                        Toast.makeText(TestScreen.this,
                                "Please select any one answer", Toast.LENGTH_SHORT).show();
                    } else {
                        // one of the radio buttons is checked
                        int selectedAns = rdQueGrp8.getCheckedRadioButtonId();
                        rdoAnswer = findViewById(selectedAns);
                        answer8 = rdoAnswer.getText().toString().trim();
                        if (answer8.trim().equalsIgnoreCase(correct_answer8.trim())) {
                            countAns++;
                            //Log.e("count_8 ", "-> " + countAns++);
                        }
                        mLlQue8.setVisibility(View.GONE);
                        mLlQue9.setVisibility(View.VISIBLE);

                    }
                } else if (mLlQue9.getVisibility() == View.VISIBLE) {

                    if (rdQueGrp9.getCheckedRadioButtonId() == -1) {
                        Toast.makeText(TestScreen.this,
                                "Please select any one answer", Toast.LENGTH_SHORT).show();
                    } else {
                        // one of the radio buttons is checked
                        int selectedAns = rdQueGrp9.getCheckedRadioButtonId();
                        rdoAnswer = findViewById(selectedAns);
                        answer9 = rdoAnswer.getText().toString().trim();
                        if (answer9.trim().equalsIgnoreCase(correct_answer9.trim())) {
                            countAns++;
                            //Log.e("count_9 ", "-> " + countAns++);
                        }
                        mLlQue9.setVisibility(View.GONE);
                        mLlQue10.setVisibility(View.VISIBLE);

                    }
                } else if (mLlQue10.getVisibility() == View.VISIBLE) {

                    if (rdQueGrp10.getCheckedRadioButtonId() == -1) {
                        Toast.makeText(TestScreen.this,
                                "Please select any one answer", Toast.LENGTH_SHORT).show();
                    } else {
                        // one of the radio buttons is checked
                        int selectedAns = rdQueGrp10.getCheckedRadioButtonId();
                        rdoAnswer = findViewById(selectedAns);
                        answer10 = rdoAnswer.getText().toString().trim();
                        if (answer10.trim().equalsIgnoreCase(correct_answer10.trim())) {
                            countAns++;
                            // Log.e("count_10 ", "-> " + countAns++);
                        }
                        btnNextquestion.setVisibility(View.GONE);
                        btnCompleteQuiz.setVisibility(View.VISIBLE);


                    }
                }

                Log.e(" correct_total_answer ", "-> " + countAns);
            }
        });


    }


}