package com.example.dailyexpenses;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class Registration extends AppCompatActivity {
   EditText username,passward,email;
   Button singup;
   DataBaseHelper sqliteHelper;
   TextView signIn_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        username = (EditText) findViewById(R.id.username);
        passward = (EditText) findViewById(R.id.password);
        email = (EditText) findViewById(R.id.mobileno);
        singup = (Button) findViewById(R.id.signup);
        signIn_text=(TextView)findViewById(R.id.signIn_text);
        sqliteHelper = new DataBaseHelper(this);
        singup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    String UserName = username.getText().toString();
                    String Email = email.getText().toString();
                    String Password = passward.getText().toString();

                    //Check in the database is there any user associated with  this email
                    if (!sqliteHelper.isEmailExists(Email)) {

                        //Email does not exist now add new user to database
                        sqliteHelper.addUser(new User(null, UserName, Email, Password));
                        Snackbar.make(singup, "User created successfully! Please Login ", Snackbar.LENGTH_LONG).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                login();
                            }
                        }, Snackbar.LENGTH_LONG);
                    } else {

                        //Email exists with email input provided so show error user already exist
                        Snackbar.make(singup, "User already exists with same email ", Snackbar.LENGTH_LONG).show();
                    }


                }
            }
        });
        signIn_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(Registration.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
    public boolean validate() {
        boolean valid = false;

        //Get values from EditText fields
        String UserName = username.getText().toString();
        String Email = email.getText().toString();
        String Password = passward.getText().toString();

        //Handling validation for UserName field
        if (UserName.isEmpty()) {
            valid = false;
            username.setError("Please enter valid username!");
        } else {
            if (UserName.length() > 5) {
                valid = true;
                username.setError(null);
            } else {
                valid = false;
                username.setError("Username is to short!");
            }
        }

        //Handling validation for Email field
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            valid = false;
            email.setError("Please enter valid email!");
        } else {
            valid = true;
            email.setError(null);
        }
        //Handling validation for Password field
        if (Password.isEmpty()) {
            valid = false;
            passward.setError("Please enter valid password!");
        } else {
            if (Password.length() > 5) {
                valid = true;
                passward.setError(null);
            } else {
                valid = false;
                passward.setError("Password is to short!");
            }
        }
        return valid;
    }
    void login(){
        Intent i = new Intent(Registration.this,MainActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(Registration.this,MainActivity.class);
        startActivity(i);
        finish();
    }
}
