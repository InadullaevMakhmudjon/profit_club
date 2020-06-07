package com.example.profitclub.ui.account.details

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.profitclub.R

class SectionPageAdapter(fm: FragmentManager, context: Context) : FragmentPagerAdapter(fm) {
    var context: Context = context
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                ProfileDetailsFragment()
            }
            1 -> {
                ProfileReviewsFragment()
            }
            else -> Fragment()
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> context.getString(R.string.about)
            1 -> context.getString(R.string.reviews)
            else -> null
        }
    }
}