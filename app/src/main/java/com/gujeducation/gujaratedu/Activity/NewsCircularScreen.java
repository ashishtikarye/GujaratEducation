package com.gujeducation.gujaratedu.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.gujeducation.R;
import com.gujeducation.gujaratedu.Adapter.NewsCircularAdapter;
import com.gujeducation.gujaratedu.Helper.Connection;
import com.gujeducation.gujaratedu.Helper.Functions;
import com.gujeducation.gujaratedu.Interface.OnResult;
import com.gujeducation.gujaratedu.Model.NewsCircular;
import com.gujeducation.gujaratedu.ServerAPIs.APIs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NewsCircularScreen extends AppCompatActivity implements OnResult {

    public static final int ITEMS_PER_AD = 3;
    private final ArrayList<NewsCircular> listArrNewsCircular = new ArrayList<NewsCircular>();
    private final ArrayList<Object> mListItems = new ArrayList<>();
    public InterstitialAd interstitialAd;
    RecyclerView recyclerViewNewsCircularList;
    LinearLayoutManager mLayoutManager;
    NewsCircularAdapter mNewsCircularListAdapter;
    Functions mFunctions;
    AdView mAdView;
    Intent intent;
    private AppCompatImageView btnBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_circular);
        mFunctions = new Functions(this);
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        btnBack = findViewById(R.id.ivback);

        recyclerViewNewsCircularList = (RecyclerView) findViewById(R.id.recyclerview_newscircular);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        recyclerViewNewsCircularList.setHasFixedSize(true);
        recyclerViewNewsCircularList.setLayoutManager(mLayoutManager);





        /*addCountriesData();
        addAdMobBannerAds();
        setUIRef();
        loadBannerAds();*/
        interstitialAd = new InterstitialAd(NewsCircularScreen.this);
        //interstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        interstitialAd.setAdUnitId("ca-app-pub-6923797063551368/3272831029");
        if (Functions.knowInternetOn(this)) {
            APIs.getNewsCircular(NewsCircularScreen.this, this, mFunctions.getPrefMediumId());
        } else {
            Functions.showInternetAlert(this);
        }
        //loadInterstitialAd();

        //List<String> testDeviceIds = Arrays.asList("FA6829B84BA0124B24A6C4019575F9D6");

        //List<String> testDeviceIds = Arrays.asList("8A898BC8824C996E9320D350D4AF1F10");
        List<String> testDeviceIds = new ArrayList<String>();
        testDeviceIds.add("8A898BC8824C996E9320D350D4AF1F10");
        testDeviceIds.add("FFB848305EE41D5DB1D6C522BFB75BEE");
        testDeviceIds.add("105122E1816DB58B97D2DF2E357E7A37");

        RequestConfiguration configuration =
                new RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build();
        MobileAds.setRequestConfiguration(configuration);



        /*listArrNewsCircular.add(new NewsCircular(1,"News 1","10/10/2020"));
        listArrNewsCircular.add(new NewsCircular(2,"News 2","20/10/2020"));
        if(listArrNewsCircular.size() != 0){
            mNewsCircularListAdapter = new NewsCircularAdapter(NewsCircularScreen.this,
                    listArrNewsCircular);
            recyclerViewNewsCircularList.setAdapter(mNewsCircularListAdapter);
            recyclerViewNewsCircularList.setVisibility(View.VISIBLE);
        }
        else
        {
            recyclerViewNewsCircularList.setVisibility(View.GONE);
        }*/


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(NewsCircularScreen.this, HomeScreen.class);
                startActivity(intent);
            }
        });


        // creating different AdListener for Interstitial Ad with some Override methods


    }

    /*private void setUIRef() {
        //Reference of RecyclerView
        RecyclerView mRecyclerView = findViewById(R.id.recyclerview_newscircular);

        //Linear Layout Manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(NewsCircularScreen.this, RecyclerView.VERTICAL, false);

        //Set Layout Manager to RecyclerView
        mRecyclerView.setLayoutManager(linearLayoutManager);

        //Create adapter
        MyRecyclerViewAdapter myRecyclerViewAdapter = new MyRecyclerViewAdapter(mListItems, new MyRecyclerViewAdapter.MyRecyclerViewItemClickListener() {
            //Handling clicks
            @Override
            public void onItemClicked(NewsCircular country) {
                Toast.makeText(NewsCircularScreen.this, country.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });

        //Set adapter to RecyclerView
        mRecyclerView.setAdapter(myRecyclerViewAdapter);
    }

    private void addAdMobBannerAds() {
        for (int i = ITEMS_PER_AD; i <= mListItems.size(); i += ITEMS_PER_AD) {
            final AdView adView = new AdView(NewsCircularScreen.this);
            adView.setAdSize(AdSize.LARGE_BANNER);
            adView.setAdUnitId(getResources().getString(R.string.banner_small));
            mListItems.add(i, adView);
        }

        loadBannerAds();
    }

    private void loadBannerAds() {
        //Load the first banner ad in the items list (subsequent ads will be loaded automatically in sequence).
        loadBannerAd(ITEMS_PER_AD);
    }

    private void addCountriesData() {
        mListItems.add(new NewsCircular(1, 1, "Indian", "Rupee", "25-01-2021"));
        mListItems.add(new NewsCircular(2, 1, "Canadian", "Dollar", "25-01-2021"));
        mListItems.add(new NewsCircular(3, 1, "Norwegian", "Krone", "25-01-2021"));
        mListItems.add(new NewsCircular(4, 1, "Malaysian", "Ringgit", "25-01-2021"));
        mListItems.add(new NewsCircular(5, 1, "Singapore", "Dollar", "25-01-2021"));
        mListItems.add(new NewsCircular(6, 1, "Washington", "Dollar", "25-01-2021"));
        mListItems.add(new NewsCircular(7, 1, "Brazilian", "Real", "25-01-2021"));
        mListItems.add(new NewsCircular(8, 1, "Australian", "Dollar", "25-01-2021"));
        mListItems.add(new NewsCircular(9, 1, "Russian", "Ruble", "25-01-2021"));
        mListItems.add(new NewsCircular(10, 1, "Japanese", "Yen", "25-01-2021"));
        //adding extra dummy data
        for (int i = 0; i < 80; i++) {
            mListItems.add(new NewsCircular(i, i, "dummy", "dummy", "12-02-2021"));
        }
    }

    private void loadBannerAd(final int index) {
        if (index >= mListItems.size()) {
            return;
        }

        Object item = mListItems.get(index);
        if (!(item instanceof AdView)) {
            throw new ClassCastException("Expected item at index " + index + " to be a banner ad" + " ad.");
        }

        final AdView adView = (AdView) item;

        // Set an AdListener on the AdView to wait for the previous banner ad
        // to finish loading before loading the next ad in the items list.
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                // The previous banner ad loaded successfully, call this method again to
                // load the next ad in the items list.
                loadBannerAd(index + ITEMS_PER_AD);
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // The previous banner ad failed to load. Call this method again to load
                // the next ad in the items list.
                Log.e("NewsCircularActivity", "The previous banner ad failed to load. Attempting to"
                        + " load the next banner ad in the items list.");
                loadBannerAd(index + ITEMS_PER_AD);
            }
        });

        // Load the banner ad.
        adView.loadAd(new AdRequest.Builder().build());
    }*/

    public void showInterstitialAd() {
        if (interstitialAd.isLoaded()) {
            //showing the ad Interstitial Ad if it is loaded
            interstitialAd.show();

            // Showing a simple Toast message to user when an Interstitial ad is shown to the user
            Toast.makeText(NewsCircularScreen.this, "Interstitial is loaded and showing ad  ", Toast.LENGTH_SHORT).show();
        } else {
            //Load the Interstitial ad if it is not loaded
            loadInterstitialAd();

            // Showing a simple Toast message to user when an ad is not loaded
            Toast.makeText(NewsCircularScreen.this, "Interstitial Ad is not Loaded ", Toast.LENGTH_SHORT).show();
        }
    }

    public void loadInterstitialAd() {
        // Creating  a Ad Request
        AdRequest adRequest = new AdRequest.Builder().build();
        // load Ad with the Request
        interstitialAd.loadAd(adRequest);
        adRequest.isTestDevice(NewsCircularScreen.this);

        // Showing a simple Toast message to user when an ad is Loading
        Toast.makeText(NewsCircularScreen.this, "Interstitial Ad is loading ", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onResult(JSONObject jobjWhole) {
        try {
            if (jobjWhole != null) {
                JSONObject jObj = jobjWhole.optJSONObject(Connection.TAG_DATA);
                int strStatus = jObj.optInt("success");
                String strMessage = jObj.optString("message");

                if (strStatus != 0) {
                    JSONArray jArrayTextSub = jObj.getJSONArray("newsCircular");
                    if (jArrayTextSub.length() > 0) {
                        for (int i = 0; i < jArrayTextSub.length(); i++) {
                            try {
                                JSONObject obj = jArrayTextSub.getJSONObject(i);
                                listArrNewsCircular.add(new NewsCircular(
                                        obj.optInt("newsCircularId"),
                                        obj.optInt("mediumId"),
                                        obj.optString("title"),
                                        obj.optString("image"),
                                        obj.optString("date")
                                ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        if (listArrNewsCircular.size() != 0) {
                            mNewsCircularListAdapter = new NewsCircularAdapter(NewsCircularScreen.this, listArrNewsCircular);
                            recyclerViewNewsCircularList.setAdapter(mNewsCircularListAdapter);
                        }
                    }
                } else {
                    Functions.ToastUtility(NewsCircularScreen.this, strMessage);
                    //recyclerViewLanguage.setVisibility(View.GONE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
