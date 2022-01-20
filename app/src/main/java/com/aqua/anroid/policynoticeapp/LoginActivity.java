package com.aqua.anroid.policynoticeapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class LoginActivity extends AppCompatActivity {
    private static String TAG = "phpquerytest";

    private static final String TAG_JSON="root";
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "userID";
    private static final String TAG_COUNTRY ="userPass";

    EditText mEditTextSearchKeyword1, mEditTextSearchKeyword2;
    private Button btn_login, btn_register;
    TextView mTextViewResult;
    String mJsonString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //et_id = findViewById(R.id.login_id);
        //et_pass = findViewById(R.id.login_pw);
        btn_login = findViewById(R.id.login_btn);
        btn_register = findViewById(R.id.login_joinbtn);
        mTextViewResult = (TextView) findViewById(R.id.login_result);
        mEditTextSearchKeyword1 = (EditText) findViewById(R.id.login_id);
        mEditTextSearchKeyword2 = (EditText) findViewById(R.id.login_pw);

        /*mRecyclerView = (RecyclerView) findViewById(R.id.listView_main_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));*/


        // 회원가입 버튼을 클릭 시 수행
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //mArrayList.clear();
                GetData task = new GetData();
                task.execute( mEditTextSearchKeyword1.getText().toString(), mEditTextSearchKeyword2.getText().toString());
            }
        });
        //mArrayList = new ArrayList<>();
    }

    private class GetData extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(LoginActivity.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            mTextViewResult.setText(result);
            Log.d(TAG, "response - " + result);

            if (result == null){

                mTextViewResult.setText(errorString);
            }
            else {

                mJsonString = result;
                showResult();
            }
        }


        @Override
        protected String doInBackground(String... params) {

            String searchKeyword1 = params[0];
            String searchKeyword2 = params[1];

            String serverURL = "http://10.0.2.2/query.php";
            String postParameters = "userID=" + searchKeyword1 + "&userPass=" + searchKeyword2;


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
        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);

                /*String id = item.getString(TAG_ID);
                String userID = item.getString(TAG_NAME);
                String userPass = item.getString(TAG_COUNTRY);*/

                /*HashMap<String,String> hashMap = new HashMap<>();

                hashMap.put(TAG_ID, id);
                hashMap.put(TAG_NAME, userID);
                hashMap.put(TAG_COUNTRY, userPass);*/

                Intent intent = new Intent(LoginActivity.this, MemberActivity.class);
                startActivity(intent);

                mTextViewResult.setText("");

                //mArrayList.add(hashMap);
            }

            /*ListAdapter adapter = new SimpleAdapter(
                    MainActivity.this, mArrayList, R.layout.item_list,
                    new String[]{TAG_ID,TAG_NAME, TAG_COUNTRY},
                    new int[]{R.id.textView_list_id, R.id.textView_list_name, R.id.textView_list_country}
            );

            mListViewList.setAdapter(adapter);*/

        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }

}