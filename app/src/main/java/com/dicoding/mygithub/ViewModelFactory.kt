package com.dicoding.mygithub

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.mygithub.database.UserConfig
import com.dicoding.mygithub.ui.FavoriteViewModel
import com.dicoding.mygithub.ui.FollowersViewModel
import com.dicoding.mygithub.ui.FollowingViewModel
import com.dicoding.mygithub.ui.MainViewModel
import com.dicoding.mygithub.utils.DetailUserViewModel

class ViewModelFactory(private val pref: SettingPreferences, private val application: Application, private val dbModule: UserConfig) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(pref) as T
        }
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(dbModule) as T
        }
        if (modelClass.isAssignableFrom(FollowersViewModel::class.java)) {
            return FollowersViewModel() as T
        }
        if (modelClass.isAssignableFrom(FollowingViewModel::class.java)) {
            return FollowingViewModel() as T
        }
        if (modelClass.isAssignableFrom(DetailUserViewModel::class.java)) {
            return DetailUserViewModel(dbModule) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}