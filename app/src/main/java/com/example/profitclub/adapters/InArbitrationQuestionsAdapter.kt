package com.example.profitclub.adapters

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

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items?.get(position)
        if (holder is EventFeedHolder) {
            val binding = holder.binding
            binding!!.arbitration = item

            binding.questionText.text = item?.title
            binding.questionId.text = item?.question_id.toString()
            binding.category.text = item?.categories?.reduce { a, b -> "$a/$b"}
            binding.price.text = item?.price.toString()
            binding.status.text = item?.status.toString()

            binding?.container.tag = item
            binding?.container.setOnClickListener(this)
                //items[position].callBack?.invoke("CLicked ${item.num_bids}")

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