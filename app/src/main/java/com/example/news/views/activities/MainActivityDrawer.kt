package com.example.news.views.activities

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.news.R
import com.example.news.databinding.MainActivityDrawerBinding
import com.example.news.utils.FullScreen.setFullScreen
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView


/**
Created by ian
 */

class MainActivityDrawer : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var binding: MainActivityDrawerBinding
    private lateinit var navigationView: NavigationView
    private lateinit var headerView: View
    private lateinit var navController: NavController
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var drawerFab: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen(this)
        binding = DataBindingUtil.setContentView(this, R.layout.drawer_activity_main)
        navigationView = binding.navigationView
        headerView = navigationView.getHeaderView(0)
        headerView.findViewById<TextView>(R.id.name_tag).apply {
//            the person's initials should be shown in this textview
            text = getInitials()
        }
        navController = findNavController(R.id.nav_host_fragment)
//        this ensures the selected item is highlighted automatically
        navigationView.setupWithNavController(navController)
        navigationView.setNavigationItemSelectedListener(this)
        drawerLayout = binding.drawer
        drawerFab = binding.drawerFab
        drawerFab.setOnClickListener {
            when (drawerLayout.isOpen) {
                false -> drawerLayout.open()
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        drawerLayout.closeDrawers()
        when (item.itemId) {

            R.id.top_headlines_menu -> {
                checkIfIsCurrentFragment(R.id.top_headlines_fragment) {
                    replaceFragment(R.id.top_headlines_fragment)
                }
            }

            R.id.everything_menu -> {
                checkIfIsCurrentFragment(R.id.everything_fragment) {
                    replaceFragment(R.id.everything_fragment)
                }
            }

            R.id.sources_menu -> {
                checkIfIsCurrentFragment(R.id.sources_fragment) {
                    replaceFragment(R.id.sources_fragment)
                }
            }

        }
        return true
    }

    private fun checkIfIsCurrentFragment(fragmentID: Int, action: () -> Unit) {
        val currentLocation = navController.currentDestination!!.id
        if (currentLocation == fragmentID) {
            drawerLayout.closeDrawers()
        } else action()
    }

    private fun replaceFragment(id: Int) {
        navController.navigate(id)
    }

    private fun getInitials(name: String = "Johnny Cage"): String {
        return when {
            name.trim().isEmpty() -> "JC"
            else -> with(name.split(" ")) {
                val initial1 = this[0].take(1)
                val initial2 = this[1].take(1)
                "$initial1$initial2"
            }
        }
    }

}