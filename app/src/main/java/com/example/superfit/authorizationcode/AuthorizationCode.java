package com.example.superfit.authorizationcode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.example.superfit.R;
import com.example.superfit.authorization.Authorization;

import java.util.ArrayList;

public class AuthorizationCode extends AppCompatActivity {

    TextView tvUserName;
    String pin_code;
    GridView gridNumbers;
    private ArrayList<String> numbers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(R.style.Theme_AppCompat_Light_NoActionBar);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization_code);

        tvUserName = findViewById(R.id.txUserName);
        Intent i = getIntent();
        tvUserName.setText(i.getStringExtra("userName"));
        pin_code = i.getStringExtra("code");

        numbers.add("1");
        numbers.add("2");
        numbers.add("3");
        numbers.add("4");
        numbers.add("5");
        numbers.add("6");
        numbers.add("7");
        numbers.add("8");
        numbers.add("9");
        gridNumbers = findViewById(R.id.gridNumbers);
        CustomAdapter customAdapter = new CustomAdapter(numbers, this, pin_code);
        gridNumbers.setAdapter(customAdapter);
    }

    public void SignIn(View view) {
        Intent intent = new Intent(this, Authorization.class);
        startActivity(intent);
    }
}