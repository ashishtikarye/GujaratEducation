package com.gujeducation.gujaratedu.Adapter;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gujeducation.R;
import com.gujeducation.gujaratedu.Activity.DonationScreen;
import com.gujeducation.gujaratedu.Helper.Functions;
import com.gujeducation.gujaratedu.Model.Donation;

import java.util.ArrayList;

public class DonationAdapter extends RecyclerView.Adapter<DonationAdapter.MyViewHolder> {

    private final AppCompatActivity activity;
    public boolean error = false;
    ArrayList<Donation> listDonation = new ArrayList();
    Functions mFunction;
    private int mNumColumns = 0;

    public DonationAdapter(AppCompatActivity activity, ArrayList<Donation> listDonation) {
        this.activity = activity;
        this.listDonation = listDonation;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup holder, int i) {
        View itemView = LayoutInflater.from(holder.getContext()).inflate(R.layout.row_donation, holder, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final MyViewHolder menuItemHolder = holder;
        mFunction = new Functions(activity);
        final Donation donationList = listDonation.get(position);

        try {
            menuItemHolder.mTvDonationName.setText(donationList.getDonationName());
            menuItemHolder.mTvMadeBy.setText(donationList.getMadeBy());
            // menuItemHolder.mTvPaymentLink.setText("Payment : " + donationList.getPaymentLink());

            Glide.with(activity)
                    .load(donationList.getImage())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.IMMEDIATE).dontAnimate()
                    .into(menuItemHolder.mIvImage);
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.btnDonateUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(donationList.getPaymentLink()));
                activity.startActivity(browserIntent);
                try {
                    ((DonationScreen) activity).showInterstitialAd();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return listDonation.size();
    }

    public int getNumColumns() {
        return this.mNumColumns;
    }

    public void setNumColumns(int numColumns) {
        this.mNumColumns = numColumns;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final AppCompatTextView mTvDonationName;
        private final AppCompatTextView mTvMadeBy;
        private final AppCompatImageView mIvImage;
        private final AppCompatButton btnDonateUs;

        public MyViewHolder(View itemView) {
            super(itemView);
            mTvDonationName = itemView.findViewById(R.id.tv_title);
            mTvMadeBy = itemView.findViewById(R.id.tv_subtitle);
            mIvImage = itemView.findViewById(R.id.iv_title_img);
            btnDonateUs = itemView.findViewById(R.id.btndonateus);
        }
    }
}
