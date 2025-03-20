package com.example.noteapp.data.repository

import com.example.noteapp.data.dao.NoteDao
import com.example.noteapp.data.model.Note
import com.example.noteapp.data.model.Tag
import com.example.noteapp.data.model.cross_ref.NoteTagCrossRef
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Date

class NoteRepository(private val noteDao: NoteDao) {

    // ➡️ GET ALL NOTES (WITH TAGS)
    fun getAllNotes(): Flow<List<Note>> =
        noteDao.getAllNotesWithTags().map { it.map { entity -> entity.toNote() } }

    fun getArchivedNotes(): Flow<List<Note>> =
        noteDao.getArchivedNotesWithTags().map { it.map { entity -> entity.toNote() } }

    fun getNotesByTag(tagName: String): Flow<List<Note>> =
        noteDao.getNotesByTag(tagName).map { it.map { entity -> entity.toNote() } }

    fun getNote(noteId: Int): Flow<Note> =
        noteDao.getNoteWithTags(noteId).map { it.toNote() }

    // ➡️ CREATE NOTE WITHOUT TAGS
    suspend fun saveNote(note: Note) {
        val now = Date() // lastEdited should be set here
        val noteEntity = note.copy(lastEdited = now).toEntity()
        noteDao.insertNote(noteEntity)
    }

    // ➡️ CREATE NOTE WITH TAGS
    suspend fun addNoteWithTags(note: Note) {
        val now = Date()
        val noteEntity = note.copy(lastEdited = now).toEntity()
        val noteId = noteDao.insertNote(noteEntity)

        note.tags.forEach { tag ->
            val tagId = noteDao.insertTag(tag.toEntity())
            noteDao.insertNoteTagCrossRef(
                NoteTagCrossRef(noteId = noteId.toInt(), tagId = tagId.toInt())
            )
        }
    }

    // ➡️ EDIT NOTE (update title/text + lastEdited)
    suspend fun editNote(note: Note) {
        val now = Date()
        val updatedNote = note.copy(lastEdited = now).toEntity()
        noteDao.updateNote(updatedNote)
    }

    // ➡️ EDIT TAGS OF A NOTE
    suspend fun replaceNoteTags(noteId: Int, newTags: List<Tag>) {
        // Clear existing crossRefs
        noteDao.deleteAllNoteTags(noteId)

        // Insert new crossRefs
        newTags.forEach { tag ->
            val tagId = noteDao.insertTag(tag.toEntity())
            noteDao.insertNoteTagCrossRef(
                NoteTagCrossRef(noteId = noteId, tagId = tagId.toInt())
            )
        }
    }

    // ➡️ ADD TAG TO EXISTING NOTE
    suspend fun addTagToNote(noteId: Int, tag: Tag) {
        val tagId = noteDao.insertTag(tag.toEntity())
        noteDao.insertNoteTagCrossRef(NoteTagCrossRef(noteId, tagId.toInt()))
    }

    // ➡️ REMOVE TAG FROM EXISTING NOTE
    suspend fun removeTagFromNote(noteId: Int, tagId: Int) {
        noteDao.deleteNoteTagCrossRef(noteId, tagId)
    }

    // ➡️ TOGGLE ARCHIVE
    suspend fun toggleArchive(noteId: Int) {
        noteDao.toggleArchive(noteId)
    }

    // ➡️ DELETE NOTE (and tags crossRefs automatically with CASCADE)
    suspend fun deleteNote(note: Note) {
        noteDao.deleteNote(note.toEntity())
        // Optional cleanup if not using CASCADE:
        // noteDao.deleteAllNoteTags(note.id)
    }
}
