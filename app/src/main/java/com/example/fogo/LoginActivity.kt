package com.example.fogo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog


class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    override fun onResume() {
        super.onResume()
        supportFragmentManager.beginTransaction().replace(R.id.container, LoginFragment.newInstance()).addToBackStack("log in").commit()
    }

    override fun onBackPressed() {
        when(supportFragmentManager.getBackStackEntryAt(supportFragmentManager.backStackEntryCount -1).name){
            "log in" -> {
                val builder =
                    AlertDialog.Builder(this@LoginActivity)
                builder.setTitle("Fogo")
                builder.setMessage("Do you want to log out?")
                    .setCancelable(false)
                    .setPositiveButton(
                        "Yes"
                    ) { dialog, id -> finish() }
                    .setNegativeButton(
                        "No"
                    ) { dialog, id -> dialog.cancel() }
                val alert = builder.create()
                alert.show()
            }
            "sign up" ->{
                supportFragmentManager.popBackStack()
            }
        }
    }
}