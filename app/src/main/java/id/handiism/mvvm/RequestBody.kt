package id.handiism.mvvm

import com.google.gson.annotations.SerializedName

data class RequestBody(

    @field:SerializedName("name")
    val name: String
)
