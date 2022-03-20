package com.news.headlines.data.persistance

import javax.inject.Inject

class NewsDBRepo @Inject constructor(val newsDao: NewsDao){
    private val mTAG = NewsDBRepo::class.java.simpleName
}