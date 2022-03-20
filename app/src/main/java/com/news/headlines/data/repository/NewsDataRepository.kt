package com.news.headlines.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.news.headlines.data.persistance.NewsDBRepo
import com.news.headlines.data.response.NewsData
import com.news.headlines.data.response.Resource
import com.news.headlines.utils.API_KEY
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Inject

@ExperimentalSerializationApi
class NewsDataRepository @Inject constructor(
    private var apiService: NewsDataApi,
    private var dbService: NewsDBRepo
) : BaseRepository() {

    val TAG = NewsDataRepository::class.java.simpleName

    val coroutineErrorHandler = CoroutineExceptionHandler { _, exception ->
        Log.e(TAG, exception.printStackTrace().toString())
    }


    fun insertNews(newsData: NewsData) {
        dbService.newsDao.insert(newsData)
    }

    fun getOfflineNews(): Flow<Resource.Success<NewsData>> {
        return flow {
            dbService.newsDao.fetchOfflineNewsAsFlow().collect {
                emit(Resource.Success(it))
                delay(60*60 *1000*60)
            }
        }
    }

    suspend fun getNews(
        country: String,
        category: String
    ) = safeApiCall {
        apiService.getNews(category,country, API_KEY)
    }
}
