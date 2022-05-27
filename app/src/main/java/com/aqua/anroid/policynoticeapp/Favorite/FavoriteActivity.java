package com.aqua.anroid.policynoticeapp.Favorite;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.aqua.anroid.policynoticeapp.R;
import com.aqua.anroid.policynoticeapp.User.MemberUpdateActivity;

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
import java.util.HashMap;

/*즐겨찾기 클릭 시*/
public class FavoriteActivity extends AppCompatActivity {

    private static String IP_ADDRESS = "10.0.2.2";
    private static String TAG = "phptest";
    private static final String TAG_JSON="root";


//    private TextView tv_list1, tv_content1, tv_list2, tv_content2, tv_list3, tv_content3, tv_list4, tv_content4;
//    private TextView mTextViewResult
//    private TextView textView_list_name,textView_list_content;

    TextView empty_view, empty_view1;
    Button button_main_all;
    String userID;

//    final ArrayList<String> scroll_favoriteList = new ArrayList<String>();
//    ArrayAdapter<String> adapter = null;

    String mJsonString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

//        ListView listView = findViewById(R.id.listView_favorite_list);
//       // FavoriteAdapter favoriteAdapter = new FavoriteAdapter(scroll_favoriteList,getApplicationContext());
//        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, scroll_favoriteList);
//
//        listView.setAdapter(adapter);

        /*tv_list1 = findViewById(R.id.favorite_list_1);
        tv_content1 = findViewById(R.id.favorite_list_content1);
        tv_list2 = findViewById(R.id.favorite_list_2);
        tv_content2 = findViewById(R.id.favorite_list_content2);
        tv_list3 = findViewById(R.id.favorite_list_3);
        tv_content3 = findViewById(R.id.favorite_list_content3);
        tv_list4 = findViewById(R.id.favorite_list_4);
        tv_content4 = findViewById(R.id.favorite_list_content4);
        mTextViewResult = (TextView)findViewById(R.id.textView_main_result);*/

        /*TextView text_fa = findViewById(R.id.text_fa);

        Intent intent = getIntent();
        String text = intent.getStringExtra("즐찾추가");
        Log.d("즐찾", text);
        text_fa.setText(text);*/

//        textView_list_content = findViewById(R.id.textView_list_content);
//        textView_list_name = findViewById(R.id.textView_list_name);

        empty_view = findViewById(R.id.empty_view);
        empty_view1 = findViewById(R.id.empty_view1);

        button_main_all = findViewById(R.id.button_main_all);

        //mTextViewResult.setMovementMethod(new ScrollingMovementMethod());


        /*ImageButton buttonInsert1 = (ImageButton) findViewById(R.id.favorite_btn_1);
        buttonInsert1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name1 = tv_list1.getText().toString();
                String content1 = tv_content1.getText().toString();

                InsertData task = new InsertData();
                task.execute("http://" + IP_ADDRESS + "/favorite.php", name1,content1);

            }
        });

        ImageButton buttonInsert2 = (ImageButton) findViewById(R.id.favorite_btn_2);
        buttonInsert2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name2 = tv_list2.getText().toString();
                String content2 = tv_content2.getText().toString();

                InsertData task = new InsertData();
                task.execute("http://" + IP_ADDRESS + "/favorite.php", name2,content2);

            }
        });

        ImageButton buttonInsert3 = (ImageButton) findViewById(R.id.favorite_btn_3);
        buttonInsert3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name3 = tv_list3.getText().toString();
                String content3 = tv_content3.getText().toString();

                InsertData task = new InsertData();
                task.execute("http://" + IP_ADDRESS + "/favorite.php", name3, content3);

            }
        });

        ImageButton buttonInsert4 = (ImageButton) findViewById(R.id.favorite_btn_4);
        buttonInsert4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name4 = tv_list4.getText().toString();
                String content4 = tv_content4.getText().toString();

                InsertData task = new InsertData();
                task.execute("http://" + IP_ADDRESS + "/favorite.php", name4,content4);

            }
        });

        ImageButton imageButton = (ImageButton) findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),FavoriteActivity.class);
                startActivity(intent);
            }
        });*/

        // 리스트뷰 아이템 클릭
        SharedPreferences sharedPreferences = getSharedPreferences("userID",MODE_PRIVATE);
        userID  = sharedPreferences.getString("userID","");

        Log.d("유저id_to_favo",userID);

        button_main_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetData task = new GetData();
                task.execute(userID);

            }
        });


    }

    // 리스트뷰초기화
    //void Favorite_InitListView() {
//        ListView list = (ListView) findViewById(R.id.listView_favorite_list);
//        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, scroll_favoriteList);
//        // list.setAdapter(adapter);
//        //adapter = new ArrayAdapter<String>(this, R.layout.parsing_list, R.id.TxtMemoListType, scrollItemList);
//
//        list.setAdapter(adapter);


        // 리스트뷰 아이템 클릭
        /*list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    serachServID = scrollServID.get(position);
                    Toast.makeText(getApplicationContext(), "servID : " + serachServID  + " / pos : " + position, Toast.LENGTH_SHORT).show();
                }
        });*/
    //}


    private class GetData extends AsyncTask<String, Void, String>{

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(FavoriteActivity.this,
                    "Please Wait", null, true, true);



        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            Log.d(TAG, "response - " + result);

            if (result != null){
                mJsonString = result;
                showResult();
            }

        }


        @Override
        protected String doInBackground(String... params) {

            String searchKeyword1 = params[0];
            //String searchKeyword2 = params[1];
            //String searchKeyword3 = params[2];
            //String[] searchKeyword1 = new String[100];
            //String[] searchKeyword2 = new String[100];
            //String[] searchKeyword3 = new String[100];

            /*for(int q=0;q<searchKeyword1.length; q++){
                searchKeyword1[q] = params[0];
            }

            for(int q=0;q<searchKeyword2.length; q++){
                searchKeyword2[q] = params[1];
            }

            for(int q=0;q<searchKeyword3.length; q++){
                searchKeyword3[q] = params[2];
            }*/

            String serverURL = "http://10.0.2.2/favorite_query.php";
            //String postParameters = "userID=" + searchKeyword1 + "&item_name=" + searchKeyword2 + "&item_content=" + searchKeyword3;
            //String postParameters = "item_name=" + searchKeyword1 + "&item_content=" + searchKeyword2;
            String postParameters = "userID=" + searchKeyword1;

            Log.d(TAG, "userID_favorite : " + searchKeyword1);

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
                Log.d(TAG, "JSONObject : "+ item);

                String [] item_name = new String[jsonArray.length()];
                String [] item_content = new String[jsonArray.length()];

                item_name[i] = item.getString("item_name");
                item_content[i] = item.getString("item_content");

                empty_view.setText(item_name[0]);
                empty_view1.setText(item_name[1]);

                //ArrayList<HashMap<String,String>>Data = new ArrayList<HashMap<String,String>>();
//                HashMap<String,String> hashMap1 = new HashMap<>();
//                //HashMap<String,String> hashMap2 = new HashMap<>();
//
//                hashMap1.put("item_name",item_name);
//                hashMap1.put("item_content",item_content);
//                Log.d(TAG, "hashMap : " + hashMap1.toString());

                //Data.add(hashMap1);
                //Data.add(hashMap2);
                //Log.d(TAG, "Data : "+ Data);

                Log.d(TAG, "item_name : "+ item_name);
                Log.d(TAG, "item_content : "+ item_content);



                //textView_list_content.setText(item_content);

//                for(int k = 0; k<100; k++){
//                    scroll_favoriteList.add((i+1)+ " : " + Data);
//
//                }
//
//                Log.d(TAG, "scroll_favoriteList : "+ scroll_favoriteList);

                /*for(int q=0; q<lifeArray_items.length ; q++){
                    if(lifeArray_items[q].equals(userLifearray)) {
                        update_lifearray.setSelection(q);
                    }
                }

                for(int t=0; t<trgterIndvdlArray_items.length ; t++){
                    if(trgterIndvdlArray_items[t].equals(userTrgterIndvdl)) {
                        update_trgterIndvdlArray.setSelection(t);
                    }
                }*/
            }

        } catch (JSONException e) {

            Log.d(TAG, "showResult_member : ", e);
        }

    }

}