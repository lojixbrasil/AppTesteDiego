package com.example.apptestediego.model

import com.google.gson.annotations.SerializedName

data class PostsResponseModel (
    @SerializedName("userId")
    var  userId : Int = 0,

    @SerializedName("id")
    var  id : Int = 0,

    @SerializedName("title")
    var title :String = "",

    @SerializedName("body")
    var body : String = ""
)