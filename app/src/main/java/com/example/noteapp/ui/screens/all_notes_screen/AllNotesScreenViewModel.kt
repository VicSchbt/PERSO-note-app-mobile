package com.example.noteapp.ui.screens.all_notes_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.noteapp.data.model.Note
import com.example.noteapp.data.repository.NoteRepository

class AllNotesScreenViewModel(private val repository: NoteRepository): ViewModel() {
    val notes: LiveData<List<Note>> = repository.allNotes
}