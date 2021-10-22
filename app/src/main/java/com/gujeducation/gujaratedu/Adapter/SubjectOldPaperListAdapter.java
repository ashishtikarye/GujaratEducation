package com.gujeducation.gujaratedu.Adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.gujeducation.R;
import com.gujeducation.gujaratedu.Activity.PdfScreen;
import com.gujeducation.gujaratedu.Model.OldPaper;
import com.gujeducation.gujaratedu.Model.OldPaperSubject;

import java.util.ArrayList;

public class SubjectOldPaperListAdapter extends RecyclerView.Adapter<SubjectOldPaperListAdapter.MyViewHolder> {

    private final AppCompatActivity activity;
    public boolean error = false;
    ArrayList<OldPaperSubject> listOldPapersSubject = new ArrayList();
    private int mNumColumns = 0;


    public SubjectOldPaperListAdapter(AppCompatActivity activity, ArrayList<OldPaperSubject> listOldPapersSubject) {
        this.activity = activity;
        this.listOldPapersSubject = listOldPapersSubject;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup holder, int i) {
        View itemView = LayoutInflater.from(holder.getContext()).inflate(R.layout.row_papers, holder, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final MyViewHolder menuItemHolder = holder;
        final OldPaperSubject oldpaperList = listOldPapersSubject.get(position);
        try {

            menuItemHolder.mTvOldPaperName.setText(oldpaperList.getYear());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, PdfScreen.class);
                    intent.putExtra("Name", oldpaperList.getOldPaperPDFName());
                    intent.putExtra("PDF", oldpaperList.getOldPaperPDF());
                    activity.startActivity(intent);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    @Override
    public int getItemCount() {
        return listOldPapersSubject.size();
    }

    public int getNumColumns() {
        return this.mNumColumns;
    }

    public void setNumColumns(int numColumns) {
        this.mNumColumns = numColumns;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView mTvOldPaperName;

        public MyViewHolder(View itemView) {
            super(itemView);
            mTvOldPaperName = itemView.findViewById(R.id.tv_papername);

        }
    }
}


