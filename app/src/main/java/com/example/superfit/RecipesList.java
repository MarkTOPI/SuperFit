package com.example.superfit;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class RecipesList extends AppCompatActivity {

    CheckBox balancedButton;
    CheckBox highFiberButton;
    CheckBox highProteinButton;

    ListView recipesList;
    private RecipeAdapter adapter;
    private ArrayList<Recipe> recipes;

    private HttpURLConnection connection = null;
    private String data;

    SearchView searchView;
    String prodName = "chicken";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(R.style.Theme_AppCompat_Light_NoActionBar);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes_list);


        balancedButton = findViewById(R.id.balanced_button);
        highFiberButton = findViewById(R.id.high_fiber_button);
        highProteinButton = findViewById(R.id.high_protein_button);
        recipesList = findViewById(R.id.recycleView);
        recipes = new ArrayList<>();
        adapter = new RecipeAdapter(this, recipes);

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    data = GetDownloadData();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Parsing(data);
                            recipesList.setAdapter(adapter);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

        searchView = findViewById(R.id.search_bar);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                prodName = query;
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            data = GetDownloadData();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Parsing(data);
                                    recipesList.setAdapter(adapter);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
    }
    private String GetDownloadData() {

        StringBuilder result = new StringBuilder();
        try {
            connection = (HttpURLConnection) new URL("https://api.edamam.com/search?q=" + prodName + "&app_id=4da5a427&app_key=6dd6f99730da1737e964379d886e607d&diet=high-protein").openConnection();
            // установка метода запроса
            connection.setRequestMethod("GET");
            // поключение
            connection.connect();
            if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {
                // считывание данных
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line; // строка для чтения
                // цикл чтения данных
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                    result.append("\n");
//                    Log.d("MSG", line);
                }
            }
        }
        catch (Exception exc) {
            exc.printStackTrace();
        }
        // отключение
        if (connection != null) {
            connection.disconnect();
        }
        return result.toString();
    }

    private void Parsing(String jsonData) throws ParseException {
        Log.d("Msg", "Привет!");
//        try {
            // парсинг json
//            Object object = new JSONParser().parse(jsonData);

            Object object = new JSONParser().parse(jsonData);
            org.json.simple.JSONObject jsonObject = (JSONObject) object;
            // получение списка рецептов
            org.json.simple.JSONArray jsonArray = (org.json.simple.JSONArray) jsonObject.get("hits");

            for (Object obj : jsonArray) {
                org.json.simple.JSONObject item = (org.json.simple.JSONObject) obj;
                org.json.simple.JSONObject recipeObject = (org.json.simple.JSONObject) item.get("recipe");
                String label = recipeObject.get("label").toString();
                String image =recipeObject.get("image").toString();
                Log.d("LABEL", label);
                String calories = recipeObject.get("calories").toString();
                Log.d("CALORIES", calories);
                org.json.simple.JSONObject totalNutrients = (org.json.simple.JSONObject) recipeObject.get("totalNutrients");
                org.json.simple.JSONObject proteinsObject = (org.json.simple.JSONObject) totalNutrients.get("PROCNT");
                String proteinsValue = proteinsObject.get("quantity").toString();
                Log.d("PROTEINS", proteinsValue);
                org.json.simple.JSONObject fatsObject = (org.json.simple.JSONObject) totalNutrients.get("FAT");
                String fatsValue = fatsObject.get("quantity").toString();
                Log.d("FATS", fatsValue);
                org.json.simple.JSONObject carbsObject = (org.json.simple.JSONObject) totalNutrients.get("CHOCDF");
                String carbsValue = carbsObject.get("quantity").toString();
                Log.d("CARBS", carbsValue);



                Recipe recipe = new Recipe();
                recipe.setLabel(label);
                recipe.setCalories(String.valueOf( Math.round(Double.parseDouble(calories))));
                recipe.setProtein(String.valueOf(Math.round(Double.parseDouble(proteinsValue))));
                recipe.setCarbs(String.valueOf(Math.round(Double.parseDouble(carbsValue))));
                recipe.setFats(String.valueOf( Math.round(Double.parseDouble(fatsValue))));
                recipe.setImageFoot(image);

                recipes.add(recipe);
            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

}