package com.example.profitclub.ui.account.details


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class SectionPageAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {
        return when (position) {
            0 -> {
                ProfileDetailsFragment()
            }

            1 -> {
                ProfileReviewsFragment()
            }

            else -> null
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Details"

            1 -> "Reviews"

            else -> null
        }
    }
}