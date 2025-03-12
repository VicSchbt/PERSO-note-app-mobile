package com.example.noteapp.ui.screens.all_notes_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.data.model.Note
import com.example.noteapp.data.repository.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AllNotesScreenViewModel(repository: NoteRepository): ViewModel() {
    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes: StateFlow<List<Note>> = _notes.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getAllNotes().collect {notesList ->
                _notes.value = notesList
            }
        }
    }
}