package com.example.newsapplication.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface newsDao {

    @Insert
    suspend fun insertToDo(newsDatabase: NewsDatabase)

    @Delete
    suspend fun deleteToDo(newsDatabase: NewsDatabase)



    @Query("SELECT * FROM NewsDatabase")
    fun getAllnews():MutableList<NewsDatabase>


    @Query("SELECT * FROM NewsDatabase WHERE cetogary = :cetogary")
    fun getCetogary(cetogary: String): MutableList<NewsDatabase>

    @Query("SELECT * FROM NEWSDATABASE WHERE id = :id")
    fun getSingleNews(id: Int):MutableList<NewsDatabase>

}