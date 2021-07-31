package com.example.dailyexpenses;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class IncomeFragment extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener{

    EditText dop,description,amount,incomefrom;//,mop;
    private int mYear, mMonth, mDay;
    Button add,total,view;
    TextView totalamount;
    Spinner mop;
    DataBaseHelper myDb;
    String email,item;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.income_fragment);
        dop=(EditText)findViewById(R.id.dop);
        description=(EditText)findViewById(R.id.description);
        amount=(EditText)findViewById(R.id.amount);
        incomefrom=(EditText) findViewById(R.id.incomefrom);
        add=(Button)findViewById(R.id.add);
        total=(Button)findViewById(R.id.total);
      //  mop=(EditText)findViewById(R.id.mop);
        mop=(Spinner) findViewById(R.id.mop);

        totalamount=(TextView) findViewById(R.id.totalamount);
        view=(Button)findViewById(R.id.viewdata);
        dop.setOnClickListener(this);
        add.setOnClickListener(this);
        myDb = new DataBaseHelper(this);
        total.setOnClickListener(this);
        view.setOnClickListener(this);
        dop.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()) {
                    dop.setError(null);
                }
            }
        });
//        mop.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if (!s.toString().isEmpty()) {
//                    mop.setError(null);
//                }
//            }
//        });
        description.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()) {
                    description.setError(null);
                }
            }
        });
        incomefrom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()) {
                    incomefrom.setError(null);
                }
            }
        });
        amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()) {
                    amount.setError(null);
                }
            }
        });
        email = Registerdata.getPreferences(this, "Email");
        mop.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Cash ");
        categories.add("Phone Pay");
        categories.add("Google pay");
        categories.add("PayTM");
        categories.add("by Friend");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        mop.setAdapter(dataAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dop:
                // Get Current Date
                Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                dop.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
                break;
            case R.id.add:
                if(validate()) {
                    boolean isInserted = myDb.insertIncomeData(dop.getText().toString(),
                            item,
                            incomefrom.getText().toString(),
                            description.getText().toString(),
                            email,
                            amount.getText().toString());
                    Log.d("isInserted....", String.valueOf(isInserted));
                    if (isInserted == true) {
                        Toast.makeText(IncomeFragment.this, "Data Inserted", Toast.LENGTH_LONG).show();
                        clear();
                    }else
                        Toast.makeText(IncomeFragment.this, "Data not Inserted", Toast.LENGTH_LONG).show();
                }
                int res = myDb.getIncomeTotal();
                Log.d("total : ", String.valueOf(res));
                if(res == 0) {
                    // show message
                    totalamount.setText("Total Amount :"+0);
                    return;
                }
                totalamount.setText("Total Amount :"+res);
                break;
            case R.id.total:
                Integer deletedRows = myDb.deleteIncome(email);
                Log.d("deletedRows....", String.valueOf(deletedRows));
                if(deletedRows > 0)
                    Toast.makeText(IncomeFragment.this,"Data Deleted",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(IncomeFragment.this,"Data not Deleted",Toast.LENGTH_LONG).show();
                break;

            case R.id.viewdata:
                Intent i = new Intent(IncomeFragment.this, IncomeList.class);
                startActivity(i);
                finish();
                break;

//                Cursor ress = myDb.getAllIncome();
//            if (ress.getCount() == 0) {
//                // show message
//                showMessage("Error", "Nothing found");
//                return;
//            }
//            StringBuffer buffer = new StringBuffer();
//            while (ress.moveToNext()) {
//                buffer.append("Id :" + ress.getString(0) + "\n");
//                buffer.append("Date :" + ress.getString(1) + "\n");
//                buffer.append("Method :" + ress.getString(2) + "\n");
//                buffer.append("Paid to :" + ress.getString(3) + "\n");
//                buffer.append("Description :" + ress.getString(4) + "\n");
//                buffer.append("email :" + ress.getString(5) + "\n");
//                buffer.append("Amount :" + ress.getString(6) + "\n\n");
//            }
//            // Show all data
//            showMessage("Data", buffer.toString());
//
        }
    }

    void total(){
        int res = myDb.getIncomeTotal();
        Log.d("total : ", String.valueOf(res));
        if(res == 0) {
            // show message
            showMessage("Error","Nothing found");
            return;
        }
        totalamount.setText("Total Amount :"+res);
    }


    boolean validate() {
        boolean b = false;
        if (dop.getText().toString().trim().equals("")) {
            b = false;
            dop.setError("All field is required...!");
//        } else if (mop.getText().toString().trim().equals("")) {
//            mop.setError("All field is required...!");
//            b = false;
        } else if (incomefrom.getText().toString().trim().equals("")) {
            incomefrom.setError("All field is required...!");
            b = false;
        } else if (description.getText().toString().trim().equals("")) {
            b = false;
            description.setError("All field is required...!");
        } else if (amount.getText().toString().trim().equals("")) {
            b = false;
            amount.setError("All field is required...!");
        } else {
            b = true;
        }
        return b;
    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(IncomeFragment.this,DayExpenses.class);
        startActivity(i);
        finish();
    }

    public void showMessage(String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
    void clear(){
        dop.setText(" ");
        //dop.setError("");
        description.setText(" ");
        //description.setError("");
        incomefrom.setText(" ");
        //incomefrom.setError("");
        amount.setText(" ");
        //amount.setError("");
      //  mop.setText(" ");
        //mop.setError("");
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        item = parent.getItemAtPosition(position).toString();
        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
