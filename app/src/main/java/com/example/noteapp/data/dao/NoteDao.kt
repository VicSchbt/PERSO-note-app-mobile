package com.example.noteapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.noteapp.data.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNote(note: Note)

    @Query("SELECT * FROM notes")
    fun getAllNotes(): Flow<List<Note>>

    @Query("SELECT * FROM notes WHERE isArchived = 1")
    fun getArchivedNotes(): Flow<List<Note>>

    @Query("SELECT * FROM notes WHERE id = :id")
    suspend fun getNote(id: Int): Note?

    @Query("UPDATE notes SET isArchived = NOT isArchived WHERE id = :noteId")
    suspend fun toggleArchive(noteId: Int)

    @Delete
    suspend fun deleteNote(note: Note)
}