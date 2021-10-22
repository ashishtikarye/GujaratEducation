package com.gujeducation.gujaratedu.Adapter;

import android.content.Intent;
import android.util.Log;
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
import com.gujeducation.BuildConfig;
import com.gujeducation.R;
import com.gujeducation.gujaratedu.Activity.PdfScreen;
import com.gujeducation.gujaratedu.Helper.Functions;
import com.gujeducation.gujaratedu.Model.Calender;
import com.gujeducation.gujaratedu.Model.CalenderEdition;

import java.util.ArrayList;

public class CalenderEditionAdapter extends RecyclerView.Adapter<CalenderEditionAdapter.MyViewHolder> {

    private final AppCompatActivity activity;
    public boolean error = false;
    ArrayList<CalenderEdition> listCalenderEdition = new ArrayList();
    Functions mFunction;
    private int mNumColumns = 0;

    public CalenderEditionAdapter(AppCompatActivity activity, ArrayList<CalenderEdition> listCalenderEdition) {
        this.activity = activity;
        this.listCalenderEdition = listCalenderEdition;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup holder, int i) {
        View itemView = LayoutInflater.from(holder.getContext()).inflate(R.layout.row_magazine, holder, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final MyViewHolder menuItemHolder = holder;
        mFunction = new Functions(activity);
        final CalenderEdition calenderEdList = listCalenderEdition.get(position);

        try {
            menuItemHolder.mTvCalenderName.setText(calenderEdList.getMonths());
            menuItemHolder.mTvMadeBy.setText("Made By : " + calenderEdList.getMadeBy());
            menuItemHolder.mTvCreditLicence.setText("Credit & Licence : " + calenderEdList.getCreditsAndLicence());
            //Log.e("cvrimg-",""+calenderEdList.getCoverImage());
            Glide.with(activity)
                    .load(calenderEdList.getCoverImage())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.IMMEDIATE).dontAnimate()
                    .into(menuItemHolder.mIvCalenderImage);

        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, PdfScreen.class);
                intent.putExtra("Name", calenderEdList.getPdf_name());
                intent.putExtra("PDF", calenderEdList.getPdf());
                activity.startActivity(intent);
            }
        });

        holder.btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        return listCalenderEdition.size();
    }

    public int getNumColumns() {
        return this.mNumColumns;
    }

    public void setNumColumns(int numColumns) {
        this.mNumColumns = numColumns;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final AppCompatTextView mTvCalenderName;
        private final AppCompatTextView mTvMadeBy;
        private final AppCompatTextView mTvCreditLicence;
        private final AppCompatImageView mIvCalenderImage;
        private final AppCompatButton btnView;
        private final AppCompatButton btnShare;
        private final AppCompatButton btnMonth;
        //private AppCompatImageView mIvBoardMemberPic;

        public MyViewHolder(View itemView) {
            super(itemView);
            mTvCalenderName = itemView.findViewById(R.id.tv_magazine_name);
            mTvMadeBy = itemView.findViewById(R.id.tv_magazine_made_by);
            mTvCreditLicence = itemView.findViewById(R.id.tv_creditlicence);
            mIvCalenderImage = itemView.findViewById(R.id.iv_magazine_img);
            btnMonth = itemView.findViewById(R.id.btnmonth);
            btnView = itemView.findViewById(R.id.btnsee);
            btnShare = itemView.findViewById(R.id.btnshare);
        }
    }
}
