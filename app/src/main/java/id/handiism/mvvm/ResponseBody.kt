package id.handiism.mvvm

import com.google.gson.annotations.SerializedName

data class ResponseBody(

    @field:SerializedName("code")
    val code: Int,

    @field:SerializedName("data")
    val data: List<Category>? = null,

    @field:SerializedName("status")
    val status: String
)