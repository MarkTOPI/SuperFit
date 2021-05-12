package com.example.wearwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.GridView;

import java.util.ArrayList;

public class Authorization extends AppCompatActivity {

    String code;
    GridView gridNumbers;
    private ArrayList<String> numbers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(R.style.Theme_AppCompat_DayNight_NoActionBar);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);

        Intent i  = getIntent();
        code = i.getStringExtra("code");

        numbers.add("1");
        numbers.add("2");
        numbers.add("3");
        numbers.add("4");
        numbers.add("5");
        numbers.add("6");
        numbers.add("7");
        numbers.add("8");
        numbers.add("9");
        gridNumbers = findViewById(R.id.gridnumbers);
        CustomAdapter customAdapter = new CustomAdapter(numbers, this,code);
        gridNumbers.setAdapter(customAdapter);
    }
}