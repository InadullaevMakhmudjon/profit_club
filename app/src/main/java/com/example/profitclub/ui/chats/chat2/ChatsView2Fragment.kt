package com.example.profitclub.ui.chats.chat2

import android.os.Bundle
import android.view.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.profitclub.adapters.MessageFileListAdapter
import com.example.profitclub.databinding.FragmentChatsView2Binding
import com.example.profitclub.model.File
import com.example.profitclub.ui.chats.ChatViewActivity

class ChatsView2Fragment : Fragment(), View.OnClickListener {

    private lateinit var homeViewModel: ChatsView2Model
    private lateinit var binding: FragmentChatsView2Binding
    private var adapter: MessageFileListAdapter? = null
    private var layoutManager: LinearLayoutManager? = null

    val list = listOf(
       File("ProfitClub.doc", "13:30", 1), File("ProfitClub.doc", "13:30", 2),
        File("ProfitClub.doc", "13:30", 1), File("ProfitClub.doc", "13:30", 2),
        File("ProfitClub.doc", "13:30", 2), File("ProfitClub.doc", "13:30", 1),
        File("ProfitClub.doc", "13:30", 1), File("ProfitClub.doc", "13:30", 2)
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        homeViewModel =
            ViewModelProviders.of(this).get(ChatsView2Model::class.java)
        binding = FragmentChatsView2Binding.inflate(layoutInflater)

        adapter = MessageFileListAdapter(this.context!!, list, this)
        layoutManager = LinearLayoutManager(this.context!!, LinearLayoutManager.VERTICAL, false)
        binding.messagesList.layoutManager = layoutManager
        binding.messagesList.adapter = adapter
        adapter?.notifyDataSetChanged()

        val activity = activity as ChatViewActivity?
        val myDataFromActivity = activity!!.getMyDataChat()

        if(myDataFromActivity == 2 || myDataFromActivity == 3){
            binding.attach.isVisible = false
        }

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    
    override fun onClick(p0: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}