package com.example.profitclub.ui.bids

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.profitclub.MainActivity
import com.example.profitclub.R
import kotlinx.android.synthetic.main.fragment_questions.*
import com.example.profitclub.adapters.QuestionsAdapter
import com.example.profitclub.databinding.FragmentBidsBinding

class BidsFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentBidsBinding
    private lateinit var galleryViewModel: BidsViewModel
    private var mSectionPageAdapter: SectionPageAdapter3? = null

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
        galleryViewModel =
            ViewModelProviders.of(this).get(BidsViewModel::class.java)
       // val root = inflater.inflate(R.layout.fragment_questions, container, false)
        binding = FragmentBidsBinding.inflate(layoutInflater)

        mSectionPageAdapter = SectionPageAdapter3(childFragmentManager, activity!!)
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