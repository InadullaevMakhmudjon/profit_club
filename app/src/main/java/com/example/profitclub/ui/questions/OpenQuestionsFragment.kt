package com.example.profitclub.ui.questions

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.profitclub.adapters.QuestionsAdapter
import com.example.profitclub.databinding.FragmentOpenQuestionsBinding
import com.example.profitclub.model.Questions

class OpenQuestionsFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentOpenQuestionsBinding
    private lateinit var galleryViewModel: OpenQuestionsViewModel
    private var layoutManager: LinearLayoutManager? = null
    private var adapter: QuestionsAdapter? = null

        val list = listOf(Questions("I need en expert who will help me putting my website into a domain", 11, 99),
                          Questions("I need en expert who will help me putting my website into a domain", 41, 99),
            Questions("I need en expert who will help me putting my website into a domain", 41, 99),
            Questions("I need en expert who will help me putting my website into a domain", 41, 99),Questions("I need en expert who will help me putting my website into a domain", 41, 99),
            Questions("I need en expert who will help me putting my website into a domain", 41, 99),Questions("I need en expert who will help me putting my website into a domain", 41, 99),
            Questions("I need en expert who will help me putting my website into a domain", 41, 99),Questions("I need en expert who will help me putting my website into a domain", 41, 99),
            Questions("I need en expert who will help me putting my website into a domain", 41, 99),Questions("I need en expert who will help me putting my website into a domain", 41, 99),
            Questions("I need en expert who will help me putting my website into a domain", 41, 99))

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       // (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        galleryViewModel =
            ViewModelProviders.of(this).get(OpenQuestionsViewModel::class.java)
       // val root = inflater.inflate(R.layout.fragment_questions, container, false)
        binding = FragmentOpenQuestionsBinding.inflate(layoutInflater)
        adapter = QuestionsAdapter(this.context!!, list, this)
        layoutManager = LinearLayoutManager(this.context!!, LinearLayoutManager.VERTICAL, false)
        binding.recyclerCompleted.layoutManager = layoutManager
        binding.recyclerCompleted.adapter = adapter
        adapter?.notifyDataSetChanged()
        /*val textView: TextView = root.findViewById(R.id.text_gallery)
        galleryViewModel.text.observe(this, Observer {
            textView.text = it
        })*/

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }



    override fun onResume() {
        super.onResume()
        adapter?.notifyDataSetChanged()
    }

    override fun onClick(p0: View?) {

        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}