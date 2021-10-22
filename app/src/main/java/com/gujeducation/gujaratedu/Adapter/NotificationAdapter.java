package com.gujeducation.gujaratedu.Adapter;

import android.content.Intent;
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
import com.gujeducation.gujaratedu.Activity.CalenderEditionScreen;
import com.gujeducation.gujaratedu.Helper.Functions;
import com.gujeducation.gujaratedu.Model.Calender;
import com.gujeducation.gujaratedu.Model.Notification;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {

    private final AppCompatActivity activity;
    public boolean error = false;
    ArrayList<Notification> listNotification = new ArrayList();
    Functions mFunction;
    private int mNumColumns = 0;

    public NotificationAdapter(AppCompatActivity activity, ArrayList<Notification> listNotification) {
        this.activity = activity;
        this.listNotification = listNotification;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup holder, int i) {
        View itemView = LayoutInflater.from(holder.getContext()).inflate(R.layout.row_notification, holder, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final MyViewHolder menuItemHolder = holder;
        mFunction = new Functions(activity);
        final Notification notificationList = listNotification.get(position);

        try {
            menuItemHolder.mTvNotificationTitle.setText(notificationList.getTitle());
            menuItemHolder.mTvNotificationBody.setText(notificationList.getBody());

            /*Glide.with(activity)
                    .load(calenderList.getCoverImage())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.IMMEDIATE).dontAnimate()
                    .into(menuItemHolder.mIvCalenderImage);*/
        } catch (Exception e) {
            e.printStackTrace();
        }




    }

    @Override
    public int getItemCount() {
        return listNotification.size();
    }

    public int getNumColumns() {
        return this.mNumColumns;
    }

    public void setNumColumns(int numColumns) {
        this.mNumColumns = numColumns;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final AppCompatTextView mTvNotificationTitle;
        private final AppCompatTextView mTvNotificationBody;

        public MyViewHolder(View itemView) {
            super(itemView);
            mTvNotificationTitle = itemView.findViewById(R.id.tv_notify_title);
            mTvNotificationBody = itemView.findViewById(R.id.tv_notify_body);
        }
    }
}
