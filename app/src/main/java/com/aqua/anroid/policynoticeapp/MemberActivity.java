package com.aqua.anroid.policynoticeapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

//import com.mobile.PolicyApp.R;

public class MemberActivity extends AppCompatActivity {
    private static String IP_ADDRESS = "10.0.2.2";
    private static String TAG = "phptest";
    private static final String TAG_JSON="root";

    TextView member_age, member_gender, member_area, member_job;
    TextView user_id_main, user_result_main;
    EditText user_id;
    ImageView btn_menu;
    String mJsonString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);


        Spinner ss_mpopular = (Spinner) findViewById(R.id.s_mpopular);
        String[] a_mpopular = getResources().getStringArray(R.array.popular_array);

        ArrayAdapter<String> adapter7;
        adapter7 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, a_mpopular);
        ss_mpopular.setAdapter(adapter7);

        Button resetbutton = (Button) findViewById(R.id.resetbutton);
        resetbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ResetActivity.class);
                startActivity(intent);
            }
        });

        user_result_main = findViewById(R.id.user_result_main);
        user_id_main = findViewById(R.id.user_id_main);
        Intent intent2 = getIntent();
        String userID = intent2.getStringExtra("유저id");

        Log.d(TAG, "intent결과 : " + userID);
        user_id_main.setText(userID);
        member_age = findViewById(R.id.age2);
        user_id = findViewById(R.id.login_id);
        member_gender = findViewById(R.id.gender2);
        member_area = findViewById(R.id.area2);
        member_job = findViewById(R.id.job2);

        btn_menu = findViewById(R.id.menuimage);

        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MemberActivity.this, MenuActivity.class);
                intent.putExtra("유저id_setting",userID);
                startActivity(intent);

            }
        });
        GetData task = new GetData();
        task.execute(user_id_main.getText().toString());

    }

    private class GetData extends AsyncTask<String, Void, String>{

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(MemberActivity.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            user_result_main.setText(result);
            Log.d(TAG, "response - " + result);

            if (result == null){

                user_result_main.setText(errorString);
            }
            else {

                mJsonString = result;
                showResult();
            }
        }


        @Override
        protected String doInBackground(String... params) {

            String searchKeyword1 = params[0];

            String serverURL = "http://10.0.2.2/main_userinfo.php";
            String postParameters = "userID=" + searchKeyword1;
            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }

                bufferedReader.close();

                return sb.toString().trim();


            } catch (Exception e) {

                Log.d(TAG, "InsertData: Error ", e);
                errorString = e.toString();

                return null;
            }

        }
    }

    private void showResult(){
        String TAG_JSON="root";


        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);
                Log.d(TAG, "JSONObject : "+ item);

                String userAge = item.getString("userAge");
                String userGender = item.getString("userGender");
                String userArea = item.getString("userArea");
                String userJob = item.getString("userJob");

                member_age.setText(userAge);
                member_gender.setText(userGender);
                member_area.setText(userArea);
                member_job.setText(userJob);
            }

        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }

}