package com.example.profitclub.ui.bids

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.profitclub.adapters.ChatListAdapter
import com.example.profitclub.adapters.OpenBidAdapter
import com.example.profitclub.databinding.FragmentOpenBidsBinding
import com.example.profitclub.model.Bid
import com.example.profitclub.toast

class OpenBidsFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentOpenBidsBinding
    private lateinit var viewModel: OpenBidsViewModel
    private var layoutManager: LinearLayoutManager? = null
    private var adapter: OpenBidAdapter? = null
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
       // (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        activity?.let {
            val preferences = activity!!.getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE)

            viewModel =
                ViewModelProviders.of(this, BidsViewModelFactory(preferences)).get(OpenBidsViewModel::class.java)
            // val root = inflater.inflate(R.layout.fragment_questions, container, false)
            binding = FragmentOpenBidsBinding.inflate(layoutInflater)
            adapter = OpenBidAdapter(this.context!!, null, this)
            layoutManager = LinearLayoutManager(this.context!!, LinearLayoutManager.VERTICAL, false)
            binding.recyclerOpenBids.layoutManager = layoutManager
            binding.recyclerOpenBids.adapter = adapter
            adapter?.notifyDataSetChanged()

            viewModel.data.observe(viewLifecycleOwner, Observer { data ->

                if(data != null) {
                    toast("come data")
                    adapter = OpenBidAdapter(this.context!!, data.data, this)
                    binding.recyclerOpenBids.adapter = adapter
                }
            })

            viewModel.error.observe(viewLifecycleOwner, Observer { message ->
                toast("Error: $message")
            })
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onResume() {
        super.onResume()
        adapter?.notifyDataSetChanged()
    }

    override fun onClick(p0: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}