package com.gujeducation.gujaratedu.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.gujeducation.R;
import com.gujeducation.gujaratedu.Activity.ChapterScreen;
import com.gujeducation.gujaratedu.Activity.EssayListScreen;
import com.gujeducation.gujaratedu.Activity.EvalutionScreen;
import com.gujeducation.gujaratedu.Activity.PdfScreen;
import com.gujeducation.gujaratedu.Activity.SubjectOldPaperListScreen;
import com.gujeducation.gujaratedu.Helper.Connection;
import com.gujeducation.gujaratedu.Helper.Functions;
import com.gujeducation.gujaratedu.Interface.OnResult;
import com.gujeducation.gujaratedu.Model.ExamChapterOptionList;
import com.gujeducation.gujaratedu.ServerAPIs.APIs;

import org.json.JSONObject;

import java.util.ArrayList;

public class SubOptionAdapter extends RecyclerView.Adapter<SubOptionAdapter.MyViewHolder> implements OnResult {

    private final AppCompatActivity activity;
    public boolean error = false;
    ArrayList<ExamChapterOptionList> listArrSubList = new ArrayList();
    Functions mFunction;
    String chapterName, PDF, PDFName;
    Intent intent;
    private int mNumColumns = 0;

    public SubOptionAdapter(AppCompatActivity activity, ArrayList<ExamChapterOptionList> listArrSubList) {
        this.activity = activity;
        this.listArrSubList = listArrSubList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup holder, int i) {
        View itemView = LayoutInflater.from(holder.getContext()).inflate(R.layout.row_sublist, holder, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final MyViewHolder menuItemHolder = holder;
        mFunction = new Functions(activity);
        final ExamChapterOptionList subList = listArrSubList.get(position);

        try {
            menuItemHolder.mTvOptionName.setText(subList.getOptinonName());

            menuItemHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (subList.getOptinonName().equalsIgnoreCase("Syllabus")) {
                        if (Functions.knowInternetOn(activity)) {
                          //  ((ChapterScreen)activity).showInterstitialAd();
                            APIs.getPdfSubject(activity, SubOptionAdapter.this::onResult, mFunction.getPrefMediumId(),
                                    mFunction.getSubjectId(), "Syllabus");
                        } else
                            Functions.showInternetAlert(activity);
                    }

                    if (subList.getOptinonName().equalsIgnoreCase("Blueprint")) {
                        if (Functions.knowInternetOn(activity)) {
                          //  ((ChapterScreen)activity).showInterstitialAd();
                            APIs.getPdfSubject(activity, SubOptionAdapter.this::onResult, mFunction.getPrefMediumId(),
                                    mFunction.getSubjectId(), "Blueprint");
                        } else
                            Functions.showInternetAlert(activity);
                    }

                    if (subList.getOptinonName().equalsIgnoreCase("Learning Outcome")) {
                        if (Functions.knowInternetOn(activity)) {
                           // ((ChapterScreen)activity).showInterstitialAd();
                            APIs.getPdfSubject(activity, SubOptionAdapter.this::onResult, mFunction.getPrefMediumId(),
                                    mFunction.getSubjectId(), "Learning Outcome");
                        } else
                            Functions.showInternetAlert(activity);
                    }

                    if (subList.getOptinonName().equalsIgnoreCase("Worksheet")) {
                        if (Functions.knowInternetOn(activity)) {
                          //  ((ChapterScreen)activity).showInterstitialAd();
                            APIs.getPdfSubject(activity, SubOptionAdapter.this::onResult, mFunction.getPrefMediumId(),
                                    mFunction.getSubjectId(), "Worksheet");
                        } else
                            Functions.showInternetAlert(activity);
                    }

                    if (subList.getOptinonName().equalsIgnoreCase("Old Paper")) {
                        if (Functions.knowInternetOn(activity)) {
                            //((ChapterScreen)activity).showInterstitialAd();
                            intent = new Intent(activity, SubjectOldPaperListScreen.class);
                            intent = intent.putExtra("optionType","Old Paper");
                            intent = intent.putExtra("subjectId",mFunction.getSubjectId());
                            activity.startActivity(intent);
                        } else
                            Functions.showInternetAlert(activity);
                    }


                    if (subList.getOptinonName().equalsIgnoreCase("Essay")) {
                        if (Functions.knowInternetOn(activity)) {
                            //((ChapterScreen)activity).showInterstitialAd();
                            intent = new Intent(activity, EssayListScreen.class);
                            intent = intent.putExtra("optionType","Essay");
                            intent = intent.putExtra("subjectId",mFunction.getSubjectId());
                            activity.startActivity(intent);
                        } else
                            Functions.showInternetAlert(activity);
                    }


                    if (subList.getOptinonName().equalsIgnoreCase("Evalution")) {
                        if (Functions.knowInternetOn(activity)) {
                            //((ChapterScreen)activity).showInterstitialAd();
                            intent = new Intent(activity, EvalutionScreen.class);
                            intent = intent.putExtra("optionType","Evalution");
                            intent = intent.putExtra("subjectId",mFunction.getSubjectId());
                            activity.startActivity(intent);
                        } else
                            Functions.showInternetAlert(activity);
                    }


                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return listArrSubList.size();
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
                String strMessage = jObj.optString("message");
                String strApi = jObj.optString("api");

                if (strStatus != 0) {
                    if (strApi.equalsIgnoreCase("getPdfSubject")) {
                        if (jObj != null) {
                            try {
                                PDF = jObj.optString("pdf");
                                PDFName = jObj.optString("pdf_name");

                                /*Log.e("pdfresponse", "PDF->" + PDF +
                                        "\nPDFName->" + PDFName);*/

                                if (!(PDF.isEmpty() && PDFName.isEmpty())) {
                                    intent = new Intent(activity, PdfScreen.class);
                                    intent.putExtra("PDF", PDF);
                                    intent.putExtra("Name", PDFName);
                                    ChapterScreen.dialogList.dismiss();
                                    activity.startActivity(intent);
                                } else {
                                    Toast.makeText(activity, "Data Not Found...!", Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } else {
                    Functions.ToastUtility(activity, strMessage);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final AppCompatTextView mTvOptionName;

        public MyViewHolder(View itemView) {
            super(itemView);
            mTvOptionName = itemView.findViewById(R.id.tv_optionname);
        }
    }
}
