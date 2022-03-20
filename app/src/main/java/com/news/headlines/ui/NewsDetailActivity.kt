package com.news.headlines.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.news.headlines.R
import com.news.headlines.data.response.Articles
import com.news.headlines.databinding.ActivityNewsDetailBinding
import com.news.headlines.utils.DETAILS
import com.news.headlines.utils.getProgressDrawable
import com.news.headlines.utils.timeConverter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewsDetailBinding
    var articles:Articles? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var articles: Articles? = intent.getParcelableExtra(DETAILS)
        articles?.let { updateUi(it) }
    }

    private fun updateUi(articles:Articles) {
        binding.txtNewsTitle.setText(articles.title)
        binding.txtDescription.setText(articles.description)
        val circularProgressDrawable = getProgressDrawable()
        circularProgressDrawable.start()
        if (articles.urlToImage != null) {
            Glide.with(this).load(articles.urlToImage)
                .placeholder(circularProgressDrawable).into(binding.imageView)
        } else
            Glide.with(this).load(circularProgressDrawable)
                .into(binding.imageView)
        binding.txtWebsite.setText(articles.source?.name)
        binding.txtDate.setText(articles.publishedAt?.let {timeConverter(it) })
        binding.backImg.setOnClickListener { onBackPressed() }


    }
}