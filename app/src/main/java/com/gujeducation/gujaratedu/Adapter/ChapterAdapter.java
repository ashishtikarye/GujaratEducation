package com.gujeducation.gujaratedu.Adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.gujeducation.R;
import com.gujeducation.gujaratedu.Activity.PrePrimaryTextScreen;
import com.gujeducation.gujaratedu.Fragment.BottomSheetChapterFragment;
import com.gujeducation.gujaratedu.Helper.Connection;
import com.gujeducation.gujaratedu.Helper.Functions;
import com.gujeducation.gujaratedu.Interface.OnResult;
import com.gujeducation.gujaratedu.Model.Chapter;
import com.gujeducation.gujaratedu.ServerAPIs.APIs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ChapterAdapter extends RecyclerView.Adapter<ChapterAdapter.MyViewHolder> implements OnResult {

    public static BottomSheetChapterFragment bottomSheetChapterFragment;
    private final AppCompatActivity activity;
    public boolean error = false;
    ArrayList<Chapter> listChapter = new ArrayList();
    ArrayList<String> listChapterOption = new ArrayList();
    Functions mFunction;
    Intent intent;
    String chapterName, chapterPDF, chapterPDFName;
    private int mNumColumns = 0;

    public ChapterAdapter(AppCompatActivity activity, ArrayList<Chapter> listChapter) {
        this.activity = activity;
        this.listChapter = listChapter;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup holder, int i) {
        View itemView = LayoutInflater.from(holder.getContext()).inflate(R.layout.row_chapter, holder, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final MyViewHolder menuItemHolder = (MyViewHolder) holder;
        final Chapter chapterList = listChapter.get(position);

        try {
            menuItemHolder.tvChapterNo.setText(chapterList.getChapterNo());
            menuItemHolder.tvChapter.setText(chapterList.getChapterName());

            menuItemHolder.cardChapter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("GETStandardIdentify","----"+mFunction.getStandardIdentify());

                    if (mFunction.getStandardIdentify() == 1) {
                        mFunction.setChapterId(chapterList.getChapterId());
                        mFunction.setChapterName(chapterList.getChapterName());
                        intent = new Intent(activity, PrePrimaryTextScreen.class);
                        intent.putExtra("chapterId", chapterList.getChapterId());
                        intent.putExtra("chapterName", chapterList.getChapterName());
                        activity.startActivity(intent);
                    } else {
                        mFunction.setChapterId(chapterList.getChapterId());
                        mFunction.setChapterName(chapterList.getChapterName());
                        /*chapterName = chapterList.getChapterName();
                        chapterPDF = chapterList.getChapterPDF();
                        chapterPDFName = chapterList.getChapterPDFName();*/
                        //Log.e("ChpAdp","chapterName-->"+chapterName+" chapterPDF-->"+chapterPDF);
                        callApi();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void callApi() {
        if (Functions.knowInternetOn(activity)) {
            /*Log.e("ChpAdp_Pref", "mediumId->" + mFunction.getPrefMediumId() +
                    "\ngetSection->" + mFunction.getSection() +
                    "\ngetSemester->" + mFunction.getSemester() +
                    "\ngetStandardId->" + mFunction.getStandardId() +
                    "\ngetSubjectId->" + mFunction.getSubjectId() +
                    "\ngetChapterId->" + mFunction.getChapterId());*/

            APIs.getChapterOption(activity, this, mFunction.getPrefMediumId(), mFunction.getSection(), mFunction.getSemester(),
                    mFunction.getStandardId(), mFunction.getSubjectId(), mFunction.getChapterId());
        } else {
            Functions.showInternetAlert(activity);
        }
    }

    @Override
    public int getItemCount() {
        return listChapter.size();
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

                if (strStatus != 0) {
                    JSONArray jArraySection = jObj.getJSONArray("chapterOptionList");
                    if (jArraySection.length() > 0) {
                        listChapterOption.clear();
                        for (int i = 0; i < jArraySection.length(); i++) {
                            try {
                                JSONObject obj = jArraySection.getJSONObject(i);
                                listChapterOption.add(
                                        obj.optString("option")
                                );
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        if (listChapterOption.size() != 0) {

                            bottomSheetChapterFragment = new BottomSheetChapterFragment(activity, chapterName, listChapterOption);
                            bottomSheetChapterFragment.setCancelable(false);
                            bottomSheetChapterFragment.show(activity.getSupportFragmentManager(), "Option");
                            //Toast.makeText(this, "" + sectionList.getStandard(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(activity, "Data Not Found...!", Toast.LENGTH_SHORT).show();
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

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final CardView cardChapter;
        private final AppCompatTextView tvChapterNo;
        private final AppCompatTextView tvChapter;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardChapter = (CardView) itemView.findViewById(R.id.card_chapter);
            tvChapterNo = (AppCompatTextView) itemView.findViewById(R.id.tv_chapterno);
            tvChapter = (AppCompatTextView) itemView.findViewById(R.id.tv_chapter);
            mFunction = new Functions(activity);
        }
    }
}
