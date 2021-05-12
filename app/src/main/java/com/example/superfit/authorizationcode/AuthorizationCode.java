package com.example.superfit.authorizationcode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.superfit.R;
import com.example.superfit.authorization.Authorization;

public class AuthorizationCode extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(R.style.Theme_AppCompat_Light_NoActionBar);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization_code);


    }

    public void SignIn(View view) {
        Intent intent = new Intent(this, Authorization.class);
        startActivity(intent);
    }
}