package com.example.profitclub.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.profitclub.R
import com.example.profitclub.data.bids.ConsultantBidsClickData
import com.example.profitclub.databinding.OpenBidItemBinding
import com.example.profitclub.ui.browse.BrowseQuestionsActivity

class OpenBidAdapter(private val context: Context, private val items: ArrayList<ConsultantBidsClickData>?, private val listener: View.OnClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), View.OnClickListener {
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = OpenBidItemBinding.inflate(inflater, parent, false)
        return EventFeedHolder(binding.root)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items?.get(position)
        if (holder is EventFeedHolder) {
            val binding = holder.binding
            binding!!.chatQuestion = item

            /* Picasso.get()
                     .load(item.phost_photo)
                     .into(binding.imageActual)*/
            binding.questionText.text = item?.title
            binding.questionId.text = item?.bid_id.toString()
            binding.category.text = item?.categories?.reduce { a, b -> "$a/$b"}
            binding.price.text = item?.price
            binding.status.text = item?.deadline

            binding.container.tag = item
            binding.container.setOnClickListener(this)
        }
    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    private inner class EventFeedHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: OpenBidItemBinding? = null

        init {
            binding = DataBindingUtil.bind(itemView)
        }
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.container -> {
                val item = p0.tag as ConsultantBidsClickData
                val intent: Intent = Intent(context, BrowseQuestionsActivity::class.java)
                intent.putExtra("key", 1)
                intent.putExtra("item_open", item)
                context.startActivity(intent)
            }
        }
    }

    private operator fun <T> MutableLiveData<T>.get(position: Int): ConsultantBidsClickData = get(position)
}