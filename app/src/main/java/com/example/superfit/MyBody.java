package com.example.superfit;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.superfit.mainscreen.mainScreen;

import static com.example.superfit.R.layout.activity_my_body;

public class MyBody extends AppCompatActivity {

    Button btn1;
    Button btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(R.style.Theme_AppCompat_Light_NoActionBar);

        super.onCreate(savedInstanceState);
        setContentView(activity_my_body);

    }

    public void back(View view) {
        Intent intent = new Intent(this, mainScreen.class);
        startActivity(intent);
    }

    public void editkg(View view) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater ItInflater = getLayoutInflater();
            View view2 = ItInflater.inflate(R.layout.dialog, null);
            builder.setView(view2);

            AlertDialog alertDialog = builder.create();
            btn1 = view2.findViewById(R.id.btnok);
            btn2 = view2.findViewById(R.id.btnno);

            TextView txtKG = findViewById(R.id.txtkg);

            btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    EditText editText = alertDialog.findViewById(R.id.editTextKg);
                    txtKG.setText(editText.getText().toString());
                    assert editText != null;
                    alertDialog.dismiss();
                }
            });

            btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });
            alertDialog.show();
        }

    public void editcm(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater ItInflater = getLayoutInflater();
        View view1 = ItInflater.inflate(R.layout.dialogcm, null);
        builder.setView(view1);
        AlertDialog alertDialog = builder.create();
        btn1 = view1.findViewById(R.id.btnok);
        btn2 = view1.findViewById(R.id.btnno);

        TextView txtCM = findViewById(R.id.txtcm);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText editText = alertDialog.findViewById(R.id.editTextCm);
                txtCM.setText(editText.getText().toString());
                assert editText != null;
                alertDialog.dismiss();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }
}
