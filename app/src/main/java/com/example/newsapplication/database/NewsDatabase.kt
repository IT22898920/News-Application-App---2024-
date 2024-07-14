package com.example.newsapplication.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class NewsDatabase(
    var Title:String?,
    var cetogary : String?,
    var Description:String ? ,

) : Serializable
{
    @PrimaryKey(autoGenerate = true)
     var id:Int? =null
}