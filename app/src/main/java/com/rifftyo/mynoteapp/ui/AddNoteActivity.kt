package com.rifftyo.mynoteapp.ui

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.rifftyo.mynoteapp.R
import com.rifftyo.mynoteapp.database.Note
import com.rifftyo.mynoteapp.databinding.ActivityAddNoteBinding
import com.rifftyo.mynoteapp.viewmodel.AddNoteViewModel
import com.rifftyo.mynoteapp.viewmodel.ViewModelFactory

class AddNoteActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_NOTE = "extra_note"
    }

    private lateinit var addNoteViewModel: AddNoteViewModel

    private var _activityAddNoteBinding: ActivityAddNoteBinding? = null
    private val binding get() = _activityAddNoteBinding

    private var isEdit = false
    private var note: Note? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Hubungkan ViewBinding
        _activityAddNoteBinding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        // Hubungkan ViewModel
        addNoteViewModel = obtainViewModel(this@AddNoteActivity)

        // Menerima Data dari MainActivity
        note = intent.getParcelableExtra(EXTRA_NOTE)

        // Mengubah Nilai isEdit
        if(note != null) {
            isEdit = true
        } else {
            note = Note()
        }

        // Mengatur Teks Yang Diterima Dari MainActivity
        binding?.addJudul?.setText(note?.title)
        binding?.addDeskripsi?.setText(note?.description)

        // Menyimpan Catatan
        binding?.iconBackSave?.setOnClickListener {
            saveNote()
        }

        // Menghapus Catatan
        binding?.iconDelete?.setOnClickListener {
            showDeleteConfirmationDialog()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityAddNoteBinding = null
    }

    private fun obtainViewModel(activity: AppCompatActivity): AddNoteViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(AddNoteViewModel::class.java)
    }

    // Menampilkan Pesan Konfirmasi
    private fun showDeleteConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(R.string.delete_confirmation)
            .setPositiveButton(R.string.delete) {_, _ ->
                addNoteViewModel.delete(note as Note)
                finish()
            }
            .setNegativeButton(R.string.cancel, null)
        builder.create().show()

    }

    // Fungsi Menyimpan Catatan
    private fun saveNote() {
        val title = binding?.addJudul?.text.toString().trim()
        val description = binding?.addDeskripsi?.text.toString().trim()
        when {
            title.isEmpty() -> {
                binding?.addJudul?.error = getString(R.string.empty)
            }
            description.isEmpty() -> {
                binding?.addDeskripsi?.error = getString(R.string.empty)
            }
            else -> {
                note.let { note ->
                    note?.title = title
                    note?.description = description
                }
                if (isEdit) {
                    addNoteViewModel.update(note as Note)
                } else {
                    addNoteViewModel.insert(note as Note)
                }
                finish()
            }
        }
    }

    override fun onBackPressed() {
        saveNote()
        super.onBackPressed()
    }
}