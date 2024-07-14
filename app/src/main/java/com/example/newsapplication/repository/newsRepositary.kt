package com.example.newsapplication.repository

import com.example.newsapplication.database.NewsDatabase
import com.example.newsapplication.database.newsDetails
import com.example.newsapplication.database.newsDao

class newsRepositary (
    private val db : newsDetails
) {
    suspend fun insert(newsDatabase: NewsDatabase) = db.getNews().insertToDo(newsDatabase)
    suspend fun delete(newsDatabase: NewsDatabase) = db.getNews().deleteToDo(newsDatabase)
    fun getAllnews():MutableList<NewsDatabase> = db.getNews().getAllnews()
    fun getCetogary(cetogary:String):MutableList<NewsDatabase> = db.getNews().getCetogary(cetogary)





}