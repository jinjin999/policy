package com.aqua.anroid.policynoticeapp.User;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.aqua.anroid.policynoticeapp.NonMenuActivity;
import com.aqua.anroid.policynoticeapp.R;


public class NonmemberActivity extends AppCompatActivity {
    ImageView btn_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nonmember);
    }
}
