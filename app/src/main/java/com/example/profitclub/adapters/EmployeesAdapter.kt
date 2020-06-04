package com.example.profitclub.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.profitclub.R
import com.example.profitclub.data.BASE_URL
import com.example.profitclub.data.registration.GetUserStaffInfoBody
import com.example.profitclub.databinding.EmployeesItemBinding
import com.example.profitclub.ui.account.details.ProfileActivity
import com.example.profitclub.ui.account.details.ProfileDetailsFragment
import com.example.profitclub.ui.account.employees.EmployeeCreationActivity
import com.example.profitclub.ui.questions.open.QuestionDetailActivity
import com.squareup.picasso.Picasso

class EmployeesAdapter(private val context: Context, val items: ArrayList<GetUserStaffInfoBody>?, val listener: View.OnClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), View.OnClickListener {
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = EmployeesItemBinding.inflate(inflater, parent, false)
        return EventFeedHolder(binding.root)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items?.get(position)
        if (holder is EventFeedHolder) {
            val binding = holder.binding
            binding!!.staff = item
            binding.name.text = item?.lname
            binding.surname.text = item?.fname
            binding.patronymic.text =  item?.mname
            binding.role.text = item?.role

            if (item?.media_url != null){
                Picasso.get()
                    .load(BASE_URL + item.media_url + "/sm_avatar.jpg").fit()
                    .into(binding.staffPhoto)
            }

            binding.container.tag = item
            binding.container.setOnClickListener(this)

        }
    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    private inner class EventFeedHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: EmployeesItemBinding? = null

        init {
            binding = DataBindingUtil.bind(itemView)
        }
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.container -> {
                val item = p0?.tag as GetUserStaffInfoBody
                val intent: Intent = Intent(context, EmployeeCreationActivity::class.java)
                intent.putExtra("key", 1)
                intent.putExtra("item", item)
                context.startActivity(intent)
            }
        }
    }
}