package com.example.profitclub.ui.transactions

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
        /*galleryViewModel =
            ViewModelProviders.of(this).get(BidsViewModel::class.java)*/
       // val root = inflater.inflate(R.layout.fragment_questions, container, false)
        binding = FragmentTransactionContainerBinding.inflate(layoutInflater)

        mSectionPageAdapter = SectionPageAdapter5(childFragmentManager, activity!!)
        binding.viewPager.adapter = mSectionPageAdapter
        binding.pagerHeader.setTabIndicatorColorResource(R.color.colorAccent2)

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
}