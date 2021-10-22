package com.gujeducation.gujaratedu.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gujeducation.R;
import com.gujeducation.gujaratedu.Adapter.DonationAdapter;
import com.gujeducation.gujaratedu.Adapter.GroupAdapter;
import com.gujeducation.gujaratedu.Helper.Connection;
import com.gujeducation.gujaratedu.Helper.Functions;
import com.gujeducation.gujaratedu.Interface.OnResult;
import com.gujeducation.gujaratedu.Model.Donation;
import com.gujeducation.gujaratedu.Model.Group;
import com.gujeducation.gujaratedu.ServerAPIs.APIs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GroupScreen extends AppCompatActivity implements OnResult {

    private AppCompatImageView btnBack;
    RecyclerView recyclerViewGroup;
    LinearLayoutManager mLayoutManager;
    GroupAdapter mGroupAdapter;
    Functions mFunctions;
    Intent intent;
    AppCompatTextView mTvHeaderTitle;
    private ArrayList<Group> listArrGroup = new ArrayList<Group>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magazine);

        mFunctions = new Functions(this);
        mTvHeaderTitle = findViewById(R.id.header_title);
        btnBack = (AppCompatImageView)findViewById(R.id.ivback);
        recyclerViewGroup = (RecyclerView) findViewById(R.id.recyclerview_magazine);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mTvHeaderTitle.setText("Group");
        recyclerViewGroup.setHasFixedSize(true);
        recyclerViewGroup.setLayoutManager(mLayoutManager);
        //Log.e("DonationScreen", "UserId->" + mFunctions.getPrefUserId());

        if(mFunctions.knowInternetOn(this)){
            APIs.getGroup(GroupScreen.this,this,mFunctions.getPrefUserId());
        }
        else {
            Functions.showInternetAlert(this);
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(GroupScreen.this,HomeScreen.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        intent = new Intent(GroupScreen.this,HomeScreen.class);
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
                    JSONArray jArrayTextSub = jObj.getJSONArray("group");
                    if (jArrayTextSub.length() > 0) {
                        for (int i = 0; i < jArrayTextSub.length(); i++) {
                            try {
                                JSONObject obj = jArrayTextSub.getJSONObject(i);
                                listArrGroup.add(new Group(
                                        obj.optInt("groupId"),
                                        obj.optInt("userId"),
                                        obj.optString("group_name"),
                                        obj.optString("particepentsId")
                                ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        if (listArrGroup.size() != 0) {
                            mGroupAdapter= new GroupAdapter(GroupScreen.this, listArrGroup);
                            recyclerViewGroup.setAdapter(mGroupAdapter);
                        }
                    }
                } else {
                    mFunctions.ToastUtility(GroupScreen.this, strMessage);
                    //recyclerViewLanguage.setVisibility(View.GONE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
