package com.gujeducation.gujaratedu.Adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.LoadAdError;
import com.gujeducation.R;
import com.gujeducation.gujaratedu.Activity.DaysSpecialDescription;
import com.gujeducation.gujaratedu.Activity.DaysSpecialScreen;
import com.gujeducation.gujaratedu.Activity.NewsCircularScreen;
import com.gujeducation.gujaratedu.Activity.PdfScreen;
import com.gujeducation.gujaratedu.Helper.Functions;
import com.gujeducation.gujaratedu.Helper.ProgressLoadingView;
import com.gujeducation.gujaratedu.Model.DaySpecial;

import java.util.ArrayList;

public class DaysSpecialListAdapter extends RecyclerView.Adapter<DaysSpecialListAdapter.MyViewHolder> {

    private final AppCompatActivity activity;
    public boolean error = false;
    ArrayList<DaySpecial> listDaysSpecial = new ArrayList();
    Functions mFunction;
    Intent intent;
    private int mNumColumns = 0;
    ProgressLoadingView mView = null;

    public DaysSpecialListAdapter(AppCompatActivity activity, ArrayList<DaySpecial> listDaysSpecial) {
        this.activity = activity;
        this.listDaysSpecial = listDaysSpecial;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup holder, int i) {
        View itemView = LayoutInflater.from(holder.getContext()).inflate(R.layout.row_days_special, holder, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final MyViewHolder menuItemHolder = holder;
        mFunction = new Functions(activity);
        final DaySpecial daysList = listDaysSpecial.get(position);


        try {
            menuItemHolder.tvTitle.setText(daysList.getTitle());
            menuItemHolder.tvSubTitle.setText(daysList.getSubtitle());
            Glide.with(activity)
                    .load(daysList.getImage())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.IMMEDIATE).dontAnimate()
                    .into(menuItemHolder.mIvDaySpecial);
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.btnSee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ((DaysSpecialScreen) activity).loadInterstitialAd();

                mView = new ProgressLoadingView();
                mView.show(activity.getSupportFragmentManager(), "load");


                ((DaysSpecialScreen) activity).interstitialAd.setAdListener(new AdListener() {
                    @Override
                    public void onAdLoaded() {

                        // Showing a simple Toast message to user when an ad is loaded
                        //Toast.makeText(activity, "Interstitial Ad is Loaded", Toast.LENGTH_LONG).show();

                        ((DaysSpecialScreen) activity).showInterstitialAd();

                    }

                    @Override
                    public void onAdFailedToLoad(LoadAdError adError) {
                        Log.e("loadFail", "error-" + adError);
                        if (mView != null)
                            mView.dismiss();
                        // Showing a simple Toast message to user when and ad is failed to load


                        intent = new Intent(activity, DaysSpecialDescription.class);
                        intent.putExtra("title", daysList.getTitle());
                        intent.putExtra("subtitle", daysList.getSubtitle());
                        intent.putExtra("description", daysList.getDescription());
                        intent.putExtra("image", daysList.getImage());
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
                        //Toast.makeText(activity, "Interstitial Ad Clicked", Toast.LENGTH_LONG).show();
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
                        //Toast.makeText(activity, "Interstitial Ad is Closed", Toast.LENGTH_LONG).show();
                        if (mView != null)
                            mView.dismiss();
                        intent = new Intent(activity, DaysSpecialDescription.class);
                        intent.putExtra("title", daysList.getTitle());
                        intent.putExtra("subtitle", daysList.getSubtitle());
                        intent.putExtra("description", daysList.getDescription());
                        intent.putExtra("image", daysList.getImage());
                        activity.startActivity(intent);

                        //Toast.makeText(activity, ""+newsList.getNewsCircularId(), Toast.LENGTH_SHORT).show();
                    }
                });















            }
        });

        holder.btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "*" + daysList.getTitle() + "*" + " - " + daysList.getSubtitle() + "\n\n" + daysList.getDescription());
                sendIntent.setType("text/plain");
                activity.startActivity(sendIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listDaysSpecial.size();
    }

    public int getNumColumns() {
        return this.mNumColumns;
    }

    public void setNumColumns(int numColumns) {
        this.mNumColumns = numColumns;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final AppCompatTextView tvTitle;
        private final AppCompatTextView tvSubTitle;
        private final AppCompatButton btnSee;
        private final AppCompatButton btnShare;
        private final AppCompatImageView mIvDaySpecial;
        private AppCompatTextView tvDate;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvSubTitle = itemView.findViewById(R.id.tv_subtitle);
            mIvDaySpecial = itemView.findViewById(R.id.iv_title_img);
            //tvDate = (AppCompatTextView) itemView.findViewById(R.id.tv_date);
            btnSee = itemView.findViewById(R.id.btnsee);
            btnShare = itemView.findViewById(R.id.btnshare);

        }
    }
}


