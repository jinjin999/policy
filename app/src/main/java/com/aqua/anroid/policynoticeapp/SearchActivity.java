package com.aqua.anroid.policynoticeapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SearchActivity extends AppCompatActivity {

    private static String IP_ADDRESS = "10.0.2.2";
    private static String TAG = "phptest";

    private TextView tv_list1, tv_content1, tv_list2, tv_content2, tv_list3, tv_content3, tv_list4, tv_content4;
    private TextView mTextViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);



        tv_list1 = findViewById(R.id.favorite_list_1);
        tv_content1 = findViewById(R.id.favorite_list_content1);
        tv_list2 = findViewById(R.id.favorite_list_2);
        tv_content2 = findViewById(R.id.favorite_list_content2);
        tv_list3 = findViewById(R.id.favorite_list_3);
        tv_content3 = findViewById(R.id.favorite_list_content3);
        tv_list4 = findViewById(R.id.favorite_list_4);
        tv_content4 = findViewById(R.id.favorite_list_content4);
        mTextViewResult = (TextView)findViewById(R.id.textView_main_result);

        mTextViewResult.setMovementMethod(new ScrollingMovementMethod());

        ImageButton buttonInsert1 = (ImageButton) findViewById(R.id.favorite_btn_1);
        buttonInsert1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name1 = tv_list1.getText().toString();
                String content1 = tv_content1.getText().toString();

                InsertData task = new InsertData();
                task.execute("http://" + IP_ADDRESS + "/insert.php", name1,content1);

            }
        });

        ImageButton buttonInsert2 = (ImageButton) findViewById(R.id.favorite_btn_2);
        buttonInsert2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name2 = tv_list2.getText().toString();
                String content2 = tv_content2.getText().toString();

                InsertData task = new InsertData();
                task.execute("http://" + IP_ADDRESS + "/insert.php", name2,content2);

            }
        });

        ImageButton buttonInsert3 = (ImageButton) findViewById(R.id.favorite_btn_3);
        buttonInsert3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //int num = 0;
                //num++;
                String name3 = tv_list3.getText().toString();
                String content3 = tv_content3.getText().toString();

                // if(num%2==0) {
                InsertData task = new InsertData();
                task.execute("http://" + IP_ADDRESS + "/insert.php", name3, content3);
                // }
                // else{
                //InsertData task = new InsertData();
                //task.execute("http://" + IP_ADDRESS + "/delete.php", name3, content3);
                // }

            }
        });

        ImageButton buttonInsert4 = (ImageButton) findViewById(R.id.favorite_btn_4);
        buttonInsert4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name4 = tv_list4.getText().toString();
                String content4 = tv_content4.getText().toString();

                InsertData task = new InsertData();
                task.execute("http://" + IP_ADDRESS + "/insert.php", name4,content4);

            }
        });

        ImageButton imageButton = (ImageButton) findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MenuActivity.class);
                startActivity(intent);
            }
        });

    }

    class InsertData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(SearchActivity.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            mTextViewResult.setText(result);
            Log.d(TAG, "POST response  - " + result);
        }


        @Override
        protected String doInBackground(String... params) {

            String name = (String)params[1];
            String content = (String)params[2];

            String serverURL = (String)params[0];
            String postParameters = "name=" + name + "&content=" + content;


            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "POST response code - " + responseStatusCode);

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
                String line = null;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }


                bufferedReader.close();


                return sb.toString();


            } catch (Exception e) {

                Log.d(TAG, "InsertData: Error ", e);

                return new String("Error: " + e.getMessage());
            }

        }
    }




}