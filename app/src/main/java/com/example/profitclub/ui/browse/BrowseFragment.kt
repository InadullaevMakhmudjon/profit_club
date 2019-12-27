package com.example.profitclub.ui.browse

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.profitclub.R

class BrowseFragment : Fragment() {

    private lateinit var sendViewModel: BrowseViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sendViewModel =
            ViewModelProviders.of(this).get(BrowseViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_browse, container, false)
        //val textView: TextView = root.findViewById(R.id.text_send)
        /*sendViewModel.text.observe(this, Observer {
           // textView.text = it
        })*/
        var mainGrid = root.findViewById(R.id.mainGrid) as GridLayout
        setSingleEvent(mainGrid)

        return root
    }

    private fun setSingleEvent(mainGrid: GridLayout) {
        for(i in 0 until mainGrid.childCount){
            var cardview = mainGrid.getChildAt(i) as CardView
            cardview.setOnClickListener {
                startActivity(Intent(context, BrowseQuestionsActivity::class.java))
            }
        }

    }
}