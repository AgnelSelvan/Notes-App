package com.agnelselvan.myapplication

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.agnelselvan.myapplication.DB.DatabaseHandler
import com.agnelselvan.myapplication.Models.Notes
import com.agnelselvan.myapplication.utils.NoteBottomSheetFragment
import kotlinx.android.synthetic.main.fragment_create_note.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class CreateNoteFragment : BaseFragment() {
    var currentDate: String? = null
    var selectedColor: String? = "#171C26"

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

        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(
                BroadcastReceiver, IntentFilter("bottom_sheet_action")
        )

        val sdf = SimpleDateFormat("dd/MM/yyyy hh:mm:ss")
        currentDate = sdf.format(Date())
        tvDateTime.text = currentDate

        colorView.setBackgroundColor(Color.parseColor(selectedColor))

        imgDone.setOnClickListener{
            saveNote()
        }
        imgBack.setOnClickListener{
            requireActivity().supportFragmentManager.popBackStack()
        }

        imgMore.setOnClickListener{
            var noteBottomSheetFragment = NoteBottomSheetFragment.newInstance()
            noteBottomSheetFragment.show(requireActivity().supportFragmentManager, "Note Bottom Sheet Fragment")
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
                var notes = Notes();
                notes.title = noteTitle.text.toString()
                notes.subTitle = noteSubTitle.text.toString()
                notes.datetime = currentDate.toString()
                notes.noteText = noteDesc.text.toString()
                notes.color = selectedColor
                var db = DatabaseHandler(context)
                notes?.let {
                    db?.insertNote(it)
                    noteTitle.setText("")
                    noteSubTitle.setText("")
                    noteDesc.setText("")
                }

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

    private val BroadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            var actionColor = intent!!.getStringExtra("actionColor")
            when (actionColor!!){
                "Blue" -> {
                    selectedColor = intent.getStringExtra("selectedColor")
                    colorView.setBackgroundColor(Color.parseColor(selectedColor))
                }
                "Yellow" -> {
                    selectedColor = intent.getStringExtra("selectedColor")
                    colorView.setBackgroundColor(Color.parseColor(selectedColor))
                }

                "Purple" -> {
                    selectedColor = intent.getStringExtra("selectedColor")
                    colorView.setBackgroundColor(Color.parseColor(selectedColor))
                }

                "Orange" -> {
                    selectedColor = intent.getStringExtra("selectedColor")
                    colorView.setBackgroundColor(Color.parseColor(selectedColor))
                }

                "Green" -> {
                    selectedColor = intent.getStringExtra("selectedColor")
                    colorView.setBackgroundColor(Color.parseColor(selectedColor))
                }
                else -> {
                    selectedColor = intent.getStringExtra("selectedColor")
                    colorView.setBackgroundColor(Color.parseColor(selectedColor))
                }

            }
        }

    }


    override fun onDestroy() {
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(BroadcastReceiver)
        super.onDestroy()
    }

}








