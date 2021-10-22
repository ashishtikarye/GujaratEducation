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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.gujeducation.R;
import com.gujeducation.gujaratedu.Adapter.ExamSubjectAdapter;
import com.gujeducation.gujaratedu.Adapter.SectionAdapter;
import com.gujeducation.gujaratedu.Model.ExamSubject;
import com.gujeducation.gujaratedu.Model.Section;

import java.util.ArrayList;

public class BottomSheetSubjectFragment extends BottomSheetDialogFragment {

    /*private LinearLayout std_1_2_categoryLayout,std_11_12_categoryLayout;
    private RelativeLayout rlPragna,rlNonPragna,rlArts,rlCommerce,rlScience;*/
    private AppCompatImageView btnCancle;
    Intent intent;
    RecyclerView recyclerSubject;
    LinearLayoutManager mLayoutManager;
    private ArrayList<ExamSubject> listArrExamSubject = new ArrayList<ExamSubject>();
    ExamSubjectAdapter mExamSubjectAdapter;
    String standard;


    public BottomSheetSubjectFragment(ArrayList<ExamSubject> listArrExamSubject){
        this.listArrExamSubject = listArrExamSubject;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_subjectlist,container,false);
        recyclerSubject = view.findViewById(R.id.recyclerview_subject);
        recyclerSubject.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerSubject.setLayoutManager(mLayoutManager);
        standard = getTag();
        Log.e("Tagestandardddd",""+getTag());
        if(listArrExamSubject.size() != 0){
            mExamSubjectAdapter = new ExamSubjectAdapter(getActivity(),listArrExamSubject);
            recyclerSubject.setAdapter(mExamSubjectAdapter);
            mExamSubjectAdapter.notifyDataSetChanged();
            recyclerSubject.setVisibility(View.VISIBLE);
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
