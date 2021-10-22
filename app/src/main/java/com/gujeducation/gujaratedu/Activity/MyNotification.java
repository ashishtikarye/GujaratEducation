package com.gujeducation.gujaratedu.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gujeducation.R;
import com.gujeducation.gujaratedu.Adapter.NotificationAdapter;
import com.gujeducation.gujaratedu.Helper.Connection;
import com.gujeducation.gujaratedu.Helper.Functions;
import com.gujeducation.gujaratedu.Interface.OnResult;
import com.gujeducation.gujaratedu.Model.Notification;
import com.gujeducation.gujaratedu.ServerAPIs.APIs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MyNotification extends AppCompatActivity implements OnResult {
    private final ArrayList<Notification> listArrNotification = new ArrayList<Notification>();
    RecyclerView recyclerViewNotification;
    LinearLayoutManager mLayoutManager;
    NotificationAdapter mNotificationAdapter;
    Functions mFunctions;
    AppCompatTextView mTvTitle;
    Intent intent;
    private AppCompatImageView btnBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        mFunctions = new Functions(this);
        mTvTitle = findViewById(R.id.header_title);

        //Log.e("Notify", "PreferLangID-->" + mFunctions.getPrefMediumId());
        if (mFunctions.getPrefMediumId() == 1) {
            com.gujeducation.gujaratedu.Helper.Language.set(MyNotification.this, "gu");
            mTvTitle.setText(R.string.menu_notification);
        }
        if (mFunctions.getPrefMediumId() == 2) {
            com.gujeducation.gujaratedu.Helper.Language.set(MyNotification.this, "en");
            mTvTitle.setText(R.string.menu_notification);

        }
        if (mFunctions.getPrefMediumId() == 3) {
            com.gujeducation.gujaratedu.Helper.Language.set(MyNotification.this, "hi");
            mTvTitle.setText(R.string.menu_notification);

        }
        if (mFunctions.getPrefMediumId() == 4) {
            com.gujeducation.gujaratedu.Helper.Language.set(MyNotification.this, "mr");
            mTvTitle.setText(R.string.menu_notification);

        }
        if (mFunctions.getPrefMediumId() == 5) {
            com.gujeducation.gujaratedu.Helper.Language.set(MyNotification.this, "od");
            mTvTitle.setText(R.string.menu_notification);

        }
        if (mFunctions.getPrefMediumId() == 6) {
            com.gujeducation.gujaratedu.Helper.Language.set(MyNotification.this, "tl");
            mTvTitle.setText(R.string.menu_notification);

        }
        if (mFunctions.getPrefMediumId() == 7) {
            com.gujeducation.gujaratedu.Helper.Language.set(MyNotification.this, "ur");
            mTvTitle.setText(R.string.menu_notification);

        }

        btnBack = findViewById(R.id.ivback);
        recyclerViewNotification = findViewById(R.id.recyclerview_notification);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        recyclerViewNotification.setHasFixedSize(true);
        recyclerViewNotification.setLayoutManager(mLayoutManager);

        try {
            //Log.e("getPrefRole", "--" + mFunctions.getPrefRole());
            if (Functions.knowInternetOn(this)) {
                APIs.getNotificationList(MyNotification.this, this, mFunctions.getPrefRole());

            } else {
                Functions.showInternetAlert(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MyNotification.this, HomeScreen.class);
                startActivity(intent);
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

                if (strStatus != 0) {
                    JSONArray jArrayTextSub = jObj.getJSONArray("notification");
                    if (jArrayTextSub.length() > 0) {
                        for (int i = 0; i < jArrayTextSub.length(); i++) {
                            try {
                                JSONObject obj = jArrayTextSub.getJSONObject(i);
                                listArrNotification.add(new Notification(
                                        obj.optString("title"),
                                        obj.optString("body")));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        if (listArrNotification.size() != 0) {
                            mNotificationAdapter = new NotificationAdapter(MyNotification.this, listArrNotification);
                            recyclerViewNotification.setAdapter(mNotificationAdapter);
                        }
                    }
                } else {
                    Functions.ToastUtility(MyNotification.this, strMessage);
                    //recyclerViewLanguage.setVisibility(View.GONE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
