package com.example.news.utils

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.news.adapters.ArticlesAdapter
import com.example.news.adapters.SourcesAdapter


/**
Created by ian
 */

class SwipeToDismiss (
    private val articlesAdapter: ArticlesAdapter? = null,
    private val sourcesAdapter: SourcesAdapter? = null
): ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

//    Example usage in activity:
//    ItemTouchHelper(SwipeToDismiss(adapter)).attachToRecyclerView(recyclerView)
    private var adapterType: Int

    init {
        adapterType = when {
            articlesAdapter != null -> 1
            sourcesAdapter != null -> 2
            else -> 0
        }
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