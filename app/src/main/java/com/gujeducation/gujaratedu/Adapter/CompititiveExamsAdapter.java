package com.gujeducation.gujaratedu.Adapter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.PictureDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gujeducation.R;
import com.gujeducation.gujaratedu.Activity.PaperListScreen;
import com.gujeducation.gujaratedu.Activity.PdfScreen;
import com.gujeducation.gujaratedu.Helper.Functions;
import com.gujeducation.gujaratedu.Model.CompititveExams;

import java.util.ArrayList;

public class CompititiveExamsAdapter extends RecyclerView.Adapter<CompititiveExamsAdapter.MyViewHolder> {

    private final AppCompatActivity activity;
    public boolean error = false;
    ArrayList<CompititveExams> listComptExams = new ArrayList<>();
    Functions mFunction;
    SharedPreferences mPreference;
    Intent intent;
    private int mNumColumns = 0;
    private RequestBuilder<PictureDrawable> requestBuilder;

    public CompititiveExamsAdapter(AppCompatActivity activity, ArrayList<CompititveExams> listComptExams) {
        this.activity = activity;
        this.listComptExams = listComptExams;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup holder, int viewType) {
        // Log.e("getMobileScreenSize", mFunction.getMobileScreenSize(activity) + "");
        /*if (mFunction.getMobileScreenSize(activity) > 5) {
            ITEMS_PER_PAGE = 6;
        } else {
            ITEMS_PER_PAGE = 4;
        }*/
        View itemView = LayoutInflater.from(holder.getContext()).inflate(R.layout.row_compititve_exams, holder, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final MyViewHolder menuItemHolder = holder;
        //mPreference = activity.getSharedPreferences(activity.getResources().getString(R.string.app_name), MODE_PRIVATE);
        mFunction = new Functions(activity);
        final CompititveExams comptExamsList = listComptExams.get(position);

        try {
            menuItemHolder.mTvComptExamTitle.setText(comptExamsList.getCompititiveExamName());
            menuItemHolder.mTvComptExamClassName.setText(comptExamsList.getClassName());
            Glide.with(activity)
                    .load(comptExamsList.getClassImage())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.IMMEDIATE).dontAnimate()
                    .into(menuItemHolder.mIvComptExam);

        } catch (Exception e) {
            e.printStackTrace();
        }
        menuItemHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(activity, PaperListScreen.class);
                intent.putExtra("exam_id", comptExamsList.getCompititiveExamId());
                intent.putExtra("exam_name", comptExamsList.getCompititiveExamName());
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listComptExams.size();
    }

    public int getNumColumns() {
        return this.mNumColumns;
    }

    public void setNumColumns(int numColumns) {
        this.mNumColumns = numColumns;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final AppCompatTextView mTvComptExamClassName;
        private final AppCompatTextView mTvComptExamTitle;
        private final AppCompatImageView mIvComptExam;

        public MyViewHolder(View itemView) {
            super(itemView);
            mTvComptExamTitle = itemView.findViewById(R.id.tv_compt_exams_title);
            mTvComptExamClassName = itemView.findViewById(R.id.tv_compt_exams_class);
            mIvComptExam = itemView.findViewById(R.id.iv_compt_exmas);
        }
    }
}
