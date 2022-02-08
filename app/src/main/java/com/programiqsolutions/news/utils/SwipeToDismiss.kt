package com.programiqsolutions.news.utils

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.programiqsolutions.news.adapters.ArticlesAdapterNew
import com.programiqsolutions.news.adapters.ArticlesAdapterOld
import com.programiqsolutions.news.adapters.SourcesAdapter


/**
Created by ian
 */

class SwipeToDismiss (
    private val articlesAdapterNew: ArticlesAdapterNew? = null,
    private val articlesAdapterOld: ArticlesAdapterOld? = null,
    private val sourcesAdapter: SourcesAdapter? = null
): ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

//    Example usage in activity:
//    ItemTouchHelper(SwipeToDismiss(adapter)).attachToRecyclerView(recyclerView)
    private var adapterType: Int = when {
        articlesAdapterNew != null -> 1
        sourcesAdapter != null -> 2
        else -> 0
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        when (adapterType) {
            0 -> {}
            1 -> {
                articlesAdapterNew!!.deleteItem(position)
            }
            2 -> {
                sourcesAdapter!!.deleteItem(position)
            }
        }
    }

}