package com.aqua.anroid.policynoticeapp.Favorite;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;


import com.aqua.anroid.policynoticeapp.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class FavoriteAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<FavoriteData> favoriteDatalist; //Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private static String IP_ADDRESS = "10.0.2.2";
    private Activity activity;
    public FavoriteAdapter(Context context, ArrayList<FavoriteData> favoriteDatalist, Activity activity) {
        this.context = context;
        this.favoriteDatalist = favoriteDatalist;
        //다이얼로그 때문에 activity 선언
        this.activity=activity;
    }

    //Adapter에 사용되는 데이터의 개수를 리턴
    @Override
    public int getCount() {
        return favoriteDatalist.size();
    }

    //뷰홀더 추가
    class ViewHolder{
        TextView textview_list_name;
        TextView textview_list_content;
    }

    //i에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴
    @Override
    public View getView(int i, View view, ViewGroup parent) {
        //int pos = i;
        Context context = parent.getContext();
        final ViewHolder holder;//아이템 내 view들을 저장할 holder 생성

        //"item_list" Layout을 inflate하여 view 참조 획득
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            //최초 생성 view인 경우, inflation -> ViewHolder 생성 -> 해당 View에 setTag 저장
            view = inflater.inflate(R.layout.favorite_item_list, parent, false);

            holder = new ViewHolder();

            //화면에 표시될 View(Layoutㅇ inflate된)으로부터 위젯에 대한 참조 획득
            holder.textview_list_name = (TextView) view.findViewById(R.id.textView_list_name);
            holder.textview_list_content = (TextView) view.findViewById(R.id.textView_list_content);

            //해당 view에 setTag로 Holder 객체 저장
            view.setTag(holder);
        }

        else{
            //view가 이미 생성된 적이 있다면, 저장되어 있는 Holder 가져오기
            holder=(ViewHolder) view.getTag();
        }

        //Holder 객체 내의 뷰를 세팅
        holder.textview_list_name.setText(favoriteDatalist.get(i).getMember_name());
        holder.textview_list_content.setText(favoriteDatalist.get(i).getMember_content());


        /*성공한거
        TextView textView_list_name = view.findViewById(R.id.textView_list_name);
        TextView textView_list_content = view.findViewById(R.id.textView_list_content);
        FavoriteData favoriteData = favoriteDatalist.get(i);
        textView_list_name.setText(favoriteData.getMember_name());
        textView_list_content.setText(favoriteData.getMember_content());*/

        /*LinearLayout cmdArea = (LinearLayout) view.findViewById(R.id.cmdArea);
        cmdArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),favoriteDatalist.get(i).getMember_name(),Toast.LENGTH_LONG).show();
                DeleteData task = new DeleteData();
                task.execute("http://" + IP_ADDRESS + "/delete.php",textView_list_name.getText().toString());
            }
        });*/

        //삭제 버튼 클릭 시
        Button deletebutton = (Button) view.findViewById(R.id.deletebutton);
        //ViewHolder finalHolder = holder;
        deletebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteData task = new DeleteData();
                task.execute("http://" + IP_ADDRESS + "/favorite_delete.php", holder.textview_list_name.getText().toString());
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
                alertDialogBuilder
                        .setMessage("'"+favoriteDatalist.get(i).getMember_name()+"'"+" 삭제 완료")
                        .setCancelable(true)
                        .setPositiveButton("확인",
                                new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int arg1) {
                                        context.startActivity(new Intent(context, FavoriteActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

                                    }
                                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
        //해당 view 반납
        return view;
    }

    //지정한 위치(i)에 있는 데이터 리턴턴
    @Override
    public Object getItem(int i) {
        return favoriteDatalist.get(i);
    }


    //지정한 위치(i)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴
    @Override
    public long getItemId(int i) {
        return i;
    }


    // 아이템 데이터 추가를 위한 함수.
    /*public void addItem(String textView_list_name, String textView_list_content) {
        FavoriteData item = new FavoriteData();

        item.setMember_name(textView_list_name);
        item.setMember_content(textView_list_content);

        favoriteDatalist.add(item);
    }*/


    class DeleteData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //progressDialog = ProgressDialog.show(FavoriteActivity.this,
            //"Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

        }


        @Override
        protected String doInBackground(String... params) {


            String name = (String)params[1];


            String serverURL = (String)params[0];
            String postParameters = "name=" + name ;


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

                // Log.d(TAG, "DeleteData: Error ", e);

                return new String("Error: " + e.getMessage());
            }

        }
    }
}
