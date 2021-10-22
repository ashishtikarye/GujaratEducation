package com.gujeducation.gujaratedu.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gujeducation.R;
import com.gujeducation.gujaratedu.Fragment.BottomSheetSubjectFragment;
import com.gujeducation.gujaratedu.Helper.Connection;
import com.gujeducation.gujaratedu.Helper.Functions;
import com.gujeducation.gujaratedu.Interface.OnResult;
import com.gujeducation.gujaratedu.Model.ExamSubject;
import com.gujeducation.gujaratedu.Model.Papers;
import com.gujeducation.gujaratedu.ServerAPIs.APIs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PaperListAdapter extends RecyclerView.Adapter<PaperListAdapter.MyViewHolder> implements OnResult {

    private final AppCompatActivity activity;
    public boolean error = false;
    // private final ArrayList<ExamSubject> listArrExamSubject = new ArrayList<ExamSubject>();
    ArrayList<ExamSubject> listArrExamSubject;
    ArrayList<Papers> listPapers = new ArrayList();
    Functions mFunction;
    Intent intent;
    BottomSheetSubjectFragment bottomSheetDialog;
    Dialog dialogList;
    RecyclerView recyclerViewDialog;
    LinearLayoutManager mLayoutManager;
    ExamSubjectAdapter mExamSubjectAdapter;
    // ArrayList<ExamSubject> listExamSubject;
    private int mNumColumns = 0;

    public PaperListAdapter(AppCompatActivity activity, ArrayList<Papers> listPapers) {
        this.activity = activity;
        this.listPapers = listPapers;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup holder, int i) {
        View itemView = LayoutInflater.from(holder.getContext()).inflate(R.layout.row_papers, holder, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final MyViewHolder menuItemHolder = holder;
        mFunction = new Functions(activity);
        final Papers paperList = listPapers.get(position);


        try {
            int pos = position + 1;
            menuItemHolder.mTvPaperName.setText(pos + ")  " + paperList.getPaperName());
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

                callApi(paperList.getExamId(), paperList.getPaperId());
                bottomSheetDialog = new BottomSheetSubjectFragment(listArrExamSubject);


            }
        });


    }


    private void callApi(int examId, int paperId) {
        if (Functions.knowInternetOn(activity)) {
            APIs.getExamSubject(activity, this, mFunction.getPrefMediumId(), examId, paperId);
        } else {
            Functions.showInternetAlert(activity);
        }
    }


    /*public void showPaperList() {

        dialogList = new Dialog(
                activity, R.style.popupTheme);
        LayoutInflater inflater = (LayoutInflater)
                activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_recyclerview, null);
        dialogList.setContentView(view); // your custom view.
        dialogList.setCancelable(true);
        dialogList.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialogList.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialogList.show();
        //countryHading = (TextView) view.findViewById(R.id.country_search_heading);
        //mEt_SearchBox = (EditText) view.findViewById(R.id.dialog_search_autocomplete);
        recyclerViewDialog = (RecyclerView) view.findViewById(R.id.recyclerview_popup);
        mLayoutManager = new LinearLayoutManager(activity);

        recyclerViewDialog.setHasFixedSize(true);
        recyclerViewDialog.setLayoutManager(mLayoutManager);

        Log.e("listArrExamSubject", "" + listArrExamSubject.size());
        Log.e("getSubjectName", "" + listArrExamSubject.get(0).getSubjectName());
        if (listArrExamSubject.size() != 0) {
            mExamSubjectAdapter = new ExamSubjectAdapter(activity, listArrExamSubject);
            recyclerViewDialog.setAdapter(mExamSubjectAdapter);
            mExamSubjectAdapter.notifyDataSetChanged();
            recyclerViewDialog.setVisibility(View.VISIBLE);
        }


    }*/


    @Override
    public void onResult(JSONObject jobjWhole) {
        try {
            if (jobjWhole != null) {
                JSONObject jObj = jobjWhole.optJSONObject(Connection.TAG_DATA);
                int strStatus = jObj.optInt("success");
                String strMessage = jObj.optString("message");

                if (strStatus != 0) {
                    JSONArray jArrayExamSubject = jObj.getJSONArray("exam_subject");
                    if (jArrayExamSubject.length() > 0) {
                        listArrExamSubject.clear();
                        for (int i = 0; i < jArrayExamSubject.length(); i++) {
                            try {
                                JSONObject obj = jArrayExamSubject.getJSONObject(i);
                                listArrExamSubject.add(new ExamSubject(
                                        obj.optInt("mediumId"),
                                        obj.optInt("examId"),
                                        obj.optInt("papersId"),
                                        obj.optInt("subjectId"),
                                        obj.optString("subject"),
                                        obj.optString("madeBy"),
                                        obj.optString("creditsAndLicence"),
                                        obj.optString("image")
                                ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        //showPaperList();


                        final ExamSubject examSubjectList = listArrExamSubject.get(0);
                        if (listArrExamSubject.size() > 0) {
                            bottomSheetDialog.setCancelable(false);
                            bottomSheetDialog.show(activity.getSupportFragmentManager(), examSubjectList.getSubjectName());
                        }


                        //final ExamSubject examSubjectList = listExamSubject.get(0);

                        //if (listExamSubject.size()) {
                        //Log.e("SubjectName",""+examSubjectList.getSubjectName());
                        //bottomSheetDialog.setCancelable(false);
                        //bottomSheetDialog.show(activity.getSupportFragmentManager(), examSubjectList.getSubjectName());
                        //Toast.makeText(activity, "asd0-" + examSubjectList.getSubjectName(), Toast.LENGTH_SHORT).show();
                        //}
                       /* else if(listSection.size() == 1){
                            mFunction.setSectionId(sectionList.getSectionId());
                            mFunction.setStandardId(sectionList.getStandardId());
                            intent = new Intent(activity, ClassScreen.class);
                            intent.putExtra("class",sectionList.getStandard());
                            activity.startActivity(intent);
                        }*/
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
        return listPapers.size();
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
            listArrExamSubject = new ArrayList<>();
            mTvPaperName = itemView.findViewById(R.id.tv_papername);
        }
    }
}


