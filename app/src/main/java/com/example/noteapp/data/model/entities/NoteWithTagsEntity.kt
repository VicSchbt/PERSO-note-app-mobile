package com.example.noteapp.data.model.entities

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.noteapp.data.model.Note
import com.example.noteapp.data.model.cross_ref.NoteTagCrossRef

data class NoteWithTagsEntity(
    @Embedded val note: NoteEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            NoteTagCrossRef::class,
            parentColumn = "noteId",
            entityColumn = "tagId"
        )
    )
    val tags: List<TagEntity>
) {
    fun toNote(): Note {
        return Note(
            id = note.id,
            title = note.title,
            text = note.text,
            isArchived = note.isArchived,
            lastEdited = note.lastEdited,
            tags = tags.map { it.toTag() }
        )
    }
}