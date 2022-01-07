package id.handiism.mvvm

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateCategoryDialogViewModel : ViewModel() {
    private var createCategoryLiveData: MutableLiveData<Category?> = MutableLiveData()

    fun getCreateCategoryObservable(): MutableLiveData<Category?> {
        return createCategoryLiveData
    }

    fun createCategory(requestBody: RequestBody) {
        val retroInstance = RetroInstance.getRetroInstance().create(RetroService::class.java)
        val call = retroInstance.createCategory(requestBody)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                Log.d("CreateCategoryLiveData", "onResponse: ")
                if (response.isSuccessful) {
                    createCategoryLiveData.postValue(response.body()?.data?.get(0))
                } else {
                    createCategoryLiveData.postValue(null)
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                createCategoryLiveData.postValue(null)
                Log.d("CreateCategoryLiveData", "onFailure: $t")
            }

        })
    }
}