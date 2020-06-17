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
import com.example.profitclub.data.questions.QuestionConsultantDisputeData
import com.example.profitclub.databinding.InarbitrationQuestionItemBinding
import com.example.profitclub.model.Questions
import com.example.profitclub.ui.questions.open.QuestionDetailActivity

class InArbitrationQuestionsAdapter(private val context: Context, private val items: ArrayList<QuestionConsultantDisputeData>?, private val listener: View.OnClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), View.OnClickListener {
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = InarbitrationQuestionItemBinding.inflate(inflater, parent, false)
        return EventFeedHolder(binding.root)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items?.get(position)
        if (holder is EventFeedHolder) {
            val binding = holder.binding
            binding!!.arbitration = item
            val title = context.getString(R.string.title_)
            val language = context.getString(R.string.language_)
            val categories = context.getString(R.string.categories)
            val answerManager = context.getString(R.string.dispute_manager)
            val winner = context.getString(R.string.winner_desc)
            binding.questionTitle.text = "$title ${item?.title}"
            binding.questionLanguage.text = "$language ${item?.qlang}"
            binding.questionCategory.text = "$categories ${item?.categories!!.reduce { acc, s -> "$acc/$s" }}"
            binding.answerManager.text = "$answerManager: ${item.answer_dispute}"
            binding.winner.text = "$winner: ${item.true_user_fullname}"
            binding.container.tag = item
            binding.container.setOnClickListener(this)
        }
    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    private inner class EventFeedHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: InarbitrationQuestionItemBinding? = null

        init {
            binding = DataBindingUtil.bind(itemView)
        }
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.container -> {
              val item = p0.tag as QuestionConsultantDisputeData
                val intent: Intent = Intent(context, QuestionDetailActivity::class.java)
                intent.putExtra("role", 2)
                intent.putExtra("item_dispute", item)
                context.startActivity(intent)
            }
        }
    }
}