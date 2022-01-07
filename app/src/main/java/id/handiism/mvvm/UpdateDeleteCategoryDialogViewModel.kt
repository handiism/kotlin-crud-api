package id.handiism.mvvm

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateDeleteCategoryDialogViewModel : ViewModel() {
    var updateCategoryLiveData: MutableLiveData<Category?> = MutableLiveData()
    private var deleteCategoryLiveData: MutableLiveData<Category?> = MutableLiveData()


    fun getDeleteCategoryObservable(): MutableLiveData<Category?> {
        return deleteCategoryLiveData
    }

    fun getUpdateCategoryObservable(): MutableLiveData<Category?> {
        return updateCategoryLiveData
    }

    fun updateCategory(id: Int, requestBody: RequestBody) {
        val retroInstance = RetroInstance.getRetroInstance().create(RetroService::class.java)
        val call = retroInstance.updateCategory(id, requestBody)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                if (response.isSuccessful) {
                    updateCategoryLiveData.postValue(response.body()?.data?.get(0))
                } else {
                    updateCategoryLiveData.postValue(null)
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                updateCategoryLiveData.postValue(null)
                Log.d(TAG, "onFailure: $t")
            }

        })
    }

    fun deleteCategory(id: Int) {
        val retroInstance = RetroInstance.getRetroInstance().create(RetroService::class.java)
        val call = retroInstance.deleteCategory(id)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                if (response.isSuccessful) {
                    Log.d(TAG, "onResponse: pass")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d(TAG, "onFailure: $t")
            }

        })
    }
}