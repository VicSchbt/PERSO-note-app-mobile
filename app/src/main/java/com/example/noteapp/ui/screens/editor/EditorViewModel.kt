package com.example.noteapp.ui.screens.editor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.data.model.Note
import com.example.noteapp.data.repository.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EditorViewModel(private val repository: NoteRepository): ViewModel() {
    private val _note = MutableStateFlow<Note?>(null)
    val note: StateFlow<Note?> = _note.asStateFlow()

    fun saveNote(title: String, text: String, isArchived: Boolean) = viewModelScope.launch {
        val currentId = note.value?.id
        repository.saveNote(
            if (currentId != null) {
                Note(id = currentId, title = title, text = text, isArchived = isArchived)
            } else {
                Note(title = title, text = text, isArchived = isArchived)
            }

        )
    }

    fun loadNote(id: Int) {
        viewModelScope.launch {
            val fetchedNote = repository.getNote(id)
            _note.value = fetchedNote
        }
    }

    fun resetNote() {
        _note.value = null
    }

    fun toggleNoteArchive(noteId: Int) {
        viewModelScope.launch {
            if (note.value != null) {
                repository.toggleArchive(noteId)
            }
        }
    }
}