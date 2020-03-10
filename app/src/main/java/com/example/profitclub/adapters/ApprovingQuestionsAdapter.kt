package com.example.profitclub.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.profitclub.R
import com.example.profitclub.databinding.ApprovingQuestionItemBinding
import com.example.profitclub.model.Questions
import com.example.profitclub.ui.questions.open.QuestionDetailActivity


class ApprovingQuestionsAdapter(context: Context, items: List<Questions>, listener: View.OnClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), View.OnClickListener {
    private val inflater: LayoutInflater
    private val TYPE_FEED = 1
    private var context: Context = context
    private var items: List<Questions> = ArrayList()
    private var listener: View.OnClickListener

    init {
        this.items =  items
        this.listener = listener
        this.inflater = LayoutInflater.from(context)
    }


    override fun getItemViewType(position: Int): Int {
        return TYPE_FEED
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        if (viewType == TYPE_FEED) {
        val binding = ApprovingQuestionItemBinding.inflate(inflater, parent, false)
        return EventFeedHolder(binding.root)
//        }
//        return null

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        if (holder is EventFeedHolder) {
            val binding = holder.binding
            if (binding != null) {
                binding.question = item
            }
            if (binding != null) {
                binding.questionText.text = item.question
            }
            if (binding != null) {
                binding.bidText.text = item.num_bids.toString()
            }
            if (binding != null) {
                binding.sawText.text = item.num_saw.toString()
            }

            /* Picasso.get()
                     .load(item.phost_photo)
                     .into(binding.imageActual)*/

            binding?.container?.tag = item
            binding?.container?.setOnClickListener(this)

        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    private inner class EventFeedHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: ApprovingQuestionItemBinding? = null

        init {
            binding = DataBindingUtil.bind(itemView)
        }
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.container -> {
              //  val item = p0?.getTag() as Questions
                val intent: Intent = Intent(context, QuestionDetailActivity::class.java)
                intent.putExtra("role", 4)
                context.startActivity(intent)
            }
        }
    }
}