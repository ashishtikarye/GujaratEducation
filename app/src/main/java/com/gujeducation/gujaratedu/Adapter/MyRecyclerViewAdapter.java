package com.gujeducation.gujaratedu.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdView;
import com.gujeducation.R;
import com.gujeducation.gujaratedu.Activity.NewsCircularScreen;
import com.gujeducation.gujaratedu.Helper.Functions;
import com.gujeducation.gujaratedu.Model.NewsCircular;

import java.util.ArrayList;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM_TYPE_COUNTRY = 0;
    private static final int ITEM_TYPE_BANNER_AD = 1;
    public boolean error = false;
    //private final AppCompatActivity activity;
    ArrayList<NewsCircular> listNewsCircular = new ArrayList();
    Functions mFunction;
    Intent intent;
    private final int mNumColumns = 0;
    private final ArrayList<Object> mList;
    private final MyRecyclerViewItemClickListener mItemClickListener;


    public MyRecyclerViewAdapter(ArrayList<Object> listItems, MyRecyclerViewItemClickListener itemClickListener) {
        this.mList = listItems;
        this.mItemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case ITEM_TYPE_BANNER_AD:
                //Inflate ad banner container
                View bannerLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.banner_ad_row, parent, false);

                //Create View Holder
                MyAdViewHolder myAdViewHolder = new MyAdViewHolder(bannerLayoutView);

                return myAdViewHolder;
            case ITEM_TYPE_COUNTRY:
            default:

                //Inflate RecyclerView row
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_news_circular, parent, false);

                //Create View Holder
                final MyCountryViewHolder myCountryViewHolder = new MyCountryViewHolder(view);

                //Item Clicks
                myCountryViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mItemClickListener.onItemClicked((NewsCircular) mList.get(myCountryViewHolder.getLayoutPosition()));
                    }
                });
                return myCountryViewHolder;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);

        switch (viewType) {
            case ITEM_TYPE_BANNER_AD:
                if (mList.get(position) instanceof AdView) {
                    MyAdViewHolder bannerHolder = (MyAdViewHolder) holder;
                    AdView adView = (AdView) mList.get(position);
                    ViewGroup adCardView = (ViewGroup) bannerHolder.itemView;
                    // The AdViewHolder recycled by the RecyclerView may be a different
                    // instance than the one used previously for this position. Clear the
                    // AdViewHolder of any subviews in case it has a different
                    // AdView associated with it, and make sure the AdView for this position doesn't
                    // already have a parent of a different recycled AdViewHolder.
                    if (adCardView.getChildCount() > 0) {
                        adCardView.removeAllViews();
                    }
                    if (adView.getParent() != null) {
                        ((ViewGroup) adView.getParent()).removeView(adView);
                    }

                    // Add the banner ad to the ad view.
                    adCardView.addView(adView);
                }
                break;

            case ITEM_TYPE_COUNTRY:
            default:
                if (mList.get(position) instanceof NewsCircular) {
                    MyCountryViewHolder myCountryViewHolder = (MyCountryViewHolder) holder;
                    NewsCircular country = (NewsCircular) mList.get(position);

                    //Set Country Name
                    myCountryViewHolder.textViewName.setText(country.getTitle());

                    //Set Capital
                    myCountryViewHolder.textViewCapital.setText(country.getDate());


                }
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 || mList.get(position) instanceof NewsCircular) {
            return ITEM_TYPE_COUNTRY;
        } else {
            return (position % NewsCircularScreen.ITEMS_PER_AD == 0) ? ITEM_TYPE_BANNER_AD : ITEM_TYPE_COUNTRY;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    //Country Item Click Listener
    public interface MyRecyclerViewItemClickListener {
        void onItemClicked(NewsCircular country);
    }

    //Country View Holder
    class MyCountryViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewName;
        private final TextView textViewCapital;

        MyCountryViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.tv_title);
            textViewCapital = itemView.findViewById(R.id.tv_date);
        }
    }

    //Banner Ad View Holder
    class MyAdViewHolder extends RecyclerView.ViewHolder {
        MyAdViewHolder(View itemView) {
            super(itemView);
        }
    }

}
