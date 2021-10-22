package com.gujeducation.gujaratedu.Activity;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatTextView;

import com.gujeducation.R;
import com.gujeducation.gujaratedu.Helper.Connection;
import com.gujeducation.gujaratedu.Helper.Functions;
import com.gujeducation.gujaratedu.Interface.OnResult;
import com.gujeducation.gujaratedu.ServerAPIs.APIs;

import org.json.JSONObject;

import static com.gujeducation.gujaratedu.Helper.Functions.isEmptyEdittext;


public class SignUpScreen extends AppCompatActivity implements OnResult {

    private final String[] RoleData = {String.valueOf(R.string.sellect_role),
            String.valueOf(R.string.student),
            String.valueOf(R.string.teacher)};
    AppCompatButton btnSignUp;
    Functions mFunction;
    String UserName, EmailId, MobileNo, LoginWith;
    int UserId;
    EditText mEdFullName, mEdEmailId, mEdPassword, mEdMobileNo, mEdSchoolName, mEdTal, mEdDist;
    TextView mTvRole;
    AppCompatCheckBox mChkAgree;
    String role;
    Dialog dialogType;
    AppCompatTextView mTvStudent, mTvTeacher;
    private Context mcontext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mcontext = this;
        mFunction = new Functions(this);
        Log.e("SignUpScreen-->", "MediumId->" + mFunction.getPrefMediumId());

        Intent intent = getIntent();
        if (intent.hasExtra("txtfullname") && intent.hasExtra("txtemailid") && intent.hasExtra("txtloginwith")) {
            UserName = intent.getExtras().getString("txtfullname");
            EmailId = intent.getExtras().getString("txtemailid");
            LoginWith = intent.getExtras().getString("txtloginwith");
        } else {
            UserName = "";
            EmailId = "";
            LoginWith = "M";
        }
        Log.e("signupIntent", "UserName->" + UserName + " EmailId->" + EmailId +
                " LoginWith->" + LoginWith);

        btnSignUp = findViewById(R.id.btnRegister);
        mEdFullName = findViewById(R.id.edfullname);
        mEdEmailId = findViewById(R.id.edemailid);
        mEdPassword = findViewById(R.id.edpassword);
        mEdMobileNo = findViewById(R.id.edmobileno);
        mTvRole = findViewById(R.id.tvteacherstudent);
        mEdSchoolName = findViewById(R.id.edschoolname);
        mEdTal = findViewById(R.id.edtaluka);
        mEdDist = findViewById(R.id.eddistict);
        mChkAgree = findViewById(R.id.chkagree);

        mEdFullName.setText(UserName);
        mEdEmailId.setText(EmailId);

        mTvRole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTypePopup();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // mFunction.SetLoginFlag(1);
             /*   finish();
                startActivity(new Intent(SignUpScreen.this,VerifyOTPScreen.class));*/
                if (isEmptyEdittext(mEdFullName)) {
                    Functions.ToastUtility(SignUpScreen.this, "Please Enter Fullname");
                } else if (isEmptyEdittext(mEdEmailId)) {
                    Functions.ToastUtility(SignUpScreen.this, "Please Enter Email id");
                } else if (isEmptyEdittext(mEdPassword)) {
                    Functions.ToastUtility(SignUpScreen.this, "Please Enter Password");
                } else if (isEmptyEdittext(mEdMobileNo)) {
                    Functions.ToastUtility(SignUpScreen.this, "Please Enter Mobile No.");
                } else if (mEdMobileNo.getText().length() != 10) {
                    Functions.ToastUtility(SignUpScreen.this, "Mobile Number Must be 10 Digit.");
                } else if (mTvRole.getText().toString().equals("")) {
                    Functions.ToastUtility(SignUpScreen.this, "Please Select Teacher / Student");
                } else if (isEmptyEdittext(mEdSchoolName)) {
                    Functions.ToastUtility(SignUpScreen.this, "Please Enter School Name");
                } else if (isEmptyEdittext(mEdTal)) {
                    Functions.ToastUtility(SignUpScreen.this, "Please Enter Taluka");
                } else if (isEmptyEdittext(mEdDist)) {
                    Functions.ToastUtility(SignUpScreen.this, "Please Enter District");
                } else if (!mChkAgree.isChecked()) {
                    Functions.ToastUtility(SignUpScreen.this, "Please Accept our Terms and Conditions");
                } else {
                    MobileNo = mEdMobileNo.getText().toString().trim();
                    if (Functions.knowInternetOn(SignUpScreen.this)) {
                        Log.e("LoginWith", "--->>>" + LoginWith);
                        APIs.doSignUpUser(SignUpScreen.this, SignUpScreen.this, mEdFullName.getText().toString().trim(),
                                mEdEmailId.getText().toString().trim(), mEdPassword.getText().toString().trim(),
                                mEdMobileNo.getText().toString().trim(), role, mEdSchoolName.getText().toString().trim(),
                                mEdTal.getText().toString().trim(), mEdDist.getText().toString().trim(), LoginWith.trim());
                    } else {
                        Functions.showInternetAlert(SignUpScreen.this);
                    }
                }
                // Intent intent = new Intent(SignUpScreen.this, HomeScreen.class);
             /*   intent.putExtra("tfullname",FullName);
                intent.putExtra("temailid",EmailId);*/
                //startActivity(intent);
            }
        });


    }


    public void showTypePopup() {

        dialogType = new Dialog(
                this, R.style.popupTheme);
        LayoutInflater inflater = (LayoutInflater)
                this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popup_type, null);
        dialogType.setContentView(view); // your custom view.
        dialogType.setCancelable(false);
        dialogType.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialogType.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        mTvStudent = view.findViewById(R.id.tvstudent);
        mTvTeacher = view.findViewById(R.id.tvteacher);


        dialogType.show();

        mTvStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTvRole.setText(R.string.student);
                role = "S";
                dialogType.dismiss();
            }
        });
        mTvTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTvRole.setText(R.string.teacher);
                role = "T";
                dialogType.dismiss();

            }
        });

    }

    @Override
    public void onResult(JSONObject jobjWhole) {
        JSONObject jObj = jobjWhole.optJSONObject(Connection.TAG_DATA);
        if (jObj != null) {
            String strApiName = jObj.optString("APIName");
            int strStatus = jObj.optInt("success");
            String strMessage = jObj.optString("message");
            UserId = jObj.optInt("userId");
            UserName = jObj.optString("fullname");
            Log.e("signup-", "UserName-->" + UserName + " UserId-->" + UserId);
            Functions.ToastUtility(SignUpScreen.this, strMessage);
            if (strStatus == 1) {
                Log.e("signupppp-", "UserName-->" + UserName + " UserId-->" + UserId);
                Intent intent = new Intent(SignUpScreen.this, VerifyOTPScreen.class);
                intent.putExtra("userId", UserId);
                intent.putExtra("userName", UserName);
                intent.putExtra("mobileNo", MobileNo);
                startActivity(intent);
                finishAffinity();
            } else if (strStatus == 2) {
                Functions.ToastUtility(SignUpScreen.this, strMessage);
            } else {
                Functions.ToastUtility(SignUpScreen.this, strMessage);
            }
        }
    }
}