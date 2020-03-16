package com.example.news.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.news.R
import com.example.news.databinding.MainActivityBinding
import com.example.news.views.fragments.topHeadlines.TopHeadlines
import com.example.news.views.fragments.sources.Sources
import com.example.news.views.fragments.everything.Everything
import com.example.tabapplication.PagerAdapter
import com.google.android.material.tabs.TabLayout
import com.example.news.utils.FullScreen.setFullScreen

class MainActivity : AppCompatActivity() {
    lateinit var binding: MainActivityBinding
    lateinit var adapter: PagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val toolbar = binding.toolbar
        toolbar.title = ""
        val tabLayout = binding.tabLayout
        val viewPager = binding.pager
        setSupportActionBar(toolbar)
        adapter = PagerAdapter(supportFragmentManager)
        adapter.addFragment(TopHeadlines(), "Trending News Only")
        adapter.addFragment(Sources(), "All News Sources")
        adapter.addFragment(Everything(), "All News Articles")
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(p0: TabLayout.Tab?) {

            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {

            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                viewPager.currentItem = p0!!.position
            }

        })


    }
}

