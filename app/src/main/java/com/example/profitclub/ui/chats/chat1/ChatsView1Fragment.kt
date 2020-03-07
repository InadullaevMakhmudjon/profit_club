package com.example.profitclub.ui.chats.chat1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.profitclub.adapters.MessageListAdapter
import com.example.profitclub.data.Service
import com.example.profitclub.databinding.FragmentChatsView1Binding
import com.example.profitclub.model.Messages
import com.example.profitclub.toast
import com.example.profitclub.ui.chats.ChatViewActivity
import com.github.nkzawa.emitter.Emitter

class ChatsView1Fragment : Fragment(), View.OnClickListener {

    private lateinit var homeViewModel: ChatsView1Model
    private lateinit var binding: FragmentChatsView1Binding
    private var adapter: MessageListAdapter? = null
    private var layoutManager: LinearLayoutManager? = null
    private val socket = Service.socket

    val list = listOf(
        Messages("Hello there. How Can I help you?", "14:51", 2),
        Messages("I need your help in order to make my website attractive", "14:59", 1),
        Messages("Oh! I have 10 years experience in this field", "15:03", 2),
        Messages("I wish you make your best!", "15:11", 1),
        Messages("I give you feedback as soon as possible", "15:21", 2),
        Messages("Okay", "16:40", 1)
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(ChatsView1Model::class.java)
        binding = FragmentChatsView1Binding.inflate(layoutInflater)

        adapter = MessageListAdapter(this.context!!, list, this)
        layoutManager = LinearLayoutManager(this.context!!, LinearLayoutManager.VERTICAL, false)
        binding.messagesList.layoutManager = layoutManager
        binding.messagesList.adapter = adapter
        adapter?.notifyDataSetChanged()

        val activity = activity as ChatViewActivity?
        val myDataFromActivity = activity!!.getMyDataChat()

        val questionId = activity.getMyQusetionId()

        toast("question_id: $questionId")

        if(myDataFromActivity == 2 || myDataFromActivity == 3){
            binding.linearLayout.isVisible = false
        }

        return binding.root
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        socket?.on("", Emitter.Listener { args ->
            Toast.makeText(activity, args[0].toString(), Toast.LENGTH_LONG).show()
        })
        socket?.connect()
    }

    override fun onDestroy() {
        super.onDestroy()
        socket?.disconnect()
        socket?.off("")
    }

    override fun onClick(p0: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}