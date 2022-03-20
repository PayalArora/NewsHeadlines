package com.news.headlines.data.response

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.news.headlines.data.persistance.NewsDataConverter
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity
@TypeConverters(NewsDataConverter::class)
class NewsData (
    @SerialName("status") val status : String?,
    @PrimaryKey
    @SerialName("totalResults") val totalResults : Int,
    @SerialName("articles") val articles : List<Articles>?
)
