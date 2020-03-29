package com.example.profitclub.ui.questions.open

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.profitclub.adapters.QuestionsAdapter
import com.example.profitclub.databinding.FragmentOpenQuestionsBinding
import com.example.profitclub.toast

class OpenQuestionsFragment : Fragment(), View.OnClickListener {

    private val APP_PREFERENCE = "MYSETTINGS"
    private lateinit var binding: FragmentOpenQuestionsBinding
    private lateinit var viewModel: OpenQuestionsViewModel
    private var layoutManager: LinearLayoutManager? = null
    private var adapter: QuestionsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOpenQuestionsBinding.inflate(layoutInflater)
       // (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // val root = inflater.inflate(R.layout.fragment_questions, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       activity?.let {activity ->
           val preferences = activity.getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE)
           viewModel =
               ViewModelProviders.of(this, OpenQuestionsViewModelFactory(preferences)).get(OpenQuestionsViewModel::class.java)

           adapter = QuestionsAdapter(this.context!!, null, this)
           layoutManager = LinearLayoutManager(this.context!!, LinearLayoutManager.VERTICAL, false)
           binding.recyclerOpen.layoutManager = layoutManager
           binding.recyclerOpen.adapter = adapter
           adapter?.notifyDataSetChanged()

           viewModel.data.observe(viewLifecycleOwner, Observer { data ->
               if (data != null){
                   adapter = QuestionsAdapter(this.context!!, data.data, this)
                   binding.recyclerOpen.adapter = adapter
                   adapter?.notifyDataSetChanged()
               }
           })

           viewModel.error.observe(viewLifecycleOwner, Observer { message ->
               toast("Error: $message")
           })

           viewModel.fetchData()
       }
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchData()
    }

    override fun onClick(p0: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}