package com.gujeducation.gujaratedu.Helper;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.widget.Button;

import androidx.core.app.ActivityCompat;

import com.gujeducation.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/*import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;*/

/**
 * Created by SONU on 29/10/15.
 */
public class DownloadTask {
    private static final String TAG = "Download Task";
    private Context context;
    private String downloadFileName;
    private File downloadUrl;
    private ProgressDialog progressDialog;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static final int REQUEST_WRITE_STORAGE = 112;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };


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
        }
    }


    public DownloadTask(Context context, File downloadUrl, String downloadFileName) {
        this.context = context;
        this.downloadFileName = downloadFileName;
        this.downloadUrl = downloadUrl;


        /*downloadFileName = downloadUrl.substring(downloadUrl.lastIndexOf('/'));//Create file name by picking download file name from URL
        Log.e(TAG, downloadFileName);*/

        //Start Downloading Task
        new DownloadingTask().execute();
    }

    private class DownloadingTask extends AsyncTask<Void, Void, Void> {

        File sourceLocation=null;
        File targetLocation=null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Downloading PDF...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @SuppressLint("ResourceAsColor")
        @Override
        protected void onPostExecute(Void result) {
            try {
                if (targetLocation != null) {
                    progressDialog.dismiss();

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("PDF");
                    builder.setMessage("Downloaded Successfully");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.setNegativeButton("Open", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            File file  = new File(Environment.getExternalStorageDirectory() + "/GujaratEducation/" + downloadFileName);  // -> filename = maven.pdf

                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setDataAndType(Uri.fromFile(file), "application/pdf");
                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                            context.startActivity(intent);
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();

                    Button positiveBtn = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                    Button negativeBtn = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);

                    positiveBtn.setBackgroundColor(context.getResources().getColor(R.color.white));
                    positiveBtn.setTextColor(context.getResources().getColor(R.color.odiya));
                    negativeBtn.setBackgroundColor(context.getResources().getColor(R.color.white));
                    negativeBtn.setTextColor(context.getResources().getColor(R.color.colorPrimary));

                } else {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                        }
                    }, 3000);
                }
            } catch (Exception e) {
                e.printStackTrace();

                //Change button text if exception occurs

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                    }
                }, 3000);

            }


            super.onPostExecute(result);
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            try {



                sourceLocation = new File(downloadUrl.toString());
                targetLocation = new File(Environment.getExternalStorageDirectory() + "/GujaratEducation/" + downloadFileName);  // -> filename = maven.pdf
                File targetLocationMkDir = new File(Environment.getExternalStorageDirectory() + "/GujaratEducation");


                if (!targetLocationMkDir.exists()) {
                    targetLocationMkDir.mkdir();
                }
                if(sourceLocation.exists()){
                    InputStream in = new FileInputStream(sourceLocation);
                    OutputStream out = new FileOutputStream(targetLocation);

                    // Copy the bits from instream to outstream
                    byte[] buf = new byte[1024];
                    int len;

                    while ((len = in.read(buf)) > 0) {
                        out.write(buf, 0, len);
                    }

                    in.close();
                    out.close();

                }


            } catch (Exception e) {

                //Read exception if something went wrong
                e.printStackTrace();
                targetLocation = null;
            }

            return null;
        }
    }
}
