package com.gujeducation.gujaratedu.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.gujeducation.R;
import com.gujeducation.gujaratedu.Adapter.ExamChapterOptionAdapter;
import com.gujeducation.gujaratedu.Adapter.ExamSubjectAdapter;
import com.gujeducation.gujaratedu.Model.ExamChapterOptionList;
import com.gujeducation.gujaratedu.Model.ExamSubject;

import java.util.ArrayList;

public class BottomSheetSubjectChapterFragment extends BottomSheetDialogFragment {

    /*private LinearLayout std_1_2_categoryLayout,std_11_12_categoryLayout;
    private RelativeLayout rlPragna,rlNonPragna,rlArts,rlCommerce,rlScience;*/
    private AppCompatImageView btnCancle;
    Intent intent;
    RecyclerView recyclerSubjectChapter;
    LinearLayoutManager mLayoutManager;
    private ArrayList<ExamChapterOptionList> listArrExamChapterOptionList = new ArrayList<ExamChapterOptionList>();
    ExamChapterOptionAdapter mExamChapterOptionAdapter;
    String standard;
    AppCompatTextView mTvCategory;


    public BottomSheetSubjectChapterFragment(ArrayList<ExamChapterOptionList> listArrExamChapterOptionList){
        this.listArrExamChapterOptionList = listArrExamChapterOptionList;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_subjectlist,container,false);
        mTvCategory = view.findViewById(R.id.tv_category);
        mTvCategory.setText(R.string.Com_exam_chapter_options);

        recyclerSubjectChapter = view.findViewById(R.id.recyclerview_subject);
        recyclerSubjectChapter.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerSubjectChapter.setLayoutManager(mLayoutManager);
        standard = getTag();
        Log.e("Tagestandardsubchp",""+getTag());
        if(listArrExamChapterOptionList.size() != 0){
            mExamChapterOptionAdapter = new ExamChapterOptionAdapter(getActivity(),listArrExamChapterOptionList);
            recyclerSubjectChapter.setAdapter(mExamChapterOptionAdapter);
            mExamChapterOptionAdapter.notifyDataSetChanged();
            recyclerSubjectChapter.setVisibility(View.VISIBLE);
            //Log.e("Data",""+listArrSection.get(0).getSection());
        }
        btnCancle = view.findViewById(R.id.iv_cancle);

        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return view;
    }
}
