package com.programiqsolutions.news.adapters

import androidx.recyclerview.widget.DiffUtil
import com.programiqsolutions.news.data.response.everything.ArticleResponseEntity


/**
Created by ian
 */

class ArticlesDiffCallBack: DiffUtil.ItemCallback<ArticleResponseEntity>() {
    override fun areItemsTheSame(
        oldItem: ArticleResponseEntity,
        newItem: ArticleResponseEntity
    ): Boolean = itemSimilarityCheck(oldItem, newItem)

    override fun areContentsTheSame(
        oldItem: ArticleResponseEntity,
        newItem: ArticleResponseEntity
    ): Boolean = contentSimilarityCheck(oldItem, newItem)

    private fun itemSimilarityCheck(entity1: ArticleResponseEntity, entity2: ArticleResponseEntity): Boolean{
        val check1 = (entity1.author == entity2.author)
        val check2 = (entity1.publishedAt == entity2.publishedAt)
        val check3 = (entity1.title == entity2.title)
        return check1 && check2 && check3
    }

    private fun contentSimilarityCheck(entity1: ArticleResponseEntity, entity2: ArticleResponseEntity): Boolean{
        val check1 = (entity1.content == entity2.content)
        val check2 = (entity1.description == entity2.description)
        val check3 = (entity1.url == entity2.url)
        val check4 = (entity1.urlToImage == entity2.urlToImage)
        return check1 && check2 && check3 && check4
    }
}