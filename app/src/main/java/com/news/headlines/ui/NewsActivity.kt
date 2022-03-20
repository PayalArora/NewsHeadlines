package com.news.headlines.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import com.news.headlines.R
import com.news.headlines.data.repository.NewsDataRepository
import com.news.headlines.data.response.Articles
import com.news.headlines.data.response.Resource
import com.news.headlines.databinding.ActivityNewsBinding
import com.news.headlines.databinding.ToolbarMainBinding
import com.news.headlines.ui.adapter.NewsAdapter
import com.news.headlines.ui.adapter.OnItemClickPosition
import com.news.headlines.utils.DETAILS
import com.news.headlines.utils.hideProgressBar
import com.news.headlines.utils.showProgressBar
import com.news.headlines.viewmodels.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Inject

@ExperimentalSerializationApi
@AndroidEntryPoint
class NewsActivity : AppCompatActivity(), OnItemClickPosition {
    private val TAG = NewsActivity::class.java.simpleName
    private lateinit var binding: ActivityNewsBinding
    private lateinit var toolBarBinding: ToolbarMainBinding
    private val viewModel: NewsViewModel by viewModels()
    @Inject
    lateinit var repository: NewsDataRepository
    var newsData: List<Articles>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        toolBarBinding = binding.toolbar
        setContentView(binding.root)
        setSupportActionBar(toolBarBinding.toolbarMain)

        binding.recyclerNews.apply {
            layoutManager = LinearLayoutManager(this@NewsActivity)
            adapter = NewsAdapter(this@NewsActivity)
        }

        viewModel.getNews("us","business")
        showProgressBar()
        viewModel.newsResponse.observe(this, {
            when (it) {
                is Resource.Success -> {
                    Log.d(TAG, "Success Data::${it.value}")
                    (binding.recyclerNews.adapter as NewsAdapter).apply {
                        newsData = it.value.articles
                        response = newsData
                        notifyDataSetChanged()
                    }
                 viewModel.insertNews(it.value)
                    hideProgressBar()
                }
                is Resource.Failure -> {
                    Log.d(TAG, "Failure Data::${it.errorBody}")
                    hideProgressBar()
                    viewModel.getOfflineData()
                }
            }
        })
    }

    override fun OnClick(position: Int) {
        if (newsData?.get(position)!=null) {
            val intent = Intent(this, NewsDetailActivity::class.java).putExtra(DETAILS,
                    newsData?.get(position))
            startActivity(intent)
        }
    }

}