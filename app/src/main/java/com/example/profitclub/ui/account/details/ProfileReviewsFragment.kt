package com.example.profitclub.ui.account.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.profitclub.R
import com.example.profitclub.adapters.ReviewsAdapter
import com.example.profitclub.model.Reviews
import kotlinx.android.synthetic.main.activity_question_detail.*
import kotlinx.android.synthetic.main.fragment_profile_reviews.*

class ProfileReviewsFragment : Fragment(), View.OnClickListener {


    private var layoutManager: LinearLayoutManager? = null
    private var adapter: ReviewsAdapter? = null

    val list = listOf(Reviews("Very helpful. Best consultant I have ever worked. Thank you!!!", 5.0, "William"),
        Reviews("Very helpful. Best consultant I have ever worked. Thank you!!!", 5.0, "William"),
        Reviews("Very helpful. Best consultant I have ever worked. Thank you!!!", 5.0, "William"),
        Reviews("Very helpful. Best consultant I have ever worked. Thank you!!!", 5.0, "William"),
        Reviews("Very helpful. Best consultant I have ever worked. Thank you!!!", 5.0, "William"))

    private lateinit var homeViewModel: ProfileReviewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(ProfileReviewsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_profile_reviews, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(this, Observer {
            textView.text = it
        })

        val recyclerView: RecyclerView = root.findViewById(R.id.recycler_reviews)
        adapter = ReviewsAdapter(context!!, list, this)
        layoutManager = LinearLayoutManager(context!!, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        adapter?.notifyDataSetChanged()

        return root
    }

    override fun onClick(p0: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}