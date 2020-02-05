package com.example.profitclub.ui.chats


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.profitclub.R

class SectionPageAdapter4(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                ChatsView1Fragment()
            }

            1 -> {
                ChatsView2Fragment()
            }

            else -> Fragment()
        }
    }

    override fun getCount(): Int {
        return 2
    }
    //getPageTitle(R.string.complain)

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Message"

            1 -> "Files"

            else -> null
        }
    }
}