package com.example.dailyexpenses;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {


    //DATABASE NAME
    public static final String DATABASE_NAME = "loopwiki.com";

    //DATABASE VERSION
    public static final int DATABASE_VERSION = 1;

    //TABLE NAME
    public static final String TABLE_USERS = "users";

    //TABLE USERS COLUMNS
    //ID COLUMN @primaryKey
    public static final String KEY_ID = "id";

    //COLUMN user name
    public static final String KEY_USER_NAME = "username";
    //COLUMN email
    public static final String KEY_EMAIL = "email";
    //COLUMN password
    public static final String KEY_PASSWORD = "password";


    public static final String TABLE_USERSEXP = "expenses";
    public static final String KEY_USER_EXPDATE = "date";
    public static final String METHOD = "method";
    public static final String PAIDTO = "paidto";
    public static final String DESCRIPTION = "description";
    public static final String EXP_EMAIL = "email";
    public static final String AMOUNT = "amount";

    public static final String TABLE_USERSINCOME = "income";
    public static final String KEY_USER_INCOMEDATE = "date";
    public static final String INCOMEMETHOD = "method";
    public static final String FROMINCOME = "income";
    public static final String INCOMEDESCRIPTION = "description";
    public static final String INC_EMAIL = "email";
    public static final String INCOMEAMOUNT = "amount";


    //SQL for creating users table
    public static final String SQL_TABLE_USERS = " CREATE TABLE " + TABLE_USERS
            + " ( "
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_USER_NAME + " TEXT, "
            + KEY_EMAIL + " TEXT, "
            + KEY_PASSWORD + " TEXT"
            + " ) ";

    public static final String SQL_TABLE_USERSEXP = " CREATE TABLE " + TABLE_USERSEXP
            + " ( "
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_USER_EXPDATE + " DATE, "
            + METHOD + " TEXT, "
            + PAIDTO + " TEXT, "
            + DESCRIPTION + " TEXT, "
            + EXP_EMAIL + " TEXT, "
            + AMOUNT + " NUMBER"
            + " ) ";

    public static final String SQL_TABLE_USERSINCOME = " CREATE TABLE " + TABLE_USERSINCOME
            + " ( "
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_USER_INCOMEDATE + " DATE, "
            + INCOMEMETHOD + " TEXT, "
            + FROMINCOME + " TEXT, "
            + INCOMEDESCRIPTION + " TEXT, "
            + INC_EMAIL + " TEXT, "
            + INCOMEAMOUNT + " NUMBER"
            + " ) ";


    //+ "FOREIGN KEY(" + KEY_ID + ") REFERENCES "
    //            + TABLE_USERS + "(KEY_ID) " + ")";


    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(SQL_TABLE_USERS);
        db.execSQL(SQL_TABLE_USERSEXP);
        db.execSQL(SQL_TABLE_USERSINCOME);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        // Drop older table if existed
        if (newVersion > oldVersion) {
            db.execSQL(" DROP TABLE IF EXISTS " + TABLE_USERS);
            db.execSQL(" DROP TABLE IF EXISTS " + TABLE_USERSEXP);
            db.execSQL(" DROP TABLE IF EXISTS " + TABLE_USERSINCOME);
        }

    }


    //using this method we can add users to user table
    public void addUser(User user) {

        //get writable database
        SQLiteDatabase db = this.getWritableDatabase();

        //create content values to insert
        ContentValues values = new ContentValues();

        //Put username in  @values
        values.put(KEY_USER_NAME, user.userName);

        //Put email in  @values
        values.put(KEY_EMAIL, user.email);

        //Put password in  @values
        values.put(KEY_PASSWORD, user.password);

        // insert row
        long todo_id = db.insert(TABLE_USERS, null, values);
    }


    public User Authenticate(User user) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS,// Selecting Table
                new String[]{KEY_ID, KEY_USER_NAME, KEY_EMAIL, KEY_PASSWORD},//Selecting columns want to query
                KEY_EMAIL + "=?",
                new String[]{user.email},//Where clause
                null, null, null);

        if (cursor != null && cursor.moveToFirst() && cursor.getCount() > 0) {
            //if cursor has value then in user database there is user associated with this given email
            User user1 = new User(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));

            //Match both passwords check they are same or not
            if (user.password.equalsIgnoreCase(user1.password)) {
                return user1;
            }
        }
        //if user password does not matches or there is no record with that email then return @false
        return null;
    }

    public boolean isEmailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS,// Selecting Table
                new String[]{KEY_ID, KEY_USER_NAME, KEY_EMAIL, KEY_PASSWORD},//Selecting columns want to query
                KEY_EMAIL + "=?",
                new String[]{email},//Where clause
                null, null, null);

        if (cursor != null && cursor.moveToFirst() && cursor.getCount() > 0) {
            //if cursor has value then in user database there is user associated with this given email so return true
            return true;
        }

        //if email does not exist return false
        return false;
    }

    public boolean insertData(String date, String mop, String paidto, String description,String email, String amount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_USER_EXPDATE, date);
        contentValues.put(METHOD, mop);
        contentValues.put(PAIDTO, paidto);
        contentValues.put(DESCRIPTION, description);
        contentValues.put(EXP_EMAIL, email);
        contentValues.put(AMOUNT, amount);
        long result = db.insert(TABLE_USERSEXP, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean insertIncomeData(String date, String mop, String incomefrom, String description,String email, String amount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_USER_INCOMEDATE, date);
        contentValues.put(INCOMEMETHOD, mop);
        contentValues.put(FROMINCOME, incomefrom);
        contentValues.put(INCOMEDESCRIPTION, description);
        contentValues.put(INC_EMAIL, email);
        contentValues.put(INCOMEAMOUNT, amount);
        long result = db.insert(TABLE_USERSINCOME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_USERSEXP, null);
        return res;
    }

    public Cursor getPassword() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_USERS, null);
        return res;
    }

    public Cursor getAllIncome() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_USERSINCOME, null);

        return res;
    }

    public int getTotal() {
        int sum = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(AMOUNT) FROM " + TABLE_USERSEXP, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            sum = cursor.getInt(0);
        }
        return sum;
    }

    public Integer deleteExp (String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_USERSEXP, "email = ?",new String[] {email});
    }

    public Integer deleteIncome (String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_USERSINCOME, "email = ?",new String[] {email});
    }


    public Integer deleteData (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_USERSEXP, "KEY_ID = ?",new String[] {id});
    }

    public int getIncomeTotal() {
        int sum = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(AMOUNT) FROM " + TABLE_USERSINCOME, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            sum = cursor.getInt(0);
        }
        return sum;
    }

    public ArrayList<HashMap<String, String>> GetUsers(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        String query = "SELECT * FROM "+ TABLE_USERSEXP;
        //Cursor cursor = db.rawQuery(query,null);

        Cursor cursor = db.query(TABLE_USERSEXP, new String[]{KEY_ID,KEY_USER_EXPDATE,PAIDTO, METHOD, DESCRIPTION,AMOUNT},
                EXP_EMAIL+ "=?",new String[]{email},null, null, null, null);

        while (cursor.moveToNext()){
            HashMap<String,String> user = new HashMap<>();
            user.put("KEY_ID",cursor.getString(cursor.getColumnIndex(KEY_ID)));
            user.put("KEY_USER_EXPDATE",cursor.getString(cursor.getColumnIndex(KEY_USER_EXPDATE)));
            user.put("METHOD",cursor.getString(cursor.getColumnIndex(METHOD)));
            user.put("PAIDTO",cursor.getString(cursor.getColumnIndex(PAIDTO)));
            user.put("DESCRIPTION",cursor.getString(cursor.getColumnIndex(DESCRIPTION)));
            user.put("AMOUNT",cursor.getString(cursor.getColumnIndex(AMOUNT)));
            userList.add(user);
        }
        Log.d("userList :", String.valueOf(userList));
        return  userList;
    }
    public ArrayList<HashMap<String, String>> GetIncome(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        String query = "SELECT * FROM "+ TABLE_USERSINCOME;
        //Cursor cursor = db.rawQuery(query,null);
        Cursor cursor = db.query(TABLE_USERSINCOME, new String[]{KEY_USER_INCOMEDATE,FROMINCOME, INCOMEMETHOD, INCOMEDESCRIPTION,INCOMEAMOUNT},
                EXP_EMAIL+ "=?",new String[]{email},null, null, null, null);
        while (cursor.moveToNext()){
            HashMap<String,String> user = new HashMap<>();
            user.put("KEY_USER_INCOMEDATE",cursor.getString(cursor.getColumnIndex(KEY_USER_INCOMEDATE)));
            user.put("INCOMEMETHOD",cursor.getString(cursor.getColumnIndex(INCOMEMETHOD)));
            user.put("FROMINCOME",cursor.getString(cursor.getColumnIndex(FROMINCOME)));
            user.put("INCOMEDESCRIPTION",cursor.getString(cursor.getColumnIndex(INCOMEDESCRIPTION)));
            user.put("INCOMEAMOUNT",cursor.getString(cursor.getColumnIndex(INCOMEAMOUNT)));
            userList.add(user);
        }
        Log.d("incomelist :", String.valueOf(userList));
        return  userList;
    }
}
