package com.example.profitclub

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.navigation.findNavController
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.*
import com.example.profitclub.databinding.ActivityMainBinding
import com.example.profitclub.splashscreens.SplashScreen
import com.example.profitclub.ui.AuthentificationActivity
import kotlinx.android.synthetic.main.activity_chat_view.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.toolbar
import kotlinx.android.synthetic.main.fragment_splash.*
import kotlinx.android.synthetic.main.main_custom_bar.*
import kotlinx.android.synthetic.main.main_custom_bar.view.*

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainActivityViewModel
    private lateinit var preferences: SharedPreferences
    private val APP_PREFERENCE = "MYSETTINGS"
    private lateinit var titleView: TextView

    //private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    @SuppressLint("ShowToast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setSupportActionBar(toolbar)
        val actionBar = this.supportActionBar

        actionBar?.setDisplayShowCustomEnabled(true)

        val mLayoutInflater: LayoutInflater = this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val actionBarView: View = mLayoutInflater.inflate(R.layout.main_custom_bar, null)

        actionBar?.customView = actionBarView
        titleView = actionBar?.customView?.findViewById<TextView>(R.id.title)!!
        customActionBarTitle(titleView.text.toString())

        //chooseLanguage()

        preferences = getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE)
        mainViewModel = ViewModelProviders.of(this, MainActivityViewModelFactory(preferences)).get(MainActivityViewModel::class.java)

        mainViewModel.token.observe(this, Observer {token ->
            if(token == null) {
                Toast.makeText(this, "Token is not exist", Toast.LENGTH_LONG)
                startActivity(Intent(this, AuthentificationActivity::class.java))
                this.finish()
            }
        })

        // Error Observer
        mainViewModel.error.observe(this, Observer {error ->
            if(error.isNotEmpty()) {
                Toast.makeText(this, error, Toast.LENGTH_LONG).show()
            }
        })

        //setSupportActionBar(binding.toolbar)
        // this.role = this.intent.getIntExtra("role", 1)

        val myNavHostFragment: NavHostFragment = face as NavHostFragment
        val inflater = myNavHostFragment.navController.navInflater
        navController = Navigation.findNavController(this, R.id.face)

        mainViewModel.role.observe(this, Observer { role ->
            if(role != null) {
                if (role == 5){
                    val graph = inflater.inflate(R.navigation.mobile_navigation_manager)
                    myNavHostFragment.navController.graph = graph
                    binding.bottomBar.menu.clear() //clear old inflated items.
                    binding.bottomBar.inflateMenu(R.menu.bottom_nav_view_manager)
                    NavigationUI.setupWithNavController(binding.bottomBar, navController)

                } else{
                    val graph = inflater.inflate(R.navigation.mobile_navigation)
                    myNavHostFragment.navController.graph = graph
                    NavigationUI.setupWithNavController(binding.bottomBar, navController)
                    //binding.bottomBarManager.isVisible = false

                    if(role == 5 || role == 7){
                        bottom_bar.menu.removeItem(R.id.bids)
                    }
                    if(role == 2 || role == 4){
                        bottom_bar.menu.removeItem(R.id.questions)
                    }
                }
            }
        })
    }

    fun getMyData(): Int? {
        return mainViewModel.role.value
    }

    override fun onResume() {
        val icon = getLanguageDrawable(this.getLanguage())
        choose_main_language.setImageDrawable(icon)
        super.onResume()
    }

    @Override
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocaleManager.setLocale(base))
    }

    fun customActionBarTitle(title: String){
        titleView.text = title
    }

   /* override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.language_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.language -> {
                Toast.makeText(this, "asdsdfa", Toast.LENGTH_SHORT).show()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }*/
}

        /*val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)*/

   /* override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }*/

    /*override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }*/

