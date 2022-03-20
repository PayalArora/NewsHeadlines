package com.news.headlines.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.news.headlines.data.response.Articles
import com.news.headlines.databinding.ItemNewsBinding
import com.news.headlines.utils.getProgressDrawable
import com.news.headlines.utils.timeConverter

class NewsAdapter(val onItemClick: OnItemClickPosition) :
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {
    var response: List<Articles>? = null

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val infalter = LayoutInflater.from(viewGroup.context)
        return ViewHolder(ItemNewsBinding.inflate(infalter, viewGroup, false))
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.binding.apply {
            imageView.setOnClickListener {
                Log.e("ONCLICK","ONCLICK")
                onItemClick.OnClick(position)
            }
            val item = response?.get(position)
            val circularProgressDrawable = viewHolder.binding.root.context.getProgressDrawable()
            circularProgressDrawable.start()

            txtNews.text = item?.title
            txtWebsite.text = item?.source?.name
            txtDate.text = item?.publishedAt?.let { viewHolder.binding.root.context.timeConverter(it) }

            if (item?.urlToImage != null) {
                Glide.with(this.root).load(item?.urlToImage)
                    .placeholder(circularProgressDrawable).into(imageView)
            } else
                Glide.with(this.root).load(circularProgressDrawable)
                    .into(imageView)


        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = response?.size ?: 0

}

interface OnItemClickPosition {
    fun OnClick(position:Int)
}

