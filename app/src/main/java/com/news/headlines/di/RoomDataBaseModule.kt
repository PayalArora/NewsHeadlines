package com.news.headlines.di

import android.content.Context
import androidx.room.Room
import com.news.headlines.data.persistance.NewsDBRepo
import com.news.headlines.data.persistance.NewsDao
import com.news.headlines.data.persistance.NewsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Singleton

@ExperimentalSerializationApi
@Module
@InstallIn(SingletonComponent::class)
object RoomDataBaseModule {

    @Singleton
    @Provides
    fun provideYourDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        NewsDatabase::class.java,
        "news.db"
    ).build()

    @Provides
    @Singleton
    fun provideRouteDBRepo(newsDao: NewsDao): NewsDBRepo {
        return NewsDBRepo(newsDao)
    }


    @Provides
    @Singleton
    fun provideNewsDao(db: NewsDatabase): NewsDao = db.newsDao()
}