package com.gujeducation.gujaratedu.Activity;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.tasks.OnCompleteListener;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.android.play.core.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.gujeducation.BuildConfig;
import com.gujeducation.R;
import com.gujeducation.gujaratedu.Adapter.SliderAdapter;
import com.gujeducation.gujaratedu.Helper.Connection;
import com.gujeducation.gujaratedu.Helper.Functions;
import com.gujeducation.gujaratedu.Helper.WrapContentHeightViewPager;
import com.gujeducation.gujaratedu.Interface.OnResult;
import com.gujeducation.gujaratedu.Model.Slider;
import com.gujeducation.gujaratedu.ServerAPIs.APIs;
import com.gujeducation.gujaratedu.Viewpagerindicator.CirclePageIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class HomeScreen extends AppCompatActivity implements View.OnClickListener, OnResult {
    private static final int RC_APP_UPDATE = 100;
    private static WrapContentHeightViewPager mPager;
    private static int NUM_PAGES = 0;
    private static int currentPage = 0;
    private final ArrayList<Slider> listArrSlider = new ArrayList<Slider>();
   // public InterstitialAd interstitialAd;
    ReviewManager mReviewManager;
    ReviewInfo mReviewInfo;
    LinearLayout mLlFacebook, mLlGoogleplus;
    Functions mFunction;
    String strFullName, MobileNo, strEmailId;
    CirclePageIndicator indicator;
    AppCompatTextView mTvUsername;
    LinearLayout mLlShare, mLlMyAccount, mLlNotification, mLlLogout, mLlFeedback, mLlCredits, mLlMinaniDuniya, mLlAboutUs, mLlTermsCondition, mLlMyGroup, mLlDonation;
    Intent intent;
    Dialog dialogPopup;
    AppCompatImageView mIvPopupImage, mIvClose;
    AdView mAdView;
    AppCompatActivity activity;
    int flag;
    String device_os, device_osversion, firebase_token, device_model, devicUUID;
    private AppUpdateManager mAppUpdateManager;
    private final InstallStateUpdatedListener installStateUpdatedListener = new InstallStateUpdatedListener() {
        @Override
        public void onStateUpdate(@NonNull InstallState state) {
            if (state.installStatus() == InstallStatus.DOWNLOADED) {
                showCompletedUpdate();
            }

        }
    };
    private Context mcontext;
    private DrawerLayout drawerLayout;
    private RelativeLayout leftRL, btnAvailableClass, mRlTopUsername;
    private AppCompatImageView btnlanguage_change;
    private CardView btnCalender, btnLiveConf, btnDaysSpecial, btnComptExams, btnImportantLinks, btnNewsCircular, btnMagazine, btnEducationCorner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);
        //  ManageScreenView(this);
        mFunction = new Functions(this);
        activity = this;
        mcontext = this;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        mAppUpdateManager = AppUpdateManagerFactory.create(this);

        mAppUpdateManager.getAppUpdateInfo().addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo result) {
                if (result.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE &&
                        result.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                    try {
                        mAppUpdateManager.startUpdateFlowForResult(result, AppUpdateType.IMMEDIATE, HomeScreen.this, RC_APP_UPDATE);
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

        mReviewManager = ReviewManagerFactory.create(HomeScreen.this);
        Task<ReviewInfo> request = mReviewManager.requestReviewFlow();
        request.addOnCompleteListener(new OnCompleteListener<ReviewInfo>() {
            @Override
            public void onComplete(@NonNull Task<ReviewInfo> task) {

                if (task.isSuccessful()) {
                    mReviewInfo = task.getResult();

                    Task<Void> flow = mReviewManager.launchReviewFlow(HomeScreen.this, mReviewInfo);

                    flow.addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void result) {
                        }
                    });
                } else {
                    Toast.makeText(activity, "error", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //mAppUpdateManager.registerListener(installStateUpdatedListener);


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
        Log.e("notificationH", /*"android_id-" + android_id +*/
                "\ndevicUUID-" + devicUUID +
                        " firebase_token-" + firebase_token + "\ndevice_model-" + device_model +
                        "\ndevice_osversion-" + device_osversion + "\ndevice_os-" + device_os);









        intent = getIntent();
        if (intent.hasExtra("flagPopup")) {
            flag = intent.getExtras().getInt("flagPopup");
        } else {
            flag = 0;
        }


        /*try {
            if (Functions.knowInternetOn(HomeScreen.this)) {
                if (SplashScreen.playstore_version == null)
                    Log.d("Version", "NULL do not check version");
                else
                    CheckVersion();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        /*interstitialAd = new InterstitialAd(HomeScreen.this);
        //interstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        interstitialAd.setAdUnitId("ca-app-pub-6923797063551368/3272831029");


        List<String> testDeviceIds = Arrays.asList("8A898BC8824C996E9320D350D4AF1F10");
        RequestConfiguration configuration =
                new RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build();
        MobileAds.setRequestConfiguration(configuration);
*/
      //  loadInterstitialAd();
        /*Log.e("HomeScreen", "UserId->" + mFunction.getPrefUserId() + " MediumId->" + mFunction.getPrefMediumId() +
                "\nUserName->" + mFunction.getPrefUserName() +
                " Role->" + mFunction.getPrefRole());*/


        FirebaseMessaging.getInstance().setAutoInitEnabled(true);
        leftRL = findViewById(R.id.whatYouWantInLeftDrawer);
        drawerLayout = findViewById(R.id.drawer_layout);
        mRlTopUsername = findViewById(R.id.leftmenu_profile);
        mTvUsername = findViewById(R.id.username);
        mLlLogout = findViewById(R.id.llLogout);
        mLlFeedback = findViewById(R.id.llFeedback);
        mLlCredits = findViewById(R.id.llCreditmember);
        mLlAboutUs = findViewById(R.id.ll_aboutus);
        mLlMinaniDuniya = findViewById(R.id.llMinamiduniya);
        mLlTermsCondition = findViewById(R.id.lltermscondition);
        mLlShare = findViewById(R.id.llShare);
        mLlMyAccount = findViewById(R.id.llmyaccount);
        mLlMyGroup = findViewById(R.id.llGroupEducation);
        mLlNotification = findViewById(R.id.llnotification);
        mLlMyGroup.setVisibility(View.GONE);
        mLlDonation = findViewById(R.id.llDonation);
        mTvUsername.setText("Hi " + mFunction.getPrefUserName());

        /*if (mFunction.getPrefRole().equalsIgnoreCase("T")) {
            mLlMyGroup.setVisibility(View.VISIBLE);
        }*/
        findViewById(R.id.ivmenu_drawer).setOnClickListener(this);
        findViewById(R.id.ivmenu_drawer).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                drawerLayout.openDrawer(leftRL);
                return false;
            }
        });

        mPager = findViewById(R.id.pager);
        indicator = findViewById(R.id.indicator);


        if (flag == 1) {
            if (Functions.knowInternetOn(this)) {
                APIs.getPopup(this, this, mFunction.getPrefMediumId(),device_model, device_os, devicUUID, device_osversion,
                        firebase_token);
            } else {
                Functions.showInternetAlert(this);
            }
        } else {
            Log.e("flag", "Don't show popup");
        }

        if (Functions.knowInternetOn(this)) {
            APIs.getSlider_image(HomeScreen.this, this, mFunction.getPrefMediumId());
        } else {
            Functions.showInternetAlert(this);
        }


        mLlMinaniDuniya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(leftRL);
                startActivity(new Intent(HomeScreen.this, MinamiDuniaScreen.class));
            }
        });
        mLlFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(leftRL);
                startActivity(new Intent(HomeScreen.this, FeedbackScreen.class));
            }
        });

        mLlCredits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(leftRL);
                startActivity(new Intent(HomeScreen.this, CreditorsScreen.class));
            }
        });
        mLlNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(leftRL);
                startActivity(new Intent(HomeScreen.this, MyNotification.class));
            }
        });
        mLlTermsCondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(leftRL);
                startActivity(new Intent(HomeScreen.this, TermsConditionScreen.class));
            }
        });

      /*  mLlEvalution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(leftRL);
                startActivity(new Intent(HomeScreen.this, EvalutionScreen.class));
            }
        });*/


        mLlShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(leftRL);
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "Hey check out learning app at: https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });

        mLlMyAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(leftRL);
                startActivity(new Intent(HomeScreen.this, MyAccountScreen.class));
            }
        });
        mLlDonation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(leftRL);
                startActivity(new Intent(HomeScreen.this, DonationScreen.class));
            }
        });

        mLlMyGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(leftRL);
                startActivity(new Intent(HomeScreen.this, GetAllContacts.class));
            }
        });

        mLlAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(leftRL);
                startActivity(new Intent(HomeScreen.this, AboutUsScreen.class));
            }
        });

        mLlLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(leftRL);
                mFunction.SetPrefUserName("");
                mFunction.SetPrefUserId(0);
                mFunction.SetPrefLogin(false);
                //mFunction.SetPrefMediumId(0);
                mFunction.SetPrefRole("");
                //mFunction.SetPrefLanguage("");
                intent = new Intent(HomeScreen.this, SignInScreen.class);
                startActivity(intent);
                finishAffinity();
                /*SignInScreen signin = new SignInScreen();
                signin.logoutGoogle();*/
                //android.os.Process.killProcess(android.os.Process.myPid());
            }
        });


       /* try {
            Intent intent = getIntent();
            if (intent.hasExtra("tfullname") && intent.hasExtra("temailid")) {
                strFullName = intent.getExtras().getString("tfullname");
                strEmailId = intent.getExtras().getString("temailid");
            } else {
                strFullName = "Guest";
                strEmailId = "guest@gmail.com";
            }
            Log.e("getIntent", "FullName->" + strFullName + " EmailId->" + strEmailId);
            mcontext = this;
            mFunction = new Functions(this);

            Functions.ToastUtility(HomeScreen.this, "WELCOME " + strFullName + "!");
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        //  Log.e("FlagLogCode_HM", "-->" + mFunction.getFlagLogCode());

        btnlanguage_change = findViewById(R.id.ivlanguage_change);
        btnAvailableClass = findViewById(R.id.btn_availableclass);
        btnLiveConf = findViewById(R.id.card_live_class);
        btnDaysSpecial = findViewById(R.id.card_days_special);
        btnComptExams = findViewById(R.id.card_compt_exams);
        btnImportantLinks = findViewById(R.id.card_important_links);
        btnNewsCircular = findViewById(R.id.card_news_circular);
        btnMagazine = findViewById(R.id.card_magazines);
        btnEducationCorner = findViewById(R.id.card_eduucation_corner);
        btnCalender = findViewById(R.id.card_calender);

        btnlanguage_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(HomeScreen.this, LanguageScreen.class);
                startActivity(intent);
            }
        });

        btnAvailableClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(HomeScreen.this, AvailableClassScreen.class);
                startActivity(intent);
            }
        });

        btnLiveConf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(HomeScreen.this, "Coming soon", Toast.LENGTH_SHORT).show();
                   /* intent = new Intent(HomeScreen.this, MainActivity.class);
                    startActivity(intent);*/
            }
        });

        btnDaysSpecial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(HomeScreen.this, DaysSpecialScreen.class);
                startActivity(intent);
            }
        });

        btnComptExams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(HomeScreen.this, CompititiveExamScreen.class);
                startActivity(intent);
            }
        });

        btnImportantLinks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(HomeScreen.this, ImportantLinksScreen.class);
                startActivity(intent);
            }
        });

        btnNewsCircular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //loadInterstitialAd();
                intent = new Intent(HomeScreen.this, NewsCircularScreen.class);
                startActivity(intent);
            }
        });

        btnMagazine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(HomeScreen.this, MagazineScreen.class);
                startActivity(intent);
            }
        });
        btnEducationCorner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(HomeScreen.this, EducationCornerScreen.class);
                //intent = new Intent(HomeScreen.this, CreatePostScreen.class);
                startActivity(intent);
                //showInterstitialAd();
            }
        });

        btnCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(HomeScreen.this, CalenderScreen.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public void onClick(View v) {
    }


    private void initSlider() {
        //mPager.setPagingEnabled(false);
        indicator.setViewPager(mPager);
        final float density = getResources().getDisplayMetrics().density;
        indicator.setRadius(5 * density);
        // NUM_PAGES = IMAGES.length;
        NUM_PAGES = listArrSlider.size();
        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 500, 3000);
        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                currentPage = position;
            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int pos) {
            }
        });

    }

    @Override
    public void onResult(JSONObject jobjWhole) {
        if (jobjWhole != null) {
            JSONObject jObj = jobjWhole.optJSONObject(Connection.TAG_DATA);
            //Log.e("onResult", "jObj->" + jObj.toString());
            String strApiName = jObj.optString("api");
            int strStatus = jObj.optInt("success");
            String strMessage = jObj.optString("message");

            if (strApiName.equalsIgnoreCase("getSlider")) {
                if (strStatus == 1) {
                    JSONArray jArray_slider = jObj.optJSONArray("slider");
                    //Log.e("jArray_topSlider", "" + jObj.optJSONArray("slider").length());
                    try {
                        if (jArray_slider != null) {
                            for (int i = 0; i < jArray_slider.length(); i++) {
                                JSONObject objBMember = jArray_slider.getJSONObject(i);
                                listArrSlider.add(new Slider(
                                        objBMember.optInt("bannerId"),
                                        objBMember.optInt("mediumId"),
                                        objBMember.optString("bannerTitle"),
                                        objBMember.optString("bannerUrl"),
                                        objBMember.optString("bannerImg")
                                ));

                            }
                            if (listArrSlider.size() != 0) {
                                mPager.setAdapter(new SliderAdapter(HomeScreen.this, listArrSlider));
                                initSlider();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "API Error", Toast.LENGTH_SHORT).show();
                }
            }
            if (strApiName.equalsIgnoreCase("getPopup")) {
                if (strStatus == 1) {
                    showPopup(jObj.optString("image").trim(),
                            jObj.optString("link").trim());
                }
            }
        }
    }

    public void showPopup(final String image, final String link) {

        dialogPopup = new Dialog(
                this, R.style.popupTheme);
        LayoutInflater inflater = (LayoutInflater)
                this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
            Glide.with(this).load(image)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.HIGH).dontAnimate()
                    //.crossFade(500)
                    .into(mIvPopupImage);
        } catch (Exception e) {
            e.printStackTrace();
        }


        mIvPopupImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                startActivity(browserIntent);
            }
        });

        mIvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogPopup.dismiss();
            }
        });


    }

    @Override
    public void onBackPressed() {
        exitApp();
    }

  /*  public void showInterstitialAd() {
        if (interstitialAd.isLoaded()) {
            //showing the ad Interstitial Ad if it is loaded
            interstitialAd.show();

            // Showing a simple Toast message to user when an Interstitial ad is shown to the user
            Toast.makeText(HomeScreen.this, "Interstitial is loaded and showing ad  ", Toast.LENGTH_LONG).show();
        } else {
            //Load the Interstitial ad if it is not loaded
            loadInterstitialAd();

            // Showing a simple Toast message to user when an ad is not loaded
            Toast.makeText(HomeScreen.this, "Interstitial Ad is not Loaded ", Toast.LENGTH_LONG).show();
        }
    }

    public void loadInterstitialAd() {
        // Creating  a Ad Request
        AdRequest adRequest = new AdRequest.Builder().build();
        // load Ad with the Request
        interstitialAd.loadAd(adRequest);
        adRequest.isTestDevice(HomeScreen.this);

        // Showing a simple Toast message to user when an ad is Loading
        Toast.makeText(HomeScreen.this, "Interstitial Ad is loading ", Toast.LENGTH_SHORT).show();
    }
*/
    public void CheckVersion() {
        try {
            //String[] v1 = "2.1.9".split("\\.");
            //String[] v2 = "2.1.10".split("\\.");
            Log.e("CheckVersion", "Checking for version update status...");
            if (SplashScreen.playstore_version != null) {
                String[] playstoreVersion = SplashScreen.playstore_version.split("\\.");
                String[] deviceVersion = SplashScreen.device_version.split("\\.");

                Log.d("CHECK VERSION Fn", "playstore_version-" + SplashScreen.playstore_version +
                        " device_version-" + SplashScreen.device_version);


                if (playstoreVersion.length != deviceVersion.length)
                    return;

                for (int pos = 0; pos < playstoreVersion.length; pos++) {
                    // compare v1[pos] with v2[pos] as necessary
                    ////Log.e("Versionn", "playstoreVersion - " + Integer.parseInt(playstoreVersion[pos]) + " \n " +"deviceVersion - " + Integer.parseInt(deviceVersion[pos]));
                    if (Integer.parseInt(playstoreVersion[pos]) > Integer.parseInt(deviceVersion[pos])) {
                        //Log.e("SamitiApp", "playstoreVersion is greater - Please update");
                        //showUpdateAvailable("Latest updated version is available to download in play store. By Downloading the latest update you will get the latest features, improvements and bug fixes.");
                    } else if (Integer.parseInt(playstoreVersion[pos]) < Integer.parseInt(deviceVersion[pos])) {
                        //Log.e("SamitiApp", "No update found");
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showUpdateAvailable(String strMessage) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            //android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
            builder.setTitle(getString(R.string.update_available));
            builder.setCancelable(false);
            builder.setMessage(strMessage);
            builder.setPositiveButton("UPDATE NOW", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //new LoginSession(HomeScreenActivity.this).clearUserInfo();
                    //mFunction.SetPrefCountBankValue(0);
                    //Log.e("UPDATECLICK", "Session clear successfully!-" + mFunction.getPrefLanguageCode());
                    finishAffinity();
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    try {
                        intent.setData(Uri.parse("market://details?id=com.gujeducation"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    startActivity(intent);
                }
            });
            AlertDialog alertDialog = builder.show();
            Resources res = getResources();
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(this, R.color.blue));
            // alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(this, R.color.black));
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    private void exitApp() {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Do you want to close this application?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finishAffinity();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //  Action for 'NO' Button
                            dialog.cancel();
                        }
                    });

            AlertDialog alert = builder.create();
            //Setting the title manually
            alert.setTitle("Gujarat Education");
            alert.show();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    private void showCompletedUpdate() {
        Snackbar snackbar = Snackbar.make(findViewById(R.id.content), "New App is Ready!!", Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Install", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAppUpdateManager.completeUpdate();
            }
        });
        snackbar.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == RC_APP_UPDATE && resultCode != RESULT_OK) {
            Toast.makeText(activity, "Cancel", Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    protected void onStop() {
        super.onStop();
        /*if (mAppUpdateManager != null)
            mAppUpdateManager.unregisterListener(installStateUpdatedListener);*/
    }


    @Override
    protected void onResume() {
        super.onResume();
        mAppUpdateManager.getAppUpdateInfo().addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo result) {
                if (result.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS &&
                        result.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                    try {
                        mAppUpdateManager.startUpdateFlowForResult(result, AppUpdateType.IMMEDIATE, HomeScreen.this, RC_APP_UPDATE);
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }
}