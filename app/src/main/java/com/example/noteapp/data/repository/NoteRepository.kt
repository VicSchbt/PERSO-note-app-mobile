package com.example.noteapp.data.repository

import androidx.lifecycle.LiveData
import com.example.noteapp.data.dao.NoteDao
import com.example.noteapp.data.model.Note
import kotlinx.coroutines.flow.Flow

class NoteRepository(private val noteDao: NoteDao) {

    fun getAllNotes(): Flow<List<Note>> = noteDao.getAllNotes()

    suspend fun saveNote(note: Note) {
        noteDao.addNote(note)
    }

    suspend fun getNote(noteId: Int): Note? {
        return noteDao.getNote(noteId)
    }

}