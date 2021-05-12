package com.example.superfit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.example.superfit.DB.DBHelper;
import com.example.superfit.authorization.Authorization;
import com.example.superfit.mainscreen.mainScreen;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import android.database.sqlite.SQLiteDatabase;

public class MainActivity extends AppCompatActivity{


    @NotEmpty
    Button btnSignUp;
    EditText etUserName,etEmail,etPin_code,etRepeat_code;
    private AwesomeValidation awesomeValidation;
    DBHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(R.style.Theme_AppCompat_Light_NoActionBar);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        EditText etUserName = findViewById(R.id.txUserName);
        EditText etEmail = findViewById(R.id.txEmail);
        EditText etPin_code = findViewById(R.id.txCode);
        EditText etRepeat_code = findViewById(R.id.txRepeatCode);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        dbHelper = new DBHelper(this);

        awesomeValidation.addValidation(this, R.id.txUserName, RegexTemplate.NOT_EMPTY,R.string.user_name);
        awesomeValidation.addValidation(this, R.id.txEmail, Patterns.EMAIL_ADDRESS,R.string.emailerror);
        awesomeValidation.addValidation(this, R.id.txCode, ".{4,}",R.string.pin_cod);
        awesomeValidation.addValidation(this, R.id.txCode, "[1-9][1-9][1-9][1-9]",R.string.pin_cod2);
        awesomeValidation.addValidation(this, R.id.txRepeatCode,".{4,}", R.string.repeat_pin_cod);
        awesomeValidation.addValidation(this, R.id.txRepeatCode,R.id.txCode,R.string.pin_code_match);

        btnSignUp = findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(awesomeValidation.validate()){

                    String name = etUserName.getText().toString();
                    String email = etEmail.getText().toString();
                    String pin_code = etPin_code.getText().toString();

                    SQLiteDatabase database = dbHelper.getWritableDatabase();

                    ContentValues contentValues = new ContentValues();

                    contentValues.put(DBHelper.KEY_NAME,name);
                    contentValues.put(DBHelper.KEY_EMAIL,email);
                    contentValues.put(DBHelper.KEY_CODE,pin_code);

                    database.insert(DBHelper.TABLE_CONTACTS, null, contentValues);

                    Cursor cursor = database.query(DBHelper.TABLE_CONTACTS, null, null, null, null, null, null);

                    if(cursor.moveToNext()){
                        int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
                        int nameIndex = cursor.getColumnIndex(DBHelper.KEY_NAME);
                        int emailIndex = cursor.getColumnIndex(DBHelper.KEY_EMAIL);
                        int codeIndex = cursor.getColumnIndex(DBHelper.KEY_CODE);
                        do{

                            Log.d("Log", "ID = " + cursor.getInt(idIndex)
                                            + "\n"
                                    + "Name = " + cursor.getInt(nameIndex)
                                    + "\n"
                                    + "Email = " + cursor.getInt(emailIndex)
                                    + "\n"
                                    + "Code = " + cursor.getInt(codeIndex)
                                    );

                        }while (cursor.moveToNext());
                    }else {
                        Log.d("Error Log", "0 rows");
                    }

                    cursor.close();
                    dbHelper.close();

                    Intent intent = new Intent(MainActivity.this, mainScreen.class);
                    startActivity(intent);
                }
            }
        });
    }

    public void SignIn(View view) {
        Intent intent = new Intent(this, Authorization.class);
        startActivity(intent);
    }
}