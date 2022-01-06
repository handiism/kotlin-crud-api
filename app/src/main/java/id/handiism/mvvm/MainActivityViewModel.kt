package id.handiism.mvvm

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityViewModel : ViewModel() {
    var recyclerListData: MutableLiveData<List<Category>?> = MutableLiveData()

    fun getAllCategoryObservable(): MutableLiveData<List<Category>?> {
        return recyclerListData
    }

    fun getAllCategory() {
        val retroInstance = RetroInstance.getRetroInstance().create(RetroService::class.java)
        val call = retroInstance.getAllCategory()
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Log.d(TAG, "onResponse: ${response.headers()}")
                Log.d(TAG, "onResponse: ${response.body()}")
                if (response.isSuccessful) {
                    recyclerListData.postValue(response.body()?.data)
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                recyclerListData.postValue(null)
                Log.d(TAG, "onFailure: $t")
            }
        })

    }
}