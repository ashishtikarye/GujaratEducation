package com.gujeducation.gujaratedu.Adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gujeducation.R;
import com.gujeducation.gujaratedu.Activity.SignInScreen;
import com.gujeducation.gujaratedu.Activity.SignUpScreen;
import com.gujeducation.gujaratedu.Activity.SubLinkScreen;
import com.gujeducation.gujaratedu.Helper.Functions;
import com.gujeducation.gujaratedu.Model.ImportantLinks;
import com.gujeducation.gujaratedu.Model.SubLink;

import java.util.ArrayList;

public class ImportantLinksAdapter extends RecyclerView.Adapter<ImportantLinksAdapter.MyViewHolder> {

    private final AppCompatActivity activity;
    public boolean error = false;
    ArrayList<ImportantLinks> listImpLinks = new ArrayList();
    Functions mFunction;
    private int mNumColumns = 0;

    public ImportantLinksAdapter(AppCompatActivity activity, ArrayList<ImportantLinks> listImpLinks) {
        this.activity = activity;
        this.listImpLinks = listImpLinks;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup holder, int i) {
        View itemView = LayoutInflater.from(holder.getContext()).inflate(R.layout.row_imp_links, holder, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final MyViewHolder menuItemHolder = (MyViewHolder) holder;
        //mPreference = activity.getSharedPreferences(activity.getResources().getString(R.string.app_name), MODE_PRIVATE);
        mFunction = new Functions(activity);
        final ImportantLinks impLinksList = listImpLinks.get(position);


        try {
            menuItemHolder.tvLinkName.setText(impLinksList.getImpLinksName());
            menuItemHolder.tvLinkBy.setText("Made By : " + impLinksList.getImpLinksBy());
            menuItemHolder.tvLinkDetails.setText("Credit & Licence : " + impLinksList.getImpLinksCredits());


            Glide.with(activity)
                    .load(impLinksList.getImpLinksImage())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.IMMEDIATE).dontAnimate()
                    .into(menuItemHolder.mIvImpLinks);

            menuItemHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*Log.e("ImpLinksAdptr","impLinkId->"+impLinksList.getImpLinksId()+
                            " impLinkName->"+impLinksList.getImpLinksName());*/

                    Intent i = new Intent(activity, SubLinkScreen.class);
                    i.putExtra("impLinkId", impLinksList.getImpLinksId());
                    i.putExtra("impLinkName", impLinksList.getImpLinksName());
                    activity.startActivity(i);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return listImpLinks.size();
    }

    public int getNumColumns() {
        return this.mNumColumns;
    }

    public void setNumColumns(int numColumns) {
        this.mNumColumns = numColumns;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final AppCompatTextView tvLinkName;
        private final AppCompatTextView tvLinkBy;
        private final AppCompatTextView tvLinkDetails;
        private final AppCompatImageView mIvImpLinks;
        private final AppCompatImageView btnViewAero;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvLinkName = (AppCompatTextView) itemView.findViewById(R.id.tv_implinks_name);
            tvLinkBy = (AppCompatTextView) itemView.findViewById(R.id.tv_implinks_by);
            tvLinkDetails = (AppCompatTextView) itemView.findViewById(R.id.tv_implinks_details);
            btnViewAero = (AppCompatImageView) itemView.findViewById(R.id.btnview_aero);
            mIvImpLinks = (AppCompatImageView) itemView.findViewById(R.id.iv_implinks_img);

        }
    }
}
