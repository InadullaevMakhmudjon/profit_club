package com.example.profitclub.ui.questions

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.profitclub.R
import com.example.profitclub.model.Bids
import kotlinx.android.synthetic.main.activity_question_detail.*
import uz.abubakrr.eventme.adapters.BidsAdapter

class QuestionDetailActivity : AppCompatActivity(), View.OnClickListener {


    private var layoutManager: LinearLayoutManager? = null
    private var adapter: BidsAdapter? = null

    val list = listOf(Bids("Jason", "$100,00 USD in 9 days"),
        Bids("William", "$200,00 USD in 7 days"),Bids("William", "$200,00 USD in 7 days"),
                Bids("Jason", "$100,00 USD in 9 days"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_detail)

        val role = this.intent.getIntExtra("role",1)

        if(role == 2){
            recycler.isVisible = false
        } else{
            place_bid.isVisible = false
        }

        adapter = BidsAdapter(this.applicationContext, list, this)
        layoutManager = LinearLayoutManager(this.applicationContext, LinearLayoutManager.VERTICAL, false)
        this.recycler.layoutManager = layoutManager
        this.recycler.adapter = adapter
        adapter?.notifyDataSetChanged()

        place_bid.setOnClickListener {
            val intent = Intent(applicationContext, QuestionCreationActivity::class.java)
            intent.putExtra("role", 2)
            startActivity(intent)
        }
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)

    }

    override fun onClick(p0: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
