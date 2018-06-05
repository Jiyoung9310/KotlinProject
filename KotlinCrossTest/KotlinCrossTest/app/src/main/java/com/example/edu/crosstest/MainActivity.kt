package com.example.edu.crosstest

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.cell_layout.*
import kotlinx.android.synthetic.main.cell_layout.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    val SORT = "recency" //최신순
    val MAX_PAGE = 40 //한번에 가져오는 갯수

    var myAdapter: MyAdapter? = null
    var papers = ArrayList<Documents>()
    var cur_page = 1
    var isMore = false //더보기;
    lateinit var keyword: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // 뷰 초기화 -> findViewById를 사용하는 코드는 생략됨

        // 리스트뷰에 아답텨 연결
        myAdapter = MyAdapter()
        result_list.adapter = myAdapter
    }

    // 검색 버튼 클릭시 호출
    fun onSearch(view: View) {
        //검색어 획득
        keyword = search_input.text.toString()
        //유효성 검사
        if (TextUtils.isEmpty(keyword)) {
            search_input.error = "정확하게 입력하세요"
            return
        }
        //새로운 검색은 무조건 첫페이지를 출력
        cur_page = 1
        //로딩 처리
        showLoading(true)
        //검색 결과 초기화
        papers.clear()
        //통신 - 비동기 - 끝나면 - 로딩 완료, 결과 확인 - 화면 처리
        getImageQuery()
    }

    fun getImageQuery() {
        val res = Net.kakaoImp.imgSearch(keyword, SORT, cur_page, MAX_PAGE)
        res.enqueue(object : Callback<Model> {
            override fun onResponse(call: Call<Model>, response: Response<Model>) {
                if (response.isSuccessful) {
                    for (doc in response.body()!!.documents) {
                        Log.i("KT", doc.thumbnail_url)
                    }
                    //검색결과를 한번에 받는다
                    papers.addAll(response.body()!!.documents)
                    // 리스트뷰를 갱신 ( 데이터가 변경됨 )
                    myAdapter?.notifyDataSetChanged()

                    Toast.makeText(this@MainActivity, "로딩 완료", Toast.LENGTH_SHORT).show()
                    isMore = false
                }
                showLoading(false)
            }

            override fun onFailure(call: Call<Model>, t: Throwable) {
                showLoading(false)
            }
        })
    }

    fun showLoading(flag: Boolean) {
        loading.visibility = if (flag) View.VISIBLE else View.GONE
        if(!flag) search_input.error = null
    }

    inner class ViewHolder {
        lateinit var cell_img: ImageView
        lateinit var cell_t1: TextView
        lateinit var cell_t2: TextView
    }

    inner class MyAdapter: BaseAdapter() {
        lateinit var viewHolder: ViewHolder
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            //엘비스 연산을 통해 null 일 때(최초 생성 시)와 nul 이 아닐 때(재활용 시기) 처리가 가능해졌다.
            var view = convertView ?: layoutInflater.inflate(R.layout.cell_layout, parent, false)
            if (convertView == null) {
                // LayoutInflater : xml -> View로 생성하는 기능 제공\
                viewHolder = ViewHolder()
                //사진 셋팅
                viewHolder.cell_img = view.cell_img
                // textView 두줄 셋팅 (display_sitename, collection)
                viewHolder.cell_t1 = view.cell_t1
                viewHolder.cell_t2 = view.cell_t2
                view.tag = viewHolder
            } else {
                viewHolder = convertView.tag as ViewHolder
            }

            //데이터 획득
            val doc: Documents = getItem(position)
            viewHolder.cell_t1.text = doc.display_sitename
            viewHolder.cell_t2.text = doc.collection
            Picasso.get().load(doc.thumbnail_url).into(viewHolder.cell_img)

            //현재 목록 상 마지막 셀을 요청하면
            if (position == papers.size - 1 && !isMore) {
                cur_page++
                isMore = true
                //더보기 더 가져오기
                showLoading(true)
                getImageQuery()
            }
            return view
        }

        override fun getItem(position: Int): Documents {
            return papers[position]
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getCount(): Int {
            return papers.size
        }
    }
}
