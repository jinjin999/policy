package com.aqua.anroid.policynoticeapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.aqua.anroid.policynoticeapp.User.LoginActivity;

//초기화면 엑티비티
public class SplashActivity extends AppCompatActivity {
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_splash);
        //startLoading();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent splash_intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(splash_intent);
                finish();
            }
        },2000);

        /*try{
            Thread.sleep(2000);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }*/

    }
   /*private void startLoading(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 4000);
    }*/
}
