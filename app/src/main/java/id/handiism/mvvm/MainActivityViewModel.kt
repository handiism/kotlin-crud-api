package id.handiism.mvvm

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityViewModel : ViewModel() {
    var allCategoryLiveData: MutableLiveData<List<Category>?> = MutableLiveData()
    private var categoryLiveData: MutableLiveData<Category?> = MutableLiveData()
    var status = MutableLiveData<Boolean>()


    fun getAllCategoryObservable(): MutableLiveData<List<Category>?> {
        return allCategoryLiveData
    }

    fun getCategoryByIdObservable(): MutableLiveData<Category?> {
        return categoryLiveData
    }

    fun getAllCategory() {
        val retroInstance = RetroInstance.getRetroInstance().create(RetroService::class.java)
        val call = retroInstance.getAllCategory()
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                Log.d("GetAllCategory", "onResponse: true")
                Log.d("GetAllCategory", "onResponse: ${response.body()}")
                if (response.isSuccessful) {
                    allCategoryLiveData.postValue(response.body()?.data)
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                allCategoryLiveData.postValue(null)
                Log.d("GetAllCategory", "onFailure: $t")
            }
        })
    }

    fun getCategoryById(id: Int) {
        val retroInstance = RetroInstance.getRetroInstance().create(RetroService::class.java)
        val call = retroInstance.getCategoryById(id)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                if (response.isSuccessful) {
                    Log.d("GetCategoryById", "onResponse: true")
                    Log.d("GetCategoryById", "responseBody: ${response.body()}")
                    categoryLiveData.postValue(response.body()?.data?.get(0))
                    status.value = true
                } else {
                    Log.d("GetCategoryById", "onResponse: false")
                    categoryLiveData.postValue(null)
                    status.value = false
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                categoryLiveData.postValue(null)
                status.value = false
                Log.d("GetCategoryById", "onFailure: $t")
            }
        })
    }
}