package com.example.profitclub.ui.chats.chat1

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.profitclub.R
import com.example.profitclub.adapters.MessageListAdapter
import com.example.profitclub.data.Service
import com.example.profitclub.data.questions.Message
import com.example.profitclub.data.questions.MessageListener
import com.example.profitclub.databinding.FragmentChatsView1Binding
import com.example.profitclub.toast
import com.example.profitclub.ui.chats.ChatViewActivity
import com.google.gson.Gson

class ChatsView1Fragment : Fragment(), View.OnClickListener {

    private lateinit var homeViewModel: ChatsView1Model
    private lateinit var binding: FragmentChatsView1Binding
    private var adapter: MessageListAdapter? = null
    private var layoutManager: LinearLayoutManager? = null
    private val socket = Service.socket
    private var questionId: Int = 0
    private var clientId: Int = 0
    private var consultantId: Int = 0
    private val APP_PREFERENCE = "MYSETTINGS"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val preferences = activity!!.getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE)
        var allMessages:ArrayList<Message> = arrayListOf()

        homeViewModel =
            ViewModelProviders.of(this, ChatsView1ModelFactory(activity!!.application, preferences)).get(ChatsView1Model::class.java)
        binding = FragmentChatsView1Binding.inflate(layoutInflater)
        binding.viewModel = homeViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        val mpIn = MediaPlayer.create(this.activity, R.raw.sound_in)
        val mpOut = MediaPlayer.create(this.activity, R.raw.sound_out)
        // adapter = MessageListAdapter(this.context!!, list, )
        layoutManager = LinearLayoutManager(this.context!!, LinearLayoutManager.VERTICAL, false)
        binding.messagesList.layoutManager = layoutManager

        val activity = activity as ChatViewActivity?
        val myDataFromActivity = activity!!.getMyDataChat()
        adapter = MessageListAdapter(this.context!!,allMessages, homeViewModel.userId)
        binding.messagesList.adapter = adapter

        homeViewModel.questionId.apply { value = activity.getMyQusetionId() }
        homeViewModel.receiverId.apply { value = activity.getOtherId }
        questionId = activity.getMyQusetionId()

        clientId = activity.getClientConsultantId().first
        consultantId = activity.getClientConsultantId().second

        if(myDataFromActivity == 2 || myDataFromActivity == 3){
            binding.linearLayout.isVisible = false
        }

        activity.getSocket.on("update_chat_${questionId}") { arg ->
            activity.runOnUiThread {
                val message = Gson().fromJson(arg[0].toString(), MessageListener::class.java)
                allMessages.add(message.data)
                adapter?.notifyDataSetChanged()
                binding.messagesList.smoothScrollToPosition(allMessages.lastIndex - 1)
                mpIn.start()
            }
        }
        homeViewModel.getData(questionId)

        homeViewModel.messages.observe(viewLifecycleOwner, Observer {messages ->
            if(messages != null) {
                allMessages.addAll(messages)
                adapter?.notifyDataSetChanged()
                binding.messagesList.smoothScrollToPosition(allMessages.lastIndex - 1)
            }
        })

        homeViewModel.error.observe(viewLifecycleOwner, Observer {message ->
            toast(message)
        })
        return binding.root
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        socket?.connect()
    }

    override fun onDestroy() {
        super.onDestroy()
        socket?.disconnect()
        socket?.off("update_chat_$questionId")
    }

    override fun onClick(p0: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}