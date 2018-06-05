package com.example.hello.crosstest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    EditText search_input;
    ListView result_list;
    ProgressBar loading;
    MyAdapter myAdapter;
    String keyword;
    int cur_page = 1;
    boolean isMore = false;
    ArrayList<Model.Document> papers = new ArrayList<>();

    final String SORT = "recency";
    final int MAX_PAGE = 40;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //화면 구성
        setContentView(R.layout.activity_main);
        //뷰 초기화
        search_input = findViewById(R.id.search_input);
        result_list = findViewById(R.id.result_list);
        loading = findViewById(R.id.loading);
        //리스트 뷰에 어댑터 연결
        myAdapter = new MyAdapter();
        result_list.setAdapter(myAdapter);

    }

    public void onSearch(View view) {

        //검색어 획득
        keyword = search_input.getText().toString();
        //유효성 검사
        if(TextUtils.isEmpty(keyword)) {
            search_input.setError("정확하게 입력하세요");
            return;
        }
        //로딩 처리
        showLoading(true);
        //
        //통신 - 비동기 - 끝나면 - 로딩 완료, 결과 확인 - 화면 처리
        getImageQuery();
    }

    public void getImageQuery() {
        Call<Model> res = Net.getInstance().getKakaoImp().imgSearch(keyword, SORT, cur_page, MAX_PAGE);
        res.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                if(response.isSuccessful()) {
                    for(Model.Document doc : response.body().getDocuments()) {
                        Log.i("KT", doc.getThumbnail_url());
                    }
                    //검색 결과를 한번에 받는다
                    papers.addAll(response.body().getDocuments());
                    //리스트뷰를 갱신 (데이터가 변경됨)
                    myAdapter.notifyDataSetChanged();

                    Toast.makeText(MainActivity.this, "로딩 완료", Toast.LENGTH_SHORT).show();
                    isMore = false;
                }
                showLoading(false);
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                showLoading(false);
            }
        });

    }

    public void showLoading(boolean flag) {
        loading.setVisibility(flag ? View.VISIBLE : View.GONE);
    }

    class ViewHolder {
        ImageView cell_img;
        TextView cell_t1;
        TextView cell_t2;
    }

    //리스트뷰의 어댑터
    class MyAdapter extends BaseAdapter {
        ViewHolder viewHolder;

        //cell 의 총 갯수
        @Override
        public int getCount() {
            return papers.size();
        }

        //특정 인덱스에 해당되는 데이터를 획득
        @Override
        public Model.Document getItem(int i) {
            return papers.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        //리스트뷰 화면을 구성할 떄 특정 번째의 인덱스를 전달하여 호출
        //스크롤을 통해서 새로운 데이터가 들어와도 호출
        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            if(convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.cell_layout, viewGroup, false);

                viewHolder = new ViewHolder();
                viewHolder.cell_img = convertView.findViewById(R.id.cell_img);
                viewHolder.cell_t1 = convertView.findViewById(R.id.cell_t1);
                viewHolder.cell_t2 = convertView.findViewById(R.id.cell_t2);
                convertView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            //데이터 바인딩
            Model.Document doc = getItem(i);
            viewHolder.cell_t1.setText(doc.getDisplay_sitename());
            viewHolder.cell_t2.setText(doc.getCollection());
            Picasso.get().load(doc.getThumbnail_url()).into(viewHolder.cell_img);

            //현재 목록상 마지막 셀을 요청하면
            if(i == papers.size()-1 && !isMore) {
                cur_page++;
                isMore = true;
                //더보기
                showLoading(true);
                getImageQuery();
            }

            return convertView;
        }
    }

}
