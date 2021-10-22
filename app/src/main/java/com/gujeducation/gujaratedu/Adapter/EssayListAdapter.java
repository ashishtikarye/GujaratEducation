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
import com.gujeducation.gujaratedu.Activity.EssayDescription;
import com.gujeducation.gujaratedu.Activity.PdfScreen;
import com.gujeducation.gujaratedu.Model.Essay;
import com.gujeducation.gujaratedu.Model.OldPaperSubject;

import java.util.ArrayList;

public class EssayListAdapter extends RecyclerView.Adapter<EssayListAdapter.MyViewHolder> {

    private final AppCompatActivity activity;
    public boolean error = false;
    ArrayList<Essay> listArrEssay = new ArrayList();
    private int mNumColumns = 0;


    public EssayListAdapter(AppCompatActivity activity, ArrayList<Essay> listArrEssay) {
        this.activity = activity;
        this.listArrEssay = listArrEssay;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup holder, int i) {
        View itemView = LayoutInflater.from(holder.getContext()).inflate(R.layout.row_papers, holder, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final MyViewHolder menuItemHolder = holder;
        final Essay essayList = listArrEssay.get(position);
        try {



            menuItemHolder.mTvOldPaperName.setText(essayList.getTitle());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, EssayDescription.class);
                    intent.putExtra("essayBody", essayList.getEssayBody());
                    intent.putExtra("essayTitle", essayList.getTitle());
                    activity.startActivity(intent);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    @Override
    public int getItemCount() {
        return listArrEssay.size();
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


