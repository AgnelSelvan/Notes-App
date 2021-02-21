package com.agnelselvan.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.agnelselvan.myapplication.DB.DatabaseHandler
import com.agnelselvan.myapplication.Models.Notes
import com.agnelselvan.myapplication.adapter.NotesAdapter
import kotlinx.android.synthetic.main.fragment_create_note.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment(){
    var notesAdapter: NotesAdapter = NotesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let{

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
                HomeFragment().apply {
                    arguments = Bundle().apply {
                    }
                }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler_view.setHasFixedSize(true)
        recycler_view.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        launch {
            var db = context?.let { DatabaseHandler(it) }
            var notes: ArrayList<Notes>? = db?.readNotes()
            if (notes != null) {
                notesAdapter!!.setData(notes)
                recycler_view.adapter = notesAdapter
            }

        }
        notesAdapter!!.setOnClickListener(onClicked)
        fabBtnCreateNote.setOnClickListener{
            replaceFragment( CreateNoteFragment.newInstance(), false )
        }
    }

    private val onClicked = object: NotesAdapter.OnItemClickListener{
        override fun onClicked(notesModel: Notes) {
            var fragment: Fragment
            var bundle = Bundle()
            bundle.putString("edit", "isEdit")
            bundle.putInt("noteId", notesModel.id!!)
            fragment = CreateNoteFragment.newInstance()
            fragment.arguments = bundle
            replaceFragment(fragment, false)
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