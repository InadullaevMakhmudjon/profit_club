package com.example.profitclub.ui.account.employees

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.profitclub.R
import com.example.profitclub.adapters.EmployeesAdapter
import com.example.profitclub.data.registration.GetUserStaffInfoBody
import kotlinx.android.synthetic.main.activity_employees_list.*

class EmployeesListActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var vm: EmployeesListViewModel
    private val APP_PREFERENCE = "MYSETTINGS"
    private var layoutManager: LinearLayoutManager? = null
    private var adapter: EmployeesAdapter? = null
    private val items = ArrayList<GetUserStaffInfoBody>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employees_list)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val preferences = getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE)
        vm = ViewModelProviders.of(this, EmployeesListViewModelFactory(preferences)).get(EmployeesListViewModel::class.java)

        adapter = EmployeesAdapter(this.applicationContext, items, this)
        layoutManager = LinearLayoutManager(this.applicationContext, LinearLayoutManager.VERTICAL, false)
        this.employees_list.layoutManager = layoutManager
        this.employees_list.adapter = adapter
        adapter?.notifyDataSetChanged()

        vm.staff.observe(this, Observer { data ->
            if (data != null){
                items.clear()
                items.addAll(data.data)
                adapter!!.notifyDataSetChanged()
            }
        })

        add_user.setOnClickListener {
            val intent: Intent = Intent(this, EmployeeCreationActivity::class.java)
            intent.putExtra("key", 2)
            this.startActivity(intent)
        }
    }

    override fun onClick(p0: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
