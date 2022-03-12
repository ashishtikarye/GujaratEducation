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
import com.gujeducation.gujaratedu.Helper.Connection;
import com.gujeducation.gujaratedu.Helper.Functions;
import com.gujeducation.gujaratedu.Interface.OnResult;
import com.gujeducation.gujaratedu.Model.District;
import com.gujeducation.gujaratedu.Model.Taluka;
import com.gujeducation.gujaratedu.ServerAPIs.APIs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DistrictAdapter extends RecyclerView.Adapter<DistrictAdapter.MyViewHolder> implements OnResult {

    private final AppCompatActivity activity;
    public boolean error = false;
    ArrayList<District> listDistrict = new ArrayList();
    Functions mFunction;
    int districtId = 0;
    private int mNumColumns = 0;

    public DistrictAdapter(AppCompatActivity activity, ArrayList<District> listDistrict) {
        this.activity = activity;
        this.listDistrict = listDistrict;

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
        final District districtList = listDistrict.get(position);

        try {
            menuItemHolder.mTvDistrictTaluka.setText(districtList.getDisttrictName());


        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //Toast.makeText(activity, "DistrictId - "+districtList.getDisttrictId(), Toast.LENGTH_SHORT).show();
                Log.e("Adapter-1-", "distId->" + districtList.getDisttrictId());

                districtId = districtList.getDisttrictId();

                if (activity instanceof SignUpScreen) {
                    Log.e("From", " SignupScreen");
                    SignUpScreen.dialogDistTaluka.dismiss();
                    ((SignUpScreen) activity).mEdDist.setText(districtList.getDisttrictName());
                    ((SignUpScreen) activity).mEdTal.setText("");
                    if (Functions.knowInternetOn(activity)) {
                        //Toast.makeText(activity, "districtIdED - "+districtList.getDisttrictId(), Toast.LENGTH_SHORT).show();
                        APIs.getTalukaList((AppCompatActivity) activity, DistrictAdapter.this::onResult, districtList.getDisttrictId());
                    } else {
                        Functions.showInternetAlert(activity);
                    }
                } else {
                    Log.e("From", " MyAccountScreen");
                    MyAccountScreen.dialogDistTaluka.dismiss();
                    ((MyAccountScreen) activity).mEdDistrict.setText(districtList.getDisttrictName());
                    ((MyAccountScreen) activity).mEdTaluka.setText("");
                    if (Functions.knowInternetOn(activity)) {
                        //Toast.makeText(activity, "districtIdED - "+districtList.getDisttrictId(), Toast.LENGTH_SHORT).show();
                        APIs.getTalukaList((AppCompatActivity) activity, DistrictAdapter.this::onResult, districtList.getDisttrictId());
                    } else {
                        Functions.showInternetAlert(activity);
                    }
                }


            }
        });

        if (activity instanceof SignUpScreen) {
            Log.e("started","call SignUpScreen");
            ((SignUpScreen) activity).mEdTal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((SignUpScreen) activity).showDistrictTalukaPopup("t");
                }
            });
        } else {
            Log.e("started","call MyAccountScreen");
            ((MyAccountScreen) activity).mEdTaluka.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((MyAccountScreen) activity).showDistrictTalukaPopup("t");
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return listDistrict.size();
    }

    public int getNumColumns() {
        return this.mNumColumns;
    }

    public void setNumColumns(int numColumns) {
        this.mNumColumns = numColumns;
    }

    @Override
    public void onResult(JSONObject jobjWhole) {
        try {
            if (jobjWhole != null) {
                JSONObject jObj = jobjWhole.optJSONObject(Connection.TAG_DATA);
                int strStatus = jObj.optInt("success");
                String strApiName = jObj.optString("APIName");
                String strMessage = jObj.optString("message");
                Log.e("onResult", "strApiName-" + strApiName + " strStatus-" + strStatus);

                if (activity instanceof SignUpScreen) {
                    ((SignUpScreen) activity).listArrTaluka.clear();
                    if (strApiName.equalsIgnoreCase("getTaluka")) {
                        if (strStatus != 0) {
                            JSONArray jArrayTextSub = jObj.getJSONArray("taluka");
                            if (jArrayTextSub.length() > 0) {
                                for (int i = 0; i < jArrayTextSub.length(); i++) {
                                    try {
                                        JSONObject obj = jArrayTextSub.getJSONObject(i);
                                        ((SignUpScreen) activity).listArrTaluka.add(new Taluka(
                                                obj.optInt("disttrictId"),
                                                obj.optInt("talukaId"),
                                                obj.optString("talukaName")
                                        ));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                //Log.e("talukasizze22","listArrTalukaSize-"+((SignUpScreen)activity).listArrTaluka.size());
                            }
                            //Log.e("talukasizze","listArrTalukaSize-"+((SignUpScreen)activity).listArrTaluka.size());

                        } else {
                            Functions.ToastUtility(activity, strMessage);
                            //recyclerViewLanguage.setVisibility(View.GONE);
                        }
                    }
                } else {
                    ((MyAccountScreen) activity).listArrTaluka.clear();
                    if (strApiName.equalsIgnoreCase("getTaluka")) {
                        if (strStatus != 0) {
                            JSONArray jArrayTextSub = jObj.getJSONArray("taluka");
                            if (jArrayTextSub.length() > 0) {
                                for (int i = 0; i < jArrayTextSub.length(); i++) {
                                    try {
                                        JSONObject obj = jArrayTextSub.getJSONObject(i);
                                        ((MyAccountScreen) activity).listArrTaluka.add(new Taluka(
                                                obj.optInt("disttrictId"),
                                                obj.optInt("talukaId"),
                                                obj.optString("talukaName")
                                        ));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                //Log.e("talukasizze22","listArrTalukaSize-"+((SignUpScreen)activity).listArrTaluka.size());
                            }
                            //Log.e("talukasizze","listArrTalukaSize-"+((SignUpScreen)activity).listArrTaluka.size());

                        } else {
                            Functions.ToastUtility(activity, strMessage);
                            //recyclerViewLanguage.setVisibility(View.GONE);
                        }
                    }
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final AppCompatTextView mTvDistrictTaluka;

        public MyViewHolder(View itemView) {
            super(itemView);
            mTvDistrictTaluka = itemView.findViewById(R.id.tv_district_taluka);
        }
    }
}
