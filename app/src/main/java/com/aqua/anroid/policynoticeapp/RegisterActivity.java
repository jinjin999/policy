package com.aqua.anroid.policynoticeapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class RegisterActivity extends AppCompatActivity {

    private static String IP_ADDRESS = "10.0.2.2";
    private static String TAG = "register";

    private EditText et_id, et_pass;
    private Spinner et_age, et_gender, et_area, et_job;
    private Button btn_register;
    private TextView mTextViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) { // 액티비티 시작시 처음으로 실행되는 생명주기!
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        // 아이디 값 찾아주기
        et_id = findViewById(R.id.join_id);
        et_pass = findViewById(R.id.join_pw);
        et_age = findViewById(R.id.join_age);
        et_gender = findViewById(R.id.join_gender);
        et_area = findViewById(R.id.join_area);
        et_job = findViewById(R.id.join_job);
        mTextViewResult = (TextView)findViewById(R.id.textView_main_result);


        mTextViewResult.setMovementMethod(new ScrollingMovementMethod());


        // 회원가입 버튼 클릭 시 수행
        btn_register = findViewById(R.id.join_savebtn);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // EditText에 현재 입력되어있는 값을 get(가져온다)해온다.
                String userID = et_id.getText().toString();
                String userPass = et_pass.getText().toString();
                String userAge = et_age.getSelectedItem().toString();
                String userGender = et_gender.getSelectedItem().toString();
                String userArea = et_area.getSelectedItem().toString();
                String userJob = et_job.getSelectedItem().toString();

                InsertData task = new InsertData();
                task.execute("http://" + IP_ADDRESS + "/insert.php", userID,userPass,userAge,userGender,userArea,userJob);

                //et_id.setText("");
                //et_pass.setText("");
                

            }
        });

    }
    class InsertData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(RegisterActivity.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();

            if(result.equals("새로운 사용자를 추가했습니다.")){
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
            else{
                mTextViewResult.setText(result);
            }



            Log.d(TAG, "POST response  - " + result);
        }


        @Override
        protected String doInBackground(String... params) {

            String userID = (String)params[1];
            String userPass = (String)params[2];
            String userAge = (String)params[3];
            String userGender = (String)params[4];
            String userArea = (String)params[5];
            String userJob = (String)params[6];


            String serverURL = (String)params[0];
            String postParameters = "userID=" + userID + "& userPass=" + userPass + "& userAge=" + userAge +
                    "& userGender=" + userGender + "& userArea=" + userArea + "& userJob=" + userJob;


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