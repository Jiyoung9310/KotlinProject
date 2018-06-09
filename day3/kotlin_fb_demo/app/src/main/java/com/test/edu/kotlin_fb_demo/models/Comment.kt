package com.test.edu.kotlin_fb_demo.models

import com.google.firebase.database.IgnoreExtraProperties

// 댓글 그릇
@IgnoreExtraProperties
class Comment {
    lateinit var uid: String
    lateinit var author: String
    lateinit var text: String

    constructor()
    constructor(uid: String, author: String, text: String) {
        this.uid = uid
        this.author = author
        this.text = text
    }

}