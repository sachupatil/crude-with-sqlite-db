package com.example.dailyexpenses;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DayExpenses extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    EditText dop, description, amount, paidto;//, mop;
    private int mYear, mMonth, mDay,week;
    Button add, total, view,delete;
    TextView totalamount, income, balance, logout;
    Spinner mop;
    DataBaseHelper myDb;
    String item;

    TabLayout tabLayout;
    ViewPager viewPager;
    String email;

    //TabLayout tabLayout;
    //FrameLayout frameLayout;
    //  Fragment fragment = null;
    //  FragmentManager fragmentManager;
    //   FragmentTransaction fragmentTransaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_expenses);

//        tabLayout = findViewById(R.id.tabLayout);
//        viewPager = findViewById(R.id.viewPager);
//        tabLayout.addTab(tabLayout.newTab().setText("Football"));
//        tabLayout.addTab(tabLayout.newTab().setText("Cricket"));
//        tabLayout.addTab(tabLayout.newTab().setText("NBA"));
//        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
//        final MyAdapter adapter = new MyAdapter(this,getSupportFragmentManager(),
//                tabLayout.getTabCount());
//        viewPager.setAdapter(adapter);
//        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
//        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                viewPager.setCurrentItem(tab.getPosition());
//            }
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//            }
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//            }
//        });
//    }
//}
        logout = (TextView) findViewById(R.id.logout);
       // logout.setPaintFlags(logout.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        dop = (EditText) findViewById(R.id.dop);
        mop=(Spinner)findViewById(R.id.mop);
        description = (EditText) findViewById(R.id.description);
        amount = (EditText) findViewById(R.id.amount);
        paidto = (EditText) findViewById(R.id.paidto);
        add = (Button) findViewById(R.id.add);
        total = (Button) findViewById(R.id.total);
       // mop = (EditText) findViewById(R.id.mop);
        totalamount = (TextView) findViewById(R.id.totalamount);
        view = (Button) findViewById(R.id.viewdata);
        income = (TextView) findViewById(R.id.income);
        balance = (TextView) findViewById(R.id.balance);
        ShapeDrawable shape = new ShapeDrawable(new RectShape());
        shape.getPaint().setColor(Color.WHITE);
        shape.getPaint().setStyle(Paint.Style.STROKE);
        shape.getPaint().setStrokeWidth(3);

        // Assign the created border to EditText widget
        dop.setBackground(shape);
         delete=(Button) findViewById(R.id.delete);
        dop.setOnClickListener(this);
        add.setOnClickListener(this);
        myDb = new DataBaseHelper(this);
        total.setOnClickListener(this);
        view.setOnClickListener(this);
        // Intent i = getIntent();
        // Bundle b = i.getBundleExtra("email");
        //email = b.getString("email");
//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
//        email = prefs.getString("email", null);
        //Log.d("email1",email);
        income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DayExpenses.this, IncomeFragment.class);
                startActivity(i);
                finish();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                myDb.deleteData();


                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DayExpenses.this);

                // set title
                alertDialogBuilder.setTitle("Save Money");

                // set dialog message
                alertDialogBuilder
                        .setMessage("Are you sure for delete items")
                        .setCancelable(false)
                        .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                Integer deletedRows = myDb.deleteExp(email);
                                Log.d("deletedRows....", String.valueOf(deletedRows));
                                if(deletedRows > 0)
                                    Toast.makeText(DayExpenses.this,"Data Deleted",Toast.LENGTH_LONG).show();
                                else
                                    Toast.makeText(DayExpenses.this,"Data not Deleted",Toast.LENGTH_LONG).show();
                            }
                        })
                        .setNegativeButton("No",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();
                            }
                        });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
            }
        });
        // Spinner click listener
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
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSharedPreferences("prefs_login", Context.MODE_PRIVATE).edit().clear().commit();
                Intent intent = new Intent(DayExpenses.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
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
        paidto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()) {
                    paidto.setError(null);
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
    }

    void profit() {
        int exp = myDb.getTotal();//();
        int in = myDb.getIncomeTotal();
        int profit = in - exp;
        balance.setText("Balance :" + profit);
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
               // week=c.get(Calendar.DAY_OF_WEEK);
              //  Log.d("week of the month", String.valueOf(week));
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
                if (validate()) {
                    boolean isInserted = myDb.insertData(dop.getText().toString(),
                            item,
                            paidto.getText().toString(),
                            description.getText().toString(),
                            email,
                            amount.getText().toString());
                    Log.d("isInserted....", String.valueOf(isInserted));
                    if (isInserted == true) {
                        Toast.makeText(DayExpenses.this, "Data Inserted", Toast.LENGTH_LONG).show();
                        clear();
                    } else
                        Toast.makeText(DayExpenses.this, "Data not Inserted", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.total:
                //  clear();
                int res = myDb.getTotal();//();
                Log.d("total : ", String.valueOf(res));
                if (res == 0) {
                    // show message
                    showMessage("Error", "Nothing found");
                    return;
                }
                totalamount.setText("Total :" + res);
                profit();
                break;
            case R.id.viewdata:
                Intent i = new Intent(DayExpenses.this, ExpFragment.class);
                startActivity(i);
                finish();

//
//                Cursor ress = myDb.getAllData();
//                if (ress.getCount() == 0) {
//                    // show message
//                    showMessage("Error", "Nothing found");
//                    return;
//                }
//                StringBuffer buffer = new StringBuffer();
//                while (ress.moveToNext()) {
//                    buffer.append("Id :" + ress.getString(0) + "\n");
//                    buffer.append("Date :" + ress.getString(1) + "\n");
//                    buffer.append("Method :" + ress.getString(2) + "\n");
//                    buffer.append("Paid to :" + ress.getString(3) + "\n");
//                    buffer.append("Description :" + ress.getString(4) + "\n");
//                    buffer.append("email :" + ress.getString(5) + "\n");
//                    buffer.append("Amount :" + ress.getString(6) + "\n\n");
//                }
//                // Show all data
//                showMessage("Data", buffer.toString());
        }
    }

    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    boolean validate() {
        boolean b = false;
        if (dop.getText().toString().trim().equals("")) {
            b = false;
            dop.setError("All field is required...!");
//        } else if (mop.getText().toString().trim().equals("")) {
//            mop.setError("All field is required...!");
//            b = false;
        } else if (paidto.getText().toString().trim().equals("")) {
            paidto.setError("All field is required...!");
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

    void clear() {
        dop.setText(" ");
        //dop.setError("");
        description.setText(" ");
        // description.setError("");
        paidto.setText(" ");
        // paidto.setError("");
        amount.setText(" ");
        //  amount.setError("");
      //  mop.setText(" ");
        //   mop.setError("");
    }

    @Override
    public void onBackPressed() {
        getSharedPreferences("prefs_login", Context.MODE_PRIVATE).edit().clear().commit();
       // Registerdata.SetPreferences(DayExpenses.this, "login", "yes");
//        Intent intent = new Intent(DayExpenses.this, MainActivity.class);
//        startActivity(intent);
//        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        item = parent.getItemAtPosition(position).toString();
        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
