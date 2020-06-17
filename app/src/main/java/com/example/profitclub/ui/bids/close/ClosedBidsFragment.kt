package com.example.profitclub.ui.bids.close

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.profitclub.adapters.ClosedBidAdapter
import com.example.profitclub.databinding.FragmentClosedBidsBinding
import com.example.profitclub.toast

class ClosedBidsFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentClosedBidsBinding
    private lateinit var vm: ClosedBidsViewModel
    private var layoutManager: LinearLayoutManager? = null
    private var adapter: ClosedBidAdapter? = null
    private val APP_PREFERENCE = "MYSETTINGS"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentClosedBidsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let { activity ->
            val preferences = activity.getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE)
            vm =
                ViewModelProviders.of(this, BidsCloseViewModelFactory(preferences)).get(ClosedBidsViewModel::class.java)
            adapter = ClosedBidAdapter(this.context!!, null, this)
            layoutManager = LinearLayoutManager(this.context!!, LinearLayoutManager.VERTICAL, false)
            binding.recyclerClosedBids.layoutManager = layoutManager
            binding.recyclerClosedBids.adapter = adapter
            adapter?.notifyDataSetChanged()

            vm.data.observe(viewLifecycleOwner, Observer { data ->
                if (data != null){
                    adapter = ClosedBidAdapter(this.context!!, data.data, this)
                    binding.recyclerClosedBids.adapter = adapter
                }
            })

            vm.error.observe(viewLifecycleOwner, Observer { message ->
                toast("$message")
            })
        }
    }

    override fun onResume() {
        super.onResume()
        adapter?.notifyDataSetChanged()
    }

    override fun onClick(p0: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}