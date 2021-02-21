package com.agnelselvan.myapplication.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.agnelselvan.myapplication.Models.Notes
import com.agnelselvan.myapplication.R
import kotlinx.android.synthetic.main.item_rv_notes.view.*

class NotesAdapter() : RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {
    var listener:OnItemClickListener? = null
    var arrList = ArrayList<Notes>()

    fun setData(arrNotesList: List<Notes>){
        arrList = arrNotesList as ArrayList<Notes>
    }

    fun setOnClickListener(listener1 : OnItemClickListener){
        listener = listener1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_rv_notes, parent, false)
        );
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.itemView.tvTitle.text = arrList[position].title
        holder.itemView.tvDesc.text = arrList[position].noteText
        holder.itemView.tvDateTime.text = arrList[position].datetime
        println(arrList[position])
        holder.itemView.notesCardView.setCardBackgroundColor(Color.parseColor(arrList[position].color))
//        if(arrList[position].color != "" || arrList[position].color != null){
//            holder.itemView.notesCardView.setCardBackgroundColor(Color.parseColor(arrList[position].color))
//        }
//        else{
//            holder.itemView.notesCardView.setCardBackgroundColor(Color.parseColor(R.color.ColorLightBlack.toString()))
//        }
        if(arrList[position].webLink == "" || arrList[position].webLink == null ){
            holder.itemView.tvWebUrl.visibility = View.GONE
        }
        else{
            holder.itemView.tvWebUrl.text = arrList[position].webLink
            holder.itemView.tvWebUrl.visibility = View.VISIBLE
        }

        holder.itemView.notesCardView.setOnClickListener {
            listener!!.onClicked(arrList[position])

        }

    }

    override fun getItemCount(): Int {
        return arrList.size
    }


    class NotesViewHolder(view: View): RecyclerView.ViewHolder(view){

    }

    interface OnItemClickListener{
        fun onClicked(notesModel: Notes){}
        
    }

}