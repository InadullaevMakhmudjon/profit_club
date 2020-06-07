package com.example.profitclub.ui.account.details

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.profitclub.R
import com.example.profitclub.adapters.ReviewsAdapter
import com.example.profitclub.data.bids.ResponseUserRating

class ProfileReviewsFragment : Fragment(), View.OnClickListener {
    private var layoutManager: LinearLayoutManager? = null
    private var adapter: ReviewsAdapter? = null
    val reviews = ArrayList<ResponseUserRating>()
    private val APP_PREFERENCE = "MYSETTINGS"
    private lateinit var vm: ProfileReviewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile_reviews, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity.let { activity ->
            val preferences = activity!!.getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE)
            vm =
                ViewModelProviders.of(this, ProfileReviewViewModelFactory(preferences)).get(ProfileReviewsViewModel::class.java)

            val recyclerView: RecyclerView = view.findViewById(R.id.recycler_reviews)
            adapter = ReviewsAdapter(context!!, reviews)
            layoutManager = LinearLayoutManager(context!!, LinearLayoutManager.VERTICAL, false)
            recyclerView.layoutManager = layoutManager
            recyclerView.adapter = adapter
            adapter?.notifyDataSetChanged()
            val userId = preferences.getInt("user_id", 0)
            vm.getUserRating(userId)

            vm.userRating.observe(viewLifecycleOwner, Observer { data ->
                if (data != null){
                    reviews.clear()
                    reviews.addAll(data.data)
                    adapter!!.notifyDataSetChanged()
                }
            })
        }
    }

    override fun onClick(p0: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}