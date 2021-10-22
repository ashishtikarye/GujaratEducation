package com.gujeducation.gujaratedu.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.gujeducation.R;
import com.gujeducation.gujaratedu.Activity.ChapterScreen;
import com.gujeducation.gujaratedu.Activity.PdfScreen;
import com.gujeducation.gujaratedu.Activity.QuizScreen;
import com.gujeducation.gujaratedu.Activity.VideoListScreen;
import com.gujeducation.gujaratedu.Helper.Connection;
import com.gujeducation.gujaratedu.Helper.Functions;
import com.gujeducation.gujaratedu.Interface.OnResult;
import com.gujeducation.gujaratedu.Model.ExamChapterOptionList;
import com.gujeducation.gujaratedu.Model.SubjectOptionMenu;
import com.gujeducation.gujaratedu.ServerAPIs.APIs;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ExamChapterOptionAdapter extends RecyclerView.Adapter<ExamChapterOptionAdapter.MyViewHolder> implements OnResult {

    //  private final AppCompatActivity activityApp;
    private final FragmentActivity activity;
    public boolean error = false;
    ArrayList<ExamChapterOptionList> listExamChapterOption = new ArrayList();
    Functions mFunction;
    Intent intent;
    String examTextbookPdf,pdfName, examWorksheet;
    private int mNumColumns = 0;
    private String standard;

    public ExamChapterOptionAdapter(FragmentActivity activity, ArrayList<ExamChapterOptionList> listExamChapterOption) {
        this.activity = activity;
        this.listExamChapterOption = listExamChapterOption;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup holder, int i) {
        View itemView = LayoutInflater.from(holder.getContext()).inflate(R.layout.row_examsubject, holder, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final MyViewHolder menuItemHolder = holder;
        //mPreference = activity.getSharedPreferences(activity.getResources().getString(R.string.app_name), MODE_PRIVATE);
        mFunction = new Functions(activity);
        final ExamChapterOptionList examChapterOptList = listExamChapterOption.get(position);

        try {
            //Log.e("data",""+sectionList.getSection());
            menuItemHolder.tvExamSubject.setText(examChapterOptList.getOptinonName());
            menuItemHolder.rlExamSubject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    /*Log.e("ExamChptrOptnAdp", "getExamId->" + mFunction.getPrefExamId() +
                            " getPapersId->" + mFunction.getPrefPapersId() +
                            " getSubjectId->" + mFunction.getPrefSubjectId()+
                            " getChapterId->" + mFunction.getPrefChapterId()
                    );*/

                    if (examChapterOptList.getOptinonName().equalsIgnoreCase("Textbook")) {
                        if (Functions.knowInternetOn(activity)) {
                            callApi("Textbook",
                                    mFunction.getPrefExamId(), mFunction.getPrefPapersId(),
                                    mFunction.getPrefSubjectId(), mFunction.getPrefChapterId());
                        } else {
                            Functions.showInternetAlert(activity);
                        }
                    }

                    if (examChapterOptList.getOptinonName().equalsIgnoreCase("Worksheet")) {
                        if (Functions.knowInternetOn(activity)) {
                            callApi("Worksheet",
                                    mFunction.getPrefExamId(), mFunction.getPrefPapersId(),
                                    mFunction.getPrefSubjectId(), mFunction.getPrefChapterId());
                        } else {
                            Functions.showInternetAlert(activity);
                        }
                    }

                    if (examChapterOptList.getOptinonName().equalsIgnoreCase("Study Video")) {
                        if (Functions.knowInternetOn(activity)) {
                            callApi("Study Video",
                                    mFunction.getPrefExamId(), mFunction.getPrefPapersId(),
                                    mFunction.getPrefSubjectId(), mFunction.getPrefChapterId());
                        } else {
                            Functions.showInternetAlert(activity);
                        }
                    }


                    if (examChapterOptList.getOptinonName().equalsIgnoreCase("MCQs")) {
                        if (Functions.knowInternetOn(activity)) {
                            intent = new Intent(activity, QuizScreen.class);
                            intent.putExtra("mediumId", mFunction.getPrefMediumId());
                            intent.putExtra("examId", mFunction.getPrefExamId());
                            intent.putExtra("papersId", mFunction.getPrefPapersId());
                            intent.putExtra("subjectId", mFunction.getPrefSubjectId());
                            intent.putExtra("chapterId", mFunction.getPrefChapterId());
                            activity.startActivity(intent);
                        } else {
                            Functions.showInternetAlert(activity);
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void callApi(String apiName, int examId, int papersId, int subjectId, int chapterId) {

        if (apiName.equalsIgnoreCase("Textbook")) {
            if (Functions.knowInternetOn(activity)) {
                APIs.getExamTextbook((AppCompatActivity) activity, this, mFunction.getPrefMediumId(),
                        examId, papersId, subjectId, chapterId);
            } else {
                Functions.showInternetAlert(activity);
            }
        }

        if (apiName.equalsIgnoreCase("Worksheet")) {
            if (Functions.knowInternetOn(activity)) {
                APIs.getExamWorksheetChapter((AppCompatActivity) activity, this, mFunction.getPrefMediumId(),
                        examId, papersId, subjectId, chapterId);
            } else {
                Functions.showInternetAlert(activity);
            }
        }

        if (apiName.equalsIgnoreCase("Study Video")) {
            if (Functions.knowInternetOn(activity)) {

                intent = new Intent(activity, VideoListScreen.class);
                intent.putExtra("examId", examId);
                intent.putExtra("papersId", papersId);
                intent.putExtra("subjectId", subjectId);
                intent.putExtra("chapterId", chapterId);
                activity.startActivity(intent);

            } else {
                Functions.showInternetAlert(activity);
            }
        }


        if (apiName.equalsIgnoreCase("MCQs")) {
            if (Functions.knowInternetOn(activity)) {
                APIs.getExamTextbook((AppCompatActivity) activity, this, mFunction.getPrefMediumId(),
                        examId, papersId, subjectId, chapterId);
            } else {
                Functions.showInternetAlert(activity);
            }
        }
    }

    @Override
    public int getItemCount() {
        return listExamChapterOption.size();
    }

    public int getNumColumns() {
        return this.mNumColumns;
    }

    public void setNumColumns(int numColumns) {
        this.mNumColumns = numColumns;
    }

    @Override
    public void onResult(JSONObject jobjWhole) {
        try {
            if (jobjWhole != null) {
                JSONObject jObj = jobjWhole.optJSONObject(Connection.TAG_DATA);
                int strStatus = jObj.optInt("success");
                String strApiName = jObj.optString("api");
                String strMessage = jObj.optString("message");

                if (strApiName.equalsIgnoreCase("getExamTextbook")) {
                    JSONArray jArrayTextSub = jObj.getJSONArray("exam_textbook");
                    JSONObject obj = jArrayTextSub.getJSONObject(0);
                    examTextbookPdf = obj.optString("textbookPdf");
                    pdfName = obj.optString("pdfName");
                    Intent intent = new Intent(activity, PdfScreen.class);
                    intent.putExtra("Name", pdfName);
                    intent.putExtra("PDF", examTextbookPdf);
                    activity.startActivity(intent);
                }

                if (strApiName.equalsIgnoreCase("getExamWorksheetChapter")) {
                    JSONArray jArrayTextSub = jObj.getJSONArray("exam_worksheet_chapter");
                    JSONObject obj = jArrayTextSub.getJSONObject(0);
                    //examWorksheet = obj.optString("worksheetPdf");
                    Intent intent = new Intent(activity, PdfScreen.class);
                    intent.putExtra("Name", obj.optString("pdf_name"));
                    intent.putExtra("PDF", obj.optString("worksheetPdf"));
                    activity.startActivity(intent);
                    activity.overridePendingTransition(R.anim.slide_in_right, 0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final RelativeLayout rlExamSubject;
        private final AppCompatTextView tvExamSubject;
        //private AppCompatImageView mIvBoardMemberPic;

        public MyViewHolder(View itemView) {
            super(itemView);
            rlExamSubject = itemView.findViewById(R.id.rl_examsubjectid);
            tvExamSubject = itemView.findViewById(R.id.tv_examsubject);
        }
    }

}
