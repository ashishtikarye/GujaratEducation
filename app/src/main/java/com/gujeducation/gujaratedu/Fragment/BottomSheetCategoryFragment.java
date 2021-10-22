package com.gujeducation.gujaratedu.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.gujeducation.R;
import com.gujeducation.gujaratedu.Adapter.SectionAdapter;
import com.gujeducation.gujaratedu.Model.Section;

import java.util.ArrayList;

public class BottomSheetCategoryFragment extends BottomSheetDialogFragment {

    /*private LinearLayout std_1_2_categoryLayout,std_11_12_categoryLayout;
    private RelativeLayout rlPragna,rlNonPragna,rlArts,rlCommerce,rlScience;*/
    private AppCompatImageView btnCancle;
    Intent intent;
    RecyclerView recyclerSection;
    LinearLayoutManager mLayoutManager;
    private ArrayList<Section> listArrSection = new ArrayList<Section>();
    SectionAdapter mSectionAdapter;
    String standard;


    public BottomSheetCategoryFragment(ArrayList<Section> listArrSection){
        this.listArrSection = listArrSection;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_category,container,false);
        recyclerSection = view.findViewById(R.id.recyclerview_section);
        recyclerSection.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerSection.setLayoutManager(mLayoutManager);
        standard = getTag();
        if(listArrSection.size() != 0){
            mSectionAdapter = new SectionAdapter(getActivity(),listArrSection);
            recyclerSection.setAdapter(mSectionAdapter);
            mSectionAdapter.notifyDataSetChanged();
            recyclerSection.setVisibility(View.VISIBLE);
            //Log.e("Data",""+listArrSection.get(0).getSection());
        }

        /*std_1_2_categoryLayout = view.findViewById(R.id.std_1_2_category_layout);
        std_11_12_categoryLayout = view.findViewById(R.id.std_11_12_category_layout);

        rlPragna = view.findViewById(R.id.rl_pragna);
        rlNonPragna = view.findViewById(R.id.rl_nonpragna);
        rlArts = view.findViewById(R.id.rl_arts);
        rlCommerce = view.findViewById(R.id.rl_commerce);
        rlScience = view.findViewById(R.id.rl_science);
*/


        btnCancle = view.findViewById(R.id.iv_cancle);

        /*if(getTag() == "std1" || getTag() == "std2"){
            std_11_12_categoryLayout.setVisibility(View.GONE);
        }
        else if(getTag() == "std11" || getTag() == "std12"){
            std_1_2_categoryLayout.setVisibility(View.GONE);
        }

        rlPragna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getActivity(), ClassScreen.class);
                if(getTag() == "std1") {
                    intent.putExtra("class", "Class 1 (Pragna)");
                }
                else if (getTag() == "std2"){
                    intent.putExtra("class","Class 2 (Pragna)");
                }
                startActivity(intent);
            }
        });

        rlNonPragna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getActivity(), ClassScreen.class);
                if(getTag() == "std1") {
                    intent.putExtra("class", "Class 1 (Non-Pragna)");
                }
                else if (getTag() == "std2"){
                    intent.putExtra("class","Class 2 (Non-Pragna)");
                }
                startActivity(intent);
            }
        });

        rlArts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getActivity(), ClassScreen.class);
                if(getTag() == "std11") {
                    intent.putExtra("class", "Class 11 (Arts)");
                }
                else if (getTag() == "std12"){
                    intent.putExtra("class","Class 12 (Arts)");
                }
                startActivity(intent);
            }
        });

        rlCommerce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getActivity(), ClassScreen.class);
                if(getTag() == "std11") {
                    intent.putExtra("class", "Class 11 (Commerce)");
                }
                else if (getTag() == "std12"){
                    intent.putExtra("class","Class 12 (Commerce)");
                }
                startActivity(intent);
            }
        });

        rlScience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getActivity(), ClassScreen.class);
                if(getTag() == "std11") {
                    intent.putExtra("class", "Class 11 (Science)");
                }
                else if (getTag() == "std12"){
                    intent.putExtra("class","Class 12 (Science)");
                }
                startActivity(intent);
            }
        });*/

        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return view;
    }
}
