package com.gujeducation.gujaratedu.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gujeducation.R;
import com.gujeducation.gujaratedu.Adapter.ClassAdapter;
import com.gujeducation.gujaratedu.Helper.Connection;
import com.gujeducation.gujaratedu.Helper.Functions;
import com.gujeducation.gujaratedu.Interface.OnResult;
import com.gujeducation.gujaratedu.Model.Class;
import com.gujeducation.gujaratedu.Model.SubClass;
import com.gujeducation.gujaratedu.ServerAPIs.APIs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AvailableClassScreen extends AppCompatActivity implements OnResult {

    private AppCompatImageView btnBack;
    Intent intent;
    private RecyclerView recyclerClass;
    LinearLayoutManager mLayoutManager;
    private ArrayList<Class> listArrClass = new ArrayList<>();
    private ArrayList<String> listArrCategory = new ArrayList<>();
    Functions mFunctions;
    ClassAdapter mClassAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_class);
        mFunctions = new Functions(this);
        recyclerClass = findViewById(R.id.recyclerview_class);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerClass.setHasFixedSize(true);
        recyclerClass.setLayoutManager(mLayoutManager);
        btnBack = findViewById(R.id.ivback);

        if (Functions.knowInternetOn(this)) {
            APIs.getClass(AvailableClassScreen.this,this,mFunctions.getPrefMediumId());
        } else {
            Functions.showInternetAlert(this);
        }
        // set default preference
        mFunctions.setSectionId(0);
        mFunctions.setStandardId(0);
        mFunctions.setStandardIdentify(0);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(AvailableClassScreen.this, HomeScreen.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        intent = new Intent(AvailableClassScreen.this,HomeScreen.class);
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
                    JSONArray jArrayClass = jObj.getJSONArray("class");
                    if (jArrayClass.length() > 0) {
                        listArrClass.clear();
                        for (int i = 0; i < jArrayClass.length(); i++) {
                            try {
                                JSONObject jsonObject = jArrayClass.getJSONObject(i);
                                JSONArray result = jsonObject.getJSONArray("category"+(i+1));
                                if(result.length() != 0) {
                                    Class objClass = new Class(this);
                                    String strCategory = "category" + (i + 1);
                                    if (strCategory.equalsIgnoreCase("category1")) objClass.setCategory(1);
                                    else if (strCategory.equalsIgnoreCase("category2")) objClass.setCategory(2);
                                    else if (strCategory.equalsIgnoreCase("category3")) objClass.setCategory(3);
                                    else if (strCategory.equalsIgnoreCase("category4")) objClass.setCategory(4);
                                    else if (strCategory.equalsIgnoreCase("category5")) objClass.setCategory(5);

                                    ArrayList<SubClass> listSubClass = new ArrayList<>();

                                    for (int j = 0; j < result.length(); j++) {
                                        JSONObject object = result.getJSONObject(j);
                                        //Log.e("Data", "Category" + (i + 1) + "-->" + object.getInt("category"));
                                        SubClass objSubClass = new SubClass();
                                        objSubClass.setClassId(object.optInt("standardId"));
                                        objSubClass.setMediumId(object.optInt("mediumId"));
                                        objSubClass.setIs_identify(object.optInt("is_identify"));
                                        objSubClass.setClassName(object.optString("standard").trim());
                                        listSubClass.add(objSubClass);
                                    }
                                    objClass.setListClass(listSubClass);
                                    listArrClass.add(objClass);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        if(listArrClass.size() != 0) {
                            for (int i = 0;i<listArrClass.size();i++){
                                listArrCategory.add(listArrClass.get(i).getCategoryName());
                                listArrCategory = Class.removeDuplicates(listArrCategory);
                            }
                            mClassAdapter= new ClassAdapter(AvailableClassScreen.this, listArrClass, listArrCategory);
                            recyclerClass.setAdapter(mClassAdapter);
                            mClassAdapter.notifyDataSetChanged();
                        }
                        else{
                            Toast.makeText(this, "No Data Found...!", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    mFunctions.ToastUtility(AvailableClassScreen.this, strMessage);
                    //recyclerViewLanguage.setVisibility(View.GONE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
