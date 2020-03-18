package com.example.profitclub.ui.chats.chat2

import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.profitclub.adapters.MessageFileListAdapter
import com.example.profitclub.databinding.FragmentChatsView2Binding
import com.example.profitclub.model.File
import com.example.profitclub.toast
import com.example.profitclub.ui.chats.ChatViewActivity
import kotlinx.android.synthetic.main.fragment_chats_view_2.*
import java.util.jar.Manifest

class ChatsView2Fragment : Fragment(), View.OnClickListener {
    private var filePath: Uri? = null
    private val REQUEST_CODE = 101

    private lateinit var vm: ChatsView2Model
    private lateinit var binding: FragmentChatsView2Binding
    private var adapter: MessageFileListAdapter? = null
    private var layoutManager: LinearLayoutManager? = null
    private val REQUEST_PICK = 100

    val list = listOf(
       File("ProfitClub.doc", "13:30", 1), File("ProfitClub.doc", "13:30", 2),
        File("ProfitClub.doc", "13:30", 1), File("ProfitClub.doc", "13:30", 2),
        File("ProfitClub.doc", "13:30", 2), File("ProfitClub.doc", "13:30", 1),
        File("ProfitClub.doc", "13:30", 1), File("ProfitClub.doc", "13:30", 2)
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatsView2Binding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupPermissions()

        activity?.let {
            vm =
                ViewModelProviders.of(this).get(ChatsView2Model::class.java)


            adapter = MessageFileListAdapter(this.context!!, list, this)
            layoutManager = LinearLayoutManager(this.context!!, LinearLayoutManager.VERTICAL, false)
            binding.messagesList.layoutManager = layoutManager
            binding.messagesList.adapter = adapter
            adapter?.notifyDataSetChanged()

            val activity = activity as ChatViewActivity?
            val myDataFromActivity = activity!!.getMyDataChat()

            if(myDataFromActivity == 2 || myDataFromActivity == 3){
                binding.attach.isVisible = false
            }

            attach.setOnClickListener {
                fileOpen()
            }
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
       // super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode){
            REQUEST_CODE ->  if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Log.i(TAG, "Permission has been denied by user")
            } else {
                Log.i(TAG, "Permission has been granted by user")
                activity!!.finish()
            }
        }
    }

    private fun fileOpen() {
        val intent = Intent()
        intent.type = "*/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, REQUEST_PICK)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_PICK && resultCode == Activity.RESULT_OK
            && data != null && data.data != null){
            filePath = data.data
            toast(filePath.toString())
        }
    }

    override fun onClick(p0: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun setupPermissions() {
        val permission = ContextCompat.checkSelfPermission(activity!!,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "Permission to record denied")
            makeRequest()
        }
    }

    private fun makeRequest() {
        ActivityCompat.requestPermissions(activity!!,
            arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
            REQUEST_CODE)
    }
}
/*    private fun setupPermissions() {
      val permission = ContextCompat.checkSelfPermission(activity!!,
          android.Manifest.permission.WRITE_EXTERNAL_STORAGE)

      if (permission != PackageManager.PERMISSION_GRANTED) {
          Log.i(TAG, "Permission to record denied")
          if (ActivityCompat.shouldShowRequestPermissionRationale(activity!!,
                  android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
              val builder = AlertDialog.Builder(activity!!)
              builder.setMessage("Permission to access the microphone is required for this app to record audio.")
                      .setTitle("Permission required")
                          builder.setPositiveButton("OK"
                          ) { dialog, id ->
                      Log.i(TAG, "Clicked")
                      makeRequest()
                  }

                  val dialog = builder.create()
              dialog.show()
          } else {
              makeRequest()
          }
      }
  }﻿*/