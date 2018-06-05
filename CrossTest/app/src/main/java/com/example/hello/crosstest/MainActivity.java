package com.example.hello.crosstest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {
    EditText search_input;
    ListView result_list;
    ProgressBar loading;
    MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //화면 구성
        setContentView(R.layout.activity_main);
        //뷰 초기화
        search_input = findViewById(R.id.search_input);
        result_list = findViewById(R.id.result_list);
        loading = findViewById(R.id.loading);
        myAdapter = new MyAdapter();

        result_list.setAdapter(myAdapter);

    }

    public void onSearch(View view) {

        //검색어 획득
        String keyword = search_input.getText().toString();
        //유효성 검사
        if(TextUtils.isEmpty(keyword)) {
            search_input.setError("정확하게 입력하세요");
            return;
        }
        //로딩 처리
        showLoading(true);
        //통신 - 비동기 - 끝나면 - 로딩 완료, 결과 확인 - 화면 처리

    }

    public void showLoading(boolean flag) {

        loading.setVisibility(flag ? View.VISIBLE : View.GONE);
    }
    //리스트뷰의 어댑터
    class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            return null;
        }
    }

}
