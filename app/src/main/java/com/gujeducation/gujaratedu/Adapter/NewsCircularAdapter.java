package com.gujeducation.gujaratedu.Adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.LoadAdError;
import com.gujeducation.BuildConfig;
import com.gujeducation.R;
import com.gujeducation.gujaratedu.Activity.NewsCircularScreen;
import com.gujeducation.gujaratedu.Activity.PdfScreen;
import com.gujeducation.gujaratedu.Helper.Functions;
import com.gujeducation.gujaratedu.Helper.ProgressLoadingView;
import com.gujeducation.gujaratedu.Model.NewsCircular;

import java.util.ArrayList;

public class NewsCircularAdapter extends RecyclerView.Adapter<NewsCircularAdapter.MyViewHolder> {

    private final AppCompatActivity activity;
    public boolean error = false;
    ArrayList<NewsCircular> listNewsCircular = new ArrayList();
    Functions mFunction;
    Intent intent;
    ProgressLoadingView mView = null;
    private int mNumColumns = 0;

    public NewsCircularAdapter(AppCompatActivity activity, ArrayList<NewsCircular> listNewsCircular) {
        this.activity = activity;
        this.listNewsCircular = listNewsCircular;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup holder, int viewType) {
        View itemView = LayoutInflater.from(holder.getContext()).inflate(R.layout.row_news_circular, holder, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final MyViewHolder menuItemHolder = (MyViewHolder) holder;
        //mPreference = activity.getSharedPreferences(activity.getResources().getString(R.string.app_name), MODE_PRIVATE);
        mFunction = new Functions(activity);
        final NewsCircular newsList = listNewsCircular.get(position);

        try {
            menuItemHolder.tvTitle.setText(newsList.getTitle());
            menuItemHolder.tvDate.setText(newsList.getDate());
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.btnSee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((NewsCircularScreen) activity).loadInterstitialAd();

                mView = new ProgressLoadingView();
                mView.show(activity.getSupportFragmentManager(), "load");


                ((NewsCircularScreen) activity).interstitialAd.setAdListener(new AdListener() {
                    @Override
                    public void onAdLoaded() {

                        // Showing a simple Toast message to user when an ad is loaded
                     //   Toast.makeText(activity, "Interstitial Ad is Loaded", Toast.LENGTH_LONG).show();

                        ((NewsCircularScreen) activity).showInterstitialAd();

                    }

                    @Override
                    public void onAdFailedToLoad(LoadAdError adError) {
                        Log.e("loadFail", "error-" + adError);
                        if (mView != null)
                            mView.dismiss();
                        // Showing a simple Toast message to user when and ad is failed to load
                      //  Toast.makeText(activity, "Interstitial Ad Failed to Load ", Toast.LENGTH_LONG).show();
                        intent = new Intent(activity, PdfScreen.class);
                        intent.putExtra("Name", newsList.getTitle());
                        intent.putExtra("PDF", newsList.getImage());
                        activity.startActivity(intent);
                    }

                    @Override
                    public void onAdOpened() {
                        // Showing a simple Toast message to user when an ad opens and overlay and covers the device screen
                        //Toast.makeText(activity, "Interstitial Ad Opened", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onAdClicked() {
                        // Showing a simple Toast message to user when a user clicked the ad
                      //  Toast.makeText(activity, "Interstitial Ad Clicked", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onAdLeftApplication() {
                        // Showing a simple Toast message to user when the user left the application
                        //Toast.makeText(activity, "Interstitial Ad Left the Application", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onAdClosed() {
                        //loading new interstitialAd when the ad is closed
                       // ((NewsCircularScreen) activity).loadInterstitialAd();
                        // Showing a simple Toast message to user when the user interacted with ad and got the other app and then return to the app again
                        //mView.show(activity.getSupportFragmentManager(), "load");
                       // Toast.makeText(activity, "Interstitial Ad is Closed", Toast.LENGTH_LONG).show();
                        if (mView != null)
                            mView.dismiss();
                        intent = new Intent(activity, PdfScreen.class);
                        intent.putExtra("Name", newsList.getTitle());
                        intent.putExtra("PDF", newsList.getImage());
                        activity.startActivity(intent);

                        //Toast.makeText(activity, ""+newsList.getNewsCircularId(), Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });

        holder.btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(activity, newsList.getTitle(), Toast.LENGTH_SHORT).show();

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "Hey check out learning app at: https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
                sendIntent.setType("text/plain");
                activity.startActivity(sendIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listNewsCircular.size();
    }

    public int getNumColumns() {
        return this.mNumColumns;
    }

    public void setNumColumns(int numColumns) {
        this.mNumColumns = numColumns;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final AppCompatTextView tvTitle;
        private final AppCompatTextView tvDate;
        private final AppCompatButton btnSee;
        private final AppCompatButton btnShare;
        //private AppCompatImageView mIvBoardMemberPic;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvTitle = (AppCompatTextView) itemView.findViewById(R.id.tv_title);
            tvDate = (AppCompatTextView) itemView.findViewById(R.id.tv_date);
            btnSee = (AppCompatButton) itemView.findViewById(R.id.btnsee);
            btnShare = (AppCompatButton) itemView.findViewById(R.id.btnshare);

        }
    }

}
