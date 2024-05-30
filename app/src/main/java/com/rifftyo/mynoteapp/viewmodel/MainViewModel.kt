package com.rifftyo.mynoteapp.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.rifftyo.mynoteapp.database.Note
import com.rifftyo.mynoteapp.repository.NoteRepository

class MainViewModel(application: Application): ViewModel() {
    private val mNoteRepository: NoteRepository = NoteRepository(application)

    fun getAllNotes(): LiveData<List<Note>> = mNoteRepository.getAllNotes()

    fun searchNotes(searchQuery: String): LiveData<List<Note>> = mNoteRepository.searchNotes(searchQuery)
}