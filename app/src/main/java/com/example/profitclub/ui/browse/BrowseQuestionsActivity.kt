package com.example.profitclub.ui.browse

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.profitclub.R
import com.example.profitclub.model.Questions
import kotlinx.android.synthetic.main.activity_browse_questions.*
import kotlinx.android.synthetic.main.fragment_browse.*
import kotlinx.android.synthetic.main.fragment_browse.toolbar
import uz.abubakrr.eventme.adapters.QuestionsAdapter
import uz.abubakrr.eventme.adapters.QuestionsInBrowseAdapter

class BrowseQuestionsActivity : AppCompatActivity(), View.OnClickListener {

    private var layoutManager: LinearLayoutManager? = null
    private var adapter: QuestionsInBrowseAdapter? = null

    val list = listOf(
        Questions("I need en expert who will help me putting my website into a domain", 11, 99),
        Questions("I need en expert who will help me putting my website into a domain", 41, 99),
        Questions("I need en expert who will help me putting my website into a domain", 41, 99),
        Questions("I need en expert who will help me putting my website into a domain", 41, 99),
        Questions("I need en expert who will help me putting my website into a domain", 41, 99),
        Questions("I need en expert who will help me putting my website into a domain", 41, 99),
        Questions("I need en expert who will help me putting my website into a domain", 41, 99),
        Questions("I need en expert who will help me putting my website into a domain", 41, 99),
        Questions("I need en expert who will help me putting my website into a domain", 41, 99),
        Questions("I need en expert who will help me putting my website into a domain", 41, 99),
        Questions("I need en expert who will help me putting my website into a domain", 41, 99),
        Questions("I need en expert who will help me putting my website into a domain", 41, 99)
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browse_questions)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        adapter = QuestionsInBrowseAdapter(applicationContext, list, this)
        layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        recycler_view.layoutManager = layoutManager
        recycler_view.adapter = adapter
        adapter?.notifyDataSetChanged()

    }

    override fun onClick(p0: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
