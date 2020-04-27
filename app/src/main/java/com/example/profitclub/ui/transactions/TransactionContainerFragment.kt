package com.example.profitclub.ui.transactions

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.profitclub.MainActivity
import com.example.profitclub.R
import com.example.profitclub.databinding.FragmentBidsBinding
import com.example.profitclub.databinding.FragmentTransactionContainerBinding

class TransactionContainerFragment : Fragment(), View.OnClickListener {
    private lateinit var preferences: SharedPreferences
    private val APP_PREFERENCE = "MYSETTINGS"
    private lateinit var binding: FragmentTransactionContainerBinding
    //private lateinit var galleryViewModel: BidsViewModel
    private var mSectionPageAdapter: SectionPageAdapter5? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //(activity as AppCompatActivity).setSupportActionBar(toolbar)
       // (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val activity = activity as MainActivity?
        activity.let {
            activity?.customActionBarTitle(getString(R.string.bids))
        }
        binding = FragmentTransactionContainerBinding.inflate(layoutInflater)
        preferences = activity!!.getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE)
         when (preferences.getInt("role", 0)){
           5, 7 -> mSectionPageAdapter = SectionPageAdapter5(childFragmentManager, activity, 1)
           2, 4 -> mSectionPageAdapter = SectionPageAdapter5(childFragmentManager, activity, 2)
       }
        //mSectionPageAdapter = SectionPageAdapter5(childFragmentManager, activity!!)
        binding.viewPager.adapter = mSectionPageAdapter
        binding.pagerHeader.setTabIndicatorColorResource(R.color.colorAccent2)

     /*   binding.topUp.setOnClickListener {
            context?.let { it1 -> openApp(it1, "uz.dida.payme") }
        }*/

        binding.withdraw.setOnClickListener {
            context?.let { it1 -> openApp(it1, "uz.dida.payme") }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onClick(p0: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun openApp(context: Context, packageName: String): Boolean {
        val manager = context.packageManager
        try {
            val i = manager.getLaunchIntentForPackage(packageName)
            if (i == null){
                return false
                throw PackageManager.NameNotFoundException()
            }
            i.addCategory(Intent.CATEGORY_LAUNCHER)
            context.startActivity(i)
            return true
        } catch (e: ActivityNotFoundException) {
            return false
        }
    }
}