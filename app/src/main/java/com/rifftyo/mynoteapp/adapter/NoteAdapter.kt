package com.rifftyo.mynoteapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rifftyo.mynoteapp.database.Note
import com.rifftyo.mynoteapp.databinding.ListItemBinding
import com.rifftyo.mynoteapp.helper.NoteDiffCallback
import com.rifftyo.mynoteapp.ui.AddNoteActivity

class NoteAdapter: RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
    private val listNotes = ArrayList<Note>()

    fun setListNotes(listNote: List<Note>) {
        val diffCallback = NoteDiffCallback(this.listNotes, listNote)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listNotes.clear()
        this.listNotes.addAll(listNote)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(listNotes[position])
    }

    override fun getItemCount(): Int {
        return listNotes.size
    }

    inner class NoteViewHolder(private val binding: ListItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Note) {
            with(binding) {
                tvJudul.text = note.title
                tvIsiNote.text = note.description
                detailNote.setOnClickListener {
                    val intent = Intent(it.context, AddNoteActivity::class.java)
                    intent.putExtra(AddNoteActivity.EXTRA_NOTE, note)
                    it.context.startActivity(intent)
                }
            }
        }
    }
}