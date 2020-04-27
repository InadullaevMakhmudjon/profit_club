package com.example.profitclub.ui.transactions.penalty

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.profitclub.MainActivity
import com.example.profitclub.R
import com.example.profitclub.adapters.TransactionAdapter2
import com.example.profitclub.databinding.FragmentPenaltyBinding
import com.example.profitclub.ui.transactions.TransactionViewModel
import com.example.profitclub.ui.transactions.TransactionViewModelFactory

class PenaltyFragment : Fragment(), View.OnClickListener {
    private val APP_PREFERENCE = "MYSETTINGS"
    private lateinit var preferences: SharedPreferences
    private lateinit var vm: TransactionViewModel
    private lateinit var binding: FragmentPenaltyBinding
    private var layoutManager: LinearLayoutManager? = null
    private var adapter: TransactionAdapter2? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPenaltyBinding.inflate(layoutInflater)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity.let {

            preferences = it!!.getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE)
            val activity = activity as MainActivity?
            activity.let {
                activity?.customActionBarTitle(getString(R.string.transaction))
            }
            vm =
                ViewModelProviders.of(this, TransactionViewModelFactory(preferences)).get(TransactionViewModel::class.java)

            adapter = TransactionAdapter2(this.context!!, null, this)
            layoutManager = LinearLayoutManager(this.context!!, LinearLayoutManager.VERTICAL, false)
            binding.transactionList.layoutManager = layoutManager
            binding.transactionList.adapter = adapter
            adapter?.notifyDataSetChanged()

            vm.penaltyBody.observe(viewLifecycleOwner, Observer {data ->
                if (data != null){
                    adapter = TransactionAdapter2(this.context!!, data.data, this)
                    binding.transactionList.adapter = adapter
                }
            })
        }
    }

    override fun onClick(p0: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}