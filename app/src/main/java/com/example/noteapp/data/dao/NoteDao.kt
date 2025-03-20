package com.example.noteapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.noteapp.data.model.Note
import com.example.noteapp.data.model.cross_ref.NoteTagCrossRef
import com.example.noteapp.data.model.entities.NoteEntity
import com.example.noteapp.data.model.entities.NoteWithTagsEntity
import com.example.noteapp.data.model.entities.TagEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    // ➡️ INSERT
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(noteEntity: NoteEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTag(tagEntity: TagEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNoteTagCrossRef(crossRef: NoteTagCrossRef)

    // ➡️ DELETE CROSS REFS
    @Query("DELETE FROM note_tag_cross_ref WHERE noteId = :noteId AND tagId = :tagId")
    suspend fun deleteNoteTagCrossRef(noteId: Int, tagId: Int)

    @Query("DELETE FROM note_tag_cross_ref WHERE noteId = :noteId")
    suspend fun deleteAllNoteTags(noteId: Int)

    // ➡️ SELECTS (always return WITH TAGS for consistency)
    @Transaction
    @Query("SELECT * FROM notes")
    fun getAllNotesWithTags(): Flow<List<NoteWithTagsEntity>>

    @Transaction
    @Query("SELECT * FROM notes WHERE id = :noteId")
    fun getNoteWithTags(noteId: Int): Flow<NoteWithTagsEntity>

    @Transaction
    @Query("SELECT * FROM notes WHERE isArchived = 1")
    fun getArchivedNotesWithTags(): Flow<List<NoteWithTagsEntity>>

    @Transaction
    @Query("""
        SELECT * FROM notes
        INNER JOIN note_tag_cross_ref ON notes.id = note_tag_cross_ref.noteId
        INNER JOIN tags ON tags.id = note_tag_cross_ref.tagId
        WHERE tags.name = :tagName
    """)
    fun getNotesByTag(tagName: String): Flow<List<NoteWithTagsEntity>>

    // ➡️ UPDATE NOTES (edit title/text/lastEdited)
    @Update
    suspend fun updateNote(noteEntity: NoteEntity)

    @Query("UPDATE notes SET isArchived = NOT isArchived WHERE id = :noteId")
    suspend fun toggleArchive(noteId: Int)

    @Delete
    suspend fun deleteNote(note: NoteEntity)
}
