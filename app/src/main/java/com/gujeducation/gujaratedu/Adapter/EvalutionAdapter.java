package com.gujeducation.gujaratedu.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.gujeducation.BuildConfig;
import com.gujeducation.R;
import com.gujeducation.gujaratedu.Activity.PdfScreen;
import com.gujeducation.gujaratedu.Helper.Functions;
import com.gujeducation.gujaratedu.Model.Evalution;
import com.gujeducation.gujaratedu.Model.NewsCircular;

import java.util.ArrayList;

public class EvalutionAdapter extends RecyclerView.Adapter<EvalutionAdapter.MyViewHolder> {

    private final AppCompatActivity activity;
    ArrayList<Evalution> listEvalution = new ArrayList();
    public boolean error = false;
    Functions mFunction;
    private int mNumColumns = 0;
    Intent intent;

    public EvalutionAdapter(AppCompatActivity activity, ArrayList<Evalution> listEvalution) {
        this.activity = activity;
        this.listEvalution = listEvalution;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup holder, int i) {
        View itemView = LayoutInflater.from(holder.getContext()).inflate(R.layout.row_sublink, holder, false);
        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final MyViewHolder menuItemHolder = (MyViewHolder) holder;
        //mPreference = activity.getSharedPreferences(activity.getResources().getString(R.string.app_name), MODE_PRIVATE);
        mFunction = new Functions(activity);
        final Evalution evalutionList = listEvalution.get(position);

        try {
            //Log.e("position", "-->" + position);

            int pos = position + 1;
            //Log.e("pos", "-->" + pos);

            menuItemHolder.tvPatrakName.setText(evalutionList.getPatrak());

            menuItemHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(activity, PdfScreen.class);
                    i.putExtra("Name", evalutionList.getPdf_name());
                    i.putExtra("PDF", evalutionList.getPdf());
                    activity.startActivity(i);

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }



    }
    @Override
    public int getItemCount() {
        return listEvalution.size();
    }
    public int getNumColumns() {
        return this.mNumColumns;
    }
    public void setNumColumns(int numColumns) {
        this.mNumColumns = numColumns;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final AppCompatTextView tvPatrakName;
        //private AppCompatImageView mIvBoardMemberPic;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvPatrakName = (AppCompatTextView) itemView.findViewById(R.id.tv_sublink_name);

        }
    }

}
