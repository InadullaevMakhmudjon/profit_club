package com.example.profitclub.ui.manager.approve

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.profitclub.adapters.ApproveListAdapter
import com.example.profitclub.databinding.FragmentManagerApproveBinding
import com.example.profitclub.model.Approve

class ApproveManagerFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentManagerApproveBinding
    private lateinit var galleryViewModel: ApproveManagerViewModel
    private var layoutManager: LinearLayoutManager? = null
    private var adapter: ApproveListAdapter? = null

        val list = listOf(Approve("Name Surname Patronymic",
            "Accounting", "19784221", "Approved"),
            Approve("Name Surname Patronymic",
                "Business Management", "19784221", "Not Approved"),
            Approve("Name Surname Patronymic",
                "IT & Software", "19784221", "Not Approved"),
            Approve("Name Surname Patronymic",
                "Law", "19784221", "Approved"),
            Approve("Name Surname Patronymic",
                "Logistics", "19784221", "Approved"))

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       // (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        galleryViewModel =
            ViewModelProviders.of(this).get(ApproveManagerViewModel::class.java)
       // val root = inflater.inflate(R.layout.fragment_questions, container, false)
        binding = FragmentManagerApproveBinding.inflate(layoutInflater)
        adapter = ApproveListAdapter(this.context!!, list, this)
        layoutManager = LinearLayoutManager(this.context!!, LinearLayoutManager.VERTICAL, false)
        binding.recyclerApprovingBids.layoutManager = layoutManager
        binding.recyclerApprovingBids.adapter = adapter
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