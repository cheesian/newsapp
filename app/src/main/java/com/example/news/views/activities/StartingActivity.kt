package com.example.news.views.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.ViewPager
import com.example.news.R
import com.example.news.adapters.StartingPagerAdapter
import com.example.news.databinding.StartingActivityBinding
import com.example.news.utils.DepthPageTransformer
import com.example.news.utils.FullScreen.setFullScreen
import com.example.news.utils.Notify.toast
import com.example.news.utils.ZoomOutPageTransformer
import com.example.news.views.fragments.start.SignIn
import com.example.news.views.fragments.start.SignUp
import com.example.news.views.fragments.start.Welcome
import com.google.android.material.tabs.TabLayout


/**
Created by ian
 */

class StartingActivity: AppCompatActivity() {

    lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager
    lateinit var binding: StartingActivityBinding
    lateinit var pagerAdapter: StartingPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        INSTANCE = this
        PAGE_STACK.add(0)

        setFullScreen(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_starting)

        pagerAdapter = StartingPagerAdapter(supportFragmentManager)
        pagerAdapter.addFragment("1", Welcome())
        pagerAdapter.addFragment("2", SignUp())
        pagerAdapter.addFragment("3", SignIn())

        viewPager = binding.viewPager
        viewPager.adapter = pagerAdapter
        viewPager.setPageTransformer(true, DepthPageTransformer())
        viewPager.addOnPageChangeListener(
            object: ViewPager.SimpleOnPageChangeListener () {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
//                    when a page is selected, its previous instance is removed and current one is placed on the top of the PAGE_STACK
                    PAGE_STACK.apply {
                        removeIf { it == position }
                        add(position)
                    }

                }
            }
        )

        tabLayout = binding.tabs
        tabLayout.setupWithViewPager(viewPager)
        tabLayout.addOnTabSelectedListener(
            object: TabLayout.OnTabSelectedListener {
                override fun onTabReselected(tab: TabLayout.Tab?) {

                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {

                }

                override fun onTabSelected(tab: TabLayout.Tab?) {
                    viewPager.currentItem = tab!!.position
                }

            }
        )
    }

    override fun onDestroy() {
        super.onDestroy()
//        memory management for companion objects
        INSTANCE = null
        PAGE_STACK.clear()
    }

    private fun handleBackPress() {
        with (PAGE_STACK) {
//            remove the page which was onBackPressed from the PAGE_STACK
            remove(viewPager.currentItem)
//            set the current page to the last page which was added to the PAGE_STACK
            viewPager.currentItem = this[size-1]
        }
    }

    override fun onBackPressed() {
        if (PAGE_STACK.size == 1)
            super.onBackPressed()
        else
            handleBackPress()
    }

    companion object {
        var INSTANCE: StartingActivity? = null
        var PAGE_STACK = mutableListOf<Int>()
    }

}