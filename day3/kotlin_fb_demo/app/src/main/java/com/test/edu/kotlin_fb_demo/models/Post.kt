package com.test.edu.kotlin_fb_demo.models

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class Post {
    // uid: 작성자의 고유 아이디(익명아이디:해시값)
    // author: 작성자 이름
    // title: 글 제목
    // body: 글 내용
    // --------------------
    // likeCount: 좋아요 갯수
    // likes: Map<String, Boolean> 누가 좋아요 했는지 기록
    lateinit var uid: String
    lateinit var author: String
    lateinit var title: String
    lateinit var body: String

    var likeCount = 0
    val likes: MutableMap<String, Boolean> = mutableMapOf()

    constructor(){}
    constructor(uid: String, author: String, title: String, body: String) {
        this.uid = uid
        this.author = author
        this.title = title
        this.body = body
    }

    // 여러 게시판에 (DB상으로는 가지에(전체, 내글)) 동시에 데이터를 넣기 위한 방법
    @Exclude
    fun toMap(): Map<String, Any> {
        return hashMapOf("uid" to uid,
                "author" to author,
                "title" to title,
                "body" to body,
                "likeCount" to likeCount,
                "likes" to likes
                )
    }

}