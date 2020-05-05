package com.example.news.views.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.ViewPager
import com.example.news.R
import com.example.news.adapters.StartingPagerAdapter
import com.example.news.databinding.StartingActivityBinding
import com.example.news.utils.FullScreen.setFullScreen
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

        setFullScreen(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_starting)

        pagerAdapter = StartingPagerAdapter(supportFragmentManager)
        pagerAdapter.addFragment("1", Welcome())
        pagerAdapter.addFragment("2", SignUp())
        pagerAdapter.addFragment("3", SignIn())

        viewPager = binding.viewPager
        viewPager.adapter = pagerAdapter

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

}