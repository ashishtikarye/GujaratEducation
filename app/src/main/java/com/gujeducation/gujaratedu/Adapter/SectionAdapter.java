package com.gujeducation.gujaratedu.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.gujeducation.R;
import com.gujeducation.gujaratedu.Activity.ClassScreen;
import com.gujeducation.gujaratedu.Helper.Functions;
import com.gujeducation.gujaratedu.Model.Section;

import java.util.ArrayList;

public class SectionAdapter extends RecyclerView.Adapter<SectionAdapter.MyViewHolder> {

    private final FragmentActivity activity;
    ArrayList<Section> listSection = new ArrayList();
    public boolean error = false;
    Functions mFunction;
    private int mNumColumns = 0;
    private String standard;
    Intent intent;

    public SectionAdapter(FragmentActivity activity, ArrayList<Section> listSection){
        this.activity=activity;
        this.listSection=listSection;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup holder, int i) {
        View itemView = LayoutInflater.from(holder.getContext()).inflate(R.layout.row_section, holder, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final MyViewHolder menuItemHolder = (MyViewHolder) holder;
        //mPreference = activity.getSharedPreferences(activity.getResources().getString(R.string.app_name), MODE_PRIVATE);
        mFunction = new Functions(activity);
        final Section sectionList = listSection.get(position);

        try{
            //Log.e("data",""+sectionList.getSection());
            menuItemHolder.tvSection.setText(sectionList.getSection());
            menuItemHolder.rlSection.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(activity, ""+sectionList.getStandard(), Toast.LENGTH_SHORT).show();
                    mFunction.setSectionId((int)sectionList.getSectionId());
                    mFunction.setStandardId((int)sectionList.getStandardId());
                    intent = new Intent(activity, ClassScreen.class);
                    intent.putExtra("class",sectionList.getStandard());
                    activity.startActivity(intent);
                }
            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    @Override
    public int getItemCount() {
        return listSection.size();
    }
    public int getNumColumns() {
        return this.mNumColumns;
    }
    public void setNumColumns(int numColumns) {
        this.mNumColumns = numColumns;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout rlSection;
        private AppCompatTextView tvSection;
        //private AppCompatImageView mIvBoardMemberPic;

        public MyViewHolder(View itemView) {
            super(itemView);
            rlSection = (RelativeLayout) itemView.findViewById(R.id.rl_sectionid);
            tvSection = (AppCompatTextView) itemView.findViewById(R.id.tv_section);
        }
    }

}
