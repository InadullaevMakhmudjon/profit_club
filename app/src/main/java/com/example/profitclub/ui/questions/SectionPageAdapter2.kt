package com.example.profitclub.ui.questions

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.profitclub.R
import com.example.profitclub.ui.questions.cancel.RejectedQuestionsFragment
import com.example.profitclub.ui.questions.close.CompletedQuestionsFragment
import com.example.profitclub.ui.questions.dispute.InArbitrationQuestionsFragment
import com.example.profitclub.ui.questions.open.OpenQuestionsFragment

class SectionPageAdapter2(fm: FragmentManager, context: Context) : FragmentPagerAdapter(fm) {
    var context: Context = context
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                OpenQuestionsFragment()
            }
            1 -> {
                InArbitrationQuestionsFragment()
            }
            2 -> {
                RejectedQuestionsFragment()
            }
            3 -> {
                CompletedQuestionsFragment()
            }
            else -> Fragment()
        }
    }

    override fun getCount(): Int {
        return 4
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> context.getString(R.string.bids_client)
            1 -> context.getString(R.string.arbitration)
            2 -> context.getString(R.string.closed)
            3 -> context.getString(R.string.cancelled)
            else -> null
        }
    }
}