package com.test.edu.kotlin_fb_demo.frag


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query

import com.test.edu.kotlin_fb_demo.R
import kotlinx.android.synthetic.main.fragment_root.*
import java.util.zip.DataFormatException

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class RootFragment : Fragment() {
    private val mDatabase: DatabaseReference
    private val TAG = "RootFragment"

    init {
        mDatabase = FirebaseDatabase.getInstance().reference
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_root, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // view에 접근하여 프로세스를 진행
        // recyclerView의 방향성과 모양 지정
        val mgr: LinearLayoutManager = LinearLayoutManager(context)
        mgr.reverseLayout = true
        mgr.stackFromEnd = true
        post_list.layoutManager = mgr
        post_list.setHasFixedSize(true) //높이 사이즈를 고정

        // 전체 글 가져오기 (limit)
        val postQuery = getQuery(mDatabase)



    }

    // 차후 추상클래스가 되어서 서브클래스들이 가져오고자하는 데이터는
    // 쿼리 구성을 다양하게 줘서 폼은 같이쓰고 데이터만 다르게 처리되는 내용 세팅
    fun getQuery(databaseRef: DatabaseReference): Query {
        //앞에서부터 100개 가져옴
        val result = databaseRef.child("posts").limitToFirst(100)
        return result
    }
}
