package com.example.profitclub.ui.bids

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.profitclub.R
import com.example.profitclub.ui.bids.approve.InApprovingBidsFragment
import com.example.profitclub.ui.bids.cancel.RejectedBidsFragment
import com.example.profitclub.ui.bids.close.ClosedBidsFragment
import com.example.profitclub.ui.bids.dispute.InArbitrationBidsFragment
import com.example.profitclub.ui.bids.open.OpenBidsFragment
import com.example.profitclub.ui.browse.BrowseFragment

class SectionPageAdapter3(fm: FragmentManager, context: Context) : FragmentPagerAdapter(fm) {
    lateinit var context: Context

    init {
        this.context = context
    }
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

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 ->  context.getString(R.string.bids)

            1 -> context.getString(R.string.bids_consultant)

            2 -> context.getString(R.string.approve)

            3 -> context.getString(R.string.arbitration)

            4 -> context.getString(R.string.closed)

            5 -> context.getString(R.string.cancelled)

            else -> null
        }
    }
}