package com.programiqsolutions.news.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.programiqsolutions.news.R
import com.programiqsolutions.news.data.response.everything.ArticleResponseEntity
import com.programiqsolutions.news.data.response.topHeadlines.TopHeadlinesResponseEntity
import com.programiqsolutions.news.databinding.ListItemsBinding
import com.programiqsolutions.news.utils.CircleTransform
import com.programiqsolutions.news.utils.Notify.snackBar
import com.programiqsolutions.news.utils.Notify.toast
import com.programiqsolutions.news.views.activities.ArticleActivity


/**
Created by ian
 */

class ArticlesAdapter (
    private val adapterContext: Context,
    private val rootView: View
) : ListAdapter<ArticleResponseEntity, ArticlesAdapter.ArticlesHolder>(ArticlesDiffCallBack()) {

    private var articleList = mutableListOf<ArticleResponseEntity>()
    private var origin = 0

    inner class ArticlesHolder(binding: ListItemsBinding) : RecyclerView.ViewHolder(binding.root) {
        val articleImage = binding.adapterImage
        val articleTitle = binding.adapterTitleView
        val articleText = binding.adapterTextView
        val articleFooter = binding.adapterFooterView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticlesHolder {
        val inflater: LayoutInflater = LayoutInflater.from(adapterContext)
        val itemViewBinding = ListItemsBinding.inflate(inflater, parent, false)
        return ArticlesHolder(itemViewBinding)
    }

    override fun onBindViewHolder(articlesHolder: ArticlesHolder, position: Int) {

        if (articleList.isNullOrEmpty()) {
            toast(adapterContext, "No news to display")
        } else {
            val currentArticle = getItem(position)
            articlesHolder.articleTitle.text = currentArticle.title
            articlesHolder.articleText.text = currentArticle.description
            val author = currentArticle.author ?: "Anonymous"
            val authorText = "By $author"
            val publishedAt = currentArticle.publishedAt
            val timeText = "At $publishedAt"
            val footer = "$authorText \n$timeText"
            articlesHolder.articleFooter.text = footer
            if (currentArticle.urlToImage.isNullOrBlank()) {
                Glide.with(articlesHolder.itemView)
                    .load(R.drawable.ic_broken_image_black_24dp)
                    .transform(CircleTransform(adapterContext))
                    .into(articlesHolder.articleImage)
            } else {
                Glide.with(articlesHolder.itemView)
                    .load(currentArticle.urlToImage)
                    .transform(CircleTransform(adapterContext))
                    .into(articlesHolder.articleImage)
            }

            if (currentArticle.url.isNullOrBlank()) {
                articlesHolder.itemView.setOnClickListener {
                    toast(it.context, "No link was provided for this article")
                }
            } else {
                val intent = Intent(adapterContext, ArticleActivity::class.java)
                val source = if (origin==1) "everything" else "topHeadlines"
                intent.putExtra("url", currentArticle.url)
                intent.putExtra("origin", source)
                articlesHolder.itemView.setOnClickListener {
                    adapterContext.startActivity(intent)
                }
            }
        }

    }

    fun setItems(itemList: List<ArticleResponseEntity>) {
        this.articleList.clear()
        this.articleList.addAll(itemList)
        this.articleList.distinct()
        origin++
        submitList(this.articleList)
    }

    fun setTopHeadlinesItems(itemList: List<TopHeadlinesResponseEntity>) {
        val list = mutableListOf<ArticleResponseEntity>()
        for (item in itemList) {
            val entity = ArticleResponseEntity(
                item.author,
                item.title,
                item.description,
                item.url,
                item.urlToImage,
                item.publishedAt,
                item.content,
                item.sourceResponseEntity
            )
            list.add(entity)
        }
        origin++
        setItems(list)
    }

    fun deleteItem(position: Int) {
        val deletedItem = articleList[position]
        articleList.removeAt(position)
        notifyItemRemoved(position)
        snackBar(
            view = rootView,
            message = "Item removed",
            actionMessage = "Undo",
            function = { undoDelete(position, deletedItem) }
        )
    }

    fun undoDelete (position: Int, articleResponseEntity: ArticleResponseEntity) {
        articleList.add(position, articleResponseEntity)
        notifyItemInserted(position)
    }

}