package com.example.superfit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import org.w3c.dom.Text;

import java.util.List;

public class MainActivity extends AppCompatActivity implements Validator.ValidationListener {


    @NotEmpty
    private EditText ed_1;


    //Email: arge@gmail.com
    @NotEmpty
    @Email
    private EditText ed_2;

    //Password: 1234
    @NotEmpty
    @Length(min = 4, max = 4)
    private EditText ed_3;

    //Password: 1234
    @NotEmpty
    @Length(min = 4, max = 4)
    private EditText ed_4;

    Button btnSignUp;

    private Validator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(R.style.Theme_AppCompat_Light_NoActionBar);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        validator = new Validator(this);
        validator.setValidationListener(this);


        ed_1 = findViewById(R.id.ed_1);
        ed_2 = findViewById(R.id.ed_2);
        ed_3 = findViewById(R.id.ed_3);
        ed_4 = findViewById(R.id.ed_4);

        btnSignUp = findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validator.validate();
            }
        });

    }

    public void SignIn(View view) {
        Intent intent = new Intent(this, Authorization.class);
        startActivity(intent);
    }

    public void MainScreen(View view) {
        /*Intent intent = new Intent(this, mainScreen.class);
        startActivity(intent);*/
    }

    @Override
    public void onValidationSucceeded() {
        Intent intent = new Intent(this, mainScreen.class);
        startActivity(intent);
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for(ValidationError error : errors){
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);
            if(view instanceof EditText){
                ((EditText) view).setError(message);
            }else{
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }
}