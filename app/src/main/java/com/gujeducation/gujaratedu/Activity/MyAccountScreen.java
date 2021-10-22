package com.gujeducation.gujaratedu.Activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gujeducation.R;
import com.gujeducation.gujaratedu.Helper.Connection;
import com.gujeducation.gujaratedu.Helper.Functions;
import com.gujeducation.gujaratedu.Interface.OnResult;
import com.gujeducation.gujaratedu.ServerAPIs.APIs;
import com.vincent.filepicker.Constant;
import com.vincent.filepicker.activity.ImagePickActivity;
import com.vincent.filepicker.filter.entity.ImageFile;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class MyAccountScreen extends AppCompatActivity implements OnResult {

    private final String[] RoleData = {"Select Role", "Student", "Teacher"};
    Functions mFunctions;
    Intent intent;
    AppCompatEditText mEdName, mEdSchool, mEdTaluka, mEdDistrict, mEdMobileNo, mEdEmailId;
    AppCompatTextView mTvTitle, mEdRole;
    String role = "";
    AppCompatButton btnSubmit;
    FrameLayout mFlChooseImage;
    //AppCompatImageView mIvProfilePic;
    CircleImageView mIvProfilePic;
    File pickImageFile = null;
    ProgressDialog progressD;
    private AppCompatImageView btnBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        mFunctions = new Functions(this);


        //Log.e("MyAccount", "UserId->" + mFunctions.getPrefUserId());


        mTvTitle = findViewById(R.id.header_title);
        btnBack = findViewById(R.id.ivback);
        btnSubmit = findViewById(R.id.btnSubmit);
        mEdName = findViewById(R.id.edit_name);
        mEdSchool = findViewById(R.id.edit_schoolname);
        mEdTaluka = findViewById(R.id.edit_taluka);
        mEdDistrict = findViewById(R.id.edit_district);
        mEdMobileNo = findViewById(R.id.edit_mobileno);
        mEdRole = findViewById(R.id.edit_iam);
        mEdRole.setInputType(InputType.TYPE_NULL);
        mEdEmailId = findViewById(R.id.edit_email);
        mFlChooseImage = findViewById(R.id.frmChooseImg);
        mIvProfilePic = findViewById(R.id.ivuserprofilepic);


        mFlChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MyAccountScreen.this, ImagePickActivity.class);
                intent1.putExtra("IsNeedCamera", false);
                intent1.putExtra(Constant.MAX_NUMBER, 1);
                startActivityForResult(intent1, Constant.REQUEST_CODE_PICK_IMAGE);
            }
        });

        mEdRole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ArrayAdapter<String> adp = new ArrayAdapter<String>(MyAccountScreen.this, android.R.layout.select_dialog_item, RoleData);
                AlertDialog.Builder builder = new AlertDialog.Builder(MyAccountScreen.this);
                builder.setAdapter(adp, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 1) {
                            role = "S";
                            mEdRole.setText("Student");
                        } else if (which == 2) {
                            role = "T";
                            mEdRole.setText("Teacher");
                        }
                    }
                });
                builder.create().show();
            }
        });


        try {
            mTvTitle.setText(R.string.myaccount);

            if (Functions.knowInternetOn(this)) {
                APIs.getMyAccount(MyAccountScreen.this, this, mFunctions.getPrefUserId());
            } else {
                Functions.showInternetAlert(this);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEdRole.getText().toString().trim().equalsIgnoreCase("Teacher")) {
                    role = "T";
                } else {
                    role = "S";
                }

                if (Functions.knowInternetOn(MyAccountScreen.this)) {

                    if (pickImageFile == null) {
                        System.out.println("pickImageFile-null-" + pickImageFile);
                        APIs.updateProfile(MyAccountScreen.this, MyAccountScreen.this, mEdName.getText().toString().trim(),
                                mEdSchool.getText().toString().trim(), mEdTaluka.getText().toString().trim(),
                                mEdDistrict.getText().toString().trim(), role, String.valueOf(mFunctions.getPrefUserId()));
                    } else {
                        System.out.println("pickImageFile-notnull-" + pickImageFile);
                        new UploadImageTask().execute(pickImageFile.toString().trim(),
                                String.valueOf(mFunctions.getPrefUserId()), mEdName.getText().toString().trim(),
                                mEdSchool.getText().toString().trim(), mEdTaluka.getText().toString().trim(),
                                mEdDistrict.getText().toString().trim(), role);
                    }

                } else {
                    Functions.showInternetAlert(MyAccountScreen.this);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.REQUEST_CODE_PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            ArrayList<ImageFile> list = data.getParcelableArrayListExtra(Constant.RESULT_PICK_IMAGE);

            ImageFile file = list.get(0);
            pickImageFile = new File(file.getPath());
            //Log.e("pickImageFile", "-->" + pickImageFile);
            Bitmap bitmap = BitmapFactory.decodeFile(pickImageFile.getAbsolutePath());
            mIvProfilePic.setImageBitmap(bitmap);

        }

    }


    @Override
    public void onResult(JSONObject jobjWhole) {
        try {
            if (jobjWhole != null) {
                JSONObject jObj = jobjWhole.optJSONObject(Connection.TAG_DATA);
                int strStatus = jObj.optInt("success");
                String strMessage = jObj.optString("message");
                String strApi = jObj.optString("APIName");
                Log.e("ApiName->", strApi);

                if (strApi.equalsIgnoreCase("getMyAccount")) {
                    if (strStatus != 0) {
                        mEdName.setText(jObj.optString("fullname"));
                        mEdSchool.setText(jObj.optString("schoolName"));
                        mEdTaluka.setText(jObj.optString("tal"));
                        mEdDistrict.setText(jObj.optString("dist"));
                        mEdMobileNo.setText(jObj.optString("mobile"));
                        mEdEmailId.setText(jObj.optString("email"));

                        if (jObj.optString("role").equalsIgnoreCase("T")) {
                            mEdRole.setText("Teacher");
                        } else {
                            mEdRole.setText("Student");
                        }


                        try {
                            Log.e("mIvProfilePic-", "--->" + jObj.optString("image"));
                            Glide.with(this)
                                    .load(jObj.optString("image"))
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .priority(Priority.IMMEDIATE).dontAnimate()
                                    .placeholder(R.drawable.ico_profile_user)
                                    .into(mIvProfilePic);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    } else {
                        Functions.ToastUtility(MyAccountScreen.this, strMessage);
                    }
                } else if (strApi.equalsIgnoreCase("updateMyAccount")) {
                    if (strStatus != 0) {
                        //mFunctions.SetPrefUserId(jObj.optInt("userId"));
                        //mFunctions.SetPrefUserName(jObj.optString("fullName"));
                        //mFunctions.SetPrefRole(jObj.optString("role"));
                        mFunctions.SetPrefUserImage(jObj.optString("image"));

                        Log.e("PREFMYACCPIC-",
                                "userId-"+jObj.optInt("userId")+
                                "fullName-"+jObj.optString("fullName")+
                                "role-"+jObj.optString("role")+
                                "image-"+jObj.optString("image"));
                        Functions.ToastUtility(MyAccountScreen.this, strMessage);
                        finish();
                    } else {
                        Functions.ToastUtility(MyAccountScreen.this, strMessage);
                    }
                } else if (strApi.equalsIgnoreCase("updateProfile")) {
                    if (strStatus != 0) {

                        /*mFunctions.SetPrefUserId(jObj.optInt("userId"));
                        mFunctions.SetPrefUserName(jObj.optString("fullName"));
                        mFunctions.SetPrefRole(jObj.optString("role"));*/
                        mFunctions.SetPrefUserImage(jObj.optString("image"));

                        Log.e("PREFPROFILE-",
                                "userId-"+jObj.optInt("userId")+
                                        "fullName-"+jObj.optString("fullName")+
                                        "role-"+jObj.optString("role")+
                                        "image-"+jObj.optString("image"));
                        Functions.ToastUtility(MyAccountScreen.this, strMessage);
                        finish();
                    } else {
                        Functions.ToastUtility(MyAccountScreen.this, strMessage);
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public class UploadImageTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressD = ProgressDialog.show(MyAccountScreen.this, "", "Updating..Please wait", true);
        }

        @Override
        protected String doInBackground(String... val) {
            String response_str = "";
            File file1 = new File(val[0]);
            try {
                HttpClient client = new DefaultHttpClient();
                HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000);
                HttpResponse response;
                try {
                    //HttpPost post = new HttpPost(Connection.ADD_NEWCATALAOG);
                    HttpPost post = new HttpPost(Connection.UPDATE_MYACCOUNT);
                    //Log.e("UPDATE_MYACCOUNT", Connection.UPDATE_MYACCOUNT);
                    //post.addHeader("Authorization", "Bearer 70e8e17d-e1ed-4b7a-8a8a-40383d74d467");

                    MultipartEntity reqEntity = new MultipartEntity();
                    FileBody cbFile = new FileBody(file1);

                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;

                    //Returns null, sizes are in the options variable
                    BitmapFactory.decodeFile(val[0], options);
                    reqEntity.addPart("file", cbFile);
                    reqEntity.addPart("userId", new StringBody("" + val[1]));
                    reqEntity.addPart("name", new StringBody("" + val[2]));
                    reqEntity.addPart("schoolName", new StringBody("" + val[3]));
                    reqEntity.addPart("taluka", new StringBody("" + val[4]));
                    reqEntity.addPart("district", new StringBody("" + val[5]));
                    reqEntity.addPart("role", new StringBody("" + val[6]));


                    post.setEntity(reqEntity);
                    response = client.execute(post);
                    //Log.e("responsecatalog", String.valueOf(response));
                    // Log.e("responsecat", String.valueOf(client.execute(post)));
                    if (response != null) {
                        response_str = EntityUtils.toString(response.getEntity());
                        //Log.e("response_str-->", response_str);
                        //Toast.makeText(AddCatalogActivity.this, "str--"+response_str, Toast.LENGTH_SHORT).show();

                        progressD.dismiss();
                    } else {

                    }
                    /*if (response != null) {
                        if (!val[2].equals("0")) {
                            try {
                                File file = new File(picturePathNew_Profile);
                                boolean deleted = file.delete();
                            } catch (Exception e) {

                            }
                        }
                        //response_str = EntityUtils.toString(response.getEntity());

                        // pd.dismiss();
                    } else {

                    }
*/

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("Error", "" + e.getMessage());
                }

            } catch (Exception e) {
                e.printStackTrace();
                //Log.i("Error", "" + e.getMessage());
            }

            return response_str;
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                //Log.e("ResultHere", "-->" + result);
                //Toast.makeText(AddCatalogActivity.this, ""+result, Toast.LENGTH_SHORT).show();


                JSONObject jObj = new JSONObject(result);
                jObj = jObj.optJSONObject(Connection.TAG_DATA);
                int strStatus = jObj.optInt("success");
                String strMessage = jObj.optString("message");
                String strApi = jObj.optString("APIName");
                //Log.e("ApiName->", strApi);
                //Log.e("strMessage--",""+strMessage);
                //Log.e("strStatus--",""+strStatus);

                if (strStatus == 1) {
                    Toast.makeText(MyAccountScreen.this, "" + strMessage, Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(MyAccountScreen.this, "" + strMessage, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


}
