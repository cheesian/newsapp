package com.example.news.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter


/**
Created by ian
 */

class StartingPagerAdapter (fragmentManager: FragmentManager): FragmentStatePagerAdapter(fragmentManager){

    val titles = mutableListOf<String>()
    val fragments = mutableListOf<Fragment>()

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return titles.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }

    fun addFragment (title: String, fragment: Fragment) {
        fragments.add(fragment)
        titles.add(title)
    }

}