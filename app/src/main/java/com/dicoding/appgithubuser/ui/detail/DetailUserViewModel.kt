package com.dicoding.appgithubuser.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.appgithubuser.api.RetrofitClient
import com.dicoding.appgithubuser.data.local.FavoriteUser
import com.dicoding.appgithubuser.data.local.FavoriteUserDao
import com.dicoding.appgithubuser.data.local.UserDatabase
import com.dicoding.appgithubuser.data.model.DetailUserResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(application: Application) : AndroidViewModel (application) {
    val user = MutableLiveData<DetailUserResponse>()

    private var userDao: FavoriteUserDao?
    private var userDb: UserDatabase?

    init {
        userDb = UserDatabase.getDatabase(application)
        userDao = userDb?.favoriteUserDao()
    }

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
    fun addToFavorite(username: String, id: Int, avatarUrl: String){
        CoroutineScope(Dispatchers.IO).launch {
            var user = FavoriteUser(
                username,
                id,
                avatarUrl
            )
            userDao?.addToFavorite(user)
        }
    }

    suspend fun checkUser(id: Int) = userDao?.checkUser(id)

    fun removeFromFavorite(id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.removeFromFavorite(id)
        }
    }
}