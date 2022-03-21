package com.gujeducation.gujaratedu.Activity;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.iid.FirebaseInstanceId;
import com.gujeducation.R;
import com.gujeducation.gujaratedu.Helper.Connection;
import com.gujeducation.gujaratedu.Helper.Functions;
import com.gujeducation.gujaratedu.Interface.OnResult;
import com.gujeducation.gujaratedu.ServerAPIs.APIs;

import org.json.JSONObject;

import java.util.Locale;

import static com.gujeducation.gujaratedu.Helper.CommonMethod.GoFrom;
import static com.gujeducation.gujaratedu.Helper.CommonMethod.ManageScreenView;
import static com.gujeducation.gujaratedu.Helper.Functions.isEmptyEdittext;


public class SignInScreen extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, OnResult {
    private static final int RC_SIGN_IN = 1;
    // SignInButton signInButton;
    AppCompatButton btnLogin;
    LinearLayout mLlGoogleSignIn;
    AppCompatTextView mTvCreateAccount, mTvContinueWith, mTvForgetPassword;
    EditText mEdEmail, mEdPassword;
    Functions mFunction;
    SharedPreferences mPreference;
    SharedPreferences.Editor editor;
    Locale myLocale;
    //String currentLanguage = "en";
    String loginWith = "", strEmail = "", strDisplayName = "",strLoginType="";
    String device_os, device_osversion, firebase_token, device_model, devicUUID;
    private GoogleApiClient googleApiClient;
    private GoogleSignInOptions gso;
    private Context mcontext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        ManageScreenView(this);
        mcontext = this;
        mFunction = new Functions(this);
        mPreference = getSharedPreferences("GujaratEducation", MODE_PRIVATE);

        //==============================  GET DEVICE OS =================================//
        device_os = "Android";
        //==============================  GET OS VERSTION =================================//
        device_osversion = android.os.Build.VERSION.RELEASE;
        //==============================  PUT ALL GLOBALLY KEY =================================//
        firebase_token = FirebaseInstanceId.getInstance().getToken();
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
        /*Log.e("notificationS", *//*"android_id-" + android_id +*//*
                "\ndevicUUID-" + devicUUID +
                " firebase_token-" + firebase_token + "\ndevice_model-" + device_model +
                "\ndevice_osversion-" + device_osversion + "\ndevice_os-" + device_os);*/

        Intent intent = getIntent();
        if (intent.hasExtra("loginType")) {
            strLoginType = intent.getExtras().getString("loginType");
        } else {
            strLoginType = "Unknown";
        }

        //Log.e("strLoginType", " -->" + strLoginType);


        //SignInButton signInButton = findViewById(R.id.sign_in_button);
        mLlGoogleSignIn = findViewById(R.id.lvgoogleplus);
        btnLogin = findViewById(R.id.btnLogin);
        mTvCreateAccount = findViewById(R.id.tvcreateaccount);
        mTvContinueWith = findViewById(R.id.tvcontinuewith);
        mEdEmail = findViewById(R.id.edemailid);
        mEdPassword = findViewById(R.id.edpassword);
        mTvForgetPassword = findViewById(R.id.tvforgetpassword);


        //com.gujarateducation.Helper.Language.set(SignInScreen.this, mFunction.getPrefLanguageCode());

        //Toast.makeText(mcontext, "LanguageCode--" + mFunction.getPrefLanguageCode(), Toast.LENGTH_SHORT).show();
        //   signInButton.setSize(SignInButton.SIZE_WIDE);



        myLocale = getResources().getConfiguration().locale;

//        com.gujeducation.gujaratedu.Helper.Language.set(this, "gu");

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        if(strLoginType.equalsIgnoreCase("google")){
            Intent gntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
            startActivityForResult(gntent, RC_SIGN_IN);
        }
        mLlGoogleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent, RC_SIGN_IN);
            }
        });

        mTvCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoFrom(mcontext, new SignUpScreen(), null);
            }
        });


        mTvForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInScreen.this, ForgetPassswordScreen.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEmptyEdittext(mEdEmail)) {
                    Functions.ToastUtility(SignInScreen.this, "Please Enter Email id");
                } else if (isEmptyEdittext(mEdPassword)) {
                    Functions.ToastUtility(SignInScreen.this, "Please Enter Password");
                } else {
                    if (Functions.knowInternetOn(SignInScreen.this)) {
                        loginWith = "M";
                        /*Log.e("btnLOGIN", "email-" + mEdEmail.getText().toString().trim() + " password-" + mEdPassword.getText().toString().trim() +
                                " loginWith-" + loginWith);*/

                        APIs.doLogin(SignInScreen.this, SignInScreen.this, mEdEmail.getText().toString().trim(),
                                mEdPassword.getText().toString().trim(), loginWith, device_model, device_os, devicUUID, device_osversion,
                                firebase_token);
                    } else {
                        Functions.showInternetAlert(SignInScreen.this);
                    }
                }
            }
        });
    }


    public void logoutGoogle() {

        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(Status status) {
                Toast.makeText(SignInScreen.this, "Logged Out", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(SignInScreen.this, "onConnectionFailed", Toast.LENGTH_SHORT).show();

    }

    /*public void splashGetDeviveUUID() {
        try {
            TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
            devicUUID = tm.getDeviceId();// Settings.Secure.getString(getApplicationContext().getContentResolver(),                    Settings.Secure.ANDROID_ID);
            Log.e("uuidSignin", devicUUID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            //gotoProfile();
            GoogleSignInAccount account = result.getSignInAccount();
            /*Log.e("googlelogin", "displayname->" + account.getDisplayName() + "\n email->" + account.getEmail() +
                    "\n photoUrl->" + account.getPhotoUrl());*/
            loginWith = "G";
            strEmail = account.getEmail();
            strDisplayName = account.getDisplayName();
            Toast.makeText(getApplicationContext(), "" + strDisplayName + "\n" + strEmail + "\n" + account.getGivenName() + "\n" + account.getFamilyName(), Toast.LENGTH_LONG).show();


            if (Functions.knowInternetOn(SignInScreen.this)) {

                /*Log.e("btnGOOGLELOGIN", "email-" + mEdEmail.getText().toString().trim() + " password-" + mEdPassword.getText().toString().trim() +
                        " loginWith-" + loginWith);*/


                APIs.doLogin(SignInScreen.this, SignInScreen.this, strEmail, mEdPassword.getText().toString().trim(), loginWith,
                        device_model, device_os, devicUUID, device_osversion,
                        firebase_token);


            } else {
                Functions.showInternetAlert(SignInScreen.this);
            }


        } else {
            Toast.makeText(getApplicationContext(), "Sign in cancel", Toast.LENGTH_LONG).show();
        }
    }
    /*private void gotoProfile(){
        Intent intent=new Intent(SignInScreen.this,ProfileActivity.class);
        startActivity(intent);
    }*/

    /*@Override
    protected void onStart() {
        Log.e("onStart", "onStart call");
        super.onStart();
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(googleApiClient);
        if (opr.isDone()) {
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(@NonNull GoogleSignInResult googleSignInResult) {
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }*/

    @Override
    public void onResult(JSONObject jobjWhole) {
        JSONObject jObj = jobjWhole.optJSONObject(Connection.TAG_DATA);
        if (jObj != null) {
            String strApiName = jObj.optString("api");
            int strStatus = jObj.optInt("status");
            String strMessage = jObj.optString("message");
            //Log.e("ApiStatus", "" + strStatus);
            if (strStatus == 1) {

                /*Log.e("SignInScrnRes",
                        "userId-" + jObj.optInt("userId") +
                                " fullName-" + jObj.optString("fullName") +
                                " role-" + jObj.optString("role"));*/

                mFunction.SetPrefUserId(jObj.optInt("userId"));
                mFunction.SetPrefUserName(jObj.optString("fullName"));
                mFunction.SetPrefRole(jObj.optString("role"));
                mFunction.SetPrefUserImage(jObj.optString("image"));

                Log.e("PREFSIGNIN-",
                        "userId-"+jObj.optInt("userId")+
                                "fullName-"+jObj.optString("fullName")+
                                "role-"+jObj.optString("role")+
                                "image-"+jObj.optString("image"));

                Functions.ToastUtility(SignInScreen.this, "Welcome " + jObj.optString("fullName") + " !");
                mFunction.SetPrefLogin(true);
                Intent i = new Intent(SignInScreen.this, HomeScreen.class);
                startActivity(i);
                overridePendingTransition(R.anim.right_in, R.anim.no_animation);
                finish();
            } else {
                Functions.ToastUtility(SignInScreen.this, strMessage);
                if (loginWith.equalsIgnoreCase("G")) {
                    //Log.e("type ", "Google login");
                    Intent i = new Intent(SignInScreen.this, SignUpScreen.class);
                    i.putExtra("txtfullname", strDisplayName);
                    i.putExtra("txtemailid", strEmail);
                    i.putExtra("txtloginwith", "G");
                    startActivity(i);
                } else {
                    Log.e("type ", "Normal login");
                }

            }

        }
    }


}