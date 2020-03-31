package com.example.news.views.activities

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.news.R
import com.example.news.databinding.MainActivityDrawerBinding
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
    private lateinit var toggleNavButton: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.drawer_activity_main)
        navigationView = binding.navigationView
        headerView = navigationView.getHeaderView(0)
        navController = findNavController(R.id.nav_host_fragment)
        drawerLayout = binding.drawer
        toggleNavButton = binding.drawerFab
    }

    private fun initializeDrawer() {
        toggleNavButton.setOnClickListener {
            if (!drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.openDrawer(GravityCompat.START)
            } else {
                drawerLayout.closeDrawer(GravityCompat.END)
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        item.isChecked = true
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
            onBackPressed()
        } else action()
    }

    private fun replaceFragment(id: Int) {
        navController.navigate(id)
    }

}