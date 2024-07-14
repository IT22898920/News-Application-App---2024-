package com.example.newsapplication.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [NewsDatabase::class], version = 1)
abstract class newsDetails:RoomDatabase() {

    abstract fun getNews() : newsDao

    companion object{

        private var INSTANCE:newsDetails? = null

        fun getInstance(context: Context) : newsDetails {
            synchronized(this){
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    newsDetails::class.java,
                    "news.db"
                ).build().also {
                    INSTANCE = it
                }
            }
        }

    }
}