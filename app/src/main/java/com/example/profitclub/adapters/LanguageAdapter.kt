package com.example.profitclub.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.profitclub.R
import com.example.profitclub.data.bids.Language
import com.example.profitclub.databinding.LanguageListItemBinding

class LanguageAdapter(private val context: Context, val items: ArrayList<Language>?, val languages: ArrayList<Int>, private val callBack: (Int, Boolean) -> Boolean) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), View.OnClickListener {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = LanguageListItemBinding.inflate(inflater, parent, false)
        return EventFeedHolder(binding.root)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items?.get(position)
        if (holder is EventFeedHolder) {
            val binding = holder.binding
            binding!!.language = item
            binding.nameLanguage.text = item?.name
            val color = context.resources.getColor(R.color.colorAccent)
            val black = context.resources.getColor(R.color.black)
            binding.checkboxLanguage.isChecked = languages.contains(item!!.id)

            binding.checkboxLanguage.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    binding.nameLanguage.setTextColor(color)
                } else {
                    binding.nameLanguage.setTextColor(black)
                }
                callBack.invoke(item.id, isChecked)
            }

            binding.container.tag = item
            binding.container.setOnClickListener(this)

        }
    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    private inner class EventFeedHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: LanguageListItemBinding? = null

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
               // context.startActivity(intent)
            }
        }
    }
}