package com.agnelselvan.myapplication

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity(){
    private lateinit var mAuth: FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getWindow().setStatusBarColor(getResources().getColor(R.color.ColorGray ))

        mAuth = FirebaseAuth.getInstance()
        var user = mAuth.currentUser;

        Handler(Looper.getMainLooper()).postDelayed({
            if (user == null) {
                var signInIntent = Intent(this, SignInActivity::class.java)
                startActivity(signInIntent);
            } else {
                var dashboardIntent = Intent(this, DashboardActivity::class.java)
                startActivity(dashboardIntent)
            }
        }, 2000)

    }
}