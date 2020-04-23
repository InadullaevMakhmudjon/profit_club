package com.example.profitclub.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.profitclub.R
import com.example.profitclub.data.transactions.PenaltyResponseBody
import com.example.profitclub.databinding.TransactionItem2Binding


class TransactionAdapter2(private val context: Context, val items: ArrayList<PenaltyResponseBody>?, private val listener: View.OnClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), View.OnClickListener {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = TransactionItem2Binding.inflate(inflater, parent, false)
        return EventFeedHolder(binding.root)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items?.get(position)
        if (holder is EventFeedHolder) {
            val binding = holder.binding
            binding!!.penalty = item
            val date = context.resources.getString(R.string.penalty_date)
            val title = context.resources.getString(R.string.penalty_title)
            val value = context.resources.getString(R.string.penalty_value)
            val type = context.resources.getString(R.string.penalty_type)
            val type1 = context.resources.getString(R.string.penalty_cancel_question)
            val status = context.resources.getString(R.string.penalty_status)
            val status0 = context.resources.getString(R.string.penalty_status_0)
            binding.penaltyDate.text = "$date ${item?.penalty_date}"
            binding.penaltyTitle.text = "$title ${item?.question_tile}"
            binding.penaltyValue.text = "$value ${item?.penalty_value.toString()}"
            when (item?.ptype_id){
                1 -> binding.penaltyType.text = "$type $type1"
            }
            when (item?.penalty_status){
                0 ->  binding.penaltyStatus.text = "$status $status0"
            }
            /* Picasso.get()
                     .load(item.phost_photo)
                     .into(binding.imageActual)*/

            binding.container.tag = item
            binding.container.setOnClickListener(this)
        }
    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    private inner class EventFeedHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: TransactionItem2Binding? = null

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