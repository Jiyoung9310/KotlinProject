package com.test.edu.kotlin_fb_demo.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.*
import com.test.edu.kotlin_fb_demo.R
import com.test.edu.kotlin_fb_demo.models.Comment
import com.test.edu.kotlin_fb_demo.models.Post
import com.test.edu.kotlin_fb_demo.models.User
import com.test.edu.kotlin_fb_demo.viewholder.CommentViewHolder
import com.test.edu.kotlin_fb_demo.viewholder.PostViewHolder
import kotlinx.android.synthetic.main.activity_post_detail.*
import kotlinx.android.synthetic.main.content_new_post.*
import kotlinx.android.synthetic.main.item_author.*
import kotlinx.android.synthetic.main.item_text.*

class PostDetailActivity : RootActivity() {
    private val TAG = "PostDetailActivity"
    private var mPostRef: DatabaseReference? = null
    private var mCommentRef: DatabaseReference? = null
    private var mPostListener: ValueEventListener? = null

    private var mAdapter: FirebaseRecyclerAdapter<Comment, CommentViewHolder>? = null
    // static 대용으로 사용 가능
    companion object {
        val EXTRA_KEY = "post_key"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_detail)
        // 전달 받은 키 획득
        val postKey = intent.getStringExtra(EXTRA_KEY)
        if(postKey == null) {
            finish()
            return
        }
        // 키가 존재할 경우 - 데이터를 획득 - 해당 포스트 글을 획득
        // 본 글, 본 글에 해당되는 댓글
        mPostRef = FirebaseDatabase.getInstance().reference.child("posts").child(postKey)
        mCommentRef = FirebaseDatabase.getInstance().reference.child("post-comments").child(postKey)

        // 댓글 리스트 뿌리기
        // 쿼리
        val commentQuery = mCommentRef!!.limitToFirst(100)
        // 쿼리를 기반으로 옵션
        val option = FirebaseRecyclerOptions.Builder<Comment>().setQuery(commentQuery, Comment::class.java).build()
        // 어댑터
        mAdapter = object : FirebaseRecyclerAdapter<Comment, CommentViewHolder>(option) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
                // xml -> View를 만드는 작업 : layoutInflater
                return CommentViewHolder(layoutInflater.inflate(R.layout.item_comment, parent, false))
            }

            override fun onBindViewHolder(holder: CommentViewHolder, position: Int, model: Comment) {
                // 셀 하나 하나를 셋팅해
                holder.bindToComment(model)
            }
        }
        // 뷰홀더 CommentViewHolder ok
        // 댓글 하나를 담는 그릇 ok
        // xml
        // 리사이클러뷰에 매니저 등록, 어댑터 연결
        comment_list.adapter = mAdapter
        comment_list.layoutManager = LinearLayoutManager(this)
        // 어댑터 리스닝 등록, 해제
    }

    override fun onStart() {
        super.onStart()
        // 본 글을 가져온다
        // ValueEventListener - 한번 붙어있으면 변화가 오거나 할 경우 계속 이벤트가 살아있어서 오동작을 할 수가 있음
        mPostListener = object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(this@PostDetailActivity, "데이터 획득 실패", Toast.LENGTH_SHORT).show()
            }

            override fun onDataChange(p0: DataSnapshot) {
                // DataSnapshot으로부터 데이터를 셋팅해서 화면에 처리
                val post = p0.getValue(Post::class.java)
                post_author_name.text = post?.author
                post_title_view.text = post?.title
                post_body_view.text = post?.body
            }
        }
        mPostRef?.addValueEventListener(mPostListener!!)
        // 댓글을 가져온다
        mAdapter?.startListening()
    }

    override fun onStop() {
        super.onStop()
        // ValueEventListener 해제
        if(mPostListener != null)
            mPostRef?.removeEventListener(mPostListener!!)

        mAdapter?.stopListening()
    }

    fun onCommentSend(view: View?) {
        // 댓글 입력
        // 유효성 검사
        if(!vaildForm(comment_text, "댓글을 입력해주세요.")) return
        // 로딩
        showProgressDialog("..댓글 업로드 중..")
        editEnable(false)
        // 사용자 정보 확인 -> 글정보 준비
        val userID = getUserID()
        val mDataBase = FirebaseDatabase.getInstance().reference
        mDataBase.child("users").child(userID!!).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                hideProgressDIalog()
                editEnable(true)
            }

            override fun onDataChange(p0: DataSnapshot) {
                val user = p0.getValue<User>(User::class.java)
                if( user == null ) {
                    Toast.makeText(this@PostDetailActivity, "회원이 아닙니다.", Toast.LENGTH_SHORT).show()
                } else {
                    //회원임을 검증됨, 글을 업로드한다
                    uploadNewComment(user)
                }
                hideProgressDIalog()
                editEnable(true)
            }
        })
        // 경로 맞춰서 입력
        // 로딩 완료
    }

    private fun uploadNewComment(user: User) {
        // 댓글 입력
        // 경로 : /post-comments/글번호/해시키/Comment
        val uid = getUserID()
        //댓글을 입력해야 나오는 키를 미리 획득한다 (미리확보)
        //댓글
        val comment = Comment(uid!!, user.username, comment_text.text.toString())
        mCommentRef!!.push().setValue(comment).addOnCompleteListener { task ->
            if(task.isSuccessful) {
                Log.i(TAG, "댓글 생성 완료")
            }
        }
    }

    private fun vaildForm(target: EditText, msg: String): Boolean {
        // 비웠는지 체크 각각 -> 에러 메시지 처리 및 초기화
        var result: Boolean = true
        if(TextUtils.isEmpty(target.text.toString())) {
            result = false
            target.error = "msg"
        } else {
            target.error = null
        }

        return result
    }

    private fun editEnable(enable: Boolean) {
        //DB에 글 업로드 중에는 사용자가 조작 못하게 방지
        //에디터 비활성
        comment_text.isEnabled = enable
        comment_btn.isEnabled = enable
    }
}
