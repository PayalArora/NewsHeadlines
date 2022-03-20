package com.news.headlines

import android.app.Application

import dagger.hilt.android.HiltAndroidApp
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Inject

@ExperimentalSerializationApi
@HiltAndroidApp
class NewsApplication : Application() {
    private val mTAG = NewsApplication::class.java.simpleName


}