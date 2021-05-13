package com.example.superfit.exercisesScreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.superfit.R;
import com.example.superfit.mainscreen.mainScreen;

public class ExercisesScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(R.style.Theme_AppCompat_Light_NoActionBar);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises_screen);
    }

    public void goHome(View view) {
        Intent intent = new Intent(this, mainScreen.class);
        startActivity(intent);
    }
}