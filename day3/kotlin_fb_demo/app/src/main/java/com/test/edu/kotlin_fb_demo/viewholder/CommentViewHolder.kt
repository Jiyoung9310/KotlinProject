package com.test.edu.kotlin_fb_demo.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.test.edu.kotlin_fb_demo.R
import com.test.edu.kotlin_fb_demo.R.id.*
import com.test.edu.kotlin_fb_demo.models.Comment
import com.test.edu.kotlin_fb_demo.models.Post
import kotlinx.android.synthetic.main.item_comment.view.*

class CommentViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    var comment_author_thumb: ImageView
    var comment_author_name: TextView
    var comment_body: TextView

    init {
        comment_author_thumb = itemView.findViewById(R.id.comment_author_thumb)
        comment_author_name = itemView.findViewById(R.id.comment_author_name)
        comment_body = itemView.findViewById(R.id.comment_body)
    }

    //댓글이 전달되면 뷰에 세팅
    fun bindToComment(comment: Comment) {
        comment_author_name.text = comment.author
        comment_body.text = comment.text
    }
}