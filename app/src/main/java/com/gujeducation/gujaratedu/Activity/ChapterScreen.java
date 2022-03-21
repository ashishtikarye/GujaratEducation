package com.gujeducation.gujaratedu.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.gujeducation.R;
import com.gujeducation.gujaratedu.Adapter.ChapterAdapter;
import com.gujeducation.gujaratedu.Adapter.SubOptionAdapter;
import com.gujeducation.gujaratedu.Helper.Connection;
import com.gujeducation.gujaratedu.Helper.Functions;
import com.gujeducation.gujaratedu.Interface.OnResult;
import com.gujeducation.gujaratedu.Model.Chapter;
import com.gujeducation.gujaratedu.Model.ExamChapterOptionList;
import com.gujeducation.gujaratedu.Model.SubjectOptionMenu;
import com.gujeducation.gujaratedu.ServerAPIs.APIs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ChapterScreen extends AppCompatActivity implements OnResult {

    private final ArrayList<Chapter> listArrChapter = new ArrayList<Chapter>();
    private final ArrayList<SubjectOptionMenu> listArrSubjectMenu = new ArrayList<SubjectOptionMenu>();
    Intent intent;
    CollapsingToolbarLayout collapsingToolbarLayout;
    String subject, strMadeBy, strCreditLicence, strGcrtImg;
    ChapterAdapter chapterAdapter;
    SubOptionAdapter mSubOptionAdapter;
    Functions mFunctions;
    LinearLayoutManager mLayoutManager;
    Date date;
    SimpleDateFormat dateFormat;
    ImageView mIvGcrt;
    AppCompatButton btnMoreInfo;
    public static Dialog dialogList;
    AppCompatActivity activity;
    private final ArrayList<ExamChapterOptionList> listArrSubOption = new ArrayList<ExamChapterOptionList>();
    private RecyclerView recyclerChapter, recyclerViewDialog;
    AdView mAdView;
    public InterstitialAd interstitialAd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);
        activity = this;
        mFunctions = new Functions(this);

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        interstitialAd = new InterstitialAd(ChapterScreen.this);
        //interstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        //interstitialAd.setAdUnitId("ca-app-pub-6923797063551368/3272831029");
        //loadInterstitialAd();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        collapsingToolbarLayout = findViewById(R.id.collapsingToolbar);
        btnMoreInfo = findViewById(R.id.btnstartquiz);

        //Log.e("getStandardIdentify", "--->" + mFunctions.getStandardIdentify());
        if (mFunctions.getStandardIdentify() == 1) {
            btnMoreInfo.setText(R.string.Pri_primary_quize);
        } else {
            btnMoreInfo.setText(R.string.more_info);
        }
        mIvGcrt = findViewById(R.id.ivGcrt);


        intent = getIntent();
        if (intent.hasExtra("subject") && intent.hasExtra("madeBy") && intent.hasExtra("creditsAndLicence")
                && intent.hasExtra("GcrtImage")) {
            subject = intent.getExtras().getString("subject");
            strMadeBy = intent.getExtras().getString("madeBy");
            strCreditLicence = intent.getExtras().getString("creditsAndLicence");
            strGcrtImg = intent.getExtras().getString("GcrtImage");
        } else {
            subject = "Unknown";
            strMadeBy = "Unknown";
            strCreditLicence = "Unknown";
            strGcrtImg = "";
        }

        /*Log.e("ChapterIntent", " subject->" + subject +
                " strMadeBy->" + strMadeBy +
                " strCreditLicence->" + strCreditLicence +
                " strGcrtImg->" + strGcrtImg
        );*/

        // mTvMadeBy.setText(strMadeBy);
        //mTvCreditorLisence.setText("Licence By : "+strCreditLicence);

        Glide.with(this)
                .load(strGcrtImg)
                .into(mIvGcrt);


        collapsingToolbarLayout.setTitle(subject);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*intent = new Intent(ChapterScreen.this, ClassScreen.class);
                startActivity(intent);*/
                finish();
            }
        });

        if (btnMoreInfo.getText().equals(R.string.Pri_primary_quize)) {
            btnMoreInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //showInterstitialAd();
                    Toast.makeText(ChapterScreen.this, "Quiz In Progress", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            btnMoreInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Functions.knowInternetOn(ChapterScreen.this)) {
                        APIs.getSubjectOptionList(ChapterScreen.this, ChapterScreen.this, mFunctions.getPrefMediumId(), mFunctions.getSubjectId());
                    } else {
                        Functions.showInternetAlert(ChapterScreen.this);
                    }
                }
            });
        }


        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerChapter = findViewById(R.id.recyclerview_chapter);
        recyclerChapter.setHasFixedSize(true);
        recyclerChapter.setLayoutManager(mLayoutManager);

        if (Functions.knowInternetOn(this)) {
            APIs.getChapter(ChapterScreen.this, this, mFunctions.getPrefMediumId(), mFunctions.getSection(), mFunctions.getSemester(), mFunctions.getStandardId(), mFunctions.getSubjectId());
        } else {
            Functions.showInternetAlert(this);
        }
    }

    public void showSubjectList() {

        dialogList = new Dialog(
                this, R.style.popupTheme);
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_recyclerview, null);
        dialogList.setContentView(view); // your custom view.
        dialogList.setCancelable(true);
        dialogList.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialogList.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialogList.show();
        //countryHading = (TextView) view.findViewById(R.id.country_search_heading);
        //mEt_SearchBox = (EditText) view.findViewById(R.id.dialog_search_autocomplete);
        recyclerViewDialog = (RecyclerView) view.findViewById(R.id.recyclerview_popup);
        mLayoutManager = new LinearLayoutManager(this);

        recyclerViewDialog.setHasFixedSize(true);
        recyclerViewDialog.setLayoutManager(mLayoutManager);

        if (listArrSubOption.size() != 0) {
            mSubOptionAdapter = new SubOptionAdapter(ChapterScreen.this, listArrSubOption);
            recyclerViewDialog.setAdapter(mSubOptionAdapter);
            mSubOptionAdapter.notifyDataSetChanged();
            recyclerViewDialog.setVisibility(View.VISIBLE);
        }


    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        //Log.e("Menu", "onPrepareOptionsMenu");
        menu.clear();
        if (listArrSubjectMenu.size() != 0) {
            for (int i = 0; i < listArrSubjectMenu.size(); i++) {
                //Log.e("Menu", "onPrepareOptionsMenuInsideFor");
                SubjectOptionMenu optionMenu = listArrSubjectMenu.get(i);
                menu.add(0, optionMenu.getMenuId(), Menu.NONE, optionMenu.getMenuName());
            }
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        date = Calendar.getInstance().getTime();
        dateFormat = new SimpleDateFormat("dd-mm-yyyy", Locale.getDefault());

        switch (item.getItemId()) {
            case 1:
                Toast.makeText(this, "" + item.getItemId(), Toast.LENGTH_SHORT).show();
                intent = new Intent(ChapterScreen.this, PdfScreen.class);
                SubjectOptionMenu optionMenu = listArrSubjectMenu.get((item.getItemId() - 1));
                //Log.e("getMenuName", "=======>" + dateFormat.format(date) + "_" + optionMenu.getMenuName());
                //Log.e("getPdfName", "=======>" + optionMenu.getPdfName());
                intent.putExtra("Name", dateFormat.format(date) + "_" + optionMenu.getMenuName());
                intent.putExtra("PDF", optionMenu.getPdfName());
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onResult(JSONObject jobjWhole) {
        try {
            if (jobjWhole != null) {
                JSONObject jObj = jobjWhole.optJSONObject(Connection.TAG_DATA);
                int strStatus = jObj.optInt("success");
                String strMessage = jObj.optString("message");
                String strApi = jObj.optString("api");//getChapter //getSubjectOptionList

                if (strApi.equalsIgnoreCase("getChapter")) {
                    if (strStatus != 0) {
                        JSONArray jArrayChapter = jObj.getJSONArray("chapter");
                        if (jArrayChapter.length() > 0) {
                            listArrChapter.clear();
                            for (int i = 0; i < jArrayChapter.length(); i++) {
                                try {
                                    JSONObject obj = jArrayChapter.getJSONObject(i);
                                    listArrChapter.add(new Chapter(
                                            obj.optInt("chapterId"),
                                            obj.optInt("semesterId"),
                                            obj.optString("chapter_no"),
                                            obj.optInt("subjectId"),
                                            obj.optString("chapter")
                                            //Image as a pdf
                                    ));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (listArrChapter.size() != 0) {
                                chapterAdapter = new ChapterAdapter(this, listArrChapter);
                                recyclerChapter.setAdapter(chapterAdapter);
                            }
                        }

                    } else {
                        Functions.ToastUtility(ChapterScreen.this, strMessage);
                        //recyclerViewLanguage.setVisibility(View.GONE);
                    }
                } else if (strApi.equalsIgnoreCase("getSubjectOptionList")) {
                    if (strStatus != 0) {
                        JSONArray jArraySubList = jObj.getJSONArray("subjectOptionList");
                        if (jArraySubList.length() > 0) {
                            listArrSubOption.clear();
                            for (int i = 0; i < jArraySubList.length(); i++) {
                                try {
                                    JSONObject obj = jArraySubList.getJSONObject(i);
                                    listArrSubOption.add(new ExamChapterOptionList(
                                            obj.optString("option")
                                    ));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            showSubjectList();
                        }

                    } else {
                        Functions.ToastUtility(ChapterScreen.this, strMessage);
                        //recyclerViewLanguage.setVisibility(View.GONE);
                    }
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadInterstitialAd() {
        // Creating  a Ad Request
        AdRequest adRequest = new AdRequest.Builder().build();

        // load Ad with the Request
        interstitialAd.loadAd(adRequest);

        // Showing a simple Toast message to user when an ad is Loading
        //Toast.makeText(HomeScreen.this, "Interstitial Ad is loading ", Toast.LENGTH_LONG).show();
    }


    public void showInterstitialAd() {
        if (interstitialAd.isLoaded()) {
            //showing the ad Interstitial Ad if it is loaded
            interstitialAd.show();

            // Showing a simple Toast message to user when an Interstitial ad is shown to the user
            //Toast.makeText(HomeScreen.this, "Interstitial is loaded and showing ad  ", Toast.LENGTH_LONG).show();
        } else {
            //Load the Interstitial ad if it is not loaded
            //loadInterstitialAd();

            // Showing a simple Toast message to user when an ad is not loaded
            //Toast.makeText(HomeScreen.this, "Interstitial Ad is not Loaded ", Toast.LENGTH_LONG).show();
        }
    }
}
