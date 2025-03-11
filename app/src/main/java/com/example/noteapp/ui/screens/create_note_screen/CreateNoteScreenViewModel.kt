package com.example.noteapp.ui.screens.create_note_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.data.model.Note
import com.example.noteapp.data.repository.NoteRepository
import kotlinx.coroutines.launch

class CreateNoteScreenViewModel(private val repository: NoteRepository): ViewModel() {
    fun saveNote(title: String, text: String) = viewModelScope.launch {
        repository.saveNote(
            Note(title = title, text = text)
        )
    }
}