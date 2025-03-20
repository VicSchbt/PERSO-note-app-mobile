package com.example.noteapp.data.model

import com.example.noteapp.data.model.entities.TagEntity

data class Tag(
    val id: Int,
    val name: String
) {
    fun toEntity(): TagEntity {
        return TagEntity(id, name)
    }
}

