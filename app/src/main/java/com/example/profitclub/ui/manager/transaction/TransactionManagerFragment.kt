package com.example.profitclub.ui.manager.transaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.profitclub.MainActivity
import com.example.profitclub.adapters.TransactionAdapter
import com.example.profitclub.adapters.TransactionAdapter2
import com.example.profitclub.databinding.FragmentTransactionBinding
import com.example.profitclub.model.Transaction
import kotlinx.android.synthetic.main.fragment_transaction.*
import android.content.pm.PackageManager
import android.content.Intent
import android.content.ActivityNotFoundException
import android.content.Context
import androidx.core.content.ContextCompat.startActivity
import com.example.profitclub.databinding.FragmentManagerTransactionBinding

class TransactionManagerFragment : Fragment(), View.OnClickListener {

    private lateinit var homeViewModel: TransactionManagerViewModel
    private lateinit var binding: FragmentManagerTransactionBinding

    private var layoutManager: LinearLayoutManager? = null
    private var adapter: TransactionAdapter? = null

    val list = listOf(Transaction("21/01/2020 17:19", "Top up of the balance by PAY ME amount of 50,00 USD (No168374****97)", 1),
        Transaction("21/01/2020 17:19", "Top up of the balance by PAY ME amount of 50,00 USD (No168374****97)", 1),
        Transaction("30/01/2020 20:01", "Top up of the balance by PAY ME amount of 30,00 USD (No168374****99)", 2),
        Transaction("18/02/2020 18:00", "Top up of the balance by PAY ME amount of 40,00 USD (No168374****02)", 2),
        Transaction("01/03/2020 15:03", "Top up of the balance by PAY ME amount of 60,00 USD (No168374****19)", 2),
        Transaction("21/04/2020 22:19", "Top up of the balance by PAY ME amount of 30,00 USD (No168374****25)", 1),
        Transaction("21/01/2020 17:19", "Top up of the balance by PAY ME amount of 40,00 USD (No168374****55)", 1),
        Transaction("21/01/2020 17:19", "Top up of the balance by PAY ME amount of 100,00 USD (No168374****90)", 2),
        Transaction("21/01/2020 17:19", "Top up of the balance by PAY ME amount of 50,00 USD (No168374****87)", 2),
        Transaction("21/01/2020 17:19", "Top up of the balance by PAY ME amount of 100,00 USD (No168374****71)", 1),
        Transaction("21/01/2020 17:19", "Top up of the balance by PAY ME amount of 50,00 USD (No168374****65)", 1))

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(TransactionManagerViewModel::class.java)
        binding = FragmentManagerTransactionBinding.inflate(layoutInflater)


        adapter = TransactionAdapter(this.context!!, null, this)
        layoutManager = LinearLayoutManager(this.context!!, LinearLayoutManager.VERTICAL, false)
        binding.transactionList.layoutManager = layoutManager
        binding.transactionList.adapter = adapter
        adapter?.notifyDataSetChanged()

        /*val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(this, Observer {
            textView.text = it

        })*/

        return binding.root
    }

    override fun onClick(p0: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}