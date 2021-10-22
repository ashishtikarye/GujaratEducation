package com.gujeducation.gujaratedu.Model;

import android.content.Context;

import com.gujeducation.R;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

public class Class {
    int category;
    ArrayList<SubClass> listClass;
    Context context;
    public Class(Context context){
        this.context = context;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public ArrayList<SubClass> getListClass() {
        return listClass;
    }

    public void setListClass(ArrayList<SubClass> listClass) {
        this.listClass = listClass;
    }

    public String getCategoryName(){

        if(getCategory() == 1){
            return context.getString(R.string.pre_primary);
        }
        if(getCategory() == 2){
            return context.getString(R.string.early_education);
        }
        if(getCategory() == 3){
            return context.getString(R.string.high_primary);
        }
        if(getCategory() == 4){
            return context.getString(R.string.secondary);
        }
        if(getCategory() == 5){
            return context.getString(R.string.high_secondary);
        }
        return "";
    }

    public static <T> ArrayList<T> removeDuplicates(ArrayList<T> list)
    {

        // Create a new LinkedHashSet
        Set<T> set = new LinkedHashSet<>();

        // Add the elements to set
        set.addAll(list);

        // Clear the list
        list.clear();

        // add the elements of set
        // with no duplicates to the list
        list.addAll(set);

        // return the list
        return list;
    }
}
