package id.handiism.mvvm

import retrofit2.Call
import retrofit2.http.*

interface RetroService {

    @GET("categories")
    @Headers(
        "X-API-KEY:handiism",
        "Accept:application/json"
    )
    fun getAllCategory(): Call<ResponseBody>

    @POST("categories")
    @Headers(
        "X-API-KEY:handiism",
        "Accept:application/json",
        "Content-Type:application/json"
    )
    fun createCategory(@Body requestBody: RequestBody): Call<ResponseBody>

    @GET("categories/{id}")
    @Headers(
        "X-API-KEY:handiism",
        "Accept:application/json",
    )
    fun getCategoryById(@Path("id") id: Int): Call<ResponseBody>

    @PUT("categories/{id}")
    @Headers(
        "X-API-KEY:handiism",
        "Accept:application/json",
        "Content-Type:application/json",
    )
    fun updateCategory(@Path("id") id: Int, @Body requestBody: RequestBody): Call<ResponseBody>

    @DELETE("categories/{id}")
    @Headers(
        "X-API-KEY:handiism",
        "Accept:application/json",
    )
    fun deleteCategory(@Path("id") id: Int): Call<ResponseBody>
}