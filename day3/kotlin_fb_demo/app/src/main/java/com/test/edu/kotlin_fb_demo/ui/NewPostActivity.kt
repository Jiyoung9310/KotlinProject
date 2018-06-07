package com.test.edu.kotlin_fb_demo.ui

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.google.firebase.database.*
import com.test.edu.kotlin_fb_demo.R
import com.test.edu.kotlin_fb_demo.R.id.*
import com.test.edu.kotlin_fb_demo.models.Post
import com.test.edu.kotlin_fb_demo.models.User

import kotlinx.android.synthetic.main.activity_new_post.*
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.content_new_post.*

class NewPostActivity : RootActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_post)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            onSubmitPost()
        }
    }

    private fun onSubmitPost() {
        //제목, 내용에 대한 유효성 검사
        if(!vaildForm()) return
        showProgressDialog("..글 업로드 중..")
        editEnable(false)
        //DB참조 위치 획득
        val mDataBase = FirebaseDatabase.getInstance().reference
        //회원여부 확인 -> 회원이면 -> 글을 업로드
        val userID = getUserID()
        mDataBase.child("users").child(userID!!).addListenerForSingleValueEvent(
                object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        hideProgressDIalog()
                        editEnable(true)
                    }
                    override fun onDataChange(p0: DataSnapshot) {
                        val user = p0.getValue<User>(User::class.java)
                        if( user == null ) {
                            Toast.makeText(this@NewPostActivity, "회원이 아닙니다.", Toast.LENGTH_SHORT).show()
                        } else {
                            //회원임을 검증됨, 글을 업로드한다
                            uploadNewPost(user)
                        }
                        hideProgressDIalog()
                        editEnable(true)
                    }
                }
        )



    }
    // 글 등록
    private fun uploadNewPost(user: User) {
        val mDataBase = FirebaseDatabase.getInstance().reference
        //글 입력
        //중복되지않는해시키 : 해당 글 고유 키
        //전체글 : /posts/중복되지않는해시키/POST덩어리
        //내글 : /user-posts/사용자아이디/중복되지않는해시키/POST덩어리
        val uid = getUserID()
        //글을 입력해야 나오는 키를 미리 획득한다 (미리확보)
        val hashKey = mDataBase.child("posts").push().key
        //글
        val post = Post(uid!!, user.username, post_title.text.toString(), post_body.text.toString())

        //업로드할 글 내용
        val updatePost = hashMapOf("/posts/${hashKey}" to post.toMap(),
                "/user-posts/${uid}/${hashKey}" to post.toMap()
                )
        // 반영(타입 형변환으로 형식을 맞춰줌)
        mDataBase.updateChildren(updatePost as Map<String, Any>).addOnCompleteListener { task ->
            val msg = if(task.isSuccessful) "글 업로드 성공" else "글 업로드 실패"
            Toast.makeText(this@NewPostActivity, msg, Toast.LENGTH_SHORT).show()
        }
    }

    private fun editEnable(enable: Boolean) {
        //DB에 글 업로드 중에는 사용자가 조작 못하게 방지
        //에디터 비활성
        post_title.isEnabled = enable
        post_body.isEnabled = enable
        //버튼 invisible
        fab.visibility = if(enable) View.VISIBLE else View.GONE
    }

    private fun vaildForm(): Boolean {
        // 비웠는지 체크 각각 -> 에러 메시지 처리 및 초기화
        var result: Boolean = true
        if(TextUtils.isEmpty(post_title.text.toString())) {
            result = false
            post_title.error = "제목 값이 비어있습니다."
        } else {
            post_title.error = null
        }
        if(TextUtils.isEmpty(post_body.text.toString())) {
            result = false
            post_body.error = "내용이 비어있습니다."
        } else {
            post_body.error = null
        }


        return result
    }

}
