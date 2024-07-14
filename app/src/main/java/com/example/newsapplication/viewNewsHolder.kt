package com.example.newsapplication

import android.view.View
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class viewNewsHolder(view:View) : ViewHolder(view) {

    val Deletebtn : ImageView = view.findViewById(R.id.DeleteIcon)
    val newsThumbnail : ImageView = view.findViewById(R.id.newsThumbnail)
    val newsTitle : TextView = view.findViewById(R.id.newsTitle)
    val newsDescription : TextView = view.findViewById(R.id.newsDescription)
    val bookmarkIcon : ImageView = view.findViewById(R.id.bookmarkIcon)




}