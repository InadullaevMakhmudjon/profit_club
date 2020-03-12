package com.example.profitclub.ui.bids.dispute

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.profitclub.adapters.InArbitrationBidAdapter
import com.example.profitclub.databinding.FragmentInarbitrationBidsBinding
import com.example.profitclub.model.Bid

class InArbitrationBidsFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentInarbitrationBidsBinding
    private lateinit var vm: InArbitrationBidsViewModel
    private var layoutManager: LinearLayoutManager? = null
    private var adapter: InArbitrationBidAdapter? = null
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
        binding = FragmentInarbitrationBidsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.let {
            val preferences = activity!!.getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE)
            vm = ViewModelProviders.of(this,
                BidsArbitrationViewModelFactory(
                    preferences
                )
            ).get(InArbitrationBidsViewModel::class.java)
            adapter = InArbitrationBidAdapter(this.context!!, null, this)
            layoutManager = LinearLayoutManager(this.context!!, LinearLayoutManager.VERTICAL, false)
            binding.recyclerInArbitrationBids.layoutManager = layoutManager
            binding.recyclerInArbitrationBids.adapter = adapter
            adapter?.notifyDataSetChanged()

            vm.data.observe(viewLifecycleOwner, Observer { data ->
                if (data != null){
                    adapter = InArbitrationBidAdapter(this.context!!, data.data, this)
                    binding.recyclerInArbitrationBids.adapter = adapter
                }
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