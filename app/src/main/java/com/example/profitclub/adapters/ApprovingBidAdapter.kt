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
import com.example.profitclub.data.questions.QuestionConsultantApproveData
import com.example.profitclub.databinding.InapprovingBidItemBinding
import com.example.profitclub.ui.browse.BrowseQuestionsActivity

class ApprovingBidAdapter(private val context: Context, private val items: ArrayList<QuestionConsultantApproveData>?, private val listener: View.OnClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), View.OnClickListener {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = InapprovingBidItemBinding.inflate(inflater, parent, false)
        return EventFeedHolder(binding.root)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items?.get(position)
        if (holder is EventFeedHolder) {
            val binding = holder.binding
            binding!!.approve = item
            val title = context.getString(R.string.title_)
            val language = context.getString(R.string.language_)
            val categories = context.getString(R.string.categories)
            val answer = context.getString(R.string.answer_desc)
            val dateClosure = context.getString(R.string.date_closure)
            binding.questionTitle.text = "$title ${item?.title}"
            binding.questionLanguage.text = "$language ${item?.qlang}"
            binding.questionCategory.text = "$categories ${item?.categories!!.reduce { acc, s -> "$acc/$s" }}"
            binding.questionAnswer.text = "$answer : ${item.answer_end_description}"
            binding.timeAnswer.text = "$dateClosure : ${item.answer_end_date}"

            binding.container.tag = item
            binding.container.setOnClickListener(this)
        }
    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    private inner class EventFeedHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: InapprovingBidItemBinding? = null

        init {
            binding = DataBindingUtil.bind(itemView)
        }
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.container -> {
                val item = p0.tag as QuestionConsultantApproveData
                val intent: Intent = Intent(context, BrowseQuestionsActivity::class.java)
                intent.putExtra("key", 4)
                intent.putExtra("item_approve", item)
                context.startActivity(intent)
            }
        }
    }
}