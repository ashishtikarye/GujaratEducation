package com.gujeducation.gujaratedu.Adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import com.gujeducation.gujaratedu.ServerAPIs.APIs;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class SubjectDetailsAdapter extends RecyclerView.Adapter<SubjectDetailsAdapter.MyViewHolder> implements OnResult {
    private final AppCompatActivity activity;
    ArrayList<ExamChapterOptionList> listSubjectDetails = new ArrayList();
    Functions mFunction;
    Intent intent;
    private int mNumColumns = 0;
    private String standard;

    public SubjectDetailsAdapter(AppCompatActivity activity, ArrayList<ExamChapterOptionList> listSubjectDetails) {
        this.activity = activity;
        this.listSubjectDetails = listSubjectDetails;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup holder, int i) {
        View itemView = LayoutInflater.from(holder.getContext()).inflate(R.layout.row_details_option, holder,
                false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final MyViewHolder menuItemHolder = holder;
        mFunction = new Functions(activity);
        final ExamChapterOptionList subdetailsList = listSubjectDetails.get(position);

        try {
            holder.tvOption.setText(subdetailsList.getOptinonName());


            /*Log.e("SubjectDtlsAdptr","PrefMediumId->"+mFunction.getPrefMediumId()+
                    " PrefExamId->"+mFunction.getPrefExamId()+
                    " PrefPapersId->"+mFunction.getPrefPapersId()+
                    " PrefSubjectId->"+mFunction.getPrefSubjectId()
            );*/


            menuItemHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(activity, "optName->" + subdetailsList.getOptinonName(), Toast.LENGTH_SHORT).show();


                    if (subdetailsList.getOptinonName().equalsIgnoreCase("Worksheet")) {
                        callApi("Worksheet");
                    }
                    if (subdetailsList.getOptinonName().equalsIgnoreCase("Old Papers")) {
                        Intent intent = new Intent(activity, OldPaperListScreen.class);
                        activity.startActivity(intent);
                    }
                    if (subdetailsList.getOptinonName().equalsIgnoreCase("Syllabus")) {
                        callApi("Syllabus");
                    }
                    if (subdetailsList.getOptinonName().equalsIgnoreCase("OMR")) {
                        callApi("OMR");
                    }


                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void callApi(String callType) {

        /*Log.e("callApiV","PrefMediumId->"+mFunction.getPrefMediumId()+
                " PrefExamId->"+mFunction.getPrefExamId()+
                " PrefPapersId->"+mFunction.getPrefPapersId()+
                " PrefSubjectId->"+mFunction.getPrefSubjectId()
        );*/


        if (Functions.knowInternetOn(activity)) {
            if (callType.equalsIgnoreCase("Worksheet")) {
                APIs.getExamWorksheetSubject(activity, this, mFunction.getPrefMediumId(), mFunction.getPrefExamId(),
                        mFunction.getPrefPapersId(), mFunction.getPrefSubjectId());
            }

            if (callType.equalsIgnoreCase("Syllabus")) {
                APIs.getExamSyllabus(activity, this, mFunction.getPrefMediumId(), mFunction.getPrefExamId(),
                        mFunction.getPrefPapersId(), mFunction.getPrefSubjectId());
            }

            if (callType.equalsIgnoreCase("OMR")) {
                APIs.getExamOmr(activity, this, mFunction.getPrefMediumId(), mFunction.getPrefExamId(),
                        mFunction.getPrefPapersId(), mFunction.getPrefSubjectId());
            }
        } else {
            Functions.showInternetAlert(activity);
        }
    }

    @Override
    public int getItemCount() {
        return listSubjectDetails.size();
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
                if (strApiName.equalsIgnoreCase("getExamWorksheetSubject")) {

                    JSONArray jArrayTextSub = jObj.getJSONArray("exam_worksheet_subject");
                    JSONObject obj = jArrayTextSub.getJSONObject(0);


                    Intent intent = new Intent(activity, PdfScreen.class);
                    /*Log.e("worksheet","pdf_name->"+obj.optString("pdf_name") +
                            "\npdf->"+obj.optString("pdf"));*/
                    intent.putExtra("Name", obj.optString("pdf_name"));
                    intent.putExtra("PDF", obj.optString("pdf"));
                    activity.startActivity(intent);
                    activity.overridePendingTransition(R.anim.slide_in_right, 0);
                }
                if (strApiName.equalsIgnoreCase("getExamOmr")) {

                    JSONArray jArrayTextSub = jObj.getJSONArray("exam_omr");
                    JSONObject obj = jArrayTextSub.getJSONObject(0);
                    /*Log.e("exam_omr","pdf_name->"+obj.optString("pdf_name") +
                            "\npdf->"+obj.optString("pdf"));*/

                    Intent intent = new Intent(activity, PdfScreen.class);
                    intent.putExtra("Name", obj.optString("pdf_name"));
                    intent.putExtra("PDF", obj.optString("pdf"));
                    activity.startActivity(intent);
                    activity.overridePendingTransition(R.anim.slide_in_right, 0);
                }

                if (strApiName.equalsIgnoreCase("getExamSyllabus")) {

                    JSONArray jArrayTextSub = jObj.getJSONArray("exam_syllabus");
                    JSONObject obj = jArrayTextSub.getJSONObject(0);

                    /*Log.e("exam_syllabus","pdf_name->"+obj.optString("pdf_name") +
                            "\npdf->"+obj.optString("pdf"));*/
                    Intent intent = new Intent(activity, PdfScreen.class);
                    intent.putExtra("Name", obj.optString("pdf_name"));
                    intent.putExtra("PDF", obj.optString("pdf"));
                    activity.startActivity(intent);
                    activity.overridePendingTransition(R.anim.slide_in_right, 0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final AppCompatImageView ivOption;
        private final AppCompatTextView tvOption;
        //private AppCompatImageView mIvBoardMemberPic;

        public MyViewHolder(View itemView) {
            super(itemView);
            ivOption = itemView.findViewById(R.id.btnview_aero);
            tvOption = itemView.findViewById(R.id.tv_optionname);
        }
    }
}
