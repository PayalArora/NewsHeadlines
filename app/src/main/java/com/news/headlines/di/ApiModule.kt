package com.news.headlines.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.news.headlines.BuildConfig.BASE_URL
import com.news.headlines.data.persistance.NewsDBRepo
import com.news.headlines.data.repository.NewsDataApi
import com.news.headlines.data.repository.NewsDataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@ExperimentalSerializationApi
@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    private val mediaType = "application/json".toMediaType()
    private val json = Json { ignoreUnknownKeys = true }


    @Provides
    @Singleton
    fun provideRepo(apiService: NewsDataApi, database: NewsDBRepo): NewsDataRepository {
        return NewsDataRepository(apiService, database)
    }

    @Provides
    fun provideApiService(retrofit: Retrofit): NewsDataApi {
        return retrofit.create(NewsDataApi::class.java)
    }

    @Provides
    fun provideRetrofitInstance(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory(mediaType))
            .build()
    }

    /***
     * Create OkHttpClient Instance
     */
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().apply {
            addInterceptor(loggerInterceptor)
        }.build()
    }

    /***
     * Create HttpLoggingInterceptor Instance with Level.BODY
     */
    private val loggerInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

}