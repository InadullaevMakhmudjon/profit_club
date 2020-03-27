package com.example.profitclub.ui.bids.open

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.profitclub.App
import com.example.profitclub.adapters.OpenBidAdapter
import com.example.profitclub.data.bids.ConsultantBidsClickData
import com.example.profitclub.data.bids.ConsultantBidsData
import com.example.profitclub.databinding.FragmentOpenBidsBinding
import com.example.profitclub.toast

class OpenBidsFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentOpenBidsBinding
    private lateinit var viewModel: OpenBidsViewModel
    private var layoutManager: LinearLayoutManager? = null
    private var adapter: OpenBidAdapter? = null
    private val APP_PREFERENCE = "MYSETTINGS"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       // (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        activity?.let {activity ->
            val preferences = activity.getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE)

            viewModel =
                ViewModelProviders.of(this,
                    BidsViewModelFactory(
                        preferences
                    )
                ).get(OpenBidsViewModel::class.java)

            val dataAdapter = ArrayList<ConsultantBidsClickData>()
            // val root = inflater.inflate(R.layout.fragment_questions, container, false)
            binding = FragmentOpenBidsBinding.inflate(layoutInflater)
            adapter = OpenBidAdapter(this.context!!, dataAdapter, this)
            layoutManager = LinearLayoutManager(this.context!!, LinearLayoutManager.VERTICAL, false)
            binding.recyclerOpenBids.layoutManager = layoutManager
            binding.recyclerOpenBids.adapter = adapter


            viewModel.data.observe(viewLifecycleOwner, Observer { data ->
                if(data != null) {
                    //toast("come data")
                    dataAdapter.clear()
                    dataAdapter.addAll(data.data)
                    adapter?.notifyDataSetChanged()
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