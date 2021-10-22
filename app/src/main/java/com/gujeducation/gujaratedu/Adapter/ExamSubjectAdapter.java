package com.gujeducation.gujaratedu.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.gujeducation.R;
import com.gujeducation.gujaratedu.Activity.SubjectListScreenCompExam;
import com.gujeducation.gujaratedu.Helper.Functions;
import com.gujeducation.gujaratedu.Model.ExamSubject;

import java.util.ArrayList;

public class ExamSubjectAdapter extends RecyclerView.Adapter<ExamSubjectAdapter.MyViewHolder> {

    private final FragmentActivity activity;
    public boolean error = false;
    ArrayList<ExamSubject> listExamSubject = new ArrayList();
    Functions mFunction;
    Intent intent;
    private int mNumColumns = 0;
    private String standard;

    public ExamSubjectAdapter(FragmentActivity activity, ArrayList<ExamSubject> listExamSubject) {
        this.activity = activity;
        this.listExamSubject = listExamSubject;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup holder, int i) {
        View itemView = LayoutInflater.from(holder.getContext()).inflate(R.layout.row_examsubject, holder, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final MyViewHolder menuItemHolder = (MyViewHolder) holder;
        //mPreference = activity.getSharedPreferences(activity.getResources().getString(R.string.app_name), MODE_PRIVATE);
        mFunction = new Functions(activity);
        final ExamSubject examSubjectList = listExamSubject.get(position);

        try {
            //Log.e("data",""+sectionList.getSection());
            menuItemHolder.tvExamSubject.setText(examSubjectList.getSubjectName());
            menuItemHolder.rlExamSubject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

              /*      Log.e("ExamSubAdptr",
                            "getExamId->"+examSubjectList.getExamId()+
                            "getPapersId->"+examSubjectList.getPapersId()+
                            " getSubjectId->"+examSubjectList.getSubjectId());
*/

                    Intent i = new Intent(activity, SubjectListScreenCompExam.class);
                    i.putExtra("subject_name", examSubjectList.getSubjectName());
                    i.putExtra("exam_id", examSubjectList.getExamId());
                    i.putExtra("papers_id", examSubjectList.getPapersId());
                    i.putExtra("subject_id", examSubjectList.getSubjectId());
                    activity.startActivity(i);
                    activity.finish();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return listExamSubject.size();
    }

    public int getNumColumns() {
        return this.mNumColumns;
    }

    public void setNumColumns(int numColumns) {
        this.mNumColumns = numColumns;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final RelativeLayout rlExamSubject;
        private final AppCompatTextView tvExamSubject;
        //private AppCompatImageView mIvBoardMemberPic;

        public MyViewHolder(View itemView) {
            super(itemView);
            rlExamSubject = (RelativeLayout) itemView.findViewById(R.id.rl_examsubjectid);
            tvExamSubject = (AppCompatTextView) itemView.findViewById(R.id.tv_examsubject);
        }
    }

}
