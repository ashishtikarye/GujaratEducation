package com.gujeducation.gujaratedu.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.downloader.Error;
import com.downloader.OnCancelListener;
import com.downloader.OnDownloadListener;
import com.downloader.OnPauseListener;
import com.downloader.OnProgressListener;
import com.downloader.OnStartOrResumeListener;
import com.downloader.PRDownloader;
import com.downloader.Progress;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnTapListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.gujeducation.R;
import com.gujeducation.gujaratedu.Helper.DownloadTask;

import java.io.File;


public class PdfScreen extends AppCompatActivity implements View.OnClickListener {

    public static final int progressType = 0;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static final int REQUEST_WRITE_STORAGE = 112;
    private static final String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    AppCompatActivity activity;
    AppCompatImageView mIvBack, mIvDownload, mIvShare;
    String PDF, Name, replaceDotChapterName;
    AppCompatTextView mTvHeader;
    String urlPdf = "";
    ProgressDialog pbDialog;
    PDFView pdfView;
    File file;
    Intent intent;
    AdView mAdView;
    //public InterstitialAd interstitialAd;
    boolean isTrue = false;


    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );

          //  Log.e("vrfystorg", "ELSEprmisngrntd");

        } else {
            Log.e("vrfystorg", "IFprmisngrntd");

        }
    }
    public void checkPermissionReadStorage(Context context, Activity activity) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Log.e("chkprmsn", "IFCLICK");
            } else {
                Log.e("chkprmsn", "ELSECLICK");

                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_WRITE_STORAGE);
            }
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfviewer);

        activity = PdfScreen.this;
        checkPermissionReadStorage(this, this);
        verifyStoragePermissions(this);
        intent = getIntent();
        try {

            Log.e("PdfScreenIntent", "PDF-->" + intent.getExtras().getString("PDF") +
                    "\n ChapterName-->" + intent.getExtras().getString("Name"));

            if (!(intent.getExtras().getString("Name").isEmpty() && intent.getExtras().getString("PDF").isEmpty())) {
                //Name = intent.getExtras().getString("Name").replace(".", "");
                Name = intent.getExtras().getString("Name");
                PDF = intent.getExtras().getString("PDF");
               // Log.e("Name", "<==>" + Name);
                Name = Name.replace(".", "");
            }
        } catch (Exception e) {
            Log.e("Null Object exception", "Name");
        }


        /*try {
            if(!(intent.getExtras().getString("worksheetName").isEmpty() && intent.getExtras().getString("worksheetPDF").isEmpty())) {
                Name = intent.getExtras().getString("worksheetName").replace(".", "");
                PDF = intent.getExtras().getString("worksheetPDF");
            }
        }
        catch (Exception e){
            Log.e("Null Object","WorksheetName");
        }*/

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        /*interstitialAd = new InterstitialAd(PdfScreen.this);
        interstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        //interstitialAd.setAdUnitId("ca-app-pub-4992146571652437/2750111875");
        loadInterstitialAd();*/

        pdfView = findViewById(R.id.pdfview);
        mIvBack = findViewById(R.id.ivbackbt);
        mTvHeader = findViewById(R.id.header_title);
        mIvDownload = findViewById(R.id.ivdownnload);
        mIvDownload.setVisibility(View.GONE);
        mIvShare = findViewById(R.id.iv_share);
        //Log.e("PdfScreenClass", "PDF-->" + PDF + " ChapterName-->" + Name);
        mTvHeader.setText(Name);

        mIvBack.setOnClickListener(this);
        mIvDownload.setOnClickListener(this);
        mIvShare.setOnClickListener(this);
      //  Log.e("startDownloadFromNet", "vPDF->" + PDF + "\nName->" + Name);

        if (isTrue) {
         //   Log.e("ChapterPDF", "-->" + PDF);
            startDownloadFromNet(PDF, Name);
        } else {
            //Log.e("pdf", "sampleee.pdf");
            startDownloadFromNet(PDF, Name);
        }

    //    startDownloadFromNet(PDF, Name);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void startDownloadFromNet(String url, String nm) {
       // Log.e("startDownloadFromNet", "dataurl->" + url + "\n name->" + nm);


        pbDialog = new ProgressDialog(PdfScreen.this);
        pbDialog.setTitle("" + nm);
        pbDialog.setMessage("Loading Pdf...");
        pbDialog.setIndeterminate(false);
        pbDialog.setCancelable(false);
        pbDialog.show();
        isTrue = !isTrue;
        final String dirPath = getExternalCacheDir().getAbsolutePath();
        final String fileName = Name+".pdf";

        /*Log.e("downloadFromNet", "dirPath-" + dirPath +
                "\nName-" + fileName);*/
        String path = dirPath +File.separator+  fileName;
        /*if (Name.contains(".")) {
            path = dirPath + File.separator + replaceDotChapterName;
            Log.e("path", "if-" + path);
        } else {
            path = dirPath + File.separator + Name;
            Log.e("path", "else-" + path);
        }*/
//        Log.e("finalPath", "" + path);

        file = new File(path);
        if (file.exists()) {
          //  Log.e("file already", "exists-" + file);
            loadFromLoacal(path);
            pbDialog.dismiss();
        } else {
            //PRDownloader.download(url, dirPath, fileName)
            //Log.e("filenotexists", "url-" + url + "\ndirPath-" + dirPath + "\nName-" + fileName);
            PRDownloader.download(url, dirPath, fileName)
                    .build()
                    .setOnStartOrResumeListener(new OnStartOrResumeListener() {
                        @Override
                        public void onStartOrResume() {
                            //Log.e("PRDownloader", "onStartOrResume");
                        }
                    })
                    .setOnPauseListener(new OnPauseListener() {
                        @Override
                        public void onPause() {
                            //Log.e("PRDownloader", "onPause");

                        }
                    })
                    .setOnCancelListener(new OnCancelListener() {
                        @Override
                        public void onCancel() {
                            //Log.e("PRDownloader", "onCancel");

                        }
                    })
                    .setOnProgressListener(new OnProgressListener() {
                        @Override
                        public void onProgress(Progress progress) {
                            // Log.e("PRDownloader", "onProgress->" + progress);

                        }
                    })
                    .start(new OnDownloadListener() {
                        @Override
                        public void onDownloadComplete() {
                            //Log.e("PRDownloader", "onDownloadComplete");
                            Toast.makeText(PdfScreen.this, "Scoll down for next page", Toast.LENGTH_SHORT).show();
                            String path = dirPath + File.separator + fileName;//Name;//fileName;
                          //  Log.e("ListenerLoadloadLoacal-", "path-" + path);
                            loadFromLoacal(path);
                            pbDialog.dismiss();
                            //showInterstitialAd();
                        }

                        @Override
                        public void onError(Error error) {
                         //   Log.e("PRDownloader_er1", "======== 1121212212 ====" + error.getConnectionException().getMessage());
                            Log.e("PRDownloader_er2", "" + error.getResponseCode());
                        }
                    });
        }
    }

    private void loadFromLoacal(String path) {

        //String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download/demopdf.pdf";
        //  File file = new File(path);
        file = new File(path);
       // Log.e("loadingFromLocal", "file->" + file+"\n path->" + path);
        pdfView.fromFile(file)
                .enableSwipe(true) // allows to block changing pages using swipe
                .swipeHorizontal(false)
                .enableDoubletap(false)
                .defaultPage(0)
                .enableAnnotationRendering(false) // render annotations (such as comments, colors or forms)
                .password(null)
                .scrollHandle(null)
                /*.onTap(new OnTapListener() {
                    @Override
                    public boolean onTap(MotionEvent e) {
                        Log.e("onTap","Click-"+e);
                        Toast.makeText(getApplicationContext(), "onTap", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                })*/
                 .enableAntialiasing(true) // improve rendering a little bit on low-res screens
                // spacing between pages in dp. To define spacing color, set view background
                .spacing(0)
                //.invalidPageColor(Color.BLUE) // color of page that is invalid and cannot be loaded
                .load();
    }

    @Override
    public void onClick(View view) {
        try {
            if (view == mIvDownload) {
                try {
                    String filename = Name + ".pdf";
                    /*Log.e("mIvDownloadClick", "file-->" + file + " filename-->" + filename +
                            "\n getAbsoluteFile-->" + file.getAbsoluteFile() +
                            "\n fileString-->" + file.toString() +
                            "\n getNamefile-->" + file.getName());*/
                    new DownloadTask(PdfScreen.this, file, filename);
                    // Toast.makeText(PdfScreen.this, "Pdf save in internal storage in GujaratEducation folder.", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //  new DownloadFromURL().execute(getCatalogFullImage);
            } else if (view == mIvShare) {
                //Toast.makeText(PdfScreen.this, "mIvShare Click", Toast.LENGTH_SHORT).show();
                //Log.e("mIvShareClick", "pthfile->" + file);
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                sendIntent.setDataAndType(Uri.fromFile(file), "application/pdf");
                Uri apkURI = FileProvider.getUriForFile(
                        getApplicationContext(),
                        getApplicationContext().getApplicationContext()
                                .getPackageName() + ".provider", file);
                sendIntent.setDataAndType(apkURI, "application/pdf");
                sendIntent.putExtra(Intent.EXTRA_STREAM, apkURI);//uri
                startActivity(Intent.createChooser(sendIntent, "Share Chapter PDF Via!!"));

               /* Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                sendIntent.setDataAndType(Uri.fromFile(file), "application/pdf");
                Uri apkURI = FileProvider.getUriForFile(
                        getApplicationContext(),
                        getApplicationContext().getApplicationContext()
                                .getPackageName() + ".provider", file);
                sendIntent.setDataAndType(apkURI, "application/pdf");
                sendIntent.putExtra(Intent.EXTRA_STREAM, apkURI);//uri
                startActivity(Intent.createChooser(sendIntent, "Share Chapter PDF Via!!"));

                Log.e("mIvShare", "pthfile->" + file);


                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setAction(Intent.ACTION_SEND);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setDataAndType(Uri.fromFile(file), "application/pdf");
                intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
                startActivity(Intent.createChooser(intent, "Share Chapter PDF Via!!"));*/







                /*Intent share = new Intent(android.content.Intent.ACTION_SEND);
                share.setType("text/plain");
                share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                share.putExtra(Intent.EXTRA_SUBJECT, ChapterName);
                share.putExtra(Intent.EXTRA_TEXT, ChapterPDF);

                startActivity(Intent.createChooser(share, "Share Chapter PDF Via!!"));*/
            } else if (view == mIvBack) {

                finish();
                overridePendingTransition(R.anim.no_animation, R.anim.activity_back_out);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /*public void loadInterstitialAd() {
        // Creating  a Ad Request
        AdRequest adRequest = new AdRequest.Builder().build();

        // load Ad with the Request
        interstitialAd.loadAd(adRequest);

        // Showing a simple Toast message to user when an ad is Loading
        //Toast.makeText(HomeScreen.this, "Interstitial Ad is loading ", Toast.LENGTH_LONG).show();
    }
    public void showInterstitialAd() {
        if (interstitialAd.isLoaded()) {
            //showing the ad Interstitial Ad if it is loaded
            interstitialAd.show();

            // Showing a simple Toast message to user when an Interstitial ad is shown to the user
            //Toast.makeText(HomeScreen.this, "Interstitial is loaded and showing ad  ", Toast.LENGTH_LONG).show();
        } else {
            //Load the Interstitial ad if it is not loaded
            loadInterstitialAd();

            // Showing a simple Toast message to user when an ad is not loaded
            //Toast.makeText(HomeScreen.this, "Interstitial Ad is not Loaded ", Toast.LENGTH_LONG).show();
        }
    }*/
}
