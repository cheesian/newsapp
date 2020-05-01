package com.example.news.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.news.R
import com.example.news.data.response.everything.ArticleResponseEntity
import com.example.news.data.response.topHeadlines.TopHeadlinesResponseEntity
import com.example.news.utils.CircleTransform
import com.example.news.utils.Notify.snackBar
import com.example.news.utils.Notify.toast
import com.example.news.views.activities.ArticleActivity


/**
Created by ian
 */

class ArticlesAdapter (context: Context) : RecyclerView.Adapter<ArticlesAdapter.ArticlesHolder>() {

    private var articleList = mutableListOf<ArticleResponseEntity>()
    private val adapterContext = context
    private var rootView: View? = null
    private var origin = 0

    inner class ArticlesHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val articleImage = itemView.findViewById<ImageView>(R.id.adapterImage)!!
        val articleTitle = itemView.findViewById<TextView>(R.id.adapterTitleView)!!
        val articleText = itemView.findViewById<TextView>(R.id.adapterTextView)!!
        val articleFooter = itemView.findViewById<TextView>(R.id.adapterFooterView)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticlesHolder {
        val inflater: LayoutInflater = LayoutInflater.from(adapterContext)
        val itemView = inflater.inflate(R.layout.list_items, parent, false)
        rootView =itemView.rootView
        return ArticlesHolder(itemView)
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    override fun onBindViewHolder(articlesHolder: ArticlesHolder, position: Int) {

        if (articleList.isNullOrEmpty()) {
            toast(adapterContext, "No news to display")
        } else {
            val currentArticle = articleList[position]
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
        this.articleList = mutableListOf()
        this.articleList.addAll(itemList)
        origin++
        notifyDataSetChanged()
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
            view = rootView!!,
            message = "Item removed",
            actionMessage = "Undo",
            function = { undoDelete(position, deletedItem) }
        )
    }

    private fun undoDelete (position: Int, articleResponseEntity: ArticleResponseEntity) {
        articleList.add(position, articleResponseEntity)
        notifyItemInserted(position)
    }

}