package com.example.profitclub.ui.questions

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.profitclub.R

class SectionPageAdapter2(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {
        return when (position) {
            0 -> {
                OpenQuestionsFragment()
            }

            1 -> {
                InProgressQuestionsFragment()
            }

            2 -> {
                InApprovingQuestionsFragment()
            }

            3 -> {
                InArbitrationQuestionsFragment()
            }

            4 -> {
                RejectedQuestionsFragment()
            }

            5 -> {
                CompletedQuestionsFragment()
            }

            else -> null
        }
    }

    override fun getCount(): Int {
        return 6
    }
    //getPageTitle(R.string.complain)

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Open"

            1 -> "In Progress"

            2 -> "In Approving"

            3 -> "In Arbitration"

            4 -> "Rejected"

            5 -> "Closed"

            else -> null
        }
    }
}