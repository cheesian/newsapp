package com.programiqsolutions.news.utils

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.programiqsolutions.news.adapters.ArticlesAdapter
import com.programiqsolutions.news.adapters.SourcesAdapter


/**
Created by ian
 */

class SwipeToDismiss (
    private val articlesAdapter: ArticlesAdapter? = null,
    private val sourcesAdapter: SourcesAdapter? = null
): ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

//    Example usage in activity:
//    ItemTouchHelper(SwipeToDismiss(adapter)).attachToRecyclerView(recyclerView)
    private var adapterType: Int = when {
        articlesAdapter != null -> 1
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
                articlesAdapter!!.deleteItem(position)
            }
            2 -> {
                sourcesAdapter!!.deleteItem(position)
            }
        }
    }

}