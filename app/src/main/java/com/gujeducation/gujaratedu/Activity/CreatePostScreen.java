package com.gujeducation.gujaratedu.Activity;

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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gujeducation.R;
import com.gujeducation.gujaratedu.Adapter.SubOptionAdapter;
import com.gujeducation.gujaratedu.Helper.Functions;
import com.vincent.filepicker.Constant;
import com.vincent.filepicker.activity.ImagePickActivity;
import com.vincent.filepicker.activity.NormalFilePickActivity;
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

import java.io.File;
import java.util.ArrayList;


public class CreatePostScreen extends AppCompatActivity {

    public ProgressDialog progressD;
    RelativeLayout.LayoutParams layoutParams;
    Intent intent;
    File imgFile = null;
    AppCompatImageView ivPostImg, ivPdfIcon;
    AppCompatTextView tvPdfName, mTvUsername;
    Functions mFunctions;
    int flag = 0;
    private EditText edtPostTitle, edtYoutubeLink;
    private AppCompatButton btnAddPost;
    private LinearLayout btnAddImage, btnAddPDF, optionLayout, showPdfLayout, llAttachment;
    private RelativeLayout relativeLayout;
    private AppCompatImageView btnBack;
    Dialog dialogPost;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
        mFunctions = new Functions(this);

        mTvUsername = findViewById(R.id.tv_user_name);
        mTvUsername.setText(mFunctions.getPrefUserName());
        edtPostTitle = findViewById(R.id.edt_title_post);
        edtYoutubeLink = findViewById(R.id.edt_youtube_link);
        btnAddImage = findViewById(R.id.btn_add_image);
        btnAddPDF = findViewById(R.id.btn_add_pdf);
        btnAddPost = findViewById(R.id.btn_add_post);
        optionLayout = findViewById(R.id.ll_options);
        ivPostImg = findViewById(R.id.iv_image);
        tvPdfName = findViewById(R.id.tv_pdf_name);
        showPdfLayout = findViewById(R.id.ll_show_pdf);
        ivPdfIcon = findViewById(R.id.iv_pdf_icon);
        llAttachment = findViewById(R.id.ll_attachment_layout);

        btnBack = findViewById(R.id.ivbackbt);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
                startActivityForResult(intent, 10);*/

                /*intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
                startActivityForResult(intent, 10);*/

                intent = new Intent(CreatePostScreen.this, ImagePickActivity.class);
//                intent.putExtra(IS_NEED_CAMERA,true);
                intent.putExtra(Constant.MAX_NUMBER, 1);
                startActivityForResult(intent, Constant.REQUEST_CODE_PICK_IMAGE);
            }
        });

        btnAddPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(intent.CATEGORY_OPENABLE);
                //intent.setType("application/pdf");
                intent.putExtra(Intent.EXTRA_MIME_TYPES,"application/pdf");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
                startActivityForResult(Intent.createChooser(intent, "Choose File to Upload.."), 20);*/

                intent = new Intent(CreatePostScreen.this, NormalFilePickActivity.class);
                intent.putExtra(Constant.MAX_NUMBER, 1);
                intent.putExtra(NormalFilePickActivity.SUFFIX, new String[]{"pdf"});
                startActivityForResult(intent, Constant.REQUEST_CODE_PICK_FILE);
            }
        });

        btnAddPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (edtPostTitle.getText().length() == 0) {
                        Toast.makeText(CreatePostScreen.this, "You must fill in What's your mind...!", Toast.LENGTH_SHORT).show();
                    } else if ((edtYoutubeLink.getText().toString().isEmpty()) && (ivPostImg.getDrawable() == null) && (tvPdfName.getText().toString().isEmpty())) {
                        Toast.makeText(CreatePostScreen.this, "Please fill youtube link or select image or pdf file...!", Toast.LENGTH_SHORT).show();
                    } else {
                        if (!edtYoutubeLink.getText().toString().isEmpty()) {
                            if (edtPostTitle.getText().length() == 0) {
                                Toast.makeText(CreatePostScreen.this, "You must fill in What's your mind...!", Toast.LENGTH_SHORT).show();
                            } else {
                                new uploadImageTask().execute(imgFile.toString(), edtPostTitle.getText().toString());
                            }
                        }
                        if (ivPostImg.getDrawable() != null) {
                            if (edtPostTitle.getText().length() == 0) {
                                Toast.makeText(CreatePostScreen.this, "You must fill in What's your mind...!", Toast.LENGTH_SHORT).show();
                            } else {
                                new uploadImageTask().execute(imgFile.toString(), edtPostTitle.getText().toString());
                            }
                        } else if (!tvPdfName.getText().toString().isEmpty()) {
                            if (edtPostTitle.getText().length() == 0) {
                                Toast.makeText(CreatePostScreen.this, "You must fill in What's your mind...!", Toast.LENGTH_SHORT).show();
                            } else {
                                new uploadImageTask().execute(imgFile.toString(), edtPostTitle.getText().toString());
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    //Toast.makeText(CreatePostScreen.this, "Please fill properly...!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.REQUEST_CODE_PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            /*Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            Log.e("DataImage", picturePath);
            cursor.close();*/
            ArrayList<ImageFile> list = data.getParcelableArrayListExtra(Constant.RESULT_PICK_IMAGE);
            ImageFile file = list.get(0);
            imgFile = new File(file.getPath());
            Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            ivPostImg.setImageBitmap(bitmap);
            llAttachment.setVisibility(View.VISIBLE);
            ivPostImg.setVisibility(View.VISIBLE);
            showPdfLayout.setVisibility(View.GONE);
            tvPdfName.setText("");
            edtYoutubeLink.setText("");
            flag = 1;
        }
        if (requestCode == Constant.REQUEST_CODE_PICK_FILE && resultCode == RESULT_OK && data != null) {
            /*String selectedFile = "";
            Uri uri = data.getData();
            String[] filePathColmn = {MediaStore.Files.FileColumns.DATA};
            Cursor cursor = managedQuery(uri,filePathColmn,null,null,null);
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA);
            cursor.moveToFirst();
            selectedFile = cursor.getString(columnIndex);*/

            Uri path = data.getData();
            ArrayList<NormalFile> list = data.getParcelableArrayListExtra(Constant.RESULT_PICK_FILE);
            NormalFile file = list.get(0);
            Log.e("ListArray", "Name:-" + file.getName() + "/nPath:-" + file.getPath());
            tvPdfName.setText(file.getName());
            imgFile = new File(file.getPath());
            llAttachment.setVisibility(View.VISIBLE);
            showPdfLayout.setVisibility(View.VISIBLE);
            ivPostImg.setVisibility(View.GONE);
            ivPostImg.setImageDrawable(null);
            edtYoutubeLink.setText("");

            //Log.e("DATAURI",path.toString());
            //Log.e("Datauri",selectedFile);
            /*String str = data.getData().getPath();
            Log.e("str--------",str);
            File file = new File(path.toString());
            Log.e("Datauri",file.toString());*/
            /*String fileName = data.getData().getPath().trim().toLowerCase();
            Log.e("FileName",fileName);
            if(fileName.endsWith(".pdf")) {
                //imgFile = new File(selectedFile);
                String[] strData = fileName.split("/");
                int strDataLength = strData.length;
                Log.e("Data", strData[strDataLength - 1]);
                tvPdfName.setText(strData[strDataLength - 1]);
                llAttachment.setVisibility(View.VISIBLE);
                showPdfLayout.setVisibility(View.VISIBLE);
                ivPostImg.setVisibility(View.GONE);
                ivPostImg.setImageDrawable(null);
                edtYoutubeLink.setText("");

                String selectedFile = "";*/
                /*String[] filePathColmn = {MediaStore.Files.FileColumns.DATA};
                Cursor cursor = managedQuery(path,filePathColmn,null,null,null);
                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA);
                cursor.moveToFirst();
                selectedFile = cursor.getString(columnIndex);
                cursor.close();
                imgFile = new File(selectedFile);
                Log.e("str--------",selectedFile);*/

                /*try {
                    inputStream = CreatePostScreen.this.getContentResolver().openInputStream(path);
                    pdfInBytes = new byte[inputStream.available()];
                    inputStream.read(pdfInBytes);
                    encodedPDF = Base64.encodeToString(pdfInBytes,Base64.DEFAULT);
                    flag = 1;
                    //Toast.makeText(this, ""+encodedStr, Toast.LENGTH_SHORT).show();
                    Log.e("DataPDF",""+encodedPDF);
                } catch (IOException e) {
                    e.printStackTrace();
                }*/

                /*final boolean isJellyBean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1;
                if(isJellyBean && DocumentsContract.isDocumentUri(this,path)){
                    if("com.android.externalstorage.documents".equals(path.getAuthority())){
                        final String docId = DocumentsContract.getDocumentId(path);
                        final String[] split = docId.split(":");
                        final String type = split[0];
                        if ("primary".equalsIgnoreCase(type)){
                            selectedFile = Environment.getExternalStorageDirectory() + "/" + split[1];
                            imgFile = new File(selectedFile);
                            Log.e("DataPDF",""+selectedFile);
                        }
                    }
                }*/
            flag = 2;
        }
    }




    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //  startActivity(new Intent(CreatePostScreen.this, EducationCornerScreen.class));
    }

    public class uploadImageTask extends AsyncTask<String, Void, String> {
        String response_str = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressD = ProgressDialog.show(CreatePostScreen.this, "", "Uploading..Please wait", true);
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
                    /*BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    BitmapFactory.decodeFile(strings[0], options);*/
                    Log.e("title:-----", "" + strings[1]);
                    reqEntity.addPart("file", cbFile);
                    Log.e("UserId", "" + mFunctions.getPrefUserId());
                    reqEntity.addPart("userId", new StringBody("" + mFunctions.getPrefUserId()));
                    reqEntity.addPart("title", new StringBody("" + strings[1]));
                    post.setEntity(reqEntity);
                    response = client.execute(post);
                    Log.e("offer_repsponse", String.valueOf(response));
                    if (response != null) {
                        response_str = EntityUtils.toString(response.getEntity());
                        progressD.dismiss();
                    } else {

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
            try {
                result = response_str;
                Log.e("ResultHere", "-" + result);
             /*  // Log.e("ResultHere", "-" + result.toString());
                JSONObject json = new JSONObject(result);
                //json = json.optJSONObject(Connection.TAG_DATA);
                int success = json.getInt("status");
                String message = json.getString("message");
                String error = json.getString("error");
                if (success == 1) {
                    Toast.makeText(CreatePostScreen.this, "" + message, Toast.LENGTH_SHORT).show();
                    //startActivity(new Intent(AddOfferActivity.this, CategoryActivity_Admin.class));
                } else {
                    Toast.makeText(CreatePostScreen.this, "" + message, Toast.LENGTH_SHORT).show();
                }*/
                if (result != null) {
                    edtPostTitle.setText("");
                    ivPostImg.setVisibility(View.GONE);
                    ivPostImg.setImageDrawable(null);
                    ivPdfIcon.setVisibility(View.GONE);
                    tvPdfName.setText("");
                    tvPdfName.setVisibility(View.GONE);
                    Functions.ToastUtility(CreatePostScreen.this, "Upload Successfully.");
                    startActivity(new Intent(CreatePostScreen.this, EducationCornerScreen.class));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}