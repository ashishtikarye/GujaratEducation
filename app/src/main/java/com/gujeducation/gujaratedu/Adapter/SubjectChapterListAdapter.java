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
import com.gujeducation.gujaratedu.Fragment.BottomSheetSubjectChapterFragment;
import com.gujeducation.gujaratedu.Helper.Connection;
import com.gujeducation.gujaratedu.Helper.Functions;
import com.gujeducation.gujaratedu.Interface.OnResult;
import com.gujeducation.gujaratedu.Model.ExamChapterOptionList;
import com.gujeducation.gujaratedu.Model.SubjectChapterCompExam;
import com.gujeducation.gujaratedu.ServerAPIs.APIs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SubjectChapterListAdapter extends RecyclerView.Adapter<SubjectChapterListAdapter.MyViewHolder> implements OnResult {

    public int iExamId, iSubjectId, iPapersId, iChapterId;
    private final AppCompatActivity activity;
    public boolean error = false;
    ArrayList<SubjectChapterCompExam> listSubjectChapterCompExam = new ArrayList();
    Functions mFunction;
    Intent intent;
    BottomSheetSubjectChapterFragment bottomSheetDialog;
    ArrayList<ExamChapterOptionList> listExamChapterOptionList;

    private int mNumColumns = 0;


    public SubjectChapterListAdapter(AppCompatActivity activity, ArrayList<SubjectChapterCompExam> listSubjectChapterCompExam) {
        this.activity = activity;
        this.listSubjectChapterCompExam = listSubjectChapterCompExam;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup holder, int i) {
        View itemView = LayoutInflater.from(holder.getContext()).inflate(R.layout.row_papers, holder, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final MyViewHolder menuItemHolder = (MyViewHolder) holder;
        mFunction = new Functions(activity);
        final SubjectChapterCompExam subchapterList = listSubjectChapterCompExam.get(position);


        try {


            iExamId = subchapterList.getExamId();
            iPapersId = subchapterList.getPapersId();
            iSubjectId = subchapterList.getSubjectId();



            mFunction.SetPrefPapersId(subchapterList.getPapersId());;
            mFunction.SetPrefSubjectId(subchapterList.getSubjectId());
            mFunction.SetPrefExamId(subchapterList.getExamId());




            int pos = position + 1;
            menuItemHolder.mTvPaperName.setText(subchapterList.getChapterNo() + ".  " + subchapterList.getChapterName());




        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*intent = new Intent(activity, DaysSpecialDescription.class);
                intent.putExtra("mediumId", paperList.getMediumId());
                intent.putExtra("examId", paperList.getExamId());
                activity.startActivity(intent);*/
                iChapterId = subchapterList.getChapterId();
                mFunction.SetPrefChapterId(subchapterList.getChapterId());
                Log.e("SubChapterListAdppp", "iExamId->" + iExamId +
                        " iChapterId->" + iChapterId +
                        " iSubjectId->" + iSubjectId +
                        " iPapersId->" + iPapersId);

                callApi(subchapterList.getChapterId());
                bottomSheetDialog = new BottomSheetSubjectChapterFragment(listExamChapterOptionList);


            }
        });


    }


    private void callApi(int chapterId) {
        if (Functions.knowInternetOn(activity)) {
            APIs.getExamChapterOptionList(activity, this, mFunction.getPrefMediumId(), chapterId);
        } else {
            Functions.showInternetAlert(activity);
        }
    }


    @Override
    public void onResult(JSONObject jobjWhole) {
        try {
            if (jobjWhole != null) {
                JSONObject jObj = jobjWhole.optJSONObject(Connection.TAG_DATA);
                int strStatus = jObj.optInt("success");
                String strMessage = jObj.optString("message");

                if (strStatus != 0) {
                    JSONArray jArrayexamChapterOptionList = jObj.getJSONArray("examChapterOptionList");
                    if (jArrayexamChapterOptionList.length() > 0) {
                        listExamChapterOptionList.clear();
                        for (int i = 0; i < jArrayexamChapterOptionList.length(); i++) {
                            try {
                                JSONObject obj = jArrayexamChapterOptionList.getJSONObject(i);
                                listExamChapterOptionList.add(new ExamChapterOptionList(
                                        obj.optString("option")));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        final ExamChapterOptionList examChapterOpList = listExamChapterOptionList.get(0);
                        if (listExamChapterOptionList.size() > 0) {
                            bottomSheetDialog.setCancelable(false);
                            bottomSheetDialog.show(activity.getSupportFragmentManager(), examChapterOpList.getOptinonName());
                            //Toast.makeText(activity, "asd0-" + examSubjectList.getSubjectName(), Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Functions.ToastUtility(activity, strMessage);
                    //recyclerViewLanguage.setVisibility(View.GONE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return listSubjectChapterCompExam.size();
    }

    public int getNumColumns() {
        return this.mNumColumns;
    }

    public void setNumColumns(int numColumns) {
        this.mNumColumns = numColumns;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final AppCompatTextView mTvPaperName;

        public MyViewHolder(View itemView) {
            super(itemView);
            mFunction = new Functions(activity);
            listExamChapterOptionList = new ArrayList<>();
            mTvPaperName = (AppCompatTextView) itemView.findViewById(R.id.tv_papername);

        }
    }
}


