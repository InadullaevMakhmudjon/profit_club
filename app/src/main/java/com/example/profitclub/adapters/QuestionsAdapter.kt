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
import com.example.profitclub.data.bids.ConsultantBidsData
import com.example.profitclub.databinding.QuestionItemBinding
import com.example.profitclub.ui.questions.open.QuestionDetailActivity

class QuestionsAdapter(private val context: Context, private val items: ArrayList<ConsultantBidsData>?, private val listener: View.OnClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), View.OnClickListener {
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = QuestionItemBinding.inflate(inflater, parent, false)
        return EventFeedHolder(binding.root)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items?.get(position)
        if (holder is EventFeedHolder) {
            val binding = holder.binding
            binding!!.question = item

            val title = context.getString(R.string.title_)
            val description = context.getString(R.string.description_)
            val language = context.getString(R.string.language_)
            val categories = context.getString(R.string.categories)
            val deadline = context.getString(R.string.deadline__)

            binding.questionTitle.text = "$title ${item?.title}"
            binding.questionDescription.text = "$description ${item?.description}"
            binding.questionLanguage.text = "$language ${item?.qlang}"
            binding.questionCategory.text = "$categories ${item?.categories?.reduce { acc, s -> "$acc/$s" }}"
            binding.questionDeadline.text = "$deadline ${item?.question_date}"

            binding.container.tag = item
            binding.container.setOnClickListener(this)
        }
    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    private inner class EventFeedHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: QuestionItemBinding? = null

        init {
            binding = DataBindingUtil.bind(itemView)
        }
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.container -> {
                val item = p0.tag as ConsultantBidsData
                val intent = Intent(context, QuestionDetailActivity::class.java)
                intent.putExtra("item_open", item)
                context.startActivity(intent)
            }
        }
    }
}