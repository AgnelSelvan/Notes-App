package com.agnelselvan.myapplication

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.agnelselvan.myapplication.DB.DatabaseHandler
import com.agnelselvan.myapplication.Models.Notes
import kotlinx.android.synthetic.main.fragment_create_note.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class CreateNoteFragment : BaseFragment() {
    var currentDate: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let{

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_note, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
                CreateNoteFragment().apply {
                    arguments = Bundle().apply {
                    }
                }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sdf = SimpleDateFormat("dd/MM/yyyy hh:mm:ss")
        currentDate = sdf.format(Date())
        tvDateTime.text = currentDate
        imgDone.setOnClickListener{
            saveNote()
        }
        imgBack.setOnClickListener{
            replaceFragment(HomeFragment.newInstance(), true)
        }
    }

    private fun saveNote(){
        if(noteTitle.text.isNullOrEmpty()){
            Toast.makeText(context, "Title Required", Toast.LENGTH_SHORT).show()
        }
        else if(noteSubTitle.text.isNullOrEmpty()){
            Toast.makeText(context, "Sub Title Required", Toast.LENGTH_SHORT).show()
        }
        else if(noteDesc.text.isNullOrEmpty()){
            Toast.makeText(context, "Note Required", Toast.LENGTH_SHORT).show()
        }
        else{
            val context: Context = this.requireContext();
            launch {
                var notes = Notes(noteTitle.text.toString(), noteSubTitle.text.toString(), currentDate.toString(), noteDesc.text.toString(), "", "", ""  );
                var db = DatabaseHandler(context)
                db?.insertNote(notes)
            }
        }
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