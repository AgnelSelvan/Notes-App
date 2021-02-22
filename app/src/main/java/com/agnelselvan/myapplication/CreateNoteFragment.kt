package com.agnelselvan.myapplication

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Patterns
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
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class CreateNoteFragment : BaseFragment() {
    var currentDate: String? = null
    var selectedColor: String? = "#FBFBFB"
    private var webUrl = ""
    private var noteId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        noteId = requireArguments().getInt("noteId", -1)!!
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

        if(noteId != -1){
            launch {
                var db = context?.let { DatabaseHandler(it) }
                var notes: Notes? = db?.getNoteById(noteId)
                if (notes != null) {
                    selectedColor = notes.color
                    webUrl = notes.webLink.toString()
                    noteTitle.setText(notes.title)
                    colorView.setBackgroundColor(Color.parseColor(notes.color))
                    noteSubTitle.setText(notes.subTitle)
                    noteDesc.setText(notes.noteText)
                    if(notes.webLink != "" || notes.webLink != null){
                        tvWebUrl.text = notes.webLink
                        tvWebUrl.visibility = View.VISIBLE
                    }
                    else{
                        tvWebUrl.visibility = View.GONE
                    }
                }

            }
        }

        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(
                BroadcastReceiver, IntentFilter("bottom_sheet_action")
        )

        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm")
        currentDate = sdf.format(Date())
        tvDateTime.text = currentDate

        colorView.setBackgroundColor(Color.parseColor(selectedColor))


        imgDone.setOnClickListener{
            if(noteId != -1){
                updateNote()
            }
            else{
                saveNote()
            }
        }
        imgBack.setOnClickListener{
            requireActivity().supportFragmentManager.popBackStack()
        }

        imgMore.setOnClickListener{
            var noteBottomSheetFragment = NoteBottomSheetFragment.newInstance(noteId)
            noteBottomSheetFragment.show(requireActivity().supportFragmentManager, "Note Bottom Sheet Fragment")
        }
        okayBtn.setOnClickListener{
            if(noteWebUrl.text.toString().trim().isNotBlank()){
                checkWebUrl()
            }
            else{
                Toast.makeText(requireContext(), "Url is required", Toast.LENGTH_SHORT).show()
            }
        }
        cancelBtn.setOnClickListener{
            layoutWebUrl.visibility = View.GONE
        }

        tvWebUrl.setOnClickListener{
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse(noteWebUrl.text.toString()))
            startActivity(intent)
        }
    }

    private fun updateNote(){
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
                notes.id = noteId
                notes.title = noteTitle.text.toString()
                notes.subTitle = noteSubTitle.text.toString()
                notes.datetime = currentDate.toString()
                notes.noteText = noteDesc.text.toString()
                notes.color = selectedColor
                notes.webLink = webUrl
                var db = DatabaseHandler(context)
                notes?.let {
                    db?.updateNote(it)
                    noteTitle.setText("")
                    noteSubTitle.setText("")
                    noteDesc.setText("")
                    tvWebUrl.visibility = View.GONE
                    requireActivity().supportFragmentManager.popBackStack()
                }

            }
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
                notes.webLink = webUrl
                var db = DatabaseHandler(context)
                notes?.let {
                    db?.insertNote(it)
                    noteTitle.setText("")
                    noteSubTitle.setText("")
                    noteDesc.setText("")
                    tvWebUrl.visibility = View.GONE
                    requireActivity().supportFragmentManager.popBackStack()
                }

            }
        }
    }


    private val BroadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            var actionColor = intent!!.getStringExtra("action")
            when (actionColor!!){
                "Blue" -> {
                    selectedColor = intent.getStringExtra("selectedColor")
                    colorView.setBackgroundColor(Color.parseColor(selectedColor))
                    okayBtn.setBackgroundColor(Color.parseColor(selectedColor))
                    cancelBtn.setBackgroundColor(Color.parseColor(selectedColor))
                }
                "Yellow" -> {
                    selectedColor = intent.getStringExtra("selectedColor")
                    colorView.setBackgroundColor(Color.parseColor(selectedColor))
                    okayBtn.setBackgroundColor(Color.parseColor(selectedColor))
                    cancelBtn.setBackgroundColor(Color.parseColor(selectedColor))
                }

                "Purple" -> {
                    selectedColor = intent.getStringExtra("selectedColor")
                    colorView.setBackgroundColor(Color.parseColor(selectedColor))
                    okayBtn.setBackgroundColor(Color.parseColor(selectedColor))
                    cancelBtn.setBackgroundColor(Color.parseColor(selectedColor))
                }

                "Orange" -> {
                    selectedColor = intent.getStringExtra("selectedColor")
                    colorView.setBackgroundColor(Color.parseColor(selectedColor))
                    okayBtn.setBackgroundColor(Color.parseColor(selectedColor))
                    cancelBtn.setBackgroundColor(Color.parseColor(selectedColor))
                }

                "Green" -> {
                    selectedColor = intent.getStringExtra("selectedColor")
                    colorView.setBackgroundColor(Color.parseColor(selectedColor))
                    okayBtn.setBackgroundColor(Color.parseColor(selectedColor))
                    cancelBtn.setBackgroundColor(Color.parseColor(selectedColor))
                }
                "WebUrl" -> {
                    layoutWebUrl.visibility = View.VISIBLE
                }
                "DeleteNote" -> {
                    deleteNote()
                }
                else -> {
                    layoutWebUrl.visibility = View.VISIBLE
                    selectedColor = intent.getStringExtra("selectedColor")
                    colorView.setBackgroundColor(Color.parseColor(selectedColor))
                }

            }
        }

    }

    private fun deleteNote(){
        val context: Context = this.requireContext();
        var db = DatabaseHandler(context)
        launch {
            db?.deleteNote(noteId)
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    private fun checkWebUrl(){
        if(Patterns.WEB_URL.matcher(noteWebUrl.text.toString()).matches()){
            layoutWebUrl.visibility = View.GONE
            noteWebUrl.isEnabled = false
            webUrl = noteWebUrl.text.toString()
            tvWebUrl.visibility = View.VISIBLE
            tvWebUrl.setText(webUrl.toString())
        }
        else{
            Toast.makeText(requireContext(), "Url is not valid", Toast.LENGTH_SHORT ).show()
        }
    }


    override fun onDestroy() {
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(BroadcastReceiver)
        super.onDestroy()
    }

}








