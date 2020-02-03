package com.example.profitclub.ui.bids


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.profitclub.R
import com.example.profitclub.ui.browse.BrowseFragment

class SectionPageAdapter3(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {
        return when (position) {
            0 -> {
                BrowseFragment()
            }

            1 -> {
                OpenBidsFragment()
            }

            2 -> {
                InProgressBidsFragment()
            }

            3 -> {
                InApprovingBidsFragment()
            }

            4 -> {
                InArbitrationBidsFragment()
            }

            5 -> {
                RejectedBidsFragment()
            }

            6 -> {
                ClosedBidsFragment()
            }
            else -> null
        }
    }

    override fun getCount(): Int {
        return 7
    }
    //getPageTitle(R.string.complain)

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Browse"

            1 -> "Open"

            2 -> "In Progress"

            3 -> "In Approving"

            4 -> "In Arbitration"

            5 -> "Rejected"

            6 -> "Closed"

            else -> null
        }
    }
}