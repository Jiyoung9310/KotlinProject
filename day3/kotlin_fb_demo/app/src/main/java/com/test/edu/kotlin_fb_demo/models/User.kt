package com.test.edu.kotlin_fb_demo.models

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class User {
    lateinit var username: String
    lateinit var email: String

    // FB 실시간 DB에서 모델에 데이터를 넣고 한번에 밀어넣을 땐 기본 생성자가 있어야한다.
    constructor() {}
    constructor(username: String, email: String) {
        this.username = username
        this.email = email
    }
}