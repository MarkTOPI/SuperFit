package com.example.superfit;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.ArrayList;

public class CustomListAdapter extends ArrayAdapter<Product_List>{

    ArrayList<Product_List>product_list;
    Context context;
    int resourse;

    public CustomListAdapter(@NonNull Context context, int resourse, @NonNull ArrayList<Product_List> product_list){
        super(context, resourse, product_list);
        this.product_list=product_list;
        this.context=context;
        this.resourse=resourse;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }
}
