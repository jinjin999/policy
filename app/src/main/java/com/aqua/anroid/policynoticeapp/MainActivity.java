package com.aqua.anroid.policynoticeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /* activity_memberupate

        Spinner spin_join_age = (Spinner) findViewById(R.id.join_age);
        Spinner spin_join_gender = (Spinner) findViewById(R.id.join_gender);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> age_adapter = ArrayAdapter.createFromResource(this,
                R.array.age_array, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> gender_adapter = ArrayAdapter.createFromResource(this,
                R.array.gender_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        age_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spin_join_age.setAdapter(age_adapter);
        spin_join_gender.setAdapter(gender_adapter);


        */

        /*
        Button login_btn = (Button) findViewById(R.id.login_btn);

        //btn_login Button의 Click이벤트
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, StartViewActivity.class);
                startActivity(intent);
            }
        });
        */
    }
}