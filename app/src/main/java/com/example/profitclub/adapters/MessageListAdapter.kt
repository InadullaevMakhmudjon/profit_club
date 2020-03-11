package com.example.profitclub.adapters


import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.profitclub.R
import com.example.profitclub.data.BASE_URL
import com.example.profitclub.data.questions.Message
import com.example.profitclub.databinding.MessageItemBinding
import com.squareup.picasso.Picasso


class MessageListAdapter(
    context: Context,
    private val items: List<Message>,
    private val userId: Int
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), View.OnClickListener {
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = MessageItemBinding.inflate(inflater, parent, false)
        return EventFeedHolder(binding.root)
    }
    
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        if (holder is EventFeedHolder) {
            val binding = holder.binding
            binding!!.messages = item
            if (item.idother != userId){
                binding!!.container.setBackgroundResource(R.drawable.message_text_border2)
                binding.messageText.setTextColor(Color.BLACK)
                binding.time.setTextColor(Color.BLACK)
            } else {
                binding!!.container.setBackgroundResource(R.drawable.message_text_border)
                binding.messageText.setTextColor(Color.WHITE)
            }
            binding.messageText.text = item.content
            binding.time.text = item.date
            Picasso.get()
                .load("$BASE_URL${item.avatar}")
                .into(binding.profileImage)

            binding.container.tag = item
            binding.container.setOnClickListener(this)

        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    private inner class EventFeedHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: MessageItemBinding? = null

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