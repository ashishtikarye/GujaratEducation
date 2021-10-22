package com.gujeducation.gujaratedu.Adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.gujeducation.R;
import com.gujeducation.gujaratedu.Activity.ChapterScreen;
import com.gujeducation.gujaratedu.Activity.MagazineEditionScreen;
import com.gujeducation.gujaratedu.Activity.PdfScreen;
import com.gujeducation.gujaratedu.Activity.VideoListChapterScreen;
import com.gujeducation.gujaratedu.Helper.Connection;
import com.gujeducation.gujaratedu.Helper.Functions;
import com.gujeducation.gujaratedu.Interface.OnResult;
import com.gujeducation.gujaratedu.ServerAPIs.APIs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ChapterOptionAdapter extends RecyclerView.Adapter<ChapterOptionAdapter.MyViewHolder> implements OnResult {

    private final AppCompatActivity mainActivity;
    private final FragmentActivity activity;
    public boolean error = false;
    ArrayList<String> listOption = new ArrayList();
    Functions mFunction;
    Intent intent;
    String chapterName, PDF, PDFName;
    private int mNumColumns = 0;
    private String standard;

    public ChapterOptionAdapter(AppCompatActivity mainActivity, FragmentActivity activity, String chapterName, ArrayList<String> listOption) {
        this.mainActivity = mainActivity;
        this.activity = activity;
        this.chapterName = chapterName;
        this.listOption = listOption;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup holder, int i) {
        View itemView = LayoutInflater.from(holder.getContext()).inflate(R.layout.row_chapter_option, holder, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final MyViewHolder menuItemHolder = holder;
        mFunction = new Functions(activity);
        //final String sectionList = listSection.get(position);

        /*Log.e("ChpOptnAdp_Pref", "mediumId->" + mFunction.getPrefMediumId() +
                "\ngetSection->" + mFunction.getSection() +
                "\ngetSemester->" + mFunction.getSemester() +
                "\ngetStandardId->" + mFunction.getStandardId() +
                "\ngetSubjectId->" + mFunction.getSubjectId() +
                "\ngetChapterId->" + mFunction.getChapterId());*/


        try {
            if (listOption.get(position).equalsIgnoreCase("Textbook"))
                menuItemHolder.ivOption.setBackgroundResource(R.drawable.ic_read);
            if (listOption.get(position).equalsIgnoreCase("Exercise"))
                menuItemHolder.ivOption.setBackgroundResource(R.drawable.ic_exercise);
            if (listOption.get(position).equalsIgnoreCase("Worksheet"))
                menuItemHolder.ivOption.setBackgroundResource(R.drawable.ic_worksheet);
            if (listOption.get(position).equalsIgnoreCase("Videos"))
                menuItemHolder.ivOption.setBackgroundResource(R.drawable.ic_video);
            if (listOption.get(position).equalsIgnoreCase("Teacher Edition"))
                menuItemHolder.ivOption.setBackgroundResource(R.drawable.ic_teacher_edition);
            if (listOption.get(position).equalsIgnoreCase("MCQs"))
                menuItemHolder.ivOption.setBackgroundResource(R.drawable.ic_mcq);

            menuItemHolder.tvOption.setText(listOption.get(position));
            menuItemHolder.rlOption.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (listOption.get(position).equalsIgnoreCase("Textbook")) {
                        //((ChapterScreen)activity).showInterstitialAd();
                        if (Functions.knowInternetOn(activity)) {
                            APIs.getPDFChapter(mainActivity, ChapterOptionAdapter.this::onResult, mFunction.getPrefMediumId(),
                                    mFunction.getSection(), mFunction.getSemester(),
                                    mFunction.getStandardId(), mFunction.getSubjectId(),
                                    mFunction.getChapterId(), "Textbook");
                        } else
                            Functions.showInternetAlert(activity);

                    }
                    if (listOption.get(position).equalsIgnoreCase("Worksheet")) {
                        if (Functions.knowInternetOn(activity)) {
                            //((ChapterScreen)activity).showInterstitialAd();
                            APIs.getPDFChapter(mainActivity, ChapterOptionAdapter.this::onResult, mFunction.getPrefMediumId(),
                                    mFunction.getSection(), mFunction.getSemester(),
                                    mFunction.getStandardId(), mFunction.getSubjectId(),
                                    mFunction.getChapterId(), "Worksheet");
                        } else
                            Functions.showInternetAlert(activity);
                    }

                    if (listOption.get(position).equalsIgnoreCase("Exercise")) {
                        if (Functions.knowInternetOn(activity)) {
                            //((ChapterScreen)activity).showInterstitialAd();
                            APIs.getPDFChapter(mainActivity, ChapterOptionAdapter.this::onResult, mFunction.getPrefMediumId(),
                                    mFunction.getSection(), mFunction.getSemester(),
                                    mFunction.getStandardId(), mFunction.getSubjectId(),
                                    mFunction.getChapterId(), "Exercise");
                        } else
                            Functions.showInternetAlert(activity);
                    }

                    if (listOption.get(position).equalsIgnoreCase("Teacher Edition")) {
                        if (Functions.knowInternetOn(activity)) {
                            //((ChapterScreen)activity).showInterstitialAd();
                            APIs.getPDFChapter(mainActivity, ChapterOptionAdapter.this::onResult, mFunction.getPrefMediumId(),
                                    mFunction.getSection(), mFunction.getSemester(),
                                    mFunction.getStandardId(), mFunction.getSubjectId(),
                                    mFunction.getChapterId(), "Teacher Edition");
                        } else
                            Functions.showInternetAlert(activity);
                    }
                    if (listOption.get(position).equalsIgnoreCase("Videos")) {
                        if (Functions.knowInternetOn(activity)) {
                            //((ChapterScreen)activity).showInterstitialAd();
                            intent = new Intent(activity, VideoListChapterScreen.class);
                            intent.putExtra("section",mFunction.getSection());
                            intent.putExtra("semester",mFunction.getSemester());
                            intent.putExtra("standardId",mFunction.getStandardId());
                            intent.putExtra("subjectId",mFunction.getSubjectId());
                            intent.putExtra("chapterId",mFunction.getChapterId());
                            activity.startActivity(intent);
                        } else
                            Functions.showInternetAlert(activity);
                    }

                    /*if (listOption.get(position).equalsIgnoreCase("MCQs")) {
                        intent = new Intent(activity, TestScreen.class);
                        activity.startActivity(intent);
                    }*/
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return listOption.size();
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
                    if (strApi.equalsIgnoreCase("getPdf")) {
                        if (jObj != null) {
                            try {
                                PDF = jObj.optString("pdf");
                                PDFName = jObj.optString("pdf_name");

                                Log.e("pdfresponse","PDF->"+PDF+
                                        "\nPDFName->"+PDFName);

                                if (!(PDF.isEmpty() && PDFName.isEmpty())) {
                                    intent = new Intent(activity, PdfScreen.class);
                                    intent.putExtra("PDF", PDF);
                                    intent.putExtra("Name", PDFName);
                                    ChapterAdapter.bottomSheetChapterFragment.dismiss();
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
        private final RelativeLayout rlOption;
        private final AppCompatImageView ivOption;
        private final AppCompatTextView tvOption;
        //private AppCompatImageView mIvBoardMemberPic;

        public MyViewHolder(View itemView) {
            super(itemView);
            rlOption = itemView.findViewById(R.id.rl_option);
            ivOption = itemView.findViewById(R.id.iv_option);
            tvOption = itemView.findViewById(R.id.tv_option);
        }
    }
}
