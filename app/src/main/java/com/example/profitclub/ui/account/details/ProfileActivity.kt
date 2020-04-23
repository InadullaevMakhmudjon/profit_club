package com.example.profitclub.ui.account.details

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.profitclub.LocaleManager
import com.example.profitclub.R
import com.example.profitclub.data.BASE_URL
import com.example.profitclub.databinding.ActivityProfileBinding
import com.example.profitclub.toast
import com.google.android.material.appbar.AppBarLayout
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_profile.*
import kotlin.math.abs

class ProfileActivity : AppCompatActivity() {
    //private lateinit var notificationManager: NotificationManagerCompat
    private lateinit var binding: ActivityProfileBinding
    private val APP_PREFERENCE = "MYSETTINGS"
    private lateinit var vm: ProfileDetailsViewModel
    private var userId: Int = 0
    private var appBarExpanded: Boolean = true
    private var collapsedMenu: Menu? = null
    private lateinit var appBarLayout: AppBarLayout
    var picture: String? = null // picture

    private var mSectionPageAdapter: SectionPageAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)

        setSupportActionBar(anim_toolbar)
        if(supportActionBar != null)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        mSectionPageAdapter = SectionPageAdapter(supportFragmentManager!!)
        binding.viewPager!!.adapter = mSectionPageAdapter
        binding.pagerHeader.setTabIndicatorColorResource(R.color.colorAccent2)
        //notificationManager = NotificationManagerCompat.from(this)

        val preferences = getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE)
        vm = ViewModelProviders.of(this, ProfileDetailsViewModelFactory(preferences)).get(ProfileDetailsViewModel::class.java)

        vm.getUserInfo()

        vm.userInfo.observe(this, Observer { data ->
             if (data != null){
                 userId = data.user_id
                 picture = data.media_url // Set value to picture
                 if (picture != null){
                     Picasso.get()
                         .load("$BASE_URL${picture}/sm_avatar.jpg").into(binding.logo)
                 }
                 collapsing_toolbar.title = data.fname
                 toast(picture.toString())
             }
        })

        appBarLayout = binding?.appbar!!

        binding?.appbar!!.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            // Vertical offset == 0 indicates appBar is fully expanded.
            if (abs(verticalOffset) > 200) {
                appBarExpanded = false
                invalidateOptionsMenu()
            } else {
                appBarExpanded = true
                invalidateOptionsMenu()
            }
        })

        fab.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED){
                    //permission denied
                    val permissions = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE);
                    //show popup to request runtime permission
                    requestPermissions(permissions, PERMISSION_CODE);
                }
                else{
                    //permission already granted
                    pickImageFromGallery();
                }
            }
            else{
                //system OS is < Marshmallow
                pickImageFromGallery();
            }
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        if(collapsedMenu != null && (!appBarExpanded || collapsedMenu!!.size() != 1)){
            collapsedMenu!!.add("Add")
                .setIcon(R.drawable.ic_camera_alt_black_24dp)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)
        } else{
            //expanded
        }
        return super.onPrepareOptionsMenu(menu)
    }

    /*override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_view, menu)
        collapsedMenu = menu
        return true
    }*/

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId){
            /*R.id.home -> {
                finish()
                return true
            }*/
        }
        if(item.title == "Add"){
            /*Snackbar.make(item.actionView, "Check one of checkboxes only", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()*/
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

  /*  private fun notification(){

        val notification = NotificationCompat.Builder(this, App.CHANNEL_1_ID)
            .setSmallIcon(R.drawable.profile_user)
            .setContentTitle("You were hired!")
            .setContentText("You were hired!")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .build()

        notificationManager.notify(1, notification)
    }*/

    @Override
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocaleManager.setLocale(base))
    }

    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    //handle requested permission result
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                    //permission from popup granted
                    pickImageFromGallery()
                }
                else{
                    //permission from popup denied
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    companion object {
        //image pick code
        private val IMAGE_PICK_CODE = 1000;
        //Permission code
        private val PERMISSION_CODE = 1001;
    }

    //handle result of picked image
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
            vm.deletePhoto(userId, 1)
            val image = data?.data
            binding.logo.setImageURI(image)
            vm.uploadPhoto(userId, 1)
            toast(image.toString())
            picture = image.toString() // Set value to picture from gallery
        }
    }

    // Function returns picture
    fun getPhoto(): String?{
        return picture.toString()
    }
}
