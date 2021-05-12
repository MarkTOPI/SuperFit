package com.example.superfit.recipe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.superfit.R;

import java.util.ArrayList;

public class CustomAdapterIngridients extends BaseAdapter {

    ArrayList<String> ingridients;
    Context context;
    LayoutInflater inflater;

    CustomAdapterIngridients(Context context, ArrayList<String> ingridients){
        this.ingridients = ingridients;
        this.context = context;
        inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return ingridients.size();
    }

    @Override
    public Object getItem(int position) {
        return ingridients.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if(view == null){
            view = inflater.inflate(R.layout.ingredient_item, null, false);
        }

        String ingridient = ingridients.get(position);
        ((TextView) view.findViewById(R.id.ingridient)).setText(ingridient);

        return view;
    }
}
