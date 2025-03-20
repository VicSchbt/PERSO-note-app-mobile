package com.example.noteapp.ui.screens.editor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.data.model.Note
import com.example.noteapp.data.model.Tag
import com.example.noteapp.data.repository.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Date

class EditorViewModel(private val repository: NoteRepository): ViewModel() {
    private val _note = MutableStateFlow<Note?>(null)
    val note: StateFlow<Note?> = _note.asStateFlow()

    var tagsListForNewNote: List<Tag> = emptyList()

    /**
     * Save or update a note.
     * - If it's a new note -> creates it
     * - If it's an existing note -> updates it
     * Tags are preserved unless explicitly updated via unother function
     */
    fun saveNote(title: String, text: String, isArchived: Boolean) = viewModelScope.launch {
        val currentNote = _note.value

        if (currentNote == null) {
            // Create a new note (without tags initially)
            val newNote = Note(
                title = title,
                text = text,
                isArchived = isArchived,
                lastEdited = Date(),
                tags = emptyList()
            )
            repository.addNoteWithTags(newNote)
            _note.value = newNote // Update local state
        } else {
            // Update existing note (tags stay as they are)
            val updatedNote = currentNote.copy(
                title = title,
                text = text,
                isArchived = isArchived
            )
            repository.editNote(updatedNote)
            _note.value = updatedNote
        }
    }

    /**
     * Loads a note from the repository and observe it.
     */
    fun loadNote(id: Int) {
        viewModelScope.launch {
            repository.getNote(id).collect{ fetchedNote ->
                _note.value = fetchedNote
            }
        }
    }

    /**
     * Reset the current note being edited
     */
    fun resetNote() {
        _note.value = null
    }

    /**
     * Toggles the archived state of the note.
     * Also updates local state.
     */
    fun toggleNoteArchive(noteId: Int) {
        val currentNote = _note.value ?: return

        viewModelScope.launch {
            repository.toggleArchive(currentNote.id)

            // Update local state manually to reflect toggle
            _note.value = currentNote.copy(isArchived = !currentNote.isArchived)
        }
    }

    /**
     * Deletes the current note.
     */
    fun deleteCurrentNote() {
        val currentNote = _note.value ?: return

        viewModelScope.launch {
            repository.deleteNote(currentNote)
            _note.value = null
        }
    }

    /**
     * Replace all tags for the current note.
     */
    fun replaceTagsForCurrentNote(newTags: List<Tag>) {
        val currentNote = _note.value

        if (currentNote == null) {
            tagsListForNewNote = newTags
        } else {
            viewModelScope.launch {
                repository.replaceNoteTags(currentNote.id, newTags)
            }
        }
    }

    /**
     * Add a tag to the current note.
     */
    fun addTagToCurrentNote(tag: Tag) {
        val currentNote = _note.value ?: return

        viewModelScope.launch {
            repository.addTagToNote(currentNote.id, tag)

            // Update local state
            val updatedTags = currentNote.tags + tag
            _note.value = currentNote.copy(tags = updatedTags)
        }
    }

    /**
     * Remove a tag from the current note.
     */
    fun removeTagFromCurrentNote(tag: Tag) {
        val currentNote = _note.value ?: return

        viewModelScope.launch {
            repository.removeTagFromNote(currentNote.id, tag.id)

            // Update local state
            val updatedTags = currentNote.tags.filter { it.id != tag.id }
            _note.value = currentNote.copy(tags = updatedTags)
        }
    }
}