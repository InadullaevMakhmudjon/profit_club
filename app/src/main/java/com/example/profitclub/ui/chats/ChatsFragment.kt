package com.example.profitclub.ui.chats

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.profitclub.MainActivity
import com.example.profitclub.R
import com.example.profitclub.adapters.ChatListAdapter
import com.example.profitclub.databinding.FragmentChatsBinding
import com.example.profitclub.toast

class ChatsFragment : Fragment(), View.OnClickListener {

    private lateinit var viewmodel: ChatsViewModel
    private lateinit var binding: FragmentChatsBinding
    private val APP_PREFERENCE = "MYSETTINGS"
    private var layoutManager: LinearLayoutManager? = null
    private var adapter: ChatListAdapter? = null

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

        var clicked: (() -> Unit)

        activity?.let {activity ->

            val preferences = activity.getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE)
            viewmodel =
                ViewModelProviders.of(this, ChatsViewModelFactory(preferences)).get(ChatsViewModel::class.java)
            binding = FragmentChatsBinding.inflate(layoutInflater)
            adapter = ChatListAdapter(this.context!!, null, this)
            layoutManager = LinearLayoutManager(this.context!!, LinearLayoutManager.VERTICAL, false)
            binding.chatList.layoutManager = layoutManager
            binding.chatList.adapter = adapter
            adapter!!.notifyDataSetChanged()

            if (activityMain?.getMyData() == 2 || activityMain?.getMyData() == 4){

                viewmodel.data.observe(viewLifecycleOwner, Observer { data ->

                    if(data != null) {
                        //toast("come data")
                        adapter = ChatListAdapter(this.context!!, data.data, this)
                        binding.chatList.adapter = adapter
                    }
                })

                viewmodel.error.observe(viewLifecycleOwner, Observer { message ->
                    toast("Error: $message")
                })

            } else {
                viewmodel.dataClient.observe(viewLifecycleOwner, Observer { data ->

                    if(data != null) {
                        //toast("come dataClient")
                        adapter = ChatListAdapter(this.context!!, data.data, this)
                        binding.chatList.adapter = adapter
                    }
                })

                viewmodel.error.observe(viewLifecycleOwner, Observer { message ->
                    toast("Error: $message")
                })
            }
        }

        return binding.root
    }

  /*  override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }*/
    
    override fun onClick(p0: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /*override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
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
    }*/
}