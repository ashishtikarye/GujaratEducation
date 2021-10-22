package com.gujeducation.gujaratedu.Adapter;

import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.gujeducation.R;
import com.gujeducation.gujaratedu.Helper.Functions;
import com.gujeducation.gujaratedu.Model.Creditors;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class CreditorsListAdapter extends RecyclerView.Adapter<CreditorsListAdapter.MyViewHolder> {

    private final AppCompatActivity activity;
    public boolean error = false;
    ArrayList<Creditors> listCreditors = new ArrayList<>();
    Functions mFunction;
    SharedPreferences mPreference;
    private int mNumColumns = 0;

    public CreditorsListAdapter(AppCompatActivity activity, ArrayList<Creditors> listCreditors) {
        this.activity = activity;
        this.listCreditors = listCreditors;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup holder, int viewType) {
        View itemView = LayoutInflater.from(holder.getContext()).inflate(R.layout.row_creditors, holder, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        //MyViewHolder menuItemHolder = (MyViewHolder) holder;
        final MyViewHolder menuItemHolder = holder;
        mPreference = activity.getSharedPreferences(activity.getResources().getString(R.string.app_name), MODE_PRIVATE);
        mFunction = new Functions(activity);
        final Creditors CreditorsList = listCreditors.get(position);


        try {
            menuItemHolder.mTvCreditorName.setText(CreditorsList.getCreditorsName());
            menuItemHolder.mTvCreditorTitle.setText(CreditorsList.getCreditorsTitle());
            menuItemHolder.mTvCreditorDesignation.setText(CreditorsList.getCreditorsDesignation());
            menuItemHolder.mTvCreditorDescription.setText(CreditorsList.getCreditorsDescription());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return listCreditors.size();
    }

    public int getNumColumns() {
        return this.mNumColumns;
    }

    public void setNumColumns(int numColumns) {
        this.mNumColumns = numColumns;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView mTvCreditorName, mTvCreditorTitle, mTvCreditorDesignation, mTvCreditorDescription;

        public MyViewHolder(View itemView) {
            super(itemView);
            mTvCreditorName = itemView.findViewById(R.id.tv_creditorname);
            mTvCreditorTitle = itemView.findViewById(R.id.tv_creditortitle);
            mTvCreditorDesignation = itemView.findViewById(R.id.tv_creditordesignation);
            mTvCreditorDescription = itemView.findViewById(R.id.tv_creditordescription);

        }
    }
}
