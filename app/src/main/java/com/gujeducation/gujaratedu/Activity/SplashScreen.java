package com.gujeducation.gujaratedu.Activity;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.gujeducation.R;
import com.gujeducation.gujaratedu.Helper.Functions;
import com.gujeducation.gujaratedu.Helper.MarketVersionChecker;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.util.List;

import static com.gujeducation.gujaratedu.Helper.CommonMethod.ManageScreenView;


public class SplashScreen extends AppCompatActivity {

    public static final int RequestPermissionCode = 7;
    public static String playstore_version, device_version;
    private final int REQUEST_READ_PHONE_STATE_PERMISSION = 777;
    SharedPreferences mPreference;
    SharedPreferences.Editor editor;
    Functions mFunction;
    boolean val_login;
    File apkStorage = null;
    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }*/

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ManageScreenView(this);
        mFunction = new Functions(this);

        //Log.e("PreferLangID", "----->" + mFunction.getPrefMediumId());

        if (mFunction.getPrefMediumId() == 1) {
            com.gujeducation.gujaratedu.Helper.Language.set(SplashScreen.this, "gu");
        }
        if (mFunction.getPrefMediumId() == 2) {
            com.gujeducation.gujaratedu.Helper.Language.set(SplashScreen.this, "en");
        }
        if (mFunction.getPrefMediumId() == 3) {
            com.gujeducation.gujaratedu.Helper.Language.set(SplashScreen.this, "hi");
        }
        if (mFunction.getPrefMediumId() == 4) {
            com.gujeducation.gujaratedu.Helper.Language.set(SplashScreen.this, "mr");
        }
        if (mFunction.getPrefMediumId() == 5) {
            com.gujeducation.gujaratedu.Helper.Language.set(SplashScreen.this, "od");
        }
        if (mFunction.getPrefMediumId() == 6) {
            com.gujeducation.gujaratedu.Helper.Language.set(SplashScreen.this, "tl");
        }
        if (mFunction.getPrefMediumId() == 7) {
            com.gujeducation.gujaratedu.Helper.Language.set(SplashScreen.this, "ur");
        }


        try {
            Log.e("SDK_INT_VERSION", "--" + Build.VERSION.SDK_INT);
            Dexter.withContext(this)
                    .withPermissions(
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_PHONE_STATE)
                    .withListener(new MultiplePermissionsListener() {
                        @Override
                        public void onPermissionsChecked(MultiplePermissionsReport report) {
                            // check if all permissions are granted
                            if (report.areAllPermissionsGranted()) {
                                // do you work now
                                countDownTimerStart();
                            }

                            // check for permanent denial of any permission
                            if (report.isAnyPermissionPermanentlyDenied()) {
                                // permission is denied permenantly, navigate user to app settings
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                        Uri.parse("package:" + getPackageName()));
                                intent.addCategory(Intent.CATEGORY_DEFAULT);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                            token.continuePermissionRequest();
                        }
                    })
                    .onSameThread()
                    .check();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void countDownTimerStart() {
        //Log.e("USER_ID_splsh1", "Uid-->" + mFunction.getPrefUserId());


        final ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setMax(100);


        new CountDownTimer(3000, 1000) {

            int i = 1;

            public void onTick(long millisUntilFinished) {
                int progress = ((i * 1000) * 100) / 3000;

                progressBar.setProgress(progress);
                progressBar.setSecondaryProgress(progress + (i * 10));
                i++;
            }

            public void onFinish() {
                if (Functions.knowInternetOn(SplashScreen.this)) {
                    if (Functions.knowInternetOn(SplashScreen.this)) {
                        try {
                            MarketVersionChecker versionChecker = new MarketVersionChecker();
                            //playstore_version = "1.2";
                            playstore_version = versionChecker.execute().get();
                            device_version = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
                            Log.e("VersionInfo", "Playstore Version-" + playstore_version +
                                    "\n Device Version-" + device_version);
                        } catch (PackageManager.NameNotFoundException e) {
                            e.printStackTrace();
                            Log.e("ExceptionErr", "-" + e.getMessage());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                progressBar.setProgress(100);
                progressBar.setSecondaryProgress(100);


                if (mFunction.getPrefLogin()) {
                    /*Log.e("SplashSc", "MediumId-->" + mFunction.getPrefMediumId() +
                            " UserId-->" + mFunction.getPrefUserId());*/
                    com.gujeducation.gujaratedu.Helper.Language.set(SplashScreen.this, mFunction.getPrefLanguageCode());
                    intent = new Intent(SplashScreen.this, HomeScreen.class);
                    intent.putExtra("flagPopup", 1);
                    startActivity(intent);
                    finish();
                } else if (mFunction.getPrefMediumId() != 0 && mFunction.getPrefLanguageCode() != null) {
                    com.gujeducation.gujaratedu.Helper.Language.set(SplashScreen.this, mFunction.getPrefLanguageCode());
                    intent = new Intent(SplashScreen.this, SignInScreen.class);
                    startActivity(intent);
                    finish();
                } else {
                    intent = new Intent(SplashScreen.this, LanguageScreen.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
            }
        }.start();
    }

}