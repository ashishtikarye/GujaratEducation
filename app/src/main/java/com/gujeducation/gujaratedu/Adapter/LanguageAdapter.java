package com.gujeducation.gujaratedu.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.gujeducation.R;
import com.gujeducation.gujaratedu.Activity.HomeScreen;
import com.gujeducation.gujaratedu.Activity.SignInScreen;
import com.gujeducation.gujaratedu.Helper.Functions;
import com.gujeducation.gujaratedu.Model.Language;

import java.util.ArrayList;

public class LanguageAdapter extends RecyclerView.Adapter<LanguageAdapter.MyViewHolder> {

    public static String mediumId_guest = "";
    private final AppCompatActivity activity;
    public boolean error = false;
    ArrayList<Language> listLanguage = new ArrayList();
    Functions mFunction;
    Intent intent;
    private int mNumColumns = 0;

    public LanguageAdapter(AppCompatActivity activity, ArrayList<Language> listLanguage) {
        this.activity = activity;
        this.listLanguage = listLanguage;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup holder, int i) {
        View itemView = LayoutInflater.from(holder.getContext()).inflate(R.layout.row_language, holder, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final MyViewHolder menuItemHolder = (MyViewHolder) holder;
        //mPreference = activity.getSharedPreferences(activity.getResources().getString(R.string.app_name), MODE_PRIVATE);
        mFunction = new Functions(activity);
        final Language languageList = listLanguage.get(position);

        try {
            /*menuItemHolder.tvTitle.setText(daysList.getTitile());
            menuItemHolder.tvDate.setText(daysList.getDate());*/
            menuItemHolder.tvLanguage.setText(languageList.getLanguageName().trim());
            //Log.e("LangAdptr","-->"+languageList.getLanguageId()+"---->"+languageList.getLanguageName());

            if (languageList.getLanguageId() == 1) {
                //Toast.makeText(activity, "GUJARATI", Toast.LENGTH_SHORT).show();
                menuItemHolder.ivLanguage.setImageResource(R.drawable.lang_gujarati);
                menuItemHolder.rlvLanguage.setBackgroundResource(R.drawable.shape_gujarati);
            } else if (languageList.getLanguageId() == 2) {
                menuItemHolder.ivLanguage.setImageResource(R.drawable.lang_english);
                menuItemHolder.rlvLanguage.setBackgroundResource(R.drawable.shape_english);
            } else if (languageList.getLanguageId() == 3) {
                menuItemHolder.ivLanguage.setImageResource(R.drawable.lang_hindi);
                menuItemHolder.rlvLanguage.setBackgroundResource(R.drawable.shape_hindi);
            } else if (languageList.getLanguageId() == 4) {
                menuItemHolder.ivLanguage.setImageResource(R.drawable.lang_marathi);
                menuItemHolder.rlvLanguage.setBackgroundResource(R.drawable.shape_marathi);
            } else if (languageList.getLanguageId() == 5) {
                menuItemHolder.ivLanguage.setImageResource(R.drawable.lang_odia);
                menuItemHolder.rlvLanguage.setBackgroundResource(R.drawable.shape_odiya);
            } else if (languageList.getLanguageId() == 6) {
                menuItemHolder.ivLanguage.setImageResource(R.drawable.lang_telugu);
                menuItemHolder.rlvLanguage.setBackgroundResource(R.drawable.shape_telugu);
            } else if (languageList.getLanguageId() == 7) {
                menuItemHolder.ivLanguage.setImageResource(R.drawable.lang_urdu);
                menuItemHolder.rlvLanguage.setBackgroundResource(R.drawable.shape_urdu);
            }

            menuItemHolder.rlvLanguage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(activity, languageList.getLanguageName(), Toast.LENGTH_SHORT).show();
                    if (languageList.getLanguageId() == 1) {
                        mFunction.SetPrefLanguage("gu");
                        com.gujeducation.gujaratedu.Helper.Language.set(activity, "gu");
                        mFunction.SetPrefMediumId(1);
                    }
                    if (languageList.getLanguageId() == 2) {
                        mFunction.SetPrefLanguage("en");
                        com.gujeducation.gujaratedu.Helper.Language.set(activity, "en");
                        mFunction.SetPrefMediumId(2);
                    }
                    if (languageList.getLanguageId() == 3) {
                        mFunction.SetPrefLanguage("hi");
                        com.gujeducation.gujaratedu.Helper.Language.set(activity, "hi");
                        mFunction.SetPrefMediumId(3);
                    }
                    if (languageList.getLanguageId() == 4) {
                        mFunction.SetPrefLanguage("mr");
                        com.gujeducation.gujaratedu.Helper.Language.set(activity, "mr");
                        mFunction.SetPrefMediumId(4);
                    }
                    if (languageList.getLanguageId() == 5) {
                        mFunction.SetPrefLanguage("od");
                        com.gujeducation.gujaratedu.Helper.Language.set(activity, "od");
                        mFunction.SetPrefMediumId(5);
                    }
                    if (languageList.getLanguageId() == 6) {
                        mFunction.SetPrefLanguage("tl");
                        com.gujeducation.gujaratedu.Helper.Language.set(activity, "tl");
                        mFunction.SetPrefMediumId(6);
                    }
                    if (languageList.getLanguageId() == 7) {
                        mFunction.SetPrefLanguage("ur");
                        com.gujeducation.gujaratedu.Helper.Language.set(activity, "ur");
                        mFunction.SetPrefMediumId(7);
                    }


                    if (mFunction.getPrefUserId() != 0) {
                        intent = new Intent(activity, HomeScreen.class);
                        activity.startActivity(intent);
                    } else {
                        intent = new Intent(activity, SignInScreen.class);
                        activity.startActivity(intent);
                    }

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return listLanguage.size();
    }

    public int getNumColumns() {
        return this.mNumColumns;
    }

    public void setNumColumns(int numColumns) {
        this.mNumColumns = numColumns;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final AppCompatTextView tvLanguage;
        private final RelativeLayout rlvLanguage;
        private final AppCompatImageView ivLanguage;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvLanguage = (AppCompatTextView) itemView.findViewById(R.id.tvlanguage);
            rlvLanguage = (RelativeLayout) itemView.findViewById(R.id.rlv_language);
            ivLanguage = (AppCompatImageView) itemView.findViewById(R.id.ivlanguage);
            //mIvBoardMemberPic = (AppCompatImageView) itemView.findViewById(R.id.iv_bmemberpic);
            //mIvBoardMemberPic = (CircularImageView) itemView.findViewById(R.id.iv_bmemberpic);
        }
    }
}
