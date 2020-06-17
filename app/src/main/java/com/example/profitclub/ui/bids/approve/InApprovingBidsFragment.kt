package com.example.profitclub.ui.bids.approve

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.profitclub.adapters.ApprovingBidAdapter
import com.example.profitclub.databinding.FragmentInApprovingBidsBinding
import com.example.profitclub.toast

class InApprovingBidsFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentInApprovingBidsBinding
    private lateinit var vm: InApprovingBidsViewModel
    private var layoutManager: LinearLayoutManager? = null
    private var adapter: ApprovingBidAdapter? = null
    private val APP_PREFERENCE = "MYSETTINGS"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInApprovingBidsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let { activity ->
            val preferences = activity.getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE)
            vm =
                ViewModelProviders.of(this, BidsApproveViewModelFactory(preferences)).get(InApprovingBidsViewModel::class.java)
            adapter = ApprovingBidAdapter(this.context!!, null, this)
            layoutManager = LinearLayoutManager(this.context!!, LinearLayoutManager.VERTICAL, false)
            binding.recyclerApprovingBids.layoutManager = layoutManager
            binding.recyclerApprovingBids.adapter = adapter
            adapter?.notifyDataSetChanged()

            vm.data.observe(viewLifecycleOwner, Observer { data ->
                if (data != null){
                    adapter = ApprovingBidAdapter(this.context!!, data.data, this)
                    binding.recyclerApprovingBids.adapter = adapter
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