package com.example.newsapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapplication.database.NewsDatabase

class MainActivityData:ViewModel() {

    private val _data = MutableLiveData<MutableList<NewsDatabase>>()

    val data : LiveData<MutableList<NewsDatabase>> = _data

    fun setData(data: MutableList<NewsDatabase>){
        _data.value = data
    }

    private val _backgroundColor = MutableLiveData<Int> ()

    fun getBackgroundColor(): LiveData<Int> = _backgroundColor



    fun setBackground(color:Int) {
        _backgroundColor.value = color
    }
}