package com.aqua.anroid.policynoticeapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.aqua.anroid.policynoticeapp.Favorite.Favorite;

public class MenuActivity extends AppCompatActivity {
    TextView meun_setting, menu_search, menu_bookmark;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Intent intent2 = getIntent();
        String userID = intent2.getStringExtra("유저id_setting");

        meun_setting = findViewById(R.id.meun_setting);
        menu_search = findViewById(R.id.menu_search);
        menu_bookmark = findViewById(R.id.menu_bookmark);

        meun_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, SettingActivity.class);
                intent.putExtra("유저id_setting",userID);
                startActivity(intent);
            }
        });

        menu_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        menu_bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, Favorite.class);
                startActivity(intent);
            }
        });
    }
}
