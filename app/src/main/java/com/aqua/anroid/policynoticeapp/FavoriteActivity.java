package com.aqua.anroid.policynoticeapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class FavoriteActivity extends AppCompatActivity {
    private FavoriteAdapter favoriteAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_activity);
        init();
        getData();
    }

    private void init() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        favoriteAdapter = new FavoriteAdapter();
        recyclerView.setAdapter(favoriteAdapter);
    }
    private void getData() {
        // 임의의 데이터입니다.
        List<String> listTitle = Arrays.asList("즐겨찾기1", "즐겨찾기2", "즐겨찾기3", "즐겨찾기4", "즐겨찾기5", "즐겨찾기6", "즐겨찾기7", "즐겨찾기8"
        );
        List<String> listContent = Arrays.asList(
                "요약",
                "요약",
                "요약",
                "요약",
                "요약",
                "요약",
                "요약",
                "요약"
        );
        for (int i = 0; i < listTitle.size(); i++) {
            // 각 List의 값들을 data 객체에 set 해줍니다.
            FavoriteData favoriteData = new FavoriteData();
            favoriteData.setTitle(listTitle.get(i));
            favoriteData.setContent(listContent.get(i));
            //data.setResId(listResId.get(i));
            // 각 값이 들어간 data를 adapter에 추가합니다.
            favoriteAdapter.addItem(favoriteData);
        }
        // adapter의 값이 변경되었다는 것을 알려줍니다.
        favoriteAdapter.notifyDataSetChanged();
    }

}