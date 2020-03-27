package com.example.profitclub.ui.questions.dispute

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.profitclub.adapters.InArbitrationQuestionsAdapter
import com.example.profitclub.data.questions.QuestionConsultantDisputeData
import com.example.profitclub.model.Questions

import com.example.profitclub.databinding.FragmentArbitrationQuestionsBinding
import com.example.profitclub.toast
import java.util.ArrayList

class InArbitrationQuestionsFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentArbitrationQuestionsBinding
    private lateinit var vm: InArbitrationQuestionsViewModel
    private var layoutManager: LinearLayoutManager? = null
    private var adapter: InArbitrationQuestionsAdapter? = null
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
        binding = FragmentArbitrationQuestionsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dataHolder = ArrayList<QuestionConsultantDisputeData>()
        activity?.let { activity ->
            val preferences = activity.getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE)
            vm =
                ViewModelProviders.of(this, ArbitrationQuestionsViewModelFactory(preferences)).get(InArbitrationQuestionsViewModel::class.java)
            adapter = InArbitrationQuestionsAdapter(this.context!!, dataHolder, this)
            layoutManager = LinearLayoutManager(this.context!!, LinearLayoutManager.VERTICAL, false)
            binding.recyclerArbitration.layoutManager = layoutManager
            binding.recyclerArbitration.adapter = adapter
            adapter?.notifyDataSetChanged()

            vm.data.observe(viewLifecycleOwner, Observer { data ->
                if (data != null){
                    dataHolder.clear()
                    dataHolder.addAll(data.data)
                    adapter?.notifyDataSetChanged()
                }
            })

            vm.error.observe(viewLifecycleOwner, Observer { message ->
                toast(message)
            })
        }
    }

    override fun onResume() {
        super.onResume()
        vm.getData()
    }

    override fun onClick(p0: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}