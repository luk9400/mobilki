package com.example.gallery

import org.json.JSONObject

data class Image (
    val imageUrl: String,
    val title: String,
    var rating: Float
) {
    fun toJson() : JSONObject {
        return JSONObject().run {
            put("imageUrl", imageUrl)
            put("title", title)
            put("rating", rating)
        }
    }

    constructor(json: JSONObject): this(
        json.getString("imageUrl"),
        json.getString("title"),
        json.getString("rating").toFloat()
    )
}