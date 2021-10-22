package com.gujeducation.gujaratedu.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.gujeducation.R;
import com.gujeducation.gujaratedu.Activity.ChapterScreen;
import com.gujeducation.gujaratedu.Helper.Functions;
import com.gujeducation.gujaratedu.Model.Subject;

import java.util.ArrayList;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.MyViewHolder> {

    private final FragmentActivity activity;
    ArrayList<Subject> listSubject = new ArrayList();
    public boolean error = false;
    Functions mFunction;
    private int mNumColumns = 0;
    Intent intent;

    public SubjectAdapter(FragmentActivity activity, ArrayList<Subject> listSubject) {
        this.activity = activity;
        this.listSubject = listSubject;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup holder, int i) {
        View itemView = LayoutInflater.from(holder.getContext()).inflate(R.layout.row_subject, holder, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final MyViewHolder menuItemHolder = (MyViewHolder) holder;
        //mPreference = activity.getSharedPreferences(activity.getResources().getString(R.string.app_name), MODE_PRIVATE);
        mFunction = new Functions(activity);
        final Subject subjectsList = listSubject.get(position);

        try{
            menuItemHolder.tvSubject.setText(subjectsList.getSubjectName());
            menuItemHolder.cardSubject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mFunction.setSemesterId(subjectsList.getSemesterId());
                    mFunction.setSubjectId(subjectsList.getSubjectId());
                    mFunction.setSubjectName(subjectsList.getSubjectName());
                    intent = new Intent(activity, ChapterScreen.class);
                    intent.putExtra("subject",subjectsList.getSubjectName());
                    intent.putExtra("madeBy",subjectsList.getMadeBy());
                    intent.putExtra("creditsAndLicence",subjectsList.getCreditsAndLicence());
                    intent.putExtra("GcrtImage",subjectsList.getGcrtImage());
                    activity.startActivity(intent);
                }
            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return listSubject.size();
    }
    public int getNumColumns() {
        return this.mNumColumns;
    }
    public void setNumColumns(int numColumns) {
        this.mNumColumns = numColumns;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private CardView cardSubject;
        private AppCompatTextView tvSubject;

        public MyViewHolder(View itemView) {
            super(itemView);
            cardSubject = (CardView) itemView.findViewById(R.id.card_subject);
            tvSubject = (AppCompatTextView) itemView.findViewById(R.id.tv_subject);

        }
    }
}
