package com.example.superfit.recipe;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.superfit.R;

import org.json.JSONException;

public class RecipeScreen extends AppCompatActivity {

    CustomAdapterIngridients adapter;
    ListView ingredientListView;
    LinearLayout backImg;
    RecipeObject recipe;
    TextView label;
    TextView calories;
    TextView protein;
    TextView fat;
    TextView cards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_AppCompat_DayNight_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_screen);


        ingredientListView = findViewById(R.id.ingridientsLV);
        backImg = findViewById(R.id.backgroundPreview);
        label = findViewById(R.id.label);
        calories = findViewById(R.id.calories);
        protein = findViewById(R.id.protein);
        fat = findViewById(R.id.fat);
        cards = findViewById(R.id.carbs);

        recipe = (RecipeObject) getIntent().getParcelableExtra("Recipe");

        Drawable draw = new BitmapDrawable(getResources(), recipe.getImage());
        backImg.setBackground(draw);

        label.setText(recipe.getLabel());
        calories.setText(recipe.getCalories());
        protein.setText(recipe.getDigest()[0]);
        fat.setText(recipe.getDigest()[1]);
        cards.setText(recipe.getDigest()[2]);
        try {
            adapter = new CustomAdapterIngridients(this, recipe.getIngridientLines());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ingredientListView.setAdapter(adapter);
    }
    public void goBack(View view){
        Intent intent = new Intent(this, RecipesList.class);
        startActivity(intent);
    }
}