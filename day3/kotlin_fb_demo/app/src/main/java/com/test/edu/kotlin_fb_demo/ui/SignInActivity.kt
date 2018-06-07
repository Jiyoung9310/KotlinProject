package com.test.edu.kotlin_fb_demo.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.text.TextUtils.indexOf
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.test.edu.kotlin_fb_demo.R
import com.test.edu.kotlin_fb_demo.models.User
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlin.math.sign

class SignInActivity : RootActivity(), View.OnClickListener {
    private val mAuth: FirebaseAuth
    private val mDataBase: DatabaseReference

    private val TAG = "SignInActivity"
    init {
        //인증에 필요한 모든 작업
        mAuth = FirebaseAuth.getInstance()
        //디비의 시작점 경로 : (root)/
        mDataBase = FirebaseDatabase.getInstance().getReference()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        //버튼 이벤트
        btn_sign_in.setOnClickListener(this)
        btn_sign_up.setOnClickListener(this)
    }

    override fun onStart() {
        super.onStart()
        //인증이 살아있으면 - 세션이 살아있다면 - 로그아웃 하지 않았다면
        if( mAuth.currentUser != null ) {
            onAuthSuccess(mAuth.currentUser!!)
        }
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onClick(v: View?) {
        //btn_sign_in 누르면 sign_in()호출
        //btn_sign_up 누르면 sign_up()호출
        when( v?.id ?: -1) {
            R.id.btn_sign_in -> sign_in()
            R.id.btn_sign_up -> sign_up()
        }
    }

    fun sign_in() {
        //입력 폼 검사
        if(!vaildForm()) return
        //로딩 띄우기
        showProgressDialog("..로그인 중..")
        //이메일 비번 획득
        val email = field_email.text.toString()
        val password = field_password.text.toString()
        //로그인
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    //로딩 닫기
                    hideProgressDIalog()
                    if(task.isSuccessful) {
                        //성공하면
                        Log.i(TAG, "로그인 성공 ${task.result.user}")
                        //인증성공으로 함수 호출 -> 화면 이동
                        onAuthSuccess(task.result.user)
                    } else {
                        //로그인 실패
                        //실패하면 -> 토스트
                        task.addOnFailureListener(this) {exception ->
                            Toast.makeText(this@SignInActivity, "로그인 실패 : ${exception.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
    }

    fun sign_up() {
        //회원 가입
        //입력폼 검사 OK
        if(!vaildForm()) return
        //로딩 띄우기
        showProgressDialog("..회원 가입 중..")
        //이메일, 비번 획득
        val email = field_email.text.toString()
        val password = field_password.text.toString()
        //fb 이메일과 비번으로 계정 생성하기 진행
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    //로딩 닫기
                    hideProgressDIalog()
                    if(task.isSuccessful) {
                        //가입 성공
                        //성공하면 -> 로그인
                        Log.i(TAG, "로그인 성공 ${task.result.user}")
                        onAuthSuccess(task.result.user)
                    } else {
                        //가입 실패
                        //실패하면 -> 토스트
                        Toast.makeText(this@SignInActivity, "가입 실패", Toast.LENGTH_LONG).show()
                    }
                }

    }

    private fun vaildForm(): Boolean {
        // 비웠는지 체크 각각 -> 에러 메시지 처리 및 초기화
        var result: Boolean = true
        if(TextUtils.isEmpty(field_email.text.toString())) {
            result = false
            field_email.error = "이메일 값이 비어있습니다."
        } else {
            field_email.error = null
        }
        if(TextUtils.isEmpty(field_password.text.toString())) {
            result = false
            field_password.error = "비밀번호가 정확하지 않습니다."
        } else {
            field_password.error = null
        }


        return result
    }

    // firebase 유저 정보를 인자로 받는다
    private fun onAuthSuccess(user:FirebaseUser) {
        // 실시간 데이터베이스 입력 - 비동기
        var email = user.email!!
        val userName = if (email.contains("@")) {
            email.split("@".toRegex())[0]
        } else {
            email
        } //email에서 @앞부분만 취한다

        updateUserInfo(user.uid, userName, email)

        // 화면 전환 - > 서비스 화면 이동
        startActivity(Intent(this, ServiceActivity::class.java))
        finish()

    }
    // 회원 정보 가입 혹은 업데이트
    private fun updateUserInfo(uid: String, name: String, email: String) {
        val user = User(name, email)
        // /users/해시값(중복안됨)/유저정보(구조)
        // /users/uid(유저의 익명아이디)/유저정보(구조)
        mDataBase.child("users").child(uid).setValue(user)
                .addOnCompleteListener(this) { task -> Log.i(TAG, "디비 입력결과 ${task.isSuccessful}") }
    }
}