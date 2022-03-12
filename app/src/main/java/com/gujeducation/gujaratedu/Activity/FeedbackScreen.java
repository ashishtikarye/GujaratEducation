package com.gujeducation.gujaratedu.Activity;

import static com.gujeducation.gujaratedu.Helper.Functions.isEmptyEdittext;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gujeducation.R;
import com.gujeducation.gujaratedu.Helper.Connection;
import com.gujeducation.gujaratedu.Helper.Functions;
import com.gujeducation.gujaratedu.Interface.OnResult;
import com.gujeducation.gujaratedu.ServerAPIs.APIs;

import org.json.JSONObject;

import java.util.List;


/**
 * Created by Tikarye Ashish on 19/01/2019.
 */


public class FeedbackScreen extends AppCompatActivity implements OnResult {

    RecyclerView recyclerViewStandard;
    Functions mFunction;
    SharedPreferences mPreference;
    LinearLayoutManager mLayoutManager;
    GridLayoutManager mGridLayoutManager;
    AppCompatImageView mIvBack;
    LinearLayout mLlFeedback, mLlLanguage;
    AppCompatTextView mTitleFedback, mLlEnglish, mLlGujarati, mLlHindi, mTvSelectCategory, mTvGoodThings, mTvLoginProblem, mTvSuggestion, mTvNewTabInfo, mTvComplaint, mTvHelpMSB, mTvOthers, mTvDialogtitle;
    String getUserID;
    RelativeLayout mRlSpinner;
    List<String> categories;
    Dialog dialogFeedback, dialogFeedbackCategory;
    AppCompatEditText mEdFullName, mEdSchoolName, mEdMobileNo, mEdEmailId, mEdFeedback;
    AppCompatButton btnSubmit;
    RatingBar simpleRatingBar;
    String langCode = "";

    Spinner spFeedback;
    //boolean val_login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_feedback);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }

        // mPreference = getSharedPreferences(getResources().getString(R.string.app_name), MODE_PRIVATE);
        mFunction = new Functions(this);
        //  val_login = mPreference.getBoolean(Connection.TAG_ISLOGIN, false);
        //Log.e("USER_ID", ""+mFunction.getPrefUserId());
        // Log.e("val_login", String.valueOf(val_login));
        // com.samitiapp.Helper.Language.set(FeedbackScreen.this, mFunction.getPrefLanguageCode());
        //Log.e("FeedbackScreen", "getPrefLanguageCode-->" + mFunction.getPrefLanguageCode());
        //Log.e("FeedbackScreen", "-->" + mFunction.getFlagLogCode());

        mLlFeedback = findViewById(R.id.lvfeedbackcategory);
        mRlSpinner = findViewById(R.id.rlvspinner);
        mTvSelectCategory = findViewById(R.id.tvselectcategory);
        mTvSelectCategory.setInputType(InputType.TYPE_NULL);
        simpleRatingBar = findViewById(R.id.ratingBar);

        mEdFullName = findViewById(R.id.etfullname);
        mEdSchoolName = findViewById(R.id.etschoolname);
        mEdMobileNo = findViewById(R.id.etmobilno);
        mEdEmailId = findViewById(R.id.etemailid);
        mEdFeedback = findViewById(R.id.etfeedback);
        btnSubmit = findViewById(R.id.btnSubmit);
        mTitleFedback = findViewById(R.id.fdcatetitle);


        showLanguagePopup();
        mRlSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFeedback();
            }
        });

        mIvBack = findViewById(R.id.ivback);

        if (Functions.knowInternetOn(this)) {
            APIs.getMyAccount(FeedbackScreen.this, this, mFunction.getPrefUserId());
        } else {
            Functions.showInternetAlert(this);
        }

        /*Log.e("FeedbackScreen", "mediumId-->" + mFunction.getMediumId() +
                "mediumId_guest-->" + mFunction.getMediumId());*/


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rating = "" + simpleRatingBar.getRating();
                //Log.e("rating", "===>" + rating);
                //Log.e("SelectCategory", "===>" + mTvSelectCategory.getText().toString().trim());
                if (mTvSelectCategory.getText().toString().trim().equalsIgnoreCase("")) {
                    Functions.ToastUtility(FeedbackScreen.this, "Please Select Feedback Category");
                } else if (isEmptyEdittext(mEdFullName)) {
                    Functions.ToastUtility(FeedbackScreen.this, "Please Enter Full Name");
                } else if (isEmptyEdittext(mEdMobileNo)) {
                    Functions.ToastUtility(FeedbackScreen.this, "Please Enter Mobileno");
                } else if (isEmptyEdittext(mEdEmailId)) {
                    Functions.ToastUtility(FeedbackScreen.this, "Please Enter EmailId");
                } else if (isEmptyEdittext(mEdSchoolName)) {
                    Functions.ToastUtility(FeedbackScreen.this, "Please Enter School Name");
                } else if (isEmptyEdittext(mEdFeedback)) {
                    Functions.ToastUtility(FeedbackScreen.this, "Please Enter Your Feedback");
                } else if (rating.equalsIgnoreCase("0.0")) {
                    Functions.ToastUtility(FeedbackScreen.this, "Please Select Rating");
                } else {
                    if (Functions.knowInternetOn(FeedbackScreen.this)) {
                        APIs.Feedback(FeedbackScreen.this, FeedbackScreen.this,
                                mTvSelectCategory.getText().toString().trim(),
                                mEdMobileNo.getText().toString().trim(),
                                mEdFullName.getText().toString().trim(),
                                mEdSchoolName.getText().toString().trim(),
                                mEdEmailId.getText().toString().trim(),
                                rating,
                                mEdFeedback.getText().toString().trim());
                    } else {
                        Functions.showInternetAlert(FeedbackScreen.this);
                    }
                }

            }
        });

        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FeedbackScreen.this, HomeScreen.class));
                overridePendingTransition(R.anim.no_animation, R.anim.slide_out_left);
                finish();
            }
        });


    }


    public void showLanguagePopup() {

        dialogFeedback = new Dialog(
                this, R.style.popupTheme);
        LayoutInflater inflater = (LayoutInflater)
                this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popup_language, null);
        dialogFeedback.setContentView(view); // your custom view.
        dialogFeedback.setCancelable(false);
        dialogFeedback.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialogFeedback.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        mLlEnglish = view.findViewById(R.id.llenglish);
        mLlGujarati = view.findViewById(R.id.llgujarati);
        mLlHindi = view.findViewById(R.id.llhindi);


        dialogFeedback.show();

        mLlEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mFunction.SetPrefLanguage("en");
                //com.samitiapp.Helper.Language.set(FeedbackScreen.this, "en");
                langCode = "en";
                mTitleFedback.setText("Feedback");
                mEdFullName.setHint("Full Name");
                mEdSchoolName.setHint("School Name");
                mEdMobileNo.setHint("Mobile No");
                mEdEmailId.setHint("EmailId");
                mEdFeedback.setHint("Write Your Feedback");
                btnSubmit.setText("Submit");
                dialogFeedback.dismiss();
            }
        });
        mLlGujarati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // mFunction.SetPrefLanguage("gu");
                // com.samitiapp.Helper.Language.set(FeedbackScreen.this, "gu");
                langCode = "gu";
                mTitleFedback.setText("અભિપ્રાય શ્રેણી");
                mEdFullName.setHint("પૂરું નામ");
                mEdSchoolName.setHint("શાળાનું નામ");
                mEdMobileNo.setHint("મો.નંબર");
                mEdEmailId.setHint("ઈમેલ આઈડી");
                mEdFeedback.setHint("અભિપ્રાય લખો");
                btnSubmit.setText("સંગ્રહિત કરો");
                dialogFeedback.dismiss();

            }
        });
        mLlHindi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //    mFunction.SetPrefLanguage("hi");
                //   com.samitiapp.Helper.Language.set(FeedbackScreen.this, "hi");
                langCode = "hi";
                mTitleFedback.setText("प्रतिक्रिया श्रेणी");
                mEdFullName.setHint("पूरा नाम");
                mEdSchoolName.setHint("विद्यालय का नाम");
                mEdMobileNo.setHint("मोबाइल नंबर");
                mEdEmailId.setHint("ईमेल आईडी");
                mEdFeedback.setHint("अपनी प्रतिक्रिया लिखें");
                btnSubmit.setText("बचाना");
                dialogFeedback.dismiss();

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
                String strApi = jObj.optString("APIName");
                Log.e("ApiName->", strApi);

                if (strApi.equalsIgnoreCase("getMyAccount")) {
                    if (strStatus != 0) {
                        mEdFullName.setText(jObj.optString("fullname"));
                        mEdSchoolName.setText(jObj.optString("schoolName"));
                        mEdMobileNo.setText(jObj.optString("mobile"));
                        mEdEmailId.setText(jObj.optString("email"));
                    } else {
                        Functions.ToastUtility(FeedbackScreen.this, strMessage);
                    }
                } else if (strApi.equalsIgnoreCase("feedback")) {
                    if (strStatus == 1) {
                        Intent intent = new Intent(FeedbackScreen.this, HomeScreen.class);
                        startActivity(intent);
                        finish();
                        Functions.ToastUtility(FeedbackScreen.this, strMessage);
                    } else {
                        Functions.ToastUtility(FeedbackScreen.this, strMessage);

                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showFeedback() {

        dialogFeedbackCategory = new Dialog(
                this, R.style.popupTheme);
        LayoutInflater inflater = (LayoutInflater)
                this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popup_feedbackcategory, null);
        dialogFeedbackCategory.setContentView(view); // your custom view.
        dialogFeedbackCategory.setCancelable(false);
        dialogFeedbackCategory.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialogFeedbackCategory.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        mTvLoginProblem = view.findViewById(R.id.tvloginproblem);
        mTvSuggestion = view.findViewById(R.id.tvsuggestion);
        mTvGoodThings = view.findViewById(R.id.tvgoodthings);
        mTvHelpMSB = view.findViewById(R.id.tvhelpmsb);
        mTvNewTabInfo = view.findViewById(R.id.tvnewtabinfo);
        mTvOthers = view.findViewById(R.id.tvothers);
        mTvComplaint = view.findViewById(R.id.tvcomplaint);
        mTvDialogtitle = view.findViewById(R.id.dialogtitle);
        //Log.e("langCode", "===>" + langCode);


        if (langCode.equalsIgnoreCase("en")) {
            mTvDialogtitle.setText("Feedback Category");
            mTvGoodThings.setText("Good Things");
            mTvLoginProblem.setText("Login Problem");
            mTvSuggestion.setText("Suggestion");
            mTvNewTabInfo.setText("Add New Tab or Information");
            mTvHelpMSB.setText("Can I help GujaratEducation MSB");
            mTvComplaint.setText("Complaint");
            mTvOthers.setText("Others");
        }
        if (langCode.equalsIgnoreCase("gu")) {
            mTvDialogtitle.setText("અભિપ્રાય શ્રેણી");
            mTvGoodThings.setText("સારી બાબતોના સંધર્ભમાં");
            mTvLoginProblem.setText("લોગીન પ્રોબ્લેમ સંધર્ભમાં");
            mTvSuggestion.setText("સુધારો કરવા સંધાર્ભમાં");
            mTvNewTabInfo.setText("નવી માહિતી ઉમેરવા બાબત");
            mTvHelpMSB.setText("સહયોગનાં સંધર્ભમાં");
            mTvComplaint.setText("ફરિયાદ");
            mTvOthers.setText("અન્ય");
        }
        if (langCode.equalsIgnoreCase("hi")) {
            mTvDialogtitle.setText("प्रतिक्रिया श्रेणी");
            mTvGoodThings.setText("अच्छी बातें");
            mTvLoginProblem.setText("लॉगिन समस्या");
            mTvSuggestion.setText("सुझाव");
            mTvNewTabInfo.setText("नयी जानकारी जोड़ें");
            mTvHelpMSB.setText("सहयोग के संधर्भ में");
            mTvComplaint.setText("शिकायत");
            mTvOthers.setText("अन्य");
        }

        /*mTvGoodThings.setText(getResources().getString(R.string.fdgoodthings));
        mTvLoginProblem.setText(getResources().getString(R.string.fdloginproblem));
        mTvSuggestion.setText(getResources().getString(R.string.fdsuggestion));
        mTvNewTabInfo.setText(getResources().getString(R.string.fcnewtabinfo));
        mTvHelpMSB.setText(getResources().getString(R.string.fdhelpmsb));
        mTvComplaint.setText(getResources().getString(R.string.fdcomplaint));
        mTvOthers.setText(getResources().getString(R.string.fdothers));*/


        dialogFeedbackCategory.show();

        mTvLoginProblem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (langCode.equalsIgnoreCase("en")) {
                    mTvSelectCategory.setText("Login Problem");
                }
                if (langCode.equalsIgnoreCase("gu")) {
                    mTvSelectCategory.setText("લોગીન પ્રોબ્લેમ સંધર્ભમાં");

                }
                if (langCode.equalsIgnoreCase("hi")) {
                    mTvSelectCategory.setText("लॉगिन समस्या");

                }
                dialogFeedbackCategory.dismiss();
            }
        });
        mTvSuggestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (langCode.equalsIgnoreCase("en")) {
                    mTvSelectCategory.setText("Suggestion");
                }
                if (langCode.equalsIgnoreCase("gu")) {
                    mTvSelectCategory.setText("સુધારો કરવા સંધાર્ભમાં");

                }
                if (langCode.equalsIgnoreCase("hi")) {
                    mTvSelectCategory.setText("सुझाव");

                }


                dialogFeedbackCategory.dismiss();

            }
        });
        mTvGoodThings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (langCode.equalsIgnoreCase("en")) {
                    mTvSelectCategory.setText("Good Things");
                }
                if (langCode.equalsIgnoreCase("gu")) {
                    mTvSelectCategory.setText("સારી બાબતોના સંધર્ભમાં");

                }
                if (langCode.equalsIgnoreCase("hi")) {
                    mTvSelectCategory.setText("अच्छी बातें");

                }
                dialogFeedbackCategory.dismiss();
            }
        });

        mTvHelpMSB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (langCode.equalsIgnoreCase("en")) {
                    mTvSelectCategory.setText("Can I help GujaratEducation");
                }
                if (langCode.equalsIgnoreCase("gu")) {
                    mTvSelectCategory.setText("સહયોગનાં સંધર્ભમાં");

                }
                if (langCode.equalsIgnoreCase("hi")) {
                    mTvSelectCategory.setText("सहयोग के संधर्भ में");

                }
                dialogFeedbackCategory.dismiss();
            }
        });

        mTvOthers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (langCode.equalsIgnoreCase("en")) {
                    mTvSelectCategory.setText("Others");
                }
                if (langCode.equalsIgnoreCase("gu")) {
                    mTvSelectCategory.setText("અન્ય");

                }
                if (langCode.equalsIgnoreCase("hi")) {
                    mTvSelectCategory.setText("अन्य");

                }
                dialogFeedbackCategory.dismiss();
            }
        });

        mTvComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (langCode.equalsIgnoreCase("en")) {
                    mTvSelectCategory.setText("Complaint");
                }
                if (langCode.equalsIgnoreCase("gu")) {
                    mTvSelectCategory.setText("ફરિયાદ");

                }
                if (langCode.equalsIgnoreCase("hi")) {
                    mTvSelectCategory.setText("शिकायत");

                }
                dialogFeedbackCategory.dismiss();
            }
        });

        mTvNewTabInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (langCode.equalsIgnoreCase("en")) {
                    mTvSelectCategory.setText("Add New Tab or Information");
                }
                if (langCode.equalsIgnoreCase("gu")) {
                    mTvSelectCategory.setText("નવી માહિતી ઉમેરવા બાબત");

                }
                if (langCode.equalsIgnoreCase("hi")) {
                    mTvSelectCategory.setText("नयी जानकारी जोड़ें");

                }
                dialogFeedbackCategory.dismiss();
            }
        });


    }


}
