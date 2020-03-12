package com.example.profitclub.ui.bids.cancel

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.profitclub.adapters.RejectedBidAdapter
import com.example.profitclub.databinding.FragmentRejectedBidsBinding
import com.example.profitclub.model.Bid
import com.example.profitclub.toast

class RejectedBidsFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentRejectedBidsBinding
    private lateinit var vm: RejectedBidsViewModel
    private var layoutManager: LinearLayoutManager? = null
    private var adapter: RejectedBidAdapter? = null
    private val APP_PREFERENCE = "MYSETTINGS"

        val list = listOf(Bid("Lorem ipsum possum dolor Lorem ipsum possum dolor  vLorem ipsum possum dolor Lorem ipsum possum dolor Lorem ipsum possum dolor Lorem ipsum possum dolor  vLorem ipsum possum dolor"),
            Bid("Lorem ipsum possum dolor Lorem ipsum possum dolor  vLorem ipsum possum dolor Lorem ipsum possum dolor Lorem ipsum possum dolor Lorem ipsum possum dolor  vLorem ipsum possum dolor"),
            Bid("Lorem ipsum possum dolor Lorem ipsum possum dolor  vLorem ipsum possum dolor Lorem ipsum possum dolor Lorem ipsum possum dolor Lorem ipsum possum dolor  vLorem ipsum possum dolor"),
            Bid("Lorem ipsum possum dolor Lorem ipsum possum dolor  vLorem ipsum possum dolor Lorem ipsum possum dolor Lorem ipsum possum dolor Lorem ipsum possum dolor  vLorem ipsum possum dolor"),
            Bid("Lorem ipsum possum dolor Lorem ipsum possum dolor  vLorem ipsum possum dolor Lorem ipsum possum dolor Lorem ipsum possum dolor Lorem ipsum possum dolor  vLorem ipsum possum dolor"),
            Bid("Lorem ipsum possum dolor Lorem ipsum possum dolor  vLorem ipsum possum dolor Lorem ipsum possum dolor Lorem ipsum possum dolor Lorem ipsum possum dolor  vLorem ipsum possum dolor"))

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRejectedBidsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.let {activity ->

            val preferences = activity.getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE)
            vm =
                ViewModelProviders.of(this, BidsRejectViewModelFactory(preferences)).get(RejectedBidsViewModel::class.java)
            adapter = RejectedBidAdapter(this.context!!, null, this)
            layoutManager = LinearLayoutManager(this.context!!, LinearLayoutManager.VERTICAL, false)
            binding.recyclerRejectedBids.layoutManager = layoutManager
            binding.recyclerRejectedBids.adapter = adapter
            adapter?.notifyDataSetChanged()
        }

        vm.data.observe(viewLifecycleOwner, Observer { data ->
            if (data != null){
                adapter = RejectedBidAdapter(this.context!!, data.data, this)
                binding.recyclerRejectedBids.adapter = adapter
            }
        })

        vm.error.observe(viewLifecycleOwner, Observer { message ->
            toast("$message")
        })
    }

    override fun onResume() {
        super.onResume()
        adapter?.notifyDataSetChanged()
    }

    override fun onClick(p0: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}