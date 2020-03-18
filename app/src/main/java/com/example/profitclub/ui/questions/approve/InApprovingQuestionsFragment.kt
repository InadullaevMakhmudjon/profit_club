package com.example.profitclub.ui.questions.approve

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.profitclub.adapters.ApprovingQuestionsAdapter
import com.example.profitclub.databinding.FragmentApprovingQuestionsBinding
import com.example.profitclub.model.Questions
import com.example.profitclub.toast

class InApprovingQuestionsFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentApprovingQuestionsBinding
    private lateinit var vm: InApprovingQuestionsViewModel
    private var layoutManager: LinearLayoutManager? = null
    private var adapter: ApprovingQuestionsAdapter? = null
    private val APP_PREFERENCE = "MYSETTINGS"

    val list = listOf(
        Questions("I need en expert who will help me putting my website into a domain", 5, 15) { msg -> toast(msg) },
        Questions("I need en expert who will help me putting my website into a domain", 41, 99) { msg -> toast(msg) },
        Questions("I need en expert who will help me putting my website into a domain", 42, 99) { msg -> toast(msg) },
        Questions("I need en expert who will help me putting my website into a domain", 43, 99) { msg -> toast(msg) },
        Questions("I need en expert who will help me putting my website into a domain", 44, 99) { msg -> toast(msg) },
        Questions("I need en expert who will help me putting my website into a domain", 45, 99) { msg -> toast(msg) },
        Questions("I need en expert who will help me putting my website into a domain", 46, 99) { msg -> toast(msg) },
        Questions("I need en expert who will help me putting my website into a domain", 47, 99) { msg -> toast(msg) },
        Questions("I need en expert who will help me putting my website into a domain", 48, 99) { msg -> toast(msg) },
        Questions("I need en expert who will help me putting my website into a domain", 49, 99) { msg -> toast(msg) },
        Questions("I need en expert who will help me putting my website into a domain", 40, 99) { msg -> toast(msg) },
        Questions("I need en expert who will help me putting my website into a domain", 499, 99) { msg -> toast(msg) }
    )
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentApprovingQuestionsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.let { activity ->

            val preferences = activity.getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE)
            vm =
                ViewModelProviders.of(this, ApproveQuestionViewModelFactory(preferences)).get(InApprovingQuestionsViewModel::class.java)
            adapter = ApprovingQuestionsAdapter(this.context!!, list, this)
            layoutManager = LinearLayoutManager(this.context!!, LinearLayoutManager.VERTICAL, false)
            binding.recyclerApprove.layoutManager = layoutManager
            binding.recyclerApprove.adapter = adapter
            adapter?.notifyDataSetChanged()
        }
    }

    override fun onResume() {
        super.onResume()
        adapter?.notifyDataSetChanged()
    }

    override fun onClick(p0: View?) {

        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}