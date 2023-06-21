package com.dicoding.mygithub.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dicoding.mygithub.model.User

@Database(entities = [User::class], version = 1)

abstract class UserDatabase: RoomDatabase() {
    abstract fun userDao() : UserDao
}