package com.example.fogo.home

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.fogo.R
import com.example.fogo.utils.HandlerManager
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {


//    private val homeFragment = HomeFragment.newInstance()
//    private val searchFragment = SearchFragment.newInstance()
//    private val favoriteFragment = FavoriteFragment.newInstance()
//    private val profileFragment = HomeFragment.newInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initView()
        setAction()

    }

    private fun setAction() {

        bottomNavigationView.setOnNavigationItemSelectedListener {

            bottomNavigationView.itemIconTintList =
                ContextCompat.getColorStateList(this, R.color.item_bottom_navigation_color)
            bottomNavigationView.itemBackground =
                ContextCompat.getDrawable(this, R.drawable.item_bottom_navigation_background)

            when (it.itemId) {
                R.id.home -> {
                    supportFragmentManager.beginTransaction().setCustomAnimations(
                        enter,
                        exit,
                        popEnter,
                        popExit
                    )
                        .replace(R.id.container, HomeFragment.newInstance(), "home")
                        .addToBackStack("home").commit()
                }
                R.id.search -> {
                    supportFragmentManager.beginTransaction().setCustomAnimations(
                        enter,
                        exit,
                        popEnter,
                        popExit
                    )
                        .replace(R.id.container, SearchFragment.newInstance(), "search")
                        .addToBackStack("search").commit()
                }
                R.id.favorite -> {
                    if (supportFragmentManager.getBackStackEntryAt(supportFragmentManager.backStackEntryCount - 1).name != "detail new")
                        supportFragmentManager.beginTransaction().setCustomAnimations(
                            enter,
                            exit,
                            popEnter,
                            popExit
                        )
                            .replace(R.id.container, FavoriteFragment.newInstance(), "favorite")
                            .addToBackStack("favorite").commit()
                }
                R.id.profile -> {
                    supportFragmentManager.beginTransaction().setCustomAnimations(
                        enter,
                        exit,
                        popEnter,
                        popExit
                    )
                        .replace(R.id.container, ProfileFragment.newInstance(), "profile")
                        .addToBackStack("profile").commit()
                }
            }
            true
        }
        bottomNavigationView.selectedItemId = R.id.home


    }

    private fun initView() {

        bottomNavigationView = findViewById(R.id.navigation)
        shadowBottom = findViewById(R.id.shadow_bottom)

    }
    fun saveEmail(email: String) {
        val sharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putString("email", email)
            apply()
        }
    }

    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var shadowBottom: View

    val enter = R.animator.slide_in
    val exit = R.animator.fade_out
    val popEnter = R.animator.fade_in
    val popExit = R.animator.slide_out
    var handler = Handler()

    var showNavigation= Runnable{
        bottomNavigationView.visibility = View.VISIBLE
        shadowBottom.visibility = View.VISIBLE
    }

    override fun onBackPressed() {
//        Log.d(TAG, "onBackPressed: " +supportFragmentManager.getBackStackEntryAt(supportFragmentManager.backStackEntryCount - 1).name
//          + supportFragmentManager.getBackStackEntryAt(supportFragmentManager.backStackEntryCount - 2).name)
        when (supportFragmentManager.getBackStackEntryAt(supportFragmentManager.backStackEntryCount - 1).name) {
            "detail room" -> {
                supportFragmentManager.popBackStack()
                handler.post(showNavigation)
            }
            "favorite" -> {
                super.onBackPressed()
            }
            "search" -> {
                super.onBackPressed()
                handler.post(showNavigation)
            }
            "home" -> {
                val builder = AlertDialog.Builder(this@HomeActivity)
                builder.setTitle("TRADIX")
                builder.setMessage("Do you want to log out?")
                    .setCancelable(false)
                    .setPositiveButton("Yes") { dialog, id -> finish() }
                    .setNegativeButton("No") { dialog, id -> dialog.cancel() }
                val alert = builder.create()
                alert.show()
            }
            "profile" -> {
                super.onBackPressed()
            }
        }

        when (supportFragmentManager.getBackStackEntryAt(supportFragmentManager.backStackEntryCount - 1).name) {

            "search" -> {
                bottomNavigationView.selectedItemId = R.id.search
            }
            "home" -> {
                bottomNavigationView.selectedItemId = R.id.home
            }
            "favorite" -> {
                bottomNavigationView.selectedItemId = R.id.favorite
            }
            "profile" -> {
                bottomNavigationView.selectedItemId = R.id.profile
            }
        }
    }
}

