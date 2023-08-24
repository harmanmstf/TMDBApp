package com.example.tmdbapp.ui.viewPager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.tmdbapp.ui.home.HomeFragment
import com.example.tmdbapp.ui.topRated.TopRatedFragment

class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment()
            1 -> TopRatedFragment()

            else -> throw IllegalStateException("Invalid position $position")
        }
    }
}