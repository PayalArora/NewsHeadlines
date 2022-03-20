package com.news.headlines.data.repository

import com.news.headlines.data.response.NewsData
import retrofit2.http.GET
import retrofit2.http.Query


interface NewsDataApi {
    //https://newsapi.org/v2/top-headlines?country=us&category=business&apiKey=cf30654b9cb04c83a0f3276e5e02c904

    @GET("top-headlines")
    suspend fun getNews(
        @Query("category") category: String,
        @Query("country") country: String,
        @Query("apiKey") apiKey: String
    ): NewsData
}