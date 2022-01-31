package com.aqua.anroid.policynoticeapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class NonMenuActivity extends AppCompatActivity {
    TextView meun_setting;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nonmenu);

        meun_setting = findViewById(R.id.meun_setting);
        meun_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NonMenuActivity.this, NonSettingActivity.class);
                startActivity(intent);
            }
        });
    }


}
