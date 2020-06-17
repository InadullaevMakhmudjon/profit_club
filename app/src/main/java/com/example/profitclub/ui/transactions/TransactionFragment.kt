package com.example.profitclub.ui.transactions

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
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
import com.example.profitclub.adapters.TransactionAdapter
import com.example.profitclub.databinding.FragmentTransactionBinding

class TransactionFragment : Fragment(), View.OnClickListener {
    private val APP_PREFERENCE = "MYSETTINGS"
    private lateinit var preferences: SharedPreferences
    private lateinit var vm: TransactionViewModel
    private lateinit var binding: FragmentTransactionBinding
    private var layoutManager: LinearLayoutManager? = null
    private var adapter: TransactionAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTransactionBinding.inflate(layoutInflater)
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
            adapter = TransactionAdapter(this.context!!, null, this)
            layoutManager = LinearLayoutManager(this.context!!, LinearLayoutManager.VERTICAL, false)
            binding.transactionList.layoutManager = layoutManager
            binding.transactionList.adapter = adapter
            adapter?.notifyDataSetChanged()

            val bill = preferences.getString("bill", null)
            val sum = resources.getString(R.string.sum_balance)
            binding.balance.text = "$bill $sum"

            vm.transactionBody.observe(viewLifecycleOwner, Observer {data ->
                if (data != null){
                    adapter = TransactionAdapter(this.context!!, data.data, this)
                    binding.transactionList.adapter = adapter
                }
            })
        }
    }

    override fun onClick(p0: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}