package com.gujeducation.gujaratedu.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import com.google.firebase.iid.FirebaseInstanceId;
import com.gujeducation.R;
import com.gujeducation.gujaratedu.Helper.Connection;
import com.gujeducation.gujaratedu.Helper.Functions;
import com.gujeducation.gujaratedu.Interface.OnResult;
import com.gujeducation.gujaratedu.ServerAPIs.APIs;

import org.json.JSONObject;

public class VerifyOTPScreen extends AppCompatActivity implements OnResult {
    private Context mcontext;
    Intent intent;
    AppCompatEditText mEdOtp;
    AppCompatButton btnVerify;
    int UserId;
    String UserName,MobileNo;
    Functions mFunction;
    String device_os, device_osversion, firebase_token, device_model, devicUUID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifyotp);
        mFunction = new Functions(this);
        mcontext = this;
        mEdOtp = findViewById(R.id.edotpverify);
        btnVerify = findViewById(R.id.btnVerify);

        intent = getIntent();

        if (intent.hasExtra("userId") && intent.hasExtra("userName") && intent.hasExtra("mobileNo")) {
            UserId = intent.getExtras().getInt("userId");
            UserName = intent.getExtras().getString("userName");
            MobileNo = intent.getExtras().getString("mobileNo");
        } else {
            UserId = 0;
            UserName = "Unknown";
            MobileNo = "0";
        }

        //==============================  GET DEVICE OS =================================//
        device_os = "Android";
        //==============================  GET OS VERSTION =================================//
        device_osversion = android.os.Build.VERSION.RELEASE;
        //==============================  PUT ALL GLOBALLY KEY =================================//

        firebase_token = FirebaseInstanceId.getInstance().getToken();
//        device_manufacture = android.os.Build.MANUFACTURER;
        device_model = android.os.Build.MODEL;

        /*String android_id = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);*/


        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            devicUUID = Settings.Secure.getString(
                    mcontext.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
        } else {
            final TelephonyManager mTelephony = (TelephonyManager) mcontext.getSystemService(Context.TELEPHONY_SERVICE);
            if (mTelephony.getDeviceId() != null) {
                devicUUID = mTelephony.getDeviceId();
            } else {
                devicUUID = Settings.Secure.getString(
                        mcontext.getContentResolver(),
                        Settings.Secure.ANDROID_ID);
            }
        }
        Log.e("notificationV", /*"android_id-" + android_id +*/
                "\ndevicUUID-" + devicUUID +
                        " firebase_token-" + firebase_token + "\ndevice_model-" + device_model +
                        "\ndevice_osversion-" + device_osversion + "\ndevice_os-" + device_os);


        Log.e("VerifyOTPScreen", "UserId->" + UserId + " UserName->" + UserName +
                " MobileNo->" + MobileNo);


        if (Functions.knowInternetOn(VerifyOTPScreen.this)) {
            APIs.sendOTP(VerifyOTPScreen.this, VerifyOTPScreen.this, UserId, MobileNo);
        } else {
            Functions.showInternetAlert(VerifyOTPScreen.this);
        }

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Functions.knowInternetOn(VerifyOTPScreen.this)) {
                    APIs.verifyOTP(VerifyOTPScreen.this, VerifyOTPScreen.this, UserId, mEdOtp.getText().toString().trim(), MobileNo,
                            device_model, device_os, devicUUID, device_osversion,firebase_token);
                } else {
                    Functions.showInternetAlert(VerifyOTPScreen.this);
                }
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onResult(JSONObject jobjWhole) {
        try {
            if (jobjWhole != null) {
                JSONObject jObj = jobjWhole.optJSONObject(Connection.TAG_DATA);
                int strStatus = jObj.optInt("success");
                String strMessage = jObj.optString("message");
                String strApiName = jObj.optString("api");
                Log.e("strMessage", "" + strMessage);

                if (strStatus == 1) {
                    if (strApiName.equalsIgnoreCase("sendOTP")) {
                        Functions.ToastUtility(VerifyOTPScreen.this, strMessage);
                    } else if (strApiName.equalsIgnoreCase("VerifyOTP")) {
                        mFunction.SetPrefUserId(UserId);
                        mFunction.SetPrefUserName(UserName);
                        Functions.ToastUtility(VerifyOTPScreen.this, strMessage);
                        mFunction.SetPrefLogin(true);
                        Intent intent = new Intent(VerifyOTPScreen.this, HomeScreen.class);
                        startActivity(intent);
                        finish();
                    }
                }else {
                    Functions.ToastUtility(VerifyOTPScreen.this, strMessage);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
