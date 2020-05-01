package com.example.news.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.news.data.response.sources.SourceResponseEntity
import com.example.news.databinding.SourcesItemBinding
import com.example.news.utils.Notify
import com.example.news.views.activities.ArticleActivity


/**
Created by ian
 */

class SourcesAdapter (var context: Context): RecyclerView.Adapter<SourcesAdapter.SourcesHolder> () {

    private var listItems = mutableListOf<SourceResponseEntity>()
    private var rootView: View? = null

    inner class SourcesHolder(binding: SourcesItemBinding): RecyclerView.ViewHolder(binding.root) {
        val title = binding.titleViewSources
        val text = binding.textViewSources
        val footer = binding.footerViewSources
        val rootView = binding.root
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SourcesHolder {
        val layoutInflater = LayoutInflater.from(context)
        val binding = SourcesItemBinding.inflate(layoutInflater, parent, false)
        rootView = binding.root
        return SourcesHolder(binding)
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    override fun onBindViewHolder(holder: SourcesHolder, position: Int) {
        val currentItem = listItems[position]
        val name = currentItem.name
        val description = currentItem.description
        val url = currentItem.url
        val language = currentItem.language
        val country = currentItem.country
        val footer = "Language: $language. Country: $country"
        if (url.isNullOrBlank()) {
            holder.rootView.setOnClickListener {
                Notify.toast(it.context, "No link was provided for this source")
            }
        } else {
            val intent = Intent(context, ArticleActivity::class.java)
            val source = "sources"
            intent.putExtra("url", url)
            intent.putExtra("origin", source)
            holder.rootView.setOnClickListener {
                context.startActivity(intent)
            }
        }
        holder.title.text = name
        holder.text.text = description
        holder.footer.text = footer

    }

    fun setItems(items: List<SourceResponseEntity>) {
        this.listItems = mutableListOf()
        this.listItems.addAll(items.sortedByDescending { it.name })
        notifyDataSetChanged()
    }

    fun deleteItem(position: Int) {
        val deletedItem = listItems[position]
        listItems.removeAt(position)
        notifyItemRemoved(position)
        Notify.snackBar(
            view = rootView!!,
            message = "Item removed",
            actionMessage = "Undo",
            function = { undoDelete(position, deletedItem) }
        )
    }

    private fun undoDelete (position: Int, sourceResponseEntity: SourceResponseEntity) {
        listItems.add(position, sourceResponseEntity)
        notifyItemInserted(position)
    }
}