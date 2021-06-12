package com.example.fogo.ui.splash


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import com.example.fogo.R
import com.example.fogo.ui.login.LoginActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(runnable, 3000)

    }

    var handler = Handler()
    var runnable = Runnable {
        startActivity(Intent(this, LoginActivity::class.java))
    }
}