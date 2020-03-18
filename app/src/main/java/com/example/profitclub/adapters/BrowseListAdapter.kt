package com.example.profitclub.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.profitclub.R
import com.example.profitclub.data.bids.ConsultantBidsData
import com.example.profitclub.databinding.BrowseItemBinding
import com.example.profitclub.ui.browse.BrowseQuestionsActivity

class BrowseListAdapter(private val context: Context, private val items: ArrayList<ConsultantBidsData>?, private val listener: View.OnClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), View.OnClickListener {
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = BrowseItemBinding.inflate(inflater, parent, false)
        return EventFeedHolder(binding.root)
    }
    
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items?.get(position)
        if (holder is EventFeedHolder) {
            val binding = holder.binding
            binding!!.browseItem = item

            binding.bidText.text = item?.title
            binding.bidCategories.text = item?.categories?.reduce{a, b -> "$a/$b"}
            binding.questionId.text = item?.question_id.toString()
            binding.clickCount.text = item?.status.toString()

            binding.container.tag = item
            binding.container.setOnClickListener(this)
        }
    }

    override fun getItemCount(): Int{
        return items?.size ?: 0
    }

    private inner class EventFeedHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: BrowseItemBinding? = null

        init {
            binding = DataBindingUtil.bind(itemView)
        }
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.container -> {
                val item = p0?.tag as ConsultantBidsData
                val intent: Intent = Intent(context, BrowseQuestionsActivity::class.java)
                intent.putExtra("key", 2)
                intent.putExtra("item_browse", item)
               context.startActivity(intent)
            }
        }
    }
}