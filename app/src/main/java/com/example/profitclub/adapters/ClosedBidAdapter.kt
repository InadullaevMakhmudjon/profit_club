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
import com.example.profitclub.data.questions.QuestionConsultantClosedData
import com.example.profitclub.databinding.ClosedBidItemBinding
import com.example.profitclub.ui.browse.BrowseQuestionsActivity

class ClosedBidAdapter(private val context: Context, private val items: ArrayList<QuestionConsultantClosedData>?, private val listener: View.OnClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), View.OnClickListener {
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ClosedBidItemBinding.inflate(inflater, parent, false)
        return EventFeedHolder(binding.root)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items?.get(position)
        if (holder is EventFeedHolder) {
            val binding = holder.binding
            binding!!.close = item
            val title = context.getString(R.string.title_)
            val language = context.getString(R.string.language_)
            val categories = context.getString(R.string.categories)
            binding.questionTitle.text = "$title ${item?.title}"
            binding.questionLanguage.text = "$language ${item?.qlang}"
            binding.questionCategory.text = "$categories ${item?.categories!!.reduce { acc, s -> "$acc/$s" }}"
            binding.container.tag = item
            binding.container.setOnClickListener(this)

        }
    }

    override fun getItemCount(): Int {
        return items?.size?: 0
    }

    private inner class EventFeedHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: ClosedBidItemBinding? = null

        init {
            binding = DataBindingUtil.bind(itemView)
        }
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.container -> {
                val item = p0.tag as QuestionConsultantClosedData
                val intent: Intent = Intent(context, BrowseQuestionsActivity::class.java)
                intent.putExtra("key", 6)
                intent.putExtra("item_close", item)
                context.startActivity(intent)
            }
        }
    }
}