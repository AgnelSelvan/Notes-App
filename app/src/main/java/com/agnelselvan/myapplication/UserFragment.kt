package com.agnelselvan.myapplication

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_user.*
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


class UserFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let{

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
                UserFragment().apply {
                    arguments = Bundle().apply {
                    }
                }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var mAuth : FirebaseAuth = FirebaseAuth.getInstance()
        var user = mAuth.currentUser;
//        if(user?.photoUrl != null){
//            val bitmap = BitmapFactory.decodeStream(URL(user?.photoUrl.toString()).content as InputStream)
//
//            ivUserImage.setImageBitmap(bitmap)
//        }
//        DownloadImageFromInternet(ivUserImage).execute(user?.photoUrl.toString())
        Log.e("src", user?.photoUrl.toString())
        imgLogout.setOnClickListener {
            mAuth.signOut()
            requireActivity().supportFragmentManager.popBackStack()
            var signInIntent = Intent(context, SignInActivity::class.java)
            startActivity(signInIntent);
        }
        imgBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        tvUserName.setText(user?.displayName)
        tvUserEmail.setText(user?.email)
    }

    private fun replaceFragment(fragment: Fragment, isTransition: Boolean ){
        val fragmentTransition = activity!!.supportFragmentManager.beginTransaction()

        if(isTransition){
            fragmentTransition.setCustomAnimations(android.R.anim.slide_out_right, android.R.anim.slide_in_left)

        }
        fragmentTransition.replace(R.id.frame_layout, fragment).addToBackStack(fragment.javaClass.simpleName)
        fragmentTransition.commit()

    }

}