package com.example.profitclub.ui.chats

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.profitclub.MainActivity
import com.example.profitclub.R
import com.example.profitclub.adapters.ChatListAdapter
import com.example.profitclub.databinding.FragmentChatsBinding
import com.example.profitclub.model.ChatQuestion
import com.example.profitclub.toast
import kotlinx.android.synthetic.main.fragment_questions.*

class ChatsFragment : Fragment(), View.OnClickListener {

    private lateinit var homeViewModel: ChatsViewModel
    private lateinit var binding: FragmentChatsBinding
    private val APP_PREFERENCE = "MYSETTINGS"

    private var layoutManager: LinearLayoutManager? = null
    private var adapter: ChatListAdapter? = null

    val list = listOf(ChatQuestion("Hi. I am looking for a consultant for my mobile app to modify and improve UI design and development of a CMS Portal", "22967117", "Completed"),
        ChatQuestion("I am looking for app (android and IOS) expert.", "6542413", "Rejected"),
        ChatQuestion("Hi. I am looking for a consultant for my mobile app to modify and improve UI design and development of a CMS Portal", "22967117", "Progress"),
        ChatQuestion("I am looking for app (android and IOS) expert.", "6542413", "Completed"),
        ChatQuestion("Hi. I am looking for a consultant for my mobile app to modify and improve UI design and development of a CMS Portal", "22967117", "Open"),
        ChatQuestion("I am looking for app (android and IOS) expert.", "6542413","Arbitration"),
        ChatQuestion("Hi. I am looking for a consultant for my mobile app to modify and improve UI design and development of a CMS Portal", "22967117", "Rejected"),
        ChatQuestion("I am looking for app (android and IOS) expert.", "6542413", "Progress"),
        ChatQuestion("Hi. I am looking for a consultant for my mobile app to modify and improve UI design and development of a CMS Portal", "22967117", "Arbitration"),
        ChatQuestion("I am looking for app (android and IOS) expert.", "6542413", "Open")
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        val activity = activity as MainActivity?
        activity.let {
            activity?.customActionBarTitle("chats")
        }

        activity?.let {activity ->
            val preferences = activity.getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE)

            homeViewModel =
                ViewModelProviders.of(this, ChatsViewModelFactory(preferences)).get(ChatsViewModel::class.java)
            binding = FragmentChatsBinding.inflate(layoutInflater)

            adapter = ChatListAdapter(this.context!!, list, this)
            layoutManager = LinearLayoutManager(this.context!!, LinearLayoutManager.VERTICAL, false)
            binding.chatList.layoutManager = layoutManager
            binding.chatList.adapter = adapter
            adapter!!.notifyDataSetChanged()

            homeViewModel.text.observe(viewLifecycleOwner, Observer {
                binding.textHome.text = it
            })

            homeViewModel.data.observe(viewLifecycleOwner, Observer { data ->
                if(data != null) {
                    toast("come data")
                }
            })

            homeViewModel.error.observe(viewLifecycleOwner, Observer { message ->
                toast("Error: $message")
            })
        }

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
        when (item!!.itemId) {
            R.id.language -> {
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
        return false
    }
}