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

    private lateinit var viewmodel: ChatsViewModel
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
        //(activity as AppCompatActivity).setSupportActionBar(toolbar)
        val activityMain = activity as MainActivity?
        activity.let {
            activityMain?.customActionBarTitle(getString(R.string.My_Messages))
        }

        activity?.let {activity ->
            val preferences = activity.getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE)

            viewmodel =
                ViewModelProviders.of(this, ChatsViewModelFactory(preferences)).get(ChatsViewModel::class.java)
            binding = FragmentChatsBinding.inflate(layoutInflater)

            if (activityMain?.getMyData() == 2 || activityMain?.getMyData() == 4){
                adapter = ChatListAdapter(this.context!!, null, this)
                layoutManager = LinearLayoutManager(this.context!!, LinearLayoutManager.VERTICAL, false)
                binding.chatList.layoutManager = layoutManager
                binding.chatList.adapter = adapter
                adapter!!.notifyDataSetChanged()

                viewmodel.text.observe(viewLifecycleOwner, Observer {
                    binding.textHome.text = it
                })

                viewmodel.data.observe(viewLifecycleOwner, Observer { data ->

                    if(data != null) {
                        toast("come data")
                        adapter = ChatListAdapter(this.context!!, data.data, this)
                        binding.chatList.adapter = adapter
                    }
                })

                viewmodel.error.observe(viewLifecycleOwner, Observer { message ->
                    toast("Error: $message")
                })
            } else {

            }
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