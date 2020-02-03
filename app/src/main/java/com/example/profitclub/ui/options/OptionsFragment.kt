package com.example.profitclub.ui.options

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.profitclub.MainActivity
import com.example.profitclub.R
import kotlinx.android.synthetic.main.fragment_options.*

class OptionsFragment : Fragment() {

    private lateinit var slideshowViewModel: SlideshowViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        slideshowViewModel =
            ViewModelProviders.of(this).get(SlideshowViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_options, container, false)
        //val textView: TextView = root.findViewById(R.id.text_slideshow)
        /*slideshowViewModel.text.observe(this, Observer {
            textView.text = it
        })*/
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        client_options.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.accountCreateAction)
        }

        consultant_options.setOnClickListener {
            val nextAction = OptionsFragmentDirections.accountCreateAction()
            nextAction.setRole(2)
            Navigation.findNavController(it).navigate(nextAction)
        }

        manager.setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)
            intent.putExtra("role", 5)
            context!!.startActivity(intent)
            activity!!.finishAffinity()
        }
    }
}