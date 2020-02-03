package com.example.profitclub.ui.account.employees

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.profitclub.R
import com.example.profitclub.adapters.EmployeesAdapter
import com.example.profitclub.model.Bids
import kotlinx.android.synthetic.main.activity_employees_list.*


class EmployeesListActivity : AppCompatActivity(), View.OnClickListener {


    private var layoutManager: LinearLayoutManager? = null
    private var adapter: EmployeesAdapter? = null

    val list = listOf(
        Bids("Jason", "Finance", 1),
        Bids("William", "Accounting / Marketing", 1),
        Bids("Stephan", "Law", 1),
        Bids("Fred", "IT / Software Engineering", 1),
        Bids("Jason", "Finance", 1),
        Bids("William", "Accounting / Marketing", 1),
        Bids("Stephan", "Law", 1),
        Bids("Fred", "IT / Software Engineering", 1),
        Bids("Jason", "Finance", 1),
        Bids("William", "Accounting / Marketing", 1),
        Bids("Stephan", "Law", 1),
        Bids("Fred", "IT / Software Engineering", 1)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employees_list)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        adapter = EmployeesAdapter(this.applicationContext, list, this)
        layoutManager = LinearLayoutManager(this.applicationContext, LinearLayoutManager.VERTICAL, false)
        this.employees_list.layoutManager = layoutManager
        this.employees_list.adapter = adapter
        adapter?.notifyDataSetChanged()
    }

    override fun onClick(p0: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
