package com.example.noteapp.data.repository

import androidx.lifecycle.LiveData
import com.example.noteapp.data.dao.NoteDao
import com.example.noteapp.data.model.Note

class NoteRepository(private val noteDao: NoteDao) {

    val allNotes: LiveData<List<Note>> = noteDao.getAllNotes()

    suspend fun saveNote(note: Note) {
        noteDao.addNote(note)
    }
}