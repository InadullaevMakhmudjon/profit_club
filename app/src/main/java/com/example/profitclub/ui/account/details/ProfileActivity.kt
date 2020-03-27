package com.example.profitclub.ui.account.details

import android.app.Notification
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.example.profitclub.App
import com.example.profitclub.LocaleManager
import com.example.profitclub.R
import com.example.profitclub.adapters.BidsAdapter
import com.example.profitclub.databinding.ActivityProfileBinding
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_profile.*
import kotlin.math.abs

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding

    private var appBarExpanded: Boolean = true
    private var collapsedMenu: Menu? = null
    private lateinit var appBarLayout: AppBarLayout
    private lateinit var notificationManager: NotificationManagerCompat


    private var mSectionPageAdapter: SectionPageAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)

        setSupportActionBar(anim_toolbar)
        if(supportActionBar != null)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        when(this.intent.getIntExtra("role", 1)){
            2 -> binding.hireMe.isVisible = true

        }

        mSectionPageAdapter = SectionPageAdapter(supportFragmentManager!!)
        binding.viewPager!!.adapter = mSectionPageAdapter
        binding.pagerHeader.setTabIndicatorColorResource(R.color.colorAccent2)
        notificationManager = NotificationManagerCompat.from(this)

        binding.hireMe.setOnClickListener {
            notification()
            Toast.makeText(applicationContext, "Wait until the Consultant confirms your request", Toast.LENGTH_SHORT).show()
            finish()
        }

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
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        if(collapsedMenu != null && (!appBarExpanded || collapsedMenu!!.size() != 1)){
            collapsedMenu!!.add("Add")
                .setIcon(R.drawable.ic_edit_black_24dp)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)
        } else{
            //expanded
        }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_view, menu)
        collapsedMenu = menu
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId){
            /*R.id.home -> {
                finish()
                return true
            }*/
            R.id.action_view -> return true
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

    private fun notification(){

        val notification = NotificationCompat.Builder(this, App.CHANNEL_1_ID)
            .setSmallIcon(R.drawable.profile_user)
            .setContentTitle("You were hired!")
            .setContentText("You were hired!")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .build()

        notificationManager.notify(1, notification)
    }

    @Override
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocaleManager.setLocale(base))
    }
}
