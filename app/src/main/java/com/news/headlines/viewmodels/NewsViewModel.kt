package com.news.headlines.viewmodels

import androidx.lifecycle.*
import com.news.headlines.data.repository.NewsDataRepository
import com.news.headlines.data.response.NewsData
import com.news.headlines.data.response.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Inject

@ExperimentalSerializationApi
@HiltViewModel
class NewsViewModel @Inject constructor(val repository: NewsDataRepository) : ViewModel() {

    private val TAG = NewsViewModel::class.java.simpleName
    private val _newsResponse: MutableLiveData<Resource<NewsData>> = MutableLiveData()
    val newsResponse: LiveData<Resource<NewsData>>
        get() = _newsResponse


    fun getNews(
        country: String,
        category: String,
    ) = viewModelScope.launch {
        _newsResponse.value = repository.getNews(country, category)
    }

    fun insertNews(
        newsData: NewsData
    ) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertNews(newsData)
    }

    fun getOfflineData() {
        viewModelScope.launch {
            repository.getOfflineNews().collect { result ->
                _newsResponse.value = result
                _newsResponse.postValue(Resource.Success(result.value))
            }
        }

    }
}

