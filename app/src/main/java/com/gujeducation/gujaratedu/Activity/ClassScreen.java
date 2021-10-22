package com.gujeducation.gujaratedu.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;
import com.gujeducation.R;
import com.gujeducation.gujaratedu.Adapter.TabAdapter;
import com.gujeducation.gujaratedu.Fragment.Tab1Fragment;
import com.gujeducation.gujaratedu.Fragment.Tab2Fragment;
import com.gujeducation.gujaratedu.Helper.Connection;
import com.gujeducation.gujaratedu.Helper.Functions;
import com.gujeducation.gujaratedu.Interface.OnResult;
import com.gujeducation.gujaratedu.Model.Subject;
import com.gujeducation.gujaratedu.ServerAPIs.APIs;
import com.vincent.filepicker.ToastUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ClassScreen extends AppCompatActivity implements OnResult {

    private final ArrayList<Subject> listArrSubject = new ArrayList<Subject>();
    Intent intent;
    CollapsingToolbarLayout collapsingToolbarLayout;
    String standard;
    Functions mFunctions;
    ViewPager mViewPAger;
    TabAdapter mViewPagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);

        mFunctions = new Functions(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        collapsingToolbarLayout = findViewById(R.id.collapsingToolbar);


        intent = getIntent();
        try {
            standard = intent.getStringExtra("class");
            if (standard.length() > 2) {
                collapsingToolbarLayout.setTitle(standard);
            } else {
                collapsingToolbarLayout.setTitle(getResources().getString(R.string._class) +
                        " " + standard);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        mViewPAger = findViewById(R.id.viewpager);
        mViewPagerAdapter = new TabAdapter(getSupportFragmentManager());

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPAger);

        if (Functions.knowInternetOn(this)) {
            APIs.getSubject(ClassScreen.this, this, mFunctions.getPrefMediumId(), mFunctions.getSection(), mFunctions.getStandardId());
        } else {
            Functions.showInternetAlert(this);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(ClassScreen.this, AvailableClassScreen.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        intent = new Intent(ClassScreen.this, AvailableClassScreen.class);
        startActivity(intent);
    }

    @Override
    public void onResult(JSONObject jobjWhole) {
        try {
            if (jobjWhole != null) {
                JSONObject jObj = jobjWhole.optJSONObject(Connection.TAG_DATA);
                int strStatus = jObj.optInt("success");
                String strMessage = jObj.optString("message");

                if (strStatus != 0) {
                    JSONArray jArraySection = jObj.getJSONArray("subject");
                    if (jArraySection.length() > 0) {
                        listArrSubject.clear();
                        for (int i = 0; i < jArraySection.length(); i++) {
                            try {
                                JSONObject obj = jArraySection.getJSONObject(i);
                                listArrSubject.add(new Subject(
                                        obj.optInt("subjectId"),
                                        obj.optInt("sectionId"),
                                        obj.optInt("semesterId"),
                                        obj.optInt("semesterId"),
                                        obj.optString("subject").trim(),
                                        obj.getString("semester"),
                                        obj.getString("madeBy"),
                                        obj.getString("creditsAndLicence"),
                                        obj.getString("GcrtImage")

                                ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        if (listArrSubject.size() != 0) {
                            //Toast.makeText(this, ""+listArrSubject.size(), Toast.LENGTH_SHORT).show();
                            ArrayList<Subject> Sem1 = new ArrayList<Subject>();
                            ArrayList<Subject> Sem2 = new ArrayList<Subject>();
                            //Toast.makeText(this, ""+listArrSubject.get(0).getSemesterTag(), Toast.LENGTH_SHORT).show();
                            //Log.e("Data",""+subjectsList.getSemesterTag());
                            for (int i = 0; i < listArrSubject.size(); i++) {
                                Subject subjectsList = listArrSubject.get(i);
                                if (subjectsList.getSemesterTag().equalsIgnoreCase("sem1")) {
                                    Sem1.add(new Subject(
                                            subjectsList.getSubjectId(),
                                            subjectsList.getSectionId(),
                                            subjectsList.getSemesterId(),
                                            subjectsList.getStandardId(),
                                            subjectsList.getSubjectName(),
                                            subjectsList.getSemesterTag(),
                                            subjectsList.getMadeBy(),
                                            subjectsList.getCreditsAndLicence(),
                                            subjectsList.getGcrtImage()

                                    ));
                                } else if (subjectsList.getSemesterTag().equalsIgnoreCase("sem2")) {
                                    Sem2.add(new Subject(
                                            subjectsList.getSubjectId(),
                                            subjectsList.getSectionId(),
                                            subjectsList.getSemesterId(),
                                            subjectsList.getStandardId(),
                                            subjectsList.getSubjectName(),
                                            subjectsList.getSemesterTag(),
                                            subjectsList.getMadeBy(),
                                            subjectsList.getCreditsAndLicence(),
                                            subjectsList.getGcrtImage()
                                    ));
                                }
                            }
                            if ((Sem1.size() != 0) && (Sem2.size() != 0)) {
                                mViewPagerAdapter.addFragment(new Tab1Fragment(Sem1), getResources().getString(R.string.semester) +
                                        " " + getResources().getString(R.string.class_1));
                                mViewPagerAdapter.addFragment(new Tab2Fragment(Sem2), getResources().getString(R.string.semester) +
                                        " " + getResources().getString(R.string.class_2));
                                mViewPAger.setAdapter(mViewPagerAdapter);
                            } else if (Sem1.size() != 0) {
                                mViewPagerAdapter.addFragment(new Tab1Fragment(Sem1), getResources().getString(R.string.yearly));
                                mViewPAger.setAdapter(mViewPagerAdapter);
                            }
                        }
                    }
                } else {
                    Functions.ToastUtility(ClassScreen.this, strMessage);
                    //recyclerViewLanguage.setVisibility(View.GONE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
