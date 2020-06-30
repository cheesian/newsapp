package com.programiqsolutions.news.adapters

import androidx.recyclerview.widget.DiffUtil
import com.programiqsolutions.news.data.response.sources.SourceResponseEntity


/**
Created by ian
 */

class SourcesDiffCallBack: DiffUtil.ItemCallback<SourceResponseEntity>() {
    override fun areItemsTheSame(
        oldItem: SourceResponseEntity,
        newItem: SourceResponseEntity
    ): Boolean = itemSimilarityCheck(oldItem, newItem)

    override fun areContentsTheSame(
        oldItem: SourceResponseEntity,
        newItem: SourceResponseEntity
    ): Boolean = contentSimilarityCheck(oldItem, newItem)

    private fun itemSimilarityCheck(sourceResponseEntity1: SourceResponseEntity, sourceResponseEntity2: SourceResponseEntity): Boolean{
        val check1 = (sourceResponseEntity1.id == sourceResponseEntity2.id)
        val check2 = (sourceResponseEntity1.category == sourceResponseEntity2.category)
        val check3 = (sourceResponseEntity1.country == sourceResponseEntity2.country)
        val check4 = (sourceResponseEntity1.name == sourceResponseEntity2.name)
        return check1 && check2 && check3 && check4
    }

    private fun contentSimilarityCheck(sourceResponseEntity1: SourceResponseEntity, sourceResponseEntity2: SourceResponseEntity): Boolean {
        val check1 = (sourceResponseEntity1.url == sourceResponseEntity2.url)
        val check2 = (sourceResponseEntity1.language == sourceResponseEntity2.language)
        return check1 && check2
    }
}