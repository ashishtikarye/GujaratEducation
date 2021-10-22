package com.gujeducation.gujaratedu.Activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.text.HtmlCompat;

import com.gujeducation.R;
import com.gujeducation.gujaratedu.Helper.Connection;
import com.gujeducation.gujaratedu.Helper.Functions;
import com.gujeducation.gujaratedu.Interface.OnResult;
import com.gujeducation.gujaratedu.ServerAPIs.APIs;

import org.json.JSONObject;

public class ForgetPassswordScreen extends AppCompatActivity{ //implements OnResult {

    Functions mFunctions;
    AppCompatEditText mEdEmail;
    AppCompatButton btnSend;
    AppCompatImageView mIvBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        mFunctions = new Functions(this);

        mEdEmail = findViewById(R.id.edemailid);
        mIvBack = findViewById(R.id.ivback);
        btnSend = findViewById(R.id.btnSend);

/*

        if (Functions.knowInternetOn(this)) {
            APIs.getAboutUs(ForgetPassswordScreen.this, this);
        } else {
            Functions.showInternetAlert(this);
        }
*/

        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    /*@Override
    public void onResult(JSONObject jobjWhole) {
        try {
            if (jobjWhole != null) {
                JSONObject jObj = jobjWhole.optJSONObject(Connection.TAG_DATA);
                int strStatus = jObj.optInt("success");
                String strMessage = jObj.optString("message");
                if (strStatus != 0) {
                    mTvAboutUs.setText(HtmlCompat.fromHtml(jObj.optString("description"), 0));
                    mTvTitle.setText(jObj.optString("title"));
                } else {
                    Functions.ToastUtility(ForgetPassswordScreen.this, strMessage);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}
