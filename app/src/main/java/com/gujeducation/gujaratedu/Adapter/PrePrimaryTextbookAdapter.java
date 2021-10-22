package com.gujeducation.gujaratedu.Adapter;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.gujeducation.R;
import com.gujeducation.gujaratedu.Helper.Functions;
import com.gujeducation.gujaratedu.Model.PrePrimaryTextbook;

import java.util.ArrayList;

public class PrePrimaryTextbookAdapter extends RecyclerView.Adapter<PrePrimaryTextbookAdapter.MyViewHolder> {

    private final AppCompatActivity activity;
    ArrayList<PrePrimaryTextbook> listPrePrimaryTextbook = new ArrayList();
    public boolean error = false;
    //Functions mFunction;
    private int mNumColumns = 0;
    Intent intent;
    MediaPlayer mediaPlayer;

    public PrePrimaryTextbookAdapter(AppCompatActivity activity, ArrayList<PrePrimaryTextbook> listPrePrimaryTextbook){
        this.activity = activity;
        this.listPrePrimaryTextbook = listPrePrimaryTextbook;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup holder, int i) {
        View itemView = LayoutInflater.from(holder.getContext()).inflate(R.layout.row_preprimary, holder, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final MyViewHolder menuItemHolder = (MyViewHolder) holder;
        PrePrimaryTextbook prePrimaryTextbookList = listPrePrimaryTextbook.get(position);
        menuItemHolder.btnMedium.setText(prePrimaryTextbookList.getMedium_title());
        menuItemHolder.btnCommon.setText(prePrimaryTextbookList.getCommon_title());
        Glide.with(activity)
                .load(prePrimaryTextbookList.getImage())
                .centerCrop()
                .into(menuItemHolder.imgView);

        menuItemHolder.btnMedium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer = MediaPlayer.create(activity, Uri.parse(prePrimaryTextbookList.getMedium_audio()));
                mediaPlayer.start();
            }
        });

        menuItemHolder.btnCommon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer = MediaPlayer.create(activity, Uri.parse(prePrimaryTextbookList.getCommon_audio()));
                mediaPlayer.start();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listPrePrimaryTextbook.size();
    }
    public int getNumColumns() {
        return this.mNumColumns;
    }
    public void setNumColumns(int numColumns) {
        this.mNumColumns = numColumns;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private AppCompatButton btnMedium,btnCommon;
        private AppCompatImageView imgView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
           // Functions mFunctions = new Functions(activity);
            btnMedium = itemView.findViewById(R.id.tv_english);
            btnCommon = itemView.findViewById(R.id.tv_hindi);
            imgView = itemView.findViewById(R.id.iv_img);
            mediaPlayer = new MediaPlayer();
        }
    }
}
