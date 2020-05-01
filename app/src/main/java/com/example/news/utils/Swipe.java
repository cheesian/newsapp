package com.example.news.utils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.news.adapters.ArticlesAdapter;
import com.example.news.views.fragments.everything.viewModels.EverythingViewModel;
import com.example.news.views.fragments.topHeadlines.viewModels.TopHeadlinesViewModel;

public class Swipe extends ItemTouchHelper.SimpleCallback {

    private ArticlesAdapter articlesAdapter;
    private TopHeadlinesViewModel topHeadlinesViewModel;
    private EverythingViewModel everythingViewModel;

    public Swipe(ArticlesAdapter adapter, TopHeadlinesViewModel topHeadlines) {
        super (0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        articlesAdapter = adapter;
        topHeadlinesViewModel = topHeadlines;
        everythingViewModel = null;
    }

    public Swipe(ArticlesAdapter adapter, EverythingViewModel everything) {
        super (0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        articlesAdapter = adapter;
        everythingViewModel = everything;
        topHeadlinesViewModel = null;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        articlesAdapter.deleteItem(position);
    }
}
