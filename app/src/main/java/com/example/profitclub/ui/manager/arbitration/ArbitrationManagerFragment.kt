package com.example.profitclub.ui.manager.arbitration

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.profitclub.R
import com.example.profitclub.adapters.ArbitrationManagerListAdapter
import com.example.profitclub.databinding.FragmentManagerArbitrationBinding
import com.example.profitclub.model.Chat_M

class ArbitrationManagerFragment : Fragment(), View.OnClickListener {

    private lateinit var homeViewModel: ArbitrationManagerViewModel
    private lateinit var binding: FragmentManagerArbitrationBinding

    private var layoutManager: LinearLayoutManager? = null
    private var adapter: ArbitrationManagerListAdapter? = null

    val list = listOf(
        Chat_M("I need en expert who will help me putting my website into a domain",
            "22446952", "19784221", "66449978", "Approved"),
        Chat_M("I need en expert who will help me putting my website into a domain",
            "22446952", "19784221", "66449978", "Not Approved"),
        Chat_M("I need en expert who will help me putting my website into a domain",
            "22446952", "19784221", "66449978", "Not Approved"),
        Chat_M("I need en expert who will help me putting my website into a domain",
            "22446952", "19784221", "66449978", "Approved"),
        Chat_M("I need en expert who will help me putting my website into a domain",
            "22446952", "19784221", "66449978", "Approved")
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //(activity as AppCompatActivity).setSupportActionBar(toolbar)

        homeViewModel =
            ViewModelProviders.of(this).get(ArbitrationManagerViewModel::class.java)
        binding = FragmentManagerArbitrationBinding.inflate(layoutInflater)

        adapter = ArbitrationManagerListAdapter(this.context!!, list, this)
        layoutManager = LinearLayoutManager(this.context!!, LinearLayoutManager.VERTICAL, false)
        binding.chatList.layoutManager = layoutManager
        binding.chatList.adapter = adapter
        adapter!!.notifyDataSetChanged()

        homeViewModel.text.observe(this, Observer {
            binding.textHome.text = it
        })

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    
    override fun onClick(p0: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.language_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.language -> {

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}