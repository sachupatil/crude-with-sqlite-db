package com.example.dailyexpenses;


import android.app.AlertDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class ExpFragment extends AppCompatActivity {

    Intent intent;
    SimpleAdapter adapter;
    DataBaseHelper myDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.explistview);
        ListView lv = (ListView) findViewById(R.id.exp_list);
        DataBaseHelper db = new DataBaseHelper(this);
        String email = Registerdata.getPreferences(this, "Email");

        final ArrayList<HashMap<String, String>> userList = db.GetUsers(email);
        if(userList.size()==0){
            showMessage("Error", "You dont have any expenses...!");
        }else {
             adapter = new SimpleAdapter(ExpFragment.this, userList,
                    R.layout.exp_list_row, new String[]{"KEY_ID","KEY_USER_EXPDATE", "METHOD", "PAIDTO", "DESCRIPTION", "AMOUNT"},
                    new int[]{R.id.srno,R.id.date, R.id.method, R.id.paidto, R.id.description, R.id.amount});
            lv.setAdapter(adapter);
        }
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,int position,long id) {
                // TODO Auto-generated method stub
                userList.remove(position);
                int positionn = position+1;
                Integer deletedRows = myDb.deleteData(String.valueOf(positionn));
                Log.d("deletedRows....", String.valueOf(deletedRows));
                if(deletedRows > 0)
                    Toast.makeText(ExpFragment.this,"Data Deleted",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(ExpFragment.this,"Data not Deleted",Toast.LENGTH_LONG).show();
                adapter.notifyDataSetChanged();
                Toast.makeText(ExpFragment.this, "Item Deleted", Toast.LENGTH_LONG).show();
                return true;
            }

        });

    }
    void delete(int position){
        Integer deletedRows = myDb.deleteData(String.valueOf((position)));
        Log.d("deletedRows....", String.valueOf(deletedRows));
        if(deletedRows > 0)
            Toast.makeText(ExpFragment.this,"Data Deleted",Toast.LENGTH_LONG).show();
        else
            Toast.makeText(ExpFragment.this,"Data not Deleted",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(ExpFragment.this,DayExpenses.class);
        startActivity(i);
        finish();
    }

    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

}