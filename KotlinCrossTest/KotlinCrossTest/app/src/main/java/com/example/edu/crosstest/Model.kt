package com.example.edu.crosstest

import java.util.ArrayList

// JSON을 담는 그릇은 코틀린에서 data class로 기술한다.

data class Model (
    var meta: Meta,
    var documents: ArrayList<Documents>
)

data class Meta (
        var isIs_end: Boolean,
        var total_count: Int,
        var pageable_count: Int
)

data class Documents (
        var collection: String,
        var datetime: String,
        var thumbnail_url: String,
        var image_url: String,
        var display_sitename: String,
        var doc_url: String,
        var width: Int,
        var height: Int
)

