package com.rifftyo.mynoteapp.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import com.rifftyo.mynoteapp.database.Note
import com.rifftyo.mynoteapp.repository.NoteRepository

class AddNoteViewModel(application: Application): ViewModel() {
    private val mNoteRepository: NoteRepository = NoteRepository(application)

    fun insert(note: Note) {
        mNoteRepository.insert(note)
    }

    fun update(note: Note) {
        mNoteRepository.update(note)
    }

    fun delete(note: Note) {
        mNoteRepository.delete(note)
    }
}