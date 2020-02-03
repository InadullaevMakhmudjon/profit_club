package com.example.profitclub.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.profitclub.R
import com.example.profitclub.databinding.ApproveItemBinding
import com.example.profitclub.databinding.ChatItemBinding
import com.example.profitclub.model.Approve
import com.example.profitclub.model.ChatQuestion
import com.example.profitclub.ui.chats.ChatViewActivity

class ApproveListAdapter(context: Context, items: List<Approve>, listener: View.OnClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), View.OnClickListener {
    private val inflater: LayoutInflater
    private val TYPE_FEED = 1
    private lateinit var context: Context
    private var items: List<Approve> = ArrayList()
    private lateinit var listener: View.OnClickListener

    init {
        this.context = context
        this.items =  items
        this.listener = listener
        this.inflater = LayoutInflater.from(context)
    }


    override fun getItemViewType(position: Int): Int {
        return TYPE_FEED
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        if (viewType == TYPE_FEED) {
        val binding = ApproveItemBinding.inflate(inflater, parent, false)
        return EventFeedHolder(binding.root)
//        }
//        return null

    }
    
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        if (holder is EventFeedHolder) {
            val binding = holder.binding
            binding!!.approve = item

            when {
                item.status == "Approved" -> binding.status.setBackgroundResource(R.drawable.bc_status_open)
                item.status == "Not Approved" -> binding.status.setBackgroundResource(R.drawable.bc_status_rejected)
            }

            /* Picasso.get()
                     .load(item.phost_photo)
                     .into(binding.imageActual)*/

            binding?.container.tag = item
            binding?.container.setOnClickListener(this)

        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    private inner class EventFeedHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: ApproveItemBinding? = null

        init {
            binding = DataBindingUtil.bind(itemView)
        }
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.container -> {
              //  val item = p0?.getTag() as Questions
               // val intent: Intent = Intent(context, QuestionDetailActivity::class.java)
                //intent.putExtra(Const.EVENT_EXTRA, item)
               context.startActivity(Intent(context, ChatViewActivity::class.java))
            }
        }
    }
}