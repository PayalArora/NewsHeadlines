package com.news.headlines.data.persistance

import androidx.room.TypeConverter
import com.news.headlines.data.response.Articles
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class NewsDataConverter {
    @TypeConverter
    fun articlesToList(json: String?): List<Articles>? {
        return json?.let { Json.decodeFromString(ListSerializer(Articles.serializer()), it) }
    }
    @TypeConverter
    fun articlesListToString(list: List<Articles?>?): String {
        return Json.encodeToString(list)
    }

}