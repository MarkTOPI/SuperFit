package com.example.superfit.recipe;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.superfit.R;

import java.util.ArrayList;

public class CustomAdapterRecipes extends BaseAdapter {

    ArrayList<RecipeObject> recipes = new ArrayList<>();
    LayoutInflater inflater;
    Context context;

    public CustomAdapterRecipes(Context context, ArrayList<RecipeObject> recipes){
        this.context = context;
        inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.recipes = recipes;
    }

    @Override
    public int getCount() {
        return recipes.size();
    }

    @Override
    public Object getItem(int position) {
        return recipes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if(view == null){
            view = inflater.inflate(R.layout.custom_listview, null, false);
        }

        RecipeObject recipe = recipes.get(position);

        ((TextView) view.findViewById(R.id.nameFood)).setText(recipe.getLabel());
        ((TextView) view.findViewById(R.id.caloriesText)).setText(recipe.getCalories());
        ((TextView) view.findViewById(R.id.fatsText)).setText(recipe.getDigest()[0]);
        ((TextView) view.findViewById(R.id.proteinsText)).setText(recipe.getDigest()[1]);
        ((TextView) view.findViewById(R.id.carbsText)).setText(recipe.getDigest()[2]);

        try {
            ((ImageView) view.findViewById(R.id.recipeImage)).setImageBitmap(recipe.getImage());
        } catch (Exception e) {
            Log.e("CustomAdapter", " " + e.getMessage());
        }

        return view;
    }
}
