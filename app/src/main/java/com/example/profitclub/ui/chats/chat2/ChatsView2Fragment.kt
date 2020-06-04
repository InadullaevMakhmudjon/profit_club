package com.example.profitclub.ui.chats.chat2

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.loader.content.CursorLoader
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.profitclub.adapters.MessageFileListAdapter
import com.example.profitclub.databinding.FragmentChatsView2Binding
import com.example.profitclub.getImagePath
import com.example.profitclub.model.File
import com.example.profitclub.toast
import com.example.profitclub.ui.chats.ChatViewActivity
import kotlinx.android.synthetic.main.fragment_chats_view_2.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody


class ChatsView2Fragment : Fragment(), View.OnClickListener {
    private var filePath: Uri? = null
    private val REQUEST_CODE = 101
    private val FILE_SELECT_CODE = 0

    private lateinit var vm: ChatsView2Model
    private lateinit var binding: FragmentChatsView2Binding
    private var adapter: MessageFileListAdapter? = null
    private var layoutManager: LinearLayoutManager? = null
    private val REQUEST_PICK = 100
    private val APP_PREFERENCE = "MYSETTINGS"
    private var questionId = 0
    private val allList = arrayListOf<File>()

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
            val preferences = activity!!.getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE)
            vm =
                ViewModelProviders.of(this, ChatsView2ModelFactory(activity!!.application, preferences)).get(ChatsView2Model::class.java)

            questionId = (activity as ChatViewActivity).getMyQusetionId()

            adapter = MessageFileListAdapter(this.context!!, allList, this)
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

            vm.getData(questionId)

            vm.allFiles.observe(viewLifecycleOwner, Observer { data ->
                allList.clear()
                adapter?.notifyDataSetChanged()
            })

            vm.clientFiles.observe(viewLifecycleOwner, Observer { data ->
                if(data != null) {
                    allList.addAll(data.map { el -> File(el.original_name, el.createdate, 1) })
                    adapter?.notifyDataSetChanged()
                }
            })

            vm.consultantFiles.observe(viewLifecycleOwner, Observer { data ->
                if(data != null) {
                    allList.addAll(data.map { el -> File(el.original_name, el.createdate, 1) })
                    adapter?.notifyDataSetChanged()
                }
            })

            vm.error.observe(viewLifecycleOwner, Observer { msg ->
                toast(msg)
            })

            activity.getSocket.on("update_file_${questionId}") { arg ->
                activity.runOnUiThread {
                    Toast.makeText(activity.applicationContext,"File come", Toast.LENGTH_LONG).show()
                }
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
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "*/*"
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        toast("Coming soon...")
        /*
        try {
            startActivityForResult(
                Intent.createChooser(intent, "Select a File to Upload"),
                FILE_SELECT_CODE
            )
        } catch (ex: ActivityNotFoundException) {
            // Potentially direct the user to the Market with a Dialog
            toast("Please install a File Manager.")
        }

        startActivityForResult(intent, REQUEST_PICK)
        */
    }

    // For getting file path
    private fun getRealPathFromURI(contentUri: Uri): String? {
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val loader = CursorLoader(activity!!.applicationContext, contentUri, proj, null, null, null)
        val cursor = loader.loadInBackground()
        if(cursor!=null) {
            val column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            val result = cursor.getString(column_index)
            cursor.close()
            return result
        } else{
            return "errored"
        }
    }

    // Uploading on server
    private fun upload(path: String) {
        val file = java.io.File(path)
        val reqFile = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("file", file.name, reqFile)
        val id = "$questionId".toRequestBody("multipart/form-data".toMediaTypeOrNull())
        vm.upload(body, id) {
            toast("Saved")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            FILE_SELECT_CODE -> if (resultCode == Activity.RESULT_OK) {
                // Get the path
                val uriimage = data?.data
                if(uriimage!=null) {
                    val file = java.io.File(uriimage.toString())
                    // upload(file.path)
                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data)
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
  }*/