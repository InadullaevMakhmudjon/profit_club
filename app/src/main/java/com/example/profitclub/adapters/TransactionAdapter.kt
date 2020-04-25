package com.example.profitclub.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.profitclub.R
import com.example.profitclub.data.transactions.TransactionResponseBody
import com.example.profitclub.databinding.TransactionItemBinding

class TransactionAdapter(private val context: Context, val items: ArrayList<TransactionResponseBody>?, private val listener: View.OnClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), View.OnClickListener {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = TransactionItemBinding.inflate(inflater, parent, false)
        return EventFeedHolder(binding.root)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items?.get(position)
        if (holder is EventFeedHolder) {
            val binding = holder.binding
            binding!!.transaction = item
            val date = context.resources.getString(R.string.date)
            val owner = context.resources.getString(R.string.owner)
            val type = context.resources.getString(R.string.type)
            val amount = context.resources.getString(R.string.amount)
            val info = context.resources.getString(R.string.information_transaction)
            binding.date.text = "$date ${item?.trans_date}"
            binding.owner.text = "$owner ${item?.name}"
            binding.type.text = "$type ${item?.v_type}"
            binding.amount.text = "$amount ${item?.amount.toString()}"
            binding.info.text = "$info ${item?.info?.title}"
            /*binding.date.text = item?.trans_date
            binding.owner.text = item?.name
            binding.type.text = item?.v_type
            binding.amount.text = item?.amount.toString()
            binding.info.text = item?.info?.title*/

            binding.container.tag = item
            binding.container.setOnClickListener(this)

        }
    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    private inner class EventFeedHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: TransactionItemBinding? = null

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