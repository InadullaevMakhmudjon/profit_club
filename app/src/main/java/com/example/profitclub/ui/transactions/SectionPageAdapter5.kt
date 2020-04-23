package com.example.profitclub.ui.transactions

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.profitclub.MainActivity
import com.example.profitclub.R
import com.example.profitclub.ui.transactions.penalty.PenaltyFragment

class SectionPageAdapter5(fm: FragmentManager, context: Context) : FragmentPagerAdapter(fm) {
    lateinit var context: Context

    init {
        this.context = context
    }
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                TransactionFragment()
            }

            1 -> {
                PenaltyFragment()
            }

            else -> Fragment()
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 ->  context.getString(R.string.transaction)

            1 -> context.getString(R.string.penalty_)

            else -> null
        }
    }
}