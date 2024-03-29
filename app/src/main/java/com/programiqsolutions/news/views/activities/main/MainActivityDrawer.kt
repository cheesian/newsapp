package com.programiqsolutions.news.views.activities.main

import android.Manifest
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.programiqsolutions.news.NewsApp
import com.programiqsolutions.news.R
import com.programiqsolutions.news.VMFactory
import com.programiqsolutions.news.data.Constants.STORAGE_PERMISSIONS_REQUEST_CODE
import com.programiqsolutions.news.data.request.user.UserEntity
import com.programiqsolutions.news.databinding.MainActivityDrawerBinding
import com.programiqsolutions.news.utils.FullScreen.setFullScreen
import com.programiqsolutions.news.utils.Notify.snackBar
import com.programiqsolutions.news.utils.Notify.toast
import com.programiqsolutions.news.views.activities.main.viewModels.MainActivityViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.programiqsolutions.news.services.viewModel.WorkerViewModel
import javax.inject.Inject

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
    private lateinit var logoutText: TextView
    private lateinit var user: UserEntity
    @Inject lateinit var factory: VMFactory
    lateinit var viewModel: MainActivityViewModel
    lateinit var workerViewModel: WorkerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (applicationContext as NewsApp).applicationComponent.inject(this)
        setFullScreen(this)
        binding = DataBindingUtil.setContentView(this, R.layout.drawer_activity_main)
        viewModel = ViewModelProvider(this, factory).get(MainActivityViewModel::class.java)
        workerViewModel = ViewModelProvider(this).get(WorkerViewModel::class.java)
        when {
            viewModel.getUserList().isNullOrEmpty() -> logout()
            else -> user = viewModel.getUserList()[0]
        }
        navigationView = binding.navigationView
        headerView = navigationView.getHeaderView(0)
        headerView.findViewById<TextView>(R.id.name_tag).apply {
//            the person's initials should be shown in this textview
            text = getInitials(user.username)
        }
        headerView.findViewById<TextView>(R.id.tv_name).apply {
//            the person's initials should be shown in this textview
            text = user.username
        }
        headerView.findViewById<TextView>(R.id.tv_email).apply {
//            the person's initials should be shown in this textview
            text = user.email
        }
        logoutText = headerView.findViewById<TextView>(R.id.tv_logout).apply {
            setOnClickListener {
//            Log out and redirect to StartingActivity
                logout()
            }
        }
        navController = findNavController(R.id.nav_host_fragment)
//        this ensures the selected item is highlighted automatically
        navigationView.setupWithNavController(navController)
        navigationView.setNavigationItemSelectedListener(this)
        drawerLayout = binding.drawer
        drawerFab = binding.drawerFab
        drawerFab.setOnClickListener {
            when(drawerLayout.isOpen) {
                false -> drawerLayout.open()
            }
        }
    }

    private fun showStoragePermissionsRationale () {
        val builder = with (AlertDialog.Builder(binding.root.context, R.style.AlertDialog)) {
            setTitle("Storage permission")
            setMessage("This permission is needed to store news on your device")
            setPositiveButton("Ok") { _: DialogInterface, _: Int ->
                requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), STORAGE_PERMISSIONS_REQUEST_CODE)
            }
            setNegativeButton("Cancel") { dialogInterface: DialogInterface, _: Int ->
                dialogInterface.dismiss()
                snackBar(
                    view = binding.root,
                    message = "Local storage disabled"
                )
            }
            this
        }
        builder.apply {
            create()
            show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            STORAGE_PERMISSIONS_REQUEST_CODE -> {
                if (grantResults.contains(PackageManager.PERMISSION_GRANTED)) {
                    snackBar(binding.root, "Storage permission granted")
                } else {
                    snackBar(binding.root, "Storage permission denied")
                }
                return
            }
        }
    }

    private fun requestStoragePermissions(){
        when (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            PackageManager.PERMISSION_GRANTED -> {
                toast(this, "Storage permission granted")
            }

            PackageManager.PERMISSION_DENIED -> {
                showStoragePermissionsRationale()
            }
        }
    }

    private fun logout() {
//        clear account information before logging out
        viewModel.deleteAccounts()
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

            R.id.feedback_menu -> {
                checkIfIsCurrentFragment(R.id.feedback_fragment) {
                    replaceFragment(R.id.feedback_fragment)
                }
            }

            R.id.optimize_menu -> {
                workerViewModel.deleteExcessiveArticles()
            }

            R.id.private_menu -> {
                checkIfIsCurrentFragment(R.id.private_login_fragment) {
                    replaceFragment(R.id.private_login_fragment)
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
                if (this.size > 1) {
                    val initial1 = this[0].take(1)
                    val initial2 = this[1].take(1)
                    "$initial1$initial2"
                } else {
                    this[0].take(1)
                }

            }
        }
    }

}