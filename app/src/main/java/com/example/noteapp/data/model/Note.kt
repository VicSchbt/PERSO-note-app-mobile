package com.example.noteapp.data.model

import com.example.noteapp.data.model.entities.NoteEntity
import java.util.Date

data class Note(
    val id: Int = 0,
    val title: String,
    val text: String,
    val isArchived: Boolean,
    val lastEdited: Date,
    val tags: List<Tag> = emptyList() // Always present!
) {
    fun toEntity(): NoteEntity {
        return NoteEntity(
            id, title, text, isArchived, lastEdited
        )
    }
}

