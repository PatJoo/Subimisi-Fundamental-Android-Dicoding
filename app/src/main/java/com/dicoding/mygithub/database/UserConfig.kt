package com.dicoding.mygithub.database

import android.content.Context
import androidx.room.Room

class UserConfig(private val context: Context) {
    private val db = Room.databaseBuilder(context, UserDatabase::class.java, "user.db")
        .allowMainThreadQueries()
        .build()

    val userDao = db.userDao()
}