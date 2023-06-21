package com.dicoding.mygithub.utils

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.mygithub.api.RetrofitClient
import com.dicoding.mygithub.database.UserConfig
import com.dicoding.mygithub.model.User
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(private val db: UserConfig): ViewModel() {
    val resultSuccessFavorite = MutableLiveData<Boolean>()
    val resultDeleteFavorite = MutableLiveData<Boolean>()
    val user = MutableLiveData<DetailUserResponse>()

    private var isFavorite = false

    fun setFavorite(user : User?) {
        viewModelScope.launch {
            user?.let {
                if (isFavorite) {
                    db.userDao.delete(user)
                    resultDeleteFavorite.value = true
                } else {
                    db.userDao.insert(user)
                    resultSuccessFavorite.value = true
                }
            }
            isFavorite = !isFavorite
        }
    }

    fun setUserDetail(username: String?){
        RetrofitClient.apiInstance.getUserDetail(username).enqueue(object : Callback<DetailUserResponse>{
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                if (response.isSuccessful){
                    user.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                Log.d("Failure", t.message.toString())
            }

        })
    }
    fun getUserDetail(): LiveData<DetailUserResponse>{
        return user
    }
}