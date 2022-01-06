package com.example.apptestediego.model

import com.google.gson.annotations.SerializedName

data class TodosResponseModel (
    @SerializedName("userId")
    var  userId : Int=0,

    @SerializedName("id")
    var  id : Int=0,

    @SerializedName("title")
    var title :String="",

    @SerializedName("completed")
    var completed :Boolean=false
)
