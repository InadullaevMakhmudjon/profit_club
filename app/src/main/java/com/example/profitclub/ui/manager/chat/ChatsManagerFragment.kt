package com.example.profitclub.ui.manager.chat

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.profitclub.R
import com.example.profitclub.adapters.ChatManagerListAdapter
import com.example.profitclub.databinding.FragmentManagerChatsBinding
import com.example.profitclub.model.Chat_M
import kotlinx.android.synthetic.main.fragment_questions.toolbar

class ChatsManagerFragment : Fragment(), View.OnClickListener {

    private lateinit var homeViewModel: ChatsManagerViewModel
    private lateinit var binding: FragmentManagerChatsBinding

    private var layoutManager: LinearLayoutManager? = null
    private var adapter: ChatManagerListAdapter? = null

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
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        homeViewModel =
            ViewModelProviders.of(this).get(ChatsManagerViewModel::class.java)
        binding = FragmentManagerChatsBinding.inflate(layoutInflater)

        adapter = ChatManagerListAdapter(this.context!!, list, this)
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
        setHasOptionsMenu(true)
    }
    
    override fun onClick(p0: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater!!.inflate(R.menu.language_menu, menu)
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