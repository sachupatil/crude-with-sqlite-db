package com.example.dailyexpenses;


import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class IncomeList extends AppCompatActivity {

    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.explistview);
        DataBaseHelper db = new DataBaseHelper(this);
        String email = Registerdata.getPreferences(this, "Email");
        ArrayList<HashMap<String, String>> userList = db.GetIncome(email);
        if(userList.size()==0){
            showMessage("Error", "You dont have any income...!");

        }else {
            ListView lv = (ListView) findViewById(R.id.exp_list);
            ListAdapter adapter = new SimpleAdapter(IncomeList.this, userList,
                    R.layout.income_list_row, new String[]{"KEY_USER_INCOMEDATE", "INCOMEMETHOD", "FROMINCOME", "INCOMEDESCRIPTION", "INCOMEAMOUNT"},
                    new int[]{R.id.date, R.id.method, R.id.paidto, R.id.description, R.id.amount});
            lv.setAdapter(adapter);

        }
    }

    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(IncomeList.this,IncomeFragment.class);
        startActivity(i);
        finish();
    }
}