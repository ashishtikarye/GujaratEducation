package com.gujeducation.gujaratedu.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gujeducation.R;
import com.gujeducation.gujaratedu.Adapter.SubjectAdapter;
import com.gujeducation.gujaratedu.Helper.Functions;
import com.gujeducation.gujaratedu.Model.Subject;

import java.util.ArrayList;

public class Tab1Fragment extends Fragment {

    private ArrayList<Subject> listArrSubjectSem1 = new ArrayList<Subject>();
    Functions mFunctions;
    Intent intent;
    RecyclerView recyclerSubject;
    LinearLayoutManager mLayoutManager;
    SubjectAdapter mSubjectAdapter;

    public Tab1Fragment(ArrayList<Subject> listArrSubjectSem1){
        this.listArrSubjectSem1 = listArrSubjectSem1;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tab1,container,false);
        recyclerSubject =  view.findViewById(R.id.recyclerview_subjectsem1);
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
        recyclerSubject.setHasFixedSize(true);
        recyclerSubject.setLayoutManager(mLayoutManager);

        if(listArrSubjectSem1.size() != 0){
            mSubjectAdapter = new SubjectAdapter(getActivity(),listArrSubjectSem1);
            mSubjectAdapter.notifyDataSetChanged();
            recyclerSubject.setAdapter(mSubjectAdapter);
            recyclerSubject.setVisibility(View.VISIBLE);
        }
        return view;
    }
}
