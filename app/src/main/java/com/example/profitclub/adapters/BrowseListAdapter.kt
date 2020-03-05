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
import com.example.profitclub.data.bids.ConsultantBidsData
import com.example.profitclub.data.questions.QuestionConsultantData
import com.example.profitclub.databinding.BrowseItemBinding
import com.example.profitclub.databinding.ChatItemBinding
import com.example.profitclub.ui.bids.BidDetailActivity

class BrowseListAdapter(private val context: Context, private val items: ArrayList<ConsultantBidsData>?, private val listener: View.OnClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), View.OnClickListener {
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        if (viewType == TYPE_FEED) {
        val binding = BrowseItemBinding.inflate(inflater, parent, false)
        return EventFeedHolder(binding.root)
//        }
//        return null

    }
    
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items?.get(position)
        if (holder is EventFeedHolder) {
            val binding = holder.binding
            binding!!.browseItem = item

         /*   when {
                item.status == "Open" -> binding.status.setBackgroundResource(R.drawable.bc_status_open)
                item.status == "Rejected" -> binding.status.setBackgroundResource(R.drawable.bc_status_rejected)
                item.status == "Arbitration" -> binding.status.setBackgroundResource(R.drawable.bc_status_in_arbitration)
                item.status == "Progress" -> binding.status.setBackgroundResource(R.drawable.bc_status_in_progress)
            }*/

            /* Picasso.get()
                     .load(item.phost_photo)
                     .into(binding.imageActual)*/
            binding.bidText.text = item?.title
            binding.bidCategories.text = item?.categories?.reduce{a, b -> "$a/$b"}
            binding.questionId.text = item?.question_id.toString()
            binding.clickCount.text = item?.status.toString()

            binding.container.tag = item
            binding.container.setOnClickListener(this)

        }
    }

    /*private val <T> MutableLiveData<T>.size: Int
        get() {
            observe(lifecycleowner: LifecycleOwner, Observer { data ->

            })
        }*/

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
              //  val item = p0?.getTag() as Questions
               // val intent: Intent = Intent(context, QuestionDetailActivity::class.java)
                //intent.putExtra(Const.EVENT_EXTRA, item)
               context.startActivity(Intent(context, BidDetailActivity::class.java))
            }
        }
    }
}

private operator fun <T> MutableLiveData<T>.get(position: Int): QuestionConsultantData = get(position)
