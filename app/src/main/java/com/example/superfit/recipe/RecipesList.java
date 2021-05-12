package com.example.superfit.recipe;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.superfit.MainActivity;
import com.example.superfit.R;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class RecipesList extends AppCompatActivity {

    ArrayList<RecipeObject> recipesBalanced;
    ArrayList<RecipeObject> recipesFiber;
    ArrayList<RecipeObject> recipesProtein;
    ArrayList<RecipeObject> recipes;
    ListView listView;
    CustomAdapterRecipes adapter;
    String dietType = "balanced";

    RadioButton balanced;
    RadioButton high_Protein;
    RadioButton high_Fiber;
    ProgressBar progressBar;


//    CheckBox balancedButton;
//    CheckBox highFiberButton;
//    CheckBox highProteinButton;
//
//    ListView recipesList;
//    private RecipeAdapter adapter;
//    private ArrayList<Recipe> recipes;
//
//    private HttpURLConnection connection = null;
//    private String data;
//
//    SearchView searchView;
//    String prodName = "chicken";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(R.style.Theme_AppCompat_Light_NoActionBar);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes_list);

        listView = findViewById(R.id.listResipe);
        balanced = findViewById(R.id.Balanced);
        high_Fiber = findViewById(R.id.High_Fiber);
        high_Protein = findViewById(R.id.High_Protein);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(RecipesList.this, RecipeScreen.class);
                intent.putExtra("Recipe", recipes.get(position));
                Log.i("AAAAAAAAAAAA", recipes.get(position).getLabel());
                startActivity(intent);
            }
        });

        RadioButton.OnCheckedChangeListener radio = new RadioButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.getId() == R.id.High_Protein && buttonView.isChecked()){
                    if(recipesProtein.size() == 0){
                        dietType="high-protein";
                        new getRecipes().execute();
                    }else{
                        adapter = new CustomAdapterRecipes(RecipesList.this, recipesProtein);
                        listView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                }
                if(buttonView.getId() == R.id.Balanced && buttonView.isChecked()){
                    if(recipesBalanced.size() == 0){
                        dietType="balanced";
                        new getRecipes().execute();
                    }else{
                        adapter = new CustomAdapterRecipes(RecipesList.this, recipesBalanced);
                        listView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                }
                if(buttonView.getId() == R.id.High_Fiber && buttonView.isChecked()){
                    if(recipesFiber.size() == 0){
                        dietType="low-carb";
                        new getRecipes().execute();
                    }else{
                        adapter = new CustomAdapterRecipes(RecipesList.this, recipesFiber);
                        listView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        };
        high_Protein.setOnCheckedChangeListener(radio);
        balanced.setOnCheckedChangeListener(radio);
        high_Fiber.setOnCheckedChangeListener(radio);

        new getRecipes().execute();
        recipesBalanced = new ArrayList<>();
        recipesProtein = new ArrayList<>();
        recipesFiber = new ArrayList<>();
        recipes = new ArrayList<>();
    }

    public void goHome(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    private class getRecipes extends AsyncTask<Void, Void, Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.i("Connection", "preExec");
            listView.setVisibility(View.GONE);
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                HttpURLConnection httpURLConnection = (HttpURLConnection) new URL("https://api.edamam.com/search?q=chicken&app_id=4da5a427&app_key=6dd6f99730da1737e964379d886e607d&diet="+dietType).openConnection();
                httpURLConnection.connect();
                if(httpURLConnection.getResponseCode() == 200){
                    Log.i("Connection", "ok");
                }else{
                    Log.e("COnnectoin", " " + httpURLConnection.getResponseCode());
                }
                InputStream stream = httpURLConnection.getInputStream();
                StringBuilder jsonText = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                String answer= "";
                while((answer = reader.readLine()) != null){
                    jsonText.append(answer);
                }
                Log.i("Parser", jsonText.toString());
                org.json.JSONObject jsonObject = new org.json.JSONObject(jsonText.toString());
                JSONArray hits = jsonObject.getJSONArray("hits");
                for(int i =0; i<hits.length(); i++){
                    org.json.JSONObject recipeobj = hits.getJSONObject(i);
                    JSONObject recipe = recipeobj.getJSONObject("recipe");
                    JSONArray digestJ = recipe.getJSONArray("digest");
                    String [] digest = new String[3];
                    digest[0] = digestJ.getJSONObject(0).get("total").toString().substring(0, digestJ.getJSONObject(0).get("total").toString().indexOf('.')) + "g protein";
                    digest[1] = digestJ.getJSONObject(1).get("total").toString().substring(0, digestJ.getJSONObject(1).get("total").toString().indexOf('.'))+ "g fat";
                    digest[2] = digestJ.getJSONObject(2).get("total").toString().substring(0, digestJ.getJSONObject(2).get("total").toString().indexOf('.')) + "g carbs";
                    switch (dietType) {
                        case "balanced":
                            recipesBalanced.add(new RecipeObject(recipe.getString("label"), recipe.getString("calories").substring(0, recipe.getString("calories").indexOf('.')) + " kcal", getImageBitmap(recipe.getString("image")), recipe.getString("source"), digest, recipe.getJSONArray("ingredientLines")));
                            break;
                        case "high-protein":
                            recipesProtein.add(new RecipeObject(recipe.getString("label"), recipe.getString("calories").substring(0, recipe.getString("calories").indexOf('.')) + " kcal", getImageBitmap(recipe.getString("image")), recipe.getString("source"), digest, recipe.getJSONArray("ingredientLines")));
                            break;
                        case "low-carb":
                            recipesFiber.add(new RecipeObject(recipe.getString("label"), recipe.getString("calories").substring(0, recipe.getString("calories").indexOf('.')) + " kcal", getImageBitmap(recipe.getString("image")), recipe.getString("source"), digest, recipe.getJSONArray("ingredientLines")));
                            break;
                    }

                }
                httpURLConnection.disconnect();
                jsonText = null;

//                Log.i("Connection", jsonText.toString());
            } catch(Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            listView.setVisibility(View.VISIBLE);

            Log.i("PostExecupe", "PostExecupe");
            switch (dietType) {
                case "balanced":
                    recipes = recipesBalanced;
                    break;
                case "high-protein":
                    recipes = recipesProtein;
                    break;
                case "low-carb":
                    recipes = recipesFiber;
                    break;
            }
            adapter = new CustomAdapterRecipes(RecipesList.this, recipes);
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
        private Bitmap getImageBitmap(String url) {
            Bitmap bm = null;
            try {
                URL aURL = new URL(url);
                URLConnection conn = aURL.openConnection();
                conn.connect();
                InputStream is = conn.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(is);
                bm = BitmapFactory.decodeStream(bis);
                bis.close();
                is.close();
            } catch (IOException e) {
                Log.e("TAG", "Error getting bitmap", e);
            }
            return bm;
        }



//        balancedButton = findViewById(R.id.balanced_button);
//        highFiberButton = findViewById(R.id.high_fiber_button);
//        highProteinButton = findViewById(R.id.high_protein_button);
//        recipesList = findViewById(R.id.recycleView);
//        recipes = new ArrayList<>();
//        adapter = new RecipeAdapter(this, recipes);
//
//        AsyncTask.execute(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    data = GetDownloadData();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            Parsing(data);
//                            recipesList.setAdapter(adapter);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
//            }
//        });
//
//        searchView = findViewById(R.id.search_bar);
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                adapter.getFilter().filter(query);
//                prodName = query;
//                AsyncTask.execute(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            data = GetDownloadData();
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                try {
//                                    Parsing(data);
//                                    recipesList.setAdapter(adapter);
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        });
//                    }
//                });
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                adapter.getFilter().filter(newText);
//                return false;
//            }
//        });
//    }
//    private String GetDownloadData() {
//
//        StringBuilder result = new StringBuilder();
//        try {
//            connection = (HttpURLConnection) new URL("https://api.edamam.com/search?q=" + prodName + "&app_id=4da5a427&app_key=6dd6f99730da1737e964379d886e607d&diet=high-protein").openConnection();
//            // установка метода запроса
//            connection.setRequestMethod("GET");
//            // поключение
//            connection.connect();
//            if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {
//                // считывание данных
//                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//                String line; // строка для чтения
//                // цикл чтения данных
//                while ((line = reader.readLine()) != null) {
//                    result.append(line);
//                    result.append("\n");
////                    Log.d("MSG", line);
//                }
//            }
//        }
//        catch (Exception exc) {
//            exc.printStackTrace();
//        }
//        // отключение
//        if (connection != null) {
//            connection.disconnect();
//        }
//        return result.toString();
//    }
//
//    private void Parsing(String jsonData) throws ParseException {
//        Log.d("Msg", "Привет!");
////        try {
//            // парсинг json
////            Object object = new JSONParser().parse(jsonData);
//
//            Object object = new JSONParser().parse(jsonData);
//            org.json.simple.JSONObject jsonObject = (JSONObject) object;
//            // получение списка рецептов
//            org.json.simple.JSONArray jsonArray = (org.json.simple.JSONArray) jsonObject.get("hits");
//
//            for (Object obj : jsonArray) {
//                org.json.simple.JSONObject item = (org.json.simple.JSONObject) obj;
//                org.json.simple.JSONObject recipeObject = (org.json.simple.JSONObject) item.get("recipe");
//                String label = recipeObject.get("label").toString();
//                String image =recipeObject.get("image").toString();
//                Log.d("LABEL", label);
//                String calories = recipeObject.get("calories").toString();
//                Log.d("CALORIES", calories);
//                org.json.simple.JSONObject totalNutrients = (org.json.simple.JSONObject) recipeObject.get("totalNutrients");
//                org.json.simple.JSONObject proteinsObject = (org.json.simple.JSONObject) totalNutrients.get("PROCNT");
//                String proteinsValue = proteinsObject.get("quantity").toString();
//                Log.d("PROTEINS", proteinsValue);
//                org.json.simple.JSONObject fatsObject = (org.json.simple.JSONObject) totalNutrients.get("FAT");
//                String fatsValue = fatsObject.get("quantity").toString();
//                Log.d("FATS", fatsValue);
//                org.json.simple.JSONObject carbsObject = (org.json.simple.JSONObject) totalNutrients.get("CHOCDF");
//                String carbsValue = carbsObject.get("quantity").toString();
//                Log.d("CARBS", carbsValue);
//
//
//
//                Recipe recipe = new Recipe();
//                recipe.setLabel(label);
//                recipe.setCalories(String.valueOf( Math.round(Double.parseDouble(calories))));
//                recipe.setProtein(String.valueOf(Math.round(Double.parseDouble(proteinsValue))));
//                recipe.setCarbs(String.valueOf(Math.round(Double.parseDouble(carbsValue))));
//                recipe.setFats(String.valueOf( Math.round(Double.parseDouble(fatsValue))));
//                recipe.setImageFoot(image);
//
//                recipes.add(recipe);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

}