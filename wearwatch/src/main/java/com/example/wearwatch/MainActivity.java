package com.example.wearwatch;

import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends WearableActivity {

    private TextView  tvsignIn;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(R.style.Theme_AppCompat_DayNight_NoActionBar);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.text);
        tvsignIn = findViewById(R.id.signIn);

        // Enables Always-on
        setAmbientEnabled();
    }

    public void goNext(View view) {
//        if(editText == null){
//            Toast toast = Toast.makeText(getApplicationContext(),
//                    "Invalid text User Name", Toast.LENGTH_SHORT);
//            toast.show();
//        }else if (editText != null){
            Intent intent = new Intent(this, Authorization.class);
            startActivity(intent);
//        }
    }
}