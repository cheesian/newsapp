package com.programiqsolutions.news.views.activities.start

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.programiqsolutions.news.NewsApp
import com.programiqsolutions.news.R
import com.programiqsolutions.news.VMFactory
import com.programiqsolutions.news.adapters.StartingPagerAdapter
import com.programiqsolutions.news.databinding.StartingActivityBinding
import com.programiqsolutions.news.utils.DepthPageTransformer
import com.programiqsolutions.news.utils.FullScreen.setFullScreen
import com.programiqsolutions.news.utils.TabLayout.createTabWithIcon
import com.programiqsolutions.news.views.activities.main.MainActivityDrawer
import com.programiqsolutions.news.views.activities.start.viewModels.StartingViewModel
import com.programiqsolutions.news.views.fragments.start.SignIn
import com.programiqsolutions.news.views.fragments.start.SignUp
import com.programiqsolutions.news.views.fragments.start.Welcome
import com.google.android.material.tabs.TabLayout
import com.programiqsolutions.news.data.request.user.UserEntity
import javax.inject.Inject


/**
Created by ian
 */

class StartingActivity: AppCompatActivity() {

    lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager
    lateinit var binding: StartingActivityBinding
    lateinit var pagerAdapter: StartingPagerAdapter
    @Inject lateinit var factory: VMFactory
    lateinit var viewModel: StartingViewModel
    lateinit var welcomeTab: TextView
    lateinit var signUpTab: TextView
    lateinit var signInTab: TextView

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        (applicationContext as NewsApp).applicationComponent.inject(this)
        INSTANCE = this
        PAGE_STACK.add(0)

        setFullScreen(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_starting)
        viewModel = ViewModelProvider(this, factory).get(StartingViewModel::class.java)
        viewModel.users.observe(this, Observer {
            if (it.isEmpty()) {
                viewModel.accountRepository.insertUser(UserEntity("default", "default", "token"))
            }
            startActivity(Intent(this, MainActivityDrawer::class.java))
            finish()
        })

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
        welcomeTab = createTabWithIcon(
            rootContext = binding.root.context,
            tabTemplate = R.layout.activity_starting_tabs,
            tabText = "",
            tabIcon = R.drawable.ic_home_white
        )
        signUpTab = createTabWithIcon(
            rootContext = binding.root.context,
            tabTemplate = R.layout.activity_starting_tabs,
            tabText = "",
            tabIcon = R.drawable.ic_add_user_white
        )
        signInTab = createTabWithIcon(
            rootContext = binding.root.context,
            tabTemplate = R.layout.activity_starting_tabs,
            tabText = "",
            tabIcon = R.drawable.ic_user_account_white
        )
        tabLayout.getTabAt(0)?.customView = welcomeTab
        tabLayout.getTabAt(1)?.customView = signUpTab
        tabLayout.getTabAt(2)?.customView = signInTab
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