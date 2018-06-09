package com.test.edu.kotlin_fb_demo.frag


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

import com.test.edu.kotlin_fb_demo.R
import com.test.edu.kotlin_fb_demo.models.Post
import com.test.edu.kotlin_fb_demo.ui.PostDetailActivity
import com.test.edu.kotlin_fb_demo.viewholder.PostViewHolder
import kotlinx.android.synthetic.main.cell_post.*
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
    private val TAG = "RootFragment"
    private val mDatabase: DatabaseReference
    private var mAdapter: FirebaseRecyclerAdapter<Post, PostViewHolder>? = null

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
        // 뷰홀더 ok
        // 데이터 담는 그릇 ok
        // 리사이클러뷰와 결합한 구조 처리 -> 어댑터
        val option = FirebaseRecyclerOptions.Builder<Post>().setQuery(postQuery, Post::class.java).build()  // 서버에서 데이터를 받아와 Post 포맷에 맞게 담는 작업
        mAdapter = object : FirebaseRecyclerAdapter<Post, PostViewHolder>(option) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
                // xml -> View를 만드는 작업 : layoutInflater
                val view = LayoutInflater.from(context).inflate(R.layout.cell_post, parent, false)
                return PostViewHolder(view)
            }

            override fun onBindViewHolder(holder: PostViewHolder, position: Int, model: Post) {
                //해당 글 고유키 획득
                val postRef = getRef(position)
                val key = postRef.key

                // 상세보기
                holder.itemView.setOnClickListener {
                    //상세 페이지 이동 전개 -> 글 고유 번호
                    val intent = Intent(activity, PostDetailActivity::class.java)
                    //글 고유번호
                    intent.putExtra(PostDetailActivity.EXTRA_KEY, key)
                    startActivity(intent)
                }
                // 좋아요 클릭
                val p = model
                if ( p.likes.containsKey(getUserID()) ) {
                    holder.star.setImageResource(R.drawable.ic_toggle_star_24)
                } else {
                    holder.star.setImageResource(R.drawable.ic_toggle_star_outline_24)
                }

                // 셀 하나 하나를 셋팅해
                holder.bindToPost(model, View.OnClickListener { v ->
                    // 좋아요 누르면 해당 유저가 눌렀음을 디비에 반영
                    // 토글 효과
                    // 전체 글에서 해당 글을 찾아서 수정
                    // 내 글에서 해당 글을 찾아서 수정
                    val totalPostRef = mDatabase.child("posts").child(key!!)
                    val myPostRef = mDatabase.child("user-posts").child(model.uid).child(key)
                    changeStarState(totalPostRef)
                    changeStarState(myPostRef)
                })
            }
        }
        // 어댑터를 리사이클러뷰에 연결
        post_list.adapter = mAdapter
        // 라이프사이클에 맞춰 리스닝 처리(글이 새로 추가되거나 삭제되었을 때, 새로 읽을 때 자동반응)


    }

    fun changeStarState(ref: DatabaseReference) {
        // star를 누르면 트렌젝션을 걸어서 수정
        ref.runTransaction(object : Transaction.Handler {
            override fun onComplete(p0: DatabaseError?, p1: Boolean, p2: DataSnapshot?) {
                Toast.makeText(context, if(p1) "변경 성공" else "변경 실패", Toast.LENGTH_SHORT).show()
            }
            override fun doTransaction(p0: MutableData): Transaction.Result {
                // 데이터를 획득 - 못하면 반납하고 종료
                val p = p0.getValue<Post>(Post::class.java) ?: return Transaction.success(p0)
                // 좋아요일 때 눌렀는가? - 풀고
                if ( p.likes.containsKey(getUserID()) ) {
                    p.likeCount--
                    p.likes.remove(getUserID())
                } else {
                    // 아닌 상태일 때 눌렀는가? - 체크
                    p.likeCount++
                    p.likes.put(getUserID()!!, true)
                }
                // 이렇게 가공된 데이터를 다시 반납
                p0.value = p
                // 이 결과를 트렌젝션에 적용함
                return Transaction.success(p0)

            }
        })
    }

    override fun onStart() {
        super.onStart()
        mAdapter?.startListening()
    }

    override fun onStop() {
        super.onStop()
        mAdapter?.stopListening()
    }

    // 차후 추상클래스가 되어서 서브클래스들이 가져오고자하는 데이터는
    // 쿼리 구성을 다양하게 줘서 폼은 같이쓰고 데이터만 다르게 처리되는 내용 세팅
    fun getQuery(databaseRef: DatabaseReference): Query {
        //앞에서부터 100개 가져옴
        val result = databaseRef.child("posts").limitToFirst(100)
        return result
    }

    fun getUserID() = FirebaseAuth.getInstance().currentUser?.uid

}
