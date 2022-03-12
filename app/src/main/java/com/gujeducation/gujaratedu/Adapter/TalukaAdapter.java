package com.gujeducation.gujaratedu.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.gujeducation.R;
import com.gujeducation.gujaratedu.Activity.MyAccountScreen;
import com.gujeducation.gujaratedu.Activity.SignUpScreen;
import com.gujeducation.gujaratedu.Helper.Functions;
import com.gujeducation.gujaratedu.Model.Taluka;

import java.util.ArrayList;

public class TalukaAdapter extends RecyclerView.Adapter<TalukaAdapter.MyViewHolder> {

    private final AppCompatActivity activity;
    public boolean error = false;
    ArrayList<Taluka> listTaluka = new ArrayList();
    Functions mFunction;
    private int mNumColumns = 0;

    public TalukaAdapter(AppCompatActivity activity, ArrayList<Taluka> listTaluka) {
        this.activity = activity;
        this.listTaluka = listTaluka;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup holder, int i) {
        View itemView = LayoutInflater.from(holder.getContext()).inflate(R.layout.row_district_taluka, holder, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final MyViewHolder menuItemHolder = holder;
        mFunction = new Functions(activity);
        final Taluka talukaList = listTaluka.get(position);

        try {
            menuItemHolder.mTvDistrictTaluka.setText(talukaList.getTalukaName());


        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (activity instanceof SignUpScreen) {
                    ((SignUpScreen) activity).mEdTal.setText(talukaList.getTalukaName());
                    Log.e("talukaClick", "talukaName-" + talukaList.getTalukaName() +
                            " TalukaIddd-" + talukaList.getTalukaId());
                    ((SignUpScreen)activity).dialogDistTaluka.dismiss();
                }else{
                    ((MyAccountScreen) activity).mEdTaluka.setText(talukaList.getTalukaName());
                    Log.e("talukaClick", "talukaName-" + talukaList.getTalukaName() +
                            " TalukaIddd-" + talukaList.getTalukaId());
                    ((MyAccountScreen)activity).dialogDistTaluka.dismiss();
                }


            }
        });


    }

    @Override
    public int getItemCount() {
        return listTaluka.size();
    }

    public int getNumColumns() {
        return this.mNumColumns;
    }

    public void setNumColumns(int numColumns) {
        this.mNumColumns = numColumns;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final AppCompatTextView mTvDistrictTaluka;

        public MyViewHolder(View itemView) {
            super(itemView);
            mTvDistrictTaluka = itemView.findViewById(R.id.tv_district_taluka);
        }
    }
}
