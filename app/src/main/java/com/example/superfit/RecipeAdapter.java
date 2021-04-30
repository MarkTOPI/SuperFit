package com.example.superfit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecipeAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Recipe> data, datafull;

    public RecipeAdapter(Context context, ArrayList<Recipe> data) {
        this.context = context;
        this.data = data;
        datafull = this.data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_listview, null);

        ImageView image = view.findViewById(R.id.recipeImage);
        Picasso.with(context).load(data.get(position).getImageFoot()).placeholder(R.drawable.ic_launcher_foreground).error(R.drawable.ic_launcher_foreground).into(image);
        TextView name = view.findViewById(R.id.nameFood);
        name.setText(data.get(position).getLabel());
        TextView calories = view.findViewById(R.id.caloriesText);
        calories.setText(data.get(position).getCalories());
        TextView proteinsText = view.findViewById(R.id.proteinsText);
        proteinsText.setText(data.get(position).getProtein()+"g");
        TextView fatsText = view.findViewById(R.id.fatsText);
        fatsText.setText(data.get(position).getFats()+"g");
        TextView carbsText = view.findViewById(R.id.carbsText);
        carbsText.setText(data.get(position).getCarbs()+"g");

        return view;
    }
    public Filter getFilter(){
        return exampleFilter;
    }
    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Recipe> filteredList = new ArrayList<>();
            if (context == null || constraint.length() == 0) {
                filteredList = datafull;
            } else {
                String filtered = constraint.toString().toLowerCase().trim();
                for (Recipe item :datafull){
                    if(item.getLabel().toLowerCase().contains(filtered)){
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            data.clear();

            data.addAll((ArrayList<Recipe>)results.values);
            notifyDataSetChanged();
        }
    };

}
