package com.example.superfit.mainscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.superfit.MyBody;
import com.example.superfit.R;
import com.example.superfit.authorization.Authorization;
import com.example.superfit.exercisesScreen.ExercisesScreen;
import com.example.superfit.recipe.RecipesList;

public class mainScreen extends AppCompatActivity {

    Button btn1;
    Button btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(R.style.Theme_AppCompat_Light_NoActionBar);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

    }



//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        LayoutInflater ItInflater = getLayoutInflater();
//        View view = ItInflater.inflate(R.layout.dialog, null);
//        builder.setView(view);
//        AlertDialog alertDialog = builder.create();
//        btn1 = view.findViewById(R.id.btnok);
//        btn2 = view.findViewById(R.id.btnno);
//
//        btn1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                alertDialog.dismiss();
//            }
//        });
//
//        btn2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                alertDialog.dismiss();
//            }
//        });
//        alertDialog.show();

    public void Recipes(View view) {
        Intent intent = new Intent(this, RecipesList.class);
        startActivity(intent);
    }

    public void Exit(View view) {
        Intent intent = new Intent(this, Authorization.class);
        startActivity(intent);
    }

    public void MyBody(View view) {
        Intent intent = new Intent(this, MyBody.class);
        startActivity(intent);
    }

    public void SeeAll(View view) {
        Intent intent = new Intent(this, ExercisesScreen.class);
        startActivity(intent);
    }
}