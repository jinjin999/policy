package com.aqua.anroid.policynoticeapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Favorite extends AppCompatActivity {

    private static String IP_ADDRESS = "10.0.2.2";
    private static String TAG = "phptest";

    private EditText mEditTextName;
    private EditText mEditTextCountry;
    private TextView mTextViewResult;
    private ArrayList<FavoriteData> mArrayList;
    private FavoriteAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private EditText mEditTextSearchKeyword;
    private String mJsonString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_result);

        mTextViewResult = (TextView)findViewById(R.id.textView_main_result);
        mRecyclerView = (RecyclerView) findViewById(R.id.listView_main_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), 1));

        mTextViewResult.setMovementMethod(new ScrollingMovementMethod());



        mArrayList = new ArrayList<>();

        mAdapter = new FavoriteAdapter(this, mArrayList);
        mRecyclerView.setAdapter(mAdapter);



        Button button_all = (Button) findViewById(R.id.button_main_all);
        button_all.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                mArrayList.clear();
                mAdapter.notifyDataSetChanged();

                GetData task = new GetData();
                task.execute( "http://" + IP_ADDRESS + "/getjson.php", "");
            }
        });

        Button button_main = (Button) findViewById(R.id.button_main);
        button_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(getApplicationContext(),SearchActivity.class);
                startActivity(intent3);
            }
        });

        Intent intent2 = getIntent();
        String name = intent2.getStringExtra("이름_favorite");
        String content = intent2.getStringExtra("내용_favorite");


        LayoutInflater inflater = getLayoutInflater();
        View view =inflater.inflate(R.layout.item_list,null);
        Button deletebutton = (Button) view.findViewById(R.id.deletebutton);
        deletebutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                mArrayList.clear();
                DeleteData task = new DeleteData();
                task.execute(name,content);
            }
        });


    }

    class DeleteData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog2;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog2 = ProgressDialog.show(Favorite.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result2) {
            super.onPostExecute(result2);
            mTextViewResult.setText(result2);
            progressDialog2.dismiss();

            Toast.makeText(getApplicationContext(), result2, Toast.LENGTH_SHORT).show();


            Log.d(TAG, "POST response  - " + result2);
        }


        @Override
        protected String doInBackground(String... params) {
            String searchKeyword3 = params[3];
            String searchKeyword4 = params[4];

            String serverURL = "http://10.0.2.2/delete.php";
            String postParameters = "content=" + searchKeyword3 + "&name=" + searchKeyword4;


            //PHP 파일을 실행시킬 수 있는 주소와 전송할 데이터를 준비
            //POST 방식으로 데이터 전달시에는 데이터가 주소에 직접 입력되지 않는다.
            //String serverURL = (String)params[0];

            //HTTP 메시지 본문에 포함되어 전송되기 때문에 따로 데이터를 준비해야 한다,
            //전송할 데이터는 '이름=값' 형식이며 여러개를 보내야 할 경우에는 항목 사이에 &를 추가한다.
            //여기에 적어준 이름을 나중에 PHP에서 사용하여 값을 얻게 된다.
            /*String postParameters = "userID=" + userID + "& userPass=" + userPass + "& userAge=" + userAge +
                    "& userGender=" + userGender + "& userArea=" + userArea + "& userJob=" + userJob;*/


            try {

                //HttpURLConnection 클래스를 사용하여 POST방식으로 데이터를 전송
                URL url2 = new URL(serverURL);   //주소가 저장된 변수를 입력
                HttpURLConnection httpURLConnection = (HttpURLConnection) url2.openConnection();

                httpURLConnection.setReadTimeout(5000); //5초안에 응답이 오지 않으면 예외가 발생
                httpURLConnection.setConnectTimeout(5000);  //5초안에 연결이 안되면 예외가 발생
                httpURLConnection.setRequestMethod("POST"); //요청방식으로 POST로 한다.
                httpURLConnection.connect();


                OutputStream outputStream2 = httpURLConnection.getOutputStream();
                //전송할 데이터가 저장된 변수를 이곳에 입력한다. 인코딩을 고려해야한다.
                outputStream2.write(postParameters.getBytes("UTF-8"));
                outputStream2.flush();
                outputStream2.close();


                //응답 읽기
                int responseStatusCode2 = httpURLConnection.getResponseCode();
                Log.d(TAG, "POST response code - " + responseStatusCode2);

                InputStream inputStream2;
                if(responseStatusCode2 == HttpURLConnection.HTTP_OK) {
                    inputStream2 = httpURLConnection.getInputStream();
                }
                else{
                    inputStream2 = httpURLConnection.getErrorStream();
                }


                //StringBuilder를 사용하여 수신되는 데이터를 저장
                InputStreamReader inputStreamReader2 = new InputStreamReader(inputStream2, "UTF-8");
                BufferedReader bufferedReader2 = new BufferedReader(inputStreamReader2);

                StringBuilder sb2 = new StringBuilder();
                String line2 = null;

                while((line2 = bufferedReader2.readLine()) != null){
                    sb2.append(line2);
                }


                bufferedReader2.close();

                return sb2.toString();   //저장된 데이터를 스트링으로 변환하여 리턴턴


            } catch (Exception e) {

                Log.d(TAG, "DeleteData: Error ", e);

                return new String("Error: " + e.getMessage());
            }

        }
    }



    private class GetData extends AsyncTask<String, Void, String>{

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(Favorite.this,
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

            String serverURL = params[0];
            String postParameters = params[1];


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

                Log.d(TAG, "GetData : Error ", e);
                errorString = e.toString();

                return null;
            }

        }
    }


    private void showResult(){

        String TAG_JSON="webnautes";
        String TAG_ID = "id";
        String TAG_NAME = "name";
        String TAG_CONTENT ="content";


        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);

                String id = item.getString(TAG_ID);
                String name = item.getString(TAG_NAME);
                String content = item.getString(TAG_CONTENT);

                FavoriteData personalData = new FavoriteData();

                personalData.setMember_id(id);
                personalData.setMember_name(name);
                personalData.setMember_content(content);

                mArrayList.add(personalData);
                mAdapter.notifyDataSetChanged();
            }



        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }

}