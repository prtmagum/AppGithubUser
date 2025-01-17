package com.dicoding.appgithubuser.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.appgithubuser.api.RetrofitClient
import com.dicoding.appgithubuser.data.model.DetailUserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel : ViewModel () {
    val user = MutableLiveData<DetailUserResponse>()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

        fun setUserDetail(username : String){
            _isLoading.value = true
            RetrofitClient.apiInstance
                .getUserDetail(username)
                .enqueue(object  : Callback<DetailUserResponse>{
                    override fun onResponse(
                        call: Call<DetailUserResponse>,
                        response: Response<DetailUserResponse>
                    ) {
                        if (response.isSuccessful){
                            user.postValue(response.body())
                            _isLoading.value = false
                        }
                    }

                    override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                        t.message?.let { Log.d("Failure", it) }
                    }
                })
        }
    fun getUserDetail(): LiveData<DetailUserResponse>{
        return user
    }
}