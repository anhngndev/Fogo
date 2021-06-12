package com.example.fogo.ui.home

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.fogo.R
import com.example.fogo.databinding.ActivityHomeBinding
import com.example.fogo.ui.favorite.FavoriteFragment
import com.example.fogo.ui.profile.ProfileFragment
import com.example.fogo.ui.search.SearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {

    lateinit var binding: ActivityHomeBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)


        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.container
        ) as NavHostFragment
        navController = navHostFragment.navController

        // Setup the bottom navigation view with navController
        binding.navigation.setupWithNavController(navController)

        initView()
        setAction()


//        binding.navigation.visibility = View.VISIBLE

    }

    private fun setAction() {

//        binding.navigation.setOnNavigationItemSelectedListener {
//
            binding.navigation.itemIconTintList =
                ContextCompat.getColorStateList(this, R.color.item_bottom_navigation_color)
            binding.navigation.itemBackground =
                ContextCompat.getDrawable(this, R.drawable.item_bottom_navigation_background)
//
//            when (it.itemId) {
//                R.id.home -> {
//                    supportFragmentManager.beginTransaction().setCustomAnimations(
//                        enter,
//                        exit,
//                        popEnter,
//                        popExit
//                    )
//                        .replace(R.id.container, HomeFragment.newInstance(), "home")
//                        .addToBackStack("home").commit()
//                }
//                R.id.search -> {
//                    supportFragmentManager.beginTransaction().setCustomAnimations(
//                        enter,
//                        exit,
//                        popEnter,
//                        popExit
//                    )
//                        .replace(R.id.container, SearchFragment.newInstance(), "search")
//                        .addToBackStack("search").commit()
//                }
//                R.id.favorite -> {
//                    if (supportFragmentManager.getBackStackEntryAt(supportFragmentManager.backStackEntryCount - 1).name != "detail new")
//                        supportFragmentManager.beginTransaction().setCustomAnimations(
//                            enter,
//                            exit,
//                            popEnter,
//                            popExit
//                        )
//                            .replace(R.id.container, FavoriteFragment.newInstance(), "favorite")
//                            .addToBackStack("favorite").commit()
//                }
//                R.id.profile -> {
//                    supportFragmentManager.beginTransaction().setCustomAnimations(
//                        enter,
//                        exit,
//                        popEnter,
//                        popExit
//                    )
//                        .replace(R.id.container, ProfileFragment.newInstance(), "profile")
//                        .addToBackStack("profile").commit()
//                }
//            }
//            true
//        }
//        binding.navigation.selectedItemId = R.id.home
//

    }

    private fun initView() {


    }
    fun saveEmail(email: String) {
        val sharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putString("email", email)
            apply()
        }
    }

    val enter = R.animator.slide_in
    val exit = R.animator.fade_out
    val popEnter = R.animator.fade_in
    val popExit = R.animator.slide_out
    var handler = Handler()

    var showNavigation= Runnable{
        binding.navigation.visibility = View.VISIBLE
        binding.shadowBottom.visibility = View.VISIBLE
    }

//    override fun onBackPressed() {
//        when (supportFragmentManager.getBackStackEntryAt(supportFragmentManager.backStackEntryCount - 1).name) {
//            "detail room" -> {
//                supportFragmentManager.popBackStack()
//                handler.post(showNavigation)
//            }
//            "favorite" -> {
//                super.onBackPressed()
//            }
//            "search" -> {
//                super.onBackPressed()
//                handler.post(showNavigation)
//            }
//            "home" -> {
//                val builder = AlertDialog.Builder(this@HomeActivity)
//                builder.setTitle("TRADIX")
//                builder.setMessage("Do you want to log out?")
//                    .setCancelable(false)
//                    .setPositiveButton("Yes") { dialog, id -> finish() }
//                    .setNegativeButton("No") { dialog, id -> dialog.cancel() }
//                val alert = builder.create()
//                alert.show()
//            }
//            "profile" -> {
//                super.onBackPressed()
//            }
//        }
//
//        when (supportFragmentManager.getBackStackEntryAt(supportFragmentManager.backStackEntryCount - 1).name) {
//
//            "search" -> {
//                binding.navigation.selectedItemId = R.id.search
//            }
//            "home" -> {
//                binding.navigation.selectedItemId = R.id.home
//            }
//            "favorite" -> {
//                binding.navigation.selectedItemId = R.id.favorite
//            }
//            "profile" -> {
//                binding.navigation.selectedItemId = R.id.profile
//            }
//        }
//    }
}

