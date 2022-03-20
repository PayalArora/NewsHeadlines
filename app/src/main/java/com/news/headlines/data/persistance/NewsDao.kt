package com.news.headlines.data.persistance

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.news.headlines.data.response.NewsData
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg newsData: NewsData)

    @Query("SELECT * FROM NewsData")
    fun fetchOfflineNewsAsFlow(): Flow<NewsData>
}