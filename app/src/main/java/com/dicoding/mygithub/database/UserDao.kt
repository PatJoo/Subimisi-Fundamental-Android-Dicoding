package com.dicoding.mygithub.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dicoding.mygithub.model.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

    @Query("SELECT * FROM User")
    fun loadAll(): LiveData<MutableList<User>>

    @Query("SELECT * FROM User WHERE id LIKE :id LIMIT 1")
    fun findById(id: Int): User

    @Delete
    fun delete(user: User)
}