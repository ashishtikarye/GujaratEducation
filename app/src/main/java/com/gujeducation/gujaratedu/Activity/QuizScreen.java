package com.gujeducation.gujaratedu.Activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.bumptech.glide.Glide;
import com.gujeducation.R;
import com.gujeducation.gujaratedu.Helper.Connection;
import com.gujeducation.gujaratedu.Helper.Functions;
import com.gujeducation.gujaratedu.Interface.OnResult;
import com.gujeducation.gujaratedu.ServerAPIs.APIs;

import org.json.JSONArray;
import org.json.JSONObject;

public class QuizScreen extends AppCompatActivity implements OnResult {
    AppCompatButton btnNext, btnPrevious;
    AppCompatImageView mIvBackbtn, mIvheader_logo;
    MediaPlayer mediaPlayer;
    Functions mFunction;

    AppCompatTextView mTvSubject, mTvChapterName, mTvStandard, mTvQuestion1, mTvQuestion2, mTvQuestion3, mTvQuestion4, mTvQuestion5, mTvQuestion6, mTvQuestion7, mTvQuestion8,
            mTvQuestion9, mTvQuestion10;
    AppCompatImageView mIvQuestion1, mIvQuestion2, mIvQuestion3, mIvQuestion4, mIvQuestion5, mIvQuestion6, mIvQuestion7, mIvQuestion8,
            mIvQuestion9, mIvQuestion10;

    LinearLayout mLlQuestion1, mLlQuestion1Img, mLlQuestion2, mLlQuestion2Img, mLlQuestion3, mLlQuestion3Img, mLlQuestion4, mLlQuestion4Img,
            mLlQuestion5, mLlQuestion5Img, mLlQuestion6, mLlQuestion6Img, mLlQuestion7, mLlQuestion7Img, mLlQuestion8, mLlQuestion8Img,
            mLlQuestion9, mLlQuestion9Img, mLlQuestion10, mLlQuestion10Img, mLlNoQuiz;

    RelativeLayout mRlAnswer1, mRlAnswer2, mRlAnswer3, mRlAnswer4, mRlAnswer5, mRlAnswer6, mRlAnswer7, mRlAnswer8, mRlAnswer9,
            mRlAnswer10;
    LinearLayout mLlAnswer1, mLlAnswer2, mLlAnswer3, mLlAnswer4, mLlAnswer5, mLlAnswer6, mLlAnswer7, mLlAnswer8;

    AppCompatTextView mTvAnswerOption1, mTvAnswer1, mTvAnswerOption2, mTvAnswer2, mTvAnswerOption3, mTvAnswer3, mTvAnswerOption4, mTvAnswer4,
            mTvAnswerOption5, mTvAnswer5, mTvAnswerOption6, mTvAnswer6, mTvAnswerOption7, mTvAnswer7, mTvAnswerOption8, mTvAnswer8,
            mTvAnswerOption9, mTvAnswer9, mTvAnswerOption10, mTvAnswer10, mTvAnswerOption11, mTvAnswer11, mTvAnswerOption12, mTvAnswer12,
            mTvAnswerOption13, mTvAnswer13, mTvAnswerOption14, mTvAnswer14, mTvAnswerOption15, mTvAnswer15, mTvAnswerOption16, mTvAnswer16,
            mTvAnswerOption17, mTvAnswer17, mTvAnswerOption18, mTvAnswer18, mTvAnswerOption19, mTvAnswer19, mTvAnswerOption20, mTvAnswer20,
            mTvAnswerOption21, mTvAnswer21, mTvAnswerOption22, mTvAnswer22, mTvAnswerOption23, mTvAnswer23, mTvAnswerOption24, mTvAnswer24,
            mTvAnswerOption25, mTvAnswer25, mTvAnswerOption26, mTvAnswer26, mTvAnswerOption27, mTvAnswer27, mTvAnswerOption28, mTvAnswer28,
            mTvAnswerOption29, mTvAnswer29, mTvAnswerOption30, mTvAnswer30, mTvAnswerOption31, mTvAnswer31, mTvAnswerOption32, mTvAnswer32,
            mTvAnswerOption33, mTvAnswer33, mTvAnswerOption34, mTvAnswer34, mTvAnswerOption35, mTvAnswer35, mTvAnswerOption36, mTvAnswer36,
            mTvAnswerOption37, mTvAnswer37, mTvAnswerOption38, mTvAnswer38, mTvAnswerOption39, mTvAnswer39, mTvAnswerOption40, mTvAnswer40;


    AppCompatTextView mIvAnswerOption1Img, mIvAnswerOption2Img, mIvAnswerOption40Img, mIvAnswerOption4Img,
            mIvAnswerOption5Img, mIvAnswerOption7Img, mIvAnswerOption6Img, mIvAnswerOption36Img,
            mIvAnswerOption9Img, mIvAnswerOption11Img, mIvAnswerOption10Img, mIvAnswerOption3Img,
            mIvAnswerOption13Img, mIvAnswerOption15Img, mIvAnswerOption14Img, mIvAnswerOption8Img,
            mIvAnswerOption17Img, mIvAnswerOption19Img, mIvAnswerOption18Img, mIvAnswerOption12Img,
            mIvAnswerOption21Img, mIvAnswerOption23Img, mIvAnswerOption22Img, mIvAnswerOption16Img,
            mIvAnswerOption25Img, mIvAnswerOption27Img, mIvAnswerOption26Img, mIvAnswerOption20Img,
            mIvAnswerOption29Img, mIvAnswerOption31Img, mIvAnswerOption30Img, mIvAnswerOption24Img,
            mIvAnswerOption33Img, mIvAnswerOption35Img, mIvAnswerOption34Img, mIvAnswerOption28Img,
            mIvAnswerOption37Img, mIvAnswerOption39Img, mIvAnswerOption38Img, mIvAnswerOption32Img;


    AppCompatImageView mIvAnswer1Img, mIvAnswer2Img, mIvAnswer3Img, mIvAnswer4Img,
            mIvAnswer5Img, mIvAnswer6Img, mIvAnswer7Img, mIvAnswer8Img,
            mIvAnswer9Img, mIvAnswer10Img, mIvAnswer11Img, mIvAnswer12Img,
            mIvAnswer13Img, mIvAnswer14Img, mIvAnswer15Img, mIvAnswer16Img,
            mIvAnswer17Img, mIvAnswer18Img, mIvAnswer19Img, mIvAnswer20Img,
            mIvAnswer21Img, mIvAnswer22Img, mIvAnswer23Img, mIvAnswer24Img,
            mIvAnswer25Img, mIvAnswer26Img, mIvAnswer27Img, mIvAnswer28Img,
            mIvAnswer29Img, mIvAnswer30Img, mIvAnswer31Img, mIvAnswer32Img,
            mIvAnswer33Img, mIvAnswer34Img, mIvAnswer35Img, mIvAnswer36Img,
            mIvAnswer37Img, mIvAnswer38Img, mIvAnswer39Img, mIvAnswer40Img;


    String op_answer1 = "", op_answer2 = "", op_answer3 = "", op_answer4 = "", op_answer5 = "", op_answer6 = "", op_answer7 = "",
            op_answer8 = "", op_answer9 = "", op_answer10 = "";
    String questionType1 = "", questionType2 = "", questionType3 = "", questionType4 = "", questionType5 = "", questionType6 = "", questionType7 = "",
            questionType8 = "", questionType9 = "", questionType10 = "";
    String op_correct_answer1 = "",
            op_correct_answer2 = "",
            op_correct_answer3 = "",
            op_correct_answer4 = "", op_correct_answer5 = "", op_correct_answer6 = "",
            op_correct_answer7 = "", op_correct_answer8 = "", op_correct_answer9 = "",
            op_correct_answer10 = "";
    int countAns = 0;


    int chapter_ID, exam_ID, papers_ID, medium_ID, subject_ID;

    int[] resID = {R.raw.wrong, R.raw.correct};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // To make activity full screen.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizscreen);
        mFunction = new Functions(this);
        try {
            Intent i = getIntent();
            if (i.hasExtra("mediumId") && i.hasExtra("examId")
                    && i.hasExtra("papersId") && i.hasExtra("subjectId") && i.hasExtra("chapterId")) {
                medium_ID = i.getExtras().getInt("mediumId");
                exam_ID = i.getExtras().getInt("examId");
                papers_ID = i.getExtras().getInt("papersId");
                subject_ID = i.getExtras().getInt("subjectId");
                chapter_ID = i.getExtras().getInt("chapterId");
            } else {
                medium_ID = 0;
                exam_ID = 0;
                papers_ID = 0;
                subject_ID = 0;
                chapter_ID = 0;
            }
                    /*Log.e("QuizScreen",
                    "Medium_ID->" + mFunction.getPrefMediumId() +
                            "medium_ID->" + medium_ID + " exam_ID->" + exam_ID +
                            " papers_ID->" + papers_ID + "  subject_ID->" + subject_ID +
                            " chapter_ID->" + chapter_ID);*/
        } catch (Exception e) {
            e.printStackTrace();
        }

        mIvheader_logo = findViewById(R.id.ivheader_logoo);
        mIvBackbtn = findViewById(R.id.ivbackbt);
        btnNext = findViewById(R.id.btnNext);
        btnPrevious = findViewById(R.id.btnPrevious);

        mLlQuestion1 = findViewById(R.id.llquestion1);
        mLlQuestion1Img = findViewById(R.id.llquestion1img);

        mLlQuestion2 = findViewById(R.id.llquestion2);
        mLlQuestion2Img = findViewById(R.id.llquestion2img);

        mLlQuestion3 = findViewById(R.id.llquestion3);
        mLlQuestion3Img = findViewById(R.id.llquestion3img);

        mLlQuestion4 = findViewById(R.id.llquestion4);
        mLlQuestion4Img = findViewById(R.id.llquestion4img);

        mLlQuestion5 = findViewById(R.id.llquestion5);
        mLlQuestion5Img = findViewById(R.id.llquestion5img);

        mLlQuestion6 = findViewById(R.id.llquestion6);
        mLlQuestion6Img = findViewById(R.id.llquestion6img);

        mLlQuestion7 = findViewById(R.id.llquestion7);
        mLlQuestion7Img = findViewById(R.id.llquestion7img);

        mLlQuestion8 = findViewById(R.id.llquestion8);
        mLlQuestion8Img = findViewById(R.id.llquestion8img);

        mLlQuestion9 = findViewById(R.id.llquestion9);
        mLlQuestion9Img = findViewById(R.id.llquestion9img);

        mLlQuestion10 = findViewById(R.id.llquestion10);
        mLlQuestion10Img = findViewById(R.id.llquestion10img);


        mLlQuestion2.setVisibility(View.GONE);
        mLlQuestion3.setVisibility(View.GONE);
        mLlQuestion4.setVisibility(View.GONE);
        mLlQuestion5.setVisibility(View.GONE);
        mLlQuestion6.setVisibility(View.GONE);
        mLlQuestion7.setVisibility(View.GONE);
        mLlQuestion8.setVisibility(View.GONE);
        mLlQuestion9.setVisibility(View.GONE);
        mLlQuestion10.setVisibility(View.GONE);
        mLlQuestion2Img.setVisibility(View.GONE);
        mLlQuestion3Img.setVisibility(View.GONE);
        mLlQuestion4Img.setVisibility(View.GONE);
        mLlQuestion5Img.setVisibility(View.GONE);
        mLlQuestion6Img.setVisibility(View.GONE);
        mLlQuestion7Img.setVisibility(View.GONE);
        mLlQuestion8Img.setVisibility(View.GONE);
        mLlQuestion9Img.setVisibility(View.GONE);
        mLlQuestion10Img.setVisibility(View.GONE);


        mRlAnswer1 = findViewById(R.id.rlv_ans_1);
        mRlAnswer2 = findViewById(R.id.rlv_ans_2);
        mRlAnswer3 = findViewById(R.id.rlv_ans_3);
        mRlAnswer4 = findViewById(R.id.rlv_ans_4);
        mLlAnswer1 = findViewById(R.id.lv_ans_1);
        mLlAnswer2 = findViewById(R.id.lv_ans_2);
        mLlAnswer3 = findViewById(R.id.lv_ans_3);
        mLlAnswer4 = findViewById(R.id.lv_ans_4);
        mLlAnswer5 = findViewById(R.id.lv_ans_5);
        mLlAnswer6 = findViewById(R.id.lv_ans_6);
        mLlAnswer7 = findViewById(R.id.lv_ans_7);
        mLlAnswer8 = findViewById(R.id.lv_ans_8);


        mRlAnswer5 = findViewById(R.id.rlv_ans_5);
        mRlAnswer6 = findViewById(R.id.rlv_ans_6);
        mRlAnswer7 = findViewById(R.id.rlv_ans_7);
        mRlAnswer8 = findViewById(R.id.rlv_ans_8);


        mRlAnswer9 = findViewById(R.id.rlv_ans_9);
        mRlAnswer10 = findViewById(R.id.rlv_ans_10);

        mTvQuestion1 = findViewById(R.id.tvquestion1);
        mTvQuestion2 = findViewById(R.id.tvquestion2);
        mTvQuestion3 = findViewById(R.id.tvquestion3);
        mTvQuestion4 = findViewById(R.id.tvquestion4);
        mTvQuestion5 = findViewById(R.id.tvquestion5);
        mTvQuestion6 = findViewById(R.id.tvquestion6);
        mTvQuestion7 = findViewById(R.id.tvquestion7);
        mTvQuestion8 = findViewById(R.id.tvquestion8);
        mTvQuestion9 = findViewById(R.id.tvquestion9);
        mTvQuestion10 = findViewById(R.id.tvquestion10);

        mIvQuestion1 = findViewById(R.id.tvquestion1img);
        mIvQuestion2 = findViewById(R.id.tvquestion2img);
        mIvQuestion3 = findViewById(R.id.tvquestion3img);
        mIvQuestion4 = findViewById(R.id.tvquestion4img);
        mIvQuestion5 = findViewById(R.id.tvquestion5img);
        mIvQuestion6 = findViewById(R.id.tvquestion6img);
        mIvQuestion7 = findViewById(R.id.tvquestion7img);
        mIvQuestion8 = findViewById(R.id.tvquestion8img);
        mIvQuestion9 = findViewById(R.id.tvquestion9img);
        mIvQuestion10 = findViewById(R.id.tvquestion10img);

        mTvAnswerOption1 = findViewById(R.id.opt_answer1);
        mTvAnswerOption2 = findViewById(R.id.opt_answer2);
        mTvAnswerOption3 = findViewById(R.id.opt_answer3);
        mTvAnswerOption4 = findViewById(R.id.opt_answer4);
        mTvAnswerOption5 = findViewById(R.id.opt_answer5);
        mTvAnswerOption6 = findViewById(R.id.opt_answer6);
        mTvAnswerOption7 = findViewById(R.id.opt_answer7);
        mTvAnswerOption8 = findViewById(R.id.opt_answer8);
        mTvAnswerOption9 = findViewById(R.id.opt_answer9);
        mTvAnswerOption10 = findViewById(R.id.opt_answer10);
        mTvAnswerOption11 = findViewById(R.id.opt_answer11);
        mTvAnswerOption12 = findViewById(R.id.opt_answer12);
        mTvAnswerOption13 = findViewById(R.id.opt_answer13);
        mTvAnswerOption14 = findViewById(R.id.opt_answer14);
        mTvAnswerOption15 = findViewById(R.id.opt_answer15);
        mTvAnswerOption16 = findViewById(R.id.opt_answer16);
        mTvAnswerOption17 = findViewById(R.id.opt_answer17);
        mTvAnswerOption18 = findViewById(R.id.opt_answer18);
        mTvAnswerOption19 = findViewById(R.id.opt_answer19);
        mTvAnswerOption20 = findViewById(R.id.opt_answer20);
        mTvAnswerOption21 = findViewById(R.id.opt_answer21);
        mTvAnswerOption22 = findViewById(R.id.opt_answer22);
        mTvAnswerOption23 = findViewById(R.id.opt_answer23);
        mTvAnswerOption24 = findViewById(R.id.opt_answer24);
        mTvAnswerOption25 = findViewById(R.id.opt_answer25);
        mTvAnswerOption26 = findViewById(R.id.opt_answer26);
        mTvAnswerOption27 = findViewById(R.id.opt_answer27);
        mTvAnswerOption28 = findViewById(R.id.opt_answer28);
        mTvAnswerOption29 = findViewById(R.id.opt_answer29);
        mTvAnswerOption30 = findViewById(R.id.opt_answer30);
        mTvAnswerOption31 = findViewById(R.id.opt_answer31);
        mTvAnswerOption32 = findViewById(R.id.opt_answer32);
        mTvAnswerOption33 = findViewById(R.id.opt_answer33);
        mTvAnswerOption34 = findViewById(R.id.opt_answer34);
        mTvAnswerOption35 = findViewById(R.id.opt_answer35);
        mTvAnswerOption36 = findViewById(R.id.opt_answer36);
        mTvAnswerOption37 = findViewById(R.id.opt_answer37);
        mTvAnswerOption38 = findViewById(R.id.opt_answer38);
        mTvAnswerOption39 = findViewById(R.id.opt_answer39);
        mTvAnswerOption40 = findViewById(R.id.opt_answer40);

        mIvAnswerOption1Img = findViewById(R.id.opt_answer1_img);
        mIvAnswerOption2Img = findViewById(R.id.opt_answer2_img);
        mIvAnswerOption3Img = findViewById(R.id.opt_answer3_img);
        mIvAnswerOption4Img = findViewById(R.id.opt_answer4_img);
        mIvAnswerOption5Img = findViewById(R.id.opt_answer5_img);
        mIvAnswerOption6Img = findViewById(R.id.opt_answer6_img);
        mIvAnswerOption7Img = findViewById(R.id.opt_answer7_img);
        mIvAnswerOption8Img = findViewById(R.id.opt_answer8_img);
        mIvAnswerOption9Img = findViewById(R.id.opt_answer9_img);
        mIvAnswerOption10Img = findViewById(R.id.opt_answer10_img);
        mIvAnswerOption11Img = findViewById(R.id.opt_answer11_img);
        mIvAnswerOption12Img = findViewById(R.id.opt_answer12_img);
        mIvAnswerOption13Img = findViewById(R.id.opt_answer13_img);
        mIvAnswerOption14Img = findViewById(R.id.opt_answer14_img);
        mIvAnswerOption15Img = findViewById(R.id.opt_answer15_img);
        mIvAnswerOption16Img = findViewById(R.id.opt_answer16_img);
        mIvAnswerOption17Img = findViewById(R.id.opt_answer17_img);
        mIvAnswerOption18Img = findViewById(R.id.opt_answer18_img);
        mIvAnswerOption19Img = findViewById(R.id.opt_answer19_img);
        mIvAnswerOption20Img = findViewById(R.id.opt_answer20_img);
        mIvAnswerOption21Img = findViewById(R.id.opt_answer21_img);
        mIvAnswerOption22Img = findViewById(R.id.opt_answer22_img);
        mIvAnswerOption23Img = findViewById(R.id.opt_answer23_img);
        mIvAnswerOption24Img = findViewById(R.id.opt_answer24_img);
        mIvAnswerOption25Img = findViewById(R.id.opt_answer25_img);
        mIvAnswerOption26Img = findViewById(R.id.opt_answer26_img);
        mIvAnswerOption27Img = findViewById(R.id.opt_answer27_img);
        mIvAnswerOption28Img = findViewById(R.id.opt_answer28_img);
        mIvAnswerOption29Img = findViewById(R.id.opt_answer29_img);
        mIvAnswerOption30Img = findViewById(R.id.opt_answer30_img);
        mIvAnswerOption31Img = findViewById(R.id.opt_answer31_img);
        mIvAnswerOption32Img = findViewById(R.id.opt_answer32_img);
        mIvAnswerOption33Img = findViewById(R.id.opt_answer33_img);
        mIvAnswerOption34Img = findViewById(R.id.opt_answer34_img);
        mIvAnswerOption35Img = findViewById(R.id.opt_answer35_img);
        mIvAnswerOption36Img = findViewById(R.id.opt_answer36_img);
        mIvAnswerOption37Img = findViewById(R.id.opt_answer37_img);
        mIvAnswerOption38Img = findViewById(R.id.opt_answer38_img);
        mIvAnswerOption39Img = findViewById(R.id.opt_answer39_img);
        mIvAnswerOption40Img = findViewById(R.id.opt_answer40_img);

        mTvAnswer1 = findViewById(R.id.answer1);
        mTvAnswer2 = findViewById(R.id.answer2);
        mTvAnswer3 = findViewById(R.id.answer3);
        mTvAnswer4 = findViewById(R.id.answer4);
        mTvAnswer5 = findViewById(R.id.answer5);
        mTvAnswer6 = findViewById(R.id.answer6);
        mTvAnswer7 = findViewById(R.id.answer7);
        mTvAnswer8 = findViewById(R.id.answer8);
        mTvAnswer9 = findViewById(R.id.answer9);
        mTvAnswer10 = findViewById(R.id.answer10);
        mTvAnswer11 = findViewById(R.id.answer11);
        mTvAnswer12 = findViewById(R.id.answer12);
        mTvAnswer13 = findViewById(R.id.answer13);
        mTvAnswer14 = findViewById(R.id.answer14);
        mTvAnswer15 = findViewById(R.id.answer15);
        mTvAnswer16 = findViewById(R.id.answer16);
        mTvAnswer17 = findViewById(R.id.answer17);
        mTvAnswer18 = findViewById(R.id.answer18);
        mTvAnswer19 = findViewById(R.id.answer19);
        mTvAnswer20 = findViewById(R.id.answer20);
        mTvAnswer21 = findViewById(R.id.answer21);
        mTvAnswer22 = findViewById(R.id.answer22);
        mTvAnswer23 = findViewById(R.id.answer23);
        mTvAnswer24 = findViewById(R.id.answer24);
        mTvAnswer25 = findViewById(R.id.answer25);
        mTvAnswer26 = findViewById(R.id.answer26);
        mTvAnswer27 = findViewById(R.id.answer27);
        mTvAnswer28 = findViewById(R.id.answer28);
        mTvAnswer29 = findViewById(R.id.answer29);
        mTvAnswer30 = findViewById(R.id.answer30);
        mTvAnswer31 = findViewById(R.id.answer31);
        mTvAnswer32 = findViewById(R.id.answer32);
        mTvAnswer33 = findViewById(R.id.answer33);
        mTvAnswer34 = findViewById(R.id.answer34);
        mTvAnswer35 = findViewById(R.id.answer35);
        mTvAnswer36 = findViewById(R.id.answer36);
        mTvAnswer37 = findViewById(R.id.answer37);
        mTvAnswer38 = findViewById(R.id.answer38);
        mTvAnswer39 = findViewById(R.id.answer39);
        mTvAnswer40 = findViewById(R.id.answer40);


        mIvAnswer1Img = findViewById(R.id.answer1_img);
        mIvAnswer2Img = findViewById(R.id.answer2_img);
        mIvAnswer3Img = findViewById(R.id.answer3_img);
        mIvAnswer4Img = findViewById(R.id.answer4_img);
        mIvAnswer5Img = findViewById(R.id.answer5_img);
        mIvAnswer6Img = findViewById(R.id.answer6_img);
        mIvAnswer7Img = findViewById(R.id.answer7_img);
        mIvAnswer8Img = findViewById(R.id.answer8_img);
        mIvAnswer9Img = findViewById(R.id.answer9_img);
        mIvAnswer10Img = findViewById(R.id.answer10_img);
        mIvAnswer11Img = findViewById(R.id.answer11_img);
        mIvAnswer12Img = findViewById(R.id.answer12_img);
        mIvAnswer13Img = findViewById(R.id.answer13_img);
        mIvAnswer14Img = findViewById(R.id.answer14_img);
        mIvAnswer15Img = findViewById(R.id.answer15_img);
        mIvAnswer16Img = findViewById(R.id.answer16_img);
        mIvAnswer17Img = findViewById(R.id.answer17_img);
        mIvAnswer18Img = findViewById(R.id.answer18_img);
        mIvAnswer19Img = findViewById(R.id.answer19_img);
        mIvAnswer20Img = findViewById(R.id.answer20_img);
        mIvAnswer21Img = findViewById(R.id.answer21_img);
        mIvAnswer22Img = findViewById(R.id.answer22_img);
        mIvAnswer23Img = findViewById(R.id.answer23_img);
        mIvAnswer24Img = findViewById(R.id.answer24_img);
        mIvAnswer25Img = findViewById(R.id.answer25_img);
        mIvAnswer26Img = findViewById(R.id.answer26_img);
        mIvAnswer27Img = findViewById(R.id.answer27_img);
        mIvAnswer28Img = findViewById(R.id.answer28_img);
        mIvAnswer29Img = findViewById(R.id.answer29_img);
        mIvAnswer30Img = findViewById(R.id.answer30_img);
        mIvAnswer31Img = findViewById(R.id.answer31_img);
        mIvAnswer32Img = findViewById(R.id.answer32_img);
        mIvAnswer33Img = findViewById(R.id.answer33_img);
        mIvAnswer34Img = findViewById(R.id.answer34_img);
        mIvAnswer35Img = findViewById(R.id.answer35_img);
        mIvAnswer36Img = findViewById(R.id.answer36_img);
        mIvAnswer37Img = findViewById(R.id.answer37_img);
        mIvAnswer38Img = findViewById(R.id.answer38_img);
        mIvAnswer39Img = findViewById(R.id.answer39_img);
        mIvAnswer40Img = findViewById(R.id.answer40_img);

        final Animation animShake = AnimationUtils.loadAnimation(this, R.anim.shake_error);


        mRlAnswer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("mRlAnswer1", "selected_option-" + mTvAnswerOption1.getText().toString().trim() +
                        " selected_answer-" + mTvAnswer1.getText().toString().trim() +
                        "\ncorrect_answer-" + op_answer1 +
                        " correct_option-" + op_correct_answer1
                );

                if (mTvAnswerOption1.getText().toString().trim().equalsIgnoreCase(op_correct_answer1)) {
                    Log.e("IF A", "correct");
                    mRlAnswer1.startAnimation(animShake);
                    mRlAnswer1.setBackgroundResource(R.drawable.quiz_correct_answer);
                    mediaPlayer = MediaPlayer.create(QuizScreen.this, resID[0]);
                    mediaPlayer.start();
                } else {
                    Log.e("elseA", "wrong");
                    mRlAnswer1.startAnimation(animShake);
                    mRlAnswer1.setBackgroundResource(R.drawable.quiz_wrong_answer);
                    mediaPlayer = MediaPlayer.create(QuizScreen.this, resID[1]);
                    mediaPlayer.start();
                }
            }
        });
        mRlAnswer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTvAnswerOption2.getText().toString().trim().equalsIgnoreCase(op_correct_answer1)) {
                    Log.e("IF B", "correct");

                    mRlAnswer2.startAnimation(animShake);
                    mRlAnswer2.setBackgroundResource(R.drawable.quiz_correct_answer);
                    mediaPlayer = MediaPlayer.create(QuizScreen.this, resID[0]);
                    mediaPlayer.start();
                } else {
                    Log.e("elseB", "wrong");
                    mRlAnswer2.startAnimation(animShake);
                    mRlAnswer2.setBackgroundResource(R.drawable.quiz_wrong_answer);
                    mediaPlayer = MediaPlayer.create(QuizScreen.this, resID[1]);
                    mediaPlayer.start();
                }
            }
        });
        mRlAnswer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTvAnswerOption3.getText().toString().trim().equalsIgnoreCase(op_correct_answer1)) {
                    Log.e("IF C", "correct");

                    mRlAnswer3.startAnimation(animShake);
                    mRlAnswer3.setBackgroundResource(R.drawable.quiz_correct_answer);
                    mediaPlayer = MediaPlayer.create(QuizScreen.this, resID[0]);
                    mediaPlayer.start();
                } else {
                    Log.e("else C", "wrong");

                    mRlAnswer3.startAnimation(animShake);
                    mRlAnswer3.setBackgroundResource(R.drawable.quiz_wrong_answer);
                    mediaPlayer = MediaPlayer.create(QuizScreen.this, resID[1]);
                    mediaPlayer.start();
                }
            }
        });
        mRlAnswer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTvAnswerOption4.getText().toString().trim().equalsIgnoreCase(op_correct_answer1)) {
                    Log.e("IF D", "correct");
                    mRlAnswer4.startAnimation(animShake);
                    mRlAnswer4.setBackgroundResource(R.drawable.quiz_correct_answer);
                    mediaPlayer = MediaPlayer.create(QuizScreen.this, resID[0]);
                    mediaPlayer.start();
                } else {
                    Log.e("ELse D", "wrong");

                    mRlAnswer4.startAnimation(animShake);
                    mRlAnswer4.setBackgroundResource(R.drawable.quiz_wrong_answer);
                    mediaPlayer = MediaPlayer.create(QuizScreen.this, resID[1]);
                    mediaPlayer.start();
                }
            }
        });


        mIvBackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.no_animation);
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("questionType1_", "-->" + questionType1);
                Log.e("questionType2_", "-->" + questionType2);
                Log.e("questionType3_", "-->" + questionType3);
                Log.e("questionType4_", "-->" + questionType4);
                /*Log.e("questionType5_", "-->" + questionType5);
                Log.e("questionType6_", "-->" + questionType6);
                Log.e("questionType7_", "-->" + questionType7);
                Log.e("questionType8_", "-->" + questionType8);
                Log.e("questionType9_", "-->" + questionType9);
                Log.e("questionType10_", "-->" + questionType10);*/

                /*if (questionType1.equalsIgnoreCase("text")) {
                    if (mLlQuestion1.getVisibility() == View.VISIBLE) {
                       *//* mLlQuestion1.setVisibility(View.GONE);
                        mLlQuestion2.setVisibility(View.VISIBLE);
                        countAns++;*//*
                       // mLlQuestion1.setVisibility(View.VISIBLE);

                    }else{
                        mLlQuestion1.setVisibility(View.GONE);
                    }
                }*/


                if (questionType1.equalsIgnoreCase("image")) {

                }
                if (mLlQuestion1Img.getVisibility() == View.VISIBLE) {
                    mLlQuestion1Img.setVisibility(View.GONE);
                    mLlQuestion2Img.setVisibility(View.VISIBLE);
                    Log.e("mLlQuestion1Img", "already visibile");
                } else if (mLlQuestion2Img.getVisibility() == View.VISIBLE) {
                    mLlQuestion2Img.setVisibility(View.GONE);
                    mLlQuestion3Img.setVisibility(View.VISIBLE);
                    Log.e("mLlQuestion2Img", "already visibile");
                } else if (mLlQuestion3Img.getVisibility() == View.VISIBLE) {
                    mLlQuestion3Img.setVisibility(View.GONE);
                    mLlQuestion4Img.setVisibility(View.VISIBLE);
                    Log.e("mLlQuestion3Img", "already visibile");
                } else if (mLlQuestion4Img.getVisibility() == View.VISIBLE) {
                    mLlQuestion4Img.setVisibility(View.GONE);
                    mLlQuestion5Img.setVisibility(View.VISIBLE);
                    Log.e("mLlQuestion4Img", "already visibile");
                }else{
                    Log.e("mLlllll", "check visibile");
                }


                /*if (questionType1.equalsIgnoreCase("image")) {
                    if (mLlQuestion1Img.getVisibility() == View.VISIBLE) {
                        Log.e("que1", "already visibile 1");
                        mLlQuestion1Img.setVisibility(View.GONE);
                        mLlQuestion2Img.setVisibility(View.VISIBLE);
                        countAns++;
                    } else {
                        //mLlQuestion1Img.setVisibility(View.VISIBLE);
                        Log.e("que1", "do visibile 1");
                    }
                } else if (questionType2.equalsIgnoreCase("image")) {
                    if (mLlQuestion2Img.getVisibility() == View.VISIBLE) {a
                        Log.e("que2", "already visibile 2");
                        mLlQuestion2Img.setVisibility(View.GONE);
                        mLlQuestion3Img.setVisibility(View.VISIBLE);
                        countAns++;
                    } else {
                        //mLlQuestion1Img.setVisibility(View.VISIBLE);
                        Log.e("que2", "do visibile 2");
                    }
                } else if (questionType3.equalsIgnoreCase("image")) {
                    if (mLlQuestion3Img.getVisibility() == View.VISIBLE) {
                        Log.e("que3", "already visibile 3");
                        mLlQuestion3Img.setVisibility(View.GONE);
                        mLlQuestion4Img.setVisibility(View.VISIBLE);
                        countAns++;
                    } else {
                        //mLlQuestion1Img.setVisibility(View.VISIBLE);
                        Log.e("que3", "do visibile 3");
                    }
                }
*/
                /*if (questionType2.equalsIgnoreCase("text")) {
                    if (mLlQuestion2.getVisibility() == View.VISIBLE) {
                        mLlQuestion2.setVisibility(View.GONE);
                        mLlQuestion3.setVisibility(View.VISIBLE);
                        countAns++;
                        Log.e("q5", "-->" + countAns);
                    }
                }

                if (questionType2.equalsIgnoreCase("image")) {
                    if (mLlQuestion2Img.getVisibility() == View.VISIBLE) {
                        mLlQuestion2Img.setVisibility(View.GONE);
                        mLlQuestion3Img.setVisibility(View.VISIBLE);
                        countAns++;
                        Log.e("q7", "-->" + countAns);
                    }
                }

*/










                /*else if (questionType2.equalsIgnoreCase("text")) {
                    if (mLlQuestion2.getVisibility() == View.VISIBLE) {
                        mLlQuestion2.setVisibility(View.GONE);
                        mLlQuestion3.setVisibility(View.VISIBLE);
                        countAns++;
                        Log.e("q3", "-->" + countAns);
                    }
                } else if (questionType2.equalsIgnoreCase("image")) {
                    if (mLlQuestion2Img.getVisibility() == View.VISIBLE) {
                        mLlQuestion2Img.setVisibility(View.GONE);
                        mLlQuestion3Img.setVisibility(View.VISIBLE);
                        countAns++;
                        Log.e("q4", "-->" + countAns);
                    }
                }*/

                /*if (questionType2.equalsIgnoreCase("text")) {
                    if (mLlQuestion2.getVisibility() == View.VISIBLE) {
                        mLlQuestion2.setVisibility(View.GONE);
                        mLlQuestion3.setVisibility(View.VISIBLE);
                        countAns++;
                        Log.e("q2ext", "-->" + countAns);
                    }
                } else {
                    if (mLlQuestion2Img.getVisibility() == View.VISIBLE) {
                        mLlQuestion2Img.setVisibility(View.GONE);
                        mLlQuestion3Img.setVisibility(View.VISIBLE);
                        countAns++;
                        Log.e("q2Img", "-->" + countAns);
                    }
                }

                if (questionType3.equalsIgnoreCase("text")) {
                    if (mLlQuestion3.getVisibility() == View.VISIBLE) {
                        mLlQuestion3.setVisibility(View.GONE);
                        mLlQuestion4.setVisibility(View.VISIBLE);
                        countAns++;
                        Log.e("q3ext", "-->" + countAns);
                    }
                } else {
                    if (mLlQuestion3Img.getVisibility() == View.VISIBLE) {
                        mLlQuestion3Img.setVisibility(View.GONE);
                        mLlQuestion4Img.setVisibility(View.VISIBLE);
                        countAns++;
                        Log.e("q3Img", "-->" + countAns);
                    }
                }*/


                /*if (mLlQuestion1.getVisibility() == View.VISIBLE) {
                    mLlQuestion1.setVisibility(View.GONE);
                    mLlQuestion2.setVisibility(View.VISIBLE);
                    countAns++;
                    Log.e("q1", "-->" + countAns);
                } else if (mLlQuestion2.getVisibility() == View.VISIBLE) {
                    mLlQuestion2.setVisibility(View.GONE);
                    mLlQuestion3.setVisibility(View.VISIBLE);
                    countAns++;
                    Log.e("q2", "-->" + countAns);
                } else if (mLlQuestion3.getVisibility() == View.VISIBLE) {
                    mLlQuestion3.setVisibility(View.GONE);
                    mLlQuestion4.setVisibility(View.VISIBLE);
                    countAns++;
                    Log.e("q3", "-->" + countAns);

                } else if (mLlQuestion4.getVisibility() == View.VISIBLE) {
                    mLlQuestion4.setVisibility(View.GONE);
                    mLlQuestion5.setVisibility(View.VISIBLE);
                    countAns++;
                    Log.e("q4", "-->" + countAns);
                } else if (mLlQuestion5.getVisibility() == View.VISIBLE) {
                    mLlQuestion5.setVisibility(View.GONE);
                    mLlQuestion6.setVisibility(View.VISIBLE);
                    countAns++;
                    Log.e("q5", "-->" + countAns);
                } else if (mLlQuestion6.getVisibility() == View.VISIBLE) {
                    mLlQuestion6.setVisibility(View.GONE);
                    mLlQuestion7.setVisibility(View.VISIBLE);
                    countAns++;
                    Log.e("q6", "-->" + countAns);
                } else if (mLlQuestion7.getVisibility() == View.VISIBLE) {
                    mLlQuestion7.setVisibility(View.GONE);
                    mLlQuestion8.setVisibility(View.VISIBLE);
                    countAns++;
                    Log.e("q7", "-->" + countAns);
                } else if (mLlQuestion8.getVisibility() == View.VISIBLE) {
                    mLlQuestion8.setVisibility(View.GONE);
                    mLlQuestion9.setVisibility(View.VISIBLE);
                    countAns++;
                    Log.e("q8", "-->" + countAns);
                } else if (mLlQuestion9.getVisibility() == View.VISIBLE) {
                    mLlQuestion9.setVisibility(View.GONE);
                    mLlQuestion10.setVisibility(View.VISIBLE);
                    countAns++;
                    Log.e("q9", "-->" + countAns);
                } else if (mLlQuestion10.getVisibility() == View.VISIBLE) {
                    mLlQuestion10.setVisibility(View.GONE);
                    //mLlQuestion5.setVisibility(View.VISIBLE);
                    countAns++;
                    Log.e("q10", "-->" + countAns);
                }*/

            }
        });


        if (Functions.knowInternetOn(this)) {
            //  mTvStandard.setText(std + " : ");
            APIs.getQuizCompExam(this, this, mFunction.getPrefMediumId(), mFunction.getPrefExamId(),
                    mFunction.getPrefPapersId(), mFunction.getPrefSubjectId(), mFunction.getPrefChapterId());
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
                if (strApiName.equalsIgnoreCase("getQuizCompExam")) {
                    if (strStatus != 0) {
                        JSONArray jArrayTextSub = jObj.getJSONArray("quizData");
                        //Toast.makeText(this, "jArrayTextSub Length->"+jArrayTextSub.length(), Toast.LENGTH_SHORT).show();
                        //Log.e("jArrayTextSub", "-->" + jArrayTextSub.toString());
                        if (jArrayTextSub.length() > 0) {

                            JSONObject objj_1 = jArrayTextSub.getJSONObject(0);
                            questionType1 = objj_1.optString("questionType");
                            JSONObject objj_2 = jArrayTextSub.getJSONObject(1);
                            questionType2 = objj_2.optString("questionType");
                            JSONObject objj_3 = jArrayTextSub.getJSONObject(2);
                            questionType3 = objj_3.optString("questionType");
                            JSONObject objj_4 = jArrayTextSub.getJSONObject(3);
                            questionType4 = objj_4.optString("questionType");
                            /*JSONObject objj_5 = jArrayTextSub.getJSONObject(4);
                            questionType5 = objj_5.optString("questionType");
                            JSONObject objj_6 = jArrayTextSub.getJSONObject(5);
                            questionType6 = objj_6.optString("questionType");
                            JSONObject objj_7 = jArrayTextSub.getJSONObject(6);
                            questionType7 = objj_7.optString("questionType");
                            JSONObject objj_8 = jArrayTextSub.getJSONObject(7);
                            questionType8 = objj_8.optString("questionType");
                            JSONObject objj_9 = jArrayTextSub.getJSONObject(8);
                            questionType9 = objj_9.optString("questionType");
                            JSONObject objj_10 = jArrayTextSub.getJSONObject(9);
                            questionType10 = objj_10.optString("questionType");*/


                            Log.e("questionType1_JSON", "-->" + questionType1);
                            Log.e("questionType2_JSON", "-->" + questionType2);
                            Log.e("questionType3_JSON", "-->" + questionType3);
                            Log.e("questionType4_JSON", "-->" + questionType4);
                            /*Log.e("questionType5_JSON", "-->" + questionType5);
                            Log.e("questionType6_JSON", "-->" + questionType6);
                            Log.e("questionType7_JSON", "-->" + questionType7);
                            Log.e("questionType8_JSON", "-->" + questionType8);
                            Log.e("questionType9_JSON", "-->" + questionType9);
                            Log.e("questionType10_JSON", "-->" + questionType10);*/

                            if (questionType1.equalsIgnoreCase("text")) {
                                mLlQuestion1.setVisibility(View.VISIBLE);
                                mLlQuestion1Img.setVisibility(View.GONE);
                                Log.e("questionType1_first", "text");
                            } else {
                                mLlQuestion1.setVisibility(View.GONE);
                                mLlQuestion1Img.setVisibility(View.VISIBLE);
                                Log.e("questionType1_first", "image");
                            }



                            /*if (questionType2.equalsIgnoreCase("text")) {
                                mLlQuestion2.setVisibility(View.VISIBLE);
                                mLlQuestion2Img.setVisibility(View.GONE);
                            } else {
                                mLlQuestion2.setVisibility(View.GONE);
                                mLlQuestion2Img.setVisibility(View.VISIBLE);
                            }*/



                            /*if (questionType3.equalsIgnoreCase("text")) {
                                mLlQuestion3.setVisibility(View.VISIBLE);
                                mLlQuestion3Img.setVisibility(View.GONE);
                            } else {
                                mLlQuestion3.setVisibility(View.GONE);
                                mLlQuestion3Img.setVisibility(View.VISIBLE);
                            }*/



                            /*if (questionType4.equalsIgnoreCase("text")) {
                                mLlQuestion4.setVisibility(View.VISIBLE);
                                mLlQuestion4Img.setVisibility(View.GONE);
                            } else {
                                mLlQuestion4.setVisibility(View.GONE);
                                mLlQuestion4Img.setVisibility(View.VISIBLE);
                            }*/



                            /*if (questionType5.equalsIgnoreCase("text")) {
                                mLlQuestion5.setVisibility(View.VISIBLE);
                                mLlQuestion5Img.setVisibility(View.GONE);
                            } else {
                                mLlQuestion5.setVisibility(View.GONE);
                                mLlQuestion5Img.setVisibility(View.VISIBLE);
                            }*/



                            /*if (questionType6.equalsIgnoreCase("text")) {
                                mLlQuestion6.setVisibility(View.VISIBLE);
                                mLlQuestion6Img.setVisibility(View.GONE);
                            } else {
                                mLlQuestion6.setVisibility(View.GONE);
                                mLlQuestion6Img.setVisibility(View.VISIBLE);
                            }*/



                            /*if (questionType7.equalsIgnoreCase("text")) {
                                mLlQuestion7.setVisibility(View.VISIBLE);
                                mLlQuestion7Img.setVisibility(View.GONE);
                            } else {
                                mLlQuestion7.setVisibility(View.GONE);
                                mLlQuestion7Img.setVisibility(View.VISIBLE);
                            }*/



                           /*if (questionType8.equalsIgnoreCase("text")) {
                                mLlQuestion8.setVisibility(View.VISIBLE);
                                mLlQuestion8Img.setVisibility(View.GONE);
                            } else {
                                mLlQuestion8.setVisibility(View.GONE);
                                mLlQuestion8Img.setVisibility(View.VISIBLE);
                            }*/



                            /*if (questionType9.equalsIgnoreCase("text")) {
                                mLlQuestion9.setVisibility(View.VISIBLE);
                                mLlQuestion9Img.setVisibility(View.GONE);
                            } else {
                                mLlQuestion9.setVisibility(View.GONE);
                                mLlQuestion9Img.setVisibility(View.VISIBLE);
                            }*/


                            /*if (questionType10.equalsIgnoreCase("text")) {
                                mLlQuestion10.setVisibility(View.VISIBLE);
                                mLlQuestion10Img.setVisibility(View.GONE);
                            } else {
                                mLlQuestion10.setVisibility(View.GONE);
                                mLlQuestion10Img.setVisibility(View.VISIBLE);
                            }*/

                            /*Log.e("questionType1", "" + objj_1.optString("questionType"));
                            Log.e("questionType2", "" + objj_2.optString("questionType"));
                            Log.e("questionType3 ", "" + objj_3.optString("questionType"));
                            Log.e("questionType4 ", "" + objj_4.optString("questionType"));
                            Log.e("questionType5 ", "" + objj_5.optString("questionType"));
                            Log.e("questionType6 ", "" + objj_6.optString("questionType"));
                            Log.e("questionType7 ", "" + objj_7.optString("questionType"));
                            Log.e("questionType8 ", "" + objj_8.optString("questionType"));
                            Log.e("questionType9 ", "" + objj_9.optString("questionType"));
                            Log.e("questionType10", "" + objj_10.optString("questionType"));*/

                            if (objj_1.optString("questionType").equalsIgnoreCase("text")) {
                                mTvQuestion1.setText(objj_1.optString("question"));
                                mTvAnswer1.setText(objj_1.optString("option_A"));
                                mTvAnswer2.setText(objj_1.optString("option_B"));
                                mTvAnswer3.setText(objj_1.optString("option_C"));
                                mTvAnswer4.setText(objj_1.optString("option_D"));
                                op_answer1 = objj_1.optString("correct_answer");
                                op_correct_answer1 = objj_1.optString("correct_answer_option");
                                Log.e("correct_answer", "" + objj_1.optString("correct_answer"));
                                Log.e("correct_answer_option", "" + objj_1.optString("correct_answer_option"));
                                Log.e("questionType1", "text");
                            } else {
                                Glide.with(this)
                                        .load(objj_1.optString("question"))
                                        .into(mIvQuestion1);

                                Glide.with(this)
                                        .load(objj_1.optString("option_A"))
                                        .into(mIvAnswer1Img);

                                Glide.with(this)
                                        .load(objj_1.optString("option_B"))
                                        .into(mIvAnswer2Img);

                                Glide.with(this)
                                        .load(objj_1.optString("option_C"))
                                        .into(mIvAnswer3Img);

                                Glide.with(this)
                                        .load(objj_1.optString("option_D"))
                                        .into(mIvAnswer4Img);

                                op_answer1 = objj_1.optString("correct_answer");
                                op_correct_answer1 = objj_1.optString("correct_answer_option");
                                Log.e("questionType1", "image");

                                Log.e("correct_answer", "" + objj_1.optString("correct_answer"));
                                Log.e("correct_answer_option", "" + objj_1.optString("correct_answer_option"));
                            }

                            if (objj_2.optString("questionType").equalsIgnoreCase("text")) {
                                mTvQuestion2.setText(objj_2.optString("question"));
                                mTvAnswer5.setText(objj_2.optString("option_A"));
                                mTvAnswer6.setText(objj_2.optString("option_B"));
                                mTvAnswer7.setText(objj_2.optString("option_C"));
                                mTvAnswer8.setText(objj_2.optString("option_D"));
                                op_answer2 = objj_2.optString("correct_answer");
                                op_correct_answer2 = objj_2.optString("correct_answer_option");
                                Log.e("questionType2", "text");

                                //Log.e("correct_answer", "" + objj_2.optString("correct_answer"));
                                Log.e("correct_answer_option", "" + objj_2.optString("correct_answer_option"));
                            } else {
                                Glide.with(this)
                                        .load(objj_2.optString("question"))
                                        .into(mIvQuestion2);

                                Glide.with(this)
                                        .load(objj_2.optString("option_A"))
                                        .into(mIvAnswer5Img);

                                Glide.with(this)
                                        .load(objj_2.optString("option_B"))
                                        .into(mIvAnswer6Img);

                                Glide.with(this)
                                        .load(objj_2.optString("option_C"))
                                        .into(mIvAnswer7Img);

                                Glide.with(this)
                                        .load(objj_2.optString("option_D"))
                                        .into(mIvAnswer8Img);

                                op_answer2 = objj_2.optString("correct_answer");
                                op_correct_answer2 = objj_2.optString("correct_answer_option");
                                Log.e("questionType2", "image");

                                Log.e("correct_answer", "" + objj_2.optString("correct_answer"));
                                Log.e("correct_answer_option", "" + objj_2.optString("correct_answer_option"));
                            }

                            if (objj_3.optString("questionType").equalsIgnoreCase("text")) {
                                mTvQuestion3.setText(objj_3.optString("question"));
                                mTvAnswer9.setText(objj_3.optString("option_A"));
                                mTvAnswer10.setText(objj_3.optString("option_B"));
                                mTvAnswer11.setText(objj_3.optString("option_C"));
                                mTvAnswer12.setText(objj_3.optString("option_D"));
                                op_answer3 = objj_3.optString("correct_answer");
                                op_correct_answer3 = objj_3.optString("correct_answer_option");
                                Log.e("questionType3", "text");

                            } else {
                                Glide.with(this)
                                        .load(objj_3.optString("question"))
                                        .into(mIvQuestion3);

                                Glide.with(this)
                                        .load(objj_3.optString("option_A"))
                                        .into(mIvAnswer9Img);

                                Glide.with(this)
                                        .load(objj_3.optString("option_B"))
                                        .into(mIvAnswer10Img);

                                Glide.with(this)
                                        .load(objj_3.optString("option_C"))
                                        .into(mIvAnswer11Img);

                                Glide.with(this)
                                        .load(objj_3.optString("option_D"))
                                        .into(mIvAnswer12Img);

                                op_answer3 = objj_3.optString("correct_answer");
                                op_correct_answer3 = objj_3.optString("correct_answer_option");
                                Log.e("questionType3", "image");

                                Log.e("correct_answer", "" + objj_3.optString("correct_answer"));
                                Log.e("correct_answer_option", "" + objj_3.optString("correct_answer_option"));
                            }

                            if (objj_4.optString("questionType").equalsIgnoreCase("text")) {
                                mTvQuestion4.setText(objj_4.optString("question"));
                                mTvAnswer13.setText(objj_4.optString("option_A"));
                                mTvAnswer14.setText(objj_4.optString("option_B"));
                                mTvAnswer15.setText(objj_4.optString("option_C"));
                                mTvAnswer16.setText(objj_4.optString("option_D"));
                                op_answer4 = objj_4.optString("correct_answer");
                                op_correct_answer4 = objj_4.optString("correct_answer_option");
                                Log.e("questionType4", "text");

                            } else {
                                Glide.with(this)
                                        .load(objj_4.optString("question"))
                                        .into(mIvQuestion4);

                                Glide.with(this)
                                        .load(objj_4.optString("option_A"))
                                        .into(mIvAnswer13Img);

                                Glide.with(this)
                                        .load(objj_4.optString("option_B"))
                                        .into(mIvAnswer14Img);

                                Glide.with(this)
                                        .load(objj_4.optString("option_C"))
                                        .into(mIvAnswer15Img);

                                Glide.with(this)
                                        .load(objj_4.optString("option_D"))
                                        .into(mIvAnswer16Img);

                                op_answer4 = objj_4.optString("correct_answer");
                                op_correct_answer4 = objj_4.optString("correct_answer_option");
                                Log.e("questionType4", "image");

                                Log.e("correct_answer", "" + objj_4.optString("correct_answer"));
                                Log.e("correct_answer_option", "" + objj_4.optString("correct_answer_option"));
                            }

                            /*if (objj_5.optString("questionType").equalsIgnoreCase("text")) {
                                mTvQuestion5.setText(objj_5.optString("question"));
                                mTvAnswer17.setText(objj_5.optString("option_A"));
                                mTvAnswer18.setText(objj_5.optString("option_B"));
                                mTvAnswer19.setText(objj_5.optString("option_C"));
                                mTvAnswer20.setText(objj_5.optString("option_D"));
                                op_answer5 = objj_5.optString("correct_answer");
                                op_correct_answer5 = objj_5.optString("correct_answer_option");
                            } else {*/
                                /*Glide.with(this)
                                        .load(objj_5.optString("question"))
                                        .into(mIvQuestion5);

                                Glide.with(this)
                                        .load(objj_5.optString("option_A"))
                                        .into(mIvAnswer17Img);

                                Glide.with(this)
                                        .load(objj_5.optString("option_B"))
                                        .into(mIvAnswer18Img);

                                Glide.with(this)
                                        .load(objj_5.optString("option_C"))
                                        .into(mIvAnswer19Img);

                                Glide.with(this)
                                        .load(objj_5.optString("option_D"))
                                        .into(mIvAnswer20Img);

                                op_answer5 = objj_5.optString("correct_answer");
                                op_correct_answer5 = objj_5.optString("correct_answer_option");
                                Log.e("correct_answer", "" + objj_5.optString("correct_answer"));
                                Log.e("correct_answer_option", "" + objj_5.optString("correct_answer_option"));
                            }*/


                            /*mFunction.SetPrefQuiz(objj_1.optString("question"),
                                    objj_1.optString("correct_answer"),
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
                            //mTvNoQuiz.setVisibility(View.VISIBLE);
                            //mLlquestionGrp.setVisibility(View.INVISIBLE);
                            //btnNextquestion.setText(View.GONE);
                        }
                    } else {
                        Functions.ToastUtility(QuizScreen.this, strMessage);
                        //mTvNoQuiz.setVisibility(View.VISIBLE);
                        //mLlquestionGrp.setVisibility(View.INVISIBLE);
                        // btnNextquestion.setText(View.GONE);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}