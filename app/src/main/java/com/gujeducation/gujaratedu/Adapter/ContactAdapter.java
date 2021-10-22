package com.gujeducation.gujaratedu.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.gujeducation.R;
import com.gujeducation.gujaratedu.Model.Contacts;

import java.util.ArrayList;

/**
 * Created by Parsania Hardik on 29-Jun-17.
 */
public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    public static ArrayList<Contacts> imageModelArrayList=new ArrayList();;
    private Context ctx;

    public ContactAdapter(Context ctx, ArrayList<Contacts> imageModelArrayList) {

        inflater = LayoutInflater.from(ctx);
        this.imageModelArrayList = imageModelArrayList;
        this.ctx = ctx;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_contacts, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
        
    }

    @Override
    public void onBindViewHolder(final ContactAdapter.MyViewHolder holder, int position) {

        //holder.checkBox.setText("Checkbox " + position);
        holder.mCbCheckBox.setChecked(imageModelArrayList.get(position).getSelected());
        holder.mTvContactName.setText(imageModelArrayList.get(position).getName());
        holder.mTvContactNo.setText(imageModelArrayList.get(position).getPhone());

       // holder.checkBox.setTag(R.integer.btnplusview, convertView);
        holder.mCbCheckBox.setTag(position);
        holder.mCbCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Integer pos = (Integer) holder.mCbCheckBox.getTag();
               // Toast.makeText(ctx, imageModelArrayList.get(pos).getName() + " clicked!", Toast.LENGTH_SHORT).show();

                if (imageModelArrayList.get(pos).getSelected()) {
                    imageModelArrayList.get(pos).setSelected(false);
                } else {
                    imageModelArrayList.get(pos).setSelected(true);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return imageModelArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        protected AppCompatCheckBox mCbCheckBox;
        private AppCompatTextView mTvContactNo,mTvContactName;

        public MyViewHolder(View itemView) {
            super(itemView);

            mCbCheckBox = (AppCompatCheckBox) itemView.findViewById(R.id.cbCheckbox);
            mTvContactNo = (AppCompatTextView) itemView.findViewById(R.id.tv_contactno);
            mTvContactName = (AppCompatTextView) itemView.findViewById(R.id.tv_contactname);
        }

    }
}


