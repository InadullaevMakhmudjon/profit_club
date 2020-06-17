package com.example.profitclub.ui.browse

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.profitclub.App
import com.example.profitclub.R
import com.example.profitclub.adapters.BrowseListAdapter
import com.example.profitclub.data.bids.ConsultantBidsData
import com.example.profitclub.databinding.FragmentBrowseBinding
import com.example.profitclub.toast

class BrowseFragment : Fragment(), View.OnClickListener {
    private lateinit var viewModel: BrowseViewModel
    private val APP_PREFERENCE = "MYSETTINGS"
    private lateinit var binding: FragmentBrowseBinding
    private var layoutManager: LinearLayoutManager? = null
    private var adapter: BrowseListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBrowseBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let {activity ->
            val preferences = activity.getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE)
            viewModel =
                ViewModelProviders.of(this, BrowseViewModelFactory(preferences)).get(BrowseViewModel::class.java)
            val adapterData = ArrayList<ConsultantBidsData>()
            adapter = BrowseListAdapter(this.context!!, adapterData, this)
            layoutManager = LinearLayoutManager(this.context!!, LinearLayoutManager.VERTICAL, false)
            binding.browseList.layoutManager = layoutManager
            binding.browseList.adapter = adapter

            viewModel.bidsConsView.observe(viewLifecycleOwner, Observer {data ->
                if (data != null){
                   // toast("come bid data")
                    adapterData.clear()
                    adapterData.addAll(data.data)
                    adapter!!.notifyDataSetChanged()
                }
            })

            viewModel.error.observe(viewLifecycleOwner, Observer { message ->
                toast("Error: $message")
            })

            (activity.application as App).getSocket.on("update_add_bids") {
                activity.runOnUiThread {
                    viewModel.getData()
                }
            }
            viewModel.getData()
        }
    }

/*
    private fun setSingleEvent(mainGrid: GridLayout) {
        for(i in 0 until mainGrid.childCount){
            var cardview = mainGrid.getChildAt(i) as CardView
            cardview.setOnClickListener {
                startActivity(Intent(context, BrowseQuestionsActivity::class.java))
            }
        }

    }*/

    override fun onClick(p0: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}