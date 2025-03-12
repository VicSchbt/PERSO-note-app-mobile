package com.example.noteapp.data.model

import androidx.compose.runtime.Composable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id") val id: Int = 0,
    @ColumnInfo("title") val title: String,
    @ColumnInfo("text") val text: String
)
