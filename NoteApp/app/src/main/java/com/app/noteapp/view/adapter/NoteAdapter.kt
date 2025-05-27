package com.app.noteapp.view.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.noteapp.databinding.ItemNoteBinding
import com.app.noteapp.view.ItemNoteActivity
import com.app.noteapp.view.MainActivity
import com.app.noteapp.view.UpdateNoteActivity
import com.app.noteapp.view.model.Note

class NoteAdapter(val activity: MainActivity): RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
   private var noteList = emptyList<Note>()
    class NoteViewHolder( val itemBinding: ItemNoteBinding): RecyclerView.ViewHolder(itemBinding.root)
    fun setNoteList(notList: List<Note>){
        this.noteList = notList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
       val view = ItemNoteBinding.inflate(
           LayoutInflater.from(parent.context),
              parent,
                false
       )
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
       val note = noteList[position]
        with(holder){
            itemBinding.noteTitle.text = note.title
            itemBinding.cardView.setOnClickListener {
                val intent = Intent(activity, UpdateNoteActivity::class.java)
                intent.putExtra("id", note.id)
                activity.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return noteList.size
    }
    fun returnItemAtGivenPosition(position: Int): Note {
        return noteList[position]
    }




}