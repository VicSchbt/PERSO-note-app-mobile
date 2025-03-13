package com.example.noteapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id") val id: Int = 0,
    @ColumnInfo("title") val title: String,
    @ColumnInfo("text") val text: String,
    @ColumnInfo("isArchived", defaultValue = "0") val isArchived: Boolean = false,
    @ColumnInfo("lastEdited") val lastEdited: Date
)
