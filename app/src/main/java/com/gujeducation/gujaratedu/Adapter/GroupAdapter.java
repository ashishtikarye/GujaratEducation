package com.gujeducation.gujaratedu.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.gujeducation.R;
import com.gujeducation.gujaratedu.Helper.Functions;
import com.gujeducation.gujaratedu.Model.Group;

import java.util.ArrayList;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.MyViewHolder> {

    private final AppCompatActivity activity;
    public boolean error = false;
    ArrayList<Group> listGroup = new ArrayList();
    Functions mFunction;
    private int mNumColumns = 0;

    public GroupAdapter(AppCompatActivity activity, ArrayList<Group> listGroup) {
        this.activity = activity;
        this.listGroup = listGroup;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup holder, int i) {
        View itemView = LayoutInflater.from(holder.getContext()).inflate(R.layout.row_links, holder, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final MyViewHolder menuItemHolder = holder;
        mFunction = new Functions(activity);
        final Group groupList = listGroup.get(position);

        try {
            menuItemHolder.mTvGroupName.setText("Group Name: "+groupList.getGroup_name());
            menuItemHolder.mTvParticipant.setText("Participant Id: "+groupList.getParticepentsId());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return listGroup.size();
    }

    public int getNumColumns() {
        return this.mNumColumns;
    }

    public void setNumColumns(int numColumns) {
        this.mNumColumns = numColumns;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final AppCompatTextView mTvGroupName;
        private final AppCompatTextView mTvParticipant;

        //private AppCompatImageView mIvBoardMemberPic;

        public MyViewHolder(View itemView) {
            super(itemView);
            mTvGroupName = itemView.findViewById(R.id.tv_links);
            mTvParticipant = itemView.findViewById(R.id.tv_slinks);
            mTvParticipant.setVisibility(View.VISIBLE);

        }
    }
}
