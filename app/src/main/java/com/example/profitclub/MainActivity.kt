package com.example.profitclub

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
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
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainActivityViewModel
    private lateinit var preferences: SharedPreferences
    private val APP_PREFERENCE = "MYSETTINGS"

    //private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private var role: Int = 0

    @SuppressLint("ShowToast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        preferences = getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE)
        mainViewModel = ViewModelProviders.of(this, MainActivityViewModelFactory(preferences)).get(MainActivityViewModel::class.java)

        mainViewModel.token.observe(this, Observer {token ->
            // TODO Logic here whether token is exist or no
            if(token != null) {
                Toast.makeText(this, token, Toast.LENGTH_LONG)
            } else {
                Toast.makeText(this, "TOken is not exist", Toast.LENGTH_LONG)
            }
        })

        // Error Observer
        mainViewModel.error.observe(this, Observer {error ->
            if(error.isNotEmpty()) {
                Toast.makeText(this, error, Toast.LENGTH_LONG).show()
            }
        })
        mainViewModel.loading.observe(this, Observer { isLoading ->
            // TODO Logic for loading progress
        })


        //setSupportActionBar(binding.toolbar)
        this.role = this.intent.getIntExtra("role", 1)

        val myNavHostFragment: NavHostFragment = face as NavHostFragment
        val inflater = myNavHostFragment.navController.navInflater
        navController = Navigation.findNavController(this, R.id.face)

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

            if(role == 1 || role == 2){
                bottom_bar.menu.removeItem(R.id.bids)
            }
            if(role == 3 || role == 4){
                bottom_bar.menu.removeItem(R.id.questions)
            }
        }
    }
    fun getMyData(): Int {
        return role
    }

    @Override
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocaleManager.setLocale(base))
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

