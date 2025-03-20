package com.example.noteapp.data.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.noteapp.data.model.Tag

@Entity(tableName = "tags")
data class TagEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String
) {
    fun toTag(): Tag = Tag(id, name)
}