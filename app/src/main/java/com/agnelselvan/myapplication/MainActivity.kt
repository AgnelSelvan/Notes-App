package com.agnelselvan.myapplication

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
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
    private fun replaceFragment(fragment: Fragment, isTransition: Boolean ){
        val fragmentTransition = supportFragmentManager.beginTransaction()

        if(isTransition){
            fragmentTransition.setCustomAnimations(android.R.anim.slide_out_right, android.R.anim.slide_in_left)

        }
        fragmentTransition.replace(R.id.frame_layout, fragment).addToBackStack(fragment.javaClass.simpleName)
        fragmentTransition.commit()

    }
}