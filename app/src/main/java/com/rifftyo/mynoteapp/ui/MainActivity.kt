package com.rifftyo.mynoteapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rifftyo.mynoteapp.adapter.NoteAdapter
import com.rifftyo.mynoteapp.databinding.ActivityMainBinding
import com.rifftyo.mynoteapp.viewmodel.MainViewModel
import com.rifftyo.mynoteapp.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity() {

    private var _activityMainBinding: ActivityMainBinding? = null
    private val binding get() = _activityMainBinding

    private lateinit var adapter: NoteAdapter
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Koneksi ViewBinding
        _activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        // Koneksi MainViewModel
        mainViewModel = obtainViewModel(this@MainActivity)

        // Koneksi Adapter
        adapter = NoteAdapter()
        binding?.rvListNote?.layoutManager = LinearLayoutManager(this)
        binding?.rvListNote?.setHasFixedSize(true)
        binding?.rvListNote?.adapter = adapter

        // Aksi Fab Add Note
        binding?.fabAddNote?.setOnClickListener {
            val intent = Intent(this@MainActivity, AddNoteActivity::class.java)
            startActivity(intent)
        }

        // Menambahkan List Catatan
        mainViewModel.getAllNotes().observe(this) { noteList ->
            if (noteList != null) {
                adapter.setListNotes(noteList)
            }
        }

        // Mencari Judul Catatan
        with(binding) {
            this?.searchView?.setupWithSearchBar(searchBar)
            this?.searchView
                ?.editText
                ?.setOnEditorActionListener{ textView, actionId, event ->
                    searchBar.setText(searchView.text)
                    val query = textView.text.toString()
                    mainViewModel.searchNotes(query).observe(this@MainActivity) { filteredList ->
                        if (filteredList != null) {
                            adapter.setListNotes(filteredList)
                        }
                    }
                    searchView?.hide()
                    false
                }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityMainBinding = null
    }

    private fun obtainViewModel(activity: AppCompatActivity): MainViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(MainViewModel::class.java)
    }
}