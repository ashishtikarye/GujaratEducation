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

import com.gujeducation.R;
import com.gujeducation.gujaratedu.Activity.PdfScreen;
import com.gujeducation.gujaratedu.Helper.Functions;
import com.gujeducation.gujaratedu.Model.SubLink;

import java.util.ArrayList;

public class SubLinkAdapter extends RecyclerView.Adapter<SubLinkAdapter.MyViewHolder> {

    private final AppCompatActivity activity;
    public boolean error = false;
    ArrayList<SubLink> listSubLinks = new ArrayList();
    Functions mFunction;
    private int mNumColumns = 0;

    public SubLinkAdapter(AppCompatActivity activity, ArrayList<SubLink> listSubLinks) {
        this.activity = activity;
        this.listSubLinks = listSubLinks;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup holder, int i) {
        View itemView = LayoutInflater.from(holder.getContext()).inflate(R.layout.row_sublink, holder, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final MyViewHolder menuItemHolder = (MyViewHolder) holder;
        //mPreference = activity.getSharedPreferences(activity.getResources().getString(R.string.app_name), MODE_PRIVATE);
        mFunction = new Functions(activity);
        final SubLink subLinksList = listSubLinks.get(position);


        try {
            //Log.e("position", "-->" + position);

            int pos = position + 1;
            //Log.e("pos", "-->" + pos);

            menuItemHolder.tvSubLinkName.setText(subLinksList.getSubLinkName());

            menuItemHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(activity, PdfScreen.class);
                    i.putExtra("Name", subLinksList.getSubLinkName());
                    i.putExtra("PDF", subLinksList.getSubLinkPDF());
                    activity.startActivity(i);

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return listSubLinks.size();
    }

    public int getNumColumns() {
        return this.mNumColumns;
    }

    public void setNumColumns(int numColumns) {
        this.mNumColumns = numColumns;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final AppCompatTextView tvSubLinkName;
        private final AppCompatImageView btnViewAero;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvSubLinkName = (AppCompatTextView) itemView.findViewById(R.id.tv_sublink_name);
            btnViewAero = (AppCompatImageView) itemView.findViewById(R.id.btnview_aero);
        }
    }
}
