package com.example.dailyexpenses;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;

public class SplashActivity extends AppCompatActivity {

    boolean flag;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        SharedPreferences wmbPreference = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isFirstRun = wmbPreference.getBoolean("FIRSTRUN", true);
//        if (isFirstRun)
//        {
//            // Code to run once
//            splashlogin();
//            flag=true;
//            SharedPreferences.Editor editor = wmbPreference.edit();
//            editor.putBoolean("FIRSTRUN", false);
//            //editor.commit();
//            editor.apply();
//        }else {
//            splashsaving();
//        }
        splashlogin();
    }
    void splashlogin(){
        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        },3000);
    }
    void splashsaving(){
        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(SplashActivity.this,DayExpenses.class);
                startActivity(intent);
                finish();
            }
        },3000);
    }
}
