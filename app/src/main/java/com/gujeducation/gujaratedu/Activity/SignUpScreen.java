package com.gujeducation.gujaratedu.Activity;


import static com.gujeducation.gujaratedu.Helper.Functions.isEmptyEdittext;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gujeducation.R;
import com.gujeducation.gujaratedu.Adapter.DistrictAdapter;
import com.gujeducation.gujaratedu.Adapter.TalukaAdapter;
import com.gujeducation.gujaratedu.Helper.Connection;
import com.gujeducation.gujaratedu.Helper.Functions;
import com.gujeducation.gujaratedu.Interface.OnResult;
import com.gujeducation.gujaratedu.Model.District;
import com.gujeducation.gujaratedu.Model.Taluka;
import com.gujeducation.gujaratedu.ServerAPIs.APIs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class SignUpScreen extends AppCompatActivity implements OnResult {

    public static Dialog dialogDistTaluka;
    private final String[] RoleData = {String.valueOf(R.string.sellect_role),
            String.valueOf(R.string.student),
            String.valueOf(R.string.teacher)};
    private final ArrayList<District> listArrDistrict = new ArrayList<District>();
    public ArrayList<Taluka> listArrTaluka = new ArrayList<Taluka>();

    AppCompatButton btnSignUp;
    Functions mFunction;
    String UserName, EmailId, MobileNo, LoginWith;
    int UserId;
    EditText mEdFullName, mEdEmailId, mEdPassword, mEdMobileNo, mEdSchoolName;
    public EditText mEdDist,mEdTal;
    TextView mTvRole;
    AppCompatCheckBox mChkAgree;
    String role;
    Dialog dialogType;

    AppCompatTextView mTvStudent, mTvTeacher, mTvDialogTitle;
    DistrictAdapter mDistrictAdapter;
    TalukaAdapter mTalukaAdapter;
    RecyclerView mRlDistrictTalukaDialog;
    LinearLayoutManager mLayoutManager;
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


        if (Functions.knowInternetOn(this)) {
            APIs.getDistrictList(this, this, mFunction.getPrefMediumId());
        } else {
            Functions.showInternetAlert(this);
        }

        mEdDist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDistrictTalukaPopup("d");
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


    public void showDistrictTalukaPopup(String type) {

        dialogDistTaluka = new Dialog(
                this, R.style.popupTheme);
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_district_taluka_list, null);
        dialogDistTaluka.setContentView(view); // your custom view.
        dialogDistTaluka.setCancelable(true);
        dialogDistTaluka.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialogDistTaluka.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialogDistTaluka.show();
        mTvDialogTitle = (AppCompatTextView) view.findViewById(R.id.tv_dialogtitle);
        mRlDistrictTalukaDialog = (RecyclerView) view.findViewById(R.id.recyclerview_distal);


        mLayoutManager = new LinearLayoutManager(this);

        mRlDistrictTalukaDialog.setHasFixedSize(true);
        mRlDistrictTalukaDialog.setLayoutManager(mLayoutManager);

        if (type.equalsIgnoreCase("d")) {
            Log.e("dialog","call---"+type);

            mTvDialogTitle.setText(R.string.distict);
            Log.e("DistrictSize--", "" + listArrDistrict.size());
            if (listArrDistrict.size() != 0) {
                mDistrictAdapter = new DistrictAdapter(SignUpScreen.this, listArrDistrict);
                mRlDistrictTalukaDialog.setAdapter(mDistrictAdapter);
            }
        } else {
            Log.e("dialog","call---"+type);

            mTvDialogTitle.setText(R.string.taluka);
            Log.e("TalukaSize--", "" + listArrTaluka.size());
            if (listArrTaluka.size() != 0) {
                mTalukaAdapter = new TalukaAdapter(SignUpScreen.this, listArrTaluka);
                mRlDistrictTalukaDialog.setAdapter(mTalukaAdapter);
            }
        }
    }

    @Override
    public void onResult(JSONObject jobjWhole) {
        try {
            JSONObject jObj = jobjWhole.optJSONObject(Connection.TAG_DATA);
            if (jObj != null) {
                String strApiName = jObj.optString("APIName");
                int strStatus = jObj.optInt("success");
                String strMessage = jObj.optString("message");
                Functions.ToastUtility(SignUpScreen.this, strMessage);
                if (strApiName.equalsIgnoreCase("getDistrict")) {
                    if (strStatus != 0) {
                        JSONArray jArrayTextSub = jObj.getJSONArray("district");
                        if (jArrayTextSub.length() > 0) {
                            for (int i = 0; i < jArrayTextSub.length(); i++) {
                                try {
                                    JSONObject obj = jArrayTextSub.getJSONObject(i);
                                    listArrDistrict.add(new District(
                                            obj.optInt("disttrictId"),
                                            obj.optString("disttrictName")
                                    ));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        /*if (listArrDistrict.size() != 0) {
                            mCalenderListAdapter= new CalenderAdapter(SignUpScreen.this, listArrDistrict);
                            recyclerViewCalender.setAdapter(mCalenderListAdapter);
                        }*/
                        }
                    } else {
                        Functions.ToastUtility(SignUpScreen.this, strMessage);
                        //recyclerViewLanguage.setVisibility(View.GONE);
                    }
                } else if (strApiName.equalsIgnoreCase("Signup")) {
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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}