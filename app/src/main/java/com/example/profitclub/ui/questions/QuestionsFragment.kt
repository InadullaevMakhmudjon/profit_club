package com.example.profitclub.ui.questions

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.profitclub.MainActivity
import com.example.profitclub.R
import com.example.profitclub.databinding.FragmentQuestionsBinding
import com.example.profitclub.toast
import kotlinx.android.synthetic.main.fragment_questions.*

class QuestionsFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentQuestionsBinding
    private lateinit var galleryViewModel: QuestionsViewModel
    private var mSectionPageAdapter: SectionPageAdapter2? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       // (activity as AppCompatActivity).setSupportActionBar(toolbar)
       // (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val activity = activity as MainActivity?
        activity.let {
            activity?.customActionBarTitle(getString(R.string.My_Questions))
        }

        galleryViewModel =
            ViewModelProviders.of(this).get(QuestionsViewModel::class.java)
       // val root = inflater.inflate(R.layout.fragment_questions, container, false)
        binding = FragmentQuestionsBinding.inflate(layoutInflater)

        mSectionPageAdapter = SectionPageAdapter2(childFragmentManager, activity!!)
        binding.viewPager.adapter = mSectionPageAdapter
        binding.pagerHeader.setTabIndicatorColorResource(R.color.colorAccent2)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        post_question.setOnClickListener {
            startActivity(Intent(context, QuestionCreationActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onClick(p0: View?) {

        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}