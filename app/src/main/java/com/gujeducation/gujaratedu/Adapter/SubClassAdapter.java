package com.gujeducation.gujaratedu.Adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.gujeducation.R;
import com.gujeducation.gujaratedu.Activity.ClassScreen;
import com.gujeducation.gujaratedu.Fragment.BottomSheetCategoryFragment;
import com.gujeducation.gujaratedu.Helper.Connection;
import com.gujeducation.gujaratedu.Helper.Functions;
import com.gujeducation.gujaratedu.Interface.OnResult;
import com.gujeducation.gujaratedu.Model.Section;
import com.gujeducation.gujaratedu.Model.SubClass;
import com.gujeducation.gujaratedu.ServerAPIs.APIs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.view.Gravity.CENTER;

public class SubClassAdapter extends RecyclerView.Adapter<SubClassAdapter.MyViewHolder> implements OnResult {

    public static String classType;
    private final AppCompatActivity activity;
    public boolean error = false;
    ArrayList<SubClass> listSubClass;
    ArrayList<Section> listSection;
    Functions mFunction;
    Intent intent;
    BottomSheetCategoryFragment bottomSheetDialog;
    private int mNumColumns = 0;

    public SubClassAdapter(AppCompatActivity activity, ArrayList<SubClass> listSubClass) {
        this.activity = activity;
        this.listSubClass = listSubClass;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup holder, int viewType) {

        View itemView = LayoutInflater.from(holder.getContext()).inflate(R.layout.row_sub_class, holder, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final MyViewHolder menuItemHolder = holder;
        mFunction = new Functions(activity);
        final SubClass classList = listSubClass.get(position);
        try {
            if (classList.getClassName().length() > 2) {
                menuItemHolder.tvClassVal.setVisibility(View.GONE);
                menuItemHolder.tvClassName.setText(classList.getClassName());
                menuItemHolder.tvClassVal.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
                menuItemHolder.tvClassName.setGravity(CENTER);
            } else {
                menuItemHolder.tvClassName.setText(R.string._class);
                menuItemHolder.tvClassVal.setText(classList.getClassName());
            }

            menuItemHolder.llClass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //standard set in sectionAdapter
                    //Log.e("getClassName", "" + classList.getClassName());
                    Log.e("getIs_identify", "" + classList.getIs_identify());
                    mFunction.setStandardName(classList.getClassName());
                    mFunction.setStandardIdentify(classList.getIs_identify());
                    callApi(classList.getClassId());
                    bottomSheetDialog = new BottomSheetCategoryFragment(listSection);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return listSubClass.size();
    }

    public int getNumColumns() {
        return this.mNumColumns;
    }

    public void setNumColumns(int numColumns) {
        this.mNumColumns = numColumns;
    }

    private void callApi(int classId) {
        if (Functions.knowInternetOn(activity)) {
            APIs.getSection(activity, this, mFunction.getPrefMediumId(), classId);
        } else {
            Functions.showInternetAlert(activity);
        }
    }

    @Override
    public void onResult(JSONObject jobjWhole) {
        try {
            if (jobjWhole != null) {
                JSONObject jObj = jobjWhole.optJSONObject(Connection.TAG_DATA);
                int strStatus = jObj.optInt("success");
                String strMessage = jObj.optString("message");

                if (strStatus != 0) {
                    JSONArray jArraySection = jObj.getJSONArray("section");
                    if (jArraySection.length() > 0) {
                        listSection.clear();
                        for (int i = 0; i < jArraySection.length(); i++) {
                            try {
                                JSONObject obj = jArraySection.getJSONObject(i);
                                listSection.add(new Section(
                                        obj.optInt("sectionId"),
                                        obj.optInt("mediumId"),
                                        obj.optInt("standardId"),
                                        obj.optString("standard"),
                                        obj.optString("section")
                                ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        final Section sectionList = listSection.get(0);
                        if (listSection.size() > 1) {
                            bottomSheetDialog.setCancelable(false);
                            bottomSheetDialog.show(activity.getSupportFragmentManager(), sectionList.getStandard());
                            //Toast.makeText(this, "" + sectionList.getStandard(), Toast.LENGTH_SHORT).show();
                        } else if (listSection.size() == 1) {
                            mFunction.setSectionId(sectionList.getSectionId());
                            mFunction.setStandardId(sectionList.getStandardId());
                            intent = new Intent(activity, ClassScreen.class);
                            intent.putExtra("class", sectionList.getStandard());
                            activity.startActivity(intent);


                        }
                    }
                } else {
                    Functions.ToastUtility(activity, strMessage);
                    //recyclerViewLanguage.setVisibility(View.GONE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final AppCompatTextView tvClassName;
        private final AppCompatTextView tvClassVal;
        private final LinearLayout llClass;

        public MyViewHolder(View itemView) {
            super(itemView);
            mFunction = new Functions(activity);
            listSection = new ArrayList<>();
            llClass = itemView.findViewById(R.id.ll_class);
            tvClassName = itemView.findViewById(R.id.tv_class);
            tvClassVal = itemView.findViewById(R.id.tv_class_val);
        }
    }
}
