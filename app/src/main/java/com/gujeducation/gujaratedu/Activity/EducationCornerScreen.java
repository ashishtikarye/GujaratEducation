package com.gujeducation.gujaratedu.Activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.gujeducation.R;
import com.gujeducation.gujaratedu.Adapter.PostUtubeAdapter;
import com.gujeducation.gujaratedu.Helper.Connection;
import com.gujeducation.gujaratedu.Helper.Functions;
import com.gujeducation.gujaratedu.Interface.OnResult;
import com.gujeducation.gujaratedu.Model.YoutubeVideo;
import com.gujeducation.gujaratedu.ServerAPIs.APIs;
import com.vincent.filepicker.Constant;
import com.vincent.filepicker.activity.ImagePickActivity;
import com.vincent.filepicker.filter.entity.ImageFile;
import com.vincent.filepicker.filter.entity.NormalFile;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class EducationCornerScreen extends AppCompatActivity implements OnResult {

    // private final ArrayList<Post> listArrPost = new ArrayList<Post>();
    private final ArrayList<YoutubeVideo> listYoutubeVideo = new ArrayList<YoutubeVideo>();
    public ProgressDialog progressD;
    LinearLayout btnCreatePost;
    RecyclerView recyclerviewPost;
    LinearLayoutManager mLayoutManager;
    //PostAdapter mPostAdapter;
    //YoutubeRecyclerAdapter mYoutubeRecyclerAdapter;
    PostUtubeAdapter mPostUtubeAdapter;
    Functions mFunctions;
    Intent intent;
    RelativeLayout.LayoutParams layoutParams;
    File imgFile = null;
    AppCompatImageView ivPostImg, ivPdfIcon, mIvBack;
    AppCompatTextView tvPdfName, mTvUsername;
    CircleImageView mIvUserPic;
    int flag = 0;
    AppCompatImageView mIvCloseDialog;
    Dialog dialogPost;
    AppCompatEditText edtPostTitle, edtYoutubeLink;
    AppCompatButton btnAddPost;
    LinearLayout mLlAttachment, btnAddImage, btnAddPDF, optionLayout, showPdfLayout;
    RelativeLayout relativeLayout;
    AppCompatImageView btnBack;
    AdView mAdView;
    RadioGroup rdoGrpUpload;
    RadioButton rdoYoutube, rdoImage;
    //String userImage = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education_corner);
        mFunctions = new Functions(this);

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        Log.e("EducationCornerScreen", "UserId->" + mFunctions.getPrefUserId() + " MediumId->" + mFunctions.getPrefMediumId() +
                "\nUserName->" + mFunctions.getPrefUserName() +
                " Role->" + mFunctions.getPrefRole());


        Log.e("PREFGET-",
                "userId-"+mFunctions.getPrefUserId()+
                        "fullName-"+mFunctions.getPrefUserName()+
                        "role-"+mFunctions.getPrefRole()+
                        "image-"+mFunctions.getPrefUserImage());

        btnCreatePost = findViewById(R.id.btn_create_post);
        mIvBack = findViewById(R.id.ivback);

        recyclerviewPost = findViewById(R.id.recyclerview_post);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerviewPost.setHasFixedSize(true);
        recyclerviewPost.setLayoutManager(mLayoutManager);


        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(EducationCornerScreen.this, HomeScreen.class);
                startActivity(intent);
                finishAffinity();
            }
        });


        if (Functions.knowInternetOn(this)) {
            APIs.getEducationCornerList(EducationCornerScreen.this, this);
        } else {
            Functions.showInternetAlert(this);
        }

        btnCreatePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //intent = new Intent(EducationCornerScreen.this,CreatePostScreen.class);
                //startActivity(intent);
                showPostDialog();
            }
        });


    }

    public void showPostDialog() {

        dialogPost = new Dialog(
                this, R.style.popupTheme);
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_create_post, null);
        dialogPost.setContentView(view); // your custom view.
        dialogPost.setCancelable(true);
        dialogPost.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialogPost.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialogPost.show();

        mIvUserPic = view.findViewById(R.id.iv_user_profile);
        mTvUsername = view.findViewById(R.id.tv_user_name);
        mTvUsername.setText(mFunctions.getPrefUserName());
        edtPostTitle = view.findViewById(R.id.edt_title_post);
        edtYoutubeLink = view.findViewById(R.id.edt_youtube_link);
        edtYoutubeLink.setVisibility(View.GONE);
        btnAddImage = view.findViewById(R.id.btn_add_image);
        btnAddImage.setVisibility(View.GONE);
        btnAddPDF = view.findViewById(R.id.btn_add_pdf);
        btnAddPDF.setVisibility(View.GONE);
        btnAddPost = view.findViewById(R.id.btn_add_post);
        optionLayout = view.findViewById(R.id.ll_options);
        ivPostImg = view.findViewById(R.id.iv_image);
        tvPdfName = view.findViewById(R.id.tv_pdf_name);
        showPdfLayout = view.findViewById(R.id.ll_show_pdf);
        ivPdfIcon = view.findViewById(R.id.iv_pdf_icon);
        mLlAttachment = view.findViewById(R.id.ll_attachment_layout);
        mIvCloseDialog = view.findViewById(R.id.closedialog);
        rdoGrpUpload = view.findViewById(R.id.radioUpload);
        rdoYoutube = view.findViewById(R.id.radioYoutube);
        rdoImage = view.findViewById(R.id.radioImage);

       /* rdoYoutube.setOnCheckedChangeListener(new Radio_check());
        rdoImage.setOnCheckedChangeListener(new Radio_check());*/


        try {
            Log.e("userImage", "-->" + mFunctions.getPrefUserImage());
            Glide.with(this).load(mFunctions.getPrefUserImage())
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .priority(Priority.HIGH).dontAnimate()
                    .into(mIvUserPic);
        } catch (Exception e) {
            e.printStackTrace();
        }

        rdoGrpUpload.clearCheck();

        rdoGrpUpload.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    //Toast.makeText(EducationCornerScreen.this,"ssss-"+ rb.getText(), Toast.LENGTH_SHORT).show();

                    if (rb.getText().equals("Youtube")) {
                        rdoImage.setChecked(false);
                        btnAddImage.setVisibility(View.GONE);
                        ivPostImg.setVisibility(View.GONE);
                        edtYoutubeLink.setVisibility(View.VISIBLE);
                        //Log.e("rdoooYoutube", "checked");
                    } else {
                        edtYoutubeLink.setVisibility(View.GONE);
                        btnAddImage.setVisibility(View.GONE);
                        intent = new Intent(EducationCornerScreen.this, ImagePickActivity.class);
                        intent.putExtra(Constant.MAX_NUMBER, 1);
                        startActivityForResult(intent, Constant.REQUEST_CODE_PICK_IMAGE);
                        //Log.e("rdoooimage", "checked");
                    }

                }

            }
        });


        /*btnAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(EducationCornerScreen.this, ImagePickActivity.class);
                intent.putExtra(Constant.MAX_NUMBER, 1);
                startActivityForResult(intent, Constant.REQUEST_CODE_PICK_IMAGE);
            }
        });*/

        /*btnAddPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(EducationCornerScreen.this, NormalFilePickActivity.class);
                intent.putExtra(Constant.MAX_NUMBER, 1);
                intent.putExtra(NormalFilePickActivity.SUFFIX, new String[]{"pdf"});
                startActivityForResult(intent, Constant.REQUEST_CODE_PICK_FILE);
            }
        });*/

        btnAddPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    if (edtYoutubeLink.getText().toString().isEmpty() && (ivPostImg.getDrawable() == null)) {
                        Log.e("Both", "either imager or link");
                        Functions.ToastUtility(EducationCornerScreen.this,
                                "Either enter youtube link or select image..!!");
                    } else {

                        if (!tvPdfName.getText().toString().isEmpty()) {
                            if (edtPostTitle.getText().length() != 0) {
                                Log.e("pdfFile", "--->" + imgFile.toString());
                                new EducationCornerScreen.uploadImageTask().execute(imgFile.toString(),
                                        edtPostTitle.getText().toString());
                            } else {
                                Functions.ToastUtility(EducationCornerScreen.this,
                                        "You must fill in What's your mind...!!");
                            }
                        }


                    }


                    if (!edtYoutubeLink.getText().toString().isEmpty() && ivPostImg.getDrawable() == null) {
                        if (edtPostTitle.getText().length() != 0) {
                            Log.e("youtubelink", "call code for upload");
                            if (Functions.knowInternetOn(EducationCornerScreen.this)) {
                                APIs.postUploadLink(EducationCornerScreen.this, EducationCornerScreen.this,
                                        mFunctions.getPrefUserId(), edtPostTitle.getText().toString().trim(),
                                        edtYoutubeLink.getText().toString().trim());
                            } else {
                                Functions.showInternetAlert(EducationCornerScreen.this);
                            }
                        } else {
                            Functions.ToastUtility(EducationCornerScreen.this,
                                    "You must fill in What's your mind...!!");
                        }
                    } else if (ivPostImg.getDrawable() != null && edtYoutubeLink.getText().toString().isEmpty()) {
                        if (edtPostTitle.getText().length() != 0) {
                            Log.e("ivpostimg", "call code for upload");
                            Log.e("imgfile", "--->" + imgFile.toString());
                            new EducationCornerScreen.uploadImageTask().execute(imgFile.toString(),
                                    edtPostTitle.getText().toString());
                        } else {
                            Functions.ToastUtility(EducationCornerScreen.this,
                                    "You must fill in What's your mind...!!");
                        }
                    } else {
                        Functions.ToastUtility(EducationCornerScreen.this,
                                "Either enter youtube link or select image..!!!");
                    }






                    /*if (edtPostTitle.getText().length() == 0) {
                        Toast.makeText(EducationCornerScreen.this, "You must fill in What's your mind...!", Toast.LENGTH_SHORT).show();
                    } else if ((edtYoutubeLink.getText().toString().isEmpty()) && (ivPostImg.getDrawable() == null) && (tvPdfName.getText().toString().isEmpty())) {
                        Toast.makeText(EducationCornerScreen.this, "Please fill youtube link or select image or pdf file...!", Toast.LENGTH_SHORT).show();
                    } else {
                        if (!edtYoutubeLink.getText().toString().isEmpty()) {
                            if (edtPostTitle.getText().length() == 0) {
                                Toast.makeText(EducationCornerScreen.this, "You must fill in What's your mind...!", Toast.LENGTH_SHORT).show();
                            } else {
                                Log.e("imgfile", "--->" + imgFile.toString());
                                new EducationCornerScreen.uploadImageTask().execute(imgFile.toString(),
                                        edtPostTitle.getText().toString());
                            }
                        }
                        if (ivPostImg.getDrawable() != null) {
                            if (edtPostTitle.getText().length() == 0) {
                                Toast.makeText(EducationCornerScreen.this, "You must fill in What's your mind...!", Toast.LENGTH_SHORT).show();
                            } else {
                                new EducationCornerScreen.uploadImageTask().execute(imgFile.toString(), edtPostTitle.getText().toString());
                            }
                        } else if (!tvPdfName.getText().toString().isEmpty()) {
                            if (edtPostTitle.getText().length() == 0) {
                                Toast.makeText(EducationCornerScreen.this, "You must fill in What's your mind...!", Toast.LENGTH_SHORT).show();
                            } else {
                                new EducationCornerScreen.uploadImageTask().execute(imgFile.toString(), edtPostTitle.getText().toString());
                            }
                        }
                    }*/
                } catch (Exception e) {
                    e.printStackTrace();
                    //Toast.makeText(CreatePostScreen.this, "Please fill properly...!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mIvCloseDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogPost.dismiss();
            }
        });


    }

    public void onClear(View v) {
        /* Clears all selected radio buttons to default */
        rdoGrpUpload.clearCheck();
    }

    public void onSubmit(View v) {
        RadioButton rb = rdoGrpUpload.findViewById(rdoGrpUpload.getCheckedRadioButtonId());
        Toast.makeText(EducationCornerScreen.this, rb.getText(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.REQUEST_CODE_PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            ArrayList<ImageFile> list = data.getParcelableArrayListExtra(Constant.RESULT_PICK_IMAGE);
            ImageFile file = list.get(0);
            imgFile = new File(file.getPath());
            Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            ivPostImg.setImageBitmap(bitmap);
            mLlAttachment.setVisibility(View.VISIBLE);
            ivPostImg.setVisibility(View.VISIBLE);
            showPdfLayout.setVisibility(View.GONE);
            tvPdfName.setText("");
            edtYoutubeLink.setText("");
            flag = 1;
        }
        if (requestCode == Constant.REQUEST_CODE_PICK_FILE && resultCode == RESULT_OK && data != null) {
            Uri path = data.getData();
            ArrayList<NormalFile> list = data.getParcelableArrayListExtra(Constant.RESULT_PICK_FILE);
            NormalFile file = list.get(0);
            Log.e("ListArray", "Name:-" + file.getName() + "/nPath:-" + file.getPath());
            tvPdfName.setText(file.getName());
            imgFile = new File(file.getPath());
            mLlAttachment.setVisibility(View.VISIBLE);
            showPdfLayout.setVisibility(View.VISIBLE);
            ivPostImg.setVisibility(View.GONE);
            ivPostImg.setImageDrawable(null);
            edtYoutubeLink.setText("");
            flag = 2;
        }
    }

    @Override
    public void onResult(JSONObject jobjWhole) {
        try {
            if (jobjWhole != null) {
                JSONObject jObj = jobjWhole.optJSONObject(Connection.TAG_DATA);
                int strStatus = jObj.optInt("success");
                String strMessage = jObj.optString("message");
                String strApiname = jObj.optString("api");


                if (strApiname.equalsIgnoreCase("getEducationCornerList")) {
                    if (strStatus != 0) {
                        JSONArray jArrayTextSub = jObj.getJSONArray("educationCorner");
                        if (jArrayTextSub.length() > 0) {
                            listYoutubeVideo.clear();
                            for (int i = 0; i < jArrayTextSub.length(); i++) {
                                try {
                                    JSONObject obj = jArrayTextSub.getJSONObject(i);
                                    listYoutubeVideo.add(new YoutubeVideo(
                                            obj.optInt("postId"),
                                            obj.optInt("userId"),
                                            obj.optString("title"),
                                            obj.optString("image"),
                                            obj.optString("pdf"),
                                            obj.optString("youtubeLink"),
                                            obj.optString("like_count"),
                                            obj.optString("fullname"),
                                            obj.optString("schoolName"),
                                            obj.optString("role"),
                                            obj.optString("user_image")
                                    ));



                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (listYoutubeVideo.size() != 0) {
                                //mPostAdapter = new PostAdapter(EducationCornerScreen.this, listArrPost);

                                mPostUtubeAdapter = new PostUtubeAdapter(EducationCornerScreen.this, listYoutubeVideo);
                                recyclerviewPost.setAdapter(mPostUtubeAdapter);
                            }
                        }
                    } else {
                        Functions.ToastUtility(EducationCornerScreen.this, strMessage);
                        recyclerviewPost.setVisibility(View.GONE);
                    }

                } else if (strApiname.equalsIgnoreCase("postUploadlink")) {
                    if (strStatus != 0) {
                        Log.e("postId", "-->" + jObj.optString("postId"));
                        Functions.ToastUtility(EducationCornerScreen.this, strMessage);
                    } else {
                        Functions.ToastUtility(EducationCornerScreen.this, strMessage);
                    }
                    dialogPost.dismiss();
                    intent = new Intent(EducationCornerScreen.this, EducationCornerScreen.class);
                    startActivity(intent);
                    finishAffinity();
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*class Radio_check implements CompoundButton.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (rdoImage.isChecked()) {
                rdoYoutube.setChecked(false);

                edtYoutubeLink.setVisibility(View.GONE);
                btnAddImage.setVisibility(View.GONE);
                intent = new Intent(EducationCornerScreen.this, ImagePickActivity.class);
                intent.putExtra(Constant.MAX_NUMBER, 1);
                startActivityForResult(intent, Constant.REQUEST_CODE_PICK_IMAGE);
                Log.e("rdoooimage", "checked");

            } else if (rdoYoutube.isChecked()) {
                rdoImage.setChecked(false);
                btnAddImage.setVisibility(View.GONE);
                edtYoutubeLink.setVisibility(View.VISIBLE);
                Log.e("rdoooYoutube", "checked");
            }
        }
    }*/

    class uploadImageTask extends AsyncTask<String, Void, String> {
        String response_str = "";

        @Override
        protected void onPreExecute() {
            progressD = new ProgressDialog(EducationCornerScreen.this);
            progressD.setMessage("Uploading..Please wait..!!!");
            progressD.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            File file1 = new File(strings[0]);
            try {
                //qLog.e("data","--------"+strings[0]);
                HttpClient client = new DefaultHttpClient();
                HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000);
                HttpResponse response;
                try {
                    HttpPost post = new HttpPost("http://gujarateducation.org/android_api/postUpload.php");
                    MultipartEntity reqEntity = new MultipartEntity();
                    FileBody cbFile = new FileBody(file1);
                    Log.e("title:-----", "" + strings[1]);
                    reqEntity.addPart("file", cbFile);
                    //Log.e("UserId", "" + mFunctions.getPrefUserId());
                    reqEntity.addPart("userId", new StringBody("" + mFunctions.getPrefUserId()));
                    reqEntity.addPart("title", new StringBody("" + strings[1]));

                    post.setEntity(reqEntity);
                    response = client.execute(post);
                    if (response != null) {
                        response_str = EntityUtils.toString(response.getEntity());
                        //Log.e("response_strrr", response_str);
                        //progressD.dismiss();
                    } else {
                        Log.e("response", "-->" + response_str);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            progressD.dismiss();
            try {
                if (progressD.isShowing()) {
                    progressD.dismiss();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                progressD.dismiss();
            }
            try {
                result = response_str;
                Log.e("ResultHere", "--->" + result);
                //progressD.dismiss();
                if (result != null) {
                    edtPostTitle.setText("");
                    ivPostImg.setVisibility(View.GONE);
                    ivPostImg.setImageDrawable(null);
                    ivPdfIcon.setVisibility(View.GONE);
                    tvPdfName.setText("");
                    tvPdfName.setVisibility(View.GONE);
                    //    Functions.ToastUtility(EducationCornerScreen.this, "Upload Successfully.");
                    dialogPost.dismiss();
                    intent = new Intent(EducationCornerScreen.this, EducationCornerScreen.class);
                    startActivity(intent);
                    finishAffinity();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
