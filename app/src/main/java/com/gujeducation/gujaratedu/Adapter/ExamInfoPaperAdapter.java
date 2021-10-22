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
import com.gujeducation.gujaratedu.Activity.OldPaperListScreen;
import com.gujeducation.gujaratedu.Activity.PdfScreen;
import com.gujeducation.gujaratedu.Helper.Connection;
import com.gujeducation.gujaratedu.Helper.Functions;
import com.gujeducation.gujaratedu.Interface.OnResult;
import com.gujeducation.gujaratedu.Model.ExamChapterOptionList;
import com.gujeducation.gujaratedu.Model.ExamInfoPaper;
import com.gujeducation.gujaratedu.ServerAPIs.APIs;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ExamInfoPaperAdapter extends RecyclerView.Adapter<ExamInfoPaperAdapter.MyViewHolder>{
    private final AppCompatActivity activity;
    ArrayList<ExamInfoPaper> listExamInfoPaper = new ArrayList();
    Functions mFunction;
    Intent intent;
    private int mNumColumns = 0;
    private String standard;

    public ExamInfoPaperAdapter(AppCompatActivity activity, ArrayList<ExamInfoPaper> listExamInfoPaper) {
        this.activity = activity;
        this.listExamInfoPaper = listExamInfoPaper;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup holder, int i) {
        View itemView = LayoutInflater.from(holder.getContext()).inflate(R.layout.row_examinformation, holder,
                false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final MyViewHolder menuItemHolder = holder;
        mFunction = new Functions(activity);
        final ExamInfoPaper examInfoPaperList = listExamInfoPaper.get(position);

        try {
            holder.tvTopic.setText(examInfoPaperList.getTopic());


            menuItemHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, PdfScreen.class);
                    intent.putExtra("Name", examInfoPaperList.getPdf_name());
                    intent.putExtra("PDF", examInfoPaperList.getPdf());
                    activity.startActivity(intent);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    @Override
    public int getItemCount() {
        return listExamInfoPaper.size();
    }

    public int getNumColumns() {
        return this.mNumColumns;
    }

    public void setNumColumns(int numColumns) {
        this.mNumColumns = numColumns;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final AppCompatTextView tvTopic;
        //private AppCompatImageView mIvBoardMemberPic;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvTopic = itemView.findViewById(R.id.tv_optionname);
        }
    }
}
