package com.example.profitclub.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.profitclub.R
import com.example.profitclub.data.BASE_URL
import com.example.profitclub.data.bids.ClientClickView
import com.example.profitclub.databinding.UserItemBinding
import com.squareup.picasso.Picasso

class BidsAdapter(private val context: Context, private val items: ArrayList<ClientClickView>?, private val listener: View.OnClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), View.OnClickListener {
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = UserItemBinding.inflate(inflater, parent, false)
        return EventFeedHolder(binding.root)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items?.get(position)
        if (holder is EventFeedHolder) {
            val binding = holder.binding
            binding!!.bids = item
            binding.name.text = item?.fullname
            binding.price.text = item?.price.toString()
            binding.ratingDesc.rating = item?.rate!!
            binding.deadline.text = item.deadline

             Picasso.get()
                    .load(BASE_URL + item.media_url + "/sm_avatar.jpg").fit()
                    .into(binding.imageConsultant)

            //Flag activity new task needed here
           /* binding.hire.setOnClickListener {
                val intent = Intent(context, ProfileActivity::class.java)
                intent.putExtra("role", 2)
                context.startActivity(intent)
            }*/

            binding.container.tag = item
            binding.container.setOnClickListener(this)

        }
    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    private inner class EventFeedHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: UserItemBinding? = null

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

    private operator fun <T> MutableLiveData<T>.get(position: Int): ClientClickView = get(position)
}