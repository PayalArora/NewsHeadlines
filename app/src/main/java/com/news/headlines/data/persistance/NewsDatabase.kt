package com.news.headlines.data.persistance

import androidx.room.Database
import androidx.room.RoomDatabase

import com.news.headlines.data.response.NewsData

@Database(entities = [NewsData::class], version = 1)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}