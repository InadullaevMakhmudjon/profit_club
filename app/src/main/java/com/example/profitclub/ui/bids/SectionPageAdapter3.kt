package com.example.profitclub.ui.bids

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.profitclub.ui.bids.approve.InApprovingBidsFragment
import com.example.profitclub.ui.bids.cancel.RejectedBidsFragment
import com.example.profitclub.ui.bids.close.ClosedBidsFragment
import com.example.profitclub.ui.bids.dispute.InArbitrationBidsFragment
import com.example.profitclub.ui.bids.open.OpenBidsFragment
import com.example.profitclub.ui.browse.BrowseFragment

class SectionPageAdapter3(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                BrowseFragment()
            }

            1 -> {
                OpenBidsFragment()
            }

           /* 2 -> {
                InProgressBidsFragment()
            }*/

            2 -> {
                InApprovingBidsFragment()
            }

            3 -> {
                InArbitrationBidsFragment()
            }

            4 -> {
                RejectedBidsFragment()
            }

            5 -> {
                ClosedBidsFragment()
            }
            else -> Fragment()
        }
    }

    override fun getCount(): Int {
        return 6
    }
    //getPageTitle(R.string.complain)

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Browse"

            1 -> "Open"

            2 -> "In Approving"

            3 -> "In Arbitration"

            4 -> "Rejected"

            5 -> "Closed"

            else -> null
        }
    }
}