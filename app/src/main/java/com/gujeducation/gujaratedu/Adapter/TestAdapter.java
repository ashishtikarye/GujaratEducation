package com.gujeducation.gujaratedu.Adapter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.gujeducation.R;
import com.gujeducation.gujaratedu.Helper.Functions;
import com.gujeducation.gujaratedu.Model.Quiz;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.MyViewHolder> {

    private final AppCompatActivity activity;
    public boolean error = false;
    ArrayList<Quiz> listQuiz = new ArrayList<>();
    Functions mFunction;
    SharedPreferences mPreference;
    Intent intent;
    private int mNumColumns = 0;

    public TestAdapter(AppCompatActivity activity, ArrayList<Quiz> listQuiz) {
        this.activity = activity;
        this.listQuiz = listQuiz;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup holder, int viewType) {
        // Log.e("getMobileScreenSize", mFunction.getMobileScreenSize(activity) + "");
        /*if (mFunction.getMobileScreenSize(activity) > 5) {
            ITEMS_PER_PAGE = 6;
        } else {
            ITEMS_PER_PAGE = 4;
        }*/
        View itemView = LayoutInflater.from(holder.getContext()).inflate(R.layout.row_quiz, holder, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        //MyViewHolder menuItemHolder = (MyViewHolder) holder;
        final MyViewHolder menuItemHolder = holder;

        mPreference = activity.getSharedPreferences(activity.getResources().getString(R.string.app_name), MODE_PRIVATE);
        mFunction = new Functions(activity);
        // getUserID = mFunction.getUserId();
        final Quiz txQuiz = listQuiz.get(position);

        /*if (getUserID.equalsIgnoreCase("")) {
            getUserID = "0";
        }*/

        try {

            menuItemHolder.mTvQuestion.setText(txQuiz.getQuestion());
            menuItemHolder.mTvAnswer_A.setText(txQuiz.getAns_A());
            menuItemHolder.mTvAnswer_B.setText(txQuiz.getAns_B());
            menuItemHolder.mTvAnswer_C.setText(txQuiz.getAns_C());
            menuItemHolder.mTvAnswer_D.setText(txQuiz.getAns_D());
            String correctAns = txQuiz.getAns_correct();
            Log.e("correctAns", "-->" + correctAns);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return listQuiz.size();
    }

    public int getNumColumns() {
        return this.mNumColumns;
    }

    public void setNumColumns(int numColumns) {
        this.mNumColumns = numColumns;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView mTvQuestion;
        private AppCompatRadioButton mTvAnswer_A, mTvAnswer_B, mTvAnswer_C, mTvAnswer_D;


        public MyViewHolder(View itemView) {
            super(itemView);
           /* mTvQuestion = itemView.findViewById(R.id.tvquestion);
            mTvAnswer_A = itemView.findViewById(R.id.answer_a);
            mTvAnswer_B = itemView.findViewById(R.id.answer_b);
            mTvAnswer_C = itemView.findViewById(R.id.answer_c);
            mTvAnswer_D = itemView.findViewById(R.id.answer_d);*/

        }
    }
}
