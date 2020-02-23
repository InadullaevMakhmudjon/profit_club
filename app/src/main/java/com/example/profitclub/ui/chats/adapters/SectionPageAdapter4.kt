package com.example.profitclub.ui.chats.adapters


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.profitclub.ui.chats.chat1.ChatsView1Fragment
import com.example.profitclub.ui.chats.chat2.ChatsView2Fragment

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