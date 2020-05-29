package com.example.profitclub.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.profitclub.data.bids.ResponseUserRating
import com.example.profitclub.databinding.ReviewItemBinding

class ReviewsAdapter(private val context: Context, val items: ArrayList<ResponseUserRating>?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ReviewItemBinding.inflate(inflater, parent, false)
        return EventFeedHolder(binding.root)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items?.get(position)
        if (holder is EventFeedHolder) {
            val binding = holder.binding
            binding!!.review = item
            binding.reviewDesc.text = item?.description
            binding.date.text = item?.cdate
        }
    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    private inner class EventFeedHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: ReviewItemBinding? = null
        init {
            binding = DataBindingUtil.bind(itemView)
        }
    }
}