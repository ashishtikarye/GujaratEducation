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
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
import com.gujeducation.BuildConfig;
import com.gujeducation.R;
import com.gujeducation.gujaratedu.Helper.Connection;
import com.gujeducation.gujaratedu.Helper.Functions;
import com.gujeducation.gujaratedu.Interface.OnResult;
import com.gujeducation.gujaratedu.Model.LikeCount;
import com.gujeducation.gujaratedu.Model.YoutubeVideo;
import com.gujeducation.gujaratedu.ServerAPIs.APIs;
import com.pierfrancescosoffritti.youtubeplayer.player.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.youtubeplayer.player.YouTubePlayerView;
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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class EducationCornerScreen extends AppCompatActivity implements OnResult {

    // private final ArrayList<Post> listArrPost = new ArrayList<Post>();
    private final ArrayList<YoutubeVideo> listYoutubeVideo = new ArrayList<YoutubeVideo>();
    public ProgressDialog progressD;
    String likeCount = "";
    LinearLayout btnCreatePost;
    RecyclerView recyclerviewPost;
    LinearLayoutManager mLayoutManager;
    PostUtubeAdapter mPostUtubeAdapter;
    Functions mFunctions;
    Intent intent;
    RelativeLayout.LayoutParams layoutParams;
    File imgFile = null;
    AppCompatImageView ivPostImg, ivPdfIcon, mIvBack;//, mIvOption;
    AppCompatTextView tvPdfName, mTvUsername, mTvLikeCount, mTvCreatePost;
    CircleImageView mIvUserPic;
    int flag = 0, myPostId;
    boolean isUpdate = false;
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
                "userId-" + mFunctions.getPrefUserId() +
                        "fullName-" + mFunctions.getPrefUserName() +
                        "role-" + mFunctions.getPrefRole() +
                        "image-" + mFunctions.getPrefUserImage());

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
            APIs.getEducationCornerList(EducationCornerScreen.this, this, mFunctions.getPrefUserId());
        } else {
            Functions.showInternetAlert(this);
        }


        btnCreatePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //intent = new Intent(EducationCornerScreen.this,CreatePostScreen.class);
                //startActivity(intent);
                showPostDialog("create", 0, mFunctions.getPrefUserId(), "", "", "");
            }
        });


    }

    public void CallDoLike(int postId) {
        try {
            Log.e("api", "Like-UserId-" + mFunctions.getPrefUserId() + " postId-" + postId);
            APIs.doLikePost(this, this, mFunctions.getPrefUserId(), postId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void CallDoDisLike(int postId) {
        try {
            Log.e("api", "DisLike-UserId-" + mFunctions.getPrefUserId() + " postId-" + postId);
            APIs.doDislikePost(this, this, mFunctions.getPrefUserId(), postId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        menu.setHeaderTitle("Select The Action");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.edit) {
            Toast.makeText(getApplicationContext(), "edit code", Toast.LENGTH_LONG).show();
        } else if (item.getItemId() == R.id.delete) {
            Toast.makeText(getApplicationContext(), "delete code", Toast.LENGTH_LONG).show();
        } else {
            return false;
        }
        return true;
    }

    public void showPostDialog(final String action, final int postId, final int userId, final String title,
                               final String image, final String youtubeLink) {

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

        mTvCreatePost = view.findViewById(R.id.tvcreatepost);
        btnAddPost = view.findViewById(R.id.btn_add_post);


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
        if (action.equalsIgnoreCase("create")) {
            mTvCreatePost.setText("Create Post");
        } else {
            myPostId = postId;
            isUpdate = true;
            Log.e("myPostId1", "" + myPostId);
            mTvCreatePost.setText("Update Post");
            edtPostTitle.setText(title);
            if (!youtubeLink.equalsIgnoreCase("NA")) {
                rdoImage.setChecked(false);
                rdoYoutube.setChecked(true);
                edtYoutubeLink.setVisibility(View.VISIBLE);
                edtYoutubeLink.setText(youtubeLink);
            }

            if (!image.equalsIgnoreCase("NA")) {

                mLlAttachment.setVisibility(View.VISIBLE);
                ivPostImg.setVisibility(View.VISIBLE);
                showPdfLayout.setVisibility(View.GONE);

                try {
                    Log.e("serverImage", "-->" + mFunctions.getPrefUserImage());
                    Glide.with(this).load(image)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .priority(Priority.HIGH).dontAnimate()
                            .into(ivPostImg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        }

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
                                Log.e("myPostId2", "--->" + myPostId);

                                if (isUpdate) {
                                    new EducationCornerScreen.uploadUpdateImageTask().execute(imgFile.toString(),
                                            edtPostTitle.getText().toString());
                                } else {
                                    new EducationCornerScreen.uploadImageTask().execute(imgFile.toString(),
                                            edtPostTitle.getText().toString());
                                }
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
                            Log.e("myPostId3", "--->" + myPostId);

                            if (isUpdate) {
                                new EducationCornerScreen.uploadUpdateImageTask().execute(imgFile.toString(),
                                        edtPostTitle.getText().toString());
                            } else {
                                new EducationCornerScreen.uploadImageTask().execute(imgFile.toString(),
                                        edtPostTitle.getText().toString());
                            }



                            /*new EducationCornerScreen.uploadImageTask().execute(imgFile.toString(),
                                    edtPostTitle.getText().toString());*/
                        } else {
                            Functions.ToastUtility(EducationCornerScreen.this,
                                    "You must fill in What's your mind...!!");
                        }
                    } else {
                        Functions.ToastUtility(EducationCornerScreen.this,
                                "Either enter youtube link or select image..!!!");
                    }


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

                System.out.println("APIName-" + strApiname);


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
                                            obj.optInt("like"),
                                            obj.optString("title"),
                                            obj.optString("image"),
                                            obj.optString("pdf"),
                                            obj.optString("youtubeLink"),
                                            obj.optString("datetime"),
                                            obj.optString("like_count"),
                                            obj.optString("fullname"),
                                            obj.optString("schoolName"),
                                            obj.optString("role"),
                                            obj.optString("user_image"), false
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
                } else if (strApiname.equalsIgnoreCase("doLike")) {
                    if (strStatus == 1) {
                        Functions.ToastUtility(EducationCornerScreen.this, strMessage);
                        likeCount = jObj.optString("like_count");
                        //listlikeCount.add(new LikeCount(jObj.optString("like_count")));
                        Log.e("likeCount", "like--" + likeCount);
                    } else if (strStatus == 2) {
                        Functions.ToastUtility(EducationCornerScreen.this, strMessage);
                        likeCount = jObj.optString("like_count");
                        //listlikeCount.add(new LikeCount(jObj.optString("like_count")));
                        Log.e("likeCount", "unlike--" + likeCount);
                    } else {
                        Functions.ToastUtility(EducationCornerScreen.this, strMessage);
                    }
                    mTvLikeCount.setText(likeCount);
                    // updateData(listYoutubeVideo);
                } else if (strApiname.equalsIgnoreCase("doDislike")) {
                    if (strStatus == 1) {
                        Functions.ToastUtility(EducationCornerScreen.this, strMessage);
                        likeCount = jObj.optString("like_count");
                        Log.e("likeCount", "unlike1--" + likeCount);
                        //  mPostUtubeAdapter.notifyDataSetChanged();

                        //listlikeCount.add(new LikeCount(jObj.optString("like_count")));
                    } else {
                        Functions.ToastUtility(EducationCornerScreen.this, strMessage);
                    }
                    mTvLikeCount.setText(likeCount);

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

    class uploadUpdateImageTask extends AsyncTask<String, Void, String> {
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
                    HttpPost post = new HttpPost("http://gujarateducation.org/android_api/postUploadUpdate.php");
                    MultipartEntity reqEntity = new MultipartEntity();
                    FileBody cbFile = new FileBody(file1);
                    Log.e("title:-----", "" + strings[1]);
                    Log.e("myPostId4:-----", "" + myPostId);
                    reqEntity.addPart("file", cbFile);
                    //Log.e("UserId", "" + mFunctions.getPrefUserId());
                    reqEntity.addPart("userId", new StringBody("" + mFunctions.getPrefUserId()));
                    reqEntity.addPart("title", new StringBody("" + strings[1]));
                    reqEntity.addPart("postId", new StringBody("" + myPostId));

                    post.setEntity(reqEntity);
                    response = client.execute(post);
                    if (response != null) {
                        response_str = EntityUtils.toString(response.getEntity());
                        //Log.e("response_strrr", response_str);
                        //progressD.dismiss();
                    } else {
                        Log.e("responseUpdt", "-->" + response_str);
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


    public class PostUtubeAdapter extends RecyclerView.Adapter<PostUtubeAdapter.MyViewHolder> implements OnResult {
        private final AppCompatActivity activity;
        public boolean error = false;
        ArrayList<YoutubeVideo> listPostUtube = new ArrayList();
        ArrayList<LikeCount> listLikeCount = new ArrayList();
        Functions mFunction;
        String videoCode;
        String strVideoCode;
        Dialog dialogPopup, dialogPost;
        AppCompatImageView mIvPopupImage, mIvClosePost, mIvClose;
        LinearLayout mLlEditPost, mLlDeletePost, mLlReportPost;
        String userImage = "";
        int likeStatus;
        int myPosition = 0;
        private int mNumColumns = 0;


        public PostUtubeAdapter(AppCompatActivity activity, ArrayList<YoutubeVideo> listPostUtube) {
            this.activity = activity;
            this.listPostUtube = listPostUtube;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup holder, int i) {
            View itemView = LayoutInflater.from(holder.getContext()).inflate(R.layout.row_post, holder, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            final MyViewHolder menuItemHolder = holder;
            mFunction = new Functions(activity);
            final YoutubeVideo utubeList = listPostUtube.get(position);
//        final LikeCount likeList = listLikeCount.get(position);
            menuItemHolder.mIvLike.setTag(R.string.position, position);


            try {

                //if (activity instanceof EducationCornerScreen) {holder.mIvLike.setVisibility(View.GONE);holder.mIvDislike.setVisibility(View.GONE);
                //}

                menuItemHolder.mTvSchoolName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.e("position", "===>" + position);
                        Log.e("postID", "===>" + utubeList.getPostId());
                        Log.e("api", "likeStatussss--" + likeStatus);

                    }
                });


                //Log.e("likeCount", "adp--" + utubeList.getLike_count());
                menuItemHolder.mTvDateTime.setText(utubeList.getDatetime());
                mTvLikeCount.setText(utubeList.getLike_count());
                menuItemHolder.mTvFullName.setText(utubeList.getFullname());
                menuItemHolder.mTvSchoolName.setText(utubeList.getSchoolname());
                if (utubeList.getRole().equalsIgnoreCase("T")) {
                    menuItemHolder.mTvUserType.setText("[Teacher]");
                } else {
                    menuItemHolder.mTvUserType.setText("[Student]");
                }
                menuItemHolder.mTvTitle.setText(utubeList.getTitle());

                try {
                    //Log.e("showPopup", "getUser_image-->" + utubeList.getUser_image());
                    userImage = utubeList.getUser_image();
                    Glide.with(activity).load(utubeList.getUser_image())
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .priority(Priority.HIGH).dontAnimate()
                            .into(menuItemHolder.mIvUserPic);
                } catch (Exception e) {
                    e.printStackTrace();
                }


                likeStatus = Integer.valueOf(listPostUtube.get(position).getLike());
                Log.e("api", "likeStatus--" + likeStatus);

                if (likeStatus == 1) {
                    utubeList.setActive(true);
                    Log.e("api", "likeStatus is true");
                    //   menuItemHolder.mIvLike.setImageResource(R.drawable.ic_like);
                } else {
                    utubeList.setActive(false);
                    Log.e("api", "likeStatus is false");
                    //menuItemHolder.mIvLike.setImageResource(R.drawable.ic_like);
                }


                menuItemHolder.mIvLike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.e("api", "statusActiveOrNot--" + utubeList.isActive());
                        if (utubeList.isActive()) {
                            Log.e("api", "call dislike");
                            ((EducationCornerScreen) activity).CallDoDisLike(utubeList.getPostId());
                        } else {
                            Log.e("api", "call like");
                            ((EducationCornerScreen) activity).CallDoLike(utubeList.getPostId());
                        }
                        notifyItemChanged(position);
/*


                    if (utubeList.isActive()) {
                        Log.e("RemoveWishlistAPI", "--" + isLikeAdd + utubeList.getPostId());
                        utubeList.setActive(false);
                        ((EducationCornerScreen) activity).CallDoDisLike(utubeList.getPostId());
                    } else {
                        Log.e("AddWishlistAPI", "--" + isLikeAdd + utubeList.getPostId());
                        utubeList.setActive(true);
                        ((EducationCornerScreen) activity).CallDoLike(utubeList.getPostId());
                    }
                    Log.e("position", "position--" + position);
                    //mTvLikeCount.setText(likeCount);
                    notifyItemChanged(position);
                    //notifyDataSetChanged();
*/


                    }
                });

                /*if (isLikeAdd) {
                    //Log.e("isWishlistAdd", "status is true->" + isWishlistAdd);
                    //menuItemHolder.mIvLike.setImageResource(R.drawable.ic_like);
                } else {
                    //Log.e("isWishlistAdd", "status is false->" + isWishlistAdd);
                    //menuItemHolder.mIvLike.setImageResource(R.drawable.ic_like);
                }
*/

                if (!utubeList.getImage().equalsIgnoreCase("NA")) {
                    menuItemHolder.mIvThumbnail.setVisibility(View.VISIBLE);
                    menuItemHolder.playButton.setVisibility(View.GONE);
                    menuItemHolder.youTubePlayerView.setVisibility(View.GONE);
                    Glide.with(activity)
                            .load(utubeList.getImage())
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .priority(Priority.IMMEDIATE).dontAnimate()
                            .into(menuItemHolder.mIvThumbnail);
                } else {
                    menuItemHolder.mIvThumbnail.setVisibility(View.GONE);
                }


                //youtube link
                if (!utubeList.getYoutubeLink().equalsIgnoreCase("NA")) {
                    menuItemHolder.mIvThumbnail.setVisibility(View.GONE);
                    menuItemHolder.playButton.setVisibility(View.VISIBLE);
                    menuItemHolder.youTubePlayerView.setVisibility(View.VISIBLE);
                /*try {
                    Log.e("onBindViewHolder", "youtbLink->" + utubeList.getYoutubeLink() + " - " + utubeList.getTitle());
                    if (utubeList.getYoutubeLink().contains("https://www.youtube.com/")) {
                        extractYoutubeCode(utubeList.getYoutubeLink());
                    } else {
                        extractYoutubeCodeShort(utubeList.getYoutubeLink());
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }*/
                } else {
                    menuItemHolder.playButton.setVisibility(View.GONE);
                    menuItemHolder.youTubePlayerView.setVisibility(View.GONE);
                }
                //Log.e("videoCodefinal->", "" + videoCode);

            } catch (Exception e) {
                e.printStackTrace();
            }


            holder.mIvThumbnail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPopup(utubeList.getImage());
                }
            });


            holder.playButton.setOnClickListener(view -> {
                holder.youTubePlayerView.setVisibility(View.VISIBLE);
                holder.playButton.setVisibility(View.GONE);
                holder.youTubePlayerView.initialize(initializedYouTubePlayer -> initializedYouTubePlayer.addListener(new AbstractYouTubePlayerListener() {
                    @Override
                    public void onReady() {

                        try {
                            if (utubeList.getYoutubeLink().contains("https://www.youtube.com/")) {
                                strVideoCode = extractYoutubeCode(utubeList.getYoutubeLink());
                                //Log.e("LongLink->", "" + strVideoCode);
                            } else {
                                strVideoCode = extractYoutubeCodeShort(utubeList.getYoutubeLink());
                                //Log.e("ShortLink->", "" + strVideoCode);
                            }
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }


                        //Log.e("onReady", "strVideoCode->" + strVideoCode);
                        initializedYouTubePlayer.loadVideo(strVideoCode, 0);
                        strVideoCode = "";
                    }
                }), true);
            });




            /*if (flaglike == true) {
                flaglike = false;
            }*/
            //Log.e("flagwish_statushere", "--" + flagwish + "\n chkwish-" + chkwish);

            /*if (flaglike == false && chklike == 1) {
                //Log.e("item", "Something to do");
                if (utubeList.isActive()) {
                    // menuItemHolder.mIvLike.setImageResource(R.drawable.ic_like);
                    Log.e("item", "isAct--" + likeCount);
                   // mTvLikeCount.setText(likeCount);

                } else {
                    //    menuItemHolder.mIvLike.setImageResource(R.drawable.ic_like);
                    Log.e("item", "isNotAct--" + likeCount);
                   // mTvLikeCount.setText(likeCount);
                }

            } else {
                //  Log.e("item", "Do Nothing");
            }*/


            menuItemHolder.mIvShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT,
                            utubeList.getTitle()+"\n\nHey check out learning app at: https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
                    sendIntent.setType("text/plain");
                    startActivity(sendIntent);
                }
            });

            //mIvOption.setOnClickListener(new View.OnClickListener() {
            menuItemHolder.mIvOption.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //  registerForContextMenu(view);

                    myPosition = position;
                    Log.e("mIvOPTIONn", "postId-->" + utubeList.getPostId() +
                            " prefUserId-->" + mFunctions.getPrefUserId() +
                            " position-" + position +
                            " myPosition-" + myPosition);
                    callApi(utubeList.getPostId(), mFunctions.getPrefUserId());
                   /* PopupMenu popup = new PopupMenu(activity, view);
                    MenuInflater inflater = popup.getMenuInflater();
                    inflater.inflate(R.menu.menu_main, popup.getMenu());

                 //   if(utubeList)

                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.edit:
                                    Toast.makeText(activity, "Edit", Toast.LENGTH_SHORT).show();
                                    return true;
                                case R.id.delete:
                                    Toast.makeText(activity, "Delete", Toast.LENGTH_SHORT).show();
                                    return true;
                                default:
                                    return false;
                            }
                        }
                    });
                    popup.show();*/
                }
            });


        }


        private void callApi(int postId, int userId) {
            if (Functions.knowInternetOn(activity)) {
                APIs.checkEditDelReport(activity, this, userId, postId);
            } else {
                Functions.showInternetAlert(activity);
            }
        }

        private void callGetPostApi(int postId, int userId) {
            if (Functions.knowInternetOn(activity)) {
                APIs.getPostInfo(activity, this, userId, postId);
            } else {
                Functions.showInternetAlert(activity);
            }
        }

        private void callDeletePostApi(int postId, int userId) {
            if (Functions.knowInternetOn(activity)) {
                APIs.deletePost(activity, this, userId, postId);
            } else {
                Functions.showInternetAlert(activity);
            }
        }


        private void callReportPostApi(int postId) {
            if (Functions.knowInternetOn(activity)) {
                APIs.reportPost(activity, this, postId);
            } else {
                Functions.showInternetAlert(activity);
            }
        }

        public void updateData(ArrayList<YoutubeVideo> viewModels) {
            listPostUtube.clear();
            listPostUtube.addAll(viewModels);
            notifyDataSetChanged();
        }

        public void showPopup(final String image) {

            dialogPopup = new Dialog(
                    activity, R.style.popupTheme);
            LayoutInflater inflater = (LayoutInflater)
                    activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.dialog_popup, null);
            dialogPopup.setContentView(view); // your custom view.
            dialogPopup.setCancelable(true);
            dialogPopup.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            dialogPopup.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
            dialogPopup.show();


            mIvPopupImage = view.findViewById(R.id.ivpopupimage);
            mIvClose = view.findViewById(R.id.ivclose);

            try {
                //Log.e("showPopup", "link-->" + link);
                Glide.with(activity).load(image)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .priority(Priority.HIGH).dontAnimate()
                        .into(mIvPopupImage);
            } catch (Exception e) {
                e.printStackTrace();
            }


            mIvPopupImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogPopup.dismiss();
                }
            });

            mIvClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogPopup.dismiss();
                }
            });


        }

        public void showPopupEditDeleteReport(final int flag, final int postId) {

            dialogPost = new Dialog(
                    activity, R.style.popupTheme);
            LayoutInflater inflater = (LayoutInflater)
                    activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.popup_editdelreprt, null);
            dialogPost.setContentView(view); // your custom view.
            dialogPost.setCancelable(true);
            dialogPost.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            dialogPost.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
            dialogPost.show();


            mLlEditPost = view.findViewById(R.id.lveditpost);
            mLlDeletePost = view.findViewById(R.id.lvdeletepost);
            mLlReportPost = view.findViewById(R.id.lvreportpost);
            mIvClosePost = view.findViewById(R.id.ivclosepost);
            //flag = 1 edit/delete
            //flag = 2 report
            if (flag == 1) {
                mLlEditPost.setVisibility(View.VISIBLE);
                mLlDeletePost.setVisibility(View.VISIBLE);
                mLlReportPost.setVisibility(View.GONE);
            } else {
                mLlEditPost.setVisibility(View.GONE);
                mLlDeletePost.setVisibility(View.GONE);
                mLlReportPost.setVisibility(View.VISIBLE);
            }


            mLlDeletePost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callDeletePostApi(postId, mFunctions.getPrefUserId());
                    dialogPost.dismiss();
                }
            });

            mLlEditPost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callGetPostApi(postId, mFunctions.getPrefUserId());
                    dialogPost.dismiss();
                }
            });

            mLlReportPost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callReportPostApi(postId);
                    dialogPost.dismiss();
                }
            });

            mIvClosePost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogPost.dismiss();
                }
            });


        }

        public String extractYoutubeCode(String url) throws MalformedURLException {
            String query = new URL(url).getQuery();
            String[] param = query.split("&");
            String id = null;
            for (String row : param) {
                String[] param1 = row.split("=");
                if (param1[0].equals("v")) {
                    id = param1[1];
                }
            }
            return id;
        }

        public String extractYoutubeCodeShort(String url) throws MalformedURLException {
            return url.substring(url.lastIndexOf("/") + 1);
        }


        @Override
        public int getItemCount() {
            return listPostUtube.size();
        }

        public int getNumColumns() {
            return this.mNumColumns;
        }

        public void setNumColumns(int numColumns) {
            this.mNumColumns = numColumns;
        }

        @Override
        public void onResult(JSONObject jobjWhole) {
            try {
                if (jobjWhole != null) {
                    JSONObject jObj = jobjWhole.optJSONObject(Connection.TAG_DATA);
                    int strStatus = jObj.optInt("success");
                    int flgEditDel = jObj.optInt("flagEdtDel");
                    int postId = jObj.optInt("postId");
                    String strMessage = jObj.optString("message");
                    String strApi = jObj.optString("api");

                    if (strApi.equalsIgnoreCase("checkEditDelReport")) {
                        if (flgEditDel == 1) {
                            showPopupEditDeleteReport(1, postId);//allow edit del
                        } else {
                            showPopupEditDeleteReport(2, postId);//allow report
                        }
                    } else if (strApi.equalsIgnoreCase("getPostInfo")) {
                        if (strStatus == 1) {
                            jObj.optInt("postId");
                            jObj.optInt("userId");
                            jObj.optString("title");
                            jObj.optString("image");
                            jObj.optString("youtubeLink");
                            showPostDialog("update", jObj.optInt("postId"), jObj.optInt("userId"),
                                    jObj.optString("title"), jObj.optString("image"), jObj.optString("youtubeLink"));

                        }
                    } else if (strApi.equalsIgnoreCase("deletePost")) {
                        Functions.ToastUtility(activity, strMessage);
                        notifyItemRemoved(myPosition);

                    } else if (strApi.equalsIgnoreCase("reportPost")) {
                        Functions.ToastUtility(activity, strMessage);
                        notifyItemRemoved(myPosition);

                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        public class MyViewHolder extends RecyclerView.ViewHolder {
            AppCompatTextView mTvFullName, mTvUserType, mTvSchoolName, mTvTitle, mTvDateTime;
            AppCompatImageView mIvThumbnail, mIvOption, mIvShare, mIvLike;//,mIvDislike;
            AppCompatImageView playButton;
            YouTubePlayerView youTubePlayerView;
            CircleImageView mIvUserPic;

            public MyViewHolder(View itemView) {
                super(itemView);
                mTvFullName = itemView.findViewById(R.id.tvfullname);
                mTvUserType = itemView.findViewById(R.id.tvusertype);
                mTvSchoolName = itemView.findViewById(R.id.tvschoolname);
                mTvTitle = itemView.findViewById(R.id.tvtopicname);
                mIvThumbnail = itemView.findViewById(R.id.ivthumbnail);
                mTvLikeCount = itemView.findViewById(R.id.likeCount);
                mTvDateTime = itemView.findViewById(R.id.tvdatetime);
                playButton = itemView.findViewById(R.id.btnPlay);
                youTubePlayerView = itemView.findViewById(R.id.youtubeView);
                mIvUserPic = itemView.findViewById(R.id.ivuser);
                //------------------------------------//
                mIvLike = itemView.findViewById(R.id.ivLike);
                //mIvDislike = itemView.findViewById(R.id.ivDisike);
                //mIvDownload = itemView.findViewById(R.id.ivDownload);
                mIvShare = itemView.findViewById(R.id.ivShare);
                mIvOption = itemView.findViewById(R.id.ivOption);

            }
        }
    }

}
