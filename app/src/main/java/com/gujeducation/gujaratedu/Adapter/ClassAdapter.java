package com.gujeducation.gujaratedu.Adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gujeducation.R;
import com.gujeducation.gujaratedu.Helper.Functions;
import com.gujeducation.gujaratedu.Model.Class;
import com.gujeducation.gujaratedu.Model.SubClass;

import java.util.ArrayList;

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.MyViewHolder> {

    private final AppCompatActivity activity;
    public boolean error = false;
    ArrayList<Class> listClass;
    ArrayList<String> listCategory;
    Functions mFunction;
    SharedPreferences mPreference;
    Intent intent;
    private int mNumColumns = 0;
    LinearLayoutManager mLayoutManager;
    SubClassAdapter subClassAdapter;

    public ClassAdapter(AppCompatActivity activity, ArrayList<Class> listClass, ArrayList<String> listCategory) {
        this.activity = activity;
        this.listClass = listClass;
        this.listCategory = listCategory;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup holder, int viewType) {

        View itemView = LayoutInflater.from(holder.getContext()).inflate(R.layout.row_class, holder, false);
        return new MyViewHolder(itemView);
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final MyViewHolder menuItemHolder = holder;
        mFunction = new Functions(activity);
        try {

            menuItemHolder.tvCategory.setText(listCategory.get(position));
            Class objClass = listClass.get(position);
            ArrayList<SubClass> singleCategory = objClass.getListClass();
            subClassAdapter = new SubClassAdapter(activity,singleCategory);
            menuItemHolder.subClassRecyclerView.setAdapter(subClassAdapter);
            menuItemHolder.subClassRecyclerView.setHasFixedSize(true);
            mLayoutManager = new GridLayoutManager(activity.getApplicationContext(),3);
            menuItemHolder.subClassRecyclerView.setLayoutManager(mLayoutManager);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return listCategory.size();
    }

    public int getNumColumns() {
        return this.mNumColumns;
    }

    public void setNumColumns(int numColumns) {
        this.mNumColumns = numColumns;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView tvCategory;
        private  RecyclerView subClassRecyclerView;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvCategory = itemView.findViewById(R.id.tv_category);
            subClassRecyclerView = itemView.findViewById(R.id.recyclerview_sub_class);
        }
    }
}
