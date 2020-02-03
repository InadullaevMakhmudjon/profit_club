package com.example.profitclub.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.profitclub.R
import com.example.profitclub.databinding.MessageFileItemBinding
import com.example.profitclub.model.File

class MessageFileListAdapter(context: Context, items: List<File>, listener: View.OnClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), View.OnClickListener {
    private val inflater: LayoutInflater
    private val TYPE_FEED = 1
    private var context: Context
    private var items: List<File> = ArrayList()
    private var listener: View.OnClickListener

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
        val binding = MessageFileItemBinding.inflate(inflater, parent, false)
        return EventFeedHolder(binding.root)
//        }
//        return null
    }
    
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        if (holder is EventFeedHolder) {
            val binding = holder.binding
            binding!!.files = item
            val role = item.role
            if (role == 2){
                binding.container.setBackgroundResource(R.drawable.message_text_border2)
                binding.messageText.setTextColor(Color.BLACK)
                binding.time.setTextColor(Color.BLACK)
                binding.icon.setImageResource(R.drawable.ic_insert_drive_file_black_24dp)
            } else {
                binding.container.setBackgroundResource(R.drawable.message_text_border)
                binding.messageText.setTextColor(Color.WHITE)
                binding.time.setTextColor(Color.WHITE)
                binding.icon.setImageResource(R.drawable.ic_insert_drive_file_white_24dp)
            }
            binding.messageText.text = item.name
            binding.time.text = item.time

            /* Picasso.get()
                     .load(item.phost_photo)
                     .into(binding.imageActual)*/

            binding.container.tag = item
            binding.container.setOnClickListener(this)

        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    private inner class EventFeedHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: MessageFileItemBinding? = null

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
              // context.startActivity(Intent(context, ChatViewActivity::class.java))
            }
        }
    }
}