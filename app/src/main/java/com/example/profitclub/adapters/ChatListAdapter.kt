package com.example.profitclub.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.profitclub.R
import com.example.profitclub.data.questions.QuestionConsultantData
import com.example.profitclub.databinding.ChatItemBinding
import com.example.profitclub.ui.bids.BidDetailActivity

class ChatListAdapter(private val context: Context, private val items: ArrayList<QuestionConsultantData>?, private val listener: View.OnClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), View.OnClickListener {
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ChatItemBinding.inflate(inflater, parent, false)
        return EventFeedHolder(binding.root)
    }
    
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items?.get(position)
        if (holder is EventFeedHolder) {
            val binding = holder.binding
            binding!!.chatQuestion = item
            val title = context.getString(R.string.title_)
            val language = context.getString(R.string.language_)
            val categories = context.getString(R.string.categories)
            val deadline = context.getString(R.string.deadline__)
            val timeLeft = context.getString(R.string.time_left)
            val day = context.getString(R.string.day)
            val hour = context.getString(R.string.hour)
            binding.questionTitle.text = "$title ${item?.title}"
            binding.questionLanguage.text = "$language ${item?.qlang}"
            binding.questionCategory.text = "$categories ${item?.categories!!.reduce { acc, s -> "$acc/$s" }}"
            binding.questionDeadline.text = "$deadline ${item.question_date}"
            binding.timeLeft.text = "$timeLeft ${item.day} $day ${item.day} $hour"
            binding.container.tag = item
            binding.container.setOnClickListener(this)
        }
    }

    override fun getItemCount(): Int{
        return items?.size ?: 0
    }

    private inner class EventFeedHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: ChatItemBinding? = null

        init {
            binding = DataBindingUtil.bind(itemView)
        }
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.container -> {
                val item = p0.tag as QuestionConsultantData
                val intent: Intent = Intent(context, BidDetailActivity::class.java)
                intent.putExtra("question_id", item.question_id)
                context.startActivity(intent)
            }
        }
    }
}
