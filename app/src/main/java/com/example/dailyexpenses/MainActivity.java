package com.example.dailyexpenses;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    EditText username, passward;
    Button singin;
    DataBaseHelper myDb;
    TextView signup,forgetpsw;
    public static final String LAST_TEXT = "";
    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DataBaseHelper(this);
        username = (EditText) findViewById(R.id.email);
        passward = (EditText) findViewById(R.id.password);
        singin = (Button) findViewById(R.id.signin);
        signup = (TextView) findViewById(R.id.signup);
        forgetpsw=(TextView)findViewById(R.id.forgetpsw);
        final SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        username.setText(pref.getString(LAST_TEXT, ""));
        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                pref.edit().putString(LAST_TEXT, s.toString()).commit();
            }
        });
        singin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Check user input is correct or not
                if (validate()) {

                    //Get values from EditText fields
                    String Email = username.getText().toString();
                    String Password = passward.getText().toString();

                    //Autheticate user
                    User currentUser = myDb.Authenticate(new User(null, null, Email, Password));
                    //Check Authentication is successful or not
                    if (currentUser != null) {
                        Snackbar.make(singin, "Successfully Logged in!", Snackbar.LENGTH_LONG).show();

                        //User Logged in Successfully Launch You home screen activity
                        Registerdata.SetPreferences(MainActivity.this, "Email",Email);
                        Intent i = new Intent(MainActivity.this, DayExpenses.class);
                        startActivity(i);
                        finish();
                    } else {

                        //User Logged in Failed
                        Snackbar.make(singin, "Failed to log in , please try again", Snackbar.LENGTH_LONG).show();

                    }
                }
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,Registration.class);
                startActivity(i);
                finish();
            }
        });
        forgetpsw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor ress = myDb.getPassword();
                if(ress.getCount() == 0) {
                    // show message
                    showMessage("Error","Nothing found");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (ress.moveToNext()) {
                    buffer.append("Id :" + ress.getString(0) + "\n");
                    buffer.append("USERNAME :" + ress.getString(1) + "\n");
                    buffer.append("EMAIL :" + ress.getString(2) + "\n");
                    buffer.append("PASSWORD :" + ress.getString(3) + "\n\n");
                }
                // Show all data
                showMessage("ALL USERS",buffer.toString());
            }
        });
    }
    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
    //This method is for handling fromHtml method deprecation
    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String html) {
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(html);
        }
        return result;
    }

    //This method is used to validate input given by user
    public boolean validate() {
        boolean valid = false;

        //Get values from EditText fields
        String Email = username.getText().toString();
        String Password = passward.getText().toString();

        //Handling validation for Email field
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            valid = false;
            username.setError("Please enter valid email!");
        } else {
            valid = true;
            username.setError(null);
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


    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG,"call of onStart method");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG,"call of onResume method");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG,"call of onPause method");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG,"call of onStop method");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG,"call of onRestart method");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"call of onDestroy method");
    }
}
