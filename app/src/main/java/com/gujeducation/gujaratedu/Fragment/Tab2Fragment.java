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


public class Tab2Fragment extends Fragment {

    Functions mFunctions;
    Intent intent;
    RecyclerView recyclerSubject;
    LinearLayoutManager mLayoutManager;
    SubjectAdapter mSubjectAdapter;
    private ArrayList<Subject> listArrSubjectSem2 = new ArrayList<Subject>();

    public Tab2Fragment(ArrayList<Subject> listArrSubjectSem2) {
        this.listArrSubjectSem2 = listArrSubjectSem2;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tab2, container, false);
        recyclerSubject = view.findViewById(R.id.recyclerview_subjectsem2);
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerSubject.setHasFixedSize(true);
        recyclerSubject.setLayoutManager(mLayoutManager);


        if (listArrSubjectSem2.size() != 0) {
            mSubjectAdapter = new SubjectAdapter(getActivity(), listArrSubjectSem2);
            mSubjectAdapter.notifyDataSetChanged();
            recyclerSubject.setAdapter(mSubjectAdapter);
            recyclerSubject.setVisibility(View.VISIBLE);
        }
        return view;
    }
}
