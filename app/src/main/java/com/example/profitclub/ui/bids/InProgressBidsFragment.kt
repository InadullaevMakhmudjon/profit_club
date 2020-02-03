package com.example.profitclub.ui.bids

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.profitclub.adapters.InProgressBidAdapter
import com.example.profitclub.adapters.OpenBidAdapter
import com.example.profitclub.databinding.FragmentInprogressBidsBinding
import com.example.profitclub.databinding.FragmentOpenBidsBinding
import com.example.profitclub.model.Bid

class InProgressBidsFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentInprogressBidsBinding
    private lateinit var galleryViewModel: InProgressBidsViewModel
    private var layoutManager: LinearLayoutManager? = null
    private var adapter: InProgressBidAdapter? = null

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

        galleryViewModel =
            ViewModelProviders.of(this).get(InProgressBidsViewModel::class.java)
       // val root = inflater.inflate(R.layout.fragment_questions, container, false)
        binding = FragmentInprogressBidsBinding.inflate(layoutInflater)
        adapter = InProgressBidAdapter(this.context!!, list, this)
        layoutManager = LinearLayoutManager(this.context!!, LinearLayoutManager.VERTICAL, false)
        binding.recyclerInProgressBids.layoutManager = layoutManager
        binding.recyclerInProgressBids.adapter = adapter
        adapter?.notifyDataSetChanged()
        /*val textView: TextView = root.findViewById(R.id.text_gallery)
        galleryViewModel.text.observe(this, Observer {
            textView.text = it
        })*/

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