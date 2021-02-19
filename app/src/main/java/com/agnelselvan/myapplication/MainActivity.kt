package com.agnelselvan.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity(){
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()
        var user = mAuth.currentUser;

        Handler(Looper.getMainLooper()).postDelayed({
            if(user == null){
                var signInIntent = Intent(this, SignInActivity::class.java )
                startActivity(signInIntent);
            }
            else{
                var dashboardIntent = Intent(this, DashboardActivity::class.java)
                startActivity(dashboardIntent)
            }
        }, 2000)

    }
}