package id.handiism.mvvm

import com.google.gson.annotations.SerializedName

data class Category(

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("id")
    val id: Int
)